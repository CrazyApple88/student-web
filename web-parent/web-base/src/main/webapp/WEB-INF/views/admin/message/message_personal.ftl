<!DOCTYPE html>
<#import "spring.ftl" as i18n/>
<html>
	<head>
		<title>我的消息</title>
        <meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="description" content="我的消息">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
        <#include "/system/common_css.ftl">
        <#include "/system/common_js.ftl">
        <style type="text/css">
		.details_box li, .details_both li{
			height:auto;
			overflow:hidden;
		}
	</style>
	</head>
<body>

<div class="main-container">
	<!-- 搜索条件 -->
    <div class="serContainer">
        <ul class="serbox">
            <input type="hidden" name="orderBy"  orderBy="orderBy" >
				<li>
					<div class="inpdiv inpdiv1">
						<input type="text" class="sea_inp"  placeholder="<@i18n.messageText code='message.management.text.message.header'  text='消息标题' />"  queryCriteria="search.title">
					</div>
				</li>
				<li>
                <label class="user_state"> <@i18n.messageText code='message.management.text.message.status'  text='消息状态:' /> </label>
                <div class="inpdiv inpdiv1">
                    <select  class="inp1 select" id="search.status" name="search.status" queryCriteria="search.status">
						<option value="" > <@i18n.messageText code='common.select.all'  text='全部' /> </option>
						<option value="0"> <@i18n.messageText code='common.select.unread'  text='未读' /> </option>
						<option value="1"> <@i18n.messageText code='common.select.read'  text='已读' /> </option>
					</select>
                </div>
            </li>
				<li>
                <label class="daily_time"> <@i18n.messageText code='message.management.text.create.time'  text='创建时间' /> </label>
                
                <div class="inpdiv inpdiv1" style="margin-right:10px;">
					<input id="d4311" name="start_time" class="Wdate" queryCriteria="search.startTime" type="text" placeholder="<@i18n.messageText code='log.in.daily.button.starting.time'  text='开始时间' />" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'d4312\')||\'2030-10-01\'}'})"/>   
                </div>
                <div class="inpdiv inpdiv1">
					<input id="d4312" name="end_time" class="Wdate" queryCriteria="search.endTime" type="text" placeholder="<@i18n.messageText code='log.in.daily.button.ending.time'  text='结束时间' />" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'d4311\')}',maxDate:'2030-10-01'})"/>  
                </div>
            </li>
			
            <li>
            	<div class="serbtnbox right" style="width:80px;height:35px;">
		            <input type="button" class="searchbtn sec_btn sec_btn_query" value="<@i18n.messageText code='common.button.query'  text='查询' />">
		        </div>
            </li>
        </ul>
    </div>
	<!-- 列表数据 -->
    <div class="tabContainer">
        <div class="col-lg-12 col-sm-12 col-xs-12">            
			<ul class="btn_set">
			<li class="add_common">
				<input id="read" type="button"  btnType="batch_mark"  class="userDeptAssignment td_btn branch_btn branch_bg" value="<@i18n.messageText code='common.button.batch.read'  text='批量标为已读' />" >
			</li>
			</ul>            
            <table class="table table-bordered">
                <thead class="bordered-darkorange">
                <tr class="th_title">
                	<th><input type="checkbox" id="checkAll" name="checkAll"/> <@i18n.messageText code='common.select.all.select'  text='全选' /> </th>
                    <th> <@i18n.messageText code='common.text.number'  text='序号' /> </th>
            		<th class="sorting" data-sortName="title" data-sortType="asc"> <@i18n.messageText code='message.management.text.message.header'  text='消息标题' /> </th>
            		<th> <@i18n.messageText code='message.management.text.message.type'  text='消息类型' /> </th>
            		<th> <@i18n.messageText code='message.management.text.message.status'  text='消息状态' /> </th>
            		<th class="sorting" data-sortName="create_by" data-sortType="asc"> <@i18n.messageText code='message.management.text.message.sender'  text='消息发送者' /> </th>
            		<th class="sorting" data-sortName="create_time" data-sortType="asc"> <@i18n.messageText code='message.management.text.create.time'  text='创建时间' /> </th>
                    <th> <@i18n.messageText code='menu.management.button.operation'  text='操作' /> </th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
    <!-- 分页 -->
    <div class="pageContainer">
        <!--存放加载进来的导航按钮-->
        <div class="pagecont">
            <div id="navbox"></div>
        </div>
    </div>
</div>

