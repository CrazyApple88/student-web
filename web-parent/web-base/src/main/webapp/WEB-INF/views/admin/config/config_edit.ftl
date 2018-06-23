<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
	<#include "/system/common_js.ftl">
   <script type="text/javascript">
		$().ready(function() {
			var toolTip = new diaPrompt();
			// validate signup form on keyup and submit
			$("#form_post").validate({
				submitHandler : function(form) { //验证通过后的执行方法
					var id = $("[name='id']").val();
					if (id != '') {
						$('#form_post').attr("action", "/admin/config/update");
					}
					//当前的form通过ajax方式提交（用到jQuery.Form文件）
					$(form).ajaxSubmit({
						dataType : "json",
						success : function(json) {
							if (json.code == 1) {
								toolTip.tips({
									timer:true,/*是否开启定时器*/
						            sec:2,/*倒计时的秒数*/
									message:"<@i18n.messageText code='business.management.text.save.sucessfully'  text='保存成功!' />",
									callback:function(){}
								});
								delDialog();
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
					name : {
						required : true,
						rangelength : [ 1, 32 ],
						spaceCheck : "name"
					},
					code : {
						required : true,
						rangelength : [ 1, 32 ],
						spaceCheck : "code",
						remote : {
							url : "/admin/config/validateCode", //后台处理程序
							type : "post", //数据发送方式
							dataType : "json", //接受数据格式   
							data : { //要传递的数据
								code : function() {
									return $("#code").val();
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
									return true;//不存在no
							}
						}
					},
					value : {
						required : true,
						rangelength : [ 1, 64 ],
						spaceCheck : "value"
					},
					intro : {
						maxlength : 128
					}
				},
				messages : {
					name : {
						required : "<@i18n.messageText code='system.configuration.management.form.confirm.configuration.name.empty'  text='配置名称不能为空' />",
						rangelength : "<@i18n.messageText code='system.configuration.management.form.confirm.configuration.name.length'  text='配置名称长度必须在1-32之间' />",
						spaceCheck : "<@i18n.messageText code='system.configuration.management.form.confirm.configuration.name.space'  text='配置名称不能以空格开头' />"
					},
					code : {
						required : "<@i18n.messageText code='common.text.usernamesystem.configuration.management.form.confirm.property.key.empty'  text='属性key不能为空!' />",
						rangelength : "<@i18n.messageText code='system.configuration.management.form.confirm.property.key.length'  text='属性key长度必须在1-32之间!' />",
						remote : "<@i18n.messageText code='system.configuration.management.form.confirm.property.key.exist'  text='属性key已存在！' />",
						spaceCheck : "<@i18n.messageText code='system.configuration.management.form.confirm.property.key.space'  text='属性key不能为空格！' />"
					},
					value : {
						required : "<@i18n.messageText code='system.configuration.management.form.confirm.property.value.empty'  text='属性值不能为空!' />",
						rangelength : "<@i18n.messageText code='system.configuration.management.form.confirm.property.value.length'  text='属性值长度必须在1-64之间' />",
						spaceCheck : "<@i18n.messageText code='system.configuration.management.form.confirm.property.value.space'  text='属性值不能为空格！' />"
					},
					intro : {
						maxlength : "<@i18n.messageText code='system.configuration.management.form.confirm.detailed.length'  text='详细说明长度不能超过128' />"
					}
				}
			});
		});
	</script>
</head>
<body>
<div class="main-container">
    <div class="tabContainer">
        <form action="${context}/admin/config/save" method="post" id="form_post" name="form_post">
			<input type="hidden" value="${entity.id}" id="entityId" name="id" >
               	<ul class="details_box">
                   	<li><span> <@i18n.messageText code='system.configuration.management.text.configuration.name'  text='配置名称' /> <font color="red">*</font></span><input type="text" class="inp_detail" name="name"  id="name"  value="${entity.name}"></li>
                    <li><span> <@i18n.messageText code='system.configuration.management.text.property.key'  text='属性key' /> <font color="red">*</font></span><input type="text" class="inp_detail" name="code"  id="code" value="${entity.code}"></li>
                    <li><span> <@i18n.messageText code='system.configuration.management.text.property.value'  text='属性值' /> <font color="red">*</font></span><input type="text" class="inp_detail" name="value" id="value" value="${entity.value}"></li>
                    <li><span> <@i18n.messageText code='dictionary.management.text.dictionary.note.description'  text='备注信息' /> </span><input type="text" class="inp_detail" name="intro" id="intro" value="${entity.intro}"></li>
                    <li  class="class_btn_li">
	                    <input type="submit" value="<@i18n.messageText code='common.button.quit.true'  text='确定' />" class="save fbtn btn_sm ensure_btn">
	                    <input type="button" class="cancel fbtn btn_sm cancle_btn" value="<@i18n.messageText code='common.button.quit.false'  text='关闭' />" onclick="javascript:delDialog()">
                    </li>
               	</ul>
		</form>
    </div>
</div>
</body>
</html>