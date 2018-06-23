<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>菜单权限授权分配</title>
    <#import "spring.ftl" as i18n/>
    <style type="text/css">
    	.tabs {
		    float:left;
		    background-image: url(/assets/images/manage_r2_c13.png);
		    width:100%;
		}
		.tabs ul 
		{
		    list-style: none outside none;
		    margin: 0;
		    padding: 0;
		}
		.tabs ul li
		{
		    float: left;
		    line-height: 24px;
		    margin: 0;
		    padding: 2px 20px 0 15px;
		}
		.tab-nav{
		     background: url(/assets/images/manage_r2_c13.png) no-repeat;
		     cursor:pointer;
		}
		.tab-nav-action{
		    background: url(/assets/images/manage_r2_c15.png) no-repeat;
		    cursor:pointer;
		}
		.tabs-body
		{
		    border-bottom: 1px solid #B4C9C6;
		    border-left: 1px solid #B4C9C6;
		    border-right: 1px solid #B4C9C6;
		    float: left;
		    padding: 5px 0 0;
		    width: 100%;
		}
		.form_box li{
    		height:45px !important;
    		margin-top: 6px !important;
    	}
    </style>
   
    <#include "/system/common_js.ftl">
	<@configjQueryzTreeJs />
    <SCRIPT type="text/javascript">
	var toolTip = new diaPrompt();
    $(function($) {
		$(".selectInfo").click(function(){
			var selectType = $('input:radio[name="centerType"]:checked').val(); 
			if(selectType == '1'){
				var newWindow = window.open ('/admin/user/getOneUserQueryPage', 'select_top', 'height=650, width=900, top=100px, left=100px, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
			}else if(selectType == '2'){
				var newWindow = window.open ('/admin/role/getOneRoleQueryPage', 'select_top', 'height=650, width=900, top=100px, left=100px, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
			}else if(selectType == '3'){
				var newWindow = window.open ('/admin/dept/getOneDeptQueryPage', 'select_top', 'height=650, width=900, top=100px, left=100px, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
			}else{
				toolTip.error({
					timer:true,/*是否开启定时器*/
		            sec:2,/*倒计时的秒数*/
					message:"<@i18n.messageText code='menu.management.text.select.authorized.object.category'  text='请选择授权对象类别！！' />",
					callback:function(){}
				});
			}
		});
		
		//访问权限保存方法
		$("#saveMenuAccess").click(function(){
			var zTree = $.fn.zTree.getZTreeObj("treeDemo_menuAccess");
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
			var relationType = $("#nowSelectType").val();//关联表类型：1用户，2角色，3部门
			var relationId = $("#nowSelectId").val();//关联表ID
			var authType = "1";//权限类型：1访问类型，2授权类型
			saveMenuCenters(ids,relationId,relationType,authType);
		});
		//授权权限保存方法
		$("#saveMenuAuth").click(function(){
			var zTree = $.fn.zTree.getZTreeObj("treeDemo_menuAuth");
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
			var relationType = $("#nowSelectType").val();//关联表类型：1用户，2角色，3部门
			var relationId = $("#nowSelectId").val();//关联表ID
			var authType = "2";//权限类型：1访问类型，2授权类型
			saveMenuCenters(ids,relationId,relationType,authType);
		});
    });
		$("#closeLayer").click(function(){
   			var toolTip3 = new Layer();
   			toolTip3.delDialog("#closeLayer");
		});
    //保存访问或授权权限
    function saveMenuCenters(menuIds,relationId,relationType,authType){
    	$.ajax({
			type: "POST",
			url: "/admin/menu/saveMenuCenters",
			data: {"menuId":menuIds,"relationId":relationId,"relationType":relationType,"authType":authType},
			async:false,//必须ajax执行完才返回值
			success: function(json){
				if(json.code == 1){
					var authName = "<@i18n.messageText code='menu.management.text.save.allocation.information.success'  text='保存分配信息成功！' />";
					if(authType == "1"){
						authName = "<@i18n.messageText code='menu.management.text.save.access.success'  text='保存访问权限成功！' />";
					}if(authType == "2"){
						authName = "<@i18n.messageText code='menu.management.text.save.authorization.privileges'  text='保存授权权限成功！' />";
					}
					toolTip.tips({
						timer:true,/*是否开启定时器*/
			            sec:2,/*倒计时的秒数*/
	    	            message:authName,
   	    	         	callback:function(){}
	        		});
// 					alert("保存成功！");
					var toolTip3 = new Layer();
		   			toolTip3.delDialog("#closeLayer");
				}else{
					toolTip.error({
						timer:true,/*是否开启定时器*/
			            sec:2,/*倒计时的秒数*/
	    	            message:"<@i18n.messageText code='menu.management.text.failed.save.allocation'  text='保存分配失败！' />",
   	    	         	callback:function(){}
	        		});
// 					alert("部门分配失败！");
				}
		   }
		});
    }
    
    //获取权限树
    function getMenuTree(id){
    	var selectType = $('input:radio[name="centerType"]:checked').val(); 
    	$.ajax({
			   type: "POST",
			   url: "/admin/menu/getMenuTreeByCenterId",
			   data: {"selectId":id,"selectType":selectType},
			   success: function(json){
			     if(json.code == 1){
			    	 createTree(json.data.listAccess,"treeDemo_menuAccess");
			    	 createTree(json.data.listAuth,"treeDemo_menuAuth");
			     }else{
			    	 toolTip.error({
							timer:true,/*是否开启定时器*/
				            sec:2,/*倒计时的秒数*/
		    	            message:"<@i18n.messageText code='menu.management.text.get.permission.assign.information.failed'  text='获取权限分配信息失败！' />",
	   	    	         	callback:function(){}
		        		});
			     }
			   }
			});
    }
    
    function createTree(jsonTree,treeId){
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
    		var zNodes =jsonTree;
    		$(document).ready(function(){
    			$.fn.zTree.init($("#"+treeId), setting, zNodes);
    			setCheck(treeId);
    			$("#py").bind("change", setCheck);
    			$("#sy").bind("change", setCheck);
    			$("#pn").bind("change", setCheck);
    			$("#sn").bind("change", setCheck);
    		});
    }
    
    function setCheck(treeId) {
		var zTree = $.fn.zTree.getZTreeObj(treeId),
		py = "p",
		sy = "s",
		pn = "",
		sn = "s",
		type = { "Y":py + sy, "N":pn + sn};
		zTree.setting.check.chkboxType = type;
	}
    
    $(document).ready(function () {
        $("#tabs li").bind("click", function () {
            var index = $(this).index();
            var divs = $("#tabs-body > div");
            $(this).parent().children("li").attr("class", "tab-nav");//将所有选项置为未选中
            $(this).attr("class", "tab-nav-action"); //设置当前选中项为选中样式
            divs.hide();//隐藏所有选中项内容
            divs.eq(index).show(); //显示选中项对应内容
        });
 		var relationId = '${relationId}';
 		if(relationId != '' && relationId != undefined && relationId != 'undefined'){
 			getMenuTree(relationId);
 		}
    });
	</SCRIPT>
    
</head>
<body>
	
	<div class="main-container" style="" align="center">
		<ul class="main_ul">
			<li>
				<span> <@i18n.messageText code='menu.management.text.authorized.object.category'  text='授权对象类别:' /> </span>
				<span>
					<#if relationType == 1>
			   		<input type="radio" style="width:15px;height:15px;" name="centerType" value="1" <#if relationType == 1> checked </#if>>
			   		<@i18n.messageText code='menu.management.text.user'  text='用户' /> 
			   		</#if>
			   		<#if relationType == 2>

			   		<input type="radio" style="width:15px;height:15px;" name="centerType" value="2" <#if relationType == 2> checked </#if>>
			   		<@i18n.messageText code='menu.management.text.role'  text='角色' /> 
			   		</#if>
			   		<#if relationType == 3>

			   		<input type="radio" style="width:15px;height:15px;" name="centerType" value="3" <#if relationType == 3> checked </#if>>
			   		<@i18n.messageText code='menu.management.text.department'  text='部门' /> 
	   				</#if>
	   			</span>
			</li>
			<li>
				<span> <@i18n.messageText code='menu.management.text.authorized.object'  text='授权对象:' /> </span>
				<span>
<!-- 		   			<input type="text" class="accredit_obj" disabled="disabled" id="nowSelectName" name="nowSelectName" value="${relationName }"> -->
		   			${relationName }
		   			<input type="hidden" id="nowSelectId" name="nowSelectId" value="${relationId }">
		   			<input type="hidden" id="nowSelectType" name="nowSelectType" value="${relationType }">
<!-- 		   			<input type="button" value="请选择" class="selectInfo fbtn btn_xs btn_primary"> -->
	   			</span>
			</li>
			<li>
				<div class="tabs">
				    <ul id="tabs">
				        <li class="tab-nav-action"> <@i18n.messageText code='menu.management.text.access.permission'  text='访问权限' /> </li>
					    <li class="tab-nav"> <@i18n.messageText code='menu.management.text.authorization.privileges'  text='授权权限' /> </li>
				    </ul>
				</div>
				<div id="tabs-body" class="tabs-body">
					    <div style="display:block">
						   	<div class="ztreeDemo_exBackground left">
								<ul id="treeDemo_menuAccess" class="ztree"></ul>
							  	<div class="sp_btnC cw_dialogB" style="height:50px">
							      <ul style="height:50px;">
							      	  <li class="class_btn_li">
							          	<input type="button" class="saveMenuAccess fbtn6 btn_sm ensure_btn" id="saveMenuAccess" value="<@i18n.messageText code='menu.management.text.save.access'  text='保存访问权限' />">
								      	<input type="button" id="closeLayer" class="cancel fbtn btn_sm cancle_btn" value="<@i18n.messageText code='common.button.quit.false'  text='关闭' />">
								      </li>
							      </ul>
								</div>
							</div>
					    </div>
					    <div style="display:none">
					    	<div class="ztreeDemo_exBackground left">
								<ul id="treeDemo_menuAuth" class="ztree"></ul>
						  	<div class="sp_btnC cw_dialogB" style="height:50px;">
						      <ul style="height:50px;">
						      	  <li class="class_btn_li">
						          	<input type="button" class="saveMenuAuth fbtn6 btn_sm ensure_btn" id="saveMenuAuth" value="<@i18n.messageText code='menu.management.text.save.access'  text='保存访问权限' />" >
							      	<input type="button" class="cancel fbtn btn_sm cancle_btn" value="<@i18n.messageText code='common.button.quit.false'  text='关闭' />" onclick="javascript:delDialog()">
							      </li>
						      </ul>
							</div>
							</div>
					    </div>
					</div>
			</li>
		</ul>
	   	
  	</div>

</body>
</html>