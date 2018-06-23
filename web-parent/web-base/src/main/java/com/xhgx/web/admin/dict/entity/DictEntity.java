package com.xhgx.web.admin.dict.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.xhgx.sdk.entity.AutoUUID;
import com.xhgx.sdk.entity.BaseEntity;

/**
 * @ClassName DictEntity
 * @Description 字典表实体对象
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@AutoUUID
public class DictEntity extends BaseEntity<String> {

	private static final long serialVersionUID = 1L;

	private String id;
	/**字典名称*/
	@Length(min = 1, max = 32, message = "字典名称长度必须在1-32之间")
	private String dictName;
	/**唯一编号001001格式，同级最多支持999*/
	@Length(min = 0, max = 32, message = "字典编号长度不能超过32")
	private String dictCode;
	/**类型ID*/
	@NotNull(message = "未找到字典类型ID")
	private String typeId;
	/**排序（升序）*/
	private int sort;
	/**备注描述*/
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

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
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
