package com.xhgx.web.admin.company.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.xhgx.sdk.entity.AutoUUID;
import com.xhgx.sdk.entity.BaseEntity;

/**
 * @ClassName CompanyEntity
 * @Description  公司/企业实体对象
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@AutoUUID
public class CompanyEntity extends BaseEntity<String> {

	private static final long serialVersionUID = 1L;

	private String id;
    /**企业/公司名称*/
	@NotNull
	private String compName; 
	/**企业编号*/
	private String compCode; 
	/**企业简称*/
	private String nameAlias;  
	/**1 实名认证 2未认证*/
	@Range(min = 1, max = 2)
	private int isReal; 
	/**实名认证的时间*/
	private Date realDt;  
	/**负责人姓名*/
	private String personName;  
	/**负责人联系方式*/
	private String personMobile;  
	/**经度*/
	private String longitude; 
	/**纬度*/
	private String latitude; 
	/**企业电话*/
	private String compPhone; 
	/**企业地址*/
	private String compAddr; 
	/**是否可用：1可用，2不可用*/
	@NotNull
	@Range(min = 1, max = 2)
	private int useable; 
	/**创建者*/
	private String createBy;  
	/**创建时间*/
	private Date createDate;  
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getCompCode() {
		return compCode;
	}

	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}

	public String getNameAlias() {
		return nameAlias;
	}

	public void setNameAlias(String nameAlias) {
		this.nameAlias = nameAlias;
	}

	public int getIsReal() {
		return isReal;
	}

	public void setIsReal(int isReal) {
		this.isReal = isReal;
	}

	public Date getRealDt() {
		return realDt;
	}

	public void setRealDt(Date realDt) {
		this.realDt = realDt;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getCompPhone() {
		return compPhone;
	}

	public void setCompPhone(String compPhone) {
		this.compPhone = compPhone;
	}

	public String getCompAddr() {
		return compAddr;
	}

	public void setCompAddr(String compAddr) {
		this.compAddr = compAddr;
	}

	public int getUseable() {
		return useable;
	}

	public void setUseable(int useable) {
		this.useable = useable;
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

}
