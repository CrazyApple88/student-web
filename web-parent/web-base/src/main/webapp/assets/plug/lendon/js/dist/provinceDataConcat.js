
// 首先这个js要基于node.js


module.exports = function(grunt) {
    grunt.initConfig({
        //获取package.json信息
        pkg: grunt.file.readJSON('package.json'),
        // concat插件配置信息(合并)
        concat: {
            options: {
                // 定义一个用于插入合并输出文件之间的字符
                separator: '\n'
            },
            dist: {
                // 将要被合并的文件
                src: ['./*.js'],
                // 合并后的JS文件的存放位置
                dest: 'dist/<%= pkg.name %>Concat.js'
            }
        },
        // uglify插件的配置信息(压缩)
        uglify: {
            options: {
                // stripBanners: true,
                banner: '/*! <%=pkg.name%>-<%=pkg.version%>.js <%= grunt.template.today("yyyy-mm-dd HH:MM:ss") %> 北京轩慧国信科技有限公司*/\n'
            },
            dist: {
                files: [{
                    // 将要被压缩的文件
                    src: '<%=pkg.name%>.js',
                    // 压缩后的JS文件的存放位置
                    dest: '<%=pkg.name%>-<%=pkg.version%>.min.js'
                },{
                    'dist/<%= pkg.name %>Concat.min.js': ['<%= concat.dist.dest %>']
                }]
            }
        },
        // watch插件（实现自动化）
        watch: {
            dist: {
                files: ['./*.js','dist/*.js'],
                tasks: ['concat', 'uglify']
            }
        }
    });
    // 告诉grunt我们将使用的插件
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-watch');
    //告诉grunt当我们在终端中输入grunt时需要做些什么（注意先后顺序）  
    grunt.registerTask('default', ['concat', 'uglify', 'watch']);
};
/*! lendon-1.0.0.js 2018-01-05 09:57:37 北京轩慧国信科技有限公司*/

var addressInit=function(n,e,t,o){var c=document.getElementById(n),i=document.getElementById(e),d=document.getElementById(t);var r=function(n){var e={};for(var t in n)e[(c=n[t]).id]=c;var o=[];for(var t in n){var c,i=e[(c=n[t]).pId];if(i){var d=i.children;d||(i.children=d=[]),d.push(c)}else o.push(c)}return o}(o);function a(n,e,t){var o=document.createElement("OPTION");n.options.add(o),o.innerHTML=e,o.value=e,o.obj=t}$.each(r,function(n,e){a(c,e.name,e)});function l(){if(d){if(d.options.length=0,-1==i.selectedIndex)return;var n=i.options[i.selectedIndex].obj;n.children&&$.each(n.children,function(n,e){a(d,e.name,null)})}}function u(){if(i.options.length=0,i.onchange=null,-1!=c.selectedIndex){var n=c.options[c.selectedIndex].obj;n.children?($.each(n.children,function(n,e){a(i,e.name,e)}),t&&(l(),i.onchange=l)):t&&(l(),i.onchange=l)}}u(),t&&l(),c.onchange=u};
/**
 * Created by xhgx on 2017/12/12.
 * 数据格式和字段
 *
 * var nodes = [
 {id:1, pId:0, name: "pNode 01"},
 {id:11, pId:1, name: "child 01"},
 {id:111, pId:11, name: "child 011"},
 {id:112, pId:11, name: "child 011"},
 {id:12, pId:1, name: "child 02"},
 {id:2, pId:0, name: "pNode 02"},
 {id:21, pId:2, name: "child 01"},
 {id:211, pId:21, name: "child 021"},
 {id:212, pId:21, name: "child 022"},
 {id:22, pId:2, name: "child 02"},
 ];
 *
 */
var addressInit = function(stairId, secondlevelId, threelevelId, dataList){
    var stair = document.getElementById(stairId);
    var secondlevel = document.getElementById(secondlevelId);
    var threelevel = document.getElementById(threelevelId);
    //数据格式处理
    function transfromData(dataList){
        var dataObj = {};
        for(var i in dataList){
            var item = dataList[i];
            dataObj[item.id] = item;
        }
        var firstList = [];
        for(var i in dataList){
            var item = dataList[i];
            var parent = dataObj[item.pId]; //获取pid的父节点
            if(parent){
                var child = parent['children'];
                if(!child){
                    parent['children'] = child = []
                }
                child.push(item);
            }else{
                firstList.push(item);
            }
        }
        return firstList;
    }
    var nodeList = transfromData(dataList);
    //添加option
    function addOptions(ele,cont,obj){
        var option = document.createElement("OPTION");
        ele.options.add(option);
        option.innerHTML = cont;
        option.value = cont;
        option.obj = obj;
    }
    $.each(nodeList,function(i,item){
       addOptions(stair,item.name,item);
    });
    function changeSecond(){
        if(threelevel){
            threelevel.options.length = 0;
            if(secondlevel.selectedIndex == -1)return;
            var data = secondlevel.options[secondlevel.selectedIndex].obj;
            if(data.children){
                $.each(data.children,function(i,item){
                    addOptions(threelevel,item.name,null);
                });
            }
        }
    }
    function changeStair(){
        secondlevel.options.length = 0;
        secondlevel.onchange = null;
        if(stair.selectedIndex == -1)return;
        var data = stair.options[stair.selectedIndex].obj;
        if(data.children){
            $.each(data.children,function(i,item){
                addOptions(secondlevel,item.name,item);
            });
            if(threelevelId){
                changeSecond();
                secondlevel.onchange = changeSecond;
            }
        }else{
            if(threelevelId){
                changeSecond();
                secondlevel.onchange = changeSecond;
            }
        }
    }
    changeStair();
    if(threelevelId){
        changeSecond();
    }
    stair.onchange = changeStair;
}














