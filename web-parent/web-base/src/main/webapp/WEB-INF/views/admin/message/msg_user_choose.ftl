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
			zTree.checkAllNodes(false); //取消所有默认勾选
		}
		
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo_dept"), setting, zNodes);
			setCheck();
			$("#py").bind("change", setCheck);
			$("#sy").bind("change", setCheck);
			$("#pn").bind("change", setCheck);
			$("#sn").bind("change", setCheck);
		});
		$("#selectInfo").click(function(){
			var zTree = $.fn.zTree.getZTreeObj("treeDemo_dept");
			var nodes = zTree.getCheckedNodes(true); 
			var users="";
			var userIds="";
			for (var i = 0; i < nodes.length; i++) {  
				users+=nodes[i].name+",";
				userIds+=nodes[i].id+",";
			}
       		if(users == '' || users == undefined || users == "undefined"){
       			alert("<@i18n.messageText code='message.management.text.select.user'  text='请选择一个用户' />");
       		}else{
       			getInfoInput(users,userIds);
	       		var toolTip3 = new Layer();
	       		toolTip3.delDialog("#cancelClose");
       		}
    	});
       	$("#cancelClose").click(function(){
       		var toolTip3 = new Layer();
       		toolTip3.delDialog("#cancelClose");
    	});
	</script>
    
</head>
<body>
				<div id="tabs-body" class="tabs-body">
				    <div style="display:block">
				    <form action="/admin/user/saveUserDept"  method="post" id="form_post" name="form_post" class="ztree_form">
						<input type="hidden" name="deptIds" id="deptIds"/>
					   	<div class="ztreeDemo_exBackground left">
							<ul id="treeDemo_dept" class="ztree"></ul>
							 <div class="sp_btnC cw_dialogB" style="text-align:center">
              					<input type="button" class="selectInfo fbtn btn_sm ensure_btn" value="<@i18n.messageText code='common.button.quit.true'  text='确定' />" id="selectInfo">
              					<input type="button" class="cancelClose fbtn btn_sm cancle_btn" value="<@i18n.messageText code='common.button.quit.false'  text='关闭' />"  id="cancelClose">
        					</div>
						</div>
				  	</form>
				    </div>
				</div>
</body>
</html>