package com.yrdce.ipo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.json.JSON;
import com.yrdce.ipo.modules.sys.service.DeliveryOrderService;
import com.yrdce.ipo.modules.sys.service.OutboundService;
import com.yrdce.ipo.modules.sys.vo.DeliveryOrder;
import com.yrdce.ipo.modules.sys.vo.Outbound;
import com.yrdce.ipo.modules.sys.vo.OutboundExtended;
import com.yrdce.ipo.modules.sys.vo.ResponseResult;

/**
 * 入库申请Controller
 * 
 * @author 李伟东
 * @version 2015.12.20
 */
@Controller
@RequestMapping("OutBoundController")
public class OutBoundController {
	@Autowired
	private OutboundService outboundService;
	@Autowired
	private DeliveryOrderService deliveryOrderService;
	static org.slf4j.Logger log = org.slf4j.LoggerFactory
			.getLogger(OutBoundController.class);

	@RequestMapping(value = "/geOutBoundInfo", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String geOutBoundInfo(@RequestParam("page") String page,
			@RequestParam("rows") String rows, OutboundExtended outbound) {
		try {
			System.out.println("start");
			List<OutboundExtended> slist = outboundService.getAllOutboundInfo(
					page, rows, outbound);
			int counts = outboundService.getTotalNum(outbound);
			ResponseResult result = new ResponseResult();
			result.setRows(slist);
			result.setTotal(counts);
			System.out.println(JSON.json(result));
			return JSON.json(result);
		} catch (Exception e) {
			log.error("获取出库信息异常", e);
			return "";
		}
	}

	@RequestMapping(value = "/getDeliveryInfo", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getDeliveryInfo(DeliveryOrder order) {
		try {
			log.info("获取提货单信息");
			DeliveryOrder deliveryOrder;
			if (!order.getPickupPassword().equals("")) {
				deliveryOrder = deliveryOrderService
						.getPickupDeliveryInfo(order);
			} else {
				deliveryOrder = deliveryOrderService
						.getExpressDeliveryInfo(order);
			}
			System.out.println(deliveryOrder);
			if (deliveryOrder != null) {
				String request = JSON.json(deliveryOrder);
				return request;
			} else {
				return "";
			}
		} catch (Exception e) {
			log.error("获取提货单信息异常", e);
			return "error";
		}

	}

	// 出库单审核
	@RequestMapping(value = "/updateOutBoundInfo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateOutBoundInfo(Outbound outbound) {
		try {
			log.info("出库单审核");
			int result = outboundService.updateOutBoundInfo(outbound);
			if (result > 0) {
				return "success";
			} else {
				return "fail";
			}
		} catch (Exception e) {
			log.error("出库单审核", e);
			return "error";
		}
	}

	// 出库单添加
	@RequestMapping(value = "/addOutBoundOrder", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addOutBoundOrder(Outbound outBound) {
		try {
			log.info("出库单添加");
			int result = outboundService.addOutBoundOrder(outBound);
			if (result == 1) {
				return "success";
			} else {
				return "fail";
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("出库单添加", e);
			return "error";
		}
	}

}
