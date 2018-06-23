<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <#import "spring.ftl" as i18n/>
    <script type="text/javascript">
		$().ready(function() {
			var toolTip = new diaPrompt();
			
			// validate signup form on keyup and submit
			$("#form_post").validate({
				submitHandler : function(form) { //验证通过后的执行方法
					var id = $("[name='id']").val();
					if (id != '') {
						$('#form_post').attr("action", "/admin/role/update");
					}
					
					var Hintd=new Hint({
						class:".saveRoleId"
					});
					Hintd.before();
					Hintd.beforeSend();
					//当前的form通过ajax方式提交（用到jQuery.Form文件）
					$(form).ajaxSubmit({
						dataType : "json",
						success : function(json) {
							Hintd.success();
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
					roleName : {
						required : true,
						rangelength : [ 1, 32 ],
						spaceCheck : "roleName",
						ajaxCheck : ""
					},
					intro : {
						maxlength : 128
					}
				},
				messages : {
					roleName : {
						required : "<@i18n.messageText code='role.management.form.confirm.role.name.empty'  text='角色名称不能为空' />",
						rangelength : "<@i18n.messageText code='role.management.form.confirm.role.name.length'  text='角色名称长度必须在1-32之间' />",
						spaceCheck : "<@i18n.messageText code='role.management.form.confirm.role.name.space'  text='角色名称不能为空格' />"
					},
					intro : {
						maxlength : "<@i18n.messageText code='system.configuration.management.form.confirm.detailed.length'  text='详细说明长度不能超过128' />"
					}
				}
			});

			jQuery.validator.addMethod("ajaxCheck", function (value, element) {
		        var roleName = $("#roleName").val();
		        var compId = $("#compId").val();
		        var id = $("[name='id']").val();
		        var bool = true;
	        	$.ajax({
					   type: "POST",
					   url: "/admin/role/validateRoleName",
					   data: {"roleName":roleName,"compId":compId,"id":id},
					   async:false,//必须ajax执行完才返回值
					   success: function(json){
							if (json.data == "y")//存在yes
								bool = false;
							else
								bool = true;//不存在no
					   }
					});
	        	return bool;
		    }, "<@i18n.messageText code='role.management.text.enterprise.role.already.exist'  text='同企业下，该角色已存在！' />");
		});
		
		
		$(".selectInfo").click(function(){
// 			var newWindow = window.open ('/admin/company/getOneCompanyQueryPage', 'select_top', 'height=650, width=900, top=100px, left=100px, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
			var toolTip = new Layer({
				layerclass:"layerContianer2",//存放页面的容器类名
		        width:900,
		        height:650,
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
// 			alert(compId+"--"+compName);
			$("#roleName").focus();
		}
	</script>
</head>
<body>

<div class="main-container">
    <div class="tabContainer">
        
            <form action="/admin/role/save" method="post" id="form_post" name="form_post">
            	<input type="hidden" value="${role.id }"  name="id" id="id">
	                <ul class="details_box">
	                    <li><span> <@i18n.messageText code='user.management.text.affiliated.companies'  text='所属企业' /> </span>
				   			<input type="text" disabled="disabled"  id="compName" class="inp_detail" name="compName" value="${role.compName }">
				   			<input type="hidden" id="compId" class="inp_detail" name="compId" value="${role.compId }">
				   			<#if superUser == 'yes' && role.id == ''><input type="button" value="<@i18n.messageText code='role.management.button.please.select'  text='请选择' />" class="selectInfo fbtn btn_xs btn_primary" ></#if>
				   		</li>
	                    <li><span> <@i18n.messageText code='role.management.text.role.name'  text='角色名称' /> <font color="red">*</font></span><input type="text" class="inp_detail" name="roleName" id="roleName" value="${role.roleName }"></li>
	                    <li><span> <@i18n.messageText code='role.management.text.detailed.description'  text='详细说明' /> </span><input type="text" class="inp_detail" name="intro" id="intro" value="${role.intro }"></li>
	                    <li class="class_btn_li" style="margin-top:20px !important">
	                    	<input type="submit" class="saveRoleId fbtn btn_sm ensure_btn " id="saveRoleId" value="<@i18n.messageText code='common.button.quit.true'  text='确定' />" >
	                    	<input type="button" class="cancel fbtn btn_sm cancle_btn" value="<@i18n.messageText code='common.button.quit.false'  text='关闭' />" onclick="javascript:delDialog()" >
	                    </li>
	                </ul>
	            
			</form>
       
    </div>
</div>
</body>
</html>