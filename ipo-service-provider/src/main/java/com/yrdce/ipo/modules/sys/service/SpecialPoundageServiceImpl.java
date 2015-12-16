package com.yrdce.ipo.modules.sys.service;

import java.util.ArrayList;
import java.util.List;
 
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yrdce.ipo.modules.sys.dao.IpoSpecialPoundageMapper;
import com.yrdce.ipo.modules.sys.entity.IpoSpecialPoundage;
import com.yrdce.ipo.modules.sys.vo.SpecialPoundage;

@Service("specialPoundageService")
public class SpecialPoundageServiceImpl implements SpecialPoundageService {
	@Autowired
	private IpoSpecialPoundageMapper ipoSpecialPoundageMapper;
	public IpoSpecialPoundageMapper getSpecialPoundageMapper(){
		return ipoSpecialPoundageMapper;
	}
	
	public void setIpoSpecialPoundageMapper(IpoSpecialPoundageMapper ipoSpecialPoundageMapper){
		this.ipoSpecialPoundageMapper = ipoSpecialPoundageMapper;
	}
	@Override
	public List<SpecialPoundage> GetAllInfo() {
		// TODO Auto-generated method stub
			List<IpoSpecialPoundage> ipoSpecialPoundageList =   ipoSpecialPoundageMapper.selectAll();		
			List<SpecialPoundage> tempList = new ArrayList<SpecialPoundage>();
			for(IpoSpecialPoundage tempPoundage:ipoSpecialPoundageList){
				SpecialPoundage poundage = new SpecialPoundage();
				BeanUtils.copyProperties(tempPoundage, poundage);
				tempList.add(poundage);
			}
			return tempList;
	
	}
	
}