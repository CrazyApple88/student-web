<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>用户角色分配页面</title>
    <#include "/system/common_js.ftl">
    <@multiselectPlugJs />
    <script type="text/javascript">
    $(function(){
    	$('.multiselect').multiselect();
    	
    	$("#saveRoleUserId").click(function(){
    		var toolTip = new diaPrompt();
    		var selectUser = "";

			var o = document.getElementById("multiselect_to_1");
			for(var i = 0; i < o.length; i++){     
				if(i == 0){
					selectUser += o.options[i].value;
				}else{
			        selectUser += "," + o.options[i].value;  
				}
		    }  
			$("#selectUserIds").val(selectUser);
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
    		$("#form_post").ajaxForm(options);
    		$("#form_post").submit(); 
    		
    	});
    	
    });
    </script>
    
</head>
<body class="tabContainer" style="">

    <div class="tabContainer" style="margin:20px 20px 0;">
            <form action="/admin/role/saveRoleUser" method="post" id="form_post" name="form_post" style="display:inherit">
            	<input type="hidden" id="selectUserIds"  name="selectUserIds" value="">
            	<input type="hidden" id="id"  name="id" value="${role.id }">
				   			
            	<div class="row" style="margin-right:-100px;margin-left:20px;">
				    <div class="col-xs-4">
				    	<@i18n.messageText code='system.management.text.unselected.users'  text='未选用户' />
				        <select name="notSelectUser" class="multiselect form-control" size="15" multiple="multiple" data-right="#multiselect_to_1" data-right-all="#right_All_1" data-right-selected="#right_Selected_1" data-left-all="#left_All_1" data-left-selected="#left_Selected_1">
				            <#list listUserNotSelect as user>
				            	<option value="${user.id }">${user.userName } (${user.realName })</option>
				            </#list>
				        </select>
				    </div>
				    
				    <div class="col-xs-2" style="margin-top:80px">
				        <button type="button" id="right_All_1" class="btn btn-block"> >> </button>
				        <button type="button" id="right_Selected_1" class="btn btn-block">></button>
				        <button type="button" id="left_Selected_1" class="btn btn-block"> < </button>
				        <button type="button" id="left_All_1" class="btn btn-block"> << </button>
				    </div>
				    
				    <div class="col-xs-4">
				    	<@i18n.messageText code='system.management.text.selected.users'  text='已选用户' />
				        <select name="selectUser" id="multiselect_to_1" class="form-control selectUser" size="15" multiple="multiple">
				        	<#list listUserSelect as user>
				            	<option value="${user.id }">${user.userName } (${user.realName })</option>
				            </#list>
				        </select>
				    </div>
				</div>
				<div class="Container" style="margin-bottom:0;text-align:center;margin-top:20px">
					 <input type="button" class="saveRoleUserId fbtn btn_sm ensure_btn" id="saveRoleUserId" value="<@i18n.messageText code='common.button.quit.true'  text='确定' />">
					 <input type="button" class="cancel fbtn btn_sm cancle_btn" value="<@i18n.messageText code='common.button.quit.false'  text='关闭' />" onclick="javascript:delDialogNoRefresh()">
				</div>
			</form>

    </div>

</body>
</html>