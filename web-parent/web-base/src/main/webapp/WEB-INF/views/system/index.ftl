<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>
    	<@sysConfig key="entry.name" ; value,map>
			<#if value != 'null'>
		  		${value}
		  	</#if>
		</@sysConfig>
	</title>
	    
    <#include "/system/common_index_js.ftl">
    
    
	
	<style>
		.menu-compact .sidebar-menu .submenu{
			width:300px;
		}
		.menu-compact .sidebar-menu > li > a .menu-text{
			width:280px;
		}
		.xhn_topNavUl li:first-child{
			width:90px;
		}
		 body{

           box-sizing: border-box;

          }
	</style>
</head>
<body>
<!--navbar-->
    <div class="navbar relative navbg-color">
        <div class="nav-logoBox left">
            <img src="/assets/images/logo.png" alt="" class="marginlf15 loadLogo"/>
        </div>
        <div class="nav-menu right" style="margin-left:-100px">
		
        </div> 
            <ul class="nav_module">
                <li><span></span></li>
                <li id="reminder"> <span >${count}</span></span></li>
                <li><a onclick="updateNowUserInfo()" href="javascript:;"><img src="/assets/images/headportrait.png" alt=""/></a></li>
            	<li class="system_skin"></li>
            </ul>
            <span class="menuTemplateClass"></span>
        </div>
    </div>
     <div class="color-menu abright">
           <div class="theme_skin relative"><div class="arrow"></div> <@i18n.messageText code='common.text.change.background'  text='更换背景' /> </div>
        <ul>
<!--             <li data-file="green"><span></span></li> -->
<!--             <li data-file="blue"><span></span></li> -->
<!--             <li data-file="science"><span></span></li> -->
           
        </ul>
        <div class="theme_skin exit_system">
			<a class="logOutFun" onclick="logOutFunction()" href="javascript:;"> <@i18n.messageText code='common.button.quit'  text='退出' /> </a>
        </div>
       </div>
    <!--page-container-->
    <div class="page-container">
        <div class="page-sidebar left  menu-compact xhn_TRlistBM" id="sidebar">
     <ul class='sidebar-menu font14'  id='userMenus'>
         <li>
            <a href='javascript:void(0);' data-type='top'>
            <div class='sidebar-collapse left font14 menu-list'> </div>
            <span class='menu-text'>导航列表</span>
         </a></li>
        </ul>
        </div>
        <div class="page-body">
            <div id="tabs" class="xhn_topNav">
                <div class="page-menu">
                    <ul class="ui-tabs-nav xhn_topNavUl ableft ui_tabs_shadow">

                    </ul>
                    <div id="box" style="display:none">
                      <ul id="cleverTabsContextMenu">
                        <li id="mnuRefresh" class="tab-items"><a href="#refresh"><span class="ui-icon ui-icon-refresh" style="float: left; margin-right: 5px;"></span> <@i18n.messageText code='common.text.close.other.labels'  text='关闭其他标签' /> </a></li>
                        <li id="mnuDisalb" class="tab-items"><a href="#disable"><span class="ui-icon ui-icon-cancel" style="float: left; margin-right: 5px;"></span> <@i18n.messageText code='common.text.refresh'  text='刷新' /> </a></li>
                        <li id="mnuCloseAll" class="tab-items"><a href="#clear"><span class="ui-icon ui-icon-closethick" style="float: left; margin-right: 5px;"></span> <@i18n.messageText code='common.text.close.all'  text='全部关闭' /> </a></li>
                        <li id="mnuEnabl" class="tab-items"><a href="#enabled"><span class="ui-icon ui-icon-pencil" style="float: left; margin-right: 5px;"></span> <@i18n.messageText code='common.text.close.current'  text='关闭当前' /> </a></li>
                        <li id="mnuLock" class="tab-items"><a href="#lock"><span class="ui-icon ui-icon-locked" style="float: left; margin-right: 5px;"></span> <@i18n.messageText code='common.text.close.left.label'  text='关闭左侧标签' /> </a></li>
                        <li id="mnuUnlock" class="tab-items"><a href="#unlock"><span class="ui-icon ui-icon-unlocked" style="float: left; margin-right: 5px;"></span> <@i18n.messageText code='common.text.close.right.label'  text='关闭右侧标签' /> </a></li>
                     </ul>
                   </div>
                </div>
            </div>
        </div>
    </div>
