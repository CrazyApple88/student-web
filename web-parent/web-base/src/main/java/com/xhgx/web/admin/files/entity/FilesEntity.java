package com.xhgx.web.admin.files.entity;

import com.xhgx.sdk.entity.AutoUUID;
import com.xhgx.sdk.entity.BaseEntity;

/**
 * @ClassName Files
 * @Description 文件资源实体信息
 * @author <font color='blue'>libo</font>
 * @date 2017-05-18 18:27:13
 * @version 1.0
 */
@AutoUUID
public class FilesEntity extends BaseEntity<String> {

	/** serialVersionUID */

	private static final long serialVersionUID = 4841799362659062357L;
    
	/**id*/
	private java.lang.String id;
	/**文件名称*/
	private java.lang.String fileName; 
	/**文件类型*/
	private java.lang.String fileType;
	/**文件目录*/
	private java.lang.String fileDir;
	/**文件大小*/
	private java.lang.String fileSize;
	/**文件后缀名称*/
	private java.lang.String suffixName; 
	/**存放名称*/
	private java.lang.String serviceName;
	/**业务关联ID*/
	private java.lang.String relationId;
	/**上传者*/
	private java.lang.String createBy;
	/**上传时间*/
	private java.util.Date createDate; 

	public void setId(java.lang.String id) {
		this.id = id;
	}

	/**
	 * <b>方法名</b>：getId<br>
	 * <b>功能</b>：取得id<br>
	 * 
	 * @author <font color='blue'>libo</font>
	 * @date 2017-05-18 18:27:13
	 * @return
	 */
	public java.lang.String getId() {
		return this.id;
	}

	public java.lang.String getFileName() {
		return this.fileName;
	}

	public void setFileName(java.lang.String fileName) {
		this.fileName = fileName;
	}

	public java.lang.String getFileType() {
		return this.fileType;
	}

	public void setFileType(java.lang.String fileType) {
		this.fileType = fileType;
	}

	public java.lang.String getFileDir() {
		return this.fileDir;
	}

	public void setFileDir(java.lang.String fileDir) {
		this.fileDir = fileDir;
	}

	public java.lang.String getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(java.lang.String fileSize) {
		this.fileSize = fileSize;
	}

	public java.lang.String getSuffixName() {
		return this.suffixName;
	}

	public void setSuffixName(java.lang.String suffixName) {
		this.suffixName = suffixName;
	}

	public java.lang.String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(java.lang.String serviceName) {
		this.serviceName = serviceName;
	}

	public java.lang.String getRelationId() {
		return this.relationId;
	}

	public void setRelationId(java.lang.String relationId) {
		this.relationId = relationId;
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

}
