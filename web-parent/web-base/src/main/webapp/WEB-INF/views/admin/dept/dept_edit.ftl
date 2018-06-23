<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>部门管理--新增按钮</title>
    
    
    <#include "/system/common_js.ftl">
    <script type="text/javascript">
		$().ready(function() {
			var toolTip = new diaPrompt();
			// validate signup form on keyup and submit
			$("#form_post").validate({
				submitHandler : function(form) { //验证通过后的执行方法
					var id = $("[name='id']").val();
					if (id != '') {
						$('#form_post').attr("action", "/admin/dept/update");
					}
					//当前的form通过ajax方式提交（用到jQuery.Form文件）
					$(form).ajaxSubmit({
						dataType : "json",
						success : function(json) {
							if (json.code == 1) {
								//location.reload();
								var deptId = json.data.id;
		    	        		var parentId = json.data.parentId;
		    	        		var deptName = json.data.deptName;
		    	        		var type = 1;
		    	        		if(id != null && id.length > 0){
		    	        			type = 2;
		    	        		}
		    	        		add_dept_success(deptId,parentId,deptName,type);
		    	    			$("#tabledata").load('/admin/dept/getDeptEditPage?id='+deptId,{},function(){});
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
	
				},
				focusInvalid : true, //验证提示时，鼠标光标指向提示的input
				rules : {
					deptName : {
						required : true,
						rangelength : [ 1, 32 ],
						spaceCheck : "deptName"
					},
					personName : {
						maxlength : 16
					},
					personMobile : {
						maxlength : 32,
						regex : "^[0-9+-]+$"
					},
					deptPhone : {
						maxlength : 32,
						regex : "^[0-9+-]+$"
					},
					deptAddr : {
						maxlength : 128
					}
				},
				messages : {
					deptName : {
						required : "<@i18n.messageText code='department.management.form.confirm.department.name.empty'  text='部门名称不能为空' />",
						rangelength : "<@i18n.messageText code='department.management.form.confirm.department.name.length'  text='部门名称长度必须在1-32之间' />",
						spaceCheck : "<@i18n.messageText code='department.management.form.confirm.department.name.begin'  text='部门名称不能以空格开头' />"
					},
					personName : {
						maxlength : "<@i18n.messageText code='business.management.form.confirm.leader.name.length'  text='负责人长度不能超过16' />"
					},
					personMobile : {
						maxlength : "<@i18n.messageText code='business.management.form.confirm.leader.contact.way.length'  text='负责人联系方式长度不能超过32' />",
						regex : "<@i18n.messageText code='business.management.form.confirm.leader.contact.way.consist'  text='负责人联系方式只能由数字、+和-组成' />"
					},
					deptPhone : {
						maxlength : "<@i18n.messageText code='department.management.form.comfirm.department.phone.length'  text='部门电话长度不能超过32' />",
						regex : "<@i18n.messageText code='department.management.form.comfirm.department.phone.consist'  text='部门电话只能由数字、+和-组成' />"
					},
					deptAddr : {
						maxlength : "<@i18n.messageText code='department.management.form.comfirm.department.address.length'  text='部门地址长度不能超过128' />"
					}
				}
			});
		});
	</script>
    <script type="text/javascript">

    $(document).ready(function(){
    	
    	$("#editDeptId").click(function(){
    		$("input,select").attr("disabled",false);
    		$("#editDeptId").hide();
    		$("#saveDeptId,#quXiaoId").show();
    		
    	});
    	$("#quXiaoId").click(function(){
    		var id = $("[name='id']").val();
    		if(id == ''){
    			id = $("[name='parentId']").val();
    		}
    		$("#tabledata").load('/admin/dept/getDeptEditPage?id='+id,{},function(){});
    	});
    	
    	var type = '${type}';
    	if(type == "add"){
    		$('#form_post').attr("action", "/admin/dept/save");
    		$("#editDeptId").click();
    		$("#parentIdTr").show();
    		
    	}
    })

	//关闭弹出层，并刷新列表
	function delDialog(){
		$(".overlay").remove();
		$(".searchbtn").click();
	}
    $(function($) {
    	var toolTip = new diaPrompt();
		$("[name='menuCenterAssignment']").on("click",function(){
	    	var that = this;
			var id = $(that).attr("id");
// 			window.open("/admin/menu/getMenuCenterAssignmentPage?relationType=2&relationId="+id);
			var toolTip = new Layer({
				layerclass:"layerContianer",//存放页面的容器类名
		        width:500,
		        height:450,
		        alerttit:"<@i18n.messageText code='department.management.button.department.authorized'  text='部门授权' />",
		        callback:function(){		        	
		        	$(".layerContianer").load('/admin/menu/getMenuCenterAssignmentPage?relationType=3&relationId='+id,{},function(){});
		        }
		    });
		    toolTip.show();
		});
		$("[name='deptUserFind']").on("click",function(){
	    	var that = this;
			var id = $(that).attr("id");
			var toolTip = new Layer({
				layerclass:"layerContianer",//存放页面的容器类名
		        width:800,
		        height:450,
		        alerttit:"<@i18n.messageText code='common.button.view.user'  text='查看用户' />",
		        callback:function(){		        	
		        	$(".layerContianer").load('/admin/user/getUserByRoleOrDeptPage?selectType=3&id='+id,{},function(){});
		        }
		    });
		    toolTip.show();
		});
    });
    $(".selectInfo").click(function(){
		var newWindow = window.open ('/admin/company/getOneCompanyQueryPage', 'select_top', 'height=650, width=900, top=100px, left=100px, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
	});
</script>

<style>
  .table>thead>tr>th, .table>tbody>tr>th, .table>tfoot>tr>th, .table>thead>tr>td, .table>tbody>tr>td, .table>tfoot>tr>td
 .table th{
      padding: 0 !important;
 }

</style>
</head>
<body>
<div class="main-container">
    <div class="tabContainer">
        <div class="content">
            <form action="/admin/dept/save" method="post" id="form_post" name="form_post">
            	<input type="hidden" value="${dept.id }"  name="id" id="id">
            	<input type="hidden" value="${dept.parentId }"  name="parentId" id="parentId">
	            <div class="form_box">
	                <ul class="col-lg-12 col-sm-12 col-xs-12">
	                    <li><span> <@i18n.messageText code='user.management.text.affiliated.companies'  text='所属企业' /> </span><font color="red">${dept.compName }</font><input type="hidden" id="compId" name="compId" value="${dept.compId }"></li>
	                	<li id="parentIdTr" style="display: none;"><span> <@i18n.messageText code='department.management.text.parent.department.name'  text='父级部门名称' /> </span><font color="red">${parentDept.deptName }</font></li>
	                	<li><span> <@i18n.messageText code='department.management.text.department.name'  text='部门名称' /> </span><p><input type="text" class="inp_set" name="deptName" id="deptName" value="${dept.deptName }"  disabled="disabled"></p><font color="red">*</font></li>
	                    <li><span> <@i18n.messageText code='business.management.text.leader.name'  text='负责人姓名' /> </span><p><input type="text" class="inp_set" name="personName" id="personName" value="${dept.personName }" disabled="disabled"></p></li>
	               		<li><span> <@i18n.messageText code='business.management.text.person.in.charge.contact.information'  text='负责人联系方式' /> </span><p><input type="text" class="inp_set" name="personMobile" id="personMobile" value="${dept.personMobile }" disabled="disabled"></p></li>
	               		<li id="menuSort" ><span> <@i18n.messageText code='dictionary.management.text.dictionary.sequence'  text='排序' /> </span><p><input type="text" class="inp_set" name="sort" id="sort" value="${dept.sort}" disabled="disabled"></p></li>
	                    <li><span> <@i18n.messageText code='department.management.text.department.phone'  text='部门电话' /></span><p><input type="text" class="inp_set" name="deptPhone" id="deptPhone" value="${dept.deptPhone }" disabled="disabled"></p></li>
	                	<li><span> <@i18n.messageText code='department.management.text.department.address'  text='部门地址' /></span><p><input type="text" class="inp_set" name="deptAddr" id="deptAddr" value="${dept.deptAddr }" disabled="disabled"></p></li>
	                    <li class="class_btn_li">
	                    	<@loginUserAuth key='dept_edit' ; value>
								<#if value =='1'>
	                    			<input type="submit" class="saveDeptId fbtn btn_sm ensure_btn" id="saveDeptId" value="<@i18n.messageText code='common.button.save'  text='保存' />" style="display: none;">
	                    			<input type="button" class="editDeptId fbtn btn_sm ensure_btn" id="editDeptId" value="<@i18n.messageText code='common.button.edit'  text='编辑' />">
	                    			<input type="button" class="quXiaoId fbtn btn_sm cancle_btn" id="quXiaoId" value="<@i18n.messageText code='common.button.quit.false'  text='取消' />" style="display: none;">
	                    		</#if>
	                		</@loginUserAuth>
	                    	<@loginUserAuth key='dept_user_find' ; value>
								<#if value =='1'>
	                    			<input type="button" class="deptUserFind fbtn btn_sm ensure_btn" id="${dept.id }" name="deptUserFind" value="<@i18n.messageText code='common.button.view.user'  text='查看用户' />">
	                    		</#if>
	                		</@loginUserAuth>
	                    	<@loginUserAuth key='dept_authAssign' ; value>
								<#if value =='1'>
	                    			<input type="button" class="menuCenterAssignment fbtn btn_sm ensure_btn" id="${dept.id }" name="menuCenterAssignment" value="<@i18n.messageText code='common.button.power.allocation'  text='权限分配' />">
	                    		</#if>
	                		</@loginUserAuth>
	                    </li>
	                </ul>
	            </div>
			</form>
        </div>
    </div>
</div>
</body>
</html>