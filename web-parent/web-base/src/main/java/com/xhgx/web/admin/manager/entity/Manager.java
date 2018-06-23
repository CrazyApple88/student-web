package com.xhgx.web.admin.manager.entity;
import com.xhgx.sdk.entity.AutoUUID;
import com.xhgx.sdk.entity.BaseEntity;


 /**
 * @ClassName: Manager
 * @Description:  tb_sys_manager实体信息
 * @author: <font color='blue'>swx</font> 
 * @date:  2018-05-17 13:51:43
 * @version: 1.0
 * 
 */
@AutoUUID
public class Manager extends BaseEntity<String>{
	
	/** serialVersionUID */
	
	private static final long serialVersionUID = 4841799362659062357L;
	
	private java.lang.String id;//id
	private java.lang.String sysName;//系统名称
	private java.lang.String copyright;//版权
	private java.lang.String recordLicense;//备案许可证
	private java.lang.String logo;//系统LOGO
	private java.lang.String compId;//企业ID
	private java.lang.String wechatCode;//微信二维码
	private java.util.Date createDt;//创建时间
	private java.lang.Integer state;//是否启用0未启用1启用

	

	public void setId(java.lang.String id) {
		this.id = id;
	}
	
	/**
	 * <b>方法名</b>：getId<br>
	 * <b>功能</b>：取得id<br>
	 * 
	 * @author <font color='blue'>swx</font> 
	 * @date  2018-05-17 13:51:43
	 * @return
	 */
	public java.lang.String getId() {
		return this.id;
	}
	
	
	
	public java.lang.String getSysName() {
		return this.sysName;
	}
	
	public void setSysName(java.lang.String sysName) {
		this.sysName = sysName;
	}
	
	
	public java.lang.String getCopyright() {
		return this.copyright;
	}
	
	public void setCopyright(java.lang.String copyright) {
		this.copyright = copyright;
	}
	
	
	public java.lang.String getRecordLicense() {
		return this.recordLicense;
	}
	
	public void setRecordLicense(java.lang.String recordLicense) {
		this.recordLicense = recordLicense;
	}
	
	
	public java.lang.String getLogo() {
		return this.logo;
	}
	
	public void setLogo(java.lang.String logo) {
		this.logo = logo;
	}
	
	
	public java.lang.String getCompId() {
		return this.compId;
	}
	
	public void setCompId(java.lang.String compId) {
		this.compId = compId;
	}
	
	
	public java.lang.String getWechatCode() {
		return this.wechatCode;
	}
	
	public void setWechatCode(java.lang.String wechatCode) {
		this.wechatCode = wechatCode;
	}
	
	
	public java.util.Date getCreateDt() {
		return this.createDt;
	}
	
	public void setCreateDt(java.util.Date createDt) {
		this.createDt = createDt;
	}
	
	public java.lang.Integer getState() {
		return state;
	}
	
	public void setState(java.lang.Integer state) {
		this.state = state;
	}
	
	
}


