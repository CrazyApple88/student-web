<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>登录日志查询页面</title>
    
    <#include "/system/common_css.ftl">
    <#include "/system/common_js.ftl">
</head>
<body>

<div class="main-container">
    <div class="serContainer">
        <ul class="serbox">
            <li>
                <div class="inpdiv inpdiv1">
                    <input type="text" placeholder="<@i18n.messageText code='log.in.daily.text.login.user'  text='登录用户' />" class="sea_inp" id="search.userName">
                </div>
            </li>
            <li>
                <label class="daily_time"> <@i18n.messageText code='log.in.daily.text.login.time'  text='登陆时间:' /> </label>
                
                <div class="inpdiv inpdiv1" style="margin-right:10px;">
					<input id="d4311" name="start_time" class="Wdate sea_inp sec_car" type="text" placeholder="<@i18n.messageText code='log.in.daily.button.starting.time'  text='开始时间' />" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'d4312\')||\'2030-10-01\'}'})"/>   
                </div>
                <div class="inpdiv inpdiv1">
					<input id="d4312" name="end_time" class="Wdate sea_inp sec_car" type="text" placeholder="<@i18n.messageText code='log.in.daily.button.ending.time'  text='结束时间' />" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'d4311\')}',maxDate:'2030-10-01'})"/>  
                </div>
            </li>
            <li>
            	<div class="serbtnbox right">
		            <input type="button" class="searchbtn sec_btn sec_btn_query" value="<@i18n.messageText code='common.button.query'  text='查询' />">
		        </div>
            </li>
        </ul>
        
    </div>
    <div class="tabContainer">
        <div class="col-lg-12 col-sm-12 col-xs-12">
            <ul class="btn_set">
	            <li><input type="button" value="<@i18n.messageText code='common.button.batch.delete'  text='批量删除' />" class="del td_btn del_btn del_bg" id="del"></li>
	            <!-- <li><input type="submit" value="批量导入" class="td_btn into_btn into_bg"></li>
	            <li><input type="submit" value="批量导出" class="td_btn outto_btn outto_bg"></li> -->
	        </ul>
            <table class="table table-bordered">
                <thead class="bordered-darkorange">
                <tr class="th_title">
                	<th>
                		<input type="checkbox" id="checkAll" name="checkAll"/>
                	</th>
                    <th> <@i18n.messageText code='common.text.number'  text='序号' /> </th>
                    <th class="sorting"  data-sortName="user_name" data-sortType="asc"> <@i18n.messageText code='log.in.daily.text.login.user'  text='登录用户' /> </th>
                    <th class="sorting"  data-sortName="login_date" data-sortType="asc"> <@i18n.messageText code='log.in.daily.text.login.time'  text='登陆时间' /> </th>
                    <th> <@i18n.messageText code='log.in.daily.text.login.type'  text='登录类型' /> </th>
                    <th> <@i18n.messageText code='log.in.daily.text.login.ip'  text='登录IP' /> </th>
                    <th> <@i18n.messageText code='log.in.daily.status'  text='状态' /> </th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    <div class="pageContainer">
        <!--存放加载进来的导航按钮-->
       <div class="pagecont">
           <div id="navbox"></div>
       </div>
    </div>
</div>

