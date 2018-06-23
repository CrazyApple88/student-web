<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <#include "/system/common_css.ftl">
    <#include "/system/common_js.ftl">
    <@menuLibraryCss />
    <style type="text/css">
  		.layerBox{
  			width: 500px !important; 
  			height: 420px !important; 
  		}
  		.sidebar-border{
  			border:1px solid #ddd;
  		}
  	</style>
	<script type="text/javascript">
	var toolTip = new diaPrompt();
		$().ready(function() {
			// validate signup form on keyup and submit
			$("#form_post").validate({
				submitHandler : function(form) { //验证通过后的执行方法
					var id = $("[name='id']").val();
					if (id != '') {
						$('#form_post').attr("action", "/admin/menu/update");
					}
					//当前的form通过ajax方式提交（用到jQuery.Form文件）
					$(form).ajaxSubmit({
						dataType : "json",
						success : function(json) {
							if (json.code == 1) {
								location.reload();
								var menuId = json.data.id;
		    	        		var parentId = json.data.parentId;
		    	        		var menuName = json.data.menuName;
		    	        		var type = 1;
		    	        		if(id != null && id.length > 0){
		    	        			type = 2;
		    	        		}
		    	        		add_menu_success(menuId,parentId,menuName,type);
		    	    			$("#tabledata").load('/admin/menu/getMenuEditPage?id='+menuId,{},function(){});
							} else {
								toolTip.error({
									timer:true,/*是否开启定时器*/
						            sec:2,/*倒计时的秒数*/
									message:"<@i18n.messageText code='menu.management.text.query.menu.information.failed'  text='查询菜单信息失败！' />",
									callback:function(){}
								});
							}
						}
					});
	
				},
				focusInvalid : true, //验证提示时，鼠标光标指向提示的input
				onkeyup:false,
				rules : {
					menuName : {
						required : true,
						rangelength : [ 1, 32 ],
						spaceCheck : "menuName"
					},
					authTab : {
						required : true,
						rangelength : [ 1, 32 ],
						regex : "^\\w+$",
						remote : {
							url : "/admin/menu/validateAuthTab", //后台处理程序,验证权限编码是否存在
							type : "post", //数据发送方式
							dataType : "json", //接受数据格式   
							data : { //要传递的数据
								authTab : function() {
									return $("#authTab").val();
								},
								id : function() {
									return $("[name='id']").val();
								}
							},
							dataFilter : function(data) { //返回结果
								var json = eval('(' + data + ')');
								if (json.data == "y")//存在yes
									return false;
								else
									//不存在no
									return true;
							}
						}
					},
					languageCode : {
						maxlength : 128,
						regex : "^[a-z.]+$"
					},
					url : {
						maxlength : 128,
						remote : {
							url : "/admin/menu/validateUrl", //后台处理程序,验证权限编码是否存在
							type : "post", //数据发送方式
							dataType : "json", //接受数据格式   
							data : { //要传递的数据
								url : function() {
									return $("#url").val();
								},
								id : function() {
									return $("[name='id']").val();
								}
							},
							dataFilter : function(data) { //返回结果
								var json = eval('(' + data + ')');
								if (json.data == "y")//存在yes
									return false;
								else
									//不存在no
									return true;
							}
						}
					},
					mobileUrl : {
						maxlength : 128
					},
					intro : {
						maxlength : 500
					}
				},
				messages : {
					menuName : {
						required : "<@i18n.messageText code='menu.management.form.confirm.menu.name.empty'  text='菜单名称不能为空' />",
						rangelength : "<@i18n.messageText code='menu.management.form.confirm.menu.name.length'  text='菜单名称长度必须在1-32之间' />",
						spaceCheck : "<@i18n.messageText code='menu.management.form.confirm.menu.name.space'  text='菜单名称不能以空格开头' />"
					},
					authTab : {
						required : "<@i18n.messageText code='menu.management.form.confirm.permission.identify.empty'  text='权限标识不能为空' />",
						rangelength : "<@i18n.messageText code='menu.management.form.confirm.permission.identify.length'  text='权限标识长度必须在1-32之间' />",
						regex : "<@i18n.messageText code='menu.management.form.confirm.permission.identify.consist'  text='权限标识只能是数字、字母与下划线' />",
						remote : "<@i18n.messageText code='menu.management.form.confirm.permission.identify.exists'  text='权限标识已存在！' />"
					},
					languageCode : {
						regex : "<@i18n.messageText code='menu.management.form.confirm.language.encoding.letters'  text='语言编码只能是字母和.' />",
						maxlength : "<@i18n.messageText code='menu.management.form.confirm.language.encoding.length'  text='语言编码长度不能超过128' />"
					},
					url : {
						maxlength : "<@i18n.messageText code='menu.management.form.confirm.link.length'  text='链接长度不能超过128' />",
						remote : "<@i18n.messageText code='menu.management.form.confirm.link.menu.url.repetition'  text='链接菜单url不允许重复！' />"
					},
					mobileUrl : {
						maxlength : "<@i18n.messageText code='menu.management.form.confirm.mobile.version.address.length'  text='手机版的访问页面地址长度不能超过128' />"
					},
					intro : {
						maxlength : "<@i18n.messageText code='menu.management.form.confirm.menu.description.length'  text='菜单描述长度不能超过500' />"
					}
				}
			});
		});
	</script>
    <script type="text/javascript">

    function showHideTr(menuTypeValue){
    	if(menuTypeValue == "99"){//按钮
			$("#menuTr1").hide();
			$("#menuTr2").hide();
			$("#menuTr3").hide();
			$("#menuTr4").hide();
			$("#menuSort").hide();
		}else if(menuTypeValue == "3"){//链接菜单
			$("#menuTr1").show();
			$("#menuTr2").show();
			$("#menuTr3").show();
			$("#menuTr4").hide();
			$("#menuSort").show();
		}else{
			$("#menuTr1").hide();
			$("#menuTr2").hide();
			$("#menuTr3").hide();
			$("#menuTr4").show();
			$("#menuSort").show();
		}
    }

    $(document).ready(function(){
    	$("#editMenuId").click(function(){
    		$("input,select").attr("disabled",false);
    		$("#editMenuId").hide();
    		$("[name='delFileDiv']").show();
    		$("[name='fileId']").show();
    		$("[name='delFileDiv0']").hide();
    		$("#saveMenuId,#quXiaoId").show();
    		
    	});
    	$("#saveMenuId").click(function(){
    		var className=$("#iconDiv").attr("data");
    		$("#icon").val(className);
    		$("#form_post").submit(); 
    	});
    	
    	$("#quXiaoId").click(function(){
    		var id = $("[name='id']").val();
    		if(id == ''){
    			id = $("[name='parentId']").val();
    		}
    		$("#tabledata").load('/admin/menu/getMenuEditPage?id='+id,{},function(){});
    	});
    	
    	var layer = new Layer({
            layerclass:"layerContianer1",//存放页面的容器类名，
            width:600,//像素是number型，百分比是string型，也可设置成百分比的形式，width和height形式要保持一致,例如：width:"50%"
            height:500,
            alerttit:"图标选择",
            setOverflow:"overflow-y:scroll",//设置滚动的属性 overflow-y：设置竖向  overflow-x:设置横向
            callback:function(){
            	 $(".layerContianer1").load("${context}/admin/menu/getIconPage",{},function(){});
            }
        });
        $(".inpBtn").click(function() {
            layer.show();
        });
        
    	var type = '${type}';
    	if(type == "add"){
    		$('#form_post').attr("action", "/admin/menu/save");
    		$("#editMenuId").click();
    		$("#parentIdTr").show();
    		showHideTr("1");
    	}
    	$('#menuType').change(function(){ 
    		var menuTypeValue = $('#menuType option:selected').val();//这就是 selected 的值 
    		showHideTr(menuTypeValue);
    	});
    });
    </script>
