
<script type="text/template" id="showStairMenu">
    <%$(data).each(function(i,item){%>
    <%var childrens = item.children%>
    <%if(item.levelnum === 1){%>
    <li id="<%=item.id%>" pId="<%=item.pId%>"  data-title="<%=item.menuName%>" data-src="<%=item.url%>"> <div class="sidebar-collapse <%=item.icon%> left font14">    </div><span><%=item.menuName%></span></li>
    <%}%>
    <%})%>
</script>


<script type="text/template" id="showStairMenu1">
    <%$(data).each(function(i,item){%>
    <%var childrens = item.children%>
    <%if(childrens == null || childrens.length == 0){%>
    <ul class="sidebar-menu font14">

    </ul>
    <%}else{%>
    <ul class="sidebar-menu font14">
        <li class="sidebar-menuli"><div class="sidebar-collapse <%=item.icon%> left font14">    </div><span><%=item.menuName%></span></li>
        <%$(childrens).each(function(j,item2){%>
        <%var childrensThree = item2.children%>
        <%if(childrensThree == null || childrensThree.length == 0){%>
        <li class='active' id="<%=item2.id%>" pId="<%=item2.pId%>" data-title='<%=item2.menuName%>' data-src='<%=item2.url%>'>
            <a href="javascript:void(0);" class="menu-dropdown">
                <div class="sidebar-collapse left font14">
                </div>
                <span class="menu-text"><%=item2.menuName%></span>
            </a>
        </li>
        <%}else{%>
        <li class="" id="<%=item2.id%>" pId="<%=item2.pId%>">
            <a href="javascript:void(0);" class="menu-dropdown">
                <div class="sidebar-collapse <%=item.icon%> left font14">    </div>
                <span class="menu-text"><%=item2.menuName%></span>
                <i class="iconfont icon-iconexpand right iconbgup rotateUp"></i>
            </a>
            <ul class="submenu relative" style="display: none;">
                <%$(childrensThree).each(function(t,item3){%>
                <li  id="<%=item3.id%>" pId="<%=item3.pId%>"  data-title='<%=item3.menuName%>' data-src='<%=item3.url%>'>
                    <a href="javascript:void(0)" data-type="<%=item3.type%>"> <%=item3.menuName%></a>
                </li>
                <%});%>
            </ul>
        </li>
        <%}%>
        <%});%>
    </ul>
    <%}%>
    <%})%>
</script>