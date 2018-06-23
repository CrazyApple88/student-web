package com.xhgx.web.admin.userToken.service;

import com.xhgx.sdk.service.BaseService;
import com.xhgx.sdk.service.CacheService;
import com.xhgx.web.admin.userToken.entity.UserToken;

/**
 * @ClassName UserTokenService
 * @Description tb_sys_user_token 业务逻辑层接口
 * @author <font color='blue'>libo</font>
 * @date 2017-06-26 18:06:06
 * @version 1.0
 */
public interface UserTokenService extends BaseService, CacheService {

	/**
	 * 通过id快速加载对象
	 * 
	 * @param userTokenId
	 * @return UserToken
	 */
	public UserToken getUserToken(String userTokenId);

	public String getUserId(String token);

}