</head>
<body>
<div class="main-container main-scroll">
	<div class="tabContainer">
		<div class="content">
				<div class="form_box">
					<form action="/admin/menu/save" method="post" id="form_post" name="form_post">
             		<ul class="col-lg-12 col-sm-12 col-xs-12">
						<li id="parentIdTr" style="display: none;" ><span> <@i18n.messageText code='menu.management.text.parent.menu.name'  text='父级菜单名称:' /> </span><font color="red">${parentMenu.menuName }</font></li>
						<li><span> <@i18n.messageText code='menu.management.text.menu.name'  text='菜单名称' /> </span><p>
							<input type="hidden" value="${menu.id }" name="id" id="id">
							<input type="hidden" value="${menu.parentId }" name="parentId" id="parentId">
							<input type="hidden" value="${menu.icon}" name="icon" id="icon">
							<input type="text" class="inp_set" name="menuName" id="menuName" value="${menu.menuName }" disabled="disabled">
						</p><font color="red">*</font></li>
						<li><span> <@i18n.messageText code='menu.management.text.permission.identify'  text='权限标识:' /> </span><p><input type="text" class="inp_set" name="authTab" id="authTab" value="${menu.authTab }" disabled="disabled"></p><font color="red">*</font></li>
						<li><span> <@i18n.messageText code='menu.management.text.menu.type'  text='菜单类型:' /> </span>
							<select class="select" id="menuType" name="menuType" disabled="disabled">
								<option value="1" <#if menu.menuType == '1'> selected </#if>> <@i18n.messageText code='menu.management.text.level.one.menu'  text='一级菜单' /> </option>
								<option value="2" <#if menu.menuType == '2'> selected </#if>> <@i18n.messageText code='menu.management.text.level.two.menu'  text='二级菜单' /> </option>
								<option value="3" <#if menu.menuType == '3'> selected </#if>> <@i18n.messageText code='menu.management.text.link.menu'  text='链接菜单' /> </option>
								<option value="99" <#if menu.menuType == '99'> selected </#if>> <@i18n.messageText code='menu.management.text.button.permissions'  text='按钮权限' /> </option>
								<option value="0" <#if menu.menuType == '0'> selected </#if>> <@i18n.messageText code='menu.management.text.top.menu'  text='顶级菜单' /> </option>
							</select>
						</li>
						<li><span> <@i18n.messageText code='menu.management.text.language.encoding'  text='语言编码:' /> </span><p><input type="text" class="inp_set" name="languageCode" id="languageCode" value="${menu.languageCode }" disabled="disabled"></p></li>
						<li id="menuSort" <#if menu.menuType != '3' && menu.menuType !='1'>style="display: none;"</#if>><span> <@i18n.messageText code='dictionary.management.text.dictionary.sequence'  text='排序' /> </span><p><input type="text" class="inp_set" name="sort" id="sort" value="${menu.sort}" disabled="disabled"></p></li>
						<li><span> <@i18n.messageText code='menu.management.text.usable.or.disabled'  text='是否可用:' /> </span>
							<select class="select" id="useable" name="useable" disabled="disabled">
								<option value="1" <#if menu.useable == '1'> selected </#if>> <@i18n.messageText code='business.management.select.usable'  text='可用' /> </option>
								<option value="2" <#if menu.useable == '2'> selected </#if>> <@i18n.messageText code='business.management.select.disabled'  text='不可用' /> </option>
							</select>
						</li>
						<li id="menuTr1" <#if menu.menuType != '3'>style="display: none;"</#if>><span> <@i18n.messageText code='menu.management.text.link'  text='链接:' /> </span><p><input type="text" class="inp_set" name="url" id="url" value="${menu.url }" disabled="disabled" ></p></li>
						<li id="menuTr2" <#if menu.menuType != '3'>style="display: none;"</#if>><span> <@i18n.messageText code='menu.management.text.mobile.version.or.not'  text='是否有手机版:' /> </span>
	                    	<select  class="select" id="isMobile" name="isMobile" disabled="disabled">
								<option value="2" <#if menu.isMobile == '2'>  selected </#if>> <@i18n.messageText code='common.text.false'  text='否' /> </option>
								<option value="1" <#if menu.isMobile == '1'>  selected </#if>> <@i18n.messageText code='common.text.true'  text='是' /> </option>
							</select>
	                    </li>
	               		<li id="menuTr3" <#if menu.menuType != '3'>style="display: none;"</#if>><span> <@i18n.messageText code='menu.management.text.mobile.phone.access.address'  text='手机访问地址:' /> </span><p><input type="text" class="inp_set" name="mobileUrl" id="mobileUrl" value="${menu.mobileUrl }" disabled="disabled"></li>
						<li><span> <@i18n.messageText code='menu.management.text.menu.description'  text='菜单描述:' /> </span><p><input type="text" class="inp_set" name="intro" id="intro" value="${menu.intro }" disabled="disabled"></li>
					</ul>
					
					<div id="menuTr4" <#if menu.menuType != '1' &&  menu.menuType != '2'>style="display: none;"</#if>>
						<ul class="col-lg-12 col-sm-12 col-xs-12">
						<li>
							<span><@i18n.messageText code='menu.management.text.icon'  text='图标:' /> </span>
							<input type="button" value="选择图标" id="icon" name="icon" class="inpBtn" disabled="disabled" style="margin-top:6px">
							<div class="menu_img" style="margin-left: 150px;margin-top: 10px;">
                        		<div id="iconDiv" class="sidebar-collapse font14 sidebar-border ${menu.icon}"></div>
                    		</div>
						</li>
						</ul>
					</div>
					</form>
					<ul class="col-lg-12 col-sm-12 col-xs-12">
					<@loginUserAuth key='menu_edit' ; value> <#if value =='1'>
						<li class="class_btn_li">
								<input type="button" class="saveMenuId fbtn btn_sm ensure_btn" id="saveMenuId" value="<@i18n.messageText code='common.button.save'  text='保存' />" style="display: none;">&nbsp;&nbsp;
								<input type="button" class="editMenuId fbtn btn_sm ensure_btn" id="editMenuId" value="<@i18n.messageText code='common.button.edit'  text='编辑' />" >&nbsp;&nbsp;
								<input type="button" class="quXiaoId fbtn btn_sm cancle_btn" id="quXiaoId" value="<@i18n.messageText code='common.button.quit.false'  text='关闭' />" style="display: none;">&nbsp;&nbsp;
							</li>
						</#if> </@loginUserAuth>
					</ul>	
				</div>	
		</div>
	</div>
</div>
</body>
</html>