<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>用户查询页面</title>
    <#import "spring.ftl" as i18n/>
    <style>
    	.cw_dialogB>ul{
    		margin-top:-3px !important;
    	}
    </style>
</head>
<body >

<div class="main-container">
    <div class="serContainer">
        <ul class="serbox">
            <li>
                <div class="inpdiv inpdiv1">
                    <input type="text" id="search.realName" placeholder="<@i18n.messageText code='user.management.text.real.name'  text='真实姓名' />" class="sea_inp">
                </div>
            </li>
            <li>
                <div class="inpdiv inpdiv1">
                    <input type="text" id="search.userName" placeholder="<@i18n.messageText code='user.management.text.username'  text='用户名' />" class="sea_inp">
                </div>
            </li>
            <li>
                <label class="user_state"><@i18n.messageText code='user.management.select.user.status'  text='用户状态' />:</label>
                <div class="inpdiv inpdiv1">
                    <select  class="inp1 select" id="search.loginStatus" name="search.loginStatus">
						<option value="" > <@i18n.messageText code='user.management.select.user.status.all'  text='全部' /> </option>
						<option value="1"> <@i18n.messageText code='user.management.select.user.status.normal'  text='正常' /> </option>
						<option value="2"> <@i18n.messageText code='user.management.select.user.status.disable'  text='停用' /> </option>
					</select>
                </div>
            </li>
            <!-- <li>
            	<div class="content_wrap inpdiv inpdiv1">
					<input id="citySel" class="sea_inp" placeholder="部门" type="text" value="" onfocus="showMenu(); return false;"/>
				</div>
				<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
					<ul id="treeDemo" class="ztree sea_inp" style="margin-top:3px; width:180px;"></ul>
				</div>
			</li> -->
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
        	 <@loginUserAuth key='user_add' ; value>
	            <#if value =='1'>
	            <li class="add_user">
					<input type="button" class="addUser td_btn add_button add_bg" value="<@i18n.messageText code='user.management.button.create.user'  text='新增用户' />">
	            </li>
				</#if>
				</@loginUserAuth>
				<!-- <li><input type="submit" value="批量删除" class="td_btn del_btn del_bg"></li> -->
