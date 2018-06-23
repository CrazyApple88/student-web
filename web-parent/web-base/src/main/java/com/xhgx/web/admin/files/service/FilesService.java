package com.xhgx.web.admin.files.service;

import java.util.List;

import com.xhgx.sdk.service.BaseService;
import com.xhgx.web.admin.files.entity.FilesEntity;

/**
 * @ClassName FilesService
 * @Description 文件资源 业务逻辑层接口
 * @author <font color='blue'>libo</font>
 * @date 2017-05-18 18:27:13
 * @version 1.0
 */
public interface FilesService extends BaseService {

	/**
	 * 通过关联id查找对应数据
	 * 
	 * @param relationId
	 * @return List
	 */
	List<FilesEntity> getFileByRelationId(String relationId);

}