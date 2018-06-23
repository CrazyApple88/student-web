<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>企业管理</title>
    
    <#include "/system/common_css.ftl">
    <#include "/system/common_js.ftl">
    
</head>
<body>

<div class="main-container">
    <div class="serContainer">
        <ul class="serbox">
            <li>
                <div class="inpdiv inpdiv1">
                    <input type="text" placeholder="<@i18n.messageText code='business.management.text.business.name'  text='企业名称' />" class="sea_inp" id="search.compName">
                </div>
            </li>
            <li>
                <div class="inpdiv inpdiv1">
                    <input type="text" placeholder="<@i18n.messageText code='business.management.text.leader.name'  text='负责人姓名' />" class="sea_inp" id="search.personName">
                </div>
            </li>
            <li>
            	<div class="serbtnbox right" style="width:80px;height:35px;">
		            <input type="button" class="searchbtn sec_btn sec_btn_query" value="<@i18n.messageText code='common.button.query'  text='查询' />">
		        </div>
            </li>
            
        </ul>
        
    </div>
    <div class="tabContainer">
        <div class="col-lg-12 col-sm-12 col-xs-12">
           <ul class="btn_set">
        	<@loginUserAuth key='comp_add' ; value>
	            <#if value =='1'>
            	<li class="add_common">
					<input type="button" class="addCompany td_btn add_button add_bg" value="<@i18n.messageText code='business.management.button.create.business'  text='新增企业' />" >
            	</li>
            	</#if>
			</@loginUserAuth>
<!-- 	            <li><input type="submit" value="批量删除" class="td_btn del_btn"></li> -->
<!-- 	            <li><input type="submit" value="批量导入" class="td_btn into_btn"></li> -->
<!-- 	            <li><input type="submit" value="批量导出" class="td_btn outto_btn"></li> -->
	        </ul>
            <table class="table table-bordered">
                <thead class="bordered-darkorange">
                <tr class="th_title">
                    <th> <@i18n.messageText code='common.text.number'  text='序号' /> </th>
                    <th class="sorting"  data-sortName="comp_name" data-sortType="asc"> <@i18n.messageText code='business.management.text.business.name'  text='企业名称' /> </th>
                    <th class="sorting"  data-sortName="name_alias" data-sortType="asc"> <@i18n.messageText code='business.management.text.business.short.name'  text='企业简称' /> </th>
                    <th class="sorting"  data-sortName="person_name" data-sortType="asc"> <@i18n.messageText code='business.management.text.leader.name'  text='负责人姓名' /> </th>
                    <th> <@i18n.messageText code='business.management.text.person.in.charge.contact.information'  text='负责人联系方式' /> </th>
                    <th> <@i18n.messageText code='business.management.text.business.phone'  text='企业电话' /> </th>
                    <th> <@i18n.messageText code='business.management.text.business.real.name.certification.or.not'  text='business.management.text.business.real.name.certification.or.not=是否实名认证' /> </th>
                    <th class="sorting"  data-sortName="real_dt" data-sortType="asc"> <@i18n.messageText code='business.management.text.business.real.name.certification.time'  text='business.management.text.business.real.name.certification.time=实名认证时间' /> </th>
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

