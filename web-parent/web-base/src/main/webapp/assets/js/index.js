/**
 * Created by xhgx on 2017/7/31.
 */
//主题色初始化
//将默认主题色的信息存到数组里面
	$(function(){
       var themeArr = [
       {dataFile:"green",bgColor:"#1ab394"},
       {dataFile:"blue",bgColor:"#57afef"},
       {dataFile:"science",bgColor:"#073856"},
       {dataFile:"gray",bgColor:"#4c5c6e"},
       {dataFile:"cambridgeblue",bgColor:"#1181e4"}
  ];
  var str = "";
  for(var i=0;i<themeArr.length;i++){
    str+= '<li data-file="'+themeArr[i].dataFile+'"><span style="background:'+themeArr[i].bgColor+'"></span></li>';
  }
  $(".color-menu ul").append(str);

});











