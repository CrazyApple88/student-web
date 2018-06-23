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
					var id = $("#entityId").val();
					if (id != '') {
						$('#form_post').attr("action", "${context}/admin/dataRecover/update");
					}
					//当前的form通过ajax方式提交（用到jQuery.Form文件）
					$(form).ajaxSubmit({
						dataType : "json",
						success : function(json) {
							if (json.code == 1) {
								toolTip.tips({
									timer:true,/*是否开启定时器*/
						            sec:2,/*倒计时的秒数*/
									message:"保存成功！",
									callback:function(){}
								});
								delDialog();
							} else {
								toolTip.error({
									timer:true,/*是否开启定时器*/
						            sec:2,/*倒计时的秒数*/
									message:"保存失败！",
									callback:function(){}
								});
							}
						}
					});
	
				},
				focusInvalid : true, //验证提示时，鼠标光标指向提示的input
				rules : {
					recoverCode : {
						required : true,
						maxlength : 100
					},
					tableName : {
						maxlength : 500
					},
					status : {
						maxlength : 32
					},
					data : {
						maxlength : 0
					},
					createBy : {
						maxlength : 32
					},
					//日期不校验
				},
				messages : {
					recoverCode : {
						required : "恢复的code不能为空",
						maxlength :"恢复的code长度不能超过100"
					},
					tableName : {
						maxlength :"对象名称长度不能超过500"
					},
					status : {
						maxlength :"状态长度不能超过32"
					},
					data : {
						maxlength :"删除的信息长度不能超过0"
					},
					createBy : {
						maxlength :"删除人长度不能超过32"
					},
					//日期不校验
				}
			});
		});
	</script>
</head>
<body>

<div class="main-container">
    <div class="tabContainer">
		<form action="${context}/admin/dataRecover/save" method="post" id="form_post" name="form_post">
					<input type="hidden" value="${entity.id}" id="entityId" name="id" >
                <ul class="details_box">
                 	<li>
                   		<span>恢复的code<font color="red">*</font></span>
                       	<input type="text" class="inp_detail" name="recoverCode" id="recoverCode" value="${entity.recoverCode}"  placeholder="请输入恢复的code" >
                	</li>
                 	<li>
                   		<span>对象名称</span>
                       	<input type="text" class="inp_detail" name="tableName" id="tableName" value="${entity.tableName}"  placeholder="请输入对象名称" >
                	</li>
                 	<li>
                   		<span>状态</span>
                       	<input type="text" class="inp_detail" name="status" id="status" value="${entity.status}"  placeholder="请输入状态" >
                	</li>
                 	<li>
                   		<span>删除的信息</span>
                       	<input type="text" class="inp_detail" name="data" id="data" value="${entity.data}"  placeholder="请输入删除的信息" >
                	</li>
                 	<li>
                   		<span>删除人</span>
                       	<input type="text" class="inp_detail" name="createBy" id="createBy" value="${entity.createBy}"  placeholder="请输入删除人" >
                	</li>
                 	<li>
                   		<span>删除时间</span>
                       	<input type="text" class="Wdate inp_detail" name="createDate"  id="createDate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" <#if entity.createDate??>value="${entity.createDate?string('yyyy-MM-dd HH:mm:ss')}"</#if>  placeholder="请输入删除时间">
                	</li>
                    <li  class="class_btn_li">
                        <input type="submit" btnType="save" value="确定" class="fbtn btn_sm ensure_btn">
                        <input type="button" value="关闭" btnType="cancel"  class="cancel fbtn btn_sm cancle_btn">
                    </li>
	           </ul>
		</form>
    </div>
</div>

</body>
</html>