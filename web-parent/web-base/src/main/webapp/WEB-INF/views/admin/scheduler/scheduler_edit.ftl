<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
	<#import "spring.ftl" as i18n/>
	<style>
    	.details_box li{
    		margin: 11px 0 !important;
    	}
    </style>
	<script type="text/javascript">
		$(document).ready(function(){
			var toolTip = new diaPrompt();
			// validate signup form on keyup and submit
			$("#form_post").validate({
				submitHandler : function(form) { //验证通过后的执行方法
					var id = $("[name='id']").val();
					if (id != '') {
						$('#form_post').attr("action", "/admin/scheduler/update");
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
				onkeyup:false,
				rules : {
					name : {
						required : true,
						rangelength : [ 1, 32 ],
					},
					corn : {
						required : true,
						maxlength : 50,
						remote : {
							url : "/admin/scheduler/validateCorn", //后台处理程序
							type : "post", //数据发送方式
							dataType : "json", //接受数据格式   
							data : { //要传递的数据
								code : function() {
									return $("#corn").val();
								},
								id : function() {
									return $("[name='id']").val();
								}
							},
							dataFilter : function(data) { //返回结果
								var json = eval('(' + data + ')');
								if (json.data == "true")//合法
									return true;
								else
									return false;//不存合法
							}
						}
					},
					parm : {
						maxlength : 500
					},
					entityClass : {
						maxlength : 150
					},
					repeatCount : {
						maxlength : 5
					},
					repeatInterval : {
						maxlength : 5
					}
				},
				messages : {
					name : {
						required : "<@i18n.messageText code='timer.management.form.confirm.timer.empty'  text='定时器名称不能为空' />",
						rangelength : "<@i18n.messageText code='timer.management.form.confirm.timer.length'  text='定时器名称长度必须在1-32之间' />",
					},
					corn : {
						required : "<@i18n.messageText code='timer.management.form.confirm.expression.empty'  text='表达式不能为空' />",
						maxlength : "<@i18n.messageText code='timer.management.form.confirm.expression.length'  text='表达式长度不能超过50' />",
						remote : "<@i18n.messageText code='timer.management.form.confirm.expression.legal'  text='表达式不合法！' />"
					},
					parm : {
						maxlength : "<@i18n.messageText code='timer.management.form.confirm.parameter.length'  text='参数长度不能超过500' />"
					},
					entityClass : {
						maxlength : "<@i18n.messageText code='timer.management.form.confirm.entity.class.length'  text='实体类长度不能超过150' />"
					},
					repeatCount : {
						maxlength : "<@i18n.messageText code='timer.management.form.confirm.repeated.executions.number'  text='重复执行次数的长度不能超过5' />"
					},
					repeatInterval : {
						maxlength : "<@i18n.messageText code='timer.management.form.confirm.interval.length'  text='执行间隔的长度不能超过5' />"
					}
				}
			});
			
			$('#jobType').change(function(){ 
				var p1=$('#jobType option:selected').val();//这就是 selected 的值 
	    		if(p1 == "cron"){
	    			$("#cronTr").show();
	    			$("#repeatCountTr").hide();
	    			$("#repeatIntervalTr").hide();
	    		}else{
	    			$("#cronTr").hide();
	    			$("#repeatCountTr").show();
	    			$("#repeatIntervalTr").show();
	    		}
			});
			$('#jobType').change();
		});
	</script>
	
</head>
<body>
<div class="main-container">
    <div class="tabContainer">
        <form action="${context}/admin/scheduler/save" method="post" id="form_post" name="form_post">
			<input type="hidden" value="${entity.id}" id="entityId" name="id" >
               <input type="hidden" class="inp1" name="count"  value="${entity.count!'0'}">
                <ul class="details_box">
                    <li><span> <@i18n.messageText code='timer.management.text.name'  text='名称' /> <font color="red">*</font></span><input type="text" class="inp_detail" name="name" value="${entity.name}"></li>
					<li><span> <@i18n.messageText code='timer.management.text.type'  text='类型' /> </span>
						<select class="det_select" name="jobType" id="jobType">
							<option <#if entity.jobType=="cron">selected</#if> value="cron"> <@i18n.messageText code='timer.management.text.expression'  text='表达式' /> </option>
							<option <#if entity.jobType=="repeat">selected</#if> value="repeat"> <@i18n.messageText code='timer.management.text.interval.repeat'  text='间隔重复' /> </option>
						</select>
					</li>
					<li id="cronTr"><span> <@i18n.messageText code='timer.management.text.expression'  text='表达式' /> <font color="red">*</font></span><input type="text" class="inp_detail" name="corn" value="${entity.corn}"></li>
                    <li><span> <@i18n.messageText code='timer.management.text.parameter'  text='参数' /> </span><input type="text"  class="inp_detail" name="parm"  value="${entity.parm}"></li>
                    <li><span> <@i18n.messageText code='timer.management.text.entity.class'  text='实体类' /> </span><input type="text" class="inp_detail" name="entityClass" value="${entity.entityClass}"></li>
					<li><span> <@i18n.messageText code='log.in.daily.button.starting.time'  text='开始时间' /> </span><input id="startTime" class="inp_detail" name="startTime" class="Wdate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" <#if entity.startTime??>value="${entity.startTime?string('yyyy-MM-dd HH:mm:ss') }"</#if>/></li>
                    <li><span> <@i18n.messageText code='log.in.daily.button.ending.time'  text='结束时间' /> </span><input id="entTime" class="inp_detail" name="entTime" class="Wdate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" <#if entity.entTime??>value="${entity.entTime?string('yyyy-MM-dd HH:mm:ss') }"</#if>/></li>
                    <li id="repeatCountTr" style="display: none;"><span> <@i18n.messageText code='timer.management.text.repetitive.execution.times'  text='重复执行次数' /> </span><input type="text" class="inp_detail" name="repeatCount" value="${entity.repeatCount}"></li>
                    <li id="repeatIntervalTr" style="display: none;"><span> <@i18n.messageText code='timer.management.text.execution.interval'  text='执行间隔' /> </span><input type="text" class="inp_detail" name="repeatInterval" value="${entity.repeatInterval}"></li>
                    <li class="class_btn_li">
                    	<input type="submit" value="<@i18n.messageText code='common.button.quit.true'  text='确定' />" class="save  fbtn btn_sm ensure_btn" >
                    	<input type="button" class="cancel fbtn btn_sm cancle_btn" value="<@i18n.messageText code='common.button.quit.false'  text='关闭' />" onclick="javascript:delDialog()">
                    </li>
                </ul>
		</form>
    </div>
</div>
</body>
</html>