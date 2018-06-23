<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <#include "/system/common_js.ftl">
    <title>消息编辑页面</title>
	<script type="text/javascript">
		$().ready(function() {
			var toolTip = new diaPrompt();
			// validate signup form on keyup and submit
			$("#form_post").validate({
				submitHandler : function(form) { //验证通过后的执行方法
					var id = $("#entityId").val();
					if (id != '') {
						$('#form_post').attr("action", "${context}/admin/message/update");
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
									callback:function(){
										top.refreshMsgNum();
									}
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
					title : {
						required : true,
						maxlength : 200
					},
					content : {
						maxlength : 2000
					},
					//日期不校验
				},
				messages : {
					title : {
						required : "<@i18n.messageText code='message.management.form.confirm.message.header.empty'  text='消息标题不能为空' />",
						maxlength :"<@i18n.messageText code='message.management.form.confirm.message.header.length'  text='消息标题长度不能超过200' />"
					},
					content : {
						maxlength :"<@i18n.messageText code='message.management.form.confirm.message.content.length'  text='消息内容长度不能超过2000' />"
					},
					//日期不校验
				}
			});
			
			$(".details_box").on("click",".userRoleAssignment",function(){
				var toolTip = new Layer({
					layerclass:"layerContianer2",//存放页面的容器类名
			        width:400,
			        height:350,
			        alerttit:"<@i18n.messageText code='message.management.text.user.selection'  text='用户选择' />",
			        setOverflow:"overflow-y:scroll",
			        callback:function(){		        	
			        	$(".layerContianer2").load("${context}/admin/message/getUserByDept",{},function(){});
			        }
			    });
			    toolTip.show();
			});
		});

		function getInfoInput(users,userIds){
			$("#users").val(users);
			$("#userIds").val(userIds);
		}
	</script>
	<style type="text/css">
		.details_box li, .details_both li{
			height:auto;
			overflow:hidden;
		}
	</style>
</head>
<body>

<div class="main-container">
    <div class="tabContainer">
		<form action="${context}/admin/message/save" method="post" id="form_post" name="form_post">
					<input type="hidden" value="${entity.id}" id="entityId" name="id" >
                <ul class="details_box">
                 	<li>
                   		<span> <@i18n.messageText code='message.management.text.message.header'  text='消息标题' /> <font color="red">*</font></span>
                       	<textarea id="title" name="title" style="width: 300px; height: 100px; text-align:left;margin: 0 auto;">${entity.title}</textarea>
                	</li>
                	<li>
                   		<span> <@i18n.messageText code='message.management.text.message.content'  text='消息内容' /> <font color="red">*</font></span>	
    					<textarea id="content" name="content" style="width: 300px; height: 200px; text-align:left;margin: 0 auto;">${entity.content}</textarea>
					</li>
					<li>
						<span> <@i18n.messageText code='message.management.text.message.receiver'  text='消息接收者' /> <font color="red">*</font></span>
						<a style="margin-left: 20px;margin-top: 5px;" class='userRoleAssignment td_btn part_btn part_bg'> <@i18n.messageText code='message.management.text.user.selection'  text='用户选择' /> </a>
						<input type="hidden" id="userIds" class="inp_detail" name="userIds" value="${entity.userIds}">
						<textarea id="users" name="users" readonly="readonly" style="width: 300px; height: 100px; text-align:left;margin: 0 auto;">${entity.users}</textarea>
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