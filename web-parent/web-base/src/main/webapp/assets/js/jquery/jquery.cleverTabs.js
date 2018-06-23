/* jQuery iFrame Tabs Plugin
 *  Version: 0.5.01
 *  Author: think8848
 *  Contact: QQ: 166156888 Blog: http://think8848.cnblogs.com
 *  Company: http://www.cleversoft.com */
;
$.fn.cleverTabs = function (options) {
    var self = this;
    var options = $.extend({
        setupContextMenu: false,
        navigation:true,
        dir: 'web'
    }, options || {});
    var tabs = new CleverTabs(self, options);
    if (options.setupContextMenu) {
        tabs.setupContextMenu();
    }
    if (options.navigation) {
        tabs.navigation();
    }else {
        document.oncontextmenu = function (e) {
            e.preventDefault();
        };
    }
    return tabs;
};

//定义CleverTabs对象
function CleverTabs(self, options) {
    var self = self;
    this.hashtable = new Array();
    this.options = $.extend({
        //锁定Tab，不允许关闭
        locked: false,
        //禁止选中Tab
        disable: false,
        //保存本地的目录，多个系统之间不会冲突
        dir: 'web',
        //每个Tab是否生成关闭按钮
        close: true,
        //当只有一个Tab页面时是否锁定该Tab
        lockOnlyOne: true,
        //Tab用于显示Panel的容器
        panelContainer: (function () {
            var tick = new Date().getTime();
            var panelElement = $('<div id="cleverTabsPanelContainer' + tick + '" ></div>');
            self.append(panelElement);
            return panelElement;
        })(),
        //Tab用于控制的头模板
        tabHeaderTemplate: '<li id="cleverTabHeaderItem-#{id}" class="#{liclass}"><a href="#" title="#{title}">#{label}</a></li>',
        //Tab用于显示的Panel模板
        tabPanelTemplate: '<div id="cleverTabPanelItem-#{id}" style="height: 100%;"><iframe name="iFrame" frameBorder="0" style="width: 100%; display: inline; height: 100%;" data_src="#{data_src}" src="#{src}"></iframe></div>',
        //Tab唯一id生成器
        uidGenerator: function () { return new Date().getTime(); }

    }, options || {});

    self.addClass("ui-tabs ui-widget ui-widget-content ui-corner-all");
    this.wrapper = self;
    var el = self.find('ol,ul').eq(0);
    this.element = el;
    this.element.addClass("ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all");//tab处在的ul容器
    var pc = this.options.panelContainer;
    this.panelContainer = pc;
    if (!this.panelContainer.hasClass('ui-tabs')) {
        this.panelContainer.addClass('ui-tabs');
    }
    this.panelContainer.addClass("ui-tabs-panel ui-widget-content ui-corner-bottom");
    this.lockOnlyOne = this.options.lockOnlyOne;
    if (this instanceof CleverTabs) {
        CleverTabs.prototype.resizePanelContainer = function () {
            if (pc.attr('id').indexOf('cleverTabsPanelContainer') === 0) {
                var height = self.height() - el.height() - 30;
                //pc.css('height', Math.floor(height / self.height() * 100) + '%');
            }
        }
    }
    try{
        if('localStorage' in window && window['localStorage'] !== null){
            this.storage = window.localStorage;
            this.url_historys= this.storage.getItem(this.options.dir+"tab_his")?$.evalJSON(this.storage.getItem(this.options.dir+"tab_his")):[];
        }else{
            this.storage=null;
            this.url_historys=[];
            //启用cookie

        }
    }catch(e){
        //浏览器不兼容localStorage
        this.storage=null;
        this.url_historys=[];
    }
};
// 关闭导航栏功能
CleverTabs.prototype.navigation = function(){
    $(document).on('contextmenu','.ui-tabs-nav li',function(e){
        var thisId  =$(this).attr("id");
        var tab1 = new CleverTab(tabs, CleverTab.getUniqueByHeaderId(thisId));
        tab1.activate(true);
        var Selected=tabs.getCurrentTab();
        var id = tabs.getCurrentUniqueId();
        var tab = new CleverTab(tabs, id);
        var ids = tab.prevTab();
        if(Selected.label=='工作台'&e.button ==2){
            $('#box').show().css({
                'top':e.pageY+'px',
                'left':e.pageX+'px',
            });
            $('#mnuUnlock a').css({'color':'black'});
            $('#mnuUnlock').removeClass('lock');
            $('#box #mnuEnabl').css({'display':'none'});
            $('#box #mnuRefresh').css({'display':'none'});
            $('#box #mnuLock').css({'display':'none'});
            e.preventDefault();
        }else{
            $('#box #mnuEnabl').css({'display':'block'});
            $('#box #mnuRefresh').css({'display':'block'});
            $('#box #mnuLock').css({'display':'block'});
        }
        if($('.ui-tabs-nav li').length==1&Selected.label=='工作台'&e.button ==2){
            $('#box').show().css({
                'top':e.pageY+'px',
                'left':e.pageX+'px',
            });
            $('#mnuUnlock a').css({'color':'#cccccc'});
            $('#mnuUnlock').addClass('lock');
            $('#mnuUnlock a').removeClass('color');
            return
        }

        var lisecond=$('.ui-tabs-nav li:eq(1)')[0].id.substring(20)
        if(lisecond==id){
            $('#mnuLock a').css({'color':'#cccccc'})
            $('#mnuLock').addClass('lock')
        }else{
            $('#mnuLock a').css({'color':'black'})
            $('#mnuLock').removeClass('lock')
        }
        var idb = tab.nextTab();
        if(idb===null){
            $('#mnuUnlock a').css({'color':'#cccccc'});
            $('#mnuUnlock').addClass('lock')

        }else{
            $('#mnuUnlock a').css({'color':'black'});
            $('#mnuUnlock').removeClass('lock');
        }
        $('#box').show().css({
            'top':e.pageY+'px',
            'left':e.pageX+'px'
        });
        e.preventDefault();
        if(isInclude("science/css/style.css")){
            $('#box li>a').addClass('color')
            var Selecteda=tabs.getCurrentTab();
            var lisecond=$('.ui-tabs-nav li:eq(1)')[0].id.substring(20)
            if(lisecond==id){
                $('#mnuLock a').removeClass('color')
                $('#mnuLock a').css({'color':'#cccccc !important'})
                $('#mnuUnlock').addClass('lock')
            }else{
                $('#mnuLock a').css({'color':'black'})
                $('#mnuUnlock a').removeClass('lock')
            }
            var idb = tab.nextTab();
            if(idb===null){
                $('#mnuUnlock a').removeClass('color')
                $('#mnuUnlock a').css({'color':'#cccccc !important'});
                $('#mnuUnlock').addClass('color')
            }else{
                $('#mnuUnlock a').css({'color':'black'});
                $('#mnuUnlock').removeClass('lock');
            }
        }
    });

    function isInclude(name) {
        var js = /js$/i.test(name);
        var es = document.getElementsByTagName(js ? 'script' : 'link');
        for (var i = 0; i < es.length; i++)
            if (es[i][js ? 'src' : 'href'].indexOf(name) != -1) return true;
        return false;
    }
    $(document).on('click','#box li',function() {
        var ids=$(this)[0].id;
        switch(ids){
            case 'mnuCloseAll': // 全部删除
                tabs.clear();
                break;
            case 'mnuEnabl':// 刷新页面
                clearInterval(timer);
                var id = tabs.getCurrentUniqueId();
                var tab = new CleverTab(tabs, id);
                tab.kill();
                var timer=setTimeout(function(){
                    var id = tabs.getCurrentUniqueId();
                    var tab = new CleverTab(tabs, id);
                    tab.refresh();
                },2);
                break;
            case 'mnuDisalb':// 关闭当前页面
                var id = tabs.getCurrentUniqueId();
                var tab = new CleverTab(tabs, id);
                tab.refresh();
                break;
            case  'mnuLock': // 关闭左侧页面
                tabs.clearLeft();
                break;
            case 'mnuUnlock':// 关闭右侧页面
                tabs.clearRight();
                break;
            case 'mnuRefresh':// 关闭其他页面
                tabs.clearElse();
                break;
        }
    });
    var flag = false;
    var isInClick = false;
    var isHasClick = false;
    var timer = null;
    $(document).on('contextmenu','.ui-tabs-nav li',function(event) {
        isInClick = true;
        if (flag) {
            flag = false;
            $('#box').hide();
        } else {
            flag = true;
            $('#box').show();
            $('#box').hover(function() {
                clearInterval(timer);
                if (isHasClick == false) {
                    $('#box li').click(function() {
                        flag = false;
                        $('#box').hide();
                    });
                    isHasClick = true;
                }
            }, function() {
                flag = false;
                $(this).hide();
            })
        }
        $('#tabs').mouseleave(function() {
            if (isInClick) {
                timer = setTimeout(function() {
                    flag = false;
                    $('#box').hide();
                }, 500);
            }
            isInClick = false;
        });
    });
}
//操作本地存数数据
CleverTabs.prototype.load_from_storage = function () {

    if(!!tabs.url_historys &&  typeof tabs.url_historys !='undefined' && tabs.url_historys.length>0){
        for(var i=0;i<tabs.url_historys.length;i++){
            var item = tabs.url_historys[i];
            if(typeof item !='undefined' && typeof item.url!='undefined' ){
                tabs.add({
                    url: item.url,
                    label: item.label,
                    user_click:false,//用户点击是false
                    locked:false//
                });
            }
        }
        //console.log(tabs.url_historys);
    }
};
//是否能继续显示tab，返回true表示能继续显示更多的tab，否则返回false
CleverTabs.prototype.can_display_tab = function () {

    var sumWidth =0;
    var showSumWidth =0;//目前处于显示状态的tab的宽度
    $(".xhn_topNavUl").find("li").each(function(){
        sumWidth += $(this).width()+30;//margin和padding数值总和
        if($(this)[0].style.display !='none'){
            showSumWidth += $(this).width()+30;
        }
    });
    sumWidth += 50;
    showSumWidth += 80;
    return ($(".xhn_topNavUl").width()-showSumWidth)>0;

};
//隐藏倒数第二个tab，如果已经隐藏了，就继续向上找上一个
CleverTabs.prototype.hide_pre_pre_tab = function () {

    var lis = $(".xhn_topNavUl li");
    if(lis.length>3){
        for(var i=lis.length-2;i>=0;i--){
            if(lis[i].style.display !='none'){
                lis[i].style.display ='none';
                return ;
            }
        }
    }
    return ;

};
CleverTabs.prototype.add_storage = function (item) {
    var self = this;
    //console.log(self);
    setTimeout(function(){
        var m = {};
        m.id=item.id;
        m.label=item.label;
        m.url = item.url;
        for(var i=0;i<tabs.url_historys.length;i++){
            var h = tabs.url_historys[i];
            if(!!h){
                if(h.url==m.url){
                    return ;
                }
            }
        }
        tabs.url_historys.push(m);
        if(tabs.storage!=null){
            tabs.storage.setItem(self.options.dir+"tab_his",$.toJSON(tabs.url_historys));
        }

    },0);

};
CleverTabs.prototype.del_storage = function (item) {
    var self = this;
    setTimeout(function(){
        var m = {};
        m.id=item.id;
        m.label=item.label;
        m.url = item.url;
        for(var i=0;i<tabs.url_historys.length;i++){
            var h = tabs.url_historys[i];
            if(!!h){
                if(h.url==m.url){
                    tabs.url_historys.splice(i,1);
                }
            }
        }
        if(tabs.storage!=null){
            tabs.storage.setItem(self.options.dir+"tab_his",$.toJSON(tabs.url_historys));
        }

    },0);

};
//添加Tab
CleverTabs.prototype.add = function (options) {
    var self = this;
    var uid = self.options.uidGenerator(self);
    var options = $.extend({
        id: uid,
        locked:false,
        save:true,
        url: '#',
        label: null,
        user_click: true,
        closeRefresh: null,
        closeActivate: null,
        add_callback: function () { },
        callback: function () { }
    }, options || {});

    
    var _url = options.url;
    var label=options.label;
    var exsitsTab = self.getTabByUrl(_url);
    if (exsitsTab) {
        if (!exsitsTab.activate(options.user_click)) {
            return false;
        }
    }


    //生成Tab头
    var tabHeader = $(self.options.tabHeaderTemplate
        .replace(/#\{id\}/g, options.id)
        .replace(/#\{liclass\}/g, 'ui-state-default ui-corner-top')
        .replace(/#\{title\}/g, options.label)
        .replace(/#\{label\}/g, function () {
            //如果Tab的Label大于7个字符，则强制使其为前7个字符加'...'
            if (options.label !=null && options.label !='undefined' && options.label.length > 7) {
                return options.label.substring(0, 7) + '...';
            }
            return options.label;
        } ()));
    if(options.label==null){
        return false;
    }
    //Tab头绑定click事件
    tabHeader.bind("click", function () {
        if (!$(this).hasClass('ui-state-disabled')) {
            tab.activate(true);

        }
    });
    //Tab头绑定mouseover事件
    tabHeader.bind('mouseover', function () {
        if (!$(this).hasClass('ui-state-disabled')) {
            $(this).addClass('ui-state-hover');
        }
    });
    //Tab头绑定mouseout事件
    tabHeader.bind('mouseout', function () {
        if (!$(this).hasClass('ui-state-disabled')) {
            $(this).removeClass('ui-state-hover');
        }
    });
    var panel = $(self.options.tabPanelTemplate
        .replace(/#\{id\}/g, options.id)
        .replace(/#\{data_src\}/g, options.url));
    //生成Tab显示面板
    if(options.user_click){
        panel = $(self.options.tabPanelTemplate
            .replace(/#\{id\}/g, options.id)
            .replace(/#\{data_src\}/g, options.url)
            .replace(/#\{src\}/g, options.url));
    }else{
        panel = $(self.options.tabPanelTemplate
            .replace(/#\{id\}/g, options.id)
            .replace(/#\{data_src\}/g, options.url)
            .replace(/#\{src\}/g, ""));
    }
    /*
     var panel = $(self.options.tabPanelTemplate
     .replace(/#\{id\}/g, options.id)
     .replace(/#\{src\}/g, options.url.toLowerCase()));
     */
    self.element.append(tabHeader);
    //没有办法的办法，因为无法使iframe自动"撑大"外面的div，所在只能在这里计算生成的Tab页面容器的高度，
    //注意，如果style属性还设置了除height之外的样式，则需要修改这里的代码，自求多福吧
    //ps: 可以使用https://github.com/aaronmanela外的jQuery.iframe.auto.height插件做到“撑大”外层的div
    //但是不能支持跨域访问，虽然这不是大问题，但是我没有采用这个方法
    if (self.panelContainer.attr('id').indexOf('cleverTabsPanelContainer') === 0) {
        var height = self.wrapper.height() - self.element.height() - 30;
    }
    self.panelContainer.append(panel);
    self.hashtable[options.id] = { 'callback': options.callback,'add_callback':options.add_callback, 'closerefresh': options.closeRefresh, 'closeactivate': options.closeActivate, 'orgLock': options.lock };
    var tab = new CleverTab(self, options.id);
    var unlock;
    if(typeof options.locked =='undefined'){
        options.locked = false;
    }

    tab.header.append(unlock);
    tab.setLock(options.locked);
    tab.activate(false);
    if(options.save != 'undefined' && options.save==true){
        tabs.add_storage(tab);
    }
    if(tabs.can_display_tab()){
        //可以继续显示隐藏的,那么什么都不干

    }else{
        //已经不能再显示更多的tab了，此时需要隐藏掉倒数第二个
        tabs.hide_pre_pre_tab();
    }
    return tab;

};
//添加子Tab
CleverTabs.prototype.add_child = function (options) {
    var self = this;
    var uid = self.options.uidGenerator(self);
    var options = $.extend({
        id: uid,
        locked:false,
        save:true,
        url: '#',
        label: null,
        user_click: true,
        closeRefresh: null,
        closeActivate: null,
        add_callback: function () { },
        callback: function () { }
    }, options || {});

    //验证指定Url的Tab是否已经开启，如果开启则kill掉，让当前的显示
    var exsitsTab = self.getTabByUrl(options.url);
    if (exsitsTab) {
        var _url = exsitsTab.url;
        _url =  _url.indexOf('?') == -1 ? _url : _url.substring(0,_url.indexOf('?'));
        if(_url){
           var exitUrl = self.getTabByUrl(_url);
            exitUrl.kill();
        }
    }
    //生成Tab头
    var tabHeader = $(self.options.tabHeaderTemplate
        .replace(/#\{id\}/g, options.id)
        .replace(/#\{liclass\}/g, 'ui-state-default ui-corner-top')
        .replace(/#\{title\}/g, options.label)
        .replace(/#\{label\}/g, function () {
            //如果Tab的Label大于7个字符，则强制使其为前7个字符加'...'
            if (options.label !=null && options.label !='undefined' && options.label.length > 7) {
                return options.label.substring(0, 7) + '...';
            }
            return options.label;
        } ()));
    if(options.label==null){
        return false;
    }
    //Tab头绑定click事件
    tabHeader.bind("click", function () {
        if (!$(this).hasClass('ui-state-disabled')) {
            tab.activate(true);
        }
    });
    //Tab头绑定mouseover事件
    tabHeader.bind('mouseover', function () {
        if (!$(this).hasClass('ui-state-disabled')) {
            $(this).addClass('ui-state-hover');
        }
    });
    //Tab头绑定mouseout事件
    tabHeader.bind('mouseout', function () {
        if (!$(this).hasClass('ui-state-disabled')) {
            $(this).removeClass('ui-state-hover');
        }
    });
    var panel = $(self.options.tabPanelTemplate
        .replace(/#\{id\}/g, options.id)
        .replace(/#\{data_src\}/g, options.url));
    //生成Tab显示面板
    if(options.user_click){
        panel = $(self.options.tabPanelTemplate
            .replace(/#\{id\}/g, options.id)
            .replace(/#\{data_src\}/g, options.url)
            .replace(/#\{src\}/g, options.url));
    }else{
        panel = $(self.options.tabPanelTemplate
            .replace(/#\{id\}/g, options.id)
            .replace(/#\{data_src\}/g, options.url)
            .replace(/#\{src\}/g, ""));
    }
    self.element.append(tabHeader);
    if (self.panelContainer.attr('id').indexOf('cleverTabsPanelContainer') === 0) {
        var height = self.wrapper.height() - self.element.height() - 30;
    }
    self.panelContainer.append(panel);
    self.hashtable[options.id] = { 'callback': options.callback,'add_callback':options.add_callback, 'closerefresh': options.closeRefresh, 'closeactivate': options.closeActivate, 'orgLock': options.lock };
    var tab = new CleverTab(self, options.id);
    var unlock;
    if(typeof options.locked =='undefined'){
        options.locked = false;
    }
    tab.header.append(unlock);
    tab.setLock(options.locked);
    tab.activate(false);
    console.log(options.save);
    if(options.save != 'undefined' && options.save==true){
        tabs.add_storage(tab);
    }
    if(tabs.can_display_tab()){
        //可以继续显示隐藏的,那么什么都不干

    }else{
        //已经不能再显示更多的tab了，此时需要隐藏掉倒数第二个
        tabs.hide_pre_pre_tab();
    }
    return tab;
};
//为Tab安装右键菜单
CleverTabs.prototype.setupContextMenu = function () {
    if(true) return;
    var self = this;
    var contextMenu;
    if (!self.options.contextMenu) {
        contextMenu = {
            element: $('<ul id="cleverTabsContextMenu">'
                + '<li id="mnuEnabl" ><a href="#enabled"><span class="ui-icon ui-icon-pencil" style="float: left; margin-right: 5px;"></span>Enable</a></li>'
                + '<li id="mnuDisalb"><a href="#disable"><span class="ui-icon ui-icon-cancel" style="float: left; margin-right: 5px;"></span>Disable</a></li>'
                + '<li id="mnuLock" ><a href="#lock"><span class="ui-icon ui-icon-locked" style="float: left; margin-right: 5px;"></span>Lock</a></li>'
                + '<li id="mnuUnlock"><a href="#unlock"><span class="ui-icon ui-icon-unlocked" style="float: left; margin-right: 5px;"></span>Unlock</a></li>'
                + '<li id="mnuRefresh" ><a href="#refresh"><span class="ui-icon ui-icon-refresh" style="float: left; margin-right: 5px;"></span>Refresh</a></li>'
                + '<li id="mnuCloseAll"><a href="#clear"><span class="ui-icon ui-icon-closethick" style="float: left; margin-right: 5px;"></span>Close All</a></li>'
                + '</ul>'),
            handler: function (action, el, pos) {
                var tab = self.getCurrentTab();
                switch (action) {
                    case 'enabled':
                        tab.setDisable(false);
                        break;
                    case 'disable':
                        tab.setDisable(true);
                        break;
                    case 'lock':
                        tab.setLock(true);
                        break;
                    case 'unlock':
                        tab.setLock(false);
                        break;
                    case 'refresh':
                        tab.refresh();
                        break;
                    case 'clear':
                        tabs.clear();
                        break;
                }
            }
        };
    }
    var menu = contextMenu.element;
    self.wrapper.parent().append(menu);
    self.element.contextMenu(
        { menu: menu.attr('id') }, contextMenu.handler);
}
//获取当前选中的Tab的唯一Id
CleverTabs.prototype.getCurrentUniqueId = function () {
    var self = this;
    if (self.element.find(' > li').size() > 0) {
        var current = self.element.find('li.ui-tabs-selected');
        return current.size() > 0 ? CleverTab.getUniqueByHeaderId(current.attr('id')) : null;
    } else {
        return null;
    }
}
//获取当前选中的Tab
CleverTabs.prototype.getCurrentTab = function () {
    var self = this;
    var uid = self.getCurrentUniqueId();
    return uid ? new CleverTab(self, self.getCurrentUniqueId()) : null;
}
//获取指定Url的Tab
CleverTabs.prototype.getTabByUrl = function (url) {
    if (!url) {
        return null;
    }
    var self = this;
    var frames = self.panelContainer.find('div[id^="cleverTabPanelItem-"]>iframe');
    var tab;
    for (var i = 0; i < frames.size(); i++) {
        var frame = $(frames[i]);
        var src = frame.attr('data_src');
        if (src.indexOf('clever_tabs_time_stamp=') > 0) {
            src = src.substring(0, src.indexOf('clever_tabs_time_stamp=') - 1);
        }
        var _src = src.toLowerCase();
        var _url = url.toLowerCase();
        _src = _src.indexOf('?') == -1 ? _src : _src.substring(0,_src.indexOf('?'));
        _url = _url.indexOf('?') == -1 ? _url : _url.substring(0,_url.indexOf('?'));
        if (_src == _url) {
            return new CleverTab(self, CleverTab.getUniqueByPanelId(frame.parent('div:first').attr('id')));
        }
    }
    return tab;
}

CleverTabs.prototype.clearElse = function() {
    var self = this;
    var lis = self.element.find('>li');

    var id= tabs.getCurrentTab();
    console.log(id)

    for(var i=0;i<lis.length;i++){
        console.log(lis[i].id)
        if(lis[i].id==id.headerId){
            console.log(i)
            lis.splice(i,1)
        }
    }

    var hasLock = self.element.find('span.ui-icon-locked').size() > 0;
    for (i = self.lockOnlyOne && !hasLock ? 1 : 0; i < lis.size(); i++) {
        var id = CleverTab.getUniqueByHeaderId(lis.eq(i).attr('id'));
        var tab = new CleverTab(self, id);
        tab.kill();
    }
}


CleverTabs.prototype.clearA = function(url,label) {
    var self = this;
    var lis = self.element.find('>li');
    var id= tabs.getCurrentTab();

    for(var i = 0;i<lis.length;i++){
        if(lis[i].id==id.headerId){

            lis.splice(0,i);
            lis.splice(i,lis.length-1);
        }
    };
    for (i = 0; i < lis.length; i++) {
        if(lis[i].id==id.headerId){
            var id = CleverTab.getUniqueByHeaderId(lis.eq(i).attr('id'));
            var tab = new CleverTab(self, id);
            tab.kill();
            tabs.add({
                url: url,
                label: label,
                user_click:true,
                save:false,
                locked: false,
                add_callback:function(){
                }
            });
            var timer=setTimeout(function(){
                var id = tabs.getCurrentUniqueId();
                var tab = new CleverTab(tabs, id);
                tab.refresh();
            },1);
        }
    }
    }

//关闭当前页签，不刷新列表页签
CleverTabs.prototype.clearClose = function(url,label) {
	var self = this;
	var lis = self.element.find('>li');
	var id= tabs.getCurrentTab();
	
	for(var i = 0;i<lis.length;i++){
		if(lis[i].id==id.headerId){
			
			lis.splice(0,i);
			lis.splice(i,lis.length-1);
		}
	};
	for (i = 0; i < lis.length; i++) {
		if(lis[i].id==id.headerId){
			var id = CleverTab.getUniqueByHeaderId(lis.eq(i).attr('id'));
			var tab = new CleverTab(self, id);
			tab.kill();
			tabs.add({
				url: url,
				label: label,
				user_click:true,
				save:false,
				locked: false,
				add_callback:function(){
				}
			});
		}
	}
}

CleverTabs.prototype.clearRight = function() {
    var self = this;
    var lis = self.element.find('>li');
    var id= tabs.getCurrentTab();
    for(var i=0;i<lis.length;i++){
        console.log(lis[i].id)
        if(lis[i].id==id.headerId){
            console.log(i)
            lis.splice(0,i)
        }
    }
    var hasLock = self.element.find('span.ui-icon-locked').size() > 0;
    for (i = self.lockOnlyOne && !hasLock ? 1 : 0; i < lis.size(); i++) {
        var id = CleverTab.getUniqueByHeaderId(lis.eq(i).attr('id'));
        var tab = new CleverTab(self, id);
        tab.kill();
    }
}

CleverTabs.prototype.clearLeft = function() {
    var self = this;
    var lis = self.element.find('>li');
    var id= tabs.getCurrentTab();
    for(var i = 0;i<lis.length;i++){
        console.log(lis[i].id)
        console.log(id.headerId)
        if(lis[i].id==id.headerId){
            console.log(i)
            lis.splice(i,lis.length-1)
        }
    }
    var hasLock = self.element.find('span.ui-icon-locked').size() > 0;
    for (i = self.lockOnlyOne && !hasLock ? 1 : 0; i < lis.size(); i++) {
        var id = CleverTab.getUniqueByHeaderId(lis.eq(i).attr('id'));
        var tab = new CleverTab(self, id);
        tab.kill();
    }
}
//清除所有Tab页面
CleverTabs.prototype.clear = function () {
    var self = this;
    var lis = self.element.find('>li');
    var hasLock = self.element.find('span.ui-icon-locked').size() > 0;
    for (i = self.lockOnlyOne && !hasLock ? 1 : 0; i < lis.size(); i++) {
        var id = CleverTab.getUniqueByHeaderId(lis.eq(i).attr('id'));
        var tab = new CleverTab(self, id);
        tab.kill();
    }
}
//定义Tab页面类
function CleverTab(tabs, id) {
    //Tab控件
    this.tabs = tabs;
    //Tab页面Id
    this.id = id
    //Tab页面头
    this.header = this.tabs.element.find('li#' + CleverTab.getFullHeaderId(id));
    this.headerId = this.header.attr('id');
    //Tab页面是否可激活
    this.disable = this.header.hasClass('ui-state-disabled');
    //Tab页面是否锁定(不允许关闭)
    this.lock = this.header.find('span.ui-icon-locked').size() != 0;
    //Tab页面有于显示内容的面板
    this.panel = tabs.panelContainer.find('div#' + CleverTab.getFullPanelId(id));
    //Tab页面id
    this.panelId = this.panel.attr('id');
    //Tab标题
    this.label = (this.header ? this.header.find('a:first').attr('title') : null);
    //Tab中iframe的url
    this.url = (this.panel ? this.panel.find(' > iframe:first').attr('data_src') : null);
    //Tab关闭时的回调函数
    this.callback = this.tabs.hashtable[id].callback;
    //创建成功以后的回调函数
    this.add_callback = this.tabs.hashtable[id].add_callback;
    //当关闭Tab时需要刷新的Tab的url
    this.closeRefresh = this.tabs.hashtable[id].closeRefresh;
    //当关闭当前Tab时需要激活的Tab的url
    this.closeActivate = this.tabs.hashtable[id].closeActivate;
    //该属性和CleverTabs.lockOnlyOne有关
    this.orgLock = this.tabs.hashtable[id].orgLock;
};
//使Tab页面处于未激活状态，不建议在代码中使用
CleverTab.prototype.deactivate = function () {
    var self = this;
    self.header.removeClass('ui-tabs-selected ui-state-active ui-state-show');
    self.panel.addClass('ui-tabs-hide');

    if (self.tabs.lockOnlyOne && !self.tabs.hashtable[self.id].orgLock && self.tabs.element.find('>li').size() > 0) {
        //self.setLock(false);
    }
}
//使Tab页面处于激活状态
CleverTab.prototype.activate = function (isclick) {
    var self = this;

    if ($.browser.msie) {
        self.header.find('a').trigger('blur');
    }

    if (self.disable) {
        return false;
    }

    var currentTab = self.tabs.getCurrentTab();
    if (currentTab) {
        if (currentTab.id == self.id) {
            if(isclick){
                if(  $(self.header)[0].style.display == 'none'){
                    tabs.hide_pre_pre_tab();
                    $(self.header)[0].style.display = 'block';
                }
            }

            return false;
        }

        currentTab.deactivate();
    }
    //显示自己，但是需要判断一下，如果需要隐藏还是需要隐藏最后一个不是自己的tab

    if(isclick){
        if(  $(self.header)[0].style.display == 'none'){
            tabs.hide_pre_pre_tab();
            $(self.header)[0].style.display = 'block';
        }
    }
    self.header.addClass('ui-tabs-selected ui-state-active ui-state-show');
    self.panel.removeClass('ui-tabs-hide');

    if (self.tabs.lockOnlyOne && self.tabs.element.find('New Tabli').size() == 1) {
        self.setLock(true, false);
    }
    if(isclick != 'undefined' && isclick!=null){
        if(isclick==true){
            var iframe = self.panel.find(' > iframe:first');
            if(iframe.attr("src") != iframe.attr("data_src") && iframe.attr("data_src").length>1){
                iframe.attr('src', iframe.attr("data_src"));
            }
        }
    }
    var add_callback = self.add_callback;
    add_callback();
}
//获取该Tab之前的Tab
CleverTab.prototype.prevTab = function () {
    var self = this;
    var prev = self.header.prev();
    if (prev.size() > 0) {
        var headerId = prev.attr('id');
        return new CleverTab(tabs, CleverTab.getUniqueByHeaderId(headerId));
    } else {
        return null;
    }
}
//获取该Tab之后的Tab
CleverTab.prototype.nextTab = function () {
    var self = this;
    var next = self.header.next();
    if (next.size() > 0) {
        var headerId = next.attr('id');
        return new CleverTab(tabs, CleverTab.getUniqueByHeaderId(headerId));
    } else {
        return null;
    }

}

//移移该Tab
CleverTab.prototype.kill = function () {
    var self = this;
    if (self.lock) {
        return;
    }
    var nextTab = self.nextTab();
    var prevTab=self.prevTab();

    /*=====================================================================================================修改的地方*/
    var callback = self.callback;
    var add_callback = self.add_callback;
    var refresh = self.closeRefresh;
    var activate = self.closeActivate;
    self.header.remove();
    self.panel.remove();
    delete self.tabs.hashtable[self.id];
    if (self.tabs.panelContainer.attr('id').indexOf('cleverTabsPanelContainer') === 0) {
        var height = self.tabs.wrapper.height() - self.tabs.element.height() - 30;
    }
    var refreshTab = self.tabs.getTabByUrl(refresh);
    if (refreshTab) {
        refreshTab.refresh();
    }
    var activateTab = self.tabs.getTabByUrl(activate);
    if (activateTab) {
        activateTab.activate(false);
    } else {
        if (nextTab) {
            nextTab.activate(false);
        }else{
            prevTab.activate(false);
        }
    }
    tabs.del_storage(self);
    callback();

    var check = function(){
        var sumWidth =0;
        $(".xhn_topNavUl").find("li").each(function(){
            sumWidth += $(this).width()+30;//margin和padding数值总和
        });
        //console.log((sumWidth+30)+":: "+$(".xhn_topNavUl").width());
        if(sumWidth + 30 < $(".xhn_topNavUl").width()){
            //查看是否有隐藏的tab，如果有，就显示最后一个隐藏的

            return true;
        }
        return false;
    }
    var getHideElement= function(){
        var lis = $(".xhn_topNavUl li");
        if(lis.length>3){
            for(var i=lis.length-1;i>=0;i--){
                if(lis[i].style.display =='none'){
                    return lis[i];
                }
            }
        }
        return null;
    }
    var t = getHideElement();
    if(t!=null){
        t.style.display ='block';
    }

}
if(!$(".xhn_topNavUl li.ui-tabs-selected")){
    alert("不存在选中")
}
//刷新该Tab的iframe中的内容
CleverTab.prototype.refresh = function () {
    var self = this;
    if (self.url.indexOf('clever_tabs_time_stamp=') > 0) {
        self.url = self.url.substring(0, self.url.indexOf('clever_tabs_time_stamp=') - 1);
    }
    var newUrl = self.url.concat(self.url.indexOf('?') < 0 ? '?' : '&').concat('clever_tabs_time_stamp=').concat(new Date().getTime());
    self.panel.find(' > iframe:first').attr('src', newUrl);
}
//打开页签，刷新新页签
/*CleverTab.prototype.refreshs = function (url) {
    var self = this;
    var newUrl=url;
    if (newUrl.indexOf('clever_tabs_time_stamp=') > 0) {
        newUrl = newUrl.substring(0, newUrl.indexOf('clever_tabs_time_stamp=') - 1);
    }
    var newUrl = newUrl.concat(newUrl.indexOf('?') < 0 ? '?' : '&').concat('clever_tabs_time_stamp=').concat(new Date().getTime());
    self.panel.find('*').attr('src', newUrl);
    var dataURL=self.panel.find('*').attr('src');

    self.panel.find('*').attr('data_src',dataURL);
};*/
CleverTab.prototype.refreshs = function (url,lable) {
    var self = this;
    var newUrl = url;
    if(lable == $(self.header ).find("a:first").text() && url == self.url){
        return false;
    }
    $(self.header ).find("a:first").text(lable);
    if (newUrl.indexOf('clever_tabs_time_stamp=') > 0) {
        newUrl = newUrl.substring(0, newUrl.indexOf('clever_tabs_time_stamp=') - 1);
    }
    var newUrl = newUrl.concat(newUrl.indexOf('?') < 0 ? '?' : '&').concat('clever_tabs_time_stamp=').concat(new Date().getTime());
    self.panel.find('*').attr('src', newUrl);
    var dataURL=self.panel.find('*').attr('src');
    self.panel.find('*').attr('data_src',dataURL);
};


//设置该Tab的disabled属性
CleverTab.prototype.setDisable = function (disable) {
    var self = this;
    if (disable) {
        self.header.addClass('ui-state-disabled');
        var overlay = $('<div class="ui-widget-overlay"></div>');
        self.panel.append(overlay);
        this.setLock(true);
    } else {
        self.header.removeClass('ui-state-disabled');
        var overlay = self.panel.find('div.ui-widget-overlay:first');
        overlay.remove();
        if (self.tabs.lockOnlyOne && self.tabs.element.find('>li').size() == 1) {
            return;
        }
        this.setLock();
    }
}
//设置该Tab的locked属性
CleverTab.prototype.setLock = function (lock, changeOrgLock) {
    var self = this;
    var changeOrgLock = changeOrgLock == undefined || changeOrgLock;
    if (changeOrgLock) {
        self.tabs.hashtable[self.id].orgLock = lock;
    }
    if (!lock && self.tabs.lockOnlyOne && self.tabs.element.find('>li').size() == 1) {
        return;
    }
    if (!lock) {
        var btnClose = $('<a href="javascript:void(0)" class="ui-corner-all" title="关闭" style="position: absolute; cursor: pointer; padding: 0px; top: 1px; right: 1px"><span id="ui-close"></span></a>');
        this.header.append(btnClose);
        btnClose.bind('mouseover', function () {
            $(this).addClass('ui-state-active');
        });
        btnClose.bind('mouseout', function () {
            $(this).removeClass('ui-state-active');
        });
        btnClose.bind('click', function () {
            new CleverTab(self.tabs, self.id).kill();
        });
    } else {
        var btnClose = this.header.find('span.ui-icon-close').parent('a:first');
        if (btnClose.size() > 0) {
            btnClose.remove();
        }
    }
}
//获取指定Tab头完全id的唯一id
CleverTab.getUniqueByHeaderId = function (id) {
    return id.replace('cleverTabHeaderItem-', '');
}
//获取指定Tab页面完全id的唯一id
CleverTab.getUniqueByPanelId = function (id) {
    return id.replace('cleverTabPanelItem-', '');
}
//获取指定Tab头唯一id的完全id
CleverTab.getFullHeaderId = function (uid) {
    return 'cleverTabHeaderItem-'.concat(uid);
}
//获取指定Tab页面唯一id的完全id
CleverTab.getFullPanelId = function (uid) {
    return 'cleverTabPanelItem-'.concat(uid);
}
