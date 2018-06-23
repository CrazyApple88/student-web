<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    
    <#include "/system/common_css.ftl">
    <#include "/system/common_js.ftl">
    <script type="text/javascript">
		$().ready(function() {
			var toolTip = new diaPrompt();
			// validate signup form on keyup and submit
			$("#form_post").validate({
				submitHandler : function(form) { //验证通过后的执行方法
					var id = $("[name='id']").val();
					if (id != '') {
						$('#form_post').attr("action", "/admin/dicttype/update");
					}
					//当前的form通过ajax方式提交（用到jQuery.Form文件）
					$(form).ajaxSubmit({
						dataType : "json",
						success : function(json) {
							if (json.code == 1) {
								var dicttypeId = json.data.id;
		    	        		var parentId = json.data.parentId;
		    	        		var typeName = json.data.typeName;
		    	        		var type = 1;
		    	        		if(id != null && id.length > 0){
		    	        			type = 2;
		    	        		}
		    	        		add_dicttype_success(dicttypeId,parentId,typeName,type);
		    	    			$("#tabledata").load('/admin/dicttype/getDicttypeEditPage?id='+dicttypeId,{},function(){});
							} else {
								toolTip.error({
									timer:true,/*是否开启定时器*/
						            sec:2,/*倒计时的秒数*/
									message:"<@i18n.messageText code='business.management.text.save.failed'  text='保存失败!' />",
									callback:function(){}
								});
							}
						}
					});
	
				},
				focusInvalid : true, //验证提示时，鼠标光标指向提示的input
				onkeyup:false,
				rules : {
					typeName : {
						required : true,
						rangelength : [ 1, 32 ],
						spaceCheck : "typeName"
					},
					typeCode : {
						required : true,
						rangelength : [ 1, 32 ],
						regex : "^\\w+$",
						remote : {
							url : "/admin/dicttype/validateTypeCode", //后台处理程序
							type : "post", //数据发送方式
							dataType : "json", //接受数据格式   
							data : { //要传递的数据
								typeCode : function() {
									return $("#typeCode").val();
								},
								id : function() {
									return $("[name='id']").val();
								}
							},
							dataFilter : function(data) { //返回结果
								var json = eval('(' + data + ')');
								if (json.data == "y")//存在yes
									return false;
								else
									//不存在no
									return true;
							}
						}
						
					},
					sort : {
						digits : true,
						range : [0,999]
					},
					intro : {
						maxlength : 128
					}
				},
				messages : {
					typeName : {
						required : "<@i18n.messageText code='dictionary.type.management.form.confirm.dictionary.name.empty'  text='字典类型名称不能为空' />",
						rangelength : "<@i18n.messageText code='dictionary.type.management.form.confirm.dictionary.name.length'  text='字典类型名称长度必须在1-32之间' />",
						spaceCheck : "<@i18n.messageText code='dictionary.type.management.form.confirm.dictionary.name.space'  text='字典类型名称不能为空格' />"
					},
					typeCode : {
						required : "<@i18n.messageText code='dictionary.type.management.form.confirm.dictionary.code.empty'  text='字典类型编码不能为空' />",
						rangelength : "<@i18n.messageText code='dictionary.type.management.form.confirm.dictionary.code.length'  text='字典类型编码长度必须在1-32之间' />",
						regex : "<@i18n.messageText code='dictionary.type.management.form.confirm.dictionary.type.code.consist'  text='字典类型编码只能是数字、字母与下划线' />",
						remote : "<@i18n.messageText code='dictionary.type.management.form.confirm.dictionary.type.code.exist'  text='字典类型编码已存在！' />"
					},
					sort : {
						digits : "<@i18n.messageText code='dictionary.management.form.comfirm.sequence.number.integer'  text='排序序号必须为整数' />",
						range : "<@i18n.messageText code='dictionary.management.form.comfirm.sequence.number.ranges'  text='排序序号范围0-999' />"
					},
					intro : {
						maxlength : "<@i18n.messageText code='dictionary.management.form.comfirm.note.length'  text='备注描述长度不能超过128' />"
					}
				}
			});
		});
	</script>
    <script type="text/javascript">

    $(document).ready(function(){
    	
    	$("#editDicttypeId").click(function(){
    		$("input,select").attr("disabled",false);
    		$("#editDicttypeId").hide();
    		$("#saveDicttypeId,#quXiaoId").show();
    		
    	});
    	$("#quXiaoId").click(function(){
    		var id = $("[name='id']").val();
    		if(id == ''){
    			id = $("[name='parentId']").val();
    		}
    		$("#tabledata").load('/admin/dicttype/getDicttypeEditPage?id='+id,{},function(){});
    	});
    	
    	var type = '${type}';
    	if(type == "add"){
    		$('#form_post').attr("action", "/admin/dicttype/save");
    		$("#editDicttypeId").click();
    		$("#parentIdTr").show();
    		
    	}
    })
    </script>
    
</head>
<body>

<div class="main-container">
    <div class="tabContainer">
        <div class="content">
            <form action="/admin/dicttype/save" method="post" id="form_post" name="form_post">
            	<input type="hidden" value="${dicttype.id }"  name="id" id="id">
            	<input type="hidden" value="${dicttype.parentId }"  name="parentId" id="parentId">
	                <div class="form_box">
	                	<ul class="col-lg-12 col-sm-12 col-xs-12">
	                		<li id="parentIdTr" style="display: none;"><span> <@i18n.messageText code='dictionary.type.management.text.parent.type'  text='父类型' /> </span><font color="red">${parentDicttype.typeName }</font></li>
	                 		<li><span> <@i18n.messageText code='dictionary.type.management.text.type.name'  text='类型名称' /> </span><p><input type="text" class="inp_set" name="typeName" id="typeName" value="${dicttype.typeName }"  disabled="disabled"></p><font color="red">*</font></li>
	                    	<li><span> <@i18n.messageText code='dictionary.type.management.text.type.number'  text='类型编号' /> </span><p><input type="text" class="inp_set" name="typeCode" id="typeCode"  value="${dicttype.typeCode }" disabled="disabled"></p><font color="red">*</font></li>
	                    	<li><span> <@i18n.messageText code='dictionary.management.text.dictionary.sequence'  text='排序' /> </span><p><input type="text" class="inp_set" name="sort" id="sort" value="${dicttype.sort }" disabled="disabled"></p></li>
	                    	<li><span> <@i18n.messageText code='dictionary.management.text.dictionary.note.description'  text='备注描述' /> </span><p><input type="text" class="inp_set" name="intro" id="intro" value="${dicttype.intro }" disabled="disabled"></p></li>
	                		<@loginUserAuth key='dicttype_edit' ; value>
								<#if value =='1'>
	                    			<li class="class_btn_li">
	                    				<input type="submit" class="saveDicttypeId fbtn btn_sm ensure_btn" id="saveDicttypeId" value="<@i18n.messageText code='common.button.quit.true'  text='确定' />" style="display: none;">
	                    				<input type="button" class="editDicttypeId fbtn btn_sm ensure_btn" id="editDicttypeId" value="<@i18n.messageText code='common.button.edit'  text='编辑' />">
	                    				<input type="button" class="quXiaoId fbtn btn_sm cancle_btn" id="quXiaoId" value="<@i18n.messageText code='common.button.quit.false'  text='取消' />" style="display: none;">
	                    			</li>
	                			</#if>
	               			</@loginUserAuth>
	               		</ul>
	               	</div>
			</form>
        </div>
    </div>
</div>
</body>
</html>