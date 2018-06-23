package com.xhgx.web.admin.userToken.dao;

import com.xhgx.sdk.dao.GenericDao;

/**
 * @ClassName UserTokenDao
 * @Description tb_sys_user_token 持久层接口
 * @author <font color='blue'>libo</font>
 * @date 2017-06-26 18:06:06
 * @version 1.0
 */
public interface UserTokenDao extends GenericDao {
	public String getUserId(String token);
}
