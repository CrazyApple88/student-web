<!DOCTYPE html>
<#import "spring.ftl" as i18n/>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>登录首页</title>
	<link rel="stylesheet" href="${context}/assets/css/common.css?v=${cssVersion}"/>
	<link rel="stylesheet" href="${context}/assets/theme/cambridgeblue/css/style.css?v=${cssVersion}"/>
</head>
<body>
<div class="main-container">
    <div class="serContainer" align="center" style="background:#fff;widrh:100%;height:100%;">
        <div class="welcont"></div>
        <font class="welcome_page"> <@i18n.messageText code='common.text.welcome'  text='欢迎' /> 【<font color="red">${_online_user.name}</font>】 <@i18n.messageText code='log.in.daily.text.login.system'  text='登录系统' /></font>
    </div>
</div>
</body>
</html>