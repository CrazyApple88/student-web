/**
 * Created by xhgx on 2017/5/10.
 */

function Public() {


    var templateSettings = {
            evaluate: /<%([\s\S]+?)%>/g,
            interpolate: /<%=([\s\S]+?)%>/g,
            escape: /<%-([\s\S]+?)%>/g
        },
        noMatch = /(.)^/;
    // Certain characters need to be escaped so that they can be put into a
    // string literal.
    var escapes = {
        "'": "'",
        '\\': '\\',
        '\r': 'r',
        '\n': 'n',
        '\u2028': 'u2028',
        '\u2029': 'u2029'
    };
    var escapeChar = function (match) {
        return '\\' + escapes[match];
    };
    var escaper = /\\|'|\r|\n|\u2028|\u2029/g;

    /**
     * 模版功能：
     * @param text
     * @param settings
     * @param oldSettings
     * @returns {Function}
     */
    function templates(text, settings, oldSettings) {
        if (!settings && oldSettings) settings = oldSettings;
        settings = $.extend(settings, templateSettings);

        // Combine delimiters into one regular expression via alternation.
        var matcher = RegExp([
                (settings.escape || noMatch).source,
                (settings.interpolate || noMatch).source,
                (settings.evaluate || noMatch).source
            ].join('|') + '|$', 'g');
        // Compile the template source, escaping string literals appropriately.
        var index = 0;
        var source = "__p+='";
        text.replace(matcher, function (match, escape, interpolate, evaluate, offset) {
            source += text.slice(index, offset).replace(escaper, escapeChar);
            index = offset + match.length;

            if (escape) {
                source += "'+\n((__t=(" + escape + "))==null?'':_.escape(__t))+\n'";
            } else if (interpolate) {
                source += "'+\n((__t=(" + interpolate + "))==null?'':__t)+\n'";
            } else if (evaluate) {
                source += "';\n" + evaluate + "\n__p+='";
            }

            // Adobe VMs need the match returned to produce the correct offest.
            return match;
        });
        source += "';\n";

        // If a variable is not specified, place data values in local scope.
        if (!settings.variable) source = 'with(data||{}){\n' + source + '}\n';

        source = "var __t,__p='',__j=Array.prototype.join," +
            "print=function(){__p+=__j.call(arguments,'');};\n" +
            source + 'return __p;\n';
        try {
            var render = new Function(settings.variable || 'data', '_', source);
        } catch (e) {
            e.source = source;
            throw e;
        }

        var template = function (data) {
            return render.call(this, data);
        };

        // Provide the compiled source as a convenience for precompilation.
        var argument = settings.variable || 'data';
        template.source = 'function(' + argument + '){\n' + source + '}';

        return template;
      

    };
    var arr=[],I18N = {};
    return {
    	
        view: function () {
            return {
                //w:可视区域的宽度    h:可视区域的高度
                w: document.documentElement.clientWidth,
                h: document.documentElement.clientHeight
            }
        },
        template: templates,
        /**
         * 初始化全选按钮 条件:checkbox  的name = checkAll；checkbox  子节点的 name = checkItem
         *  预绑定事件
         */
        checkAllInit:function(){

            //全选按钮
            $(document).on("click","input[name=checkAll]",function(){
                $("input[name=checkItem]").attr("checked",this.checked);
            });

            //字按钮事件
            $(document).on("click","input[name=checkItem]",function(){
                var checkItem = $("input[name=checkItem]");
                $("input[name=checkAll]").attr("checked",checkItem.length == $("input[name=checkItem]:checked").length ? true : false);

            });

        },
        /**
         * 获取选中的checkbox的子项，的值
         * @param doc  范围，页面上有多个多选，需要指定范围
         */
        selectedCheckItems:function(doc){
            if(doc ==undefined) doc = $('body');
            var arr=[];
            $("input[name=checkItem]:checked",doc).each(function(i,item){
                arr.push($(item).val());
            });
            return arr;
        },
        /**
         * Limitcharacter：限制字符长短，超出显示省略号
         * 需要指定限制的字符长度,自定义属性：data-limit：n
         */
        Limitcharacter:function(){
            var ele = $(".table td[data-limit]").attr("data-limit");
            $(".table td[data-limit]").each(function(i){
                var limitnum = $(this).attr("data-limit");//获取限制字符个数
                var cutcont = $(this).text();//获取将要截取的文本
                //替换换行	tab退格 去前后空格等
                cutcont = cutcont.replace(/\r\n/g,"").replace(/\n/g,"").replace(/\b/g,"").replace(/\t/g,"").trim();
                var cont = cutcont.substr(0,limitnum);
                if(limitnum>0 && cutcont.length>limitnum && limitnum != cutcont.length){
                    if(cutcont){
                        $(this).text(cont+"...");
                    }
                }
            });
        },
        /**
         * 字节转换
         * 文件大小转换，将b转换成成以kb...等单位换算的大小
         * */
        Bytetransform:function(size){
            if (size >= 1024 * 1024 * 1024 * 1024) {
                size = size / (1024 * 1024 * 1024 * 1024);
                size = size.toFixed(2) + "Tb";
            } else if (size >= 1024 * 1024 * 1024) {
                size = size / (1024 * 1024 * 1024);
                size = size.toFixed(2) + "Gb";
            } else if (size >= 1024 * 1024) {
                size = size / (1024 * 1024 );
                size = size.toFixed(2) + "Mb";
            } else if (size >= 1024) {
                size = size / (1024);
                size = size.toFixed(2) + "Kb";
            } else {
                size = size.toFixed(2) + "b";
            }
            return size;
        },
        /**
         * 创建标签link或者script*/
        createTag:function(ele,path){
        	if(ele == 'script'){
        		var script = '<script src="'+path+'" type="text/javascript"></script>';
        		$("body").append($(script));      		
        	}else{
        		var link = '<link rel="stylesheet" href="'+path+'" />';
        		$("body").append($(link));
        	}
        },
        /**
         * 将插件名称存到数组中*/
        PlugList:function(name){
            arr.push(name);
            var obj = {},arrs = [];
            for(var i=0;i<arr.length;i++){
                if(!obj[arr[i].filename]){
                    arrs.push(arr[i]);
                    obj[arr[i].filename] = 1;
                }
            }
            arr = arrs;
            return arr;
    	},

        /**
         * 获取插件对应的lang包的内容
         * key:全局变量（zh-cn...）
       */
        transformLanguage:function(key){
        	window.UDP =  window.UDP || {};
            key = key.toLowerCase();
            for(var i=0;i<arr.length;i++){
            	var path = '/assets/'+arr[i].fileType+'/'+arr[i].filename+'/lang/'+key+"/"+key+".js";
	            var str = UDP.Public.createTag("script",path);
            }
            //console.log(UDP.I18N)
            if(UDP.I18N){
            	I18N = UDP.I18N[key];
            }else{
            	return false;
            }
            return  I18N; 
        },
        
        
        /**
         key:键名
         defaultmess:键值不存在情况下，给的默认值
         args：键值中需要的变量
         * */
        getmessage:function(key,defaultmess,args){
        	
        	//根据键名匹配键值
        	var langMess = eval("I18N."+key);
        	
    		//如果查找不到对应的键值，设置默认值
    		if(!langMess){
                langMess = defaultmess;
            }
        	//判断键值中是否携带参数
            if(args){
                if(langMess.indexOf("{")>-1){
                    for(var i=0;i<args.length;i++){
                        var index = args[i];
                        langMess = langMess.replace("{"+i+"}",index);
                    }
                }
            }
            return langMess;
        }
    }
}


