<#import "spring.ftl" as i18n/>
<#assign cssVersion="${.now?string('yyyyMMdd')}" />
<#assign jsVersion="${.now?string('yyyyMMdd')}" />
<#-- 定义调试的开关 -->
<#assign isDebug=false />

<script>
var context ="${context}",language="${.locale}";
</script>

<link rel="stylesheet" href="${context}/assets/css/base-1.0.0.min.css?v=${cssVersion}" />
<link rel="stylesheet" href="${context}/assets/css/base_my-1.0.0.min.css?v=${cssVersion}" />
<link rel="stylesheet" href="${context}/assets/css/common-1.0.0.min.css?v=${cssVersion}" />


<script type="text/javascript" src="${context}/assets/js/jquery/jquery-1.10.0.min.js?v=${jsVersion}"></script>
<script type="text/javascript" src="${context}/assets/js/jquery/jquery.placeholder.js?v=${jsVersion}"></script>
<script type="text/javascript" src="${context}/assets/js/jquery/jquery-migrate-1.2.1.js?v=${jsVersion}"></script>
<script type="text/javascript" src="${context}/assets/js/jquery/jquery.cleverTabs.js?v=${jsVersion}"></script>
<script type="text/javascript" src="${context}/assets/js/jquery/jquery.json-2.2.js?v=${jsVersion}"></script>
<script type="text/javascript" src="${context}/assets/js/public-method-1.0.0.min.js?v=${jsVersion}"></script>
<script type="text/javascript" src="${context}/assets/js/main-1.0.0.min.js?v=${jsVersion}"></script>
<script type="text/javascript" src="${context}/assets/js/bootstrap.min.js?v=${jsVersion}"></script>
<script type="text/javascript" src="${context}/assets/js/browser-edition-validate-1.0.0.min.js?v=${jsVersion}"></script>
<script type="text/javascript" src="${context}/assets/plug/scroll/js/jquery.nanoscroller-1.0.0.min.js?v=${jsVersion}"></script>

<script type="text/javascript">
	$('input, textarea').placeholder();
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

	//语言切换
	$(function(){
	   	$("#selectionLang").on("change",function(){
	   	    var value = $(this).val();
	   	    console.log(value);
			$.get("${context}/common/lang/"+value,function (data) {
				window.location.reload();
               })
		})
		$(".closediv").remove();
	});
</script>
	
	