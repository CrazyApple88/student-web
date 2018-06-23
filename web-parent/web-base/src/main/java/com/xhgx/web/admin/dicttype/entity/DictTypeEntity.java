package com.xhgx.web.admin.dicttype.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.xhgx.sdk.entity.AutoUUID;
import com.xhgx.sdk.entity.BaseEntity;

/**
 * @ClassName DictTypeEntity
 * @Description 字典类型表实体对象
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@AutoUUID
public class DictTypeEntity extends BaseEntity<String> {

	private static final long serialVersionUID = 1L;

	private String id;
	/**父ID*/
	@NotNull
	private String parentId;
	/**字典类型名称*/
	@Length(min = 1, max = 32, message = "字典类型名称长度必须在1-32之间")
	private String typeName;
	/**编号*/
	@Length(min = 1, max = 32, message = "字典类型编号长度必须在1-32之间")
	private String typeCode;
	/**排序（升序）*/
	private int sort;
	/**级别*/
	@NotNull(message = "未找到字典类型的级别")
	private int levelnum;
	/**备注描述*/
	@Length(min = 0, max = 255, message = "字典类型描述长度不能超过255")
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

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public int getLevelnum() {
		return levelnum;
	}

	public void setLevelnum(int levelnum) {
		this.levelnum = levelnum;
	}

}
