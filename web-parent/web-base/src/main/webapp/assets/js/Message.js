/**
 * Created by Administrator on 2017/12/13 0013.
 */

// 用法：首先new 一个Message实例
 // index页面调用
///var websocket = new Message({
//    socketSite: "127.0.0.1:8080/websocket", // 配置地址
//});

//window.top["websocket"]=websocket;
//  获取数据的方法

//使用的页面调用的方法
//var websocket=window.top["websocket"];
//websocket.addEvent("E01_002",function(data){
//    console.log(data);
//});

function Message(options) {
    this.config = {
        sendData: null, //前台要传的参数配置
        socketSite: "", // 配置地址
        socketUrl: "", //服务端 socket 连接地址
        socket: null, //websocket
        timerid: null,
        events:[]
    };
    $.extend(this.config, options);
    this.init();
    return this;
}
Message.prototype = {
    init: function() {
        var that = this;
        var socket = that.config.socket;
        that.initUrl();
        if ('WebSocket' in window) {
            socket = new WebSocket(that.config.socketUrl);
        } else if ('MozWebSocket' in window) {
            socket = new MozWebSocket(that.config.socketUrl);
        } else {
            socket = new SockJS(that.config.socketUrl);
        }
        //连接成功
        socket.onopen = function() {
            if (that.config.sendData != null) {
                socket.send(that.config.sendData)
                console.log("信息发送中");
            }
            console.log("连接成功")
        };
        //监听推送的消息
        socket.onmessage = function(message) {
            var data = message.data;
            var data=JSON.parse(data);
			var event=data.event;
            if (!data) { console.log("数据不存在"); return };
            that.triggerEvent(event,data);
        };
        //监听关闭
        socket.onclose = function() {
            console.log("连接关闭");
            var timerid = that.config.timerid;
            if (timerid != null) {
                window.clearInterval(timerid);
            }
            that.againLink();
        };
        //监听失败
        socket.onerror = function(evt) {
            console.log("连接错误...");
        };
    },
    //初始化连接地址
    initUrl: function() {
        var that = this;
        var socketToUrl = that.config.socketSite;
        if (window.location.protocol == 'http:') {
            socketToUrl = 'ws://' + socketToUrl;
        } else {
            socketToUrl = 'wss://' + socketToUrl;
        }
        that.config.socketUrl = socketToUrl;
    },
    //  重新连接服务器
    againLink: function() {
        var that = this;
        var timer = that.config.timerid = window.setInterval(function() {
            console.log("正在重新连接...");
            if (that.config.socket == null) {
                that.init();
                return;
            }
            var readyState = that.config.socket.readyState;
            if (readyState == 1) {
                window.clearInterval(timer);
                console.log("重新连接成功");
            }
        }, 2000);
    },

    addEvent:function(eventId,fun){
        this.config.events.push({eventId:eventId,callBack:fun});
    },

    triggerEvent:function(eventId,data){
        for(var i in this.config.events){
           var e =   this.config.events[i];
            if(e.eventId === eventId){
                e.callBack.apply(this,[data]);
            }
        }
    }
};
(function(win) {
    if (win["UDP"]) {
        win["UDP"].Message = Message;
    } else {
        win.UDP = { Message: Message };
    }
})(window);

