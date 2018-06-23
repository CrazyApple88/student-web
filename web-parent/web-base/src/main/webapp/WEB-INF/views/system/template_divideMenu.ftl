
<script type="text/template" id="divideMenu">
    <%$(data).each(function(i,item){%>
    <%var childrens = item.children%>
    <%if(item.levelnum === 1){%>
    <li  id="<%=item.id%>" pId="<%=item.pId%>"  data-title="<%=item.menuName%>" data-src="<%=item.url%>">
        <a href="javascript:void(0);" data-type="<%=item.type%>">
            <div class="sidebar-collapse <%=item.icon%> left font14">

            </div>
            <span class="menu-text"> <%=item.menuName%> </span>
        </a>
    </li>
    <%}%>
    <%})%>
</script>

<script type="text/template" id="divideMenu1">
    <%$(data).each(function(i,item){%>
    <%var childrens = item.children%>
    <%if(childrens == null || childrens.length == 0){%>
    <div class="switchover">
        <div class="switchover_div">
        </div>
    </div>
    <%}else{%>
    <div class="switchover">
        <div class="switchover_div">
            <ul class="sidebar-menu font14" id="sidebar-mneu">
                <%$(childrens).each(function(j,item3){%>
                <%var childrensThree = item3.children%>
                <%if(childrensThree == null || childrensThree.length == 0){%>
                <li id="<%=item3.id%>" pId="<%=item3.pId%>" data-title='<%=item3.menuName%>' data-src='<%=item3.url%>'>
                    <a href="javascript:void(0);">
                        <span class="menu-text"><%=item3.menuName%></span>
                    </a>
                </li>
                <%}else{%>
                <li id="<%=item3.id%>" pId="<%=item3.pId%>">
                    <a href="javascript:void(0);" class="menu-dropdown">
                        <span class="menu-text"><%=item3.menuName%></span>
                        <i class="iconfont icon-iconexpand right iconbgup rotateUp"></i>
                    </a>
                    <ul class="submenu relative" style="display: none">
                        <%$(childrensThree).each(function(t,item4){%>
                        <li id="<%=item4.id%>" pId="<%=item4.pId%>"  data-title='<%=item4.menuName%>' data-src='<%=item4.url%>'>
                            <a href="javascript:void(0)" data-type="<%=item4.type%>">
                                <%=item4.menuName%>
                            </a>
                        </li>
                        <%});%>
                    </ul>
                </li>
                <%}%>
                <%});%>
            </ul>
        </div>
    </div>
    <%}%>
    <%})%>
</script>