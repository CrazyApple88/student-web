/**
 * Created by xhgx on 2017/5/8.
 */
function tableSort(options){
    this.config = {
    	sortparentClass:"",	
        sortClass:".sorting",
        callback:function(){},
        srcSort:{}
    }
    $.extend(this.config,options);
    this.sortTable();
}
tableSort.prototype = {
    sortTable:function(){
        var that = this;
        var sortName;
        var sortOrder;
        $(that.config.sortparentClass+" th[data-sortName]").click(function(){
            sortName = $(this).attr("data-sortName");
            sortOrder = $(this).attr("data-sortType");
            if(!sortOrder){
                sortOrder = "desc";
            }
            var arr=[];
            arr.push(sortName);
            arr.push(sortOrder);
            $(this).attr("class","sorting_"+sortOrder).siblings("th[data-sortName]").attr("class","sorting");
            
            if(that.config.callback){
                that.config.callback.apply(this,arr);
            }
            sortOrder = $(this).attr("data-sortType",sortOrder);
            arr[1] == "desc"? sortOrder = $(this).attr("data-sortType","asc"): sortOrder = $(this).attr("data-sortType","desc");
        });
        //记录原值
        $("th[data-sortName]").each(function(i,item){
            sortName = $(item).attr("data-sortName");
            sortOrder = $(item).attr("data-sortType");
            that.config.srcSort[sortName]=sortOrder;
        });
    },
    reloadTable:function(){
        var that = this;
        $("th[data-sortName]").each(function(i,item){
            sortName = $(this).attr("data-sortName");
            $(this).attr("data-sortType", that.config.srcSort[sortName]);
            $(item).attr("class","sorting");
        });
    }
}

