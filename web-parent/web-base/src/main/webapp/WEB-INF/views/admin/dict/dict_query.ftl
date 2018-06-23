<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    
    <#include "/system/common_css.ftl">
    <#include "/system/common_js.ftl">
</head>
<body>

<div class="main-container">
    
    <div class="tabContainer">
        <div class="col-lg-12 col-sm-12 col-xs-12">
            <ul class="btn_set">
        	 <@loginUserAuth key='dict_add' ; value>
	            <#if value =='1'>
            	<li class="add_common">
					<input type="button" class="addDict td_btn add_button add_bg"  value="<@i18n.messageText code='dictionary.management.button.create.dictionary'  text='新增字典' />">
            	</li>
            	</#if>
			</@loginUserAuth>
	            <li><input type="button" value="<@i18n.messageText code='common.button.batch.delete'  text='批量删除' />" id="del" class="del td_btn del_btn del_bg"></li>
	            <!-- <li><input type="submit" value="批量导入" class="td_btn into_btn into_bg"></li>
	            <li><input type="submit" value="批量导出" class="td_btn outto_btn outto_bg"></li> -->
	        </ul>
            <table class="table table-bordered">
                <thead class="bordered-darkorange">
                <tr class="th_title">
                	<th>
                		<input type="checkbox" id="checkAll"/>
                	</th>
                    <th> <@i18n.messageText code='common.text.number'  text='序号' /> </th>
                    <th class="sorting"  data-sortName="dict_name" data-sortType="asc"> <@i18n.messageText code='dictionary.management.text.dictionary.name'  text='字典名称' /> </th>
                    <th class="sorting"  data-sortName="dict_code" data-sortType="asc"> <@i18n.messageText code='dictionary.management.text.dictionary.number'  text='字典编号' /> </th>
                    <th class="sorting"  data-sortName="sort" data-sortType="asc"> <@i18n.messageText code='dictionary.management.text.dictionary.sequence'  text='排序' /> </th>
                    <th> <@i18n.messageText code='dictionary.management.text.dictionary.note.description'  text='备注描述' /> </th>
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
	var toolTip = new diaPrompt();
	//字段排序
	var sortName = $(this).attr("data-sortName");
	var sortClass = $(this).attr("class");
	var tabSort = new tableSort({sortClass:".sorting",callback:function(sortName,sortType){
		var typeId = "${typeId}";
        var qm_url = "/admin/dict/findPage?orderBy= "+sortName+" "+sortType;
        var nowPageSize = $("#limitpagenum").val();
    	var qm_data = {pageSize:nowPageSize,'search.typeId':typeId};
		deplayPage(qm_url,qm_data);
	}});

	//关闭弹出层，并刷新列表
	function delDialog(){
		$(".overlay").remove();
 		$("#tabledata").load("/admin/dict/getDictQueryPage?typeId=${typeId}",{},function(){});
	}
    $(function($) {
    	$(".table").on("click",".editInfo",function(){ 
	    	var that = this;
			var id = $(that).attr("id");
// 			window.open("/admin/dict/getDictEditPage?id="+id+"&typeId=${typeId}");
			var toolTip = new Layer({
				layerclass:"layerContianer",//存放页面的容器类名
		        width:450,
		        height:380,
		        alerttit:"<@i18n.messageText code='dictionary.management.text.dictionary.edit'  text='字典编辑' />",
		        callback:function(){		        	
		        	$(".layerContianer").load('/admin/dict/getDictEditPage?id='+id+'&typeId=${typeId}',{},function(){});
		        }
		    });
		    toolTip.show();
		});
		
    	$(".table").on("click",".delInfo",function(){ 
	    	var that = this;
			var id = $(that).attr("id");
			toolTip.warn({
	            message:"<@i18n.messageText code='process.definition.management.confirm.delete.cannot.restored'  text='确认要删除，删除后不可恢复！' />",
	            callback:function(){
					$.ajax({
						type: "POST",
					   url: "/admin/dict/delete",
					   data: "id="+id,
					   success: function(json){
					     if(json.code == 1){
					     	toolTip.tips({
								timer:true,/*是否开启定时器*/
					            sec:2,/*倒计时的秒数*/
					    		message:"<@i18n.messageText code='process.definition.management.text.delete.successfully'  text='删除成功！' />",
					    		callback:function(){}
					    	});
					     	$("#tabledata").load("/admin/dict/getDictQueryPage?typeId=${typeId}",{},function(){});
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
	});
    
    $(".addDict").click(function(){
//     	window.open("/admin/dict/getDictEditPage?typeId=${typeId}");
    	var toolTip = new Layer({
    		layerclass:"layerContianer",//存放页面的容器类名
	        width:450,
	        height:380,
	        alerttit:"<@i18n.messageText code='dictionary.management.button.create.dictionary'  text='新增字典' />",
	        callback:function(){		        	
	        	$(".layerContianer").load('/admin/dict/getDictEditPage?typeId=${typeId}',{},function(){});
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
        
        var typeId = "${typeId}";
        var qm_url = "/admin/dict/findPage";
        var qm_data = {pageSize:10,'search.typeId':typeId};
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
                str+="<tr><td><input type='checkbox' name='checkItem' data-item='"+item.id+"'/></td><td>"
                +(i+1)+"</td><td>"
                +item.dictName+"</td><td>"
                +item.dictCode+"</td><td class='item'>"
                +item.sort+"</td><td>"
                +item.intro+" </td><td width='150'>"
                +  "<@loginUserAuth key='dict_edit' ; value><#if value =='1'><a href='javascript:void(0);' class='editInfo td_btn edit_btn edit_bg' id=" + item.id + "> <@i18n.messageText code='common.button.edit'  text='编辑' /> </a></#if></@loginUserAuth> "
                + "<@loginUserAuth key='dict_del' ; value><#if value =='1'><a href='javascript:void(0);' class='delInfo td_btn del_btn del_bg' id=" + item.id + "> <@i18n.messageText code='common.button.delete'  text='删除' /> </a></#if></@loginUserAuth></td></tr> "
            });
            if(data == null || data == ''){
            	str +="<tr><td colspan='14'><img src='../../assets/images/load.png' class='no_data'/> <@i18n.messageText code='common.text.no.record'  text='查询无记录' /> </td></tr>";
            }
            $(".table-bordered tbody").append(str);
        }});
    
    	//全选
      	$("#checkAll").click(function() {
      		$('input[name="checkItem"]').attr("checked",this.checked); 
      	});
      	var $subBox = $("input[name='checkItem']");
      	$subBox.click(function(){
      		$("#checkAll").attr("checked",$subBox.length == $("input[name='checkItem']:checked").length ? true : false);
      	});
      	$(".item").dblclick(function(){
      	    var td = $(this);
      	    var text = td.text();
      	    var txt = $("<input type='text' style='width:15px;height:30px' name='sort' autocomplete='off'>").val(text);
      	    txt.blur(function(){
      	    	var sort = txt.val();
          		var id = td.parent().find("input").eq(0).attr("data-item")
          		$.ajax({
          			url : "/admin/dict/updateSort", 
    				type : "post", //数据发送方式
    				dataType : "json", //接受数据格式   
    				data : {sort : sort, id : id},
    				success : function(json) {
    					var typeId = "${typeId}";
    					var qm_url = "/admin/dict/findPage";
					var nowPageSize = $("#limitpagenum").val();
    			        	var qm_data = {pageSize:nowPageSize,'search.typeId':typeId};
    			       	deplayPage(qm_url,qm_data);
    				}
          		});
      	    });
      	    td.text("");
      	    td.append(txt);
      	});
      	
      	//批量删除
      	$(".del").click(function(){
     		var toolTip = new diaPrompt();
     		var arr = [];
     		$("input[name='checkItem']:checked").each(function(i,item){
     			arr.push($(item).attr("data-item"));
     		});
     		if(arr.length == 0 || arr.length == null){
     			toolTip.error({
					timer:true,/*是否开启定时器*/
		            sec:2,/*倒计时的秒数*/
     				message:"<@i18n.messageText code='dictionary.management.text.select.dictionary.data.delete'  text='请选择要删除的字典数据！' />",
     				callback:function(){}
     			});
     			return;
     		}
     		toolTip.warn({
     	    	message:"<@i18n.messageText code='process.definition.management.confirm.delete.cannot.restored'  text='确认要删除，删除后不可恢复！' />",
     	    	callback:function(){
     				$.ajax({
     					type: "POST",
     					url: "/admin/dict/deleteBatch",
     					data: "ids="+arr,
     					success: function(json){
     	 					if(json.code == 1){
     	 						toolTip.tips({
									timer:true,/*是否开启定时器*/
						            sec:2,/*倒计时的秒数*/
     	 							message:"<@i18n.messageText code='process.definition.management.text.delete.successfully'  text='删除成功！' />",
     	 							callback:function(){}
     	 						});
     	 						$("#tabledata").load("/admin/dict/getDictQueryPage?typeId=${typeId}",{},function(){});
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
    }
</script>
</body>
</html>