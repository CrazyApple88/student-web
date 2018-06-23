<!DOCTYPE html>
<html>
	<head>
		<title>系统配置列表信息</title>
        <#include "/system/common_css.ftl">
        <#include "/system/common_js.ftl">
	</head>
<body>
<div class="main-container">
    <div class="serContainer">
        <ul class="serbox">
				<li>
					<div class="inpdiv inpdiv1">
						<input type="text" placeholder="<@i18n.messageText code='system.configuration.management.text.configuration.name'  text='配置名称' />" class="sea_inp" queryCriteria="search.name">
					</div>
				</li>
				<li>
					<div class="inpdiv inpdiv1">
						<input type="text" placeholder="<@i18n.messageText code='system.configuration.management.text.property.key'  text='属性key' />" class="sea_inp" queryCriteria="search.code">
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
					<input type="button" id ="addBtn"  class="td_btn add_button add_bg"  value="<@i18n.messageText code='system.configuration.management.button.create.configuration'  text='新增配置' />">
				</li>
<!-- 	            <li><input type="submit" value="批量删除" class="td_btn del_btn del_bg"></li> -->
<!-- 	            <li><input type="submit" value="批量导入" class="td_btn into_btn into_bg"></li> -->
<!-- 	            <li><input type="submit" value="批量导出" class="td_btn outto_btn outto_bg"></li> -->
	        </ul>
            <table class="table table-bordered">
                <thead class="bordered-darkorange">
                <tr class="th_title">
                    <th> <@i18n.messageText code='common.text.number'  text='序号' /> </th>
		            <th class="text-center">
		               	 <@i18n.messageText code='system.configuration.management.text.configuration.name'  text='配置名称' />
		            </th>
		            <th class="text-center">
		               	 <@i18n.messageText code='system.configuration.management.text.property.key'  text='属性key' />
		            </th>
		            <th class="text-center">
		                 <@i18n.messageText code='system.configuration.management.text.property.value'  text='属性值' />
		            </th>
		            <th class="text-center">
		                 <@i18n.messageText code='dictionary.management.text.dictionary.note.description'  text='备注信息' />
		            </th>
		            <th class="text-center">
		                 <@i18n.messageText code='message.management.text.create.time'  text='创建时间' />
		            </th>
                    <th width="150"><@i18n.messageText code='menu.management.button.operation'  text='操作' /></th>
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
                height:350,
                alerttit:"<@i18n.messageText code='common.button.edit'  text='编辑' />",
                callback:function(){
                    $(".layerContianer").load("${context}/admin/config/editPage?id="+id,{},function(){});
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
                        url: "${context}/admin/config/delete",
                        data: {id:id},
                        success: function(json){
                        	if(json.code == 1){
                        		toolTip.tips({
									timer:true,/*是否开启定时器*/
						            sec:2,/*倒计时的秒数*/
                        			message:"<@i18n.messageText code='process.definition.management.text.delete.successfully'  text='删除成功！' />",
                        			callback:function(){}
                        		});
                        	}else{
                        		toolTip.error({
									timer:true,/*是否开启定时器*/
						            sec:2,/*倒计时的秒数*/
                        			message:"<@i18n.messageText code='system.configuration.management.text.delete.failed'  text='删除失败' />",
                        			callback:function(){}
                        		});
                        	}
                        	$(".searchbtn ").click();
                        }
                    });

                }
            });
        });

        //编辑页面的按钮
        $(document).on("click","[btnType=cancel]",function(){
            if(saveToolTip){
                saveToolTip.delDialog();
            }
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
            var qm_url = "${context}/admin/config/findPage";
            deplayPage(qm_url,qm_data);
        });

		//新增按钮操作
        $("#addBtn").click(function(){
            saveToolTip = new UDP.Layer({
                layerclass:"layerContianer",//存放页面的容器类名
                width:450,
                height:350,
                alerttit:"<@i18n.messageText code='system.configuration.management.button.create.configuration'  text='新增配置' />",
                callback:function(){
                    $(".layerContianer").load("${context}/admin/config/editPage",{},function(){});
                }
            });
            saveToolTip.show();
        });

        var qm_url = "${context}/admin/config/findPage";
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
            UDP.Public.Limitcharacter();
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
            <td  class="text-center" data-limit="30" title="<%=item.name%>">
                <%=item.name%>
            </td>
            <td  class="text-center" data-limit="30" title="<%=item.code%>">
                <%=item.code%>
            </td>
            <td  class="text-center" data-limit="30" title="<%=item.value%>">
                <%=item.value%>
            </td>
            <td  class="text-center" data-limit="30" title="<%=item.intro%>">
                <%=item.intro%>
            </td>
            <td  class="text-center">
                <%=item.createDate%>
            </td>
          <td  class="text-center" width='140'>
              <a href="javascript:void(0)" class='editInfo td_btn edit_btn edit_bg' btnType="edit" entityId ="<%=item.id%>"> <@i18n.messageText code='common.button.edit'  text='编辑' /> </a>
              <a href="javascript:void(0)" class='delInfo td_btn del_btn del_bg' btnType="del" entityId ="<%=item.id%>"> <@i18n.messageText code='common.button.delete'  text='删除' /> </a>
          </td>
      </tr>
      <%});%>
</script>
</body>
</html>