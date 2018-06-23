<!DOCTYPE html>
<#import "spring.ftl" as i18n/>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <style>
      .inp_detail{
      width: 205px;
      }
    </style>
	<script type="text/javascript">
		$().ready(function() {
			var toolTip = new diaPrompt();
			// validate signup form on keyup and submit
			$("#form_post").validate({
				submitHandler : function(form) { //验证通过后的执行方法
					var id = $("#entityId").val();
					if (id != '') {
						$('#form_post').attr("action", "${context}/admin/simpleQuery/update");
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
				rules : {
					name : {
						required : true,
						maxlength : 32
					},
                    mark : {
						maxlength : 32
					},
					content : {
                        required : true,
						maxlength : 500
					}
				},
				messages : {
					name : {
						required : "<@i18n.messageText code='simple.query.form.confirm.name.empty'  text='名称不能为空' />",
						maxlength :"<@i18n.messageText code='simple.query.form.confirm.name.length'  text='名称长度不能超过32' />"
					},
                    mark : {
						maxlength :"<@i18n.messageText code='simple.query.form.confirm.description.length'  text='描述长度不能超过32' />"
					},
					content : {
                        required : "<@i18n.messageText code='simple.query.form.confirm.script.empty'  text='脚本不能为空' />",
						maxlength :"<@i18n.messageText code='simple.query.form.confirm.script.length'  text='脚本长度不能超过500' />"
					}
				}
			});
		});
	</script>
</head>
<body>

<div class="main-container">
    <div class="tabContainer">
		<form action="${context}/admin/simpleQuery/save" method="post" id="form_post" name="form_post">
            <input type="hidden" value="${entity.id}" id="entityId" name="id" >
            <input type="hidden" value="<#if entity.createDate?? >${entity.createDate?datetime}</#if>"  name="createDate" >
            <input type="hidden" value="${entity.lastUseTime}"  name="lastUseTime" >
            <input type="hidden" value="${entity.count!'0'}"  name="count" >
                <ul class="details_box">
                 	<li>
                   		<span> <@i18n.messageText code='simple.query.text.name'  text='名称' /> <font color="red">*</font></span>
                       	<input type="text" class="inp_detail" name="name" id="name" value="${entity.name}"  placeholder="<@i18n.messageText code='simple.query.text.enter.name'  text='请输入名称' />" >
                	</li>
                 	<li>
                   		<span> <@i18n.messageText code='simple.query.text.description'  text='描述' /> </span>
                       	<input type="text" class="inp_detail" name="mark" id="mark" value="${entity.mark}"  placeholder="<@i18n.messageText code='simple.query.text.enter.description'  text='请输入描述' />" >
                	</li>
                 	<li style="    height: 115px;">
                   		<span> <@i18n.messageText code='simple.query.text.script'  text='脚本' /> </span>
                        <textarea rows="4" cols="30" name ="content" class="" >${entity.content}</textarea>
                	</li>
                    <li  class="class_btn_li">
                        <input type="submit" btnType="save" value="<@i18n.messageText code='common.button.quit.true'  text='确定' />" class="fbtn btn_sm ensure_btn">
                        <input type="button" value="<@i18n.messageText code='common.button.quit.false'  text='关闭' />" btnType="cancel"  class="cancel fbtn btn_sm cancle_btn">
                    </li>
	           </ul>
		</form>
    </div>
</div>


</body>
</html>