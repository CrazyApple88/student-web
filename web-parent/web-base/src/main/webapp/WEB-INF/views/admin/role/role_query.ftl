<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    
    <#include "/system/common_css.ftl">
    <#include "/system/common_js.ftl">
</head>
<body>

<div class="main-container">
    <div class="serContainer">
        <ul class="serbox">
            <li>
                <div class="inpdiv inpdiv1">
                    <input type="text" placeholder="<@i18n.messageText code='role.management.text.role.name'  text='角色名称' />" class="sea_inp" id="search.roleName">
                </div>
            </li>
			
            <li>
				<div class="serbtnbox right">
		            <input type="button" class="searchbtn sec_btn sec_btn_query" value="<@i18n.messageText code='common.button.query'  text='查询' />">
		        </div>
			</li>
        </ul>
    </div>
    <div class="tabContainer">
        <div class="col-lg-12 col-sm-12 col-xs-12">
            <ul class="btn_set">
        	 <@loginUserAuth key='role_add' ; value>
	            <#if value =='1'>
	            <li class="add_common">
					<input type="button" class="addRole td_btn add_button add_bg" value="<@i18n.messageText code='role.management.button.create.role'  text='新增角色' />">
	            </li>
				</#if>
			</@loginUserAuth>
			<!-- <li>
                <div>
                    <select id="roleSearchableSelect">
                    	<option value="0">选择用户</option>
					</select>
                </div>
            </li> -->
<!-- 	            <li><input type="submit" value="批量删除" class="td_btn del_btn del_bg"></li> -->
<!-- 	            <li><input type="submit" value="批量导入" class="td_btn into_btn into_bg"></li> -->
<!-- 	            <li><input type="submit" value="批量导出" class="td_btn outto_btn outto_bg"></li> -->
	        </ul>
            <table class="table table-bordered">
                <thead class="bordered-darkorange">
                <tr class="th_title">
                    <th> <@i18n.messageText code='common.text.number'  text='序号' /> </th>
                    <th> <@i18n.messageText code='user.management.text.affiliated.companies'  text='所属企业' /> </th>
                    <th class="sorting"  data-sortName="role_name" data-sortType="asc"> <@i18n.messageText code='role.management.text.role.name'  text='角色名称' /> </th>
                    <th> <@i18n.messageText code='role.management.text.detailed.description'  text='详细说明' /> </th>
                    <th> <@i18n.messageText code='menu.management.button.operation'  text='操作' /> </th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    <div class="pageContainer">
        <!--存放加载进来的导航按钮-->
       <div class="pagecont">
           <div id="navbox"></div>
       </div>
    </div>
</div>

