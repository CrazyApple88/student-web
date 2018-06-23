<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>用户管理--部门分配</title>
    
    <#include "/system/common_css.ftl">
    <#include "/system/common_js.ftl">
	<@configjQueryzTreeJs />
    <SCRIPT type="text/javascript">
    var setting = {
			check: {
				enable: true,
				chkStyle: "radio",
				radioType: "all"
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};

		var zNodes =${jsonTree};
		
		var code;		
		function setCheck() {
			$.fn.zTree.init($("#treeDemo_dept"), setting, zNodes);
		}
		$(document).ready(function(){
			setCheck();			
		});
		
		$(function(){
			var toolTip = new diaPrompt();
			var h = $(window).height() - 100 ;
			$('.main-container').css("height",h);
			$("#selectDeptInfo").click(function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo_dept");
				var nodes = zTree.getCheckedNodes();
				if(nodes == '' || nodes == null){
					toolTip.warn({
						timer:true,/*是否开启定时器*/
			            sec:2,/*倒计时的秒数*/
						message:"<@i18n.messageText code='business.management.text.save.failed'  text='保存失败!' />",
						callback:function(){}
					});
				}else{
					window.opener.document.getElementById('nowSelectId').value=nodes[0].id;
		       		window.opener.document.getElementById('nowSelectName').value=nodes[0].name;
		       		window.opener.document.getElementById('nowSelectType').value="3";//当前选择的授权类别
		       		window.opener.getMenuTree(nodes[0].id);
		       		window.close();
				}
	    	});
	    	
	    });
	</SCRIPT>
    
</head>
<body>
	<div class="main-container" style="" align="center">
	   	<table class="table table-bordered" border="1" style="width:500px;">
	   		<tr>
	   			<td >
	   				<form action="/admin/user/saveUserDept"  method="post" id="form_post" name="form_post">
					   	<div class="ztreeDemo_exBackground left">
							<ul id="treeDemo_dept" class="ztree"></ul>
						</div>
				  	</form>
	   			</td>
	   		</tr>
	   		<tr>
		   		<td>
			   		<input type="button" class="selectDeptInfo fbtn btn_sm ensure_btn" id="selectDeptInfo" value="<@i18n.messageText code='common.button.quit.true'  text='确定' />" style="height:30px;line-height:30px;margin-top:20px;text-align:center;">
	      			<input type="button" class="cancel fbtn btn_sm cancle_btn" value="<@i18n.messageText code='common.button.quit.false'  text='关闭' />"  onclick="javascript:window.close()" style="height:30px;line-height:30px;margin-top:20px;text-align:center;">
		   		</td>
	   		</tr>
	   	</table>
	</div>
</body>
</html>