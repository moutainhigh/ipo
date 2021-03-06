package com.yrdce.ipo.modules.sys.service;

import java.math.BigDecimal;
import java.util.List;

import com.yrdce.ipo.modules.sys.vo.UnderWriters;
import com.yrdce.ipo.modules.sys.vo.UnderwriterSubscribe;

/**
 * 承销会员认购管理承销设置
 * 
 * @author chenjing
 *
 */
public interface UnderwriterSubscribeService {

	/**
	 * 承销设置列表
	 * 
	 * @param page
	 * @param rows
	 * @param eample
	 * @return
	 */
	public List<UnderwriterSubscribe> getInfosByPage(String page, String rows,
			UnderwriterSubscribe example);

	/**
	 * 获取记录数
	 * 
	 * @param eample
	 * @return
	 */
	public Integer getQueryNum(UnderwriterSubscribe example);

	/**
	 * 删除承销设置信息
	 * 
	 * @param id
	 * @return
	 */
	public String deleteInfo(String ids);

	/**
	 * 添加承销设置信息
	 * 
	 * @param example
	 * @return
	 */
	public Integer insertInfo(UnderwriterSubscribe example);

	/**
	 * 查询承销商是否重复认购商品
	 * 
	 * @param example
	 * @return
	 */
	public String checkExist(UnderwriterSubscribe example);

	/**
	 * 获取承销商的承销商手续费比例总和最大值
	 * 
	 * @param example
	 * @return
	 */
	public Float checkRatioSum(String commodityId);

	/**
	 * 查询是否超出某商品的供认购总量
	 * 
	 * @param example
	 * @return
	 */
	public String checkTotalCounts(String commodityId, Long quantity);

	/**
	 * 查询认购的商品是否超出冻结资金
	 * 
	 * @param example
	 * @return
	 */
	public String checkFrozenFunds(UnderwriterSubscribe example);

	/**
	 * 查询发行会员的承销商列表
	 * 
	 * @param page
	 * @param rows
	 * @param example
	 * @return
	 */
	public List<UnderWriters> findUnderwriters(String page, String rows,
			UnderWriters example);

	/**
	 * 获取发行会员的承销商个数
	 * 
	 * @param example
	 * @return
	 */
	public Integer getUnderwritersNum(UnderWriters example);

	/**
	 * 认购资金解冻
	 * 
	 * @param example
	 * @return
	 */
	public void unfrozen(UnderwriterSubscribe example, BigDecimal frozenFunds,
			String updater);

	/**
	 * 获取未处理的设置信息
	 * 
	 * @param brokerid
	 * @return
	 */
	public List<UnderwriterSubscribe> selectUnFrozeSet(String brokerid);

	/**
	 * 解冻客户资金
	 * 
	 * @param firmId
	 * @param amount
	 *            amount （传正值）
	 */
	public void unfreeFunds(String firmId, BigDecimal amount);

}
