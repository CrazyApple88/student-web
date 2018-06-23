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
