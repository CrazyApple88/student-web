<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>用户部门信息页面</title>
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
				beforeDrag: beforeDrag,
				beforeRemove: beforeRemove,
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
		                 url: "${context}/admin/userDept/updateSort",
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
			var toolTipTree = new diaPrompt();
			toolTipTree.warn({
	            message:"<@i18n.messageText code='process.definition.management.confirm.delete.cannot.restored'  text='确认要删除，删除后不可恢复！' />",
	            callback:function(){
					$.ajax({
						type: "POST",
					   url: "${context}/admin/userDept/delete",
					   data: "id="+treeNode.id,
					   async:false,//必须ajax执行完才返回值
					   success: function(json){
					     if(json.code == 1){
					    	zTree.removeNode(treeNode);
					    	$("#tabledata").html('');
					     }else{
					    	 toolTipTree.error({
								timer:true,/*是否开启定时器*/
					            sec:2,/*倒计时的秒数*/
					    		message:json.msg,
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
				<@loginUserAuth key='dept_add' ; value>
					<#if value =='1'>
						var addStr = "<span class='button add' id='addBtn_" + treeNode.id
							+ "' title=' <@i18n.messageText code='common.button.append'  text='新增' /> ' onfocus='this.blur();'></span>";
						sObj.append(addStr);
					</#if>
        		</@loginUserAuth> 
        		<@loginUserAuth key='dept_edit' ; value>
					<#if value =='1'>
						var editStr = "<span class='button edit' id='editBtn_" + treeNode.id
							+ "' title=' <@i18n.messageText code='common.button.edit'  text='编辑' /> ' onfocus='this.blur();'></span>";
						sObj.append(editStr);
					</#if>
    			</@loginUserAuth>
			var btn = $("#addBtn_"+treeNode.id);
			if (btn) btn.bind("click", function(){
				add_dept(treeNode);
				return false;
			});
			var btn2 = $("#editBtn_"+treeNode.id);
			if (btn2) btn2.bind("click", function(){
				edit_dept(treeNode);
				return false;
			});
		};
		function removeHoverDom(treeId, treeNode) {
			$("#addBtn_"+treeNode.id).unbind().remove();
			$("#editBtn_"+treeNode.id).unbind().remove();
		};
		//是否显示部门右边的删除按钮
		function showRemoveBtn(treeId, treeNode) {
			<@loginUserAuth key='dept_del' ; value>
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
			var toolTip = new diaPrompt();
			var zNodes = [];
			$.ajax({
				type: "POST",
				url: "${context}/admin/userDept/getDeptTreeQuery",
				data: {},
				success: function(json){
					if(json.code == 1){
						zNodes = json.data;
						$.fn.zTree.init($("#treeDemo"), setting, zNodes);
						zTree = $.fn.zTree.getZTreeObj("treeDemo");
					}else{
						toolTip.error({
							message:"<@i18n.messageText code='department.management.text.query.department.information.failed'  text='查询部门信息失败！' />",
							callback:function(){}
						});
					}
			   }
			});
		}
		 var saveToolTip;//打开的编辑图层
		//点击树部门触发事件
		function zTreeOnClick(event, treeId, treeNode) {
			 console.log("-----"+treeNode);
			treeNodeEdit =  treeNode;
			load_dept_info(treeNode.id);//加载右侧列表
		};
		
		//加载右侧方法
		function load_dept_info(id){
			$("#tabledata").load('${context}/admin/userDept/getUserQueryPage?id='+id,{},function(){});
		}
		//点击部门右侧新增按钮子部门触发事件
		function add_dept(treeNode){
			treeNodeEdit = treeNode;
			saveToolTip = new UDP.Layer({
                layerclass:"layerContianerAdd",//存放页面的容器类名
                width:750,
                height:520,
                alerttit:"新增",
                callback:function(){
                    $(".layerContianerAdd").load('${context}/admin/userDept/getDeptEditPage?parentId='+treeNode.id,{},function(){});
                }
            });
            saveToolTip.show();
		}
		
		//点击部门右侧编辑按钮子部门触发事件
		function edit_dept(treeNode){
			treeNodeEdit = treeNode;
			saveToolTip = new UDP.Layer({
                layerclass:"layerContianerEdit",//存放页面的容器类名
                width:750,
                height:520,
                alerttit:"编辑",
                callback:function(){
                    $(".layerContianerEdit").load('${context}/admin/userDept/getDeptEditPage?id='+treeNode.id,{},function(){});
                }
            });
            saveToolTip.show();
		}
		
		//新增成功后需要触发的事件
		function add_dept_success(id,parentId,deptName,type){
			if(zTree == null){
				zTree = $.fn.zTree.getZTreeObj("treeDemo");
			}
			if(type == 1){//新增节点
				zTree.addNodes(treeNodeEdit, {id:id, pId:parentId, name:deptName});
			}else{//更新节点信息
				treeNodeEdit.id = id;
				treeNodeEdit.name = deptName;
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
		$("#tabledata").css("width",parseInt(iframe_width) - moveCont - 10);
// 		$(".xhn_Mleft").css("overflow-y","scroll");
	}
	
	</script>
</head>
<body>
  <div class="xhn_main" style="height:100%">
		<div id="about" class="nano" style="width:260px;height:100%;">
			  <div class="nano-content">
					<div class="ztree_box">
						<!-- 功能菜单管理 -->
						<input type="hidden" id="id">
						<ul id="treeDemo" class="ztree"></ul>
					</div>
			   </div>
			   <div class="drag_slide"></div>
		  </div>
		<div id="tabledata" style="float:left;overflow-y: scroll;">
			<!-- 加载用户列表 -->
			<div class="cw_jiazai" style="" align="center">
				<@i18n.messageText code='department.management.text.click.dapartment.name.left'  text='请点击左侧部门名称，查看相应属性' />
			</div>
		</div>
	</div>
</body>
</html>