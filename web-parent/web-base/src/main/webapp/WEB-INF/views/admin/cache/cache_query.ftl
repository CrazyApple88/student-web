<!DOCTYPE html>
<html>
	<head>
		<title>内存信息管理列表</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="description" content="内存信息管理列表">
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
						<input type="text" placeholder="<@i18n.messageText code='memory.information.management.text.name'  text='名称' />" class="sea_inp" queryCriteria="search.name">
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
		
					<input type="button" class="reLoadBatch td_btn add_button add_bg" value="<@i18n.messageText code='memory.information.management.button.bulk.reloading'  text='批量重新加载' />">
					
				</li>
<!-- 	            <li><input type="submit" value="批量删除" class="td_btn del_btn del_bg"></li> -->
<!-- 	            <li><input type="submit" value="批量导入" class="td_btn into_btn into_bg"></li> -->
<!-- 	            <li><input type="submit" value="批量导出" class="td_btn outto_btn outto_bg"></li> -->
	        </ul>
            <table class="table table-bordered">
                <thead class="bordered-darkorange">
                <tr class="th_title">
                	<th>
                		<input type="checkbox" id="checkAll"/><@i18n.messageText code='common.select.all.select'  text='全选' />
                	</th>
                    <th> <@i18n.messageText code='common.text.number'  text='序号' /> </th>
            		<th> <@i18n.messageText code='timer.management.text.name'  text='名称' /> </th>
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
		//查看按钮操作
        var saveToolTip;//保存图层
        
      	//删除按钮操作
        $(document).on("click","[btnType=reLoad]",function(){
            var that = this;
            var id = $(that).attr("id");
            toolTip.warn({
                message:"<@i18n.messageText code='memory.information.management.text.confirm.load'  text='确认要重新加载吗??' />",
                callback:function(){
                    $.ajax({
                        type: "POST",
                        url: "${context}/admin/cache/reLoad",
                        data: {id:id},
                        async:false,//必须ajax执行完才返回值
                        success: function(json){
                            if(json.code == 1){
                            	toolTip.tips({
									timer:true,/*是否开启定时器*/
						            sec:2,/*倒计时的秒数*/
		    	    	            message:"<@i18n.messageText code='memory.information.management.text.reload.successful'  text='重新加载成功！' />",
   		    	    	         	callback:function(){}
		    	        		});
                            }else{
                            	toolTip.error({
									timer:true,/*是否开启定时器*/
						            sec:2,/*倒计时的秒数*/
		    	    	            message:"<@i18n.messageText code='memory.information.management.text.reload.failed'  text='重新加载失败！' />",
   		    	    	         	callback:function(){}
		    	        		});
                            }
                            $(".searchbtn ").click();
                        }
                    });

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
            var qm_url = "${context}/admin/cache/findPage";
            deplayPage(qm_url,qm_data);
        });

        var qm_url = "${context}/admin/cache/findPage";
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
            	str +="<tr><td colspan='14'><img src='../../assets/images/load.png' class='no_data'/> <@i18n.messageText code='common.text.no.record'  text='查询无记录' /> </td></tr>";
            }
            $(".table-bordered tbody").append(str);
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

   	//批量重新加载
   	$(".reLoadBatch").click(function(){
  		var toolTip = new diaPrompt();
  		var arr = [];
  		$("input[name='checkItem']:checked").each(function(i,item){
  			arr.push($(item).attr("data-item"));
  		});
  		if(arr.length == 0 || arr.length == null){
  			toolTip.error({
				timer:true,/*是否开启定时器*/
	            sec:2,/*倒计时的秒数*/
	            message:"<@i18n.messageText code='memory.information.management.select.data.reloaded'  text='请选择要重新加载的数据！' />",
   	         	callback:function(){}
    		});
  			return;
  		}
  		toolTip.warn({
  	    	message:"<@i18n.messageText code='memory.information.management.text.confirm.load'  text='确认要重新加载吗??' />",
  	    	callback:function(){
  				$.ajax({
  					type: "POST",
  					url: "/admin/cache/reLoadBatch",
  					data: "ids="+arr,
  					async:false,//必须ajax执行完才返回值
                    success: function(json){
                        if(json.code == 1){
                        	toolTip.tips({
								timer:true,/*是否开启定时器*/
					            sec:2,/*倒计时的秒数*/
	    	    	            message:"<@i18n.messageText code='memory.information.management.text.batch.reload.sucessfully'  text='批量重新加载成功！' />",
		    	    	        callback:function(){}
	    	        		});
                        }else{
                        	toolTip.error({
								timer:true,/*是否开启定时器*/
					            sec:2,/*倒计时的秒数*/
	    	    	            message:"<@i18n.messageText code='memory.information.management.text.batch.reload.failed'  text='批量重新加载失败！' />",
		    	    	        callback:function(){}
	    	        		});
                        }
                        $('#checkAll').attr("checked",false);
                        $(".searchbtn ").click();
                    }
  				});
  	    	}
  		});
  	});
</script>

<!-- table内容模板--->
<script type="text/template" id="listTemplate">
      <%$(data).each(function(i,item){%>
         <tr>
			<td class="text-center">
				<input type='checkbox' name='checkItem' data-item='"+item+"'/>
			</td>
			<td class="text-center">
                <%=i+1%>
			</td>
            <td class="text-center">
                <%=item%>
            </td>
          	<td class="text-center">
              	<a href="javascript:void(0)" class="reLoad td_btn edit_btn edit_bg" btnType="reLoad" id ="<%=item%>"> <@i18n.messageText code='common.button.reload'  text='重新加载' /> </a>
          	</td>
      </tr>
      <%});%>
</script>
</body>
</html>