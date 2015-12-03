/**
 * 
 */
package com.yrdce.ipo.modules.sys.service;

import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yrdce.ipo.common.vo.ResultMsg;
import com.yrdce.ipo.modules.sys.SystemManager;
import com.yrdce.ipo.modules.sys.dao.IpoSysStatusMapper;
import com.yrdce.ipo.modules.sys.vo.IpoSysStatus;

/**
 * @author hxx
 *
 */
@org.springframework.stereotype.Service
public class SystemServiceImpl implements SystemService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IpoSysStatusMapper mapper;

	@Autowired
	private SystemManager systemManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yrdce.ipo.modules.sys.service.SysStatusService#querySysStatus()
	 */
	@Override
	@Transactional(readOnly = true)
	public IpoSysStatus querySysStatus() {
		try {
			com.yrdce.ipo.modules.sys.entity.IpoSysStatus status = mapper.selectAll();
			if (status != null) {
				IpoSysStatus result = new IpoSysStatus();
				BeanUtils.copyProperties(result, status);
				return result;
			} else
				return null;
		} catch (Exception e) {
			logger.error("error:", e);
			return null;
		}
	}

	@Override
	public Date getDBTime() {
		try {
			return mapper.getDBTime();
		} catch (Exception e) {
			logger.error("error:", e);
			return null;
		}
	}

	public String getDBTimeStr() {
		try {
			return mapper.getDBTimeStr();
		} catch (Exception e) {
			logger.error("error:", e);
			return null;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public synchronized ResultMsg sysControl(String code) throws Exception {
		ResultMsg msg = new ResultMsg();
		String status = null;
		if (OPR_MARKET_OPEN.equals(code)) {
			status = systemManager.openMarket();
			msg.getBusiness().put("tradeDate", getDBTimeStr());
		} else if (OPR_TRADE_PAUSE.equals(code)) {
			status = systemManager.pauseTrade();
		} else if (OPR_TRADE_RESUME.equals(code)) {
			status = systemManager.resumeTrade();
		} else if (OPR_TRADE_CLOSE.equals(code)) {
			status = systemManager.closeTrade();
		} else if (OPR_MARKET_CLOSE.equals(code)) {
			status = systemManager.closeMarket();
		}

		if (status != null) {
			msg.getBusiness().put("status", status);
		} else {
			msg.setResult(ResultMsg.RESULT_ERROR);
			msg.setMsg("操作失败");
		}

		return msg;
	}

	@Override
	public boolean canSystemTrade() throws Exception {
		return systemManager.canSystemTrade();
	}

	@Override
	public void reloadSections() {
		systemManager.reloadSections();
	}

}
