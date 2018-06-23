<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    
    <#include "/system/common_css.ftl">
    <#include "/system/common_js.ftl">
    <style>
    	.details_box li{
    		margin: 11px 0 !important;
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
						$('#form_post').attr("action", "/admin/dict/update");
					}
					//当前的form通过ajax方式提交（用到jQuery.Form文件）
					$(form).ajaxSubmit({
						dataType : "json",
						success : function(json) {
							if (json.code == 1) {
								toolTip.tips({
									timer:true,/*是否开启定时器*/
						            sec:2,/*倒计时的秒数*/
									message:"<@i18n.messageText code='business.management.text.save.sucessfully'  text='保存成功' />",
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
					dictName : {
						required : true,
						rangelength : [ 1, 32 ],
						spaceCheck : "dictName"
					},
					dictCode : {
						maxlength : 32,
						regex : "^\\w+$",
						remote : {
							url : "/admin/dict/validateDictCode", //后台处理程序
							type : "post", //数据发送方式
							dataType : "json", //接受数据格式   
							data : { //要传递的数据
								dictCode : function() {
									return $("#dictCode").val();
								},
								id : function() {
									return $("[name='id']").val();
								},
								typeId : function() {
									return $("#typeId").val();
								}
							},
							dataFilter : function(data) { //返回结果
								var json = eval('(' + data + ')');
								if (json.data == "y")//存在yes
									return false;
								else
									return true;//不存在no
							}
						}
					},
					sort : {
						digits : true,
						range : [0,999]
					},
					intro : {
						maxlength : 128
					}
				},
				messages : {
					dictName : {
						required : "<@i18n.messageText code='dictionary.management.form.comfirm.dictionary.name.empty'  text='字典名称不能为空' />",
						rangelength : "<@i18n.messageText code='dictionary.management.form.comfirm.dictionary.name.length'  text='字典名称长度必须在1-32之间' />",
						spaceCheck : "<@i18n.messageText code='dictionary.management.form.comfirm.dictionary.name.space'  text='字典名称不能以空格开头' />"
					},
					dictCode : {
						maxlength : "<@i18n.messageText code='dictionary.management.form.comfirm.dictionary.code.length'  text='字典编码长度不能超过32' />",
						regex : "<@i18n.messageText code='dictionary.management.form.comfirm.dictionary.code.consist'  text='字典编码只能是数字、字母与下划线' />",
						remote : "该字典编号已存在!"
					},
					sort : {
						digits : "<@i18n.messageText code='dictionary.management.form.comfirm.sequence.number.integer'  text='排序序号必须为整数' />",
						range : "<@i18n.messageText code='dictionary.management.form.comfirm.sequence.number.ranges'  text='排序序号范围0-999' />"
					},
					intro : {
						maxlength : "<@i18n.messageText code='dictionary.management.form.comfirm.note.length'  text='备注描述长度不能超过128' />"
					}
				}
			});
		});
	</script>
</head>
<body>
	<div class="main-container">
	    <div class="tabContainer">
	        <form action="/admin/dict/save" method="post" id="form_post" name="form_post">
	           	<input type="hidden" value="${dict.id }"  name="id" id="id">
	           	<input type="hidden" value="${dict.typeId }"  name="typeId" id="typeId">
                <ul class="details_box">
                    <li><span> <@i18n.messageText code='dictionary.management.text.dictionary.type'  text='字典类型' /> </span><span class="dictionary_name">${dicttype.typeName }</span></li>
                	<li><span> <@i18n.messageText code='dictionary.management.text.dictionary.name'  text='字典名称' /> <font color="red">*</font></span><input type="text" class="inp_detail" placeholder="<@i18n.messageText code='dictionary.management.text.enter.dictionary.name'  text='请输入字典名称' />" name="dictName" id="dictName" value="${dict.dictName }"></li>
                    <li><span> <@i18n.messageText code='dictionary.management.text.dictionary.number'  text='字典编号' /> </span><input type="text"  class="inp_detail" name="dictCode" id="dictCode" value="${dict.dictCode }"></li>
                	<li><span> <@i18n.messageText code='dictionary.management.text.dictionary.sequence'  text='排序' /> </span><input type="text"  class="inp_detail" name="sort" id="sort" value="${dict.sort }"></li>
               		<li><span> <@i18n.messageText code='dictionary.management.text.dictionary.note.description'  text='备注描述' /> </span><input type="text"  class="inp_detail" name="intro" id="intro" value="${dict.intro }"></li>
                    <li class="class_btn_li">
                    	<input type="submit" class="saveDictId fbtn btn_sm ensure_btn" id="saveDictId" value="<@i18n.messageText code='common.button.quit.true'  text='确定' />">
                    	<input type="button" class="cancel fbtn btn_sm cancle_btn" value="<@i18n.messageText code='common.button.quit.false'  text='关闭' />" onclick="javascript:delDialog()">
                    </li>
                </ul>
			</form>
	    </div>
	</div>
</body>
</html>