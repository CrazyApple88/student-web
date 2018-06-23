package com.xhgx.web.admin.files.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jmimemagic.Magic;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.xhgx.commons.lang.DateUtils;
import com.xhgx.sdk.container.WebContextUtil;
import com.xhgx.sdk.id.UIDGenerator;
import com.xhgx.web.admin.config.service.ConfigHelper;
import com.xhgx.web.admin.files.entity.FilesEntity;
import com.xhgx.web.admin.files.service.FilesService;
import com.xhgx.web.base.controller.AbstractBaseController;
import com.xhgx.web.base.entity.OnlineUser;

/**
 * @ClassName FileApiController
 * @Description 文件资源管理对应的接口
 * @author zohan(inlw@sina.com)
 * @date 2017-08-24 9:59
 * @vresion 1.0
 */
@Controller("fileApiController")
@RequestMapping("/file/api")
@Scope("prototype")
public class FileApiController extends AbstractBaseController {

	private static final Logger log = LoggerFactory
			.getLogger(FileApiController.class);
	Magic parser = new Magic();

	/**Service对象，可以调用其中的业务逻辑方法 */
	@Autowired
	@Qualifier("filesService")
	private FilesService filesService;

	/**文件对应的存储路径*/
	/**图片存放路径*/
	private String savePath = ConfigHelper.getInstance().get("file.upload.basedir");
	/**格式化*/
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
	/**文件上传服务接口*/
	@RequestMapping("/upload/{entityId}")
	public void upload(@PathVariable String entityId,
			MultipartHttpServletRequest request) {
		OnlineUser loginUser = this.getCurrentUser();

		/**相对目录*/
		String ymd = sdf.format(new Date());

		/**文件存放的相对路径*/
		String fileRelativePath = ymd + "/";

		/**检查路径（绝对路径+相对路径）是否存在，不存在则创建文件夹*/
		File dirFile = new File(savePath + fileRelativePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		List<FilesEntity> listFile = new ArrayList<FilesEntity>();
		try {
			// 取得request中的所有文件名
			Iterator<String> iter = request.getFileNames();
			while (iter.hasNext()) {
				// 记录上传过程起始时的时间，用来计算上传时间
				int statTime = (int) System.currentTimeMillis();
				// 取得上传文件
				MultipartFile file = request.getFile(iter.next());
				if (file != null) {
					FilesEntity fileEntity = new FilesEntity();
					// 取得当前上传文件的文件名称
					String myFileName = file.getOriginalFilename();
					// 如果名称不为"",说明该文件存在，否则说明该文件不存在
					if (myFileName.trim() != "") {
						// 文件后缀名
						String prefix = myFileName.substring(myFileName
								.lastIndexOf(".") + 1);
						// 获取上传后的文件名
						String fileUUID = UIDGenerator.getInstance()
								.createUID();
						// 上传后存入服务器的文件名称+后缀
						String fileServiceName = fileUUID + "." + prefix;

						// 定义上传路径
						String path = savePath + fileRelativePath
								// 文件保存路径
								+ fileServiceName; 
						File localFile = new File(path);
						file.transferTo(localFile);
						// 文件名称
						fileEntity.setFileName(myFileName);
						// 文件类型
						fileEntity.setFileType("");
						// 文件存放路径
						fileEntity.setFileDir(fileRelativePath);
						// 文件大小
						fileEntity.setFileSize(file.getSize() + "");
						// 文件后缀名称
						fileEntity.setSuffixName(prefix);
						// 存放名称的文件名称
						fileEntity.setServiceName(fileServiceName);
						// 业务关联ID
						fileEntity.setRelationId(entityId);
						// 上传者
						fileEntity.setCreateBy(loginUser.getLoginName());
						// 上传时间
						fileEntity.setCreateDate(new Date());
						filesService.save(fileEntity);
						// 保存成功后放到list中返回到页面
						listFile.add(fileEntity);
					}
				}
				// 记录上传该文件后的时间
				int endTime = (int) System.currentTimeMillis();
				log.debug(getMessage("system.management.text.uploading.files.time-consuming","上传文件耗时：") + (endTime - statTime));

			}
		} catch (IOException e) {
			log.error(e.getMessage());
			errorJson(getMessage("system.management.text.upload.file.error.try.again","上传文件出错请重试！"));
			return;
		}
		List<Map<String, Object>> lists = markeFileToMaps(listFile);
		successJson(lists);
	}
	
	/**文件上传服务接口*/
	@RequestMapping("/uploadFile/{entityId}")
	public void uploadFile(@PathVariable String entityId,
			MultipartHttpServletRequest request) {
		OnlineUser loginUser = this.getCurrentUser();
		
		// 相对目录
		String ymd = sdf.format(new Date());
		
		// 文件存放的相对路径
		String fileRelativePath = ymd + "/";
		
		// 检查路径（绝对路径+相对路径）是否存在，不存在则创建文件夹
		File dirFile = new File(savePath + fileRelativePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		List<FilesEntity> listFile = new ArrayList<FilesEntity>();
		try {
			// 取得request中的所有文件名
			Iterator<String> iter = request.getFileNames();
			while (iter.hasNext()) {
				// 记录上传过程起始时的时间，用来计算上传时间
				int statTime = (int) System.currentTimeMillis();
				// 取得上传文件
				MultipartFile file = request.getFile(iter.next());
				if (file != null) {
					FilesEntity fileEntity = new FilesEntity();
					// 取得当前上传文件的文件名称
					String myFileName = file.getOriginalFilename();
					// 如果名称不为"",说明该文件存在，否则说明该文件不存在
					if (myFileName.trim() != "") {
						// 文件后缀名
						String prefix = myFileName.substring(myFileName
								.lastIndexOf(".") + 1);
						// 获取上传后的文件名
						String fileUUID = UIDGenerator.getInstance()
								.createUID();
						// 上传后存入服务器的文件名称+后缀
						String fileServiceName = fileUUID + "." + prefix;
						
						// 定义上传路径
						String path = savePath + fileRelativePath
								// 文件保存路径
								+ fileServiceName; 
						File localFile = new File(path);
						file.transferTo(localFile);
						// 文件名称
						fileEntity.setFileName(myFileName);
						// 文件类型
						fileEntity.setFileType("");
						// 文件存放路径
						fileEntity.setFileDir(fileRelativePath);
						// 文件大小
						fileEntity.setFileSize(file.getSize() + "");
						// 文件后缀名称
						fileEntity.setSuffixName(prefix);
						// 存放名称的文件名称
						fileEntity.setServiceName(fileServiceName);
						if(!"-999".equals(entityId)){
							// 业务关联ID
							fileEntity.setRelationId(entityId);
						}
						// 上传者
						fileEntity.setCreateBy(loginUser.getLoginName());
						// 上传时间
						fileEntity.setCreateDate(new Date());
						filesService.save(fileEntity);
						// 保存成功后放到list中返回到页面
						listFile.add(fileEntity);
					}
				}
				// 记录上传该文件后的时间
				int endTime = (int) System.currentTimeMillis();
				log.debug(getMessage("system.management.text.uploading.files.time-consuming","上传文件耗时：") + (endTime - statTime));
				
			}
		} catch (IOException e) {
			log.error(e.getMessage());
			errorJson(getMessage("system.management.text.upload.file.error.try.again","上传文件出错请重试！"));
			return;
		}
		List<Map<String, Object>> lists = markeFileToMaps(listFile);
		successJson(lists);
	}

	/**
	 * 把文件转换成map对象
	 *
	 * @param listFile
	 * @return List
	 */
	private List<Map<String, Object>> markeFileToMaps(List<FilesEntity> listFile) {
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		for (FilesEntity entity : listFile) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", entity.getFileName());
			map.put("size", entity.getFileSize());
			map.put("suffix", entity.getSuffixName());
			map.put("id", entity.getId());
			map.put("date", DateUtils.formateDate(entity.getCreateDate(),
					DateUtils.DEFAULT_DATE_TIME_FORMAT));
//			map.put("path", WebContextUtil.getContextPath() + "/file/api/download/"
//							+ entity.getId() + "." + entity.getSuffixName());
			map.put("path", WebContextUtil.getContextPath() + "/file/api/download/"
					+ entity.getId());
			lists.add(map);
		}
		return lists;
	}

	/**
	 * h获取文件的列表
	 *
	 * @param entityId
	 */
	@RequestMapping("/list/{entityId}")
	public void list(@PathVariable String entityId) {
		if (StringUtils.isBlank(entityId)) {
			errorJson(getMessage("system.management.text.parameter.error","参数错误！"));
			return;
		}
		List<FilesEntity> listFile = filesService.getFileByRelationId(entityId);
		List<Map<String, Object>> lists = markeFileToMaps(listFile);
		successJson(lists);
	}
	

	/**
	 * 删除对象
	 *
	 * @param id
	 */
	@RequestMapping("/del/{id}")
	public void del(@PathVariable String id) {
		FilesEntity entity = new FilesEntity();
		entity.setId(id);
		if (StringUtils.isBlank(id)) {
			errorJson(getMessage("system.management.text.parameter.error","参数错误！"));
			return;
		}

		entity = (FilesEntity) filesService.get(entity);
		File file = new File(savePath + entity.getFileDir()
				+ entity.getServiceName());
		if (!file.exists()) {
			// System.out.println("删除文件失败:" + entity.getServiceName() + "不存在！");
			log.warn(getMessage("system.management.text.failed.delete.file","删除文件失败:") + entity.getServiceName() + getMessage("system.management.text.not.exist","不存在！"));
		} else {
			if (file.isFile()){
				file.delete();
			}
		}
		filesService.delete(entity);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(entity);

	}
	
	@RequestMapping("/delNo/{id}")
	public void delNo(@PathVariable String id) {
		successJson("");
	}

	/**
	 * 下载单个文件
	 *
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/download/{id}")
	public void download(@PathVariable String id, HttpServletRequest request,
			HttpServletResponse response) {
		FilesEntity entity = new FilesEntity();
		entity.setId(id);
		entity = (FilesEntity) filesService.get(entity);
		if (entity == null) {
			renderHtml(getMessage("system.management.text.file.not.exist","该文件不存在！"));
			return;
		}
		File dirFile = new File(savePath + entity.getFileDir()
				+ entity.getServiceName());
		if (!dirFile.exists()) {
			renderHtml(getMessage("system.management.text.file.not.exist","该文件不存在！"));
			return;
		}
		// 设置request 类型
		String attachmentName = entity.getFileName();

		// 获取文件服务器路径
		ServletContext context = request.getServletContext();
		// 设置文件MIME类型
		response.setContentType(context.getMimeType(attachmentName));
		// 设置Content-Disposition
		try {
			String content = parser.getMagicMatch(dirFile, false).getMimeType();
			response.setContentType("application/" + content);

		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		response.addHeader("Content-Length", "" + entity.getFileSize());
		try {
			// 防止中文乱码
			attachmentName = new String(attachmentName.getBytes(), "iso-8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ attachmentName + "\"");
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		try {
			IOUtils.copy(new FileInputStream(dirFile),
					response.getOutputStream());
		} catch (IOException e) {
			renderHtml(getMessage("system.management.text.failed.read.file","读取文件失败:") + e.getMessage());
			return;
		}
	}

}
