<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <#import "spring.ftl" as i18n/>
</head>
<body>
<div class="main-container">
	<div class="tabContainer">
    	<form action="${context}/admin/version/save" method="post" id="form_post" name="form_post">
			<input type="hidden" value="${entity.id}" id="entityId" name="id" >
	            <ul class="details_box">
	                <li><span> <@i18n.messageText code='version.management.text.version.name'  text='版本名称' /> <font color="red">*</font></span><input type="text" class="inp_detail" name="name" value="${entity.name}" disabled="disabled"></li>
                    <li><span> <@i18n.messageText code='process.instance.administrator.text.version.number'  text='版本号' /> <font color="red">*</font></span><input type="text" class="inp_detail" name="code" value="${entity.code}" disabled="disabled"></li>
                    <li><span> <@i18n.messageText code='version.management.text.submitter'  text='提交人' /> </span><input type="text"  class="inp_detail" name="updateBy"  value="${entity.updateBy}" disabled="disabled"></li>
                    <li><span> <@i18n.messageText code='version.management.text.update.time'  text='更新时间' /> </span><input id="updateDate" class="inp_detail" name="updateDate" class="Wdate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" <#if entity.updateDate??>value="${entity.updateDate?string('yyyy-MM-dd HH:mm:ss') }"</#if> disabled="disabled"/></li>
                    <li class="version_message">${entity.updateInfo}</li>
                    <li>${entity.updateInfo}</li>
            	</ul>
			</form>
        </div>
    </div>
</div>
</body>
</html>