
function Prompt(options) {
    this.config = {
        id: "promptBox",
        message: "填写相关提示信息",
        alertClass: {
            success: ["aTip", "信息确认"],
            warn: ["aWarn", "警告"],
            error: ["aError", "错误"]
        },
        timer: false,
        sec: "3",
        hidecancel:false,
        lang:"",
        callback: function() {},
        cancel: function() {}
    };
    $.extend(this.config, options);
    this.ele = null
}
Prompt.prototype = {
    _createDialog: function(state) {
        var that = this;
        var str = "";
        var time = "";
        var num = that.config.sec;
        var timernum = that.config.timer;
        var conditionBtn = "";
        //UDP.Public.transformLanguage("en-us");
        
        var ensureMess = UDP.Public.getmessage("prompt.btnMessage.ensureMess","确定");
        var canaleMess = UDP.Public.getmessage("prompt.btnMessage.canaleMess","取消");
        var tipmess = UDP.Public.getmessage("prompt.alertMessage."+state);
        if(this.config.hidecancel){
            conditionBtn = "none"
        }else{
            conditionBtn = "inline-block"
        }
        str = '<div class="overlay_prompt"><div id="' + this.config.id + '"class="animated zoomIn"><div class="dialogHeader">' + tipmess + '<a href="javascript:;" class="close_btn"></a></div><div class="dialogContianer ' + this.config.alertClass[state][0] + '">' + this.config.message;
        if (timernum) {
            str += '<div class="sec">( <span id="sec">' + num + "</span>s)</div>"
        }
        str += '</div><div class="dialogFooter"><input type="submit" value="'+ensureMess+'" class="btn-xs btnY" id="ensure"/><input type="submit" value="'+canaleMess+'" id="cannel" class="btn-xs btnN" style="display:'+conditionBtn+';"/></div></div></div>';
        $("body").append(str);
        $("#ensure").click(function() {
            that.delDialog();
            if (that.config.callback) {
                that.config.callback();
            }
            clearInterval(time);
        });
        $("#cannel").click(function() {
            that.delDialog();
            if (that.config.cancel) {
                that.config.cancel()
            }
            clearInterval(time);
        });
        $(document).keyup(function(event) {
            if (event.keyCode == 13) {
                $("#ensure").trigger("click")
            }
            clearInterval(time)
        });
        if (that.config.timer) {
            time = setInterval(function() {
                var those = that;
                num--;
                $("#sec").html(num);
                if (num == 0) {
                    clearInterval(time);
                    $(".overlay_prompt").remove();
                    if (those.config.callback()) {
                        those.config.callback.apply(this, [])
                    }
                }
            },
            1000);
            that.config.timer = false
        }
        $(".close_btn").click(function() {
            that.delDialog();
            clearInterval(time);
        });
        return str
    },
    delDialog: function(createOverlay) {
        $(".overlay_prompt").remove();
    }
};

function diaPrompt(options) {
    Prompt.call(this, options);
}
diaPrompt.prototype = new Prompt();
diaPrompt.prototype.tips = function(obj) {
    $.extend(this.config, obj);
    this._createDialog("success");
};
diaPrompt.prototype.warn = function(obj) {
    $.extend(this.config, obj);
    this._createDialog("warn");
};
diaPrompt.prototype.error = function(obj) {
    $.extend(this.config, obj);
    this._createDialog("error");
};
(function(win) {
    if (win["UDP"]) {
        win["UDP"].Prompt = Prompt;
    } else {
        win.UDP = {
            Prompt: Prompt
        }
    }
    var prompt = {
    		"fileType":"plug",
    		"filename":"prompt"
    }
    win["UDP"].Public.PlugList(prompt);
    
})(window);