<!DOCTYPE html>
<html>
	<head>
		<title>定时器管理列表信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="description" content="定时器管理列表信息">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
        <#include "/system/common_css.ftl">
        <#include "/system/common_js.ftl">
	</head>
<body>
<div class="main-container">
    <div class="serContainer">
        <ul class="serbox">
			<li>
				<div class="inpdiv inpdiv1">
					<input type="text" placeholder="<@i18n.messageText code='timer.management.text.name'  text='名称' />" class="sea_inp" queryCriteria="search.name">
				</div>
			</li>
			<li>
				 <div class="serbtnbox right">
		            <input type="button"   class="searchbtn sec_btn sec_btn_query" value="<@i18n.messageText code='common.button.query'  text='查询' />">
		        </div>
			</li>
			
        </ul>
    </div>
    <div class="tabContainer">
        <div class="col-lg-12 col-sm-12 col-xs-12">
           <ul class="btn_set">
	        	<li class="add_common">
		
					<input type="button" class="addBtn td_btn add_button add_bg" value="<@i18n.messageText code='timer.management.button.create.timer'  text='新增定时器' />">
					
				</li>
<!-- 	            <li><input type="submit" value="批量删除" class="td_btn del_btn del_bg"></li> -->
<!-- 	            <li><input type="submit" value="批量导入" class="td_btn into_btn into_bg"></li> -->
<!-- 	            <li><input type="submit" value="批量导出" class="td_btn outto_btn outto_bg"></li> -->
	        </ul>
            <table class="table table-bordered">
                <thead class="bordered-darkorange">
                <tr class="th_title">
                    <th> <@i18n.messageText code='common.text.number'  text='序号' /> </th>
            		<th class="sorting"  data-sortName="name" data-sortType="asc"> <@i18n.messageText code='timer.management.text.name'  text='名称' /> </th>
            		<th class="sorting"  data-sortName="jobType" data-sortType="asc"> <@i18n.messageText code='timer.management.text.type'  text='类型' /> </th>
            		<th class="sorting"  data-sortName="startTime" data-sortType="asc"> <@i18n.messageText code='log.in.daily.button.starting.time'  text='开始时间' /> </th>
            		<th class="sorting"  data-sortName="entTime" data-sortType="asc"> <@i18n.messageText code='log.in.daily.button.ending.time'  text='结束时间' /> </th>
            		<th class="sorting"  data-sortName="repeatCount" data-sortType="asc"> <@i18n.messageText code='timer.management.text.repetitive.execution.times'  text='重复执行次数' /> </th>
            		<th class="sorting"  data-sortName="repeatInterval" data-sortType="asc"> <@i18n.messageText code='timer.management.text.execution.interval'  text='执行间隔' /> </th>
            		<th class="sorting"  data-sortName="count" data-sortType="asc"> <@i18n.messageText code='timer.management.text.total.execution.times'  text='总执行次数' /> </th>
            		<th> <@i18n.messageText code='log.in.daily.status'  text='状态' /> </th>
                    <th> <@i18n.messageText code='menu.management.button.operation'  text='操作' /> </th>
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

	//关闭弹出层，并刷新列表
	function delDialog(){
		$(".overlay").remove();
		$(".searchbtn").click();
	}
	
    $(function($) {
        var toolTip = new diaPrompt();
		//编辑按钮操作
        var saveToolTip;//保存图层
        $(document).on("click","[btnType=edit]",function(){
            var id = $(this).attr("entityId");
             saveToolTip = new UDP.Layer({
                layerclass:"layerContianer",//存放页面的容器类名
                width:450,
                height:460,
                alerttit:"<@i18n.messageText code='message.management.text.message.edit'  text='信息编辑' />",
                callback:function(){
                    $(".layerContianer").load("${context}/admin/scheduler/editPage?id="+id,{},function(){});
                }
            });
            saveToolTip.show();
        });
        
		//删除按钮操作
        $(document).on("click","[btnType=del]",function(){
            var that = this;
            var id = $(that).attr("entityId");
            toolTip.warn({
                message:"<@i18n.messageText code='process.definition.management.confirm.delete.cannot.restored'  text='确认要删除，删除后不可恢复！' />",
                callback:function(){
                    $.ajax({
                        type: "POST",
                        url: "${context}/admin/scheduler/delete",
                        data: {id:id},
                        success: function(json){
                        	if(json.code == 1){
	   					    	toolTip.tips({
									timer:true,/*是否开启定时器*/
						            sec:2,/*倒计时的秒数*/
		   		    	    	    message:"<@i18n.messageText code='process.definition.management.text.delete.successfully'  text='删除成功！' />",
		   		    	    	    callback:function(){}
	   		    	        	});
	      					    $(".searchbtn ").click();
	   					    }else{
	   					    	 toolTip.error({
									 timer:true,/*是否开启定时器*/
						             sec:2,/*倒计时的秒数*/
	   		    	    	         message:"<@i18n.messageText code='system.configuration.management.text.delete.failed'  text='删除失败' />",
	      		    	    	     callback:function(){}
	   		    	        	});
	   					    }
                        }
                    });
                }
            });
        });
		
        $(document).on("click",".off",function(){
	    	var that = this;
			var id = $(that).attr("id");
			toolTip.warn({
	            message:"<@i18n.messageText code='timer.management.text.confirm.pause'  text='确认要暂停吗！' />",
	            callback:function(){
					$.ajax({
					   type: "POST",
					   url: "/admin/scheduler/updateState",
					   data: "schedulerId="+id+"&state=off",
					   success: function(json){
						   if(json.code == 1){
	   					    	toolTip.tips({
									timer:true,/*是否开启定时器*/
						            sec:2,/*倒计时的秒数*/
		   		    	    	    message:"<@i18n.messageText code='timer.management.text.pause.success'  text='暂停成功!' />",
		   		    	    	    callback:function(){}
	   		    	        	});
	      					    $(".searchbtn ").click();
	   					    }else{
	   					    	 toolTip.error({
									 timer:true,/*是否开启定时器*/
						             sec:2,/*倒计时的秒数*/
	   		    	    	         message:"<@i18n.messageText code='timer.management.text.pause.failed'  text='暂停失败' />",
	      		    	    	     callback:function(){}
	   		    	        	});
	   					    }
					   }
					});
					
	            }
	        });
			
		});
		
		$(document).on("click",".on",function(){
	    	var that = this;
			var id = $(that).attr("id");
			toolTip.warn({
	            message:"<@i18n.messageText code='timer.management.text.confirm.start'  text='确认要启动吗！' />",
	            callback:function(){
					$.ajax({
					   type: "POST",
					   url: "/admin/scheduler/updateState",
					   data: "schedulerId="+id+"&state=on",
					   success: function(json){
						   if(json.code == 1){
								toolTip.tips({
									timer:true,/*是否开启定时器*/
						            sec:2,/*倒计时的秒数*/
									message:"<@i18n.messageText code='timer.management.text.starting.success'  text='启动成功！' />",
									callback:function(){}
								});
								$(".searchbtn ").click();
							}else{
								 toolTip.error({
									 timer:true,/*是否开启定时器*/
							         sec:2,/*倒计时的秒数*/
									 message:"<@i18n.messageText code='timer.management.text.starting.failed'  text='启动失败' />",
									 callback:function(){}
								});
							}
					   }
					});
	            }
	        });
		});

        $(document).on("click","[btnType=save]",function(){
            var id = $("#entityId").val();
            if(id != ''){
                $('#form_post').attr("action", "${context}/admin/scheduler/update");
            }
            $("#form_post").ajaxForm({ success: function (json) {
            	if(json.code == 1){
            		toolTip.tips({
						timer:true,/*是否开启定时器*/
			            sec:2,/*倒计时的秒数*/
            			message:"<@i18n.messageText code='business.management.text.save.sucessfully'  text='保存成功!' />",
            			callback:function(){}
            		});
            		if(saveToolTip){
            			saveToolTip.delDialog();
            		}
            		$(".searchbtn ").click();
            	}else{
            		 toolTip.error({
							timer:true,/*是否开启定时器*/
				            sec:2,/*倒计时的秒数*/
            			 message:"<@i18n.messageText code='business.management.text.save.failed'  text='保存失败!' />",
            			 callback:function(){}
            		});
            	}
//                 if(json.code == 1){
//                     alert("保存成功");
//                     if(saveToolTip){
//                         saveToolTip.delDialog();
//                     }
//                     $(".searchbtn ").click();
//                 }else{
//                     alert("保存失败");
//                 }
            }});
            $("#form_post").submit();

        });

        //编辑页面的按钮
        $(document).on("click","[btnType=cancel]",function(){
            if(saveToolTip){
                saveToolTip.delDialog();
            }
        });
        
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
    		var qm_url = "/admin/scheduler/findPage?orderBy= "+sortName+" "+sortType;
    		deplayPage(qm_url,qm_data);
    	}});

        /*点击查询按钮*/
        $(document).on("click",".searchbtn",function(){
        	var nowPageSize = $("#limitpagenum").val();
        	var qm_data = {pageSize:nowPageSize};
			$("[queryCriteria]").each(function(i,item){
				var id  = $(item).attr("queryCriteria");
				var value  = $(item).attr("value");
                qm_data[id]=value;
			});
            var qm_url = "${context}/admin/scheduler/findPage";
            deplayPage(qm_url,qm_data);
        });

		//新增按钮操作
        $(".addBtn").click(function(){
            saveToolTip = new UDP.Layer({
                layerclass:"layerContianer",//存放页面的容器类名
                width:450,
                height:460,
                alerttit:"<@i18n.messageText code='common.button.append'  text='新增' />",
                callback:function(){
                    $(".layerContianer").load("${context}/admin/scheduler/editPage",{},function(){});
                }
            });
            saveToolTip.show();
        });

        var qm_url = "${context}/admin/scheduler/findPage";
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
            	str +="<tr><td colspan='20'><img src='../../assets/images/load.png' class='no_data'/> <@i18n.messageText code='common.text.no.record'  text='查询无记录' /> </td></tr>";
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
                <%=item.jobType%>
            </td>
            <td  class="text-center">
                <%=item.startTime%>
            </td>
            <td  class="text-center">
                <%=item.entTime%>
            </td>
			<td  class="text-center">
                <%=item.repeatCount%>
            </td>
			<td  class="text-center">
                <%=item.repeatInterval%>
            </td>
            <td  class="text-center">
                <%=item.count%>
            </td>
            <td class="text-center" width='60'>
				<%if(item.state == "on"){%>
					<a href='javascript:void(0);' class='off td_btn suspend_btn' id='<%=item.id%>'> <@i18n.messageText code='common.button.pause'  text='暂停' /> </a>
				<%}%>
				<%if(item.state == "off"){%>
					<a href='javascript:void(0);' class='on td_btn active_btn' id='<%=item.id%>'> <@i18n.messageText code='common.button.start'  text='启动' /> </a>
				<%}%>
            </td>
          <td class="text-center" width='160'>
              <a href="javascript:void(0)" class="edit td_btn edit_btn edit_bg" btnType="edit" entityId ="<%=item.id%>"> <@i18n.messageText code='common.button.edit'  text='编辑' /> </a>
              <a href="javascript:void(0)" class="del td_btn del_btn del_bg" btnType="del" entityId ="<%=item.id%>"> <@i18n.messageText code='common.button.delete'  text='删除' /> </a>
          </td>
      </tr>
      <%});%>
</script>
</body>
</html>