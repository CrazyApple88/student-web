package com.xhgx.web.admin.dataRecover.service;

import com.xhgx.sdk.service.BaseService;
import com.xhgx.sdk.service.CacheService;
import com.xhgx.web.admin.dataRecover.entity.DataRecover;

/**
 * @ClassName DataRecoverService
 * @Description tb_sys_data_recover 业务逻辑层接口
 * @author <font color='blue'>libo</font>
 * @date 2017-06-23 17:24:14
 * @version 1.0
 */
public interface DataRecoverService extends BaseService, CacheService {

	/**
	 * 通过id快速加载对象
	 * 
	 * @param dataRecoverId
	 * @return DataRecover
	 */
	public DataRecover getDataRecover(String dataRecoverId);

	/**
	 * 删除数据时调用方法(采用序列化保存信息)
	 * 
	 * @param obj
	 *            接口传过来的信息
	 * @param name
	 *            接口传过来的名称，方便查询区分
	 * @param loginUserName
	 *            操作的用户名称
	 * @return String
	 */
	public String delToRecoverDB(Object obj, String name, String loginUserName);

	/**
	 * 根据entity的id查找需要恢复的数据列表
	 * 
	 * @param code
	 *            对象ID（由之前的接口返回得到）
	 * @return Object 返回的数据
	 */
	public Object recoverByIdDB(String code);

	/**
	 * 保存在文件中 删除数据时调用方法(采用序列化保存信息)
	 * 
	 * @param obj
	 *            接口传过来的信息
	 * @param codeType
	 *            类型标识，某一类数据
	 * @param name
	 *            接口传过来的名称，方便查询区分
	 * @param loginUserName
	 *            操作的用户名称
	 * 
	 * @return String 保存的唯一文件名称
	 */
	public String delToRecoverFile(Object obj, String codeType, String name,
			String loginUserName);

	/**
	 * 保存在文件中
	 * 
	 * @param code
	 *            唯一文件名称（由之前的接口返回得到）
	 * @param codeType
	 *            类型标识，某一类数据
	 * @return Object 返回的数据
	 */
	public Object recoverByIdFile(String code, String codeType);

	/**
	 * 数据恢复后需要回调的方法，标识数据已经恢复完成
	 * 
	 * @param code
	 * @return boolean
	 */
	public boolean updateRecoverStatus(String code);

}