<!-- 	            <li><input type="button" id="importBatch" name="importBatch" value="批量导入" class="importBatch td_btn into_btn into_bg"></li> -->
<!-- 	            <li><input type="button" id="exportUser" name="exportUser" value="批量导出" class="td_btn outto_btn outto_bg"></li> -->
	        </ul>
            <table class="table table-bordered">
                <thead class="bordered-darkorange">
                <tr class="th_title">
                    <th> <@i18n.messageText code='common.text.number'  text='序号' /> </th>
                    <th> <@i18n.messageText code='user.management.text.affiliated.companies'  text='所属企业' /> </th>
                    <th class="sorting"  data-sortName="real_name" data-sortType="asc"> <@i18n.messageText code='user.management.text.real.name'  text='真实姓名' /> </th>
                    <th class="sorting"  data-sortName="user_name" data-sortType="asc"> <@i18n.messageText code='user.management.text.username'  text='用户名' /> </th>
                    <th> <@i18n.messageText code='user.management.text.telephone'  text='电话' /> </th>
                    <th> <@i18n.messageText code='user.management.text.mobile.phone'  text='手机' /> </th>
                    <th> <@i18n.messageText code='user.management.select.user.status'  text='用户状态' /> </th>
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
	var selectId = "${selectId}";
	var selectType = "${selectType}";
	//关闭弹出层，并刷新列表
	function delDialog(){
		$(".overlay").remove();
		$(".searchbtn").click();
	}
    $(function($) {
    	/* $.ajax({
		   url: "/admin/dept/deptComboTree",
		   dataType: 'json',
		   method: 'GET',
		   success: function(data){
			   $('#deptComboTree').combotree('loadData', data);
		   }
		}); */
    	
    	var toolTip = new diaPrompt();
    	$(".table-bordered").on("click",".editInfo",function(){
	    	var that = this;
			var id = $(that).attr("id");
// 			window.open("/admin/user/getUserEditPage?id="+id);
			var toolTip = new Layer({
				layerclass:"layerContianer",//存放页面的容器类名
		        width:450,
		        height:460,
		        alerttit:"<@i18n.messageText code='user.management.button.user.edit'  text='用户编辑' />",
		        callback:function(){		        	
		        	$(".layerContianer").load('${context}/admin/user/getUserEditPage?id='+id,{},function(){});
		        }
		    });
		    toolTip.show();
		});
		
		$(".table-bordered").on("click",".delInfo",function(){
	    	var that = this;
			var id = $(that).attr("id");
			toolTip.warn({
	            message:"<@i18n.messageText code='process.definition.management.confirm.delete.cannot.restored'  text='确认要删除，删除后不可恢复！' />",
	            callback:function(){
					$.ajax({
					   type: "POST",
					   url: "${context}/admin/user/delete",
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
		
		$(".table-bordered").on("click",".resetPassword",function(){
			var that = this;
			var id = $(that).attr("id");
			var toolTip = new Layer({
				layerclass:"layerContianer",//存放页面的容器类名
		        width:400,
		        height:280,
		        alerttit:"<@i18n.messageText code='common.button.change.password'  text='修改密码' />",
		        callback:function(){		        	
		        	$(".layerContianer").load('${context}/admin/user/getPasswordResetPage?id='+id,{},function(){});
		        }
		    });
		    toolTip.show();
			
		});
		$(".table-bordered").on("click",".userRoleAssignment",function(){
	    	var that = this;
			var id = $(that).attr("id");
// 			window.open("/admin/user/getUserRoleAssignmentPage?id="+id);
			var toolTip = new Layer({
				layerclass:"layerContianerUr1",//存放页面的容器类名
		        width:380,
		        height:390,
		        alerttit:"<@i18n.messageText code='common.button.role.allocation'  text='角色分配' />",
		        callback:function(){		        	
		        	$(".layerContianerUr1").load('${context}/admin/user/getUserRoleAssignmentPage?id='+id,{},function(){});
		        }
		    });
		    toolTip.show();
		});
		$(".table-bordered").on("click",".userDeptAssignment",function(){
	    	var that = this;
			var id = $(that).attr("id");
// 			window.open("/admin/user/getUserDeptAssignmentPage?id="+id);
			var toolTip = new Layer({
				layerclass:"layerContianerUd1",//存放页面的容器类名
		        width:400,
		        height:350,
		        alerttit:"<@i18n.messageText code='common.button.department.allocation'  text='部门分配' />",
		        setOverflow:"overflow-y:scroll",
		        callback:function(){		        	
		        	$(".layerContianerUd1").load('${context}/admin/user/getUserDeptAssignmentPage?id='+id,{},function(){});
		        }
		    });
		    toolTip.show();
		});
		
	$(".table-bordered").on("click",".menuCenterAssignment",function(){
	    	var that = this;
			var id = $(that).attr("id");
// 			window.open("/admin/menu/getMenuCenterAssignmentPage?relationType=1&relationId="+id);
			var toolTip = new Layer({
				layerclass:"layerContianerMc1",//存放页面的容器类名
		        width:500,
		        height:450,
		        alerttit:"<@i18n.messageText code='common.button.power.allocation'  text='权限分配' />",
		        setOverflow:"overflow-y:scroll",
		        callback:function(){		        	
		        	$(".layerContianerMc1").load('${context}/admin/menu/getMenuCenterAssignmentPage?relationType=1&relationId='+id,{},function(){});
		        }
		    });
		    toolTip.show();
		});
		
	  	
	});
    

  	
 	//字段排序
	var sortName = $(this).attr("data-sortName");
	var sortClass = $(this).attr("class");
	var tabSort = new tableSort({sortClass:".sorting",callback:function(sortName,sortType){
		var search_realName = $("[id='search.realName']").val();
    	var search_userName = $("[id='search.userName']").val();
    	var search_loginStatus = $("[id='search.loginStatus']").val();
    	var qm_url = "${context}/admin/user/findPage?orderBy= "+sortName+" "+sortType;
    	var qm_data = {pageSize:10,"search.selectId":selectId,"search.selectType":selectType,'search.realName':search_realName,'search.userName':search_userName,'search.loginStatus':search_loginStatus};
		deplayPage(qm_url,qm_data);
	}});
    
    /*点击查询按钮*/
    $(".searchbtn ").click(function(){
    	var search_realName = $("[id='search.realName']").val();
    	var search_userName = $("[id='search.userName']").val();
    	var search_loginStatus = $("[id='search.loginStatus']").val();
        var qm_url = "${context}/admin/user/findPage";
        var qm_data = {pageSize:10,"search.selectId":selectId,"search.selectType":selectType,'search.realName':search_realName,'search.userName':search_userName,'search.loginStatus':search_loginStatus};
       	deplayPage(qm_url,qm_data);
    });
    
    $(".addUser").click(function(){
//     	window.open("/admin/user/getUserEditPage");
    	var toolTip = new Layer({
    		layerclass:"layerContianer",//存放页面的容器类名
	        width:450,
	        height:460,
	        alerttit:"<@i18n.messageText code='user.management.button.create.user'  text='新增用户' />",
	        callback:function(){		        	
	        	$(".layerContianer").load('${context}/admin/user/getUserEditPage?compId='+selectId,{},function(){});
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
        
        var qm_url = "${context}/admin/user/findPage";
        var qm_data = {pageSize:10,"search.selectId":selectId,"search.selectType":selectType};
       	deplayPage(qm_url,qm_data);
    });
    
    function getLoginStatus(status){
    	if(status == 1){
    		return "<@i18n.messageText code='user.management.select.user.status.normal'  text='正常' />";
    	}else if(status == 2){
    		return "<@i18n.messageText code='user.management.select.user.status.disable'  text='停用' />";
    	}
    	return "";
    }
    function deplayPage(qm_url,qm_data,qm_limit){
        /*pageSize:设置每页显示的数据条数*/
        /*limit:设置页码按钮显示的个数*/
        /*pageBtnDiv：存放页码按钮的父元素名称*/
        /*pageBtnClass:存放按钮样式的类名*/
    	 var page  = new CommonPage({url:qm_url,data:qm_data,limit:10,pageBtnDiv:$("#navbox"),pageBtnClass:"pageBtnStyle4",callBack:function(data){
            var str="";
            $(".table-bordered tbody").empty();
            $(data).each(function (i,item) {
            	var lsname = getLoginStatus(item.loginStatus);
                str +=" <tr><td>"
                 + (i + 1) + "</td><td>"
                 + item.compName + "</td><td data-limit='20' title='"+item.realName+"'>"
                 + item.realName + "</td><td>"
                 + item.userName + "</td><td>"
                 + item.phone + "</td><td>"
                 + item.mobile + "</td><td>"
                 + lsname + "</td><td width='500'>"
                  +  "<@loginUserAuth key='user_edit' ; value><#if value =='1'><a href='javascript:void(0);' class='editInfo td_btn edit_btn edit_bg' id=" + item.id + "> <@i18n.messageText code='common.button.edit'  text='编辑' /> </a></#if></@loginUserAuth> "
                 + "<@loginUserAuth key='user_del' ; value><#if value =='1'><a href='javascript:void(0);' class='delInfo td_btn del_btn del_bg' id=" + item.id + "> <@i18n.messageText code='common.button.delete'  text='删除' /> </a></#if></@loginUserAuth> "
                 + "<@loginUserAuth key='user_passwordReset' ; value><#if value =='1'><a href='javascript:void(0);'  class='resetPassword td_btn reset_btn reset_bg' id=" + item.id + "> <@i18n.messageText code='common.button.change.password'  text='修改密码' /> </a></#if></@loginUserAuth> "
                 + "<@loginUserAuth key='user_roleAssign' ; value><#if value =='1'><a href='javascript:void(0);'  class='userRoleAssignment td_btn part_btn part_bg' id=" + item.id + "> <@i18n.messageText code='common.button.role.allocation'  text='角色分配' /> </a></#if></@loginUserAuth> "
                 + "<@loginUserAuth key='user_deptAssign' ; value><#if value =='1'><a href='javascript:void(0);'  class='userDeptAssignment td_btn branch_btn branch_bg' id=" + item.id + "> <@i18n.messageText code='common.button.department.allocation'  text='common.button.department.allocation=部门分配' /> </a></#if></@loginUserAuth> "
                 + "<@loginUserAuth key='user_authAssign' ; value><#if value =='1'><a href='javascript:void(0);'  class='menuCenterAssignment td_btn limit_btn limit_bg' id=" + item.id + "> <@i18n.messageText code='common.button.power.allocation'  text='权限分配' /> </a></#if></@loginUserAuth></td></tr>";
            });
            if(data == null || data == ''){
            	str +="<tr><td colspan='16'><img src='../../assets/images/load.png' class='no_data'/> <@i18n.messageText code='common.text.no.record'  text='查询无记录' /> </td></tr>";
            }
            $(".table-bordered tbody").append(str);
            UDP.Public.Limitcharacter();
        }});
    }
    
</script>
</body>
</html>