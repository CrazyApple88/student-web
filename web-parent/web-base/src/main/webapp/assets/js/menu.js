/**
 * Created by xhgx on 2017/12/27.
 * 依赖于public-method.js
 */

function MenuList(options){
    this.config = {
        data:"",              //循环的数据
        nodeList:"",          //数据列表
        styleType:""   //实现切换效果的函数名
    };
    $.extend(this.config,options);
};

MenuList.prototype = {
    //初始化菜单
    initList:function(){
        var that = this.config;
        var map = {};
        $(that.data).each(function(i,item){
            map[item.id]= item;
        });
        that.nodeList =[];
        $(that.data).each(function(i,item){
            var parent = map[item.parentId];
            if(parent){
                if(!parent["children"]){
                    parent["children"]=[];
                }
                parent["children"].push(item);
            }
            if(item.levelnum==1){//如果是一级菜单，
                that.nodeList.push(item);
            }

        });
        console.log(that.nodeList);
        this.eventBind(that.styleType);
    },
    //根据传进来的类名，调用对应的方法
    eventBind:function(styleType){
        var that = this.config;
        if(styleType == "allShowMenu"){
            var str = UDP.Public.template($("#allShowMenu").html(),{variable:"data" })(that.nodeList);
            $(".sidebar-menu").append(str);
        }
        if(styleType == "divideMenu"){
            var str = UDP.Public.template($("#divideMenu").html(),{variable:"data" })(that.nodeList);
            var strs = UDP.Public.template($("#divideMenu1").html(),{variable:"data" })(that.nodeList);
            $(".sidebar-menua").append(str);
            $(".page-sidebar").append(strs);
        }

        if(styleType == "flexMenu"){
            var str = UDP.Public.template($("#flexMenu").html(),{variable:"data" })(that.nodeList);
            $(".sidebar-menu").append(str);
        }
        if(styleType == "showStairMenu"){
            var str = UDP.Public.template($("#showStairMenu").html(),{variable:"data" })(that.nodeList);
            var strs = UDP.Public.template($("#showStairMenu1").html(),{variable:"data" })(that.nodeList);
            $(".navbar_uls").append(str);
            $(".page-sidebar").append(strs);
        }
    },
    //向总的数据里面添加传进来的节点数据
    addNodesData:function(nodes,parentNode){
        var that = this.config;
        if(parentNode){
            var childKey = 'children';
            if(!parentNode[childKey])parentNode[childKey] = [];
            parentNode[childKey].push(nodes);
        }else{
            that.nodeList.push(nodes);
        }
        return that.nodeList;
    },
    //添加菜单
    addMenu:function(newNodes){
        var parentNode,that = this.config;
        $.each(that.nodeList,function(i,item){
            if(newNodes.pId == item.id){
                parentNode = item;
            }else{
                if(item.children){
                    for(var n in item.children) {
                        if (newNodes.pId == item.children[n].id)  parentNode = item.children[n];
                    }
                }
            }
            return parentNode;
        });
        that.nodeList = this.addNodesData(newNodes,parentNode);
        this.eventBind(that.styleType);
        return that.nodeList;
    },
    //删除总数据里面的节点数据
    delNodeData:function(delNodes){
        var that = this.config;
        for(var i in that.nodeList){
            if(delNodes == that.nodeList[i].id){
                that.nodeList.splice(i,1);
            }else{
                var data = that.nodeList[i].children;
                for(var n in data){
                    if(delNodes == data[n].id){
                        data.splice(n,1);
                    }else{
                        var datachild = data[n].children;
                        for(var m in datachild){
                            if(delNodes == datachild[m].id){
                                datachild.splice(m,1);
                            }
                        }
                    }
                }
            }
        }
        this.eventBind(that.styleType);
        console.log(that.nodeList)
        return that.nodeList;
    },
    slideToggle:function(){
    $(".sidebar-menu").on("click",".menu-dropdown",function (){
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
};

// 第一种样式
function AllShowMenu(options) {
    MenuList.call(this, options);
    this.init();
    return this;
}
AllShowMenu.prototype = new MenuList();
AllShowMenu.prototype.init=function(){
    var ulDom= "<ul class='sidebar-menu font14'  id='userMenus'> <li> <a href='javascript:void(0);' data-type='top'> <div class='sidebar-collapse left font14 menu-list'> </div> <span class='menu-text'> 导航列表</span> </a></li> </ul>";
    $(".page-sidebar").empty().append(ulDom).removeClass("menu-compact").parent().removeClass().addClass("page-module");
    this.initList();
    this.event();
};
AllShowMenu.prototype.event=function(obj){
        $.extend(this.config, obj);
        $(document).on("mouseover",".sidebar-menu>li",function(){
            $(this).children(".banner_menu_content").show();
            var height=-($(document).height()-$(window).height());
            if($(document).height()-$(window).height()>0){
                $(this).children(".banner_menu_content").css("top",height);
            }
        });
        $(document).on("mouseout",".sidebar-menu>li",function(){
            $(this).children(".banner_menu_content").hide();
        });
};

// 第二种样式
function DivideMenu(options){
    MenuList.call(this, options);
    this.init();
    return this;
}
DivideMenu.prototype = new MenuList();
DivideMenu.prototype.init=function(){
    var ulDom="<ul class='sidebar-menua font14' id='userMenus'> <li> <a href='javascript:void(0);' data-type='top'> <div class='sidebar-collapse left font14 menu-list'> </div> <span class='menu-text'> 导航列表</span> </a></li></ul>";
    $(".page-sidebar").empty().append(ulDom).parent().removeClass().addClass("page-showContainer");
    this.initList();
    this.event();
};
DivideMenu.prototype.event=function(obj){
    $.extend(this.config, obj);
    this.slideToggle();
    $(".page-sidebar>ul>li").addClass("open");

    $(".page-sidebar>ul>li:eq(0)").removeClass("open");
    $(document).on("click",".page-sidebar>ul>li.open",function(){
        $(this).addClass("listyletype").siblings().removeClass("listyletype");

        if($(".switchover .switchover_div").eq($(this).index()-1).children().is(".sidebar-menu")==true){

            $(".page-sidebar").css({width: '150px',overflow: "visible"}, "slow")
            $(".switchover").eq($(this).index() - 1).addClass("selected").siblings().removeClass("selected");

        }if($(".switchover .switchover_div").eq($(this).index()-1).children().is(".sidebar-menu")==false) {
            $(".page-sidebar").css({width: '36px',overflow: "visible"}, "slow")
        }
        $(".page-sidebar").removeClass("menu-compact");
    });
    $(".page-sidebar>ul>li:eq(0)").on("click",function(){
        $(".page-sidebar").css({width: '36px',overflow: "visible"}, "slow")
    });
    $(document).on("mouseenter",".sidebar-menua>li",function(){
        $(this).addClass("listyletype").siblings().removeClass("listyletype");
        $(".page-showContainer .menu-text").eq($(this).index()).css("display","block")
    });
    $(document).on("mouseleave",".sidebar-menua>li",function(){
        $(this).removeClass("listyletype");
        $(".page-showContainer .menu-text").eq($(this).index()).css("display","none")
    })
};

// 第三种样式的方法
function FlexMenu(options){
    MenuList.call(this, options);
    this.init();
    return this;
}
FlexMenu.prototype = new MenuList();
FlexMenu.prototype.init=function(obj){
    $.extend(this.config, obj);
    var ulDom= "<ul class='sidebar-menu font14'  id='userMenus'> <li> <a href='javascript:void(0);' data-type='top'> <div class='sidebar-collapse left font14 menu-list'> </div> <span class='menu-text'> 导航列表</span> </a></li> </ul>";
     if(localStorage.getItem("src")==null){
         console.log(11)
         $(".page-sidebar").empty().append(ulDom).parent().removeClass().addClass("page-container");
         this.initList();
         this.slideToggle();
         this.event();
       return
     }
    if(JSON.parse(localStorage.getItem("src")).src != "gray"){
        $(".page-sidebar").empty().append(ulDom).parent().removeClass().addClass("page-container");
    }else{
        $(".page-sidebar").empty().append(ulDom).parent().removeClass().addClass("page-newContainer");
    }
    this.initList();
    this.slideToggle();
    this.event();
};
FlexMenu.prototype.event=function(obj){
    $(".page-newContainer .sidebar-menu").css("overflow","visible");
        var num = 0;
        $(document).on("click", ".menu-list", function () {
            $(".submenu").css("display", "none");
            num++;
            if (num % 2 == 0) {
                $(".page-sidebar").addClass("menu-compact");
                $(".page-newContainer .sidebar-menu").css({"overflow": "visible","width":"auto"});
        }
            if (num % 2 == 1) {
                $(".page-newContainer .sidebar-menu").css({"overflow-y": "scroll","width":"166px"});
                $(".page-sidebar").removeClass("menu-compact");
            }

        })

};

// 第四种样式的方法
function ShowStairMenu(options){
    MenuList.call(this, options);
    this.init();
    return this;
}
ShowStairMenu.prototype = new MenuList();
ShowStairMenu.prototype.init=function(obj){
    $.extend(this.config, obj);
    var ulDom='<ul class="sidebar-menu font14"> </ul>';
    var title='<div class="navbar_ul"><ul class="navbar_uls"></ul></div>';
    $(".page-sidebar").removeClass("menu-compact").empty().parent().removeClass().addClass("page-titleContainer");
    $(".navbar").append(title);
    this.initList();
    this.event();
};
ShowStairMenu.prototype.event=function(obj){
    $.extend(this.config, obj);
    $(document).on("click",".navbar_ul ul li",function(){
        if($(".sidebar-menu").eq($(this).index()).text().replace(/(^\s*)|(\s*$)/g, "") !==""){
            $(".page-titleContainer .sidebar-menu").eq($(this).index()).animate({width:'166px'},"slow").addClass("selected").siblings().animate({width:0}).removeClass("selected");
            $(".page-sidebar").animate({width:'148px'},"slow");
        }else{
            $(".page-titleContainer .sidebar-menu").animate({width:0},"slow");
            $(".page-sidebar").animate({width:0},"slow");
        }
        $(this).css("borderBottom","4px solid #bdfed1").siblings().css("borderBottom","none")
    });
    this.slideToggle();
};

function publicFrameStyle(options){
    this.config = {
       data:"",
       frameType:""
    };
    $.extend(this.config,options);
    this.init();
    return this
};
publicFrameStyle.prototype.init=function(){
    switch (this.config.frameType){
        case "allShowMenu" :
             new AllShowMenu({
                 data:this.config.data,
                 styleType:"allShowMenu"
             });
            break;
        case "divideMenu" :
            new DivideMenu({
                data:this.config.data,
                styleType:"divideMenu"
            });
            break;
        case "flexMenu" :
            new FlexMenu({
                data:this.config.data,
                styleType:"flexMenu"
            });
            break;
        case "showStairMenu" :
            new ShowStairMenu({
                data:this.config.data,
                styleType:"showStairMenu"
            });
            break;
    }
};
