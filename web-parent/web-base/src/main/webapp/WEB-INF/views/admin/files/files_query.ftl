<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<title>文件资源管理</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="description" content="文件资源列表信息">
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
						<input type="text" placeholder="<@i18n.messageText code='file.resource.management.text.file.name'  text='文件名称' />" class="sea_inp" queryCriteria="search.fileName">
					</div>
				</li>
				<li>
					<div class="inpdiv inpdiv1">
						<input type="text" placeholder="<@i18n.messageText code='file.resource.management.text.file.suffix.name'  text='文件后缀名称' />" class="sea_inp" queryCriteria="search.suffixName">
					</div>
				</li>
				<li>
					<div class="inpdiv inpdiv1">
						<input type="text" placeholder="<@i18n.messageText code='file.resource.management.text.store.name'  text='存放名称' />" class="sea_inp" queryCriteria="search.serviceName">
					</div>
				</li>
				<li>
					<div class="inpdiv inpdiv1">
						<input type="text" placeholder="<@i18n.messageText code='file.resource.management.text.uploader'  text='上传者' />" class="sea_inp" queryCriteria="search.createBy">
					</div>
				</li>
				<li>
					<div class="serbtnbox right" >
            			<input type="button"   class="searchbtn sec_btn sec_btn_query" value="<@i18n.messageText code='common.button.query'  text='查询' />">
        			</div>
        		</li>
        </ul>
    </div>
    <div class="tabContainer">
        <div class="col-lg-12 col-sm-12 col-xs-12">
            <table class="table table-bordered">
                <thead class="bordered-darkorange">
                <tr class="th_title">
                    <th> <@i18n.messageText code='common.text.number'  text='序号' /> </th>
		            <th class="text-center">
		                <@i18n.messageText code='file.resource.management.text.file.name'  text='文件名称' />
		            </th>
		            <th class="text-center">
		                <@i18n.messageText code='file.resource.management.text.file.catalog'  text='文件目录' />
		            </th>
		            <th class="text-center">
		                <@i18n.messageText code='file.resource.management.text.file.size'  text='文件大小' />
		            </th>
		            <th class="text-center">
		                <@i18n.messageText code='file.resource.management.text.file.suffix.name'  text='文件后缀名称' />
		            </th>
		            <th class="text-center">
		                <@i18n.messageText code='file.resource.management.text.store.name'  text='存放名称' />
		            </th>
		            <th class="text-center">
		                <@i18n.messageText code='file.resource.management.text.uploader'  text='上传者' />
		            </th>
		            <th class="text-center">
		                <@i18n.messageText code='file.resource.management.text.uopload.time'  text='上传时间' />
		            </th>
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
    $(function($) {
        /*点击查询按钮*/
        $(document).on("click",".searchbtn",function(){
        	var nowPageSize = $("#limitpagenum").val();
        	var qm_data = {pageSize:nowPageSize};
			$("[queryCriteria]").each(function(i,item){
				var id  = $(item).attr("queryCriteria");
				var value  = $(item).attr("value");
                qm_data[id]=value;
			});
            var qm_url = "${context}/admin/files/findPage";
            deplayPage(qm_url,qm_data);
        });


        var qm_url = "${context}/admin/files/findPage";
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
            	str +="<tr><td colspan='16'><img src='../../assets/images/load.png' class='no_data'/><@i18n.messageText code='common.text.no.record'  text='查询无记录' /></td></tr>";
            }
            $(".table-bordered tbody").append(str);
        }});
    }

    $(document).on("click",".obtainFile",function(){
		var that = this;
		var id = $(that).attr("id");
		window.open("/admin/files/getFileByte?id="+id);
	});
</script>

<!-- table内容模板--->
<script type="text/template" id="listTemplate">
      <%$(data).each(function(i,item){%>
         <tr>
          <td  class="text-center">
                <%=i+1%>
          </td>
            <td  class="text-center">
                <a href="javascript:void(0);" class="obtainFile" id="<%=item.id%>"><%=item.fileName%></a>
            </td>
            <td  class="text-center">
                <%=item.fileDir%>
            </td>
            <td  class="text-center">
                <%=item.fileSize%>
            </td>
            <td  class="text-center">
                <%=item.suffixName%>
            </td>
            <td  class="text-center">
                <%=item.serviceName%>
            </td>
            <td  class="text-center">
                <%=item.createBy%>
            </td>
            <td  class="text-center">
                <%=item.createDate%>
            </td>
      </tr>
      <%});%>



</script>

</body>
</html>
