function Timeout(options){
	this.config={
	id:"timerBox",
	sec:3,
	message:"填写相关提示信息",
	warnmessage:"返回登录界面",
	callback:function(){
		
	}};
	$.extend(this.config,options);this.ele=null}
Timeout.prototype={
	_create:function(state){
		var that=this;
		var str="";
		var num=that.config.sec;
		str='<div class="time-overlay"><div class="timerbox animated zoomIn"><div class="timer_tit">'+this.config.message+'</div> <div class="timercont"> <div class="errortip">'+this.config.warnmessage+'</div> <div class="skip">'+this.config.warnmessage+'( <span id="sec">'+num+"</span>s )</div> </div></div></div>";
		$("body").append(str);
		var timer=setInterval(function(){
			var those=that;num--;
			$("#sec").html(num);if(num == 0){
				clearInterval(timer);
				$(".time-overlay").remove();
				if(those.config.callback()){
					those.config.callback().apply(this,[]);
					}
				}
			},1000);
		  return str;
		  }
};