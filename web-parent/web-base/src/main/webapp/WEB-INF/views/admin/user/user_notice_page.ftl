<!DOCTYPE html>
<#import "spring.ftl" as i18n/>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>电子围栏--推送人选择页面</title>
</head>
<body>

<div class="main-container">
    <div class="serContainer">
        <ul class="serbox">
            <li>
                <div class="inpdiv inpdiv1">
                    <input type="text" placeholder="<@i18n.messageText code='user.management.text.username'  text='用户名' />" class="sea_inp" id="search.userName">
                </div>
            </li>
            
            <li>
                <div class="serbtnbox right" >
                    <input type="button" class="searchbtnUser sec_btn sec_btn_query" value="<@i18n.messageText code='common.button.query'  text='查询' />">
                </div>
				
            </li>

        </ul>
    </div>
    <div class="tabContainer">
        <div class="well with-header  with-footer">
            <table class="table table-bordered">
                <thead class="bordered-darkorange">
                <tr class="th_title">
                    <th>
                		<input type="checkbox" id="checkAll" name="checkAll"/> <@i18n.messageText code='common.select.all.select'  text='全选' />
                	</th>
                    <th> <@i18n.messageText code='common.text.number'  text='序号' /> </th>
                    <th> <@i18n.messageText code='user.management.text.username'  text='用户名' /> </th>
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
		      <input type="button" class="cancel fbtn btn_sm cancle_btn" value="<@i18n.messageText code='common.button.quit.false'  text='关闭' />" onclick="javascript:delDialog()">
	      </ul>
	</div>
    </div>
</div>
<script>
    
    /*点击查询按钮*/
    $(".searchbtnUser ").click(function(){
    	var search_userName = $("[id='search.userName']").val();
        var qm_url = "/admin/user/findPage";
        var nowPageSize = $("#limitpagenum").val();
    	var qm_data = {pageSize:nowPageSize,'search.userName':search_userName};
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
       		var noticeTo = "";
       		var noticeToName = "";
       		$('#show_notice_to').empty();
            $('input:checkbox[name=checkItem]:checked').each(function(i){
            	var selectIdName = $(this).val().split("|&|");
            	if(0==i){
            		noticeTo = selectIdName[0];
            		noticeToName = selectIdName[1];
                }else{
                	noticeTo += (","+selectIdName[0]);
                	noticeToName += (","+selectIdName[1]);
                }
            });
            
       		$('#noticeTo').val(noticeTo);
       		$('#show_notice_to').append(noticeToName);
       		delDialog();
    	});
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
                str+=" <tr><td><input type='checkbox' style='width:15px;height:15px' name='checkItem' value="+item.id+"|&|"+item.userName+"></td><td>"
                +(i+1)+"</td><td>"
                +item.userName+"</td></tr>";
            });
            if(data == null || data == ''){
            	str +="<tr><td colspan='10'> <@i18n.messageText code='common.text.no.record'  text='查询无记录' /> </td></tr>";
            }
            $(".table-bordered tbody").append(str);
            
            $("#checkAll").attr("checked",  false);
        }});
    
    	//初始化 check box 全选按钮
        UDP.Public.checkAllInit();
    
    	//全选
   	  	/* $("#checkAll").click(function() {
   	  		$('input[name="checkboxUserId"]').attr("checked",this.checked); 
   	  	});
     	$(document).on('click',"input[name='checkboxUserId']",function(){
    		var $subBox = $("input[name='checkboxUserId']");
   	  		$("#checkAll").attr("checked",$subBox.length == $("input[name='checkboxUserId']:checked").length ? true : false);
   	  	}); */
    }
    
</script>
</body>
</html>