<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>密码修改页面</title>
    
    <#include "/system/common_js.ftl">
    <script type="text/javascript">

		$().ready(function() {
			var toolTip = new diaPrompt();
			// validate signup form on keyup and submit
			$("#form_post").validate({
				submitHandler : function(form) { //验证通过后的执行方法
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
		    	    	            message:json.msg,
   		    	    	         	callback:function(){}
		    	        		});
							}
						}
					});
	
				},
				focusInvalid : true, //验证提示时，鼠标光标指向提示的input
				ignore: "",
				rules : {
					oldPassword : {
						required : true
					},
					password : {
						required: true,
						minlength: 3
					},
					passwordAgain : {
						required: true,
						equalTo: "#password"
					}
				},
				messages : {
					oldPassword : {
						required : "<@i18n.messageText code='user.management.form.confirm.password.empty'  text='密码不能为空' />"
					},
					password : {
						required : "<@i18n.messageText code='user.management.form.confirm.password.empty'  text='密码不能为空' />",
						minlength : "<@i18n.messageText code='user.management.form.confirm.password.length.digits'  text='密码长度不能少于3位' />"
					},
					passwordAgain : {
						required : "<@i18n.messageText code='user.management.form.confirm.duplicate.password.empty'  text='重复密码不能为空' />",
						equalTo: "<@i18n.messageText code='user.management.form.confirm.two.password.inconsistent'  text='两次密码不一致！' />"
					}
				}
			});
		});
		function delDialog(){
	    	$(".overlay").remove();
	    }
    </script>
    
</head>
<body>

<div class="main-container">
    <div class="tabContainer" style="padding-top:20px">
       <form action="/admin/user/updatePasswordByNowUser" method="post" id="form_post" name="form_post">
           	<input type="hidden" value="${user.id }"  name="id" id="id">
               	<ul class="details_box">
                   	<li><span> <@i18n.messageText code='user.management.text.old.password'  text='旧密码' /> </span><input type="password" class="inp_detail" placeholder="<@i18n.messageText code='user.management.text.enter.old.password'  text='请输入旧密码' />" name="oldPassword" id="oldPassword"><font color="red">*</font></li>
                   	<li><span> <@i18n.messageText code='user.management.text.new.password'  text='新密码' /> </span><input type="password" class="inp_detail" placeholder="<@i18n.messageText code='user.management.text.enter.new.password'  text='请输入新密码' />" name="password" id="password"><font color="red">*</font></li>
                   	<li><span> <@i18n.messageText code='user.management.text.confirm.password'  text='重复密码' /> </span><input type="password" class="inp_detail" placeholder="<@i18n.messageText code='user.management.text.enter.password.again'  text='请再次输入密码' />" name="passwordAgain" id="passwordAgain"><font color="red">*</font></li>
                   	<li class="class_btn_li">
                   		<input type="submit" class="savePassword  fbtn btn_sm ensure_btn" id="savePassword" value="<@i18n.messageText code='common.button.quit.true'  text='确定' />">
                   		<input type="button" class="cancel fbtn btn_sm cancle_btn" value="<@i18n.messageText code='common.button.quit.false'  text='关闭' />" onclick="javascript:delDialog()">
                   	</li>
               </ul>
		</form>
    </div>
</div>
</body>
</html>