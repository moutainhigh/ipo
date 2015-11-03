package com.yrdce.ipo.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yrdce.ipo.common.dao.MyBatisDao;
import com.yrdce.ipo.modules.sys.entity.IpoDistribution;
import com.yrdce.ipo.modules.sys.entity.IpoDistributionExample;

@MyBatisDao
public interface IpoDistributionMapper {
	int countByExample(IpoDistributionExample example);

	int deleteByExample(IpoDistributionExample example);

	int deleteByPrimaryKey(int id);

	int insert(IpoDistribution record);

	int insertSelective(IpoDistribution record);

	List<IpoDistribution> selectByExampleWithBLOBs(IpoDistributionExample example);

	List<IpoDistribution> selectByExample(IpoDistributionExample example);

	List<IpoDistribution> getAll(@Param("beginnum") int beginnum, @Param("endnum") int endnum);// 分页获取配号信息

	IpoDistribution selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") IpoDistribution record, @Param("example") IpoDistributionExample example);

	int updateByExampleWithBLOBs(@Param("record") IpoDistribution record, @Param("example") IpoDistributionExample example);

	int updateByExample(@Param("record") IpoDistribution record, @Param("example") IpoDistributionExample example);

	int updateByPrimaryKeySelective(IpoDistribution record);

	int updateByPrimaryKeyWithBLOBs(IpoDistribution record);

	int updateByPrimaryKey(IpoDistribution record);

	void updateBycommodityid(String sid);
}