package com.xhgx.web.admin.menu.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.xhgx.sdk.entity.AutoUUID;
import com.xhgx.sdk.entity.BaseEntity;

/**
 * @ClassName MenuEntity
 * @Description 菜单权限表实体对象
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@AutoUUID
public class MenuEntity extends BaseEntity<String> {

	private static final long serialVersionUID = 1L;
	private String id;
	/**2父级ID*/
	private String parentId;
	/**3菜单名称*/
	@Length(min = 1, max = 32, message = "菜单名称长度必须在1-64之间")
	private String menuName;
	/**4菜单编号*/
	@Length(min = 0, max = 32, message = "菜单编号长度不能超过32")
	private String menuCode;
	/**5菜单类型(顶级菜单，二级菜单)*/
	private String menuType;
	/**6权限标识唯一编码*/
	@Length(min = 0, max = 64, message = "权限标识长度不能超过64")
	private String authTab;
	/**7菜单等级，如果等于99则为权限按钮*/
	private int levelnum;
	/**8排序*/
	@Range(min = 0, max = 1000)
	private int sort;
	/**9链接*/
	@Length(min = 0, max = 255, message = "链接长度不能超过255")
	private String url;
	/**10图标相对路径*/
	@Length(min = 0, max = 128, message = "图标名称长度不能超过128")
	private String icon;
	/**11菜单描述*/
	@Length(min = 0, max = 1000, message = "菜单描述长度不能超过1000")
	private String intro;
	/**12是否可用：1可用，2不可用*/
	private int useable;
	/**13此功能是否有手机版 1是 2不是*/
	private int isMobile;
	/**14手机版的访问页面地址*/
	@Length(min = 0, max = 255, message = "手机版访问地址长度不能超过128")
	private String mobileUrl;
	/**15创建者*/
	private String createBy;
	/**16创建时间*/
	private Date createDate;
	/**语言编码，用于设定使用哪种语言*/
	private String languageCode;
	/**临时字段，用于菜单排序中的拖拽*/
	private String targetId;
	/**临时字段，用于菜单排序中的拖拽*/
	private String moveType;
	/**临时字段，菜单的类型*/
	private String type;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public int getSort() {
		return sort;
	}

	public int getLevelnum() {
		return levelnum;
	}

	public void setLevelnum(int levelnum) {
		this.levelnum = levelnum;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getUseable() {
		return useable;
	}

	public void setUseable(int useable) {
		this.useable = useable;
	}

	public int getIsMobile() {
		return isMobile;
	}

	public void setIsMobile(int isMobile) {
		this.isMobile = isMobile;
	}

	public String getMobileUrl() {
		return mobileUrl;
	}

	public void setMobileUrl(String mobileUrl) {
		this.mobileUrl = mobileUrl;
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

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getAuthTab() {
		return authTab;
	}

	public void setAuthTab(String authTab) {
		this.authTab = authTab;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
