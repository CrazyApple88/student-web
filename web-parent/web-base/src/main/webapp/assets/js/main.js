/**
 * Created by xhgx on 2017/4/24.
 */


$(function(){
	
	UDP.Public.transformLanguage(language);

//	var sort = $(".sorting").html();
//	if(sort != undefined && sort.indexOf("sort-icon") == -1){
//		$(".sorting").append(strtab);
//	}
	
	if($(".sort-icon").length <= 0){
		//表格排序
		var strtab = '<span class="sort-icon"></span>';
        $(".sorting").append(strtab);
    }
	
    /*切换网站风格*/
    var flag = false; // 判断面板是否显示
    var isInClick = false;// 判断system_skin是否Click
    var isHasClick= false;//判断是否点击了颜色按钮
    var arr = "";
    var str = "";
    var timer = null;
    $(".system_skin").click(function (event) {
    	//$(".table-bordered tbody td[title=divideMenu || title=allShowMenu || title=showStairMenu]").text("flexMenu");
        isInClick = true;
        if(flag){
            flag = false;
            $(".color-menu").hide();
        }else{
            flag = true;
            $(".color-menu").show();
            $(".color-menu").hover(function(){
                clearInterval(timer);
                if(isHasClick == false){
                    $(".color-menu li").click(function(){
                        flag = false;
                        $(".color-menu").hide();
                        var file = $(this).attr("data-file");
                        $("link[data-bind]").attr({ href:context+"/assets/theme/"+file+"/css/style.css"});
                        var linkSrc = {
                            src:file
                        };
                        linkSrc = JSON.stringify(linkSrc);
                        localStorage.setItem("src",linkSrc);
                        // 加载cs样式颜色文件
                        loadStyles(context+"/assets/theme/"+file+"/css/style.css");
                    });
                    isHasClick = true;
                }
            },function(){
                flag = false;
                $(this).hide();
            })
        }
        $(this).mouseleave(function(){
            if(isInClick){
                timer = setTimeout(function(){
                    flag = false;
                    $(".color-menu").hide();
                },500);
            }
            isInClick = false;
        });
    });
    $(".system_skin").bind("selectstart", function () { return false; });

    /*    if(localStorage.getItem("src")){
     var link = JSON.parse(localStorage.getItem("src"));
     $("link[data-bind]").attr({rel: "stylesheet",  href:context+"/assets/theme/"+link.src+"/css/style.css"});
     /!* $(".sidebar-collapse img").each(function(i,item){
     arr = $(item).attr("src").split("/");
     arr[3] = link.src;
     str = arr.join("/");
     $(item).attr("src",str);
     });*!/
     }*/
    /*左侧导航*/
    //var num=0;
    //var rotateFlag = false;
    //$(".menu-list").click(function () {
    //    num++;
    //    if(num%2 == 0){
    //        $(".page-sidebar").addClass("menu-compact");
    //        $(".menu-three .submenu").addClass("thrsubmenu");
    //        $(".submenu").hide();
    //        $(".sidebar-menu li").removeClass("open");
    //        $(".menu-dropdown").find("i").removeClass("rotateDown").addClass("rotateUp");
    //    }else{
    //        $(".page-sidebar").removeClass("menu-compact");
    //        $(".menu-three .submenu").removeClass("thrsubmenu");
    //    }
    //});
    /*function slidetoggle(){
    $(".sidebar-menu").on("click",".menu-dropdown",function () {
        var that = this;
        if($(".page-sidebar").hasClass("menu-compact")){
            return false;
        }else{
            $(that).siblings(".submenu").slideToggle();
            iconanimate(that);
            $(that).parent("li").hasClass("open") ?  $(that).parent("li").removeClass("open"):$(that).parent("li").addClass("open");
        }
    });
	}
	function slidetoggle(){
	    $(".sidebar-menu").on("click",".menu-dropdown",function () {
	        if($(".page-sidebar").hasClass("menu-compact")){
	            return false;
	        }
	        var that = this;
	        $(this).siblings(".submenu").slideDown().parents("li").siblings().children(".submenu").slideUp(function(){
	            $(that).siblings(".submenu").children(".menu-three").removeClass("open")
	        });
	        if($(this).parent("li").hasClass("open")){
	            $(this).parent("li").removeClass("open");
	            $(this).siblings(".submenu").slideUp();
	            $(this).siblings(".submenu").children(".menu-three").removeClass("open").find("i").addClass("rotateUp").removeClass("rotateDown");
	            $(this).find("i").addClass("rotateUp").removeClass("rotateDown");
	        }else{
	            $(this).parent("li").addClass("open").siblings().removeClass("open");
	            $(this).siblings(".submenu").slideDown();
	            $(this).siblings(".submenu").children(".menu-three").find(".submenu").slideUp();
	            $(this).find("i").addClass("rotateDown").removeClass("rotateUp").parents("li").siblings().find("i").removeClass("rotateDown").addClass("rotateUp");
	        }
	    });
	}

    $(".sidebar-menu").on("mouseenter",".menu-three",function(){
        if($(".page-sidebar").hasClass("menu-compact")) {
            $(this).find(".thrsubmenu").show();
            $(this).find("i").addClass("rotateDown").removeClass("rotateUp");
        }else{
            return false;
        }
    });
    $(".sidebar-menu").on("mouseleave",".submenu",function(){
        if($(".page-sidebar").hasClass("menu-compact")) {
            $(this).find(".thrsubmenu").hide();
            $(this).find("i").addClass("rotateUp").removeClass("rotateDown");
        }else{
            return false;
        }
    });

    function iconanimate(ele){
        if($(ele).find("i").hasClass("rotateDown")){
            $(ele).find("i").addClass("rotateUp").removeClass("rotateDown");
        }else{
            $(ele).find("i").addClass("rotateDown").removeClass("rotateUp");
        }
    }

    //执行
    slidetoggle();*/

    $(".sex_radio").click(function(){
        $(this).addClass("radio_active").parent().siblings().find(".sex_radio").removeClass("radio_active");
    });
    /*placeholder*/
    $('input, textarea').placeholder();
    /*scroll*/
    if( $(".nano").nanoScroller){
        $(".nano-content").mouseover(function(){
            $(".nano-pane").show();
        });
        $(".nano").nanoScroller();
    }
    
    $(".drag_slide").mousedown(function(e){
        e.stopPropagation();
        e.preventDefault();
        var moveCont = $(this).parents(".nano");
        var x = e.clientX - moveCont.width();
        $(document).mousemove(function(e){
            var that = this;
            var width = e.clientX-x;
            if(width>400){
                width = 400;
            }
            width=width<260 ? 260:width;
            moveCont.css({width:width+"px"});
            var iframe_width = $(window).width();
            moveCont.siblings("#tabledata").css({width:parseInt(iframe_width) - width - 10+"px"});
           // console.log("iframe==="+parseInt(iframe_width))
        });
        $(document).mouseup(function(){
            $(document).unbind("mousemove");
        });
    });
    
    
});


