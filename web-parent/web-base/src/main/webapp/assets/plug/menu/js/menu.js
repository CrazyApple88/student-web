/**
 * Created by xhgx on 2017/12/27.
 * 依赖于public-method.js
 */
function MenuList(options){
    this.config = {
        templateId:"",       //模板的ID
        data:"",              //循环的数据
        nodeList:"",          //数据列表
        eventStyle:"clickToggle"   //实现切换效果的函数名
    }
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
        this.eventBind(that.eventStyle);
    },
    //根据传进来的函数名，调用对应的方法
    eventBind:function(eventStyle){
        var that = this.config;
        if(eventStyle == "clickToggle"){
            var str = UDP.Public.template($("#listTemplate").html(),{variable:"data" })(that.nodeList);
            $(".sidebar-menu").append(str);
            toggleClick();
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
        this.eventBind(that.eventStyle);
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
        this.eventBind(that.eventStyle);
        return that.nodeList;
    }
};

function toggleClick(){
    var flag = false;
    $(".sidebar-menu a[data-type='top']").on("click",function(){
        $("#sidebar").toggleClass("menu-compact");
        $("[data-type='menu']").next().slideUp().parents().removeClass("open");
    });
    //type = menu  类型触发的事件
    $(".menu-dropdown[data-type='menu']").on("click",function(e){
        if(!$(".sidebar-menu").hasClass("menu-compact")){
            e.stopPropagation();
            e.preventDefault();
            var $next = $(this).next();
            $next.slideToggle();
            $(this).parent().toggleClass("open");
            if(!flag){
                $(".sidebar-menu").find(".submenu").not($next).slideUp().parent().removeClass("open");
                $(".sidebar-menu").find(".menu-three").next().slideUp().parent().removeClass("open");
            }
        }
    });

    var $this = $(".submenu").find(".menu-three[data-type='menu']");
    $this.on("click",function(e){
        if(!$(".sidebar-menu").hasClass("menu-compact")){
            e.stopPropagation();
            e.preventDefault();
            var $next = $(this).next();
            $next.slideToggle();
            $(this).parent().toggleClass("open");
            if(!flag){
                $(this).parents(".submenu").find(".thrsubmenu").not($next).slideUp().parent().removeClass("open");
            }
        }
    });

    //鼠标滑过收缩状态下li的效果
    $(".sidebar-menu li").on("mouseenter",function(){
        if($(".sidebar-menu").hasClass("menu-compact")){
            $(this).find("menu-text").show();
            $(this).children(".submenu").show();
        }
    });
    $(".sidebar-menu li").on("mouseleave",function(){
        if($(".sidebar-menu").hasClass("menu-compact")){
            $(this).children(".submenu").hide();
        }
    });
    //鼠标滑过三级菜单的效果
    $(".menu-three").on("mouseenter",function(){
        if($(".sidebar-menu").hasClass("menu-compact")){
            $(this).next().slideDown().parent().addClass("open");
        }
    });
    //鼠标离开二级菜单的效果
    $(".submenu").on("mouseleave",function(){
        if($(".sidebar-menu").hasClass("menu-compact")){
            $(".menu-three").next().slideUp().parent().removeClass("open");
        }
    });
}
























































