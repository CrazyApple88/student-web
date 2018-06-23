<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>用户单选查询页面</title>
    
    <#include "/system/common_js.ftl">
    
    
</head>
<body>

<div class="main-container">
    <div class="serContainer">
        <ul class="serbox">
            <li>
                <label class="role_name"><@i18n.messageText code='user.management.text.real.name'  text='真实姓名:' /></label>
                <div class="inpdiv inpdiv1">
                    <input type="text" placeholder="<@i18n.messageText code='user.management.text.real.name'  text='真实姓名' />" class="sea_inp" id="search.realName">
                </div>
            </li>
            <li>
                <label class="role_name"><@i18n.messageText code='user.management.text.username'  text='用户名:' /></label>
                <div class="inpdiv inpdiv1">
                    <input type="text" placeholder="<@i18n.messageText code='user.management.text.username'  text='用户名' />" class="sea_inp" id="search.userName">
                </div>
            </li>
            
            <li>
            	<label class="role_name"></label>
                <div class="serbtnbox right" >
           			<input type="button"   class="searchbtn2 sec_btn sec_btn_query" value="<@i18n.messageText code='common.button.query'  text='查询' />">
       			</div>
				
            </li>

        </ul>
    </div>
    <div class="tabContainer">
        <div class="col-lg-12 col-sm-12 col-xs-12">
            <table class="table table-bordered2" style="margin-top:10px">
                <thead class="bordered-darkorange">
                <tr class="th_title">
                    <th> <@i18n.messageText code='common.text.number'  text='序号' /> </th>
                    <th> <@i18n.messageText code='user.management.text.real.name'  text='真实姓名' /> </th>
                    <th> <@i18n.messageText code='user.management.text.username'  text='用户名' /> </th>
                </tr>
                </thead>
                <tbody id="tbody">
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
    var selectId = "${selectId}";
    var selectType = "${selectType}";
    /* 点击查询按钮，重新打开的列表页面，需要将公用方法和按钮参数都加个别名2 */
    $(".searchbtn2 ").click(function(){
    	var search_realName = $("[id='search.realName']").val();
    	var search_userName = $("[id='search.userName']").val();
        var qm_url2 = "/admin/user/findPage";
        var nowPageSize = $("#limitpagenum").val();
    	var qm_data2 = {pageSize:nowPageSize,"search.selectId":selectId,"search.selectType":selectType,'search.realName':search_realName,'search.userName':search_userName};
       	deplayPage2(qm_url2,qm_data2);
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
        
        var qm_url2 = "/admin/user/findPage";
        var qm_data2 = {pageSize:10,"search.selectId":selectId,"search.selectType":selectType};
       	deplayPage2(qm_url2,qm_data2);
       	
    });
    
    function deplayPage2(qm_url,qm_data,qm_limit){
        /*pageSize:设置每页显示的数据条数*/
        /*limit:设置页码按钮显示的个数*/
        /*pageBtnDiv：存放页码按钮的父元素名称*/
        /*pageBtnClass:存放按钮样式的类名*/
    	 var page  = new CommonPage({url:qm_url,data:qm_data,limit:10,pageBtnDiv:$("#navbox"),pageBtnClass:"pageBtnStyle4",callBack:function(data){
            var str="";
            $(".table-bordered2 #tbody").empty();
            $(data).each(function (i,item) {
                str+=" <tr><td>"
                +(i+1)+"</td><td>"
                +item.realName+"</td><td>"
                +item.userName+"</td></tr>";
            });
            if(data == null || data == ''){
            	str +="<tr><td colspan='6'><img src='../../assets/images/load.png' class='no_data'/> <@i18n.messageText code='common.text.no.record'  text='查询无记录' /> </td></tr>";
            }
            $(".table-bordered2 #tbody").append(str);
        }});
    }
    
</script>
</body>
</html>