/*! provinceData-1.0.0.js 2018-01-18 17:21:39 北京轩慧国信科技有限公司*/

var dataList=[{id:1,pId:0,name:"河北省"},{id:11,pId:1,name:"保定市"},{id:111,pId:11,name:"涿州市"},{id:112,pId:11,name:"高碑店市"},{id:113,pId:11,name:"雄县"},{id:114,pId:11,name:"涞水县"},{id:12,pId:1,name:"秦皇岛市"},{id:121,pId:12,name:"北戴河区"},{id:122,pId:12,name:"昌黎县"},{id:123,pId:12,name:"抚宁县"},{id:124,pId:12,name:"海港区"},{id:13,pId:1,name:"廊坊市"},{id:2,pId:0,name:"北京市"},{id:21,pId:2,name:"市辖区"},{id:211,pId:21,name:"朝阳区"},{id:212,pId:21,name:"丰台区"},{id:213,pId:21,name:"昌平区"},{id:214,pId:21,name:"大兴区"},{id:22,pId:2,name:"县"},{id:221,pId:22,name:"密云县"},{id:222,pId:22,name:"延庆县"},{id:3,pId:0,name:"吉林省"}],nodes=[{id:1,pId:0,name:"pNode 01"},{id:11,pId:1,name:"child 01"},{id:111,pId:11,name:"child 011"},{id:112,pId:11,name:"child 011"},{id:12,pId:1,name:"child 02"},{id:2,pId:0,name:"pNode 02"},{id:21,pId:2,name:"child 01"},{id:211,pId:21,name:"child 021"},{id:212,pId:21,name:"child 022"},{id:22,pId:2,name:"child 02"}];
/**
 * Created by xhgx on 2017/12/12.
 * 数据格式和字段
 *
 * var nodes = [
 {id:1, pId:0, name: "pNode 01"},
     {id:11, pId:1, name: "child 01"},
         {id:111, pId:11, name: "child 011"},
         {id:112, pId:11, name: "child 011"},
     {id:12, pId:1, name: "child 02"},
 {id:2, pId:0, name: "pNode 02"},
     {id:21, pId:2, name: "child 01"},
         {id:211, pId:21, name: "child 021"},
         {id:212, pId:21, name: "child 022"},
 {id:22, pId:2, name: "child 02"},
 ];
 *
 */


var dataList =[
    { id:1, pId:0, name:"河北省"},
        { id:11, pId:1, name:"保定市"},
            { id:111, pId:11, name:"涿州市"},
            { id:112, pId:11, name:"高碑店市"},
            { id:113, pId:11, name:"雄县"},
            { id:114, pId:11, name:"涞水县"},
        { id:12, pId:1, name:"秦皇岛市"},
            { id:121, pId:12, name:"北戴河区"},
            { id:122, pId:12, name:"昌黎县"},
            { id:123, pId:12, name:"抚宁县"},
            { id:124, pId:12, name:"海港区"},
        { id:13, pId:1, name:"廊坊市"},
    { id:2, pId:0, name:"北京市"},
        { id:21, pId:2, name:"市辖区"},
            { id:211, pId:21, name:"朝阳区"},
            { id:212, pId:21, name:"丰台区"},
            { id:213, pId:21, name:"昌平区"},
            { id:214, pId:21, name:"大兴区"},
        { id:22, pId:2, name:"县"},
            { id:221, pId:22, name:"密云县"},
            { id:222, pId:22, name:"延庆县"},
    { id:3, pId:0, name:"吉林省"}
];
var nodes = [
    {id:1, pId:0, name: "pNode 01"},
    {id:11, pId:1, name: "child 01"},
    {id:111, pId:11, name: "child 011"},
    {id:112, pId:11, name: "child 011"},
    {id:12, pId:1, name: "child 02"},
    {id:2, pId:0, name: "pNode 02"},
    {id:21, pId:2, name: "child 01"},
    {id:211, pId:21, name: "child 021"},
    {id:212, pId:21, name: "child 022"},
    {id:22, pId:2, name: "child 02"},
];

/*var map = {};
for(var i in nodeList){
    var item = nodeList[i];
    map[item.id]= item;
}
for(var i in nodeList){
    var item = nodeList[i];
    var parent = map[item.pId];
    if(parent){
        var child = parent["children"];
        if(!child){
            parent["children"] =child = [];
        }
        child.push(item);
    }
}*/
