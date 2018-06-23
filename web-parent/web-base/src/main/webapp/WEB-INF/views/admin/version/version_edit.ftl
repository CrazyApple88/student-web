<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <#import "spring.ftl" as i18n/>
    <script type="text/javascript" charset="utf-8" src="${context}/assets/plug/ueditor/ueditor.config.js?v=${jsVersion}"></script>
    <script type="text/javascript" charset="utf-8" src="${context}/assets/plug/ueditor/ueditor.all.min.js?v=${jsVersion}"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="${context}/assets/plug/ueditor/lang/zh-cn/zh-cn.js?v=${jsVersion}"></script>
    <script type="text/javascript">
  	$(document).ready(function(){
		var toolTip = new diaPrompt();
		// validate signup form on keyup and submit
		$("#form_post").validate({
			submitHandler : function(form) { //验证通过后的执行方法
				var id = $("[name='id']").val();
				if (id != '') {
					$('#form_post').attr("action", "/admin/version/update");
				}
				
				//当前的form通过ajax方式提交（用到jQuery.Form文件）
				$(form).ajaxSubmit({
					dataType : "json",
					success : function(json) {
						if (json.code == 1) {
							toolTip.tips({
								timer:true,/*是否开启定时器*/
					            sec:2,/*倒计时的秒数*/
	    	    	            message:"<@i18n.messageText code='business.management.text.save.sucessfully'  text='保存成功!' />",
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
			},
			focusInvalid : true, //验证提示时，鼠标光标指向提示的input
			rules : {
				name : {
					required : true,
					maxlength : 32
				},
				code : {
					required : true,
					maxlength : 32
				},
				updateBy : {
					maxlength : 16
				}
			},
			messages : {
				name : {
					required : "<@i18n.messageText code='version.management.form.confirm.version.name.empty'  text='版本名称不能为空' />",
					rangelength : "<@i18n.messageText code='version.management.form.confirm.version.name.length'  text='版本名称长度不能超过32' />"
				},
				code : {
					required : "<@i18n.messageText code='version.management.form.confirm.version.number.empty'  text='版本号不能为空' />",
					maxlength : "<@i18n.messageText code='version.management.form.confirm.version.number.length'  text='版本号长度不能超过32' />",
				},
				updateBy : {
					maxlength : "<@i18n.messageText code='version.management.form.confirm.submission.length'  text='提交人长度不能超过32' />"
				}
			}
		});
	});
  	
  	//实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
  	
</script>
</head>
<body>
<div class="main-container">
	<div class="tabContainer">
    	<form action="${context}/admin/version/save" method="post" id="form_post" name="form_post">
			<input type="hidden" value="${entity.id}" id="entityId" name="id" >
	            <ul class="details_box">
	                <li><span> <@i18n.messageText code='version.management.text.version.name'  text='版本名称' /> <font color="red">*</font></span><input type="text" class="inp_detail" name="name" value="${entity.name}"></li>
                    <li><span> <@i18n.messageText code='process.instance.administrator.text.version.number'  text='版本号' /> <font color="red">*</font></span><input type="text" class="inp_detail" name="code" value="${entity.code}"></li>
                    <li><span> <@i18n.messageText code='version.management.text.submitter'  text='提交人' /> </span><input type="text"  class="inp_detail" name="updateBy"  value="${entity.updateBy}"></li>
                    <li><span> <@i18n.messageText code='version.management.text.update.time'  text='更新时间' /> </span><input id="updateDate" class="inp_detail" name="updateDate" class="Wdate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" <#if entity.updateDate??>value="${entity.updateDate?string('yyyy-MM-dd HH:mm:ss') }"</#if>/></li>
                    <li><span> <@i18n.messageText code='dictionary.management.text.dictionary.note.description'  text='备注' /> </span><input type="text"  class="inp_detail" name="remark"  value="${entity.remark}"></li>
                    <li><span> <@i18n.messageText code='version.management.text.version.update.information'  text='更新信息' /> </span></li>
            	</ul>
            	<div>
    				<textarea id="editor" name="updateInfo" style="width: 880px; height: 400px; text-align:center;margin: 0 auto;">${entity.updateInfo}</textarea>
				</div>
                <div class="class_btn_li" style="text-align:center;margin:10px 0 15px 0;">
                  	<input type="submit" value="<@i18n.messageText code='common.button.quit.true'  text='确定' />" class="save fbtn btn_sm ensure_btn" >
                  	<input type="button" class="cancel fbtn btn_sm cancle_btn" value="<@i18n.messageText code='common.button.quit.false'  text='关闭' />" onclick="javascript:delDialog();">
                </div>
			</form>
        </div>
    </div>
</div>
</body>
</html>