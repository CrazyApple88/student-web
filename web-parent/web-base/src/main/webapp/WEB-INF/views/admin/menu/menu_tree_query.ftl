<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>用户部门分配页面</title>
    <style type="text/css">
		.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
	</style>
    <#include "/system/common_css.ftl">
    <#include "/system/common_js.ftl">
	<@configjQueryzTreeJs />
	
	<SCRIPT type="text/javascript">
		var zTree ;
		var treeNodeEdit;
		var op_ex = 1;
		var setting = {
			view: {
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom,
				selectedMulti: false
			},
			edit: {
				enable: true,
				editNameSelectAll: true,
				showRemoveBtn: showRemoveBtn,
				showRenameBtn: false,//不显示编辑按钮
				removeTitle : "<@i18n.messageText code='common.button.delete'  text='删除' />"
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: zTreeOnClick,
				beforeRemove: beforeRemove,
				beforeDrag: beforeDrag,
				beforeDrop: beforeDrop 
			}
		};
		
	    var log, className = "dark";  
	    function beforeDrag(treeId, treeNodes) {  
	        for (var i=0,l=treeNodes.length; i<l; i++) {  
	               dragId = treeNodes[i].pId;  
	             if (treeNodes[i].drag === false) {  
	                  return false;  
	            }  
	       }  
	        return true;  
	 }  
	  //拖拽释放之后执行  
	 function beforeDrop(treeId, treeNodes, targetNode, moveType) {  
		if(targetNode.pId == dragId){  
	    	var confirmVal = false;  
	    	if(moveType == "inner"){
	    		alert("<@i18n.messageText code='system.management.text.move.forward.backward'  text='只能向前或向后移动！' />");
	    	}else{
	            $.ajax({  
	                 async: false,  
	                 type: "post",  
	                 data: {
	                	 id:treeNodes[0].id,
	                	 targetId:targetNode.id,
	                	 moveType:moveType
	                 },
	                 url: "/admin/menu/updateSort",
	                 success: function(json){  
	                	 if(json.code == 1){
	                            confirmVal = true;   
		                 }else{  
		                	 alert("<@i18n.messageText code='common.text.operation.failed'  text='操作失败！' />");  
		                 }  
		             },  
		             error: function(){  
		            	 alert("<@i18n.messageText code='system.management.text.abnormal.network'  text='网络异常！' />");  
		             }  
		        });  
	    	}
	        return confirmVal;  
	  	} else{  
	  		alert("<@i18n.messageText code='system.management.text.sibling.sort'  text='只能进行同级排序！' />");    
	        return false;  
	    }  
	 }  
		function beforeRemove(treeId, treeNode) {
			var toolTip = new diaPrompt();
			toolTip.warn({
	            message:"<@i18n.messageText code='process.definition.management.confirm.delete.cannot.restored'  text='确认要删除，删除后不可恢复！' />",
	            callback:function(){
					$.ajax({
						type: "POST",
					   url: "/admin/menu/delete",
					   data: "id="+treeNode.id,
					   async:false,//必须ajax执行完才返回值
					   success: function(json){
					     if(json.code == 1){
					    	zTree.removeNode(treeNode);
					    	$("#tabledata").html('');
					     }else{
					    	toolTip.error({
								timer:true,/*是否开启定时器*/
					            sec:2,/*倒计时的秒数*/
					    		message:"<@i18n.messageText code='menu.management.text.query.menu.information.failed'  text='查询菜单信息失败！' />",
					    		callback:function(){}
					    	});
					     }
					   }
					});
	            }
	        });
			return false;
		}

		var newCount = 1;
		function addHoverDom(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#addBtn_"+treeNode.id).length>0) return;
			<@loginUserAuth key='menu_add' ; value>
				<#if value =='1'>
				var addStr = "<span class='button add' id='addBtn_" + treeNode.id
					+ "' title='<@i18n.messageText code='common.button.append'  text='新增' />' onfocus='this.blur();'></span>";
				sObj.after(addStr);
				</#if>
            </@loginUserAuth>
			var btn = $("#addBtn_"+treeNode.id);
			if (btn) btn.bind("click", function(){
				add_menu(treeNode);
				return false;
			});
		};
		function removeHoverDom(treeId, treeNode) {
			$("#addBtn_"+treeNode.id).unbind().remove();
		};
		//是否显示菜单右边的删除按钮
		function showRemoveBtn(treeId, treeNode) {
			<@loginUserAuth key='menu_del' ; value>
				<#if value =='1'>
					if(op_ex != 1){
						return false;
					}
					return !treeNode.isParent;
				</#if>
            </@loginUserAuth>
            return false;
		}
		
		
		//页面加载启动
		$(document).ready(function(){
			loadTree();
		});
		//加载树信息
		function loadTree(){
			var zNodes = [];
			$.ajax({
				type: "POST",
				url: "/admin/menu/getMenuTreeQuery",
				data: {},
				success: function(json){
					if(json.code == 1){
						zNodes = json.data;
						$.fn.zTree.init($("#treeDemo"), setting, zNodes);
						zTree = $.fn.zTree.getZTreeObj("treeDemo");
					}else{
						toolTip.error({
							timer:true,/*是否开启定时器*/
				            sec:2,/*倒计时的秒数*/
							message:"<@i18n.messageText code='menu.management.text.query.menu.information.failed'  text='查询菜单信息失败！' />",
							callback:function(){}
						});
					}
			   }
			});
		}
		//点击数菜单触发事件
		function zTreeOnClick(event, treeId, treeNode) {
			treeNodeEdit =  treeNode;
			load_menu_info(treeNode.id);//加载右侧列表
		};
		
		//加载右侧方法
		function load_menu_info(id){
			$("#tabledata").load('/admin/menu/getMenuEditPage?id='+id,{},function(){});
			
		}
		//点击菜单右侧新增按钮子菜单触发事件
		function add_menu(treeNode){
			treeNodeEdit = treeNode;
			$("#tabledata").load('/admin/menu/getMenuEditPage?parentId='+treeNode.id,{},function(){});
		}
		
		//新增成功后需要触发的事件
		function add_menu_success(id,parentId,menuName,type){
			if(zTree == null){
				zTree = $.fn.zTree.getZTreeObj("treeDemo");
			}
			if(type == 1){//新增节点
				zTree.addNodes(treeNodeEdit, {id:id, pId:parentId, name:menuName});
			}else{//更新节点信息
				treeNodeEdit.id = id;
				treeNodeEdit.name = menuName;
				treeNodeEdit.pId = parentId;
				zTree.updateNode(treeNodeEdit);
			}
		}
	</SCRIPT>
	<script type="text/javascript">

	$(document).ready(function(){
		set_window_size();
	});	


	window.onresize = function(e) {
		set_window_size();
	};
	
	
	function set_window_size(){
		var clientHeigth = $(window).height();
		var iframe_width = $(window).width();
		var moveCont = $(".nano").width();
		$(".xhn_Mleft").css("height",parseInt(clientHeigth)-25);
		$("#tabledata").css("height",parseInt(clientHeigth));
		$("#tabledata").css("width",parseInt(iframe_width) - moveCont - 100);
// 		$(".xhn_Mleft").css("overflow-y","scroll");
	}
	
	</script>
</head>
<body>
  <div class="xhn_main" style="height:100%">
		  <div id="about" class="nano" style="width:260px;height:100%;">
			  <div class="nano-content">
					<div class="ztree_box">
						<input type="hidden" id="id">
						<ul id="treeDemo" class="ztree"></ul>
					</div>
			   </div>
			   <div class="drag_slide"></div>
		  </div>
		<div id="tabledata" style="float:left;">
			<!-- 加载用户列表 -->
			<div class="cw_jiazai" style="" align="center">
				<@i18n.messageText code='menu.management.text.click.menu.name.left'  text='请点击左侧菜单名称，查看相应属性' />
			</div>
		</div>
	</div>
</body>
</html>