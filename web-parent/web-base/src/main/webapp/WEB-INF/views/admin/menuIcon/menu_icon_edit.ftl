<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <style>
    	label[class=error]{
    		height: 15px;
    	}
    </style>
	<script type="text/javascript">
		$().ready(function() {
			var toolTip = new diaPrompt();
			// validate signup form on keyup and submit
			$("#form_post").validate({
				submitHandler : function(form) { //验证通过后的执行方法
					var id = $("#entityId").val();
					if (id != '') {
						$('#form_post').attr("action", "${context}/admin/menuIcon/update");
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
									callback:function(){}
								});
								delDialog();
							} else {
								toolTip.error({
									timer:true,/*是否开启定时器*/
						            sec:2,/*倒计时的秒数*/
									message:"保存失败！",
									callback:function(){}
								});
							}
						}
					});
	
				},
				focusInvalid : true, //验证提示时，鼠标光标指向提示的input
				rules : {
					iconName : {
						required : true,
						maxlength : 255
					},
					typeId : {
						required : true
					},
					className : {
						required : true,
						maxlength : 255
					},
					//日期不校验
				},
				messages : {
					iconName : {
						required : "名称不能为空",
						maxlength :"名称长度不能超过255"
					},
					typeId : {
						required : "图标类型不能为空"
					},
					className : {
						required : "类名不能为空",
						maxlength :"类名长度不能超过255"
					},
					//日期不校验
				}
			});
		});
	</script>
</head>
<body>

<div class="main-container">
    <div class="tabContainer">
		<form action="${context}/admin/menuIcon/save" method="post" id="form_post" name="form_post">
					<input type="hidden" value="${entity.id}" id="entityId" name="id" >
                <ul class="details_box">
                 	<li>
                   		<span>名称<font color="red">*</font></span>
                       	<input type="text" class="inp_detail" name="iconName" id="iconName" value="${entity.iconName}"  placeholder="请输入名称" >
                	</li>
                 	<li>
                     	<span>图标类型<font color="red">*</font></span>
                       	<select class="det_select" id="typeId" name="typeId">
          					<option value="" >请选择</option>
          					<@sysDict key="menu_icon_type" ; value>
          						<option <#if value.dictCode==entity.typeId> selected="selected" </#if> value="${value.dictCode}">${value.dictName}</option>    
         					</@sysDict>
						</select>
					 </li>
                 	<li>
                   		<span>类名<font color="red">*</font></span>
                       	<input type="text" class="inp_detail" name="className" id="className" value="${entity.className}"  placeholder="请输入类名" >
                	</li>
                    <li  class="class_btn_li">
                        <input type="submit" btnType="save" value="确定" class="fbtn btn_sm ensure_btn">
                        <input type="button" value="关闭" btnType="cancel"  class="cancel fbtn btn_sm cancle_btn">
                    </li>
	           </ul>
		</form>
    </div>
</div>


</body>
</html>