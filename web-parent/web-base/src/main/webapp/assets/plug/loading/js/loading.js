/**
 * Created by Administrator on 2017/12/20 0020.
 */

function loading(options) {
    this.config = $.extend({
        bgcolor: "#f8fef8",  // 背景色
        style: null,          // 可选的样式参数
        fristbtnclass: null,    // input第一种按钮的类名
        secondbtnclass:null,    // input第二种按钮的类名
        thirdbtnclass:null,     // input第三种按钮的类名
        localityfirstClass:null, // 局部loading 第一种样式div的类名
        localitysecondClass:null,// 局部loading 第二种样式div的类名
        inputwidth:"",
        inputclassarr:[]
    }, options);
    this.state = {
        firststyle: null,
        secondstyle: null,
        thirdstyle: null,
        fourthstyle: null,
        localitystylef:null
    };
    this.init();
    return this
}
loading.prototype = {
    init: function() {
        this.pattern();
        this.readyStates();
    },
    // 页面加载完成的方法 
    readyStates: function() {
        var that = this;
        document.onreadystatechange = loadingChange = function() {
            if (document.readyState == "complete") {
                that.closeloading()
            }
        }
    },
    // 配置那个样式的loading放入页面
    pattern: function() {
        var style = this.config.style;
        var bgcolor = this.config.bgcolor;
        switch (style) {
            case 'style1':
                this.creatstylefdiv(bgcolor);
                break;
            case 'style2':
                this.creatstylesdiv(bgcolor);
                break;
            case 'style3':
                this.creatstyletdiv(bgcolor);
                break;
            case 'style4':
                this.creatstylefodiv(bgcolor);
                break;
        }
    },
    // 创建loading 的第一种样式的div
    creatstylefdiv: function(bgcolor) {
        this.state.firststyle = '<div class="style loading" style="background:' + bgcolor + '"><div class="contents">加载中...</div></div>';
        $("body").prepend(this.state.firststyle);
    },
    // 创建loading 的第二种样式的div
    creatstylesdiv: function(bgcolor) {
        this.state.secondstyle = '<div class="style secondstyle" style="background:' + bgcolor + '" ><div class="loadEffect"> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> <span></span> </div></div>'
        $("body").prepend(this.state.secondstyle);
    },
    // 创建loading 的第三种样式的div
    creatstyletdiv: function(bgcolor) {
        this.state.thirdstyle = '<div style="background:' + bgcolor + '" class="style thirdstyle"><div class="loadings"> <span></span> <span></span> <span></span> <span></span> <span></span> </div></div>';
        $("body").prepend(this.state.thirdstyle);
    },
    // 创建loading 的第四样式的div
    creatstylefodiv: function(bgcolor) {
        this.state.fourthstyle = '<div style="background:' + bgcolor + '" class="style thirdstyle"><div class="spinner"> <div class="rect1"></div> <div class="rect2"></div> <div class="rect3"></div> <div class="rect4"></div> <div class="rect5"></div> </div></div>';
        $("body").prepend(this.state.fourthstyle);
    },
    // 局部loading样式一
    localitystylefirst:function(){
        var localitys=this.config.localityfirstClass,
            bgcolor = this.config.bgcolor;
        localitys.split(",").forEach(function(item,i){
            $(".localitystylef").remove();
            var height=$(item).height()/2;
            $(item).prepend( '<div style="background:' + bgcolor + ';position: relative;" class="style localitystylef"> <div class="spinner"></div></div>')
        });
    },
    // 局部loading样式二
    localitystylesecond:function(bgcolor){
        var localitys=this.config.localitysecondClass,
            bgcolor = this.config.bgcolor;
        localitys.split(",").forEach(function(item,i){
            $(".localitystyles").remove();
            var height=$(item).height();
            $(item).prepend( '<div style="background:'+ bgcolor + ';text-align:center;line-height:'+ height + 'px;position: relative;" class="style localitystyles">加载中...</div>')
        });
    },
    // 按钮loading样式一
    buttonstylefirst: function() {
            var classinput = this.config.fristbtnclass,
                inputheight = $(classinput).height(),
                bgcolor = this.config.bgcolor,
                inputwidth = this.config.inputwidth=$(classinput).width(),
                coverdiv=$('<div style="height:'+inputheight+'px;margin-top:'+ -inputheight+'px;background:' + bgcolor + '" class="animation"></div>'),
                inputclassed= this.config.inputclassed= $(classinput).attr("class"),
                inputid=$(classinput).attr("id");
            this.config.inputclassarr=[];
            this.config.inputclassarr.push(inputclassed,classinput,$(classinput).val(),inputid);
            $("input"+classinput).hide();
            if($(classinput).parent().find("div").attr("id")=="first"){return};
            $(classinput).parent().prepend($("<div id='first'></div>").css({"line-height":inputheight+"px","text-align":"center","display": "inline-block"}).text($(classinput).val()).attr("class",inputclassed).append(coverdiv).addClass("return"));
       },
    // 按钮loading样式二
    buttonstylesecond: function() {
        var classinput = this.config.secondbtnclass,
            inputheight = $(classinput).height(),
            bgcolor = this.config.bgcolor,
            inputwidth = this.config.inputwidth=$(classinput).width(),
            coverdiv=$('<div style="height:6px;margin-top:'+ -inputheight+'px;background:' + bgcolor + '" class="animation"></div>'),
            inputclassed= this.config.inputclassed= $(classinput).attr("class"),
            inputid=$(classinput).attr("id");
        this.config.inputclassarr=[];
        this.config.inputclassarr.push(inputclassed,classinput,$(classinput).val(),inputid);
        $("input"+classinput).hide();
        if($(classinput).parent().find("div").attr("id")=="first"){return};
        $(classinput).parent().prepend($("<div id='first'></div>").css({"line-height":inputheight+"px","text-align":"center","display": "inline-block"}).text($(classinput).val()).attr("class",inputclassed).append(coverdiv).addClass("return"));
    },
    // 按钮loading样式三
    buttonstylethird: function() {
        var classinput = this.config.thirdbtnclass,
            inputheight = $(classinput).height(),
            bgcolor = this.config.bgcolor,
            inputwidth = this.config.inputwidth=$(classinput).width(),
            coverdiv=$('<div style="width:'+inputwidth+'px;margin-top:'+ -inputheight+'px;background:' + bgcolor + '" class="animationed"></div>'),
            inputclassed= this.config.inputclassed= $(classinput).attr("class"),
            inputid=$(classinput).attr("id");
        this.config.inputclassarr=[];
        this.config.inputclassarr.push(inputclassed,classinput,$(classinput).val(),inputid);
        $("input"+classinput).hide();
        if($(classinput).parent().find("div").attr("id")=="first"){return};
        $(classinput).parent().prepend($("<div id='first'></div>").css({"line-height":inputheight+"px","text-align":"center","display": "inline-block"}).text($(classinput).val()).attr("class",inputclassed).append(coverdiv).addClass("return"));
    },

    // 关闭按钮样式的方法
    buttoncloseloading: function() {
        var that=this;
        var inputwidth = this.config.inputwidth;
        $(".animation").css("width", inputwidth + "px");
        setTimeout(function() { $(".return").remove(); that.restore(); }, 2200);
    },
    restore:function(){
        var Array= this.config.inputclassarr;
        $(Array[1]).parent().prepend($("<input type='button'  value='"+ Array[2] + "'  class='"+ Array[0] + "'>"));
        $(Array[1]).eq(1).remove();
    },
    // 关闭loading样式的方法
    closeloading: function() {
        setTimeout(function() {
            $(".loading,.secondstyle,.thirdstyle,.fourthstyle,.localitystylef,.localitystyles").remove();
        }, 1000);
    },
    // 可以再次调用生成loading的方法
    buttonloading: function() {
        $(".style").remove();
        this.pattern();
    }
};
(function(win) {
    if (win["UDP"]) {
        win["UDP"].loading = loading
    } else {
        win.UDP = { loading: loading }
    } })(window);