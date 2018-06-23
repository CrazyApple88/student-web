<#import "spring.ftl" as i18n/>
<#assign jsVersion="${.now?string('yyyyMMdd')}" />
<#-- 定义调试的开关 -->
<#assign isDebug=true />

<script>
var context ="${context}",language="${.locale}";
</script>

	<#-- *********** 公共的js集合*************** -->
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
	<#-- 日期选择插件 -->
	<script type="text/javascript" src="${context}/assets/plug/dataPicker/WdatePicker.js?v=${jsVersion}"></script>
	<#-- 弹出框/提示框js -->	
	<script type="text/javascript" src="${context}/assets/plug/prompt/js/prompt-1.0.0.min.js?v=${jsVersion}"></script>
	<#-- 弹出层js -->	
	<script type="text/javascript" src="${context}/assets/plug/layer/js/layer-1.0.0.min.js?v=${jsVersion}"></script>
	<#-- 滚动条-->
	<script type="text/javascript" src="${context}/assets/plug/scroll/js/jquery.nanoscroller-1.0.0.min.js?v=${jsVersion}"></script>
	<!-- <script type="text/javascript" src="${contextCss}/assets/js/bootstrap.min.js?v=${cssVersion}"></script> -->
	<script type="text/javascript" src="${context}/assets/js/main-1.0.0.min.js?v=${jsVersion}"></script>
	
	<#-- *********** 列表页面/编辑页面js集合*************** -->
	<#-- 分页js -->
	<script type="text/javascript" src="${context}/assets/plug/page/js/common-page.js?v=${jsVersion}"></script>
	<#-- 字段排序js -->
	<script type="text/javascript" src="${context}/assets/plug/tableSort/js/tabsort-1.0.0.min.js?v=${jsVersion}"></script>
	<#--  jquery 的校验方法的文件 -->
	<script type="text/javascript" src="${context}/assets/js/jquery/jquery.validate.js?v=${jsVersion}"></script>
	<#--  校验公共外接方法 -->
	<script type="text/javascript" src="${context}/assets/js/public-validate-method-1.0.0.min.js?v=${jsVersion}"></script>
	<#--  表单提交插件 -->
	<script type="text/javascript" src="${context}/assets/js/jquery/jquery.form.js?v=${jsVersion}"></script>
		
		
	<#-- *********** 登录后首页js*************** -->
	<#macro configIndexJs>
		<#-- 登录后首页引用到的js -->
	    <script type="text/javascript" src="${context}/assets/js/index-1.0.0.min.js?v=${jsVersion}"></script>
	    <script type="text/javascript" src="${context}/assets/plug/menu/js/menu.js?v=${jsVersion}"></script>
	</#macro>
	
	<#-- *********** 《插件》 jquery 树集合*************** -->
	<#macro configjQueryzTreeJs>
		<#-- jquery 树插件 -->
		<link rel="stylesheet" href="${context}/assets/plug/zTree/css/zTreeStyle.css?v=${jsVersion}" type="text/css">
		<script type="text/javascript" src="${context}/assets/plug/zTree/js/jquery.ztree.core-3.5.min.js?v=${jsVersion}"></script>
		<script type="text/javascript" src="${context}/assets/plug/zTree/js/jquery.ztree.excheck-3.5.min.js?v=${jsVersion}"></script>
		<script type="text/javascript" src="${context}/assets/plug/zTree/js/jquery.ztree.exedit-3.5.min.js?v=${jsVersion}"></script>
		
	</#macro>
	<#-- *********** 《插件》 数据多选multiselect*************** -->
	<#macro multiselectPlugJs>
		<script type="text/javascript" src="${context}/assets/plug/multiselect/js/multiselect.min.js?v=${jsVersion}"></script>
	</#macro>
	<#-- *********** 《插件》 三级联动插件*************** -->
	<#macro lendonPlugJs>
		<script type="text/javascript" src="${context}/assets/plug/lendon/js/lendon-1.0.0.min.js?v=${jsVersion}"></script>
		<script type="text/javascript" src="${context}/assets/plug/lendon/js/provinceData-1.0.0.min.js?v=${jsVersion}"></script>
	</#macro>
	<#-- *********** 《插件》 加载遮罩层 loading*************** -->
	<#macro loadingPlugJs>
		<link rel="stylesheet" href="${contextCss}/assets/plug/loading/css/loading.css?v=${jsVersion}"/>
		<script type="text/javascript" src="${context}/assets/plug/loading/js/loading-1.0.0.min.js?v=${jsVersion}"></script>
	</#macro>
	<#-- *********** 《插件》 打印 print*************** -->
	<#macro jQueryprintPlugJs>
		<script type="text/javascript" src="${context}/assets/plug/print/js/jQuery.print-1.0.0.min.js?v=${jsVersion}"></script>
	</#macro>
	<#-- *********** 《插件》 下拉选择插件*************** -->
	<#macro selectPlugJs>
		<link rel="stylesheet" href="${contextCss}/assets/plug/select/css/select.css?v=${jsVersion}"/>
		<script type="text/javascript" src="${context}/assets/plug/select/js/select-1.0.0.min.js?v=${jsVersion}"></script>
	</#macro>
	<#-- *********** 《插件》 分屏*************** -->
	<#macro splitScreenPlugJs>
		<link rel="stylesheet" href="${contextCss}/assets/plug/splitScreen/css/splitScreen.css?v=${jsVersion}"/>
		<script type="text/javascript" src="${context}/assets/plug/splitScreen/js/splitScreen-1.0.0.min.js?v=${jsVersion}"></script>
	</#macro>
	
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
	
	
	