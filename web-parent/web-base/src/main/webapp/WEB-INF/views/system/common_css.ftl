<#assign cssVersion="${.now?string('yyyyMMdd')}" />
<#-- 定义调试的开关 -->
<#assign isDebug=true />

<script>
var contextCss ="${context}",language="${.locale}";
</script>
	<#-- *********** 公共的css集合,默认全部引用*************** -->
    <#-- (清除浏览器的默认样式) -->
    <link rel="stylesheet" href="${contextCss}/assets/css/base.css?v=${cssVersion}"/>
    <#-- (一些公用样式的文件：比如表格按钮) -->
	<link rel="stylesheet" href="${contextCss}/assets/css/common.css?v=${cssVersion}"/>
    <#-- (以前表格按钮的样式) -->
    <link rel="stylesheet" href="${contextCss}/assets/css/plug.css?v=${cssVersion}"/>
	<#-- 弹出框 css -->
    <link rel="stylesheet" href="${contextCss}/assets/plug/prompt/css/prompt_base.css?v=${cssVersion}"/>
    <#-- 弹出层css -->	
    <link rel="stylesheet" href="${contextCss}/assets/plug/layer/css/layer-base.css?v=${cssVersion}"/>
    <#-- 滚动条css -->
    <link rel="stylesheet" href="${contextCss}/assets/plug/scroll/css/nanoscroller.css?v=${cssVersion}"/>
    
	<#-- *********** 页面css集合*************** -->
   	<link rel="stylesheet" href="${contextCss}/assets/css/bootstrap.min.css?v=${cssVersion}"/>
    <#-- 分页 css -->
    <link rel="stylesheet" href="${contextCss}/assets/plug/page/css/page_base.css?v=${cssVersion}"/>
    <#-- 字段排序css -->
    <link rel="stylesheet" href="${contextCss}/assets/plug/tableSort/css/tab.css?v=${cssVersion}"/>
	
	<#-- *********** 菜单library *************** -->
	<#macro menuLibraryCss>
    	<link rel="stylesheet" href="${contextCss}/assets/menu-library/css/menu-library.css?v=${cssVersion}"/>
	</#macro>
	