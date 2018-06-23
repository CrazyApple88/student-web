package com.xhgx.web.admin.directive;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xhgx.web.admin.dict.entity.DictEntity;
import com.xhgx.web.admin.dicttype.service.DictHelper;
import com.xhgx.web.base.directive.BaseDirective;

/**
 * @ClassName SysDictDirective
 * @Description 
 * <@sysDict key="${key}" ; value,map></br> ${value} </br> </@sysDict></br>
 * @author ZhangJin
 * @date 2017年5月24日
 * @vresion 1.0
 */
public class SysDictDirective extends BaseDirective {

	@Override
	public void initEntity() {
		String key = this.getParam("key");
		List<DictEntity> dictList = DictHelper.getInstance().get(key);
		this.entityList = new ArrayList<Object>();
		if(dictList != null){
			for (DictEntity dict : dictList) {
				this.entityList.add(dict);
			}
			
		}
	}

	@Override
	public void initEntityextend(Object entity, Map<String, Object> map) {

	}

}
