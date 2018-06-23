<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>操作日志查询页面</title>
    
    <#include "/system/common_css.ftl">
    <#include "/system/common_js.ftl">
</head>
<body>
<div class="main-container">
    <div class="serContainer">
        <ul class="serbox">
            <li>
                <div class="inpdiv inpdiv1">
                    <input type="text" placeholder="<@i18n.messageText code='operation.daily.text.operation.user'  text='操作用户' />" class="sea_inp" id="search.createBy">
                </div>
            </li>
            <li>
                <label class="daily_time"><@i18n.messageText code='operation.daily.text.operation.time'  text='操作时间:' /></label>
                <div class="inpdiv inpdiv1" style="margin-right:10px;">
<!--                     <input type="text" class="inp1" id="search.createDate"> -->
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
                    <th class="sorting"  data-sortName="create_by" data-sortType="asc"> <@i18n.messageText code='operation.daily.text.operation.user'  text='操作用户' /> </th>
                    <th> <@i18n.messageText code='operation.daily.text.request'  text='请求URI' /> </th>
                    <th> <@i18n.messageText code='operation.daily.text.operation.mode'  text='操作方式' /> </th>
                    <th> <@i18n.messageText code='operation.daily.text.request.ip'  text='IP地址' /> </th>
                    <th class="sorting"  data-sortName="create_date" data-sortType="asc"> <@i18n.messageText code='operation.daily.text.operation.time'  text='操作时间' /> </th>
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
		var search_createBy = $("[id='search.createBy']").val();
    	var startTime = $("[name='start_time']").val();
    	var endTime = $("[name='end_time']").val();
		var qm_url = "/admin/log/findPage?orderBy= "+sortName+" "+sortType;
		var nowPageSize = $("#limitpagenum").val();
    	var qm_data = {pageSize:nowPageSize,'search.createBy':search_createBy,'search.startTime':startTime,'search.endTime':endTime};
		deplayPage(qm_url,qm_data);
	}});
    
    /*点击查询按钮*/
    $(".searchbtn ").click(function(){
    	var search_createBy = $("[id='search.createBy']").val();
    	var startTime = $("[name='start_time']").val();
    	var endTime = $("[name='end_time']").val();
        var qm_url = "/admin/log/findPage";
        var nowPageSize = $("#limitpagenum").val();
    	var qm_data = {pageSize:nowPageSize,'search.createBy':search_createBy,'search.startTime':startTime,'search.endTime':endTime};
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
        
        var qm_url = "/admin/log/findPage";
        var qm_data = {pageSize:10};
       	deplayPage(qm_url,qm_data);
    });
    
    function deplayPage(qm_url,qm_data,qm_limit){
        /*pageSize:设置每页显示的数据条数*/
        /*limit:设置页码按钮显示的个数*/
        /*pageBtnDiv：存放页码按钮的父元素名称*/
        /*pageBtnClass:存放按钮样式的类名*/
    	 var page  = new CommonPage({url:qm_url,data:qm_data,limit:10,pageBtnDiv:$("#navbox"),pageBtnClass:"pageBtnStyle4",callBack:function(data){
            var str="";
            $(".table-bordered tbody").empty();
            $(data).each(function (i,item) {
            	str+="<tr><td><input type='checkbox' name='checkItem'  data-item='"+item.id+"'/></td><td>"
                +(i+1)+"</td><td>"
                +item.createBy+"</td><td data-limit='30' title='"+item.requestUrl+"'>"
                +item.requestUrl+"</td><td>"
                +item.method+"</td><td>"
                +item.remoteAddr+"</td><td>"
                +item.createDate+"</td></tr>";
            });
            if(data == null || data == ''){
            	str +="<tr><td colspan='14'><img src='../../assets/images/load.png' class='no_data'/><@i18n.messageText code='common.text.no.record'  text='查询无记录' /></td></tr>";
            }
            $(".table-bordered tbody").append(str);
            UDP.Public.Limitcharacter();
         
   	  		$("#checkAll").attr("checked",  false);
        }});
    
    	 //初始化 check box 全选按钮
         UDP.Public.checkAllInit();
    
    	/* //全选
   	  	$("#checkAll").click(function() {
   	  		$('input[name="checkItem"]').attr("checked",this.checked); 
   	  	});
      	$(document).on('click',"input[name='checkItem']",function(){
    		var $subBox = $("input[name='checkItem']");
   	  		$("#checkAll").attr("checked",$subBox.length == $("input[name='checkItem']:checked").length ? true : false);
   	  	}); */
    }
    
  	//批量删除
   	$(".del").click(function() {
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
  					url: "/admin/log/deleteBatch",
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