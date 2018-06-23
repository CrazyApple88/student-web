<!DOCTYPE html>

<html>
	<head>

		<title>tb_sys_manager列表信息</title>
        <#include "/system/common_css.ftl">
        <#include "/system/common_js.ftl">
        <link rel="stylesheet" href="${context}/assets/css/plug.css?v=${cssVersion}"/>
        <link rel="stylesheet" href="${context}/assets/css/bootstrap.min.css?v=${cssVersion}"/>
        <link rel="stylesheet" href="${context}/assets/css/change/change.css?v=${cssVersion}"/>
	</head>
<body>

<div class="main-container">
	<!-- 搜索条件 -->
    <div class="serContainer">
        <ul class="serbox">
            <input type="hidden" name="orderBy"  orderBy="orderBy" >
			
				<li>
					<div class="inpdiv inpdiv1">
						<input type="text" class="sea_inp"  placeholder="系统名称"  queryCriteria="search.sysName">
					</div>
				</li>
			
				<li>
					<div class="inpdiv inpdiv1">
						<input type="text" class="sea_inp"  placeholder="版权"  queryCriteria="search.copyright">
					</div>
				</li>
			
				<li>
					<div class="inpdiv inpdiv1">
						<input type="text" class="sea_inp"  placeholder="备案许可证"  queryCriteria="search.recordLicense">
					</div>
				</li>
				<li>
					<div class="inpdiv inpdiv1">
						<input type="text" class="Wdate"  placeholder="创建时间开始时间"  queryCriteria="search.createDtStart"  onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
					</div>
				</li>
				<li>
					<div class="inpdiv inpdiv1">
						<input type="text" class="Wdate"  placeholder="创建时间结束时间"  queryCriteria="search.createDtEnd"  onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
					</div>
				</li>
			
            <li>
            	<div class="serbtnbox right" style="width:80px;height:35px;">
		            <input type="button" class="searchbtn sec_btn sec_btn_query" value="查询">
		        </div>
            </li>
        </ul>
    </div>
	<!-- 列表数据 -->
    <div class="tabContainer">
        <div class="col-lg-12 col-sm-12 col-xs-12">            
			<ul class="btn_set">
			 <@loginUserAuth key='manager_add' ; value>
			<#if value =='1'>
					<li class="add_common">
						<input type="button"  btnType="add" class="addBtn td_btn add_button add_bg" value="新增" >
					</li>
			</#if>
			</@loginUserAuth>
			 <@loginUserAuth key='manager_batch_del' ; value>
			<#if value =='1'>
				<li class="add_common">
					<input type="button"  btnType="batch_del"  class="del td_btn del_btn del_bg" value="批量删除" >
				</li>
			</#if>
			</@loginUserAuth>
			</ul>            
            <table class="table table-bordered">
                <thead class="bordered-darkorange">
                <tr class="th_title">
                	<th><input type="checkbox" id="checkAll" name="checkAll"/>全选</th>
                    <th>序号</th>
            		<th class="sorting" data-sortName="sys_name" data-sortType="asc">系统名称</th>
            		<th class="sorting" data-sortName="copyright" data-sortType="asc">版权</th>
            		<th class="sorting" data-sortName="record_license" data-sortType="asc">备案许可证</th>
            		<th class="sorting" data-sortName="logo" data-sortType="asc">系统LOGO</th>
