<!DOCTYPE html>
<#import "spring.ftl" as i18n/>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="/assets/css/base.css?v=${cssVersion}"/>
    <link rel="stylesheet" href="/assets/css/base_my.css?v=${cssVersion}"/>
    <link rel="stylesheet" href="/assets/css/common.css?v=${cssVersion}"/>
    <script type="text/javascript">
    function logout(){
    	top.getLogout();
    }
    </script>
</head>
<body>
    <div class="error_cont">
        <div class="error_box err_403">
            <div class="lg_a">
                <a href="javascript:history.go(-1);location.reload();"> <@i18n.messageText code='common.text.back'  text='返回上一页' /> </a>
                <a href="javascript:logout()"> <@i18n.messageText code='common.text.back.homepage'  text='返回首页' /> </a>
            </div>
        </div>
    </div>
</body>