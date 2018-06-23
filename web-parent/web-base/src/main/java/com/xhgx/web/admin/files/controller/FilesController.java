package com.xhgx.web.admin.files.controller;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.xhgx.sdk.entity.Page;
import com.xhgx.sdk.id.UIDGenerator;
import com.xhgx.web.admin.config.service.ConfigHelper;
import com.xhgx.web.admin.files.entity.FilesEntity;
import com.xhgx.web.admin.files.service.FilesService;
import com.xhgx.web.base.controller.AbstractBaseController;
import com.xhgx.web.base.controller.RequestSearch;
import com.xhgx.web.base.entity.OnlineUser;

/**
 * @ClassName FilesController
 * @Description 文件资源 控制层
 * @author <font color='blue'>libo</font>
 * @date 2017-05-18 18:27:13
 * @version 1.0
 */
@Controller("filesController")
@RequestMapping("/admin/files")
@Scope("prototype")
public class FilesController extends AbstractBaseController {

	private String dirTemp = "upload/widget/temp";

	/**上传文件的保存路径*/
	private String configPath = "upload/widget";
	/**图片存放路径*/
	private String savePath = ConfigHelper.getInstance().get("file.upload.basedir");
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(Files.class);

	/**Service对象，可以调用其中的业务逻辑方法 */
	@Autowired
	@Qualifier("filesService")
	private FilesService filesService;

	/**
	 * 统一页面路径
	 * 
	 * @return String
	 */
	public String viewPrefix() {
		return "admin/files/files_";
	}

	/**
	 * 进入分页查询页面
	 * 
	 * @return String
	 */
	@RequestMapping("/queryPage")
	public String queryPage() {
		return this.viewPrefix() + "query";
	}

	/**
	 * 分页查询功能
	 * 
	 * @param page
	 * @param orderBy
	 */
	@RequestMapping("/findPage")
	public void findPage(Page page, String orderBy) {
		Map<String, Object> param = RequestSearch.getSearch(request);
		if (StringUtils.isBlank(orderBy)) {
			orderBy = " create_date desc";
		}
		filesService.findPage(param, page, orderBy);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(page);
	}

	/**
	 * 信息保存
	 * 
	 * @param entity
	 * @param bindingResult
	 */
	@RequestMapping("/save")
	public void save(@Valid FilesEntity entity, BindingResult bindingResult) {

		// 这里是验证，要依赖实体类的 注解
		if (!validate(bindingResult)){
			return;
		}
		filesService.save(entity);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(entity);
	}

	/**
	 * 进入新增/编辑页面
	 * 
	 * @param entity
	 * @return String
	 */
	@RequestMapping("/editPage")
	public String editPage(FilesEntity entity) {
		if (!StringUtils.isBlank(entity.getId())) {
			entity = (FilesEntity) filesService.get(entity);
		}
		request.setAttribute("entity", entity);
		return this.viewPrefix() + "edit";
	}

	/**
	 * 修改用户信息
	 * 
	 * @param entity
	 * @param bindingResult
	 */
	@RequestMapping("/update")
	public void update(@Valid FilesEntity entity, BindingResult bindingResult) {
		// 这里是手动验证，错误信息用 errorJson 返回，这里是ajax请求
		if (!validate(bindingResult)){
			return;
		}
		filesService.update(entity);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(entity);
	}

	/**
	 * 删除信息操作
	 * 
	 * @param entity
	 */
	@RequestMapping("/delete")
	public void delete(FilesEntity entity) {
		entity = (FilesEntity) filesService.get(entity);
		File file = new File(savePath + entity.getFileDir()
				+ entity.getServiceName());
		if (!file.exists()) {
			System.out.println(getMessage("system.management.text.failed.delete.file","删除文件失败:") + entity.getServiceName() + getMessage("system.management.text.not.exist","不存在！"));
		} else {
			if (file.isFile()){
				file.delete();
			}
		}
		filesService.delete(entity);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(entity);
	}

