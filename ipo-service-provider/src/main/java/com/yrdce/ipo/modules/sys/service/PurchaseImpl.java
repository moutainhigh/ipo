package com.yrdce.ipo.modules.sys.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yrdce.ipo.modules.sys.dao.FFirmfundsMapper;
import com.yrdce.ipo.modules.sys.dao.IpoCommodityMapper;
import com.yrdce.ipo.modules.sys.dao.IpoOrderMapper;
import com.yrdce.ipo.modules.sys.entity.IpoCommodity;
import com.yrdce.ipo.modules.sys.entity.IpoOrder;

/**
 * 申购服务
 * 
 * @author Bob
 * 
 */
@Service("Purchase")
public class PurchaseImpl implements Purchase {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private FFirmfundsMapper funds;
	@Autowired
	private IpoCommodityMapper com;
	@Autowired
	private IpoOrderMapper order;
	@Autowired
	@Qualifier("systemService")
	private SystemService system;

	// 时间判断
	public boolean isInDates(String sId) {
		logger.info("查询商品一列信息");
		try {
			IpoCommodity c = com.selectByComid(sId.toUpperCase());
			logger.info("获取开始时间");
			Date ftimeStart1 = c.getStarttime();
			Date ftimeEnd1 = c.getEndtime();
			Date times = new Date();
			if (times.after(ftimeStart1) && times.before(ftimeEnd1)) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	// 申购
	@Override
	@Transactional(readOnly = true)
	public int apply(String userId, String sId, Integer counts, Integer id) {
		logger.info("进入申购方法");
		final int SECCESS = 0;
		final int NOT_COMMODITY_TIME = 1;
		final int LACK_OF_FUNDS = 2;
		final int REPEAT = 3;
		final int ERROR = 4;
		final int OUT_OF_QUOTA = 5;
		final int NOT_TIME = 6;
		try {
			if (system.canSystemTrade()) {
				String ID = sId.toUpperCase();
				if (this.isInDates(ID)) {
					logger.info("进入时间判断");
					if (this.repeat(userId, sId)) {
						logger.info("进入重复申购");
						// TODO Auto-generated method stub
						// 获取商品信息
						logger.info("获取商品信息");
						IpoCommodity commodity = com.selectByComid(ID);
						// 获取商品名称
						String name = commodity.getCommodityname();
						// 商品单价
						BigDecimal price = commodity.getPrice();
						// 获取申购额度
						long e = commodity.getPurchaseCredits();
						// 发售单位
						int units = commodity.getUnits();
						// 获取客户可用资金
						logger.info("调用资金存储函数");
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("monery", "");
						param.put("userid", userId);
						param.put("lock", 0);
						funds.getMonery(param);
						BigDecimal monery = (BigDecimal) param.get("monery");
						// int类型转换，购买几个单位
						BigDecimal bigDecimal = new BigDecimal(counts);
						// 一单位
						BigDecimal Unitprice = new BigDecimal(units);
						// 1单位价格
						BigDecimal total = price.multiply(Unitprice);
						// 申购消费总额
						BigDecimal allMonery = bigDecimal.multiply(total);
						// 申购额度判断
						if (counts < e) {
							// 申购资金判断
							if (monery.compareTo(allMonery) != -1) {
								logger.info("进入资金判断");
								// 当前时间
								Timestamp date = new Timestamp(System.currentTimeMillis());
								IpoOrder d = new IpoOrder();
								d.setUserid(userId);
								d.setCommodityid(sId);
								d.setCommodityname(name);
								d.setCounts(counts);
								d.setCreatetime(date);
								d.setFrozenfunds(allMonery);
								d.setFrozenst(0);
								d.setCommodity_id(id);
								order.insert(d);
								// 0：申购成功
								// 1：配号成功
								// 2：摇号成功
								// 3：结算成功
								com.updateByStatus(0, sId);

								this.frozen(userId, allMonery);

								return SECCESS;
							} else {
								return LACK_OF_FUNDS;
							}
						} else {
							return OUT_OF_QUOTA;
						}
					} else {
						return REPEAT;
					}
				} else {
					return NOT_COMMODITY_TIME;
				}
			} else {
				return NOT_TIME;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	// 冻结资金
	public BigDecimal frozen(String userId, BigDecimal allMonery) {
		logger.info("调用冻结资金函数");
		float mony = allMonery.floatValue();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("monery", "");
			param.put("userid", userId);
			param.put("amount", mony);
			param.put("moduleid", "40");
			System.out.println(new Date());
			funds.getfrozen(param);
			System.out.println(new Date());
			BigDecimal monery = new BigDecimal((Double) (param.get("monery")));
			return monery;
		} catch (Exception e) {
			e.printStackTrace();
			return new BigDecimal(0);
		}
	}

	// 判断是重复申购
	public boolean repeat(String userId, String sId) {
		logger.info("查询商品重复申购 ");
		IpoOrder ipoOrder = order.selectByid(userId, sId);
		if (ipoOrder != null) {
			return false;
		} else {
			return true;
		}
	}

}
