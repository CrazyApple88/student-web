package com.xhgx.web.admin.dicttype.entity;

import com.xhgx.sdk.entity.AutoUUID;
import com.xhgx.sdk.entity.BaseEntity;

/**
 * @ClassName DictTypeCompEntity
 * @Description 用户角色中间表实体对象
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@AutoUUID
public class DictTypeCompEntity extends BaseEntity<String> {

	private static final long serialVersionUID = 1L;
	private String id;
	/**字典类型ID*/
	private String dicttypeId;  
	/**企业ID*/
	private String compId;  

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDicttypeId() {
		return dicttypeId;
	}

	public void setDicttypeId(String dicttypeId) {
		this.dicttypeId = dicttypeId;
	}

	public String getCompId() {
		return compId;
	}

	public void setCompId(String compId) {
		this.compId = compId;
	}

}
