<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    
    <#include "/system/common_css.ftl">
    <#include "/system/common_js.ftl">
    <style type="text/css">
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
    <script type="text/javascript">

  	//验证设施名称是否存在并提交_start-------
	$("#uploadExcel").click(function(){
		var file_xls  = $("#importExcel").val();
		if(file_xls == ""){
			toolTip.warn({
				timer:true,/*是否开启定时器*/
	            sec:2,/*倒计时的秒数*/
				message:"<@i18n.messageText code='file.resource.management.text.select.excel'  text='请选择excel！' />",
				callback:function(){}
			});
			return;
		}else{
	        if(!/\.(xlsx|xls|XLSX|XLS)$/.test(file_xls))
	        {
    	        toolTip.error({
					timer:true,/*是否开启定时器*/
		            sec:2,/*倒计时的秒数*/
    	      		message:"<@i18n.messageText code='file.resource.management.text.file.format.xlsx.xls'  text='文件格式必须是 xlsx、xls' />",
    	      		callback:function(){}
    	      	});
	          	return false;
	        }
	    }
		
		var formData = new FormData($( "#form_file" )[0]);  
		$.ajax({
			url:"/admin/user/importUserInfo",
			method:"post",
			data: formData, 
			dataType:"json" , 
			async: false,
			cache: false,
			contentType: false,
			processData: false,  
			success: function (json) {
	        	if(json.code == 1){
	        		toolTip.tips({
						timer:true,/*是否开启定时器*/
			            sec:2,/*倒计时的秒数*/
		    			message:"<@i18n.messageText code='file.resource.management.text.import.success'  text='导入成功！' />",
		    			callback:function(){
		    			}
		    		});
	        		delDialog();
		       	}else{
		       		document.getElementById('errStr').innerHTML = "<font color='red'>"+json.msg+"</font>";
		       	}
	        	
		}});
	});
	</script>
</head>
<body>
	<div class="main-container" align="center">
	<ul class="main_ul">
		<li>
		<div class="tabs-body">
	        <form action="" method="post" id="form_file" name="form_file" enctype="multipart/form-data">
	             <ul class="main_ul">
	                 <li><input type="file" value="<@i18n.messageText code='file.resource.management.text.select.file'  text='选择文件' />"  name="importExcel" id="importExcel"></li>
	                 <li class="class_btn_li" style="margin-top:20px !important">
	                 	<input type="button" class="uploadExcel fbtn btn_sm ensure_btn" id="uploadExcel" value="<@i18n.messageText code='file.resource.management.button.start.importing'  text='开始导入' />" >
	                 	<input type="button" class="fbtn btn_sm cancle_btn" value="<@i18n.messageText code='common.button.quit.false'  text='关闭' />" onclick="javascript:delDialog()" >
	                 </li>
	                 <li id="errStr"></li>
	             </ul>
	            
			</form>
	       </div>
	    </div>
	    </li>
    </ul>
</body>
</html>