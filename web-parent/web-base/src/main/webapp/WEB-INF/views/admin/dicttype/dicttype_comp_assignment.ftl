<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>字典类型分配</title>
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
		
    </style>
   
    <#include "/system/common_js.ftl">
    <@configjQueryzTreeJs />
    <SCRIPT type="text/javascript">
	var toolTip = new diaPrompt();
    $(function($) {
		//保存字典类型分配
		$("#saveDicttypeComp").click(function(){
			var zTree = $.fn.zTree.getZTreeObj("treeDemo_DicttypeAccess");
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
			var compId = $("#compId").val();//关联表ID
			saveMenuCenters(ids,compId);
		});
    });
    
    //保存访问或授权权限
    function saveMenuCenters(ids,compId){
    	$.ajax({
			type: "POST",
			url: "/admin/dicttype/saveDicttypeComp",
			data: {"dicttypeId":ids,"compId":compId},
			async:false,//必须ajax执行完才返回值
			success: function(json){
				if (json.code == 1) {
					toolTip.tips({
						timer:true,/*是否开启定时器*/
			            sec:2,/*倒计时的秒数*/
						message:"<@i18n.messageText code='business.management.text.save.sucessfully'  text='保存成功!' />！",
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
    }
    
    //获取权限树
    function getMenuTree(compId){
    	$.ajax({
			   type: "POST",
			   url: "/admin/dicttype/getDicttypeTreeByCompId",
			   data: {"compId":compId},
			   success: function(json){
			     if(json.code == 1){
			    	 createTree(json.data,"treeDemo_DicttypeAccess");
			     }else{
					toolTip.error({
						timer:true,/*是否开启定时器*/
			            sec:2,/*倒计时的秒数*/
	    	            message:"<@i18n.messageText code='dictionary.type.management.text.dictionary.type.assignment.failed'  text='获取字典类型分配信息失败！' />",
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
		pn = "p",
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
 		var compId = '${comp.id}';
 		if(compId != '' && compId != undefined && compId != 'undefined'){
 			getMenuTree(compId);
 		}
    });
	</SCRIPT>
    
</head>
<body>

	<div class="main-container" style="" align="center">
		<ul class="main_ul">
			<li>
				<span> <@i18n.messageText code='business.management.text.business.name'  text='企业名称' /> </span>
				<span>${comp.compName }<input type="hidden" id="compId" name="compId" value="${comp.id }"></span>
			</li>
			<li>
				<div id="tabs-body" class="tabs-body">
				    <div style="display:block">
					   	<div class="ztreeDemo_exBackground left">
							<ul id="treeDemo_DicttypeAccess" class="ztree"></ul>
						  	<div class="sp_btnC cw_dialogB">
						      <ul style="height:50px;margin-top:20px;">
						      	  <li class="class_btn_li">
						          	<input type="button" class="saveDicttypeComp fbtn btn_sm ensure_btn" id="saveDicttypeComp" value="<@i18n.messageText code='common.button.save'  text='保存' />">
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