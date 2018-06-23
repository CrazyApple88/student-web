<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>

		<title>查询结果</title>
        <meta charset="utf-8"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<#include "/system/common_css.ftl">
		<#include "/system/common_js.ftl">




		<style type="text/css">
            .table-headWrapper2 {
                background: linear-gradient(to bottom, #F9F9F9 0%, #EAEAEA 100%) repeat
                scroll 0 0 rgba(0, 0, 0, 0);
                border-top: 2px solid #BB0000;
                box-shadow: 0 0 2px #BBBBBB;
                position: relative;
				width:100%;
                z-index: 1;
            }
		</style>
		<script type="text/javascript">
			$(function() {
				$("#exportBtn").on('click',function(){
                    $("[name=json]").val($("#allTable").html());
					$("#exportForm").submit();
				});





			});

		</script>

	</head>
<body>
		<#--<div class="">-->
			<#--<div class="">-->
				<#--<div class="">-->
					<form id="exportForm" action="${context}/admin/simpleQuery/exportFile"  method="post">
						<input name="json" type="hidden">
						<span class="help-block"></span>
						<span class="help-block"></span>
						<span class="help-block"></span>
						<div>
							<#if queryResult.isSuccess== true>
								<span class="help-block"> <@i18n.messageText code='simple.query.text.query.failed'  text='查询失败:' /> ${queryResult.errorMsg}</span>
							<#else>
                                <input type="button"  id="exportBtn"  class="common_btn active_btn" value="<@i18n.messageText code='simple.query.text.export.file'  text='导出文件' />">
								<@i18n.messageText code='simple.query.text.query.result'  text='查询结果:' /> 共${queryResult.totalCount}条数据，耗时${queryResult.simpleQuery.lastUseTime}毫秒

							</#if>
						</div>




						<div class=""  >
							<table class="table table-bordered table-hover" id ="allTable">
								<thead>
									<tr>
                                      <#list queryResult.keys as key>
                                      <th>${key}</th>
                                      </#list>
									</tr>
								</thead>
								<tbody>
								<#list queryResult.result as map>
								    <tr>
									<#list queryResult.keys as key>
                                    <td>${map[key]}</td>
									</#list>
                                    </tr>
								</#list>
								</tbody>
							</table>
						</div>
						<#--<div id="page" class="pagination pagination-right pagination-small" ></div>			-->

					</form>
				<#--</div>-->
			<#--</div>-->
		<#--</div>-->
		<script type="text/javascript">
			$(function() {

			});		
			
		</script>
	</body>	
	
</html>
