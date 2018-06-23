<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>zyupload全部功能上传实例</title>
		
        <#include "/system/common_js.ftl">
		<link rel="stylesheet" href="${context}/assets/plug/zyupload/skins/zyupload-1.0.0.min.css?v=${cssVersion}" type="text/css">
		<script type="text/javascript" src="${context}/assets/plug/zyupload/zyupload-1.0.0.min.js"></script>
	</head>
	<body>
		<h1 style="text-align:center;">zyupload全部功能上传实例</h1>
	    <div id="zyupload" class="zyupload"></div>  
	    
	    
	    <script type="text/javascript">
			$(function(){
				// 初始化插件
				$("#zyupload").zyUpload({
					width            :   "650px",                 // 宽度
					height           :   "400px",                 // 宽度
					itemWidth        :   "140px",                 // 文件项的宽度
					itemHeight       :   "115px",                 // 文件项的高度
					url              :   "/admin/files/uploadFile",  // 上传文件的路径
					fileType         :   ["jpg","png","txt","js","exe"],// 上传文件的类型
					fileSize         :   51200000,                // 上传文件的大小
					multiple         :   true,                    // 是否可以多个文件上传
					dragDrop         :   true,                    // 是否可以拖动上传文件
					tailor           :   true,                    // 是否可以裁剪图片
					del              :   true,                    // 是否可以删除文件
					finishDel        :   false,  				  // 是否在上传文件完成后删除预览
					/* 外部获得的回调接口 */
					onSelect: function(selectFiles, allFiles){    // 选择文件的回调方法  selectFile:当前选中的文件  allFiles:还没上传的全部文件
						console.info("<@i18n.messageText code='file.resource.management.text.following.documents.selected'  text='当前选择了以下文件：' />");
						console.info(selectFiles);
					},
					onDelete: function(file, files){              // 删除一个文件的回调方法 file:当前删除的文件  files:删除之后的文件
						console.info("<@i18n.messageText code='file.resource.management.text.file.currently.deleted'  text='当前删除了此文件：' />");
						console.info(file.name);
					},
					onSuccess: function(file, response){          // 文件上传成功的回调方法
						console.info("<@i18n.messageText code='file.resource.management.text.file.uploaded.successfully'  text='此文件上传成功：' />");
						console.info(file);
						console.info("<@i18n.messageText code='file.resource.management.text.file.uploaded.server.address'  text='此文件上传到服务器地址：' />");
						console.info(response);
						$("#uploadInf").append("<p><@i18n.messageText code='file.resource.management.text.file.uploaded.success.address'  text='上传成功，文件地址是：' />" + response + "</p>");
					},
					onFailure: function(file, response){          // 文件上传失败的回调方法
						console.info("<@i18n.messageText code='file.resource.management.text.file.uploaded.failed'  text='此文件上传失败：' />");
						console.info(file.name);
					},
					onComplete: function(response){           	  // 上传完成的回调方法
						console.info("<@i18n.messageText code='file.resource.management.text.file.upload.complete'  text='文件上传完成' />");
						console.info(response);
					}
				});
				
			});
		
		</script> 
	</body>
</html>







