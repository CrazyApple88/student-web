package com.xhgx.web.admin.config.entity;

import java.util.Date;

import com.xhgx.sdk.entity.AutoUUID;
import com.xhgx.sdk.entity.BaseEntity;

/**
 * @ClassName ConfigEntity
 * @Description 配置表实体对象
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@AutoUUID
public class ConfigEntity extends BaseEntity<String> {

	private static final long serialVersionUID = 1L;
	private String id;
	/**名称*/
	private String name; 
	/**唯一编号*/
	private String code;  
	/**属性参数*/
	private String value; 
	/**备注信息*/
	private String intro; 
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
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

}
