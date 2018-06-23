package com.xhgx.web.admin.dept.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.xhgx.sdk.entity.AutoUUID;
import com.xhgx.sdk.entity.BaseEntity;

/**
 * @ClassName DeptEntity
 * @Description 部门表实体对象
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@AutoUUID
public class DeptEntity extends BaseEntity<String> {

	private static final long serialVersionUID = 1L;

	private String id;
    /**企业ID*/
	private String compId;  

	/**父级ID*/
	@NotNull
	private String parentId;  
	/**部门名称*/
	@NotNull
	@Length(min = 1, max = 128, message = "部门名称长度必须在1-128之间")
	private String deptName; 
	/**部门编号，00010001格式，同级最多支持9999*/
	private String deptCode; 
	/**排序*/
	private int sort; 
	/**部门级别*/
	private int levelnum; 
	/**负责人姓名*/
	private String personName;  
	/**负责人联系方式*/
	private String personMobile;  
	/**部门电话*/
	private String deptPhone; 
	/**部门地址*/
	private String deptAddr; 
	/**创建者*/
	private String createBy; 
	/**创建时间*/
	private Date createDate;  
	/**存放公司名称，（临时字段）*/
	private String compName;
	/**临时字段，用于部门排序中的拖拽*/
	private String targetId;
	/**临时字段，用于部门排序中的拖拽*/
	private String moveType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompId() {
		return compId;
	}

	public void setCompId(String compId) {
		this.compId = compId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public int getLevelnum() {
		return levelnum;
	}

	public void setLevelnum(int levelnum) {
		this.levelnum = levelnum;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getDeptPhone() {
		return deptPhone;
	}

	public void setDeptPhone(String deptPhone) {
		this.deptPhone = deptPhone;
	}

	public String getDeptAddr() {
		return deptAddr;
	}

	public void setDeptAddr(String deptAddr) {
		this.deptAddr = deptAddr;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonMobile() {
		return personMobile;
	}

	public void setPersonMobile(String personMobile) {
		this.personMobile = personMobile;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getMoveType() {
		return moveType;
	}

	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}

}
