<!DOCTYPE html>

<html>
	<head>

		<title>定时器历史信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="description" content="定时器历史信息">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
        <#include "/system/common_css.ftl">
        <#include "/system/common_js.ftl">
		<script type="text/javascript">
			$(function() {

				
				
			
			});		
			
		</script>

	</head>
<body>

<div class="main-container">
    <div class="serContainer">
        <ul class="serbox">
			<li>
				<div class="inpdiv inpdiv1">
					<input type="text" placeholder="<@i18n.messageText code='timer.history.text.timer.name'  text='定时器名称' />" class="sea_inp" queryCriteria="search.name">
				</div>
			</li>
			<li>
				<div class="serbtnbox right" style="width:80px;height:35px;">
			    	<input type="button"   class="searchbtn sec_btn sec_btn_query" value="<@i18n.messageText code='common.button.query'  text='查询' />">
			    </div>
			</li>
        </ul>
    </div>
    <div class="tabContainer">
        <div class="col-lg-12 col-sm-12 col-xs-12">
<!--         	<ul class="btn_set"> -->
<!-- 	            <li><input type="submit" value="批量删除" class="td_btn del_btn del_bg"></li> -->
<!-- 	            <li><input type="submit" value="批量导入" class="td_btn into_btn into_bg"></li> -->
<!-- 	            <li><input type="submit" value="批量导出" class="td_btn outto_btn outto_bg"></li> -->
<!-- 	        </ul> -->
            <table class="table table-bordered">
                <thead class="bordered-darkorange">
                <tr class="th_title">
                    <th> <@i18n.messageText code='common.text.number'  text='序号' /> </th>
		            <th class="sorting"  data-sortName="name" data-sortType="asc"> <@i18n.messageText code='timer.history.text.timer.name'  text='定时器名称' /> </th>
		            <th class="sorting"  data-sortName="group_Name" data-sortType="asc"> <@i18n.messageText code='timer.history.text.group.name'  text='分组名称' /> </th>
		            <th class="sorting"  data-sortName="start_Time" data-sortType="asc"> <@i18n.messageText code='log.in.daily.button.starting.time'  text='开始时间' /> </th>
		            <th class="sorting"  data-sortName="end_Time" data-sortType="asc"> <@i18n.messageText code='log.in.daily.button.ending.time'  text='结束时间' /> </th>
		            <th class="sorting"  data-sortName="total_Time" data-sortType="asc"> <@i18n.messageText code='timer.history.text.perform.timeconsuming'  text='执行耗时' /> </th>
		            <th class="sorting"  data-sortName="count" data-sortType="asc"> <@i18n.messageText code='timer.management.text.total.execution.times'  text='执行次数' /> </th>
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

<script type="text/javascript">

	//字段排序
	var sortName = $(this).attr("data-sortName");
	var sortClass = $(this).attr("class");
	var tabSort = new tableSort({sortClass:".sorting",callback:function(sortName,sortType){
		var nowPageSize = $("#limitpagenum").val();
    	var qm_data = {pageSize:nowPageSize};
		$("[queryCriteria]").each(function(i,item){
			var id  = $(item).attr("queryCriteria");
			var value  = $(item).attr("value");
            qm_data[id]=value;
		});
		var qm_url = "/admin/schedulerHistory/findPage?orderBy= "+sortName+" "+sortType;
		deplayPage(qm_url,qm_data);
	}});

	function delDialog(){
		$(".overlay").remove();
		$(".searchbtn").click();
	}
    $(function($) {
        var toolTip = new diaPrompt();
		//编辑按钮操作
        var saveToolTip;//保存图层


        /*点击查询按钮*/
        $(document).on("click",".searchbtn",function(){
        	var nowPageSize = $("#limitpagenum").val();
        	var qm_data = {pageSize:nowPageSize};
			$("[queryCriteria]").each(function(i,item){
				var id  = $(item).attr("queryCriteria");
				var value  = $(item).attr("value");
                qm_data[id]=value;
			});
            var qm_url = "${context}/admin/schedulerHistory/findPage";
            deplayPage(qm_url,qm_data);
        });

        var qm_url = "${context}/admin/schedulerHistory/findPage";
        var qm_data = {pageSize:10};
        deplayPage(qm_url,qm_data);
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
            	str +="<tr><td colspan='16'><img src='../../assets/images/load.png' class='no_data'/> <@i18n.messageText code='common.text.no.record'  text='查询无记录' /> </td></tr>";
            }
            $(".table-bordered tbody").append(str);
        }});
    }


</script>

<!-- table内容模板--->
<script type="text/template" id="listTemplate">
      <%$(data).each(function(i,item){%>
         <tr>
          <td  class="text-center">
                <%=i+1%>
          </td>
            <td  class="text-center">
                <%=item.name%>
            </td>
            <td  class="text-center">
                <%=item.groupName%>
            </td>
            <td  class="text-center">
                <%=item.startTime%>
            </td>
            <td  class="text-center">
                <%=item.endTime%>
            </td>
          	<td  class="text-center">
                <%=item.totalTime%>
            </td>
			<td  class="text-center">
                <%=item.count%>
            </td>
      </tr>
      <%});%>
</script>
</body>
</html>