<!-- js代码 -->
<script type="text/javascript">
	function delDialog(){
		$(".overlay").remove();
		$(".searchbtn").click();
	}
	function delDialogSearch(){
		$(".searchbtn").click();
	}
    $(function($) {
        var toolTip = new diaPrompt();
        //查看按钮操作
        $(document).on("click","[btnType=see]",function(){
            var id = $(this).attr("entityId");
             saveToolTip = new UDP.Layer({
                layerclass:"layerContianer",//存放页面的容器类名
                width:450,
                height:450,
                alerttit:"<@i18n.messageText code='message.management.text.message.information'  text='消息信息' />",
                callback:function(){
                    $(".layerContianer").load("${context}/admin/message/seePage?id="+id+"&type=1",{},function(){});
                }
            });
            saveToolTip.show();
        });

        //批量标为已读按钮操作
        $(document).on("click","[btnType=batch_mark]",function(){
            var that = this;
            var arr = [];
      		$("input[name='checkItem']:checked").each(function(i,item){
      			arr.push($(item).attr("data-item"));
      		});
            if(arr.length<1){
                toolTip.error({
                    message:"<@i18n.messageText code='message.management.text.select.data.marked.read'  text='请选择要标为已读的数据！' />",
                    callback:function(){}
                });
                return false;
            }
           	$.ajax({
                type: "POST",
                url: "${context}/admin/msgUser/modifyMsgStatus",
                        data: {ids:arr.join(",")},
                        success: function(json){
                            if(json.code == 1){
                            	top.refreshMsgNum();
                                toolTip.tips({
									timer:true,/*是否开启定时器*/
						            sec:2,/*倒计时的秒数*/
                                    message:"<@i18n.messageText code='message.management.text.marked.reading.success'  text='标为已读成功!' />",
                                    callback:function(){}
                                });
                                $('#checkAll').attr("checked",false);
                                $(".searchbtn ").click();
                            }else{
                                toolTip.error({
									timer:true,/*是否开启定时器*/
						            sec:2,/*倒计时的秒数*/
                                    message:"<@i18n.messageText code='message.management.text.marked.failed.read'  text='标为已读失败!' />",
                                    callback:function(){}
                                });

                            }
                        }
                    });
        });


        /*点击查询按钮*/
        $(document).on("click",".searchbtn",function(){
        	var nowPageSize = $("#limitpagenum").val();
        	var qm_data = {pageSize:nowPageSize};
			$("[queryCriteria]").each(function(i,item){
				var id  = $(item).attr("queryCriteria");
				var value  = $(item).attr("value");
                qm_data[id]=value;
			});
            if($("[orderBy=orderBy]").length>0){
                var value  =$("[orderBy=orderBy]").val();
                if(value){
                    qm_data["orderBy"]=value;
                }
            }
            var qm_url = "${context}/admin/message/getMsgByUserId";
            deplayPage(qm_url,qm_data);
        });


        //定义排序功能
        var tabSort = new tableSort({sortClass:".sorting",callback:function(sortName,sortType){
            $("[orderBy=orderBy]").val(sortName+" "+sortType);
            $(".searchbtn").trigger("click");
        }});
        var qm_url = "${context}/admin/message/getMsgByUserId";
        var qm_data = {pageSize:10};
        deplayPage(qm_url,qm_data);
        //初始化 check box 全选按钮
        UDP.Public.checkAllInit();
    });






    function deplayPage(qm_url,qm_data,qm_limit){
        /*pageSize:设置每页显示的数据条数*/
        /*limit:设置页码按钮显示的个数*/
        /*pageBtnDiv：存放页码按钮的父元素名称*/
        /*pageBtnClass:存放按钮样式的类名*/
        var page  = new CommonPage({url:qm_url,data:qm_data,limit:10,pageBtnDiv:$("#navbox"),pageBtnClass:"pageBtnStyle4",callBack:function(data){

            $(".table-bordered tbody").empty();
            var str=UDP.Public.template($("#listTemplate").html(),{variable: "data"})(data);
            if(data == null || data == ''){
            	str +="<tr><td colspan='14'><img src='../../assets/images/load.png' class='no_data'/> <@i18n.messageText code='common.text.no.record'  text='查询无记录' /> </td></tr>";
            }
            $(".table-bordered tbody").append(str);
            //去掉全选按钮
            if( $(".table-bordered input[name=checkAll]").length>0){
                $(".table-bordered input[name=checkAll]").attr("checked",false) ;
            }
            UDP.Public.Limitcharacter();
        }});
    	
      //全选
   	  	$("#checkAll").click(function() {
   	  		$('input[name="checkItem"]').attr("checked",this.checked); 
   	  	});
   	  	var $subBox = $("input[name='checkItem']");
   	  	$subBox.click(function(){
   	  		$("#checkAll").attr("checked",$subBox.length == $("input[name='checkItem']:checked").length ? true : false);
   	  	});
    }


</script>

<!-- table内容模板--->
<script type="text/template" id="listTemplate">
      <%$(data).each(function(i,item){%>
         <tr>
		  <td  class="text-center">
                <input type="checkbox" name="checkItem"  data-item="<%=item.id%>"/>
          </td>
          <td  class="text-center">
                <%=i+1%>
          </td>
            <td  class="text-center" data-limit="30" title="<%=item.title%>">
                <%=item.title%>
            </td>
            <td  class="text-center">
            	<%if(item.type == "0"){%>
            		<@i18n.messageText code='message.management.text.private.message'  text='私信' />
            	<%}%>
            	<%if(item.type == "1"){%>
            		<@i18n.messageText code='message.management.text.system.message'  text='系统消息' />
            	<%}%>
            </td>
			<td  class="text-center">
            	<%if(item.status == "0"){%>
            		<font color="red"> <@i18n.messageText code='common.select.unread'  text='未读' /> </font>
            	<%}%>
            	<%if(item.status == "1"){%>
            		<@i18n.messageText code='common.select.read'  text='已读' />
            	<%}%>
            </td>
            <td  class="text-center">
                <%=item.createBy%>
            </td>
            <td  class="text-center">
                <%=item.createTime%>
            </td>
          <td  class="text-center">
             <a href="javascript:void(0)" class="see td_btn edit_btn see_bg" btnType="see" entityId ="<%=item.id%>"> <@i18n.messageText code='common.button.view'  text='查看' /> </a>
          </td>
      </tr>
      <%});%>



</script>

</body>
</html>
