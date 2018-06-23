package com.xhgx.web.admin.dataRecover.entity;

import com.xhgx.sdk.entity.AutoUUID;
import com.xhgx.sdk.entity.BaseEntity;

/**
 * @ClassName DataRecover
 * @Description tb_sys_data_recover实体信息
 * @author <font color='blue'>libo</font>
 * @date 2017-06-23 17:24:14
 * @version 1.0
 */
@AutoUUID
public class DataRecover extends BaseEntity<String> {

	/** serialVersionUID */

	private static final long serialVersionUID = 4841799362659062357L;
    
	/**id*/
	private java.lang.String id; 
	/**恢复的code*/
	private java.lang.String recoverCode;
	/**对象名称*/
	private java.lang.String tableName;
	/**状态*/
	private java.lang.String status;
	/**删除的信息*/
	private byte[] data;
	/**删除人*/
	private java.lang.String createBy; 
	/**删除时间*/
	private java.util.Date createDate; 

	public void setId(java.lang.String id) {
		this.id = id;
	}

	/**
	 * <b>方法名</b>：getId<br>
	 * <b>功能</b>：取得id<br>
	 * 
	 * @author <font color='blue'>libo</font>
	 * @date 2017-06-23 17:24:14
	 * @return
	 */
	public java.lang.String getId() {
		return this.id;
	}

	public java.lang.String getRecoverCode() {
		return this.recoverCode;
	}

	public void setRecoverCode(java.lang.String recoverCode) {
		this.recoverCode = recoverCode;
	}

	public java.lang.String getTableName() {
		return this.tableName;
	}

	public void setTableName(java.lang.String tableName) {
		this.tableName = tableName;
	}

	public java.lang.String getStatus() {
		return this.status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	public java.lang.String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(java.lang.String createBy) {
		this.createBy = createBy;
	}

	public java.util.Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}
