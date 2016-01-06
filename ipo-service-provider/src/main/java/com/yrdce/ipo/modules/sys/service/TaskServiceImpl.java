package com.yrdce.ipo.modules.sys.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.yrdce.ipo.common.utils.DateUtil;
import com.yrdce.ipo.common.utils.Selection;
import com.yrdce.ipo.modules.sys.dao.IpoBallotNoInfoMapper;
import com.yrdce.ipo.modules.sys.dao.IpoCommodityConfMapper;
import com.yrdce.ipo.modules.sys.dao.IpoCommodityMapper;
import com.yrdce.ipo.modules.sys.dao.IpoDistributionMapper;
import com.yrdce.ipo.modules.sys.dao.IpoNumberofrecordsMapper;
import com.yrdce.ipo.modules.sys.dao.IpoOrderMapper;
import com.yrdce.ipo.modules.sys.dao.IpoPositionMapper;
import com.yrdce.ipo.modules.sys.entity.IpoBallotNoInfo;
import com.yrdce.ipo.modules.sys.entity.IpoCommodity;
import com.yrdce.ipo.modules.sys.entity.IpoCommodityConf;
import com.yrdce.ipo.modules.sys.entity.IpoCommodityExtended;
import com.yrdce.ipo.modules.sys.entity.IpoDistribution;
import com.yrdce.ipo.modules.sys.entity.IpoNumberofrecords;
import com.yrdce.ipo.modules.sys.entity.IpoOrder;
import com.yrdce.ipo.modules.sys.entity.IpoPosition;

/**
 * 定时任务相关的 service
 * 
 * @author wq 2016-1-2
 *
 */
public class TaskServiceImpl implements TaskService {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IpoOrderMapper order;
	@Autowired
	private Distribution distribution;
	@Autowired
	private IpoNumberofrecordsMapper unmberofrecord;
	@Autowired
	private IpoCommodityMapper commodity;
	@Autowired
	private IpoDistributionMapper ipoDistribution;
	@Autowired
	private IpoBallotNoInfoMapper ipoBallotNoInfoMapper;
	@Autowired
	private IpoCommodityConfMapper commodityConfMapper;
	@Autowired
	private IpoPositionMapper ipoPositionMapper;
	@Autowired
	private IpoCommodityMapper commodityMapper;
	/**
	 * 配号
	 * 
	 * @throws Exception
	 */
	public void distribution() throws Exception {
		List<IpoCommodityConf> commodityConfList = commodityConfMapper.findAllIpoCommConfs();
		logger.info("遍历商品配置表");
		for (IpoCommodityConf conf : commodityConfList) {
			int i = +1;
			logger.info("遍历商品配置表:" + i);
			int day = conf.getTradedays();
			String oldtime = DateUtil.getTime(day);// 做了修改，此处参数应为day
			Date endtime = conf.getEndtime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String endtime1 = sdf.format(endtime);
			if (oldtime.equals(endtime1)) {
				logger.info("T+N天符合要求");
				String commodityid = conf.getCommodityid();
				List<IpoOrder> orderList = order.selectByCid(commodityid);
				if (orderList.size() != 0) {
					IpoNumberofrecords frecord = new IpoNumberofrecords();
					Date date = new Date();
					frecord.setCommodityid(commodityid);
					frecord.setCounts(BigDecimal.valueOf(0));
					frecord.setNowtime(date);
					unmberofrecord.insert(frecord);

					logger.info("调用配号任务");
					distribution.start(orderList);
				}
			}
		}

	}

	/**
	 * 摇号
	 * 
	 * @throws Exception
	 */

	@Transactional
	public void lottery() throws Exception {
		// 查找所有此商品的申购记录
		System.out.println("申购记录查询开始");
		String ballotNowtime = DateUtil.getTime(1);// 做了修改，此处参数应为1
		List<IpoDistribution> ipoDidList = ipoDistribution.allByTime(ballotNowtime);
		logger.info(ipoDidList.size() + "");
		for (IpoDistribution ipoDistribution1 : ipoDidList) {
			String commId = ipoDistribution1.getCommodityid();
			commolottery(commId);
		}

	}

