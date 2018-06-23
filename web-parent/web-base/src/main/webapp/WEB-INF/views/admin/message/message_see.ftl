<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
	<#include "/system/common_js.ftl">
	<script type="text/javascript">
	$().ready(function() {
    	top.refreshMsgNum();
    	delDialogSearch();
	})
	</script>
	<style type="text/css">
		.details_box li, .details_both li{
			height:auto;
			overflow:hidden;
		}
	</style>
</head>
<body>
<div class="main-container">
	<div class="tabContainer">
    	<form action="${context}/admin/message/save" method="post" id="form_post" name="form_post">
			<input type="hidden" value="${entity.id}" id="entityId" name="id" >
	            <ul class="details_box">
	                <li><span> <@i18n.messageText code='message.management.text.message.type'  text='消息类型' /> <font color="red">*</font></span><input type="text" class="inp_detail" name="type" <#if entity.type=="0"> value="<@i18n.messageText code='message.management.text.private.message'  text='私信' />" </#if> <#if entity.type=="1"> value="<@i18n.messageText code='message.management.text.system.message'  text='系统消息' />"</#if> disabled="disabled"/></li>
                    <li><span> <@i18n.messageText code='message.management.text.message.sender'  text='消息发送者' /> <font color="red">*</font></span><input type="text"  class="inp_detail" name="createBy"  value="${entity.createBy}" disabled="disabled"></li>
	                <li><span> <@i18n.messageText code='message.management.text.create.time'  text='创建时间' /> <font color="red">*</font></span><input id="createTime" type="text" class="inp_detail" name="createTime" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" <#if entity.createTime??>value="${entity.createTime?string('yyyy-MM-dd HH:mm:ss') }"</#if> disabled="disabled"/></li>
	                <li><span> <@i18n.messageText code='message.management.text.message.header'  text='消息标题' /> <font color="red">*</font></span><textarea id="title" name="title" style="width: 300px; height: 100px; text-align:left;margin: 0 auto;" disabled="disabled">${entity.title}</textarea></li>
                    <#if type!=1>                    
                    <li><span> <@i18n.messageText code='message.management.text.message.receiver'  text='消息接收者' /> <font color="red">*</font></span><textarea id="users" name="users" style="width: 300px; height: 100px; text-align:left;margin: 0 auto;" disabled="disabled">${entity.users}</textarea></li>
					</#if>
					<li><span> <@i18n.messageText code='message.management.text.message.content'  text='消息内容' /> <font color="red">*</font></span><textarea id="content" name="content" style="width: 300px; height: 200px; text-align:left;margin: 0 auto;" disabled="disabled">${entity.content}</textarea></li>
            	</ul>
			</form>
        </div>
    </div>
</div>
</body>
</html>