;(function (win) {
    if (win["UDP"]) {
        win["UDP"].Public = Public();
    } else {
        win.UDP = {Public: Public()};
    }
})(window);



/**
 *单独剥离出模版功能
 * 使用方式 $.template('hello <%=name%>')({name:'zohan'});
 * @author zohan
 */

(function ($) {
    var templateSettings = {
            evaluate: /<%([\s\S]+?)%>/g,
            interpolate: /<%=([\s\S]+?)%>/g,
            escape: /<%-([\s\S]+?)%>/g
        },
        noMatch = /(.)^/;
    // Certain characters need to be escaped so that they can be put into a
    // string literal.
    var escapes = {
        "'": "'",
        '\\': '\\',
        '\r': 'r',
        '\n': 'n',
        '\u2028': 'u2028',
        '\u2029': 'u2029'
    };
    var escapeChar = function (match) {
        return '\\' + escapes[match];
    };
    var escaper = /\\|'|\r|\n|\u2028|\u2029/g;

    /**
     * 模版功能：
     * @param text
     * @param settings
     * @param oldSettings
     * @returns {Function}
     */
    $.template = function (text, settings, oldSettings) {
        if (!settings && oldSettings) settings = oldSettings;
        settings = $.extend(settings, templateSettings);

        // Combine delimiters into one regular expression via alternation.
        var matcher = RegExp([
                (settings.escape || noMatch).source,
                (settings.interpolate || noMatch).source,
                (settings.evaluate || noMatch).source
            ].join('|') + '|$', 'g');
        // Compile the template source, escaping string literals appropriately.
        var index = 0;
        var source = "__p+='";
        text.replace(matcher, function (match, escape, interpolate, evaluate, offset) {
            source += text.slice(index, offset).replace(escaper, escapeChar);
            index = offset + match.length;

            if (escape) {
                source += "'+\n((__t=(" + escape + "))==null?'':_.escape(__t))+\n'";
            } else if (interpolate) {
                source += "'+\n((__t=(" + interpolate + "))==null?'':__t)+\n'";
            } else if (evaluate) {
                source += "';\n" + evaluate + "\n__p+='";
            }

            // Adobe VMs need the match returned to produce the correct offest.
            return match;
        });
        source += "';\n";

        // If a variable is not specified, place data values in local scope.
        if (!settings.variable) source = 'with(data||{}){\n' + source + '}\n';

        source = "var __t,__p='',__j=Array.prototype.join," +
            "print=function(){__p+=__j.call(arguments,'');};\n" +
            source + 'return __p;\n';
        try {
            var render = new Function(settings.variable || 'data', '_', source);
        } catch (e) {
            e.source = source;
            throw e;
        }

        var template = function (data) {
            return render.call(this, data);
        };

        // Provide the compiled source as a convenience for precompilation.
        var argument = settings.variable || 'data';
        template.source = 'function(' + argument + '){\n' + source + '}';

        return template;


    };
})(jQuery);

Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