	//商品摇号
	public void commolottery(String commId) throws Exception{
		logger.info("commID:" + commId);
		List<IpoDistribution> ipoDidList = ipoDistribution.selectByCommId(commId);
		IpoCommodity ipoCommodity = commodity.getSelectByComid(commId.toUpperCase());
		if (ipoCommodity.getStatus() == 2 || ipoCommodity.getStatus() == 3) {
			commodity.updateByStatus(31, commId);// 31表示摇号中
			commodityConfMapper.updateByStatus(31, commId);
			int commCounts = ipoCommodity.getCounts();
			logger.info("commCounts:" + commCounts);
			int saleCounts = order.bycommodityid(commId);
			logger.info("saleCounts:" + saleCounts);
			Selection selection = new Selection();
			List<String> endNumList = selection.MainSelection(commCounts, saleCounts);// 尾号集合
			System.out.println("申购记录查询成功");
			int numLength = String.valueOf(ipoDidList.get(0).getStartnumber()).length();// 配号号码长度
			// 号码匹配
			System.out.println("中签号匹配开始");
			List<IpoDistribution> ipoDidList1 = ipoDistribution.selectByCommId(commId);
			for (IpoDistribution ipoDis : ipoDidList1) {
				int userGetNum = 0;
				System.out.println(ipoDis.getUserid() + "尾号个数" + endNumList.size());
				System.out.println(ipoDis.getUserid() + "起始号码" + ipoDis.getStartnumber());
				System.out.println(ipoDis.getUserid() + "匹配个数" + ipoDis.getPcounts());
				for (String endNum : endNumList) {
					userGetNum += selection.OwnMatchingEndNum((int) ipoDis.getStartnumber(), ipoDis.getPcounts(), endNum);
				}
				System.out.println(ipoDis.getUserid() + "匹配个数" + userGetNum);
				ipoDis.setZcounts(userGetNum);// 更新对象中匹配的个数
				ipoDistribution.updateByPrimaryKey(ipoDis);// 更新数据库记录
				// commodityConfMapper.updateByStatus(3, commId);
				// commodity.updateByStatus(3, commId);
				System.out.println("中签号匹配完成");
			}
			System.out.println(commId + "尾号记录开始");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date dt = sdf.parse(DateUtil.getTime(0));
			IpoBallotNoInfo ipoBallotNoInfo = new IpoBallotNoInfo();
			// 将尾号记录到数据库
			for (String endNum : endNumList) {
				ipoBallotNoInfo.setBallotno(endNum);
				ipoBallotNoInfo.setBallotnoendlen(Integer.valueOf(numLength).shortValue());
				ipoBallotNoInfo.setBallotnostartlen(Integer.valueOf(numLength - endNum.length()).shortValue());
				ipoBallotNoInfo.setCommodityid(commId);
				ipoBallotNoInfo.setCreatetime(dt);
				ipoBallotNoInfoMapper.insert(ipoBallotNoInfo);
			}
			System.out.println(commId + "尾号记录成功");
			commodityConfMapper.updateByStatus(3, commId);
			commodity.updateByStatus(3, commId);
			logger.info("摇号结束");
		}
	}
	/**
	 * 申购结算
	 */
	@Transactional
	@Override
	public void orderBalance() throws Exception {
		// TODO Auto-generated method stub
		logger.info("申购结算开始");
		logger.info("开始获取所有未结算的中签记录");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String ballotNowtime = DateUtil.getTime(2);
		//List<IpoDistribution> distributions = ipoDistribution.getInfobyDate(ballotNowtime);
		List<IpoDistribution> distributions = ipoDistribution.allByTime(ballotNowtime);
		logger.info("费用结算开始");
		for (IpoDistribution ipod : distributions) {
			List<IpoDistribution> ipoDidList1 = ipoDistribution.selectByCommId(ipod.getCommodityid());
			for (IpoDistribution ipodb : ipoDidList1) {
				if (ipodb.getZcounts() != 0) {
					logger.info("获取发售商品信息" + ipodb.getCommodityid());
					IpoCommodityExtended commodityExtended = commodity.selectPriceByCommodityid(ipodb.getCommodityid());
					IpoCommodityConf commodityConf = commodityConfMapper.selectCommUnit(ipodb.getCommodityid());
					if (commodityConf != null) {
						BigDecimal bigDecimal = commodityExtended.getPrice();
						logger.info("计算成交金额" + bigDecimal);
						BigDecimal tempPrice = bigDecimal.multiply(new BigDecimal(ipodb.getZcounts()));
						logger.info("成交金额" + tempPrice);
						ipod.setTradingamount(tempPrice);
						logger.info("计算手续费" + commodityConf.getTradealgr());
						short tradealgr = commodityConf.getTradealgr();
						logger.info("计算手续费算法" + tradealgr);
						if (tradealgr == 1) {
							BigDecimal tempDecimal = new BigDecimal(tradealgr).divide(new BigDecimal(100));
							BigDecimal counterfee = tempPrice.multiply(tempDecimal);
							ipodb.setCounterfee(counterfee);
						} else if (tradealgr == 2) {
							BigDecimal counterfee =  new BigDecimal(tradealgr).multiply(new BigDecimal(ipod.getZcounts())) ;
							ipodb.setCounterfee(counterfee);
						}
						Date dt = sdf.parse(DateUtil.getTime(0));
						ipodb.setFrozendate(dt);
						logger.info("跟新中签计算金额开始");
						ipoDistribution.setSomeInfo(ipodb);
						logger.info("跟新中签计算金额结束");
						transferPosition(commodityExtended, ipodb, commodityConf);
						commodityMapper.updateStatusByStatusId(3, 32, ipodb.getCommodityid());
					}
				}
			}
	
		}
		logger.info("申购结束");
	}

	private void transferPosition(IpoCommodityExtended comm, IpoDistribution dst, IpoCommodityConf commodityConf) throws Exception {
		// TODO Auto-generated method stub
		logger.info("转持仓开始");
		String userid = dst.getUserid();
		String commid = comm.getCommodityid();
		IpoPosition ipoPosition = ipoPositionMapper.selectPosition(userid, commid);
		if (ipoPosition != null) {
			long price = ipoPosition.getPositionPrice();
			long num = comm.getPrice().longValue();
			long sum = price + num;
			ipoPositionMapper.updatePosition(userid, commid, sum);
		} else {
			String commUnit = commodityConf.getContractfactorname();
			IpoPosition record = new IpoPosition();
			record.setFirmid(dst.getUserid());
			record.setPosition((long) dst.getZcounts());
			record.setCommodityid(dst.getCommodityid());
			record.setCommodityname(dst.getCommodityname());
			record.setPositionPrice(comm.getPrice().longValue());
			record.setPositionUnit(commUnit);
			ipoPositionMapper.insert(record);
		}
		logger.info("转持仓结束");
	}

}
