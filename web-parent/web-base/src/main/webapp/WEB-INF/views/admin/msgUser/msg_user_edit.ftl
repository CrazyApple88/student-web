<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
	<script type="text/javascript">
		$().ready(function() {
			var toolTip = new diaPrompt();
			// validate signup form on keyup and submit
			$("#form_post").validate({
				submitHandler : function(form) { //验证通过后的执行方法
					var id = $("#entityId").val();
					if (id != '') {
						$('#form_post').attr("action", "${context}/admin/msgUser/update");
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
					userId : {
						required : true,
						maxlength : 32
					},
					msgId : {
						maxlength : 32
					},
					status : {
						maxlength : 32
					}
				},
				messages : {
					userId : {
						required : "<@i18n.messageText code='message.management.form.confirm.message.receiver.empty'  text='消息接收者不能为空' />",
						maxlength :"<@i18n.messageText code='message.management.form.confirm.message.receiver.length'  text='消息接收者长度不能超过32' />"
					},
					msgId : {
						maxlength :"<@i18n.messageText code='message.management.form.confirm.message.id.length'  text='消息id长度不能超过32' />"
					},
					status : {
						maxlength :"<@i18n.messageText code='message.management.form.confirm.message.status.length'  text='消息状态长度不能超过32' />"
					}
				}
			});
		});
	</script>
</head>
<body>

<div class="main-container">
    <div class="tabContainer">
		<form action="${context}/admin/msgUser/save" method="post" id="form_post" name="form_post">
					<input type="hidden" value="${entity.id}" id="entityId" name="id" >
                <ul class="details_box">
                 	<li>
                   		<span> <@i18n.messageText code='message.management.text.message.receiver'  text='消息接收者' /> <font color="red">*</font></span>
                       	<input type="text" class="inp_detail" name="userId" id="userId" value="${entity.userId}"  placeholder="<@i18n.messageText code='message.management.text.enter.message.receiver'  text='请输入消息接收者' />" >
                	</li>
                 	<li>
                   		<span> <@i18n.messageText code='message.management.text.message.id'  text='消息id' /> </span>
                       	<input type="text" class="inp_detail" name="msgId" id="msgId" value="${entity.msgId}"  placeholder="<@i18n.messageText code='message.management.text.enter.message.id'  text='请输入消息id' />" >
                	</li>
                 	<li>
                   		<span> <@i18n.messageText code='message.management.text.message.status'  text='消息状态' /> </span>
                       	<input type="text" class="inp_detail" name="status" id="status" value="${entity.status}"  placeholder="<@i18n.messageText code='message.management.text.enter.message.status'  text='请输入消息状态' />" >
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