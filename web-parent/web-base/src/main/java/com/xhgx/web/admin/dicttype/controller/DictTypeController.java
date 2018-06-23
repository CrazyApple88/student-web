package com.xhgx.web.admin.dicttype.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xhgx.sdk.entity.Page;
import com.xhgx.web.admin.company.entity.CompanyEntity;
import com.xhgx.web.admin.company.service.CompanyService;
import com.xhgx.web.admin.dicttype.entity.DictTypeCompEntity;
import com.xhgx.web.admin.dicttype.entity.DictTypeEntity;
import com.xhgx.web.admin.dicttype.service.DictTypeService;
import com.xhgx.web.admin.user.entity.UserEntity;
import com.xhgx.web.base.controller.AbstractBaseController;
import com.xhgx.web.base.controller.RequestSearch;
import com.xhgx.web.base.entity.OnlineUser;

/**
 * @ClassName DictTypeController
 * @Description 字典类型表Controller
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@RequestMapping("/admin/dicttype")
@Controller("dictTypeController")
@Scope("prototype")
public class DictTypeController extends AbstractBaseController {

	@Autowired
	public DictTypeService dictTypeService;

	@Autowired
	public CompanyService companyService;

	/**
	 * 分页查询
	 * 
	 * @param page
	 *            分页条件
	 * @param orderBy
	 *            排序条件
	 */
	@RequestMapping("/findPage")
	public void findPage(Page page, String orderBy) {
		Map<String, Object> param = RequestSearch.getSearch(request);
		if (StringUtils.isBlank(orderBy)) {
			orderBy = " create_date desc";
		}
		dictTypeService.findPage(param, page, orderBy);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(page);
	}

	/**
	 * 进入新增/编辑页面
	 * 
	 * @param dicttype
	 * @param parentName
	 * @param bindingResult
	 * @return String
	 */
	@RequestMapping("/getDicttypeEditPage")
	public String getDicttypeEditPage(DictTypeEntity dicttype,
			String parentName, BindingResult bindingResult) {
		String type = "edit";
		// 如果是修改页面
		if (!StringUtils.isBlank(dicttype.getId())) {
			dicttype = (DictTypeEntity) dictTypeService.get(dicttype);
			// 如果是新增页面
		} else {
			if (!StringUtils.isBlank(dicttype.getParentId())) {
				DictTypeEntity parentDicttype = new DictTypeEntity();
				parentDicttype.setId(dicttype.getParentId());
				parentDicttype = (DictTypeEntity) dictTypeService
						.get(parentDicttype);
				request.setAttribute("parentDicttype", parentDicttype);
				type = "add";
			}
		}
		request.setAttribute("dicttype", dicttype);
		request.setAttribute("type", type);
		return "admin/dicttype/dicttype_edit";
	}

	/**
	 * 字典类型信息保存
	 * 
	 * @param dictType
	 * @param bindingResult
	 */
	@RequestMapping("/save")
	public void save(@Valid DictTypeEntity dictType, BindingResult bindingResult) {
		// 这里是验证，要依赖实体类的 注解
		if (!validate(bindingResult)){
			return;
		}
		OnlineUser loginUser = this.getCurrentUser();
		dictType.setCreateBy(loginUser.getLoginName());
		dictType.setCreateDate(new Date());
		dictTypeService.save(dictType);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(dictType);
	}

	/**
	 * 修改字典类型信息
	 * 
	 * @param dictType
	 * @param bindingResult
	 */
	@RequestMapping("/update")
	public void update(@Valid DictTypeEntity dictType,
			BindingResult bindingResult) {
		if (!validate(bindingResult)){
			return;
		}
		dictTypeService.update(dictType);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(dictType);
	}

	/**
	 * 删除字典类型信息
	 * 
	 * @param dictType
	 */
	@RequestMapping("/delete")
	public void delete(DictTypeEntity dictType) {
		DictTypeEntity dt = (DictTypeEntity) dictTypeService.get(dictType);
		if (dt != null) {
			if ("0".equals(dt.getParentId())) {
				errorJson(getMessage("system.management.text.top.dictionary.type.not.allowed.delete","顶级字典类型不允许删除！"));
			} else {
				dictTypeService.deleteByDictTypeId(dictType);
				// 业务逻辑操作成功，ajax 使用successJson 返回
				successJson(dictType);
			}
		}

	}

	/**
	 * 进入字典类型树查询页面
	 * 
	 * @return String
	 */
	@RequestMapping("/getDicttypeTreeQueryPage")
	public String getDicttypeTreeQueryPage() {
		return "admin/dicttype/dicttype_tree_query";
	}

	/**
	 * 获取字典类型树信息(采用异步加载，可以进行异步刷新树 字典类型树查询，查询所有字典类型)
	 * 
	 */
	@RequestMapping("/getDicttypeTreeQuery")
	public void getDicttypeTreeQuery() {
		Map<String, Object> param = new HashMap<String, Object>();
		List<HashMap> list = dictTypeService.queryDicttypeTree(param);
		List<HashMap> mapsList = new ArrayList<HashMap>();
		for (HashMap hashMap : list) {
			HashMap map = new HashMap();
			map.put("id", hashMap.get("ID"));
			map.put("pId", hashMap.get("PID"));
			map.put("name", hashMap.get("NAME"));
			map.put("title", hashMap.get("TITLE"));
			map.put("open", true);
			mapsList.add(map);
		}
		successJson(mapsList);
	}

	/**
	 * 获取字典类型树信息(采用异步加载，可以进行异步刷新树 查询当前用户所在企业能查看到的字典类型)
	 * 
	 */
	@RequestMapping("/getDicttypeByCompTreeQuery")
	public void getDicttypeByCompTreeQuery() {
		UserEntity user = (UserEntity) this.getCurrentUser();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("compId", user.getCompId());
		List<HashMap> list = dictTypeService.queryDicttypeTree(param);
		List<HashMap> mapsList = new ArrayList<HashMap>();
		for (HashMap hashMap : list) {
			HashMap map = new HashMap();
			map.put("id", hashMap.get("ID"));
			map.put("pId", hashMap.get("PID"));
			map.put("name", hashMap.get("NAME"));
			map.put("title", hashMap.get("TITLE"));
			map.put("open", true);
			mapsList.add(map);
		}
		successJson(mapsList);
	}

	/**
	 * 进入字典类型分配页面
	 * 
	 * @param dictType
	 * @return String
	 */
	@RequestMapping("/getDicttypeAssignmentPage")
	public String getDicttypeAssignmentPage(DictTypeEntity dictType) {
		// 企业ID临时存放在DictTypeEntity中
		String compId = dictType.getId();
		CompanyEntity comp = (CompanyEntity) companyService.get(compId);
		request.setAttribute("comp", comp);
		return "admin/dicttype/dicttype_comp_assignment";
	}

	/**
	 * 进入字典类型分配页面,查询字典类型树json
	 * 
	 * @param dictType
	 * @param compId
	 */
	@RequestMapping("/getDicttypeTreeByCompId")
	public void getDicttypeTreeByCompId(DictTypeEntity dictType, String compId) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("compId", compId);
		List<HashMap> listDictType = dictTypeService
				.getDicttypeLeftJoinChecked(paramsMap);
		List<HashMap> mapsList = new ArrayList<HashMap>();
		for (HashMap hashMap : listDictType) {
			HashMap map = new HashMap();
			map.put("id", hashMap.get("ID"));
			map.put("pId", hashMap.get("PID"));
			map.put("name", hashMap.get("NAME"));
			map.put("title", hashMap.get("TITLE"));
			map.put("checked", hashMap.get("COMPID") != null ? true : false);
			map.put("open", true);
			mapsList.add(map);
		}
		successJson(mapsList);
	}

	/**
	 * 保存字典类型分配信息
	 * 
	 * @param dictTypeComp
	 */
	@RequestMapping("/saveDicttypeComp")
	public void saveDicttypeComp(DictTypeCompEntity dictTypeComp) {

		String[] typeIds = dictTypeComp.getDicttypeId().split(",");
		List<DictTypeCompEntity> list = new ArrayList<DictTypeCompEntity>();
		for (String id : typeIds) {
			if (StringUtils.isBlank(id)) {
				continue;
			}
			DictTypeCompEntity dtc = new DictTypeCompEntity();
			dtc.setCompId(dictTypeComp.getCompId());
			dtc.setDicttypeId(id);
			list.add(dtc);
		}
		Map<String, String> paramsMap = new HashMap<String, String>();
		// 中间关联表关联的ID
		paramsMap.put("compId", dictTypeComp.getCompId());
		dictTypeService.updateDicttypeComp(list, paramsMap);
		successJson("");
	}

	/**
	 * 根据字典类型编码验证编码是否存在
	 * 
	 * @param dictType
	 * @param bindingResult
	 */
	@RequestMapping("/validateTypeCode")
	public void validateTypeCode(DictTypeEntity dictType,
			BindingResult bindingResult) {
		System.out.println(getMessage("system.management.text.verify.dictionary.type.encoding","验证字典类型编码：")+ dictType.getTypeCode());
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("typeCode", dictType.getTypeCode());
		List<DictTypeEntity> list = dictTypeService.queryList(param);
		if (list != null && list.size() > 0) {
			// 如果是修改，则和修改的ID进行比较，如果一样则提示不存在
			if (list.get(0).getId().equals(dictType.getId())) {
				// 不存在no
				successJson("n");
			} else {
				// 已存在yes
				successJson("y");
			}
		} else {
			// 不存在no
			successJson("n");
		}
	}
}