<!--             		<th class="sorting" data-sortName="comp_id" data-sortType="asc">企业</th> -->
<!--             		<th class="sorting" data-sortName="wechat_code" data-sortType="asc">微信二维码</th> -->
            		<th class="sorting" data-sortName="create_dt" data-sortType="asc">创建时间</th>
            		<th class="sorting" data-sortName="state" data-sortType="asc">状态</th>
                    <th>操作</th>
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
                width:800,
                height:320,
                alerttit:"编辑",
                callback:function(){
                    $(".layerContianer").load("${context}/admin/manager/editPage?id="+id,{},function(){});
                }
            });
            saveToolTip.show();
        });
        
        $(document).on("click",".stateInfo",function(){
	    	var that = this;
			var id = $(that).attr("entityId");
			var state = $(that).attr("entityState");
			var msg = state==0?"确认要启用吗！":"确认要禁用吗！";
			var tip = state==0?"启用成功":"禁用成功";
			toolTip.warn({
	            message:msg,
	            callback:function(){
					$.ajax({
					   type: "POST",
					   url: "/admin/manager/updateState",
					   data: "id="+id+"&state="+state,
					   success: function(json){
						   if(json.code == 1){
	   					    	toolTip.tips({
									timer:true,/*是否开启定时器*/
						            sec:2,/*倒计时的秒数*/
		   		    	    	    message:tip,
		   		    	    	    callback:function(){}
	   		    	        	});
	      					    $(".searchbtn ").click();
	   					    }else{
	   					    	 toolTip.error({
									 timer:true,/*是否开启定时器*/
						             sec:2,/*倒计时的秒数*/
	   		    	    	         message:"操作失败",
	      		    	    	     callback:function(){}
	   		    	        	});
	   					    }
					   }
					});
					
	            }
	        });
			
		});

		//删除按钮操作
        $(document).on("click","[btnType=del]",function(){
            var that = this;
            var id = $(that).attr("entityId");
            toolTip.warn({
                message:"确认要删除，删除后不可恢复！！",
                callback:function(){
                    $.ajax({
                        type: "POST",
                        url: "${context}/admin/manager/delete",
                        data: {id:id},
                        success: function(json){
                            if(json.code == 1){
                                toolTip.tips({
									timer:true,/*是否开启定时器*/
						            sec:2,/*倒计时的秒数*/
                                    message:"删除成功！",
                                    callback:function(){}
                                });
                                $(".searchbtn ").click();
                            }else{
                                toolTip.error({
									timer:true,/*是否开启定时器*/
						            sec:2,/*倒计时的秒数*/
                                    message:"删除失败！",
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
                    message:"请选择要删除的数据！",
                    callback:function(){}
                });
                return false;
            }
            toolTip.warn({
                message:"确认要删除，删除后不可恢复！！",
                callback:function(){
                    $.ajax({
                        type: "POST",
                        url: "${context}/admin/manager/deleteBatch",
                        data: {ids:arr.join(",")},
                        success: function(json){
                            if(json.code == 1){
                                toolTip.tips({
									timer:true,/*是否开启定时器*/
						            sec:2,/*倒计时的秒数*/
                                    message:"删除成功！",
                                    callback:function(){}
                                });
                                $('#checkAll').attr("checked",false);
                                $(".searchbtn ").click();
                            }else{
                                toolTip.error({
									timer:true,/*是否开启定时器*/
						            sec:2,/*倒计时的秒数*/
                                    message:"删除失败！",
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

        //查看LOGO图片
        $(document).on("click","[btnType=showLogo]",function(){
        	var that = this;
            var logo = $(that).attr("entityLogo");
            window.open(logo);
        });
        
        /*点击查询按钮*/
        $(document).on("click",".searchbtn",function(){
            var qm_data={};
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
            var qm_url = "${context}/admin/manager/findPage";
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
                width:800,
                height:320,
                alerttit:"新增",
                callback:function(){
                    $(".layerContianer").load("${context}/admin/manager/editPage",{},function(){});
                }
            });
            saveToolTip.show();
        });


        var qm_url = "${context}/admin/manager/findPage";
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
            	str +="<tr><td colspan='14'><img src='../../assets/images/load.png' class='no_data'/>查询无记录</td></tr>";
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
                <%=item.sysName%>
            </td>
            <td  class="text-center">
                <%=item.copyright%>
            </td>
            <td  class="text-center">
                <%=item.recordLicense%>
            </td>
            <td  class="text-center">
                <a href="javascript:void(0)" btnType="showLogo" entityLogo="<%=item.logo%>"><%=item.logo%></a>
            </td>
            <td  class="text-center">
                <%=item.createDt%>
            </td>
			<td  class="text-center">
				<%if(item.state == "0"){%>
					<a href="javascript:void(0)" class="stateInfo td_btn suspend_btn" entityId ="<%=item.id%>"  entityState ="<%=item.state%>">未启用</a>
				<%}%>
				<%if(item.state == "1"){%>
					<a href="javascript:void(0)" class="stateInfo td_btn active_btn" entityId ="<%=item.id%>"  entityState ="<%=item.state%>">已启用</a>
				<%}%>
			</td>
          <td  class="text-center">
           <@loginUserAuth key='manager_edit' ; value>
          <#if value =='1'>
              <a href="javascript:void(0)" btnType="edit" class="editInfo td_btn edit_btn edit_bg" entityId ="<%=item.id%>">编辑</a>
          </#if>
          </@loginUserAuth>
           <@loginUserAuth key='manager_del' ; value>
          <#if value =='1'>
                  <a href="javascript:void(0)" btnType="del" class="delInfo td_btn del_btn del_bg" entityId ="<%=item.id%>">删除</a>
          </#if>
          </@loginUserAuth>

          </td>
      </tr>
      <%});%>



</script>

</body>
</html>
