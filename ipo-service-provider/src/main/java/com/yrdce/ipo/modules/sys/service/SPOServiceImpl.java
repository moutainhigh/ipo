package com.yrdce.ipo.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yrdce.ipo.modules.sys.dao.IpoSpoMapper;
import com.yrdce.ipo.modules.sys.entity.IpoSpo;
import com.yrdce.ipo.modules.sys.vo.Spo;

 /** 增发
 * li
 * 2015.12.22
 * */

@Service("spoService")
public class SPOServiceImpl implements SPOService {
	@Autowired
	private IpoSpoMapper ipoSpoMapper;
	@Override
	public List<Spo> getInfoByPages(Spo spo, String page, String rows) {
		// TODO Auto-generated method stub
	
		if(spo==null)
			return null;
		page = (page==null?"1":page);
		rows = (rows==null?"5":rows);
		int beginNum = (Integer.parseInt(page)-1)*Integer.parseInt(rows)+1;
		int endNum = Integer.parseInt(page)*Integer.parseInt(rows);
		IpoSpo ipoSpo = new IpoSpo();
		BeanUtils.copyProperties(spo,ipoSpo);
	 	List<IpoSpo> ipoSpos = ipoSpoMapper.getInfoByPages(ipoSpo, beginNum, endNum);
	 	List<Spo> spos = new ArrayList<Spo>();
	 	for (IpoSpo ipoSpo2 : ipoSpos) {
	 		Spo tempSpo = new Spo();
			BeanUtils.copyProperties(ipoSpo2, tempSpo);
			spos.add(tempSpo);
		}
		return spos;
	}
	@Override
	public int getInfoCounts(Spo spo) {
		// TODO Auto-generated method stub
		if (spo == null) {
			return 0;
		}
		IpoSpo ipoSpo = new IpoSpo();
		BeanUtils.copyProperties(spo, ipoSpo);
		int counts=ipoSpoMapper.getInfoCounts(ipoSpo);
		return counts;
	}
	@Override
	public int updateRationType(String rationType) {
		// TODO Auto-generated method stub
		int result = ipoSpoMapper.updateRationType(rationType);
		if (result>0) {
			return 1;
		}else{
			return 0;
		}
	}
}