	@RequestMapping("/getFileUploadPage")
	public String getFileUploadPage(Page page, BindingResult bindingResult) {
		return "admin/files/files_upload";
	}

	@RequestMapping("/uploadFile")
	public void uploadFile(FilesEntity entity) {
		try {
			List<FilesEntity> listFile = new ArrayList<FilesEntity>();
			OnlineUser loginUser = this.getCurrentUser();
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			// 相对目录
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			String ymd = sdf.format(new Date());

			// 文件存放的相对路径
			String fileRelativePath = ymd + "/";

			// 检查路径（绝对路径+相对路径）是否存在，不存在则创建文件夹
			File dirFile = new File(savePath + fileRelativePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 记录上传过程起始时的时间，用来计算上传时间
				int statTime = (int) System.currentTimeMillis();
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
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
						fileEntity.setRelationId("");
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
				System.out.println(endTime - statTime);

			}
			successJson(listFile);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		errorJson(getMessage("system.management.text.upload.failed","上传失败！"));
	}

	@RequestMapping("/getFileByte")
	public void getFileByte(FilesEntity entity) {
		ServletOutputStream outputStream = null;
		FileInputStream inputStream = null;
		entity = (FilesEntity) filesService.get(entity);
		try {
			if (entity != null && entity.getServiceName() != null) {
				File picFile = new File(savePath + entity.getFileDir()
						+ entity.getServiceName());
				if (!picFile.exists()) {
					String url = this.getClass().getClassLoader()
							.getResource("/").getPath();
					url = url.replace("WEB-INF/classes/", "");
					picFile = new File(url
							+ "assets/images/menu_img/menu_default.png");
					if (!picFile.exists()) {
						errorJson(getMessage("system.management.text.do.not.find.file","没有找到文件！"));
					} else {
						response.setContentType("image/jpeg; charset=GBK");
						// response.setHeader("Content-Disposition",
						// "attachment; filename="+new
						// String("temp.jpg".getBytes("GBK"),"ISO8859_1"));
						outputStream = response.getOutputStream();
						inputStream = new FileInputStream(picFile);
						byte[] buffer = new byte[1024];
						int i = -1;
						while ((i = inputStream.read(buffer)) != -1) {
							outputStream.write(buffer, 0, i);
						}
					}
				} else {
					response.setContentType("image/jpeg; charset=GBK");
					// response.setHeader("Content-Disposition",
					// "attachment; filename="+new
					// String("temp.jpg".getBytes("GBK"),"ISO8859_1"));
					outputStream = response.getOutputStream();
					inputStream = new FileInputStream(picFile);
					byte[] buffer = new byte[1024];
					int i = -1;
					while ((i = inputStream.read(buffer)) != -1) {
						outputStream.write(buffer, 0, i);
					}
				}
			} else {
				// System.out.println(this.getClass().getClassLoader().getResource("/").getPath());
				String url = this.getClass().getClassLoader().getResource("/")
						.getPath();
				url = url.replace("WEB-INF/classes/", "");
				File picFile = new File(url
						+ "assets/images/menu_img/menu_default.png");
				if (!picFile.exists()) {
					errorJson(getMessage("system.management.text.do.not.find.file","没有找到文件！"));
				} else {
					response.setContentType("image/jpeg; charset=GBK");
					// response.setHeader("Content-Disposition",
					// "attachment; filename="+new
					// String("temp.jpg".getBytes("GBK"),"ISO8859_1"));
					outputStream = response.getOutputStream();
					inputStream = new FileInputStream(picFile);
					byte[] buffer = new byte[1024];
					int i = -1;
					while ((i = inputStream.read(buffer)) != -1) {
						outputStream.write(buffer, 0, i);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorJson(getMessage("system.management.text.search.file.failed","查询文件失败！"));
		} finally {
			if (outputStream != null) {
				try {
					outputStream.flush();
					outputStream.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	@RequestMapping("/getOneFileUploadPage")
	public String getOneFileUploadPage(FilesEntity entity) {
		return "admin/files/one_file_upload";
	}

}
