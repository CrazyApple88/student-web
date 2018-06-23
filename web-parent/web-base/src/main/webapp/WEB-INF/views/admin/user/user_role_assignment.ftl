<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>用户角色分配页面</title>
    <#import "spring.ftl" as i18n/>
    <script type="text/javascript">
    $(function(){
    	$("#saveUserRoleId").click(function(){
    		var toolTip = new diaPrompt();
    		var options = {
    	        success: function (json) {
    	        	if(json.code == 1){
    	        		toolTip.tips({
							timer:true,/*是否开启定时器*/
				            sec:2,/*倒计时的秒数*/
    	    	            message:"<@i18n.messageText code='user.management.text.role.allocation.success'  text='角色分配成功！' />",
	    	    	        callback:function(){}
    	        		});
    	        		delDialog();
    	        	}else{
    	        		toolTip.error({
							timer:true,/*是否开启定时器*/
				            sec:2,/*倒计时的秒数*/
    	    	            message:"<@i18n.messageText code='user.management.text.role.assignment.failed'  text='角色分配失败！' />",
	    	    	        callback:function(){}
    	        		});
    	        	}
    	        }
    		};
    		var trLength = $("#roleTableId tr").length;
    		if(trLength == 0){
    			toolTip.warn({
    	            message:"<@i18n.messageText code='system.management.text.currently.no.role.assigned.add.new.role'  text='当前没有角色可以分配，请先新增角色！' />",
	    	        callback:function(){
	    	        	$(".overlay").remove();
	    	        }
        		});
    		}else{
        		$("#form_post").ajaxForm(options);
        		$("#form_post").submit(); 
    		}
    		
    	});
    	
    });
    </script>
    
</head>
<body class="tabContainer">

<div>
    <div class="tabContainer" style="margin:20px 20px 0;">
            <form action="/admin/user/saveUserRole" method="post" id="form_post" name="form_post">
            	<input type="hidden" value="${user.id }"  name="id" id="id">
	            <table class="table table-bordered table_allot" id="roleTableId">
	                	<#list list as role>
		                	<tr>
		                		<td>
		                			<input type="checkbox" class="allot_checkbox" name="roleIds" value="${role.id}" <#if role.selected> checked="checked" </#if>> ${role.roleName}
		                		</td>
			                </tr>
			            <#else>
			            <div align="center">
			            	<img src='../../assets/images/load.png' class='no_data'/> <@i18n.messageText code='common.text.no.record'  text='查询无记录' /> 
			            </div>
		                </#list>
	            </table>
			</form>

    </div>
    <div class="pageContainer" style="margin-bottom:0;text-align:center;">
		 <input type="button" class="saveUserRoleId fbtn btn_sm ensure_btn" id="saveUserRoleId" value="<@i18n.messageText code='common.button.quit.true'  text='确定' />">
		 <input type="button" class="cancel fbtn btn_sm cancle_btn" value="<@i18n.messageText code='common.button.quit.false'  text='关闭' />" onclick="javascript:delDialog()">
	</div>
</div>

</body>
</html>