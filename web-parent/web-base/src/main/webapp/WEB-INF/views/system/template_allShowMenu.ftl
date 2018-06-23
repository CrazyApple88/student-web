<script type="text/template" id="allShowMenu">
	<%$(data).each(function(i,item){%>
	<%var childrens = item.children%>
	<%if(childrens == null || childrens.length == 0){%>
	<li class=""  id="<%=item.id%>" pId="<%=item.pId%>"  data-title="<%=item.menuName%>" data-src="<%=item.url%>">
		<a href="javascript:void(0);"  data-type="<%=item.type%>">
			 <div class='sidebar-collapse <%=item.icon%> left font14'>

              </div>
			<span class="menu-text"> <%=item.menuName%> </span>
			<!--<i class="iconfont icon-iconexpand"></i>-->
		</a>
	</li>
	<%}else{%>
	<li class="" id="<%=item.id%>" pId="<%=item.pId%>">
		<a href="javascript:void(0);">
			 <div class="sidebar-collapse <%=item.icon%> left font14">

              </div>
			<span class="menu-text"> <%=item.menuName%> </span>
			<i class="iconfont icon-iconexpand"></i>
		</a>
		<div class="banner_menu_content">
			<ul class="banner_menu_contentul" >
				<%$(childrens).each(function(j,item2){%>
				<%var childrensThree = item2.children%>
				<%if(childrensThree == null || childrensThree.length == 0){%>
				<li id="<%=item2.id%>" pId="<%=item2.pId%>" data-title='<%=item2.menuName%>' data-src='<%=item2.url%>'>
					<a href="javascript:void(0);"><%=item2.menuName%></a>
				</li>
				<%}else{%>
				<li id="<%=item2.id%>" pId="<%=item2.pId%>">
			     	  <a href="javascript:void(0);"><%=item2.menuName%><i class="iconfont icon-iconexpand icon-jianright"></i>
					  </a>
					<ul class="banner_menu_contentuls" >
						<%$(childrensThree).each(function(t,item3){%>
						<li id="<%=item3.id%>" pId="<%=item3.pId%>"  data-title='<%=item3.menuName%>' data-src='<%=item3.url%>'>
							<a href='javascript:void(0)' data-type="<%=item3.type%>">
								<%=item3.menuName%>
							</a>
						</li>
						<%});%>
					</ul>
				</li>
				<%}%>
				<%});%>
			</ul>
		</div>
	</li>
	<%}%>
	<%});%>
</script>