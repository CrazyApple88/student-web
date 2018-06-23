package com.xhgx.web.base.entity;

import com.xhgx.sdk.entity.BaseEntity;
import com.xhgx.web.admin.dept.entity.DeptEntity;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OnlineUser
 * @Description 在线用户(session)的信息
 * @author zohan(inlw@sina.com)
 * @date 2017-04-01 14:27
 * @vresion 1.0
 */
public abstract class OnlineUser extends BaseEntity <String>  {
    /**从seesion获取用户信息的标识，一般是业务系统实现 最好能够统一*/
    public static String ONLINE_USER_TAG = "_online_user";

    /**扩展字段信息*/
    private final Map<String, Object> ext = new LinkedHashMap();

    /**部门*/
    private List<DeptEntity> listDept;
    
    /**
     * 用户的登录名称
     *
     * @return
     */
    public  abstract String getLoginName();

    /**
     * 用户的展示名称（例如：姓名）
     *
     * @return
     */
    public abstract String getName();

    /**
     * 用户的主要手机号
     *
     * @return
     */
    public abstract String getMobile();

    /**
     * 添加扩展信息
     *
     * @param key
     * @param value
     * @return
     */
    public OnlineUser addExt(String key, Object value) {
        ext.put(key, value);
        return this;
    }

    /**
     * 获取扩展信息
     *
     * @param key
     * @return
     */
    public Object getExt(String key) {
        if (StringUtils.isNotBlank(key)) {
            return ext.get(key);
        }
        return null;
    }

    /**
     * 开放扩展信息
     *
     * @return
     */
    public Map getExt() {
        return ext;
    }

    /**
     * 扩展
     */
    public void extClear(){
        ext.clear();
    }

	public List<DeptEntity> getListDept() {
		return listDept;
	}

	public void setListDept(List<DeptEntity> listDept) {
		this.listDept = listDept;
	}


}
