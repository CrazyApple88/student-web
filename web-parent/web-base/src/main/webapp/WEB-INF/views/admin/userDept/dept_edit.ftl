<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>部门管理--新增按钮</title>
    <#import "spring.ftl" as i18n/>
    <style>
    	.form_box li{
    		height:45px;
    		margin-top: 6px;
    	}
    </style>
    <script type="text/javascript">
		$().ready(function() {
			var toolTip = new diaPrompt();
			// validate signup form on keyup and submit
			$("#form_post").validate({
				submitHandler : function(form) { //验证通过后的执行方法
					var id = $("[name='id']").val();
					if (id != '') {
						$('#form_post').attr("action", "${context}/admin/userDept/update");
					}
					//当前的form通过ajax方式提交（用到jQuery.Form文件）
					$(form).ajaxSubmit({
						dataType : "json",
						success : function(json) {
							if (json.code == 1) {
								toolTip.tips({
									timer:true,/*是否开启定时器*/
						            sec:2,/*倒计时的秒数*/
									message:"保存成功！",
									callback:function(){
									/* 	 var tabs=window.top["refresh"];
										 var id = tabs.getCurrentUniqueId();
							             var tab = new CleverTab(tabs, id);
							             tab.refresh();
 */									}
								});
								var deptId = json.data.id;
		    	        		var parentId = json.data.parentId;
		    	        		var deptName = json.data.deptName;
		    	        		var type = 1;
		    	        		if(id != null && id.length > 0){
		    	        			type = 2;
		    	        		}
								delDialog();
								add_dept_success(deptId,parentId,deptName,type);
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
    	
    	$("#quXiaoId").click(function(){
    		delDialog();
    	});
    	
    	var type = '${type}';
    	if(type == "add"){
    		$('#form_post').attr("action", "${context}/admin/userDept/save");
    		$("#editDeptId").click();
    		$("#parentIdTr").show();
    		
    	}
    });

	//关闭弹出层，并刷新列表
	function delDialog(){
		$(".overlay").remove();
	}
    $(function($) {
		$("#userDeptAssignmentDept2").on("click",function(){
	    	var that = this;
			var id = $(that).attr("data");
// 			window.open("/admin/menu/getMenuCenterAssignmentPage?relationType=2&relationId="+id);
			var toolTipDept = new Layer({
				layerclass:"layerContianerDeptB",//存放页面的容器类名
		        width:500,
		        height:450,
		        alerttit:"<@i18n.messageText code='department.management.button.department.authorized'  text='部门授权' />",
		        callback:function(){		        	
		        	console.log("-----00000");
		        	$(".layerContianerDeptB").load('${context}/admin/menu/getMenuCenterAssignmentPage?relationType=3&relationId='+id,{},function(){});
		        }
		    });
			toolTipDept.show();
		});
		$("#saveDeptId").click(function(){
			$("#form_post").submit();
		});
    });
</script>
</head>
<body>
<div class="main-container">
    <div class="tabContainer">
        <div class="content">
            <form action="${context}/admin/userDept/save" method="post" id="form_post" name="form_post">
            	<input type="hidden" value="${dept.id }"  name="id" id="id">
            	<input type="hidden" value="${dept.parentId }"  name="parentId" id="parentId">
	            <div class="form_box">
	                <ul class="col-lg-12 col-sm-12 col-xs-12">
	                    <li><span> <@i18n.messageText code='user.management.text.affiliated.companies'  text='所属企业' /> </span><font color="red">${dept.compName }</font><input type="hidden" id="compId" name="compId" value="${dept.compId }"></li>
	                	<li id="parentIdTr" style="display: none;"><span> <@i18n.messageText code='department.management.text.parent.department.name'  text='父级部门名称' /> </span><font color="red">${parentDept.deptName }</font></li>
	                	<li><span> <@i18n.messageText code='department.management.text.department.name'  text='部门名称' /> </span><p><input type="text" class="inp_set" name="deptName" id="deptName" value="${dept.deptName }" ></p><font color="red">*</font></li>
	                    <li><span> <@i18n.messageText code='business.management.text.leader.name'  text='负责人姓名' /> </span><p><input type="text" class="inp_set" name="personName" id="personName" value="${dept.personName }"></p></li>
	               		<li><span> <@i18n.messageText code='business.management.text.person.in.charge.contact.information'  text='负责人联系方式' /> </span><p><input type="text" class="inp_set" name="personMobile" id="personMobile" value="${dept.personMobile }"></p></li>
	               		<li id="menuSort" ><span> <@i18n.messageText code='dictionary.management.text.dictionary.sequence'  text='排序' /> </span><p><input type="text" class="inp_set" name="sort" id="sort" value="${dept.sort}"></p></li>
	                    <li><span> <@i18n.messageText code='department.management.text.department.phone'  text='部门电话' /></span><p><input type="text" class="inp_set" name="deptPhone" id="deptPhone" value="${dept.deptPhone }"></p></li>
	                	<li><span> <@i18n.messageText code='department.management.text.department.address'  text='部门地址' /></span><p><input type="text" class="inp_set" name="deptAddr" id="deptAddr" value="${dept.deptAddr }"></p></li>
	                    <li class="class_btn_li" style="margin-top: 12px;">
	                    	<@loginUserAuth key='dept_edit' ; value>
								<#if value =='1'>
	                    			<input type="button" class="saveDeptId fbtn btn_sm ensure_btn" id="saveDeptId" value="<@i18n.messageText code='common.button.save'  text='保存' />">
	                    			<input type="button" class="quXiaoId fbtn btn_sm cancle_btn" id="quXiaoId" value="<@i18n.messageText code='common.button.quit.false'  text='取消' />">
	                    		</#if>
	                		</@loginUserAuth>
	                    	<@loginUserAuth key='dept_authAssign' ; value>
								<#if value =='1' && dept.id !=''>
	                    			<input type="button" class="userDeptAssignmentDept2 fbtn btn_sm ensure_btn" id="userDeptAssignmentDept2" data="${dept.id }" name="userDeptAssignmentDept2" value="<@i18n.messageText code='common.button.power.allocation'  text='权限分配' />">
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