<script>

	//关闭弹出层，并刷新列表
	function delDialog(){
		$(".overlay").remove();
		$(".searchbtn").click();
	}
	function delDialogNoRefresh(){
		$(".overlay").remove();
	}
    $(function($) {
    	
    	var toolTip = new diaPrompt();
    	$(document).on("click",".editInfo",function(){
	    	var that = this;
			var id = $(that).attr("id");
// 			window.open("/admin/role/getRoleEditPage?id="+id);
			var toolTip = new Layer({
				layerclass:"layerContianer",//存放页面的容器类名
		        width:450,
		        height:320,
		        alerttit:"<@i18n.messageText code='role.management.text.role.edit'  text='角色编辑' />",
		        callback:function(){		        	
		        	$(".layerContianer").load('/admin/role/getRoleEditPage?id='+id,{},function(){});
		        }
		    });
		    toolTip.show();
		});
    	
		$(document).on("click",".menuCenterAssignment",function(){
	    	var that = this;
			var id = $(that).attr("id");
// 			window.open("/admin/menu/getMenuCenterAssignmentPage?relationType=2&relationId="+id);
			var toolTip = new Layer({
				layerclass:"layerContianer",//存放页面的容器类名
		        width:500,
		        height:450,
		        alerttit:"<@i18n.messageText code='role.management.text.role.authorization'  text='角色授权' />",
		        callback:function(){		        	
		        	$(".layerContianer").load('/admin/menu/getMenuCenterAssignmentPage?relationType=2&relationId='+id,{},function(){});
		        }
		    });
		    toolTip.show();
		});
		
		$(document).on("click",".roleUserFind",function(){
	    	var that = this;
			var id = $(that).attr("id");
			var toolTip = new Layer({
				layerclass:"layerContianer",//存放页面的容器类名
		        width:800,
		        height:460,
		        alerttit:"<@i18n.messageText code='common.button.view.user'  text='查看用户' />",
		        callback:function(){		        	
		        	$(".layerContianer").load('/admin/user/getUserByRoleOrDeptPage?selectType=2&id='+id,{},function(){});
		        }
		    });
		    toolTip.show();
		});
		
		//给角色分配用户
		$(document).on("click",".roleUserAssign",function(){
	    	var that = this;
			var id = $(that).attr("id");
			var toolTip = new Layer({
				layerclass:"layerContianer",//存放页面的容器类名
		        width:800,
		        height:470,
		        alerttit:"<@i18n.messageText code='common.button.assign.users'  text='分配用户' />",
		        callback:function(){		        	
		        	$(".layerContianer").load('/admin/role/getRoleUserAssignPage?selectType=2&id='+id,{},function(){});
		        }
		    });
		    toolTip.show();
		});
		
		$(document).on("click",".delInfo",function(){
	    	var that = this;
			var id = $(that).attr("id");
			toolTip.warn({
	            message:"<@i18n.messageText code='process.definition.management.confirm.delete.cannot.restored'  text='确认要删除，删除后不可恢复！' />",
	            callback:function(){
					$.ajax({
					   type: "POST",
					   url: "/admin/role/delete",
					   data: "id="+id,
					   success: function(json){
						   if(json.code == 1){
						    	 toolTip.tips({
										timer:true,/*是否开启定时器*/
							            sec:2,/*倒计时的秒数*/
			    	    	            message:"<@i18n.messageText code='process.definition.management.text.delete.successfully'  text='删除成功！' />",
			    	    	            callback:function(){}
			    	        		});
						     }else{
						    	 toolTip.error({
										timer:true,/*是否开启定时器*/
							            sec:2,/*倒计时的秒数*/
			    	    	            message:json.msg,
	   		    	    	         	callback:function(){}
			    	        		});
						     }
						     $(".searchbtn ").click();
					   }
					});
					
	            }
	        });
		});
	});
    
  	//字段排序
	var sortName = $(this).attr("data-sortName");
	var sortClass = $(this).attr("class");
	var tabSort = new tableSort({sortClass:".sorting",callback:function(sortName,sortType){
		var search_roleName = $("[id='search.roleName']").val();
    	var search_useable = $("[id='search.useable']").val();
		var qm_url = "/admin/role/findPage?orderBy= "+sortName+" "+sortType;
		var nowPageSize = $("#limitpagenum").val();
    	var qm_data = {pageSize:nowPageSize,'search.roleName':search_roleName,'search.useable':search_useable};
		deplayPage(qm_url,qm_data);
	}});
    
    /*点击查询按钮*/
    $(".searchbtn ").click(function(){
    	var search_roleName = $("[id='search.roleName']").val();
    	var search_useable = $("[id='search.useable']").val();
        var qm_url = "/admin/role/findPage";
        var nowPageSize = $("#limitpagenum").val();
    	var qm_data = {pageSize:nowPageSize,'search.roleName':search_roleName,'search.useable':search_useable};
       	deplayPage(qm_url,qm_data);
    });
    
    $(".addRole").click(function(){
//     	window.open("/admin/role/getRoleEditPage");
    	var toolTip = new Layer({
			layerclass:"layerContianer",//存放页面的容器类名
	        width:450,
	        height:320,
	        alerttit:"<@i18n.messageText code='role.management.button.create.role'  text='新增角色' />",
	        callback:function(){		        	
	        	$(".layerContianer").load('/admin/role/getRoleEditPage',{},function(){});
	        }
	    });
	    toolTip.show();
    });

	
    $(function(){
        function AddProjectCostList(obj) {
            var opt =  obj || {};
            CommonPage.call(this, opt);
            return this;
        }
        ;(function () {
            var subPrototype = Object(CommonPage.prototype);
            subPrototype.constructor = AddProjectCostList;
            AddProjectCostList.prototype = subPrototype;
        })();
        
        var qm_url = "/admin/role/findPage";
        var qm_data = {pageSize:10};
       	deplayPage(qm_url,qm_data);
    });
    
    function deplayPage(qm_url,qm_data,qm_limit){
        /*pageSize:设置每页显示的数据条数*/
        /*limit:设置页码按钮显示的个数*/
        /*pageBtnDiv：存放页码按钮的父元素名称*/
        /*pageBtnClass:存放按钮样式的类名*/
    	 var page  = new CommonPage({url:qm_url,data:qm_data,limit:10,pageBtnDiv:$("#navbox"),pageBtnClass:"pageBtnStyle4",callBack:function(data){
            var str="";
            $(".table-bordered tbody").empty();
            $(data).each(function (i,item) {
                str +=" <tr><td>"
                 + (i + 1) + "</td><td>"
                 + item.compName + "</td><td>"
                 + item.roleName + "</td><td  data-limit='30' title='"+item.intro+"'>"
                 + item.intro + "</td><td width='550'>"
                 + "<@loginUserAuth key='role_edit' ; value><#if value =='1'><a href='javascript:void(0);' class='editInfo td_btn edit_btn edit_bg' id=" + item.id + "> <@i18n.messageText code='common.button.edit'  text='编辑' /> </a></#if></@loginUserAuth> "
                 + "<@loginUserAuth key='role_del' ; value><#if value =='1'><a href='javascript:void(0);' class='delInfo td_btn del_btn del_bg' id=" +  item.id + "> <@i18n.messageText code='common.button.delete'  text='删除' /> </a></#if></@loginUserAuth> "
                 + "<@loginUserAuth key='role_user_find' ; value><#if value =='1'><a href='javascript:void(0);' class='roleUserFind td_btn part_btn part_bg' id=" +  item.id + "> <@i18n.messageText code='common.button.view.user'  text='查看用户' /> </a></#if></@loginUserAuth> "
                 + "<@loginUserAuth key='role_user_assign' ; value><#if value =='1'><a href='javascript:void(0);' class='roleUserAssign td_btn part_btn part_bg' id=" +  item.id + "> <@i18n.messageText code='common.button.assign.users'  text='分配用户' /> </a></#if></@loginUserAuth> "
                 + "<@loginUserAuth key='role_authAssign' ; value><#if value =='1'><a href='javascript:void(0);'  class='menuCenterAssignment td_btn limit_btn limit_bg' id=" + item.id + "> <@i18n.messageText code='common.button.power.allocation'  text='权限分配' /> </a></#if></@loginUserAuth></td></tr>";
            });
            if(data == null || data == ''){
            	str +="<tr><td colspan='10'><img src='../../assets/images/load.png' class='no_data'/> <@i18n.messageText code='common.text.no.record'  text='查询无记录' /> </td></tr>";
            }
            $(".table-bordered tbody").append(str);
            UDP.Public.Limitcharacter();
        }});
    }
    
</script>
</body>
</html>