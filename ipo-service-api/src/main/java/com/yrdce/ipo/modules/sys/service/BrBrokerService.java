package com.yrdce.ipo.modules.sys.service;

import java.util.List;

import com.yrdce.ipo.modules.sys.vo.PublisherBalance;
import com.yrdce.ipo.modules.sys.vo.PublisherSettle;
import com.yrdce.ipo.modules.sys.vo.VBrBroker;

/**
 * 加盟商
 * 
 * @author chenjing
 *
 */
public interface BrBrokerService {

	/**
	 *
	 * 查询所有发行会员
	 */
	public List<VBrBroker> findAllPublisher();

	/**
	 *
	 * 查询所有承销会员
	 */
	public List<VBrBroker> findAllUnderwriter();

	int insert(VBrBroker record);

	public VBrBroker queryBrokerById(String brokerId);

	/**
	 *
	 * 查询交易商的当日和上日余额
	 */
	public PublisherBalance findBalance(String publisherid, String today);

	/**
	 *
	 * 查询发行商的当日认购金额和日发行手续费
	 */
	public List<PublisherSettle> findLoanAndHandling(String publisherid,
			String today);

	/**
	 *
	 * 查询各大会员可用资金
	 */
	public List<VBrBroker> findBrokerBalance(String page, String rows,
			VBrBroker broker, String type);

	/**
	 *
	 * 查询各大不同类型会员人数
	 */
	public Integer findBrokerNum(VBrBroker broker, String type);

	/**
	 * @Title: getBrokerBalance
	 * @Description: 分页查询承销会员可用资金和冻结货款
	 * @param brokerType
	 *            ,beginnum,endnum
	 */
	List<VBrBroker> getUnderscribeFunds(VBrBroker broker, String type,
			String page, String rows);

	/**
	 * @Title: getBrokerNum
	 * @Description: 查询承销会员人数
	 * @param brokerType
	 */
	int getUnderscribeFundsCount(VBrBroker broker, String type);

}