function Hint(options) {
    this.options = $.extend({
        class: ".ensure_btn",
    }, options || {});
}
Hint.prototype = {
        before: function() {
            $(this.options.class).attr("disabled", "true").val("提交中...");
        },
        beforeSend: function() {
            $(this.options.class).addClass("ensure_btns");
        },
        success: function() {
//            $(this.options.class).removeAttr("disabled").val("保存").removeClass("ensure_btns");
            $(this.options.class).val("提交成功");
        },
        error: function() {
            $(this.options.class).val("请求出错");
            setTimeout(function() {
                $(this.options.class).removeAttr("disabled").val("保存").removeClass("ensure_btns");
            }, 1000);

        }
    }

function Emptyinput(options) {
    this.config = $.extend({
        circulation: "",
        callback: function() {}
    }, options);
    this.init();
    return this
}

Emptyinput.prototype = {
    init: function() {
        var empty = this.config.circulation,
            arrempty = $(empty).find("input").filter(function(index, element) {
            	//console.log(element)
                if (element.type.toLowerCase() == "text" && ($(element).hasClass("sea_inp") || $(element).hasClass("clean_inp"))) {
                	return element;
                }
            });
        for (var i = 0; i < arrempty.length; i++) {
 
    	    if($(arrempty[i]).next().hasClass("closedivs")){
               return;
            }
            $(arrempty[i]).after($('<div class="closedivs closediv' + i + '"></div>'));
            $(".closedivs").hide();
            $(arrempty[i]).on("mouseenter", function() {
                $(this).parent().find("div").css("display", "block");
            });
            $(".closediv" + i + "").on("mouseenter", function() {
                $(this).css("display", "block");
            });
            $(arrempty[i]).on("mouseleave", function() {
                $(this).parent().find("div").css("display", "none");
            });
            $(document).on("click", ".closedivs", function() {
                $(this).parent().find("input").val("");
            });

        }
        if (this.config.callback) {
            this.config.callback.apply(this, []);
        }
    }
};
(function(win) {
    if (win["UDP"]) {
        win["UDP"].Emptyinput = Emptyinput
    } else {
        win.UDP = { Emptyinput: Emptyinput }
    } })(window);

$(document).ready(function(){
	var  mptyinput=new Emptyinput({
		circulation:"body",
		callback: function() {
		$(".Wdate").next().css("right","22px");
		
		}
	})
})
$(document).on("click" ,".prevents",function(){
        var str='<div class="prevents_style">'
            +'<div class="loading">'
            +'<span></span>'
            +'<span></span>'
            +'<span></span>'
            +'<span></span>'
            +'<span></span>'
            +'<div class="rect">加载中 . . .</div></div>'
            +'</div></div>';
        if($("body").find("div").hasClass("prevents_style").toString()=="false"){
            $("body").append(str);
            $(this).attr("disabled", "true");
        }
        $(this).removeAttr("disabled");
    });

$(document).on("click" ,".compile",function(){
    var str='<div class="prevents_styles">'
            +'<div class="loadingcompile">'
            +'<span></span>'
            +'<div class="rect">加载中 . . .</div></div>'
            +'</div></div>';
    if($("body").find("div").hasClass("prevents_style").toString()=="false"){
        $(".layerContianer").append(str);
        $(this).attr("disabled", "true");
    }
    $(this).removeAttr("disabled");
});

