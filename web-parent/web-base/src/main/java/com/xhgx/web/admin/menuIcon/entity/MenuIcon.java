package com.xhgx.web.admin.menuIcon.entity;
import com.xhgx.sdk.entity.AutoUUID;
import com.xhgx.sdk.entity.BaseEntity;

 /**
 * @ClassName MenuIcon
 * @Description tb_sys_menu_icon实体信息
 * @author <font color='blue'>ryh</font> 
 * @date 2018-01-18 17:19:32
 * @version 1.0
 */
@AutoUUID
public class MenuIcon extends BaseEntity<String>{
	
	/** serialVersionUID */
	
	private static final long serialVersionUID = 4841799362659062357L;
	
	/**序号*/
	private java.lang.String id;
	/**名称*/
	private java.lang.String iconName;
	/**类型id*/
	private java.lang.String typeId;
	/**类名*/
	private java.lang.String className;
	/**创建人*/
	private java.lang.String createBy;
	/**创建时间*/
	private java.util.Date createTime;
	/**类型名称,临时字段*/
	private String typeName;
	

	public void setId(java.lang.String id) {
		this.id = id;
	}
	
	/**
	 * <b>方法名</b>：getId<br>
	 * <b>功能</b>：取得序号<br>
	 * 
	 * @author <font color='blue'>ryh</font> 
	 * @date  2018-01-18 17:19:32
	 * @return
	 */
	public java.lang.String getId() {
		return this.id;
	}
	
	
	
	public java.lang.String getIconName() {
		return this.iconName;
	}
	
	public void setIconName(java.lang.String iconName) {
		this.iconName = iconName;
	}
	
	
	public java.lang.String getClassName() {
		return this.className;
	}
	
	public void setClassName(java.lang.String className) {
		this.className = className;
	}
	
	
	public java.lang.String getCreateBy() {
		return this.createBy;
	}
	
	public void setCreateBy(java.lang.String createBy) {
		this.createBy = createBy;
	}
	
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.String getTypeId() {
		return typeId;
	}

	public void setTypeId(java.lang.String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}


