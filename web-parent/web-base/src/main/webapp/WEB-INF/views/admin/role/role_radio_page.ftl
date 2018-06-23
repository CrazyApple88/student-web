<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>角色单选查询页面</title>
    
    <#include "/system/common_css.ftl">
    <#include "/system/common_js.ftl">
    
    
</head>
<body>

<div class="main-container">
    <div class="serContainer">
        <ul class="serbox">
            <li>
                <div class="inpdiv inpdiv1">
                    <input type="text" placeholder="<@i18n.messageText code='role.management.text.role.name'  text='角色名称' />" class="sea_inp" id="search.roleName">
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
                    <th> <@i18n.messageText code='role.management.text.role.name'  text='角色名称' /> </th>
                    <th> <@i18n.messageText code='role.management.text.detailed.description'  text='详细说明' /> </th>
                    <th> <@i18n.messageText code='role.management.text.whether.can.be.allocated'  text='是否可分配' /> </th>
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
	          <input type="button" class="selectRoleInfo fbtn btn_sm ensure_btn" value="<@i18n.messageText code='common.button.quit.true'  text='确定' />" id="selectRoleInfo">
		      <input type="button" class="cancel fbtn btn_sm cancle_btn" value="<@i18n.messageText code='common.button.quit.false'  text='关闭' />" onclick="javascript:window.close()">
	      </ul>
	</div>
    </div>
</div>
<script>
    
	/*点击查询按钮*/
	$(".searchbtn ").click(function(){
		var search_roleName = $("[id='search.roleName']").val();
	    var qm_url = "/admin/role/findPage";
	    var qm_data = {pageSize:10,'search.roleName':search_roleName};
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
        
        var qm_url = "/admin/role/findPage";
        var qm_data = {pageSize:10};
       	deplayPage(qm_url,qm_data);
       	
       	$("#selectRoleInfo").click(function(){
       		var selectValue = $('input:radio[name="radioRoleId"]:checked').val(); //获取选中的值
       		if(selectValue == '' || selectValue == undefined || selectValue == "undefined"){
       			alsert("<@i18n.messageText code='role.management.text.select.role.requires.authorization'  text='请选择一个需要授权的角色' />");
       		}else{
	       		var selectIdName = selectValue.split("|&|");//将ID和name切割，然后放入父窗口
	       		window.opener.document.getElementById('nowSelectId').value=selectIdName[0];
	       		window.opener.document.getElementById('nowSelectName').value=selectIdName[1];
	       		window.opener.document.getElementById('nowSelectType').value="2";//当前选择的授权类别
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
                str+=" <tr><td><input type='radio' style='width:15px;height:15px' name='radioRoleId' value="+item.id+"|&|"+item.roleName+"></td><td>"
                +(i+1)+"</td><td>"
                +item.roleName+"</td><td>"
                +item.intro+"</td><td>"
                +item.useable+"</td></tr>";
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