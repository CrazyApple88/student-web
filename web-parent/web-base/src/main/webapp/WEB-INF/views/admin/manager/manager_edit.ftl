<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<title></title>
<script type="text/javascript">
	$().ready(function() {
		var toolTip = new diaPrompt();
		// validate signup form on keyup and submit
		$("#form_post").validate({
			submitHandler : function(form) { //验证通过后的执行方法
				var id = $("#entityId").val();
				if (id != '') {
					$('#form_post').attr("action", "${context}/admin/manager/update");
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
				sysName : {
					required : true,
					maxlength : 256
				},
				copyright : {
					maxlength : 128
				},
				recordLicense : {
					maxlength : 128
				},
				logo : {
					maxlength : 256
				},
				compId : {
					maxlength : 32
				},
				wechatCode : {
					maxlength : 256
				},
				//日期不校验
			},
			messages : {
				sysName : {
					required : "系统名称不能为空",
					maxlength :"系统名称长度不能超过256"
				},
				copyright : {
					maxlength :"版权长度不能超过128"
				},
				recordLicense : {
					maxlength :"备案许可证长度不能超过128"
				},
				logo : {
					maxlength :"系统LOGO长度不能超过256"
				},
				compId : {
					maxlength :"企业ID长度不能超过32"
				},
				wechatCode : {
					maxlength :"微信二维码长度不能超过256"
				},
				//日期不校验
			}
		});	
	});
	$(document).ready(function(){
		var toolTip = new diaPrompt();
		//验证设施名称是否存在并提交_start-------
		$("#logoFile").change(function(){
			var file_image  = $("#logoFile").val();
			if(file_image == ""){
			toolTip.warn({
				timer:true,/*是否开启定时器*/
				sec:2,/*倒计时的秒数*/
				message:"请选择图片",
				callback:function(){}
			});
			return;
		}else{
			if(!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG|JPEG)$/.test(file_image)){
				toolTip.error({
					timer:true,/*是否开启定时器*/
					sec:2,/*倒计时的秒数*/
					message:"图片格式必须是 gif,jpeg,jpg,png",
					callback:function(){}
				});
				return false;
			}
		}
		var formData = new FormData($("#form_file" )[0]);
		$.ajax({
			url:"/admin/files/uploadFile",
			method:"post",
			data: formData, 
			dataType:"json" , 
			async: false,
			cache: false,
			contentType: false,
			processData: false,  
			success: function (json) {
				var data = json.data;
				if(json.code == 1){
					$("#logo").val(data[0].id);
				}else{
					toolTip.error({
						timer:true,/*是否开启定时器*/
						sec:2,/*倒计时的秒数*/
						message:"文件上传失败",
						callback:function(){}
					});
				}
			}
		});
	});
	//保存操作
	$(".saveLogo").click(function(){
		var logo = $("#logo").val();
// 		if(logo==""){
// 			toolTip.error({
// 				timer:true,/*是否开启定时器*/
// 				sec:2,/*倒计时的秒数*/
// 				message:"请选择logo图片",
// 				callback:function(){}
// 			});
// 			return ;
// 		}
		$("#form_post").submit();
	});
})
</script>
</head>
<body>
	<div class="content sg_layer_cont">
		<div class="details_cont">
			<form action="${context}/admin/manager/save" method="post" id="form_post" name="form_post">
				<input type="hidden" value="${entity.id}" id="entityId" name="id" >
				<input type="hidden" value="${entity.logo}" id="logo" name="logo" >
				<ul class="details_both details_box">
					<li>
						<span>系统名称<font color="red">*</font></span>
						<input type="text" class="inp_detail" name="sysName" id="sysName" value="${entity.sysName}"  placeholder="请输入系统名称" >
					</li>
					<li>
						<span>版权</span>
						<input type="text" class="inp_detail" name="copyright" id="copyright" value="${entity.copyright}"  placeholder="请输入版权" >
					</li>
					<li>
						<span>备案许可证</span>
						<input type="text" class="inp_detail" name="recordLicense" id="recordLicense" value="${entity.recordLicense}"  placeholder="请输入备案许可证" >
					</li>   
				</ul>
			</form>
			<ul class="details_both details_box">
				<li>
					<span>系统LOGO</span>
					<form action="/admin/files/uploadFile" method="post" id="form_file" enctype="multipart/form-data">
						<input type="file" class="inp_detail" name="file" id="logoFile" value=""  placeholder="请选择系统logo图片" >
					</form>
				</li>
				<div style="width:300px;height:80px">
					<img id="ImgPr" width="300px" height="80px" src="${entity.logo}"/>
				</div>
			</ul>
			<div style="width:700px;height:80px;float:left;padding-top:10px;text-align:center">
				<input type="button" btnType="saveLogo" value="确定" class="saveLogo fbtn btn_sm ensure_btn">
				<input type="button" value="关闭" btnType="cancel"  class="cancel fbtn btn_sm cancle_btn">
			</div>
		</div>
	</div>
	<script type="text/javascript">
		jQuery.fn.extend({
			uploadPreview: function(opts) {
				var _self = this,
				_this = $(this);
				opts = jQuery.extend({
					Img: "ImgPr",
					Width: 100,
					Height: 100,
					ImgType: ["gif", "jpeg", "jpg", "bmp", "png"],
					Callback: function() {}
				}, opts || {});
				_self.getObjectURL = function(file) {
					var url = null;
					if (window.createObjectURL != undefined) {
						url = window.createObjectURL(file)
					} else if (window.URL != undefined) {
						url = window.URL.createObjectURL(file)
					} else if (window.webkitURL != undefined) {
						url = window.webkitURL.createObjectURL(file)
					}
					return url
				};
				_this.change(function() {
					if (this.value) {
						if (!RegExp("\.(" + opts.ImgType.join("|") + ")$", "i").test(this.value.toLowerCase())) {
							alert("选择文件错误,图片类型必须是" + opts.ImgType.join("，") + "中的一种");
							this.value = "";
							return false
						}
						//高版本Jquey使用  if ($.support.leadingWhitespace)
						if ($.support.leadingWhitespace) { //低版本jquery中使用$.browser.msie
							try {
								$("#" + opts.Img).attr('src', _self.getObjectURL(this.files[0]))
							} catch (e) {
								var src = "";
								var obj = $("#" + opts.Img);
								var div = obj.parent("div")[0];
								_self.select();
								if (top != self) {
									window.parent.document.body.focus()
								} else {
									_self.blur()
								}
								src = document.selection.createRange().text;
								document.selection.empty();
								obj.hide();
								obj.parent("div").css({
									'filter': 'progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)',
									'width': opts.Width + 'px',
									'height': opts.Height + 'px'
								});
								div.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = src
							}
						} else {
							$("#" + opts.Img).attr('src', _self.getObjectURL(this.files[0]))
						}
						opts.Callback()
					}
				})
			}
		});
		$("#logoFile").uploadPreview({
			Img: "ImgPr"
		});
	</script>
</body>
</html>