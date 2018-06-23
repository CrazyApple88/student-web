<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>用户管理--新增用户--请选择</title>
    
a    <#include "/system/common_js.ftl">
    
    
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
                    <input type="text" placeholder="<@i18n.messageText code='business.management.text.business.code'  text='企业编号' />" class="sea_inp" id="search.compCode">
                </div>
            </li>
            <li>
                <div class="inpdiv inpdiv1">
                    <input type="text" placeholder="<@i18n.messageText code='business.management.text.leader.name'  text='负责人姓名' />" class="sea_inp" id="search.personName">
                </div>
            </li>
            
            <li>

                    <input type="button" class="searchbtn2 sec_btn sec_btn_query " value="<@i18n.messageText code='common.button.query'  text='查询' />">

            </li>

        </ul>
    </div>
    <div class="tabContainer">
        <div class="col-lg-12 col-sm-12 col-xs-12">
            <table class="table table-bordered" style="margin-top:10px">
                <thead class="bordered-darkorange">
                <tr class="th_title">
                	<th> <@i18n.messageText code='role.management.button.please.select'  text='选择' /> </th>
                    <th> <@i18n.messageText code='common.text.number'  text='序号' /> </th>
                    <th> <@i18n.messageText code='business.management.text.business.name'  text='企业名称' /> </th>
                    <th> <@i18n.messageText code='business.management.text.business.code'  text='企业编号' /> </th>
                    <th> <@i18n.messageText code='business.management.text.leader.name'  text='负责人姓名' /> </th>
                    <th> <@i18n.messageText code='business.management.text.person.in.charge.contact.information'  text='负责人联系方式' /> </th>
                    <th> <@i18n.messageText code='business.management.text.business.phone'  text='企业电话' /> </th>
                </tr>
                </thead>
                <tbody id="tbodyCompany2">
                </tbody>
            </table>

            
        </div>
    </div>
    <div class="pageContainer" style="margin-bottom:0;">
        <!--存放加载进来的导航按钮-->
       <div class="pagecont">
           <div id="navbox2" style="float: right"></div>
       </div>
    </div>

        <div class="sp_btnC cw_dialogB" style="text-align:center">
              <input type="button" class="selectInfo fbtn btn_sm ensure_btn" value="<@i18n.messageText code='common.button.quit.true'  text='确定' />" id="selectInfo">
              <input type="button" class="cancelClose fbtn btn_sm cancle_btn" value="<@i18n.messageText code='common.button.quit.false'  text='关闭' />"  id="cancelClose">
        </div>

</div>
<script>
    
    /*点击查询按钮*/
    $(".searchbtn2 ").click(function(){
    	var search_compName = $("[id='search.compName']").val();
    	var search_compCode = $("[id='search.compCode']").val();
    	var search_personName = $("[id='search.personName']").val();
        var qm_url = "/admin/company/findPage";
        var qm_data = {pageSize:10,'search.useable':1,'search.compName':search_compName,'search.compCode':search_compCode,'search.personName':search_personName};
       	deplayPage2(qm_url,qm_data); 
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
        var qm_data = {pageSize:10,'search.useable':1};
       	deplayPage2(qm_url,qm_data);

       	$("#selectInfo").click(function(){
       		var selectValue = $('input:radio[name="radioId"]:checked').val(); //获取选中的值
       		if(selectValue == '' || selectValue == undefined || selectValue == "undefined"){
       			alert("<@i18n.messageText code='business.management.text.select.business'  text='请选择一个企业' />");
       		}else{
	       		var selectIdName = selectValue.split("|&|");//将ID和name切割，然后放入父窗口
	       		getInfoInput(selectIdName[0],selectIdName[1]);
	       		var toolTip3 = new Layer();
	       		toolTip3.delDialog("#cancelClose");
       		}
    	});
       	$("#cancelClose").click(function(){
       		var toolTip3 = new Layer();
       		toolTip3.delDialog("#cancelClose");
    	});
    });
    
    function deplayPage2(qm_url,qm_data,qm_limit){ 
        /*pageSize:设置每页显示的数据条数*/
        /*limit:设置页码按钮显示的个数*/
        /*pageBtnDiv：存放页码按钮的父元素名称*/
        /*pageBtnClass:存放按钮样式的类名*/
    	 var page  = new CommonPage({url:qm_url,data:qm_data,limit:10,setlimitpage:false,pageBtnDiv:$("#navbox2"),pageBtnClass:"pageBtnStyle4",callBack:function(data){
    		var str="";
            $("#tbodyCompany2").empty();
            $(data).each(function (i,item) {
                str+="<tr><td><input type='radio' style='width:15px;height:15px' name='radioId' value="+item.id+"|&|"+item.compName+"></td><td>"
                +(i+1)+"</td><td>"
                +item.compName+"</td><td>"
                +item.compCode+"</td><td>"
                +item.personName+"</td><td>"
                +item.personMobile+"</td><td>"
                +item.compPhone+"</td></tr> "
            });
            if(data == null || data == ''){
            	str +="<tr><td colspan='14'> <@i18n.messageText code='common.text.no.record'  text='查询无记录' /> </td></tr>";
            }
            $("#tbodyCompany2").append(str);
        }});
    }
    
</script>
</body>
</html>