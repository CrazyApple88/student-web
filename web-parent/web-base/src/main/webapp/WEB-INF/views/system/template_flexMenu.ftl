<script type="text/template" id="flexMenu">
    <%$(data).each(function(i,item){%>
    <%var childrens = item.children%>
    <%if(childrens == null || childrens.length == 0){%>
    <li class="active" id="<%=item.id%>" pId="<%=item.parentId%>"  data-title="<%=item.menuName%>" data-src="<%=item.url%>">
        <a href="javascript:void(0);" data-type="<%=item.type%>">
            <div class="sidebar-collapse <%=item.icon%> left font14">
                
            </div>
            <span class="menu-text"><%=item.menuName%></span>
        </a>
    </li>
    <%}else{%>
    <li id="<%=item.id%>" pId="<%=item.parentId%>">
        <a href='javascript:void(0);' class='menu-dropdown' data-type="<%=item.type%>">
            <div class='sidebar-collapse <%=item.icon%> left font14'>
                
            </div>
            <span class='menu-text'><%=item.menuName%></span>
    	    <i class="iconfont icon-zhankai"></i>
        </a>
        <ul class='submenu relative'>
            <%$(childrens).each(function(j,item2){%>
            <%var childrensThree = item2.children%>
            <%if(childrensThree == null || childrensThree.length == 0){%>
            <li class='active'id="<%=item2.id%>" pId="<%=item2.parentId%>" data-title='<%=item2.menuName%>' data-src='<%=item2.url%>'>
                <a href='javascript:void(0)' data-type="<%=item2.type%>"><%=item2.menuName%></a>
            </li>
            <%}else{%>
            <li id="<%=item2.id%>" pId="<%=item2.parentId%>">
                <a href='javascript:void(0)' class='menu-three' data-type="<%=item2.type%>">
                    <span class='menu-text'><%=item2.menuName%></span>
                    <i class="iconfont icon-zhankai"></i>
                </a>
                <ul class='thrsubmenu'>
                    <%$(childrensThree).each(function(t,item3){%>
                    <li class='active' id="<%=item3.id%>" pId="<%=item3.parentId%>"  data-title='<%=item3.menuName%>' data-src='<%=item3.url%>'>
                        <a href='javascript:void(0)' data-type="<%=item3.type%>">
                            <%=item3.menuName%>
                        </a>
                    </li>
                    <%});%>
                </ul></li>
            <%}%>
            <%});%>
        </ul></li>
    <%}%>
    <%});%>
</script>