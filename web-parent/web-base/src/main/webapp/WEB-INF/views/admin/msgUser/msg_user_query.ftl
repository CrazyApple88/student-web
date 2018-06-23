<!DOCTYPE html>

<html>
	<head>

		<title>tb_sys_msg_user列表信息</title>
        <#include "/system/common_css.ftl">
        <#include "/system/common_js.ftl">
	</head>
<body>

<div class="main-container">
	<!-- 搜索条件 -->
    <div class="serContainer">
        <ul class="serbox">
            <input type="hidden" name="orderBy"  orderBy="orderBy" >
			
				<li>
					<div class="inpdiv inpdiv1">
						<input type="text" class="sea_inp"  placeholder="<@i18n.messageText code='message.management.text.message.receiver'  text='消息接收者' />"  queryCriteria="search.userId">
					</div>
				</li>
			
				<li>
					<div class="inpdiv inpdiv1">
						<input type="text" class="sea_inp"  placeholder="<@i18n.messageText code='message.management.text.message.id'  text='消息id' />"  queryCriteria="search.msgId">
					</div>
				</li>
			
				<li>
					<div class="inpdiv inpdiv1">
						<input type="text" class="sea_inp"  placeholder="<@i18n.messageText code='message.management.text.message.status'  text='消息状态' />"  queryCriteria="search.status">
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
			 <@loginUserAuth key='msgUser_add' ; value>
			<#if value =='1'>
					<li class="add_common">
						<input type="button"  btnType="add" class="addBtn td_btn add_button add_bg" value="<@i18n.messageText code='common.button.append'  text='新增' />" >
					</li>
			</#if>
			</@loginUserAuth>
			 <@loginUserAuth key='msgUser_batch_del' ; value>
			<#if value =='1'>
				<li class="add_common">
					<input type="button"  btnType="batch_del"  class="del td_btn del_btn del_bg" value="<@i18n.messageText code='common.button.batch.delete'  text='批量删除' />" >
				</li>
			</#if>
			</@loginUserAuth>
			</ul>            
            <table class="table table-bordered">
                <thead class="bordered-darkorange">
                <tr class="th_title">
                	<th><input type="checkbox" id="checkAll" name="checkAll"/> <@i18n.messageText code='common.select.all.select'  text='全选' /> </th>
                    <th> <@i18n.messageText code='common.text.number'  text='序号' /> </th>
            		<th class="sorting" data-sortName="user_id" data-sortType="asc"> <@i18n.messageText code='message.management.text.message.receiver'  text='消息接收者' /> </th>
            		<th class="sorting" data-sortName="msg_id" data-sortType="asc"> <@i18n.messageText code='message.management.text.message.id'  text='消息id' /> </th>
            		<th class="sorting" data-sortName="status" data-sortType="asc"> <@i18n.messageText code='message.management.text.message.status'  text='消息状态' /> </th>
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
    $(function($) {
        var toolTip = new diaPrompt();
		//编辑按钮操作
        var saveToolTip;//打开的编辑图层
        $(document).on("click","[btnType=edit]",function(){
            var id = $(this).attr("entityId");
             saveToolTip = new UDP.Layer({
                layerclass:"layerContianer",//存放页面的容器类名
                width:450,
                height:350,
                alerttit:"<@i18n.messageText code='common.button.edit'  text='编辑' />",
                callback:function(){
                    $(".layerContianer").load("${context}/admin/msgUser/editPage?id="+id,{},function(){});
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
                        url: "${context}/admin/msgUser/delete",
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


        //批量删除删除按钮操作
        $(document).on("click","[btnType=batch_del]",function(){
            var that = this;
            var arr = [];
      		$("input[name='checkItem']:checked").each(function(i,item){
      			arr.push($(item).attr("data-item"));
      		});
            if(arr.length<1){
                toolTip.error({
                    message:"<@i18n.messageText code='message.management.text.select.data.delete'  text='请选择要删除的数据！' />",
                    callback:function(){}
                });
                return false;
            }
            toolTip.warn({
                message:"<@i18n.messageText code='process.definition.management.confirm.delete.cannot.restored'  text='确认要删除，删除后不可恢复！' />",
                callback:function(){
                    $.ajax({
                        type: "POST",
                        url: "${context}/admin/msgUser/deleteBatch",
                        data: {ids:arr.join(",")},
                        success: function(json){
                            if(json.code == 1){
                                toolTip.tips({
									timer:true,/*是否开启定时器*/
						            sec:2,/*倒计时的秒数*/
                                    message:"<@i18n.messageText code='process.definition.management.text.delete.successfully'  text='删除成功！' />",
                                    callback:function(){}
                                });
                                $('#checkAll').attr("checked",false);
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



        //编辑页面的按钮
        $(document).on("click","[btnType=cancel]",function(){
        	$(".overlay").remove();
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
            var qm_url = "${context}/admin/msgUser/findPage";
            deplayPage(qm_url,qm_data);
        });


        //定义排序功能
        var tabSort = new tableSort({sortClass:".sorting",callback:function(sortName,sortType){
            $("[orderBy=orderBy]").val(sortName+" "+sortType);
            $(".searchbtn").trigger("click");
        }});



		//新增按钮操作
        $(document).on("click","[btnType=add]",function(){
            saveToolTip = new UDP.Layer({
                layerclass:"layerContianer",//存放页面的容器类名
                width:450,
                height:350,
                alerttit:"<@i18n.messageText code='common.button.append'  text='新增' />",
                callback:function(){
                    $(".layerContianer").load("${context}/admin/msgUser/editPage",{},function(){});
                }
            });
            saveToolTip.show();
        });


        var qm_url = "${context}/admin/msgUser/findPage";
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
            <td  class="text-center">
                <%=item.userId%>
            </td>
            <td  class="text-center">
                <%=item.msgId%>
            </td>
            <td  class="text-center">
                <%=item.status%>
            </td>
          <td  class="text-center">
           <@loginUserAuth key='msgUser_edit' ; value>
          <#if value =='1'>
              <a href="javascript:void(0)" btnType="edit" class="editInfo td_btn edit_btn edit_bg" entityId ="<%=item.id%>"> <@i18n.messageText code='common.button.edit'  text='编辑' /> </a>
          </#if>
          </@loginUserAuth>
           <@loginUserAuth key='msgUser_del' ; value>
          <#if value =='1'>
                  <a href="javascript:void(0)" btnType="del" class="delInfo td_btn del_btn del_bg" entityId ="<%=item.id%>"> <@i18n.messageText code='common.button.delete'  text='删除' /> </a>
          </#if>
          </@loginUserAuth>

          </td>
      </tr>
      <%});%>



</script>

</body>
</html>
