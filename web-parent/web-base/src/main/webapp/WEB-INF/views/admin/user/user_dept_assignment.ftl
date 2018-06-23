<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>用户部门分配页面</title>
    <#include "/system/common_js.ftl">
    <@configjQueryzTreeJs />
    <style type="text/css">
    .tabs-body
		{
		    border-bottom: 1px solid #B4C9C6;
		    border-left: 1px solid #B4C9C6;
		    border-right: 1px solid #B4C9C6;
		    float: left;
		    padding: 5px 0 0;
		    width: 100%;
		}
	</style>
	
	
    <script type="text/javascript">
		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};
		//树型数据（后台获取
		var zNodes =${jsonTree};
		
		function setCheck() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo_dept"),
			py = "",
			sy = "",
			pn = "",
			sn = "",
			type = { "Y":py + sy, "N":pn + sn};
			zTree.setting.check.chkboxType = type;
		}
		
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo_dept"), setting, zNodes);
			setCheck();
			$("#py").bind("change", setCheck);
			$("#sy").bind("change", setCheck);
			$("#pn").bind("change", setCheck);
			$("#sn").bind("change", setCheck);
		});
		
		$(function(){
	    	$("#saveUserDeptId").click(function(){
	    		var toolTip = new diaPrompt();
	    		var options = {
	    	        success: function (json) {
	    	        	if(json.code == 1){
	    	        		toolTip.tips({
								timer:true,/*是否开启定时器*/
					            sec:2,/*倒计时的秒数*/
	    	    	            message:"<@i18n.messageText code='user.management.text.department.allocation.success'  text='部门分配成功！' />",
		    	    	        callback:function(){}
	    	        		});
// 	    	        		alert("部门分配成功！");
	    	        		delDialog();
	    	        	}else{
// 	    	        		alert("部门分配失败！");
	    	        		toolTip.error({
								timer:true,/*是否开启定时器*/
					            sec:2,/*倒计时的秒数*/
	    	    	            message:"<@i18n.messageText code='user.management.text.department.allocation.failed'  text='部门分配失败！' />",
		    	    	        callback:function(){}
	    	        		});
	    	        	}
	    	        }
	    		};
	    		var zTree = $.fn.zTree.getZTreeObj("treeDemo_dept");
	    		var nodes = zTree.getCheckedNodes();
				var ids = "";
				for(var i = 0; i < nodes.length;i++){
					var node = nodes[i];
// 					if(!node.isParent){
						ids += ","+node.id;
// 					}
				}
				if(ids.length>0){
					ids = ids.substring(1);
				}
				$("#deptIds").val(ids);
// 				alert(ids);
	    		$("#form_post").ajaxForm(options);
	    		$("#form_post").submit(); 
	    		
	    	});
	    	
	    });
	</script>
    
</head>
<body>
	<div class="main-container" align="center">
		<ul class="main_ul">
			<li>
				<span> <@i18n.messageText code='user.management.text.username'  text='用户名称:' /> </span>
				<span>
					${user.userName }
				</span>
			</li>
			<li>
				<div id="tabs-body" class="tabs-body">
				    <div style="display:block">
				    <form action="/admin/user/saveUserDept"  method="post" id="form_post" name="form_post" class="ztree_form">
						<input type="hidden" name="deptIds" id="deptIds"/>
			 			<input type="hidden" value="${user.id }"  name="id" id="id">
					   	<div class="ztreeDemo_exBackground left">
							<ul id="treeDemo_dept" class="ztree"></ul>
							<div class="sp_btnC cw_dialogB">
						      <ul style="height:50px;margin-top:20px;">
						      	  <li class="class_btn_li">
						          		<input type="button" class="saveUserDeptId fbtn btn_sm ensure_btn" id="saveUserDeptId" value="<@i18n.messageText code='common.button.quit.true'  text='确定' />">
    									<input type="button" class="cancel fbtn btn_sm cancle_btn" value="<@i18n.messageText code='common.button.quit.false'  text='关闭' />"  onclick="javascript:delDialog()">
							      </li>
						      </ul>
							</div>
						</div>
				  	</form>
				    </div>
				</div>
			</li>
		</ul>
  	</div>
</body>
</html>