<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>企业管理(编辑)</title>
    
    <#import "spring.ftl" as i18n/>
  
    <script type="text/javascript">
		$().ready(function() {
			var toolTip = new diaPrompt();
			// validate signup form on keyup and submit
			$("#form_post").validate({
				submitHandler : function(form) { //验证通过后的执行方法
					var id = $("[name='id']").val();
					if (id != '') {
						$('#form_post').attr("action", "/admin/company/update");
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
					compName : {
						required : true,
						rangelength : [ 1, 128 ],
						spaceCheck : "compName"
					},
					compCode : {
						maxlength : 32,
						regex : "^\\w+$"
					},
					nameAlias : {
						maxlength : 32
					},
					personName : {
						maxlength : 16
					},
					personMobile : {
						maxlength : 32,
						regex : "^[0-9+-]+$"
					},
					longitude : {
						maxlength : 32,
					},
					latitude : {
						maxlength : 32,
					},
					compPhone : {
						maxlength : 32,
						regex : "^[0-9+-]+$"
					},
					compAddr : {
						maxlength : 128
					}
				},
				messages : {
					compName : {
						required : "<@i18n.messageText code='business.management.form.confirm.bussiness.name.empty'  text='企业名称不能为空' />",
						rangelength : "<@i18n.messageText code='business.management.form.confirm.bussiness.name.length'  text='企业名称长度必须在1-128之间' />",
						spaceCheck : "<@i18n.messageText code='business.management.form.confirm.bussiness.name.begin'  text='企业名称不能以空格开头' />"
					},
					compCode : {
						maxlength : "<@i18n.messageText code='business.management.form.confirm.business.code.length'  text='企业编码长度不能超过18' />",
						regex : "<@i18n.messageText code='business.management.form.confirm.business.code.consist'  text='企业编码只能由数字、字母和下划线组成' />"
					},
					nameAlias : {
						maxlength : "<@i18n.messageText code='business.management.form.confirm.business.short.name.length'  text='企业简称长度不能超过32' />"
					},
					personName : {
						maxlength : "<@i18n.messageText code='business.management.form.confirm.leader.name.length'  text='负责人长度不能超过16' />"
					},
					personMobile : {
						maxlength : "<@i18n.messageText code='business.management.form.confirm.leader.contact.way.length'  text='负责人联系方式长度不能超过32' />",
						regex : "<@i18n.messageText code='business.management.form.confirm.leader.contact.way.consist'  text='负责人联系方式只能由数字、+和-组成' />"
					},
					longitude : {
						maxlength : "<@i18n.messageText code='business.management.form.confirm.longitude.length'  text='经度长度不能超过32' />",
					},
					latitude : {
						maxlength : "<@i18n.messageText code='business.management.form.confirm.latitude.length'  text='纬度长度不能超过32' />",
					},
					compPhone : {
						maxlength : "<@i18n.messageText code='business.management.form.confirm.business.phone.length'  text='企业电话长度不能超过32' />",
						regex : "<@i18n.messageText code='business.management.form.confirm.business.phone.consist'  text='企业电话只能由数字、+和-组成' />"
					},
					compAddr : {
						maxlength : "<@i18n.messageText code='business.management.form.confirm.business.address.length'  text='企业地址长度不能超过128' />"
					}
				}
			});
		});
		
		
		$('#isReal').change(function(){ 
			var p1=$('#isReal option:selected').val();//这就是 selected 的值 
    		if(p1 == "2"){
    			$("#realDtTr").hide();
    		}else{
    			$("#realDtTr").show();
    		}
		});
		$('#isReal').change();
	</script>
    
</head>
<body>

<div class="main-container">
    <div class="tabContainer">
       
            <form action="/admin/company/save" method="post" id="form_post" name="form_post">
            	<input type="hidden" value="${company.id }"  name="id" id="id">
	         
	                <ul class="details_box">
	                	<li><span> <@i18n.messageText code='business.management.text.business.name'  text='企业名称' /> <font color="red">*</font></span><input type="text" class="inp_detail" name="compName" id="compName" value="${company.compName }" ></li>
	                    <li><span> <@i18n.messageText code='business.management.text.business.code'  text='企业编号' /> </span><input type="text" class="inp_detail" name="compCode" id="compCode" value="${company.compCode }"></li>
	                    <li><span> <@i18n.messageText code='business.management.text.business.short.name'  text='企业简称' /> </span><input type="text" class="inp_detail" name="nameAlias" id="nameAlias" value="${company.nameAlias }"></li>
	                	<li><span> <@i18n.messageText code='menu.management.text.usable.or.disabled'  text='是否可用' /> </span>
	                		<select class="det_select" id="useable" name="useable">
								<option value="1" <#if company.useable == '1'> selected </#if>> <@i18n.messageText code='business.management.select.usable'  text='可用' /> </option>
								<option value="2" <#if company.useable == '2'> selected </#if>> <@i18n.messageText code='business.management.select.disabled'  text='不可用' /> </option>
							</select>
	                	</li>
	                	<li><span> <@i18n.messageText code='business.management.text.leader.name'  text='负责人姓名' /> </span><input type="text" class="inp_detail" name="personName" id="personName" value="${company.personName }"></li>
	                    <li><span> <@i18n.messageText code='business.management.text.contact.information'  text='联系方式' /> </span><input type="text" class="inp_detail" name="personMobile" id="personMobile" value="${company.personMobile }"></li>
	                    <li><span> <@i18n.messageText code='business.management.text.longitude'  text='经度' /> </span><input type="text" class="inp1 inp_detail" name="longitude" id="longitude" value="${company.longitude }"></li>
	                	<li><span> <@i18n.messageText code='business.management.text.latitude'  text='纬度' /> </span><input type="text" class="inp1 inp_detail" name="latitude" id="latitude" value="${company.latitude }"></li>
	                	<li><span> <@i18n.messageText code='business.management.text.business.phone'  text='企业电话' /> </span><input type="text" class="inp1 inp_detail" name="compPhone" id="compPhone" value="${company.compPhone }"></li>
	                    <li><span> <@i18n.messageText code='business.management.text.business.address'  text='企业地址' /> </span><input type="text" class="inp1 inp_detail" name="compAddr" id="compAddr" value="${company.compAddr }"></li>
	                	<li><span> <@i18n.messageText code='business.management.text.business.real.name.certification.or.not'  text='business.management.text.business.real.name.certification.or.not=是否实名认证' /> </span>
	                		<select class="det_select" id="isReal" name="isReal">
								<option value="2" <#if company.isReal == '2'> selected </#if>> <@i18n.messageText code='common.text.false'  text='否' /> </option>
								<option value="1" <#if company.isReal == '1'> selected </#if>> <@i18n.messageText code='common.text.true'  text='是' /> </option>
							</select>
	                	</li>
	                    <li id="realDtTr"><span> <@i18n.messageText code='business.management.text.business.real.name.certification.time'  text='business.management.text.business.real.name.certification.time=实名认证时间' /> </span><input id="realDt" name="realDt" class="Wdate inp_detail" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" <#if company.realDt??>value="${company.realDt?string('yyyy-MM-dd') }"</#if>/></li>
	                    <li  class="class_btn_li">
	                    <input type="submit" class="saveCompanyId fbtn btn_sm ensure_btn" id="saveCompanyId" value="<@i18n.messageText code='common.button.quit.true'  text='确定' />">
	                    <input type="button" class="cancel fbtn btn_sm cancle_btn" value="<@i18n.messageText code='common.button.quit.false'  text='关闭' />" onclick="javascript:delDialog()">
	                    </li>
	                </ul>
	            
			</form>
       
    </div>
</div>
</body>
</html>