<script>
	//字段排序
	var sortName = $(this).attr("data-sortName");
	var sortClass = $(this).attr("class");
	var tabSort = new tableSort({sortClass:".sorting",callback:function(sortName,sortType){
		var search_userName = $("[id='search.userName']").val();
    	var startTime = $("[name='start_time']").val();
    	var endTime = $("[name='end_time']").val();
		var qm_url = "/admin/loginLog/findPage?orderBy= "+sortName+" "+sortType;
        var nowPageSize = $("#limitpagenum").val();
		var qm_data = {pageSize:nowPageSize,'search.userName':search_userName,'search.startTime':startTime,'search.endTime':endTime};
		deplayPage(qm_url,qm_data);
	}});
    
    /*点击查询按钮*/
    $(".searchbtn ").click(function(){
    	var search_userName = $("[id='search.userName']").val();
    	var startTime = $("[name='start_time']").val();
    	var endTime = $("[name='end_time']").val();
        var qm_url = "/admin/loginLog/findPage";
        var nowPageSize = $("#limitpagenum").val();
        var qm_data = {pageSize:nowPageSize,'search.userName':search_userName,'search.startTime':startTime,'search.endTime':endTime};
       	deplayPage(qm_url,qm_data);
    });
    
    $(function(){
        function AddProjectCostList(obj) {
            var opt =  obj || {};
            CommonPage.call(this, opt);
            return this;
        }
        ;(function () {
            var subPrototype = Object(CommonPage.prototype);
            subPrototype.constructor = AddProjectCostList;
            AddProjectCostList.prototype = subPrototype;
        })();
        
        var qm_url = "/admin/loginLog/findPage";
        var qm_data = {pageSize:10};
       	deplayPage(qm_url,qm_data);
    });
    
    function getLoginType(type){
    	if(type == 1){
    		return "<@i18n.messageText code='log.in.daily.text.login.system'  text='登录系统' />";
    	}else if(type == 2){
    		return "<@i18n.messageText code='log.in.daily.text.quit.system'  text='退出系统' />";
    	}
    	return "<@i18n.messageText code='log.in.daily.text.other'  text='其他' />";
    }
    function getStatus(status){
    	if(status == 1){
    		return "<@i18n.messageText code='log.in.daily.text.success'  text='成功' />";
    	}else if(status == 2){
    		return "<@i18n.messageText code='log.in.daily.text.failure'  text='失败' />";
    	}else if(status == 3){
    		return "<@i18n.messageText code='log.in.daily.text.password.error'  text='密码错误' />";
    	}else if(status == 4){
    		return "<@i18n.messageText code='log.in.daily.text.user.unavailable'  text='用户不可用' />";
    	}
    	return "<@i18n.messageText code='log.in.daily.text.other'  text='其他' />";
    }
    function deplayPage(qm_url,qm_data,qm_limit){
        /*pageSize:设置每页显示的数据条数*/
        /*limit:设置页码按钮显示的个数*/
        /*pageBtnDiv：存放页码按钮的父元素名称*/
        /*pageBtnClass:存放按钮样式的类名*/
    	 var page  = new CommonPage({url:qm_url,data:qm_data,limit:10,pageBtnDiv:$("#navbox"),pageBtnClass:"pageBtnStyle4",callBack:function(data){
            var str="";
            $(".table-bordered tbody").empty();
            $(data).each(function (i,item) {
            	var loginType = getLoginType(item.loginType);
            	var status = getStatus(item.loginStatus);
                str+="<tr><td><input type='checkbox' name='checkItem'  data-item='"+item.id+"'/></td><td>"
                +(i+1)+"</td><td>"
                +item.userName+"</td><td>"
                +item.loginDate+"</td><td>"
                +loginType+"</td><td>"
                +item.loginIp+"</td><td>"
                +status+"</td></tr>";
            });
            if(data == null || data == ''){
            	str +="<tr><td colspan='14'><img src='../../assets/images/load.png' class='no_data'/><@i18n.messageText code='common.text.no.record'  text='查询无记录' /></td></tr>";
            }
            $(".table-bordered tbody").append(str);
            
            $("#checkAll").attr("checked",  false);
        }});
    
    	//初始化 check box 全选按钮
        UDP.Public.checkAllInit();
    
    	//全选
   	  	/* $("#checkAll").click(function() {
   	  		$('input[name="checkItem"]').attr("checked",this.checked); 
   	  	});
   	  	var $subBox = $("input[name='checkItem']");
   	  	$subBox.click(function(){
   	  		$("#checkAll").attr("checked",$subBox.length == $("input[name='checkItem']:checked").length ? true : false);
   	  	}); */
    
    }
    
   	//批量删除
   	$(".del").click(function(){
  		var toolTip = new diaPrompt();
  		var arr = [];
  		$("input[name='checkItem']:checked").each(function(i,item){
  			arr.push($(item).attr("data-item"));
  		});
  		if(arr.length == 0 || arr.length == null){
  			toolTip.error({
	            message:"<@i18n.messageText code='log.in.daily.text.select.log.data.delete'  text='请选择要删除的日志数据！' />",
   	         	callback:function(){}
    		});
  			return;
  		}
  		toolTip.warn({
  	    	message:"<@i18n.messageText code='process.definition.management.confirm.delete.cannot.restored'  text='确认要删除，删除后不可恢复！' />",
  	    	callback:function(){
  				$.ajax({
  					type: "POST",
  					url: "/admin/loginLog/deleteBatch",
  					data: "ids="+arr,
  					
  					success: function(json){
  						if(json.code == 1){
				    	 	toolTip.tips({
								timer:true,/*是否开启定时器*/
					            sec:2,/*倒计时的秒数*/
	    	    	            message:"<@i18n.messageText code='process.definition.management.text.delete.successfully'  text='删除成功！' />",
	    	    	            callback:function(){
	    	    	            }
	    	        		});
				    	 	$('#checkAll').attr("checked",false);
	    	            	$(".searchbtn ").click();
   					     }else{
   					    	toolTip.error({
								timer:true,/*是否开启定时器*/
					            sec:2,/*倒计时的秒数*/
	    	    	            message:"<@i18n.messageText code='system.configuration.management.text.delete.failed'  text='删除失败' />",
	    	    	         	callback:function(){
	    	    	         	}
	    	        		});
   					     }
  					}
  				});
  	    	}
  		});
  	});
    
</script>
</body>
</html>