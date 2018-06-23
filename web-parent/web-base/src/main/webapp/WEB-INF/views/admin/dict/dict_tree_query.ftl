<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>字典管理</title>
    <style type="text/css">
		.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
	</style>
    <#include "/system/common_css.ftl">
    <#include "/system/common_js.ftl">
	<@configjQueryzTreeJs />
	<SCRIPT type="text/javascript">
		var toolTip = new diaPrompt();
		var zTree ;
		var treeNodeEdit;
		var op_ex = 1;
		var setting = {
			view: {
				selectedMulti: false
			},
			edit: {
				enable: true,
				editNameSelectAll: true,
				showRemoveBtn: false,
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
			}
		};

		//页面加载启动
		$(document).ready(function(){
			loadTree();
		});
		//加载树信息
		function loadTree(){
			var zNodes = [];
			$.ajax({
				type: "POST",
				url: "/admin/dicttype/getDicttypeByCompTreeQuery",
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
							message:"<@i18n.messageText code='dictionary.management.text.query.dictionary.type.information.failed'  text='查询字典类型信息失败！' />",
							callback:function(){}
						});
					}
			   }
			});
		}
		//点击字典类型触发事件
		function zTreeOnClick(event, treeId, treeNode) {
			treeNodeEdit =  treeNode;
			load_dicttype_info(treeNode.id);//加载右侧列表
		};
		
		//加载右侧方法
		function load_dicttype_info(id){
			$("#tabledata").load('/admin/dict/getDictQueryPage?typeId='+id,{},function(){});
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
					<!-- 功能字典管理 --><input type="hidden" id="id">
								<ul id="treeDemo" class="ztree"></ul>
					</div>
			   </div>
			   <div class="drag_slide"></div>
		  </div>
		<div id="tabledata" style="float:left;">
			<!-- 加载用户列表 -->
			<div class="cw_jiazai" style="" align="center">
				<@i18n.messageText code='dictionary.management.text.click.dictionary.type.name.left'  text='请点击左侧字典类型名称，查看相应字典数据' />
			</div>
		</div>
	</div>
</body>
</html>