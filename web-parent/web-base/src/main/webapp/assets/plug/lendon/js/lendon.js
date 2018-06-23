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













