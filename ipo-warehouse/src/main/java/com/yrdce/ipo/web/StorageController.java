package com.yrdce.ipo.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.json.JSON;
import com.yrdce.ipo.modules.sys.vo.ResponseResult;
import com.yrdce.ipo.modules.warehouse.service.IpoStorageService;
import com.yrdce.ipo.modules.warehouse.vo.StorageUnionVo;


/**
 * 入库申请Controller
 * 
 * @author 李伟东
 * @version 2015.12.20
 */
@Controller
@RequestMapping("StorageController")
public class StorageController {
	static org.slf4j.Logger log = org.slf4j.LoggerFactory
			.getLogger(StorageController.class);
	@Autowired
	private IpoStorageService ipoStorageService;
	
	
	//入库单添加视图
	@RequestMapping(value = "/AddStorageView", method = RequestMethod.GET)
	public String AddStorageView(HttpServletRequest request, HttpServletResponse response){
		log.info("入库单添加页");
		return "app/storage/addstorageaudit";
	}
	
	//入库单列表页视图
	@RequestMapping(value = "/ToStorageView", method = RequestMethod.GET)
	public String ToStorageView(HttpServletRequest request, HttpServletResponse response){
		log.info("入库单列表页");
		return "app/storage/storageApplication";
	}
	
	@RequestMapping(value = "/ListStorage")
	@ResponseBody
	public String ListStorage(@RequestParam("page") String page,@RequestParam("rows")  String rows, StorageUnionVo storageUnionVo){
		log.info("入库单列表页查询");
		if(storageUnionVo == null){
			storageUnionVo =  new StorageUnionVo();
		}
		page = (page == null ? "1" : page);
		rows = (rows == null ? "5" : rows);
		Integer curpage = Integer.parseInt(page);
		Integer pagesize = Integer.parseInt(rows);
		List<StorageUnionVo> unionVos = ipoStorageService.queryVos((curpage - 1) * pagesize + 1, curpage * pagesize, storageUnionVo);
		int totalnum = ipoStorageService.countStorage();
		ResponseResult result = new ResponseResult();
		result.setTotal(totalnum);
		result.setRows(unionVos);
		try {
			return JSON.json(result);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
