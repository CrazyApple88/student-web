<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>用户单选查询页面</title>
    
    <#include "/system/common_css.ftl">
    <#include "/system/common_js.ftl">
    
    
</head>
<body>

<div class="main-container">
    <div class="serContainer">
        <ul class="serbox">
            <li>
                <div class="inpdiv inpdiv1">
                    <input type="text" placeholder="<@i18n.messageText code='user.management.text.real.name'  text='真实姓名' />" class="sea_inp" id="search.realName">
                </div>
            </li>
            <li>
                <div class="inpdiv inpdiv1">
                    <input type="text" placeholder="<@i18n.messageText code='user.management.text.username'  text='用户名' />" class="sea_inp" id="search.userName">
                </div>
            </li>
            
            <li>
                <div class="serbtnbox right" >
                    <input type="button" class="searchbtn btn_sm btncolor3" value="<@i18n.messageText code='common.button.query'  text='查询' />">
                </div>
				
            </li>

        </ul>
    </div>
    <div class="tabContainer">
        <div class="well with-header  with-footer">
            <table class="table table-bordered">
                <thead class="bordered-darkorange">
                <tr class="th_title">
                    <th> <@i18n.messageText code='role.management.button.please.select'  text='选择' /> </th>
                    <th> <@i18n.messageText code='common.text.number'  text='序号' /> </th>
                    <th> <@i18n.messageText code='user.management.text.real.name'  text='真实姓名' /> </th>
                    <th> <@i18n.messageText code='user.management.text.username'  text='用户名' /> </th>
                    <th> <@i18n.messageText code='user.management.select.user.status'  text='用户状态' /> </th>
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
	<div class="sp_btnC cw_dialogB">
	      <ul style="text-align:center">
	          <input type="button" class="selectUserInfo fbtn btn_sm ensure_btn" value="<@i18n.messageText code='common.button.quit.true'  text='确定' />" id="selectUserInfo">
		      <input type="button" class="cancel fbtn btn_sm cancle_btn" value="<@i18n.messageText code='common.button.quit.false'  text='关闭' />" onclick="javascript:window.close()">
	      </ul>
	</div>
    </div>
</div>
<script>
    
    /*点击查询按钮*/
    $(".searchbtn ").click(function(){
    	var search_realName = $("[id='search.realName']").val();
    	var search_userName = $("[id='search.userName']").val();
        var qm_url = "/admin/user/findPage";
        var qm_data = {pageSize:10,'search.realName':search_realName,'search.userName':search_userName};
       	deplayPage(qm_url,qm_data);
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
        
        var qm_url = "/admin/user/findPage";
        var qm_data = {pageSize:10};
       	deplayPage(qm_url,qm_data);
       	
       	$("#selectUserInfo").click(function(){
       		var selectValue = $('input:radio[name="radioUserId"]:checked').val(); //获取选中的值
       		if(selectValue == '' || selectValue == undefined || selectValue == "undefined"){
       			alsert("<@i18n.messageText code='user.management.text.select.authorized.user'  text='请选择一个需要授权的用户' />");
       		}else{
	       		var selectIdName = selectValue.split("|&|");//将ID和name切割，然后放入父窗口
	       		window.opener.document.getElementById('nowSelectId').value=selectIdName[0];
	       		window.opener.document.getElementById('nowSelectName').value=selectIdName[1];
	       		window.opener.document.getElementById('nowSelectType').value="1";//当前选择的授权类别
	       		window.opener.getMenuTree(selectIdName[0]);
	       		window.close();
       		}
    	});
    });
    
    function deplayPage(qm_url,qm_data,qm_limit){
        /*pageSize:设置每页显示的数据条数*/
        /*limit:设置页码按钮显示的个数*/
        /*pageBtnDiv：存放页码按钮的父元素名称*/
        /*pageBtnClass:存放按钮样式的类名*/
    	 var page  = new CommonPage({url:qm_url,data:qm_data,limit:10,setlimitpage:false,pageBtnDiv:$("#navbox"),pageBtnClass:"pageBtnStyle4",callBack:function(data){
            var str="";
            $(".table-bordered tbody").empty();
            $(data).each(function (i,item) {
                str+=" <tr><td><input type='radio' style='width:15px;height:15px' name='radioUserId' value="+item.id+"|&|"+item.userName+"></td><td>"
                +(i+1)+"</td><td>"
                +item.realName+"</td><td>"
                +item.userName+"</td><td>"
                +item.loginStatus+"</td></tr>";
            });
            if(data == null || data == ''){
            	str +="<tr><td colspan='10'> <@i18n.messageText code='common.text.no.record'  text='查询无记录' /> </td></tr>";
            }
            $(".table-bordered tbody").append(str);
        }});
    }
    
</script>
</body>
</html>