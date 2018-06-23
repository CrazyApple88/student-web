<#import "spring.ftl" as i18n/>
<#assign jsVersion="${.now?string('yyyyMMdd')}" />
<#-- 定义调试的开关 -->
<#assign isDebug=true />

<script>
var context ="${context}",language="${.locale}";
</script>
	<#if isDebug>
	    <#-- (清除浏览器的默认样式) -->
	    <link rel="stylesheet" href="${context}/assets/css/base.css?v=${jsVersion}"/>
	    <#-- (一些公用样式的文件：比如表格按钮) -->
		<link rel="stylesheet" href="${context}/assets/css/common.css?v=${jsVersion}"/>
	    <#-- (首页用到) -->
	    <link rel="stylesheet" href="${context}/assets/css/base_my.css?v=${jsVersion}"/>
		<#-- 弹出框 css -->
	    <link rel="stylesheet" href="${context}/assets/plug/prompt/css/prompt_base.css?v=${jsVersion}"/>
	    <#-- 弹出层css -->	
	    <link rel="stylesheet" href="${context}/assets/plug/layer/css/layer-base.css?v=${jsVersion}"/>
		<#-- (字体图标首页用到) -->
	    <link rel="stylesheet" href="${context}/assets/css/iconfont/iconfont.css?v=${jsVersion}"/>
	    <link rel="stylesheet" href="${context}/assets/menu-library/css/menu-library.css?v=${jsVersion}">
	   	<link rel="stylesheet" href="${context}/assets/plug/menu/css/menu.css?v=${jsVersion}">
		
		<#-- jquery 的基本js方法 -->
		<script type="text/javascript" src="${context}/assets/js/jquery/jquery-1.10.0.min.js?v=${jsVersion}"></script>
		<#-- 兼容ie的input的placeholder属性的js -->
		<script type="text/javascript" src="${context}/assets/js/jquery/jquery.placeholder.js?v=${jsVersion}"></script>
		<#-- 应用迁移辅助插件,因为cleverTabs文件里的方法有用到jquery低版本的方法 -->
		<script type="text/javascript" src="${context}/assets/js/jquery/jquery-migrate-1.2.1.js?v=${jsVersion}"></script>
		<#-- 框架的js,首页tab标签js -->
		<script type="text/javascript" src="${context}/assets/js/jquery/jquery.cleverTabs.js?v=${jsVersion}"></script>
		<#-- 和cleverTabs文件连用 -->
		<script type="text/javascript" src="${context}/assets/js/jquery/jquery.json-2.2.js?v=${jsVersion}"></script>	
		<#-- 所有页面都需要引用的js,全局 -->
		<script type="text/javascript" src="${context}/assets/js/public-method.js?v=${jsVersion}"></script>
		<script type="text/javascript" src="${context}/assets/js/main.js?v=${jsVersion}"></script>
		<#-- 登录后首页引用到的js -->
	    <script type="text/javascript" src="${context}/assets/js/index.js?v=${jsVersion}"></script>
	    
	    <script type="text/javascript" src="${context}/assets/js/menu.js?v=${jsVersion}"></script>
		<#-- 弹出框/提示框js -->	
		<script type="text/javascript" src="${context}/assets/plug/prompt/js/prompt.js?v=${jsVersion}"></script>
		<#-- 弹出层js -->	
		<script type="text/javascript" src="${context}/assets/plug/layer/js/layer.js?v=${jsVersion}"></script>
	    <#-- websocket需要的js -->
	    <!-- <script src="${context}/assets/js/Message.js"></script> -->
	<#else>
	    <#-- (清除浏览器的默认样式) -->
	    <link rel="stylesheet" href="${context}/assets/css/base-1.0.0.min.css?v=${jsVersion}"/>
	    <#-- (一些公用样式的文件：比如表格按钮) -->
		<link rel="stylesheet" href="${context}/assets/css/common-1.0.0.min.css?v=${jsVersion}"/>
	    <#-- (首页用到) -->
	    <link rel="stylesheet" href="${context}/assets/css/base_my-1.0.0.min.css?v=${jsVersion}"/>
		<#-- (字体图标首页用到) -->
	    <link rel="stylesheet" href="${context}/assets/css/iconfont/iconfont.css?v=${jsVersion}"/>
	    <link rel="stylesheet" href="${context}/assets/menu-library/css/menu-library-1.0.0.min.css?v=${jsVersion}">
	   	<link rel="stylesheet" href="${context}/assets/plug/menu/css/menu-1.0.0.min.css?v=${jsVersion}">
		<#-- 弹出框 css -->
	    <link rel="stylesheet" href="${context}/assets/plug/prompt/css/prompt_base-1.0.0.min.css?v=${jsVersion}"/>
	    <#-- 弹出层css -->	
	    <link rel="stylesheet" href="${context}/assets/plug/layer/css/layer-base-1.0.0.min.css?v=${jsVersion}"/>
		
		<#-- jquery 的基本js方法 -->
		<script type="text/javascript" src="${context}/assets/js/jquery/jquery-1.10.0.min.js?v=${jsVersion}"></script>
		<#-- 兼容ie的input的placeholder属性的js -->
		<script type="text/javascript" src="${context}/assets/js/jquery/jquery.placeholder.js?v=${jsVersion}"></script>
		<#-- 应用迁移辅助插件,因为cleverTabs文件里的方法有用到jquery低版本的方法 -->
		<script type="text/javascript" src="${context}/assets/js/jquery/jquery-migrate-1.2.1.js?v=${jsVersion}"></script>
		<#-- 框架的js,首页tab标签js -->
		<script type="text/javascript" src="${context}/assets/js/jquery/jquery.cleverTabs.js?v=${jsVersion}"></script>
		<#-- 和cleverTabs文件连用 -->
		<script type="text/javascript" src="${context}/assets/js/jquery/jquery.json-2.2.js?v=${jsVersion}"></script>	
		<#-- 所有页面都需要引用的js,全局 -->
		<script type="text/javascript" src="${context}/assets/js/public-method-1.0.0.min.js?v=${jsVersion}"></script>
		<script type="text/javascript" src="${context}/assets/js/main-1.0.0.min.js?v=${jsVersion}"></script>
		<#-- 登录后首页引用到的js -->
	    <script type="text/javascript" src="${context}/assets/js/index-1.0.0.min.js?v=${jsVersion}"></script>
	    <script type="text/javascript" src="${context}/assets/plug/menu/js/menu-1.0.0.min.js?v=${jsVersion}"></script>
		<#-- 弹出框/提示框js -->	
		<script type="text/javascript" src="${context}/assets/plug/prompt/js/prompt-1.0.0.min.js?v=${jsVersion}"></script>
		<#-- 弹出层js -->	
		<script type="text/javascript" src="${context}/assets/plug/layer/js/layer-1.0.0.min.js?v=${jsVersion}"></script>
	    <#-- websocket需要的js -->
	    <!-- <script src="${context}/assets/js/Message.js"></script> -->
	</#if>
	
	<#-- *********** 主题集合*************** -->
	<script type="text/javascript" >
	//JS使用的上下文,页面加载完成后执行主题
	   if(localStorage.getItem("src")){
		   var link = JSON.parse(localStorage.getItem("src"));
			$.revamptag("link","stylesheet","text/css",context+"/assets/theme/"+link.src+"/css/style.css");
		}else{
			var defaultTheme="blue";
			<@sysConfig key='default.theme' ; value,map>
				<#if value != 'null'>
			  		defaultTheme = "${value}";
			  	</#if>
			</@sysConfig>
			$.createtag("link","stylesheet","text/css",context+"/assets/theme/"+defaultTheme+"/css/style.css");
		}
	</script>
	
	
	