<script type="text/javascript">
	var tabs;
    var win_height = 400;
    var win_width = 1000;
    var tmpCount = 0;
    var minus_h = 82;
    var minus_w = 48; //
    var min_height = 450; //屏幕下方的最低高度
    var iframe_width;
    var initLng;
    var initLat;
    var checkCarId = '';
    
    // 设置默认样式，根据传的key值判断key值是否为空，如果不为空加载到相应样式
    var menuTemplate = "flexMenu";
	<@sysConfig key='menu.template' ; value,map>
		<#if value != 'null'>
			menuTemplate = "${value}";
	  	</#if>
	</@sysConfig>
    
    function logOutFunction(){
    	var toolTip = new diaPrompt();
    	toolTip.warn({
            message:"<@i18n.messageText code='common.text.quit.system'  text='确认要退出系统么' />",
            callback:function(){
            	location.href='/security/logout';
            }
    	})
	}
    
    function updateNowUserInfo(){
    	var toolTip = new Layer({
			layerclass:"layerContianer",//存放页面的容器类名
	        width:400,
	        height:280,
	        alerttit:"<@i18n.messageText code='common.button.change.password'  text='修改密码' />",
	        callback:function(){		        	
	        	$(".layerContianer").load('/admin/user/getPasswordUpdatePage',{},function(){});
	        }
	    });
	    toolTip.show();
    }
    function getLocation(){
        var options={
            enableHighAccuracy:true,
            maximumAge:1000
        }
        if(navigator.geolocation){
            //浏览器支持geolocation
            navigator.geolocation.getCurrentPosition(onSuccess,onError,options);
        }else{
            //浏览器不支持geolocation
            
        }
    }
    //成功时
    function onSuccess(position){
        //返回用户位置
        //经度
        initLng =position.coords.longitude;
        //纬度
        initLat = position.coords.latitude;
    }
    //失败时
    function onError(error){
    	var defaultPosition="";
		<@sysConfig key='default.position' ; value,map>
			<#if value != 'null'>
				defaultPosition = "${value}";
		  	</#if>
		</@sysConfig>
		//经度
        initLng =(defaultPosition.split(","))[0];
        //纬度
        initLat = (defaultPosition.split(","))[1];
    }
    window.onload=getLocation;
    
    function getLogout(){
    	location.href='/security/logout';
    }
    /*
    ****触发方法，更新我的消息数量
    */
	function refreshMsgNum(){
		$.ajax({  
            type: "get",  
            dataType: "json",  
            url: "/admin/message/countUnMsgByUserId",  
            success: function (json) { 
            	if(json.code == 1){
            		$("#reminder").text(json.data);
            	}
            }
		}); 
	}
    
    /*打开新窗口可通过调用此方法*/
    function open_tab(labUrl, labTit){
        tabs.add({
            url: labUrl,
            label: labTit,
            user_click:true,
            save:false,
            locked: false,
            add_callback:function(){

            }
        });
    }

    var refreshWindowHeight = function() {
        var clientHeigth = win_height; // $(window).height();
        var iframeHeight = clientHeigth - minus_h;
        iframeHeight = Math.max(iframeHeight, min_height);
        iframe_width = 1000;
        var w_w = win_width; //$(window).width();
        iframe_width = w_w;
        if(w_w > 1000) {
            iframe_width = w_w - minus_w;
        }
        $(".ui-tabs-panel").css({
            "height": iframeHeight + "px",
            "width": iframe_width + "px"
        });
        $(".xhn_topNav .xhn_topNavUl").css({
            "width": (iframe_width) + "px"
        });
    }
    function result(){
      	 $(document).on("click","li[data-file=gray]",function(){
      	 $("body").css({"padding":"0","background":"url(/assets/images/BG.png) no-repeat"});

               $(".nav-logoBox").children("img").attr("src","/assets/theme/gray/images/logo.png")
               if($(".page-sidebar").parent().hasClass("page-container").toString()=="true"){

                  $(".page-sidebar").parent().removeClass("page-container").addClass("page-newContainer");
               }
          });
          $(document).on("click","li[data-file=blue], li[data-file=green] ,li[data-file=science],li[data-file=cambridgeblue]",function(){
             $(".nav-logoBox").children("img").attr("src","/assets/images/logo.png")
           $("body").css({"padding":"0","background":"none"});
                   var ulDom= "<ul class='sidebar-menu font14'  id='userMenus'> <li> <a href='javascript:void(0);' data-type='top'> <div class='sidebar-collapse left font14 menu-list'> </div> <span class='menu-text'> 导航列表</span> </a></li> </ul>";
                   $(".page-newContainer .sidebar-menu").css({"overflow": "visible","width":"auto"});
            if($(".page-sidebar").parent().hasClass("page-newContainer").toString()=="true"){
                    $(".page-sidebar").parent().removeClass("page-newContainer").addClass("page-container");
                }
          });
           $(document).on("click","li[data-file=cambridgeblue]",function(){
                   $("body").css({"padding":"4px 8px","background":"url(/assets/images/BG.png) no-repeat"});
           });
           if(localStorage.getItem("src")==null){
                return;
            }
           if(JSON.parse(localStorage.getItem("src")).src === "gray"){
                   $(".nav-logoBox").children("img").attr("src","/assets/theme/gray/images/logo.png");
           }
           if(JSON.parse(localStorage.getItem("src")).src === "cambridgeblue"){
                            $("body").css({"padding":"4px 8px","background":"url(/assets/images/BG.png) no-repeat"});
            }

      }
    
    function loadLogo(){
    	$.ajax({  
            type: "get",  
            dataType: "json",  
            url: "/admin/manager/getLogoByCompId",  
            success: function (json) {  
            	if(json.code == 1){
            		var data = json.data;
					$(".loadLogo").attr("src",data.logo);
            	}else{
					alert("查询异常！");
				}
            }, error: function (result) {  
                alert("查询LOGO信息失败！");  
            }
        });
    }

    $(function() {
    	loadLogo();
		result();
		// 加载对应的菜单样式模板
    	$(".menuTemplateClass").load('/security/menuTemplate?type='+menuTemplate,{},function(){});
        win_width = $(window).width();
        win_height = $(window).height();

        tabs = $('#tabs').cleverTabs({
            /*"navigation":false,*/
            "dir": "WUZI"
        });
        tabs.add({
            url: "/security/getDefaultpage",
            label: "<@i18n.messageText code='common.button.working.bench'  text='工作台' />",
            user_click:true,
            locked: true,
            add_callback:function(){

            }
        });
        
        $(document).on("click","#reminder",function(){
        	open_tab("${context}/admin/message/getMsgByUserIdPage","<@i18n.messageText code='system.management.text.my.message'  text='我的消息' />");
        });
        
        $(window).bind('resize', function() {
            tabs.resizePanelContainer();
        });

        ////////////////////////
        tabs.load_from_storage();
        window.top["topTabs"]=tabs;
        tabs.add({
            url: "/security/getDefaultpage",
            label: "<@i18n.messageText code='common.button.working.bench'  text='工作台' />",
            user_click:true,
            locked: true,
            add_callback:function(){

            }
        });

        var defaultT = ''; //'';
        var defaultN = ''; //
        if(defaultT.length > 0) {
            tabs.add({
                url: defaultT,
                label: defaultN,
                locked: false,
                add_callback:function(){

                }

            });
            //location.href = '/user/index.htm';
        }

        refreshWindowHeight();
        ////////////左侧导航的点击事件结束///////////////
        $(document).on('click', ".xhn_TRlistBM ul li", function() {
            //$('.xhn_TRlistBM ul li').click(function () {
            $(this).closest(".xhn_TRlistB").hide();
            var labTit = $(this).attr("data-title");

            var labUrl = $(this).attr("data-src");
            if(labUrl=='undefined' || labUrl==null || labUrl == "") {
                return true;
            }
            tabs.add({
                url: labUrl,
                label: labTit,
                user_click:true,
                locked: false,
                add_callback:function() {

                }
            });
            var id = tabs.getCurrentUniqueId();
            var tab = new CleverTab(tabs,id);
            tab.refreshs(labUrl,labTit)
        });

        $.ajax({  
            type: "get",  
            dataType: "json",  
            url: "/security/getMenusByUserId",  
            success: function (json) {  
//             	console.log("--"+JSON.stringify(json) );
            	if(json.code == 1){
            		var data = json.data;
            	    /* var map = {};
                    $(data).each(function(i,item){
                    	map[item.id]= item;        
                    });
                    var list =[];
                    $(data).each(function(i,item){
                    	var parent = map[item.parentId];
                    	if(parent){
                    		if(!parent["children"]){       		
                    			parent["children"]=[];
                    		}
                    		parent["children"].push(item);
                    	}  
                    	if(item.levelnum==1){//如果是一级菜单，
                    		list.push(item);
                    	}        	
                    
                    }); */
                    
                    var createmenu = new publicFrameStyle({
                        data:data,                          //循环的数据
                        frameType:menuTemplate
                    });
                    //采用模板进行菜单加载
//                     $("#userMenus").empty();
//                    console.log("--"+JSON.stringify(list) );
                   /*  var str=UDP.Public.template($("#listTemplate").html(),{variable: "list"})(list); */
                   var str = ""; 
                   if(data == null || data == ''){
                    	 str +="<font color='red'>您没有访问权限，请找管理员配置访问权限！</font>";
                    }
                    $("#userMenus").append(str);
                    
            	}else{
					alert("查询异常！");
				}
            }, error: function (result) {  
                alert("查询菜单权限信息失败！");  
            }
        });

         /* var websocket = new Message({
            socketSite: "127.0.0.1:28080/websocket",     // 配置地址
          });
          window.top["websocket"]=websocket;
        var websocket=window.top["websocket"];
          websocket.addEvent("E01_002",function(data){
            console.log("--"+data);
        }); */
    });
</script>

</body>
</html>