<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="${context}/assets/menu-library/css/menu-library.css?v=${cssVersion}"/>
    <#import "spring.ftl" as i18n/>
  	<script>
        $(function(){
            $('input, textarea').placeholder();
            if(localStorage.getItem("src")){
                var link = JSON.parse(localStorage.getItem("src"));
                $.revamptag("link","stylesheet","text/css",context+"/assets/theme/"+link.src+"/css/style.css");
            }else{
                $.createtag("link","stylesheet","text/css",context+"/assets/theme/blue/css/style.css");
            }
        });
    </script>
    <script>
        $(function(){
            $('input, textarea').placeholder();
            if(localStorage.getItem("src")){
                var link = JSON.parse(localStorage.getItem("src"));
                $.revamptag("link","stylesheet","text/css",context+"/assets/theme/"+link.src+"/css/style.css");
            }else{
                $.createtag("link","stylesheet","text/css",context+"/assets/theme/blue/css/style.css");
            }
        });
    </script> 
</head>
<body>
    <div class="menu_container">
    <#list iconTypeList as iconType>
        <div class="menu_title">
            ${iconType.dictName}
        </div>
        <div class="menu_list">
            <ul>
            <#list menuIconList as menuIcon>
            	<#if menuIcon.typeId == iconType.dictCode>
                <li>
                    <div class="menu_img">
                        <div class="sidebar-collapse font14 ${menuIcon.className}"></div>
                    </div>
                    <div class="menu_mess" data="${menuIcon.className}">${menuIcon.className}</div>
                    <div class="menu_mess">${menuIcon.iconName}</div>
                </li>
                </#if>
    		</#list>
            </ul>
        </div>
    </#list>
    </div>
    <div class="menu_btnGruop">
        <input type="button" id="save" btntype="save" value="确定" class="fbtn btn_sm ensure_btn">
        <input type="button" id="cancel" value="关闭" btntype="cancel" class="cancel fbtn btn_sm cancle_btn">
    </div>
    <script>
        var menuclass = "";
        $(".menu_list li").on("click",function(){
            $(this).addClass("active").siblings().removeClass("active").parents(".menu_list").siblings(".menu_list").find("li").removeClass("active");
        });
        $(document).on("click","[btnType=cancel]",function(){
        	$(".overlay").remove();
        });
        
        $('#save').unbind('click');
        $('#save').bind('click', function(){
        	var $selected = $(".menu_list").find(".active");
        	var className = $selected.find(".menu_mess").eq(0).attr("data");
        	$("#iconDiv").removeClass();
        	$("#iconDiv").addClass("sidebar-collapse font14 sidebar-border "+className);
        	$("#iconDiv").attr("data",className);
     		$(".class_btn_li").css("margin-top","20px");
        	$(".overlay").remove();
        });
    </script>
</body>
</html>