<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
	<title>
		<@sysConfig key="entry.name" ; value,map> 
			<#if value != 'null'> ${value} </#if> 
		</@sysConfig>
	</title>

    <#include "/system/common_login_js.ftl">
	<script type="text/javascript">

		if(self!=top){//如果登录页面不是顶部页面
			top.getLogout();
		}
		$(document).ready(
			function() {
				$("#verifyCode").on(
						"click",
						function() {
							$(this).attr("src","${context}/verify/code?v="+ new Date().getTime());
						});
			});
		function ajaxSubmit(frm, fn) {
			var dataPara = '';
			$.ajax({
				url : frm.action,
				type : frm.method,
				data : dataPara,
				dataType : "json",
				async : false,//异步
				success : fn
			});
		}
	</script>

</head>
<body>
	<div class="lg_cont">
		<select id="selectionLang" class="changelanguage" >
			<option value="zh_CN"><@i18n.message code="locale.selection"/></option>
			<option value="en_US"  <#if language == 'en_US' >selected</#if>>English</option>
			<option value="zh_CN" <#if language == 'zh_CN' >selected</#if>>中文简体</option>
			<option value="han_ko" <#if language == 'han_ko' >selected</#if>>한국어</option>
		</select>
		<form id="login_form" name="login_form"
			action="${context}/security/check" method="post" class="login-form"
			style="background: none;">
			<div class="lg_box">
			<div class="lg_titBg"></div>
				<div class="lg_tit"><@sysConfig key="entry.name" ; value,map>
					<#if value != 'null'> ${value} </#if> </@sysConfig></div>
				<div class="inpCont">
					<#if tip!=null>
					<div class="alert alert-error">
						<button class="close" data-dismiss="alert"></button>
						<strong> <@i18n.messageText code='common.text.error'  text='错误!' /> </strong> ${tip}
					</div>
					</#if>
					<div class="bg_span"><span></span><span></span></div>
					<p class="p1">
						 <span class="lgmesscont"> <@i18n.messageText code='common.text.username'  text='用户名' /> </span> 
						 <label for=""></label> 
						 <input type="text" name="userName"
							placeholder="<@i18n.messageText code='common.text.username'  text='用户名' />" />
					</p>
					<p class="p2">
						<span class="lgmesscont"> <@i18n.messageText code='common.text.password'  text='密码' /> </span> 
						<label for=""></label> 
						<input type="password" name="password"
							placeholder="<@i18n.messageText code='common.text.password' text='密码'/>" />
					</p>
					<@sysConfig key="verify.enable" ; value,map> <#if value =="true">
					<p class="p3">
						<span class="lgmesscont"> <@i18n.messageText code='common.text.captcha'  text='验证码' /> </span> 
						<input type="text" name="code"
							placeholder="<@i18n.messageText code='common.text.captcha' text='验证码'/>" />
						<img id="verifyCode" src="${context}/verify/code" alt="" width="85" />
					</p>
					</#if> </@sysConfig>
				
					<p class="p4">
						<input type="submit"
							value="<@i18n.messageText code='common.button.sign.in' text='登录'/>" />
					</p>
				</div>
			</div>
			<div class="lg_bottom">
				<span> <@i18n.messageText code='common.company.copyright' text='北京轩慧国信科技有限公司 提供技术支持 @copy; 2006-2014 备案许可：京ICP备09031955号'/></span>
			</div>
		</form>
	</div>
</body>
</html>
<script>
window.onload=function(){
resead ()
   function resead (){
                if(localStorage.getItem("src")==null){
                       return;
                 }
                if(JSON.parse(localStorage.getItem("src")).src === "gray"){
                     
                       $(".p1 span").css("display","none")
                }
                 if(JSON.parse(localStorage.getItem("src")).src !== "gray"){
                       $(".p5 span,.bg_span").css("display","none")

                  }
   }

}

</script>