$(function(){
    //.ajaxError事件定位到document对象，文档内所有元素发生ajax请求异常，都将冒泡到document对象的ajaxError事件执行处理
    $(document).ajaxError(
        //所有ajax请求异常的统一处理函数，处理
        function(event,xhr,options,exc ){
            var toolTip = new diaPrompt();
            //public-method中提示信息的多语言转化
            var noNetwork = UDP.Public.getmessage("publicMethod.abnormalPrompts.noNetwork","哎呀！貌似没有网络啦！");
	       	var loginAgain = UDP.Public.getmessage("publicMethod.abnormalPrompts.loginAgain","登录信息失效，请重新登录。是否进入登录页面？");
	       	var notAccess = UDP.Public.getmessage("publicMethod.abnormalPrompts.notAccess","系统拒绝：您没有访问权限。");
	       	var resourcesVisitNotExist = UDP.Public.getmessage("publicMethod.abnormalPrompts.resourcesVisitNotExist","您访问的资源不存在。");
	       	var dataAbnormal = UDP.Public.getmessage("publicMethod.abnormalPrompts.dataAbnormal","数据出现异常。");
            if(xhr.status == 'undefined'){
                return;
            }
            switch(xhr.status){
                case 0:
                    toolTip.error({
                        message:noNetwork,
                        callback:function(){}
                    });
                    break;
                case 401:
                    toolTip.error({
                        message:loginAgain,
                        callback:function(){
                            top.getLogout();
                        }
                    });
                    break;
                case 403:
                    toolTip.error({// 未授权异常
                        message:notAccess,
                        callback:function(){}
                    });
                    break;
                case 404:
                    toolTip.error({
                        message:resourcesVisitNotExist,
                        callback:function(){}
                    });
                    break;
                case 500:
                    toolTip.error({
                        message:dataAbnormal,
                        callback:function(){}
                    });
                    break;
            }
        }

    );
});



// 动态加载css文件
function loadStyles(url) {
    var link = document.createElement("link");
    link.type = "text/css";
    link.rel = "stylesheet";
    link.href = url;
    top.$("link[databind]").each(function(){
        fnLink(this,link);
    });
    for (var i=0; i<frames.length; i++)    {
        $("link[databind]",frames[i].document).each(function(){
            fnLink(this,link);
        });
    }
    function fnLink(ele,tag){
        $(ele).after("<"+tag+">").attr({
            rel: "stylesheet",
            type: "text/css",
            dataBind:"switchFile",
            href: url
        });
    }
}
/*创建link标签*/
;(function($){
    $.extend({
        "createtag":function(ele,rel,type,href){
            $("head").append("<"+ele+">");
            var css = $("head").children("link:last");
            css.attr({
                rel: rel,
                type:type,
                databind:"switchFile",
                href:href
            });
        }});
    $.extend({"revamptag":function(ele,rel,type,href){
        $("head").append("<"+ele+">");
        var css = $("head").children("link:last");
        css.attr({
            rel: rel,
            type:type,
            databind:"switchFile",
            href:href
        });
    }
    });
})(jQuery);


$(function(){
	var publicMethod = {
    		"fileType":"plug",
    		"filename":"publicMethod"
    }
	UDP.Public.PlugList(publicMethod);
});






























