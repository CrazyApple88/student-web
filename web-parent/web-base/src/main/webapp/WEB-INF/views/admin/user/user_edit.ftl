<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>用户新增/修改页面</title>
    
    <#import "spring.ftl" as i18n/>
	<script type="text/javascript">
		$().ready(function() {
			var toolTip = new diaPrompt();
			// validate signup form on keyup and submit
			$("#form_post").validate({
				submitHandler : function(form) { //验证通过后的执行方法
					var id = $("[name='id']").val();
					if (id != '') {
						$('#form_post').attr("action", "/admin/user/update");
					}
					//当前的form通过ajax方式提交（用到jQuery.Form文件）
					$(form).ajaxSubmit({
						dataType : "json",
						success : function(json) {
							if (json.code == 1) {
								toolTip.tips({
									timer:true,/*是否开启定时器*/
						            sec:2,/*倒计时的秒数*/
		    	    	            message:"<@i18n.messageText code='common.text.saved'  text='保存成功！' />",
   		    	    	         	callback:function(){}
		    	        		});
								delDialog();
							} else {
								toolTip.error({
									timer:true,/*是否开启定时器*/
						            sec:2,/*倒计时的秒数*/
		    	    	            message:"<@i18n.messageText code='common.text.failed.save'  text='保存失败' />",
   		    	    	         	callback:function(){}
		    	        		});
							}
						}
					});
	
				},
				focusInvalid : true, //验证提示时，鼠标光标指向提示的input
				onkeyup:false,
				ignore: "",
				rules : {
					compId : {
						required : true
					},
					userName : {
						required : true,
						rangelength : [ 2, 32 ],
						regex : "^\\w+$",
						remote : {
							url : "/admin/user/validateUserName", //后台处理程序
							type : "post", //数据发送方式
							dataType : "json", //接受数据格式   
							data : { //要传递的数据
								userName : function() {
									return $("#userName").val();
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
					password : "required",
					realName : {
						required : true,
						rangelength : [ 2, 16 ],
						spaceCheck : "realName"
					},
					idCard : {
						maxlength : 18,
						regex : "^[0-9xX]+$"
					},
					empNo : {
						maxlength : 32
					},
					email : {
						maxlength : 64,
						email : true
					},
					phone : {
						maxlength : 16,
						regex : "^[0-9+-]+$"
					},
					mobile : {
						maxlength : 16,
						regex : "^[0-9+-]+$"
					},
					address : {
						maxlength : 128
					}
				},
				messages : {
					compId : "<@i18n.messageText code='user.management.form.confirm.business.empty'  text='企业不能为空' />",
					userName : {
						required : "<@i18n.messageText code='user.management.form.confirm.username.empty'  text='用户名不能为空' />",
						rangelength : "<@i18n.messageText code='user.management.form.confirm.username.length'  text='用户名长度必须在2-32之间' />",
						regex : "<@i18n.messageText code='user.management.form.confirm.username.consist'  text='用户名只能是数字、字母与下划线' />",
						remote : "<@i18n.messageText code='user.management.form.confirm.username.exist'  text='用户名已存在！' />"
					},
					password : "<@i18n.messageText code='user.management.form.confirm.password.empty'  text='密码不能为空' />",
					realName : {
						required : "<@i18n.messageText code='user.management.form.confirm.real.name.empty'  text='真实姓名不能为空' />",
						rangelength : "<@i18n.messageText code='user.management.form.confirm.real.name.length'  text='真实姓名长度必须在2-16之间' />",
						spaceCheck : "<@i18n.messageText code='user.management.form.confirm.real.name.spase'  text='真实姓名不能为空格！' />"
					},
					idCard : {
						maxlength : "<@i18n.messageText code='user.management.form.confirm.identity.card.length'  text='身份证长度不能超过18' />",
						regex : "<@i18n.messageText code='user.management.form.confirm.identity.card.consist'  text='身份证只能有数字和xX组成' />"
					},
					empNo : {
						maxlength : "<@i18n.messageText code='user.management.form.confirm.job.number.length'  text='工号长度不能超过32' />"
					},
					email : {
						maxlength : "<@i18n.messageText code='user.management.form.confirm.email.length'  text='email长度不能超过64' />",
						email : "<@i18n.messageText code='user.management.form.confirm.email.format'  text='email格式不对' />"
					},
					phone : {
						maxlength : "<@i18n.messageText code='user.management.form.confirm.phone.number.length'  text='电话号码长度不能超过16' />",
						regex : "<@i18n.messageText code='user.management.form.confirm.phone.number.consist'  text='电话号码只能由数字、+和-组成' />"
					},
					mobile : {
						maxlength : "<@i18n.messageText code='user.management.form.confirm.mobile.phone.number.length=手机号码长度不能超过16'  text='用户名' />",
						regex : "<@i18n.messageText code='user.management.form.confirm.mobile.phone.number.consist'  text='手机号码只能由数字、+和-组成' />"
					},
					address : {
						maxlength : "<@i18n.messageText code='user.management.form.confirm.contact.address.length'  text='联系地址长度不能超过128' />"
					}
				}
			});
		});
		
		$(".selectInfo").click(function(){
// 			var newWindow = window.open ('/admin/company/getOneCompanyQueryPage', 'select_top', 'height=650, width=900, top=100px, left=100px, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
			var toolTip = new Layer({
				layerclass:"layerContianer2",//存放页面的容器类名
		        width:900,
		        height:550,
		        alerttit:"<@i18n.messageText code='role.management.text.select.business'  text='企业选择' />",
		        setOverflow:"overflow-y:scroll",
		        callback:function(){		        	
		        	$(".layerContianer2").load('/admin/company/getOneCompanyQueryPage',{},function(){});
		        }
		    });
		    toolTip.show();
		});
		
		function getInfoInput(compId, compName){
			$("#compId").val(compId);
			$("#compName").val(compName);
		}
	</script>
</head>
<body>
<div class="main-container">
    <div class="tabContainer">
       
            <form action="/admin/user/save" method="post" id="form_post" name="form_post">
            	<input type="hidden" value="${user.id }"  name="id" id="id">
            	<input type="hidden" value="${deptId}"  name="deptId" id="deptId">
	                <ul class="details_box">
				   		<li><span> <@i18n.messageText code='user.management.text.affiliated.companies'  text='所属企业' /> </span>
				   			<input type="text" disabled="disabled"  id="compName" class="inp_detail" name="compName" value="${user.compName }">
				   			<input type="hidden" id="compId" class="inp_detail" name="compId" value="${user.compId }">
				   			<#if superUser == 'yes'><input type="button" value="<@i18n.messageText code='role.management.button.please.select'  text='请选择' />" class="selectInfo fbtn btn_xs btn_primary" ></#if>
				   		</li>
	                    <li><span> <@i18n.messageText code='user.management.text.username'  text='用户名' /> <font color="red">*</font></span><input type="text" class="inp_detail" name="userName" id="userName" value="${user.userName }" <#if user.userName != ''> disabled="disabled"</#if>></li>
	                    <li><span> <@i18n.messageText code='common.text.password'  text='密码' /> <font color="red">*</font></span><input type="text" class="inp_detail" name="password" id="password" <#if user.userName != ''>value="**********" disabled="disabled"</#if>></li>
	                    <li><span> <@i18n.messageText code='user.management.text.real.name'  text='真实姓名' /> <font color="red">*</font></span><input type="text" class="inp_detail" name="realName" id="realName" value="${user.realName }"></li>
	                	<li><span> <@i18n.messageText code='user.management.text.identity.card'  text='身份证' /> </span><input type="text" class="inp_detail" name="idCard" id="idCard" value="${user.idCard }"></li>
	                    <li><span> <@i18n.messageText code='user.management.text.job.number'  text='工号' /> </span><input type="text" class="inp_detail" name="empNo" id="empNo" value="${user.empNo }"></li>
	                    <li><span>email</span><input type="text" class="inp_detail" name="email" id="email" value="${user.email }"></li>
	                    <li><span> <@i18n.messageText code='user.management.text.telephone'  text='电话' /> </span><input type="text" class="inp_detail" name="phone" id="phone" value="${user.phone }"></li>
	                    <li><span> <@i18n.messageText code='user.management.text.mobile.phone'  text='手机' /> </span><input type="text" class="inp_detail" name="mobile" id="mobile" value="${user.mobile }"></li>
	                    <li><span> <@i18n.messageText code='user.management.text.logging.status'  text='登录状态' /> </span>
		                    <select class="det_select" id="loginStatus" name="loginStatus">
								<option value="1" <#if user.loginStatus = 1> selected="selected" </#if>> <@i18n.messageText code='user.management.select.user.status.normal'  text='正常' /> </option>
								<option value="2" <#if user.loginStatus = 2> selected="selected" </#if>> <@i18n.messageText code='user.management.select.user.status.disable'  text='停用' /> </option>
							</select>
	                    </li>
	               		<li><span> <@i18n.messageText code='user.management.text.user.type'  text='用户类型' /> </span>
	               			<select class="det_select" id="userType" name="userType">
          						<@sysDict key="user_type" ; value>
          							<option <#if value.id==user.userType> selected="selected" </#if> value="${value.id}">${value.dictName}</option>  
         						</@sysDict>
							</select>
	               		</li>
	                    <li><span> <@i18n.messageText code='user.management.text.contact.address'  text='联系地址' /> </span><input type="text" name="address" class="inp_detail" id="address" value="${user.address }"></li>
	                    <li class="class_btn_li">
	                    	<input type="submit" class="saveUserId fbtn btn_sm ensure_btn" id="saveUserId" value="<@i18n.messageText code='common.button.quit.true'  text='确定' />">
	                    	<input type="button" class="cancel fbtn btn_sm cancle_btn" value="<@i18n.messageText code='common.button.quit.false'  text='关闭' />" onclick="javascript:delDialog()">
	                    </li>
	                </ul>
			</form>
       
    </div>
</div>
</body>
</html>