<script>

	//关闭弹出层，并刷新列表
	function delDialog(){
		$(".overlay").remove();
		$(".searchbtn").click();
	}
    $(function($) {
    	var toolTip = new diaPrompt();
    	$(document).on("click",".editInfo",function(){
	    	var that = this;
			var id = $(that).attr("id");
// 			window.open("/admin/company/getCompanyEditPage?id="+id);
			var toolTip = new Layer({
				layerclass:"layerContianer",//存放页面的容器类名
		        width:450,
		        height:450,
		        alerttit:"<@i18n.messageText code='common.button.edit'  text='编辑' />",
		        callback:function(){		        	
		        	$(".layerContianer").load('/admin/company/getCompanyEditPage?id='+id,{},function(){});
		        }
		    });
		    toolTip.show();
		});
    	$(document).on("click",".dicttypeAssignment",function(){
	    	var that = this;
			var id = $(that).attr("id");
			var toolTip = new Layer({
				layerclass:"layerContianer",//存放页面的容器类名
		        width:500,
		        height:400,
		        alerttit:"<@i18n.messageText code='common.button.dictionary.type.allocation'  text='字典类型分配' />",
		        setOverflow:"overflow-y:scroll",
		        callback:function(){		        	
		        	$(".layerContianer").load('/admin/dicttype/getDicttypeAssignmentPage?id='+id,{},function(){});
		        }
		    });
		    toolTip.show();
		});
		
		$(document).on("click",".delInfo",function(){
	    	var that = this;
			var id = $(that).attr("id");
			toolTip.warn({
	            message:"<@i18n.messageText code='process.definition.management.confirm.delete.cannot.restored'  text='确认要删除，删除后不可恢复！' />",
	            callback:function(){
					$.ajax({
						type: "POST",
					   url: "/admin/company/delete",
					   data: "id="+id,
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
									message:json.msg,
									callback:function(){}
								});
							}
					   }
					});
					
	            }
	        });
		});
	});
    
  //字段排序
	var sortName = $(this).attr("data-sortName");
	var sortClass = $(this).attr("class");
	var tabSort = new tableSort({sortClass:".sorting",callback:function(sortName,sortType){
		var search_compName = $("[id='search.compName']").val();
    	var search_personName = $("[id='search.personName']").val();
		var qm_url = "/admin/company/findPage?orderBy= "+sortName+" "+sortType;
		var nowPageSize = $("#limitpagenum").val();
    	var qm_data = {pageSize:nowPageSize,'search.compName':search_compName,'search.personName':search_personName};
		deplayPage(qm_url,qm_data);
	}});
    
    /*点击查询按钮*/
    $(".searchbtn ").click(function(){
    	var search_compName = $("[id='search.compName']").val();
    	var search_personName = $("[id='search.personName']").val();
        var qm_url = "/admin/company/findPage";
        var nowPageSize = $("#limitpagenum").val();
    	var qm_data = {pageSize:nowPageSize,'search.compName':search_compName,'search.personName':search_personName};
       	deplayPage(qm_url,qm_data); 
    });
    
    $(".addCompany").click(function(){
//     	window.open("/admin/company/getCompanyEditPage");
    	var toolTip = new Layer({
    		layerclass:"layerContianer",//存放页面的容器类名
	        width:450,
	        height:450,
	        alerttit:"<@i18n.messageText code='business.management.button.create.business'  text='新增企业' />",
	        callback:function(){		        	
	        	$(".layerContianer").load('/admin/company/getCompanyEditPage',{},function(){});
	        }
	    });
	    toolTip.show();
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
        
        var qm_url = "/admin/company/findPage";
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
                str+="<tr><td>"
                +(i+1)+"</td><td data-limit='30' title='"+item.compName+"'>"
                +item.compName+"</td><td data-limit='30' title='"+item.nameAlias+"'>"
                +item.nameAlias+"</td><td data-limit='30' title='"+item.personName+"'>"
                +item.personName+"</td><td data-limit='30' title='"+item.personMobile+"'>"
                +item.personMobile+"</td><td data-limit='30' title='"+item.compPhone+"'>"
                +item.compPhone+"</td><td>"
                +(item.isReal==1?" <@i18n.messageText code='common.text.true'  text='是' /> ":" <@i18n.messageText code='common.text.false'  text='否' /> ")+"</td><td>"
                +(item.realDt == '' ? '' : new Date(Date.parse(item.realDt.toString().replace(/-/g,"/"))).Format("yyyy-MM-dd"))+"</td><td width='280'>"
                + "<@loginUserAuth key='comp_edit' ; value><#if value =='1'><a href='javascript:void(0);' class='editInfo td_btn edit_btn edit_bg' id=" + item.id + "> <@i18n.messageText code='common.button.edit'  text='编辑' /> </a></#if></@loginUserAuth> "
                + "<@loginUserAuth key='comp_del' ; value><#if value =='1'><a href='javascript:void(0);' class='delInfo td_btn del_btn del_bg' id=" + item.id + "> <@i18n.messageText code='common.button.delete'  text='删除' /> </a></#if></@loginUserAuth> "
                + "<@loginUserAuth key='comp_dicttypeAssign' ; value><#if value =='1'><a href='javascript:void(0);' class='dicttypeAssignment td_btn limit_btn limit_bg' id=" + item.id + "> <@i18n.messageText code='common.button.dictionary.type.allocation'  text='字典类型分配' /> </a></#if></@loginUserAuth></td></tr> "
            });
            if(data == null || data == ''){
            	str +="<tr><td colspan='16'><img src='../../assets/images/load.png' class='no_data'/> <@i18n.messageText code='common.text.no.record'  text='查询无记录' /> </td></tr>";
            }
            $(".table-bordered tbody").append(str);
            UDP.Public.Limitcharacter();
        }});
    }
    
</script>
</body>
</html>