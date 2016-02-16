package com.yrdce.ipo.modules.sys.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * ipo 持仓减持设置
 * 
 * @author wq 2016-2-16
 * 
 */
public class IpoPositionReduce implements Serializable {
	 
	private static final long serialVersionUID = 451322486746581577L;
	
	
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * ipo持仓单号
	 */
	private Long positionFlowId;
	/**
	 * 减持比例
	 */
	private BigDecimal ratio;
	/**
	 * 减持数量
	 */
	private Long reduceqty;
	/**
	 * 减持日期
	 */
	private Date reduceDate;
	/**
	 * 创建人
	 */
	private String createUser;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 修改人
	 */
	private String updateUser;
	/**
	 * 修改时间
	 */
	private Date updateDate;
	/**
	 * 状态 1:未减持 2:已减持
	 */
	private int state;
	/**
	 * 删除标记 0:有效 1:无效
	 */
	private int deleteFlag;

	public IpoPositionReduce() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPositionFlowId() {
		return positionFlowId;
	}

	public void setPositionFlowId(Long positionFlowId) {
		this.positionFlowId = positionFlowId;
	}

	public BigDecimal getRatio() {
		return ratio;
	}

	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}

	public Long getReduceqty() {
		return reduceqty;
	}

	public void setReduceqty(Long reduceqty) {
		this.reduceqty = reduceqty;
	}

	public Date getReduceDate() {
		return reduceDate;
	}

	public void setReduceDate(Date reduceDate) {
		this.reduceDate = reduceDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	
	
	
}
