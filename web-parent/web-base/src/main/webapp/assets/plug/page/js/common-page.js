/**
 * Created by zohan on 2016/10/27.
 */

/**
 * 通用数据分页列表信息
 * @param opt
 * @constructor
 */
function CommonPage(opt) {
    var options = {
        url: '', //分页数据的url
        flag: true,
        pageBtnDiv:null, //分页信息的父元素
        pageBtnClass:"pageBtnStyle",
        limit: 10, //默认分页个数
        totalpageflag:true, //是否显示总页数
        skipflag:true, //是否显示快速跳转
        setlimitpage:true,//是否显示可以选择每页条数的按钮
        //分页数据信息
        data: {
            pageNo: 1,
            pageSize: 10,
            totalPages: 0
        },
        /**
         * 数据加载前要执行的函数
         */
        beforLoadData:function(){

        },
        /**
         * 加载数据后执行的函数
         */
        afterLoadData:function(){

        }
    };
    this.options = $.extend(options, opt);
    if(this.options.setlimitpage){//只有用可选每页行数时才执行下面的
	    var ymPageSize = $("#limitpagenum").val();//获取分页的每页显示条数
	    if(ymPageSize != undefined && ymPageSize != 'undefined'){
	    	//将设置的值插入到data中，后台需要查询
	    	this.options.data.pageSize = ymPageSize;
	    }
    }
    this.listData = null;
    this.init();//初始化并加载数据
   /* this.ininttag();*/
    return this;
};

/**
 * 初始化相关操作，并加载数据
 */
CommonPage.prototype.init = function () {
    this.loadData();
};

/**
 * 有变化，需要子类重写此方法
 */
CommonPage.prototype.getQueryParameter = function () {
    return this.options.data;
};

/**
 * 对外提供设置第几页的功能
 * @param pageNo
 * @returns {*}
 */
CommonPage.prototype.setPageNo = function (pageNo) {
    return this.setParameter("pageNo",pageNo);
};
/**
 * 设置每页显示多少条数据
 * @param pageSize
 * @returns {*}
 */
CommonPage.prototype.setPageSize = function (pageSize) {
    return this.setParameter("pageSize",pageSize);
};
/**
 * 设置总页数
 * @param totalPages
 * @returns {*}
 */
CommonPage.prototype.setTotalPages = function (totalPages) {
    return this.setParameter("totalPages",totalPages);
};

/**
 * 设置总条数
 * @param pageCount
 * @returns {*}
 */
CommonPage.prototype.setPageCount = function (pageCount) {
    return this.setParameter("pageCount",pageCount);
};

/**
 * 增加查询参数
 * @param name
 * @param value
 * @returns {*}
 */
CommonPage.prototype.setParameter = function (name,value) {
    return this.options.data[name] = value;
};


/**
 * 加载数据
 * @param callBack
 */
CommonPage.prototype.loadData = function (callBack) {
    var _this = this,
        sign;
    //数据加前要执行的函数
    if(_this.options.beforLoadData){
        _this.options.beforLoadData.apply(this,[]);//this代表：
    }
    $.ajax({
        url: _this.options.url,
        dataType: "json",
        type: 'post',
        async: false,
        data: _this.getQueryParameter(),
        success: function (result) {
            if (result.code == '1') {
            	result = result.data;
                _this.setPageNo(result.pageNo);
                _this.setPageSize(result.pageSize);
                _this.setTotalPages(result.pageTotal);
                _this.setPageCount(result.pageCount);
                if (_this.options.callBack) {
                    _this.options.callBack.apply(this, [result.result]);
                }
                if (callBack) {
                    callBack.apply(this, [result.result]);
                }
            }
            if(_this.options.afterLoadData){
                _this.options.afterLoadData.apply(this,[sign]);
            }

            if(_this.options.pageBtnDiv){
            	
                _this.initPageButton();
            }
            _this.options.flag = true;
            _this.listData = result.result;
        },
        error:function(){
            sign='error';
            if(_this.options.afterLoadData){
                _this.options.afterLoadData.apply(this,[sign]);
            }
        }
    });
};

/**
 * 下一页
 * @returns {boolean}
 */
CommonPage.prototype.nextPage = function () {
    var _this = this;
    var next = ++_this.options.data.pageNo;
    if (next > _this.options.data.totalPages) {
        return false;
    }
    _this.loadData();
};

/**
 * 跳到制定页面
 * @param pageNo
 * @returns {boolean}
 */
CommonPage.prototype.gotopage = function (pageNo) {
    
    var _this = this;
    if (pageNo >_this.options.data.totalPages) {
        return false;
    }
    _this.setPageNo(pageNo);
    _this.loadData();
};
/**
 * 快速跳转到指定页面
 */
CommonPage.prototype.gotoskip = function(pageNo){
	var _this = this;
	 if (pageNo >_this.options.data.totalPages) {
        return false;
    }
	 _this.setPageNo(pageNo);
	 _this.loadData();
};
/**
 * 前台限制数据条数
 * 
*/
CommonPage.prototype.limitpage = function(pageNo){
	var _this = this;
	var options = $("#limitpagenum option:selected").val();
	_this.setPageSize(options);
	_this.setPageNo(pageNo);
	_this.loadData();
	 
};

/**
 * 初始化分页按钮
 * @returns {boolean}
 */
CommonPage.prototype.initPageButton = function () {
    var _this = this;
    //没有设置就不显示
    if(!_this.options.pageBtnDiv)  return;
    $(_this.options.pageBtnDiv).empty();
    $(_this.options.pageBtnDiv).siblings(".fontpage").remove();
    $(_this.options.pageBtnDiv).siblings(".skipcont").remove();
    $(_this.options.pageBtnDiv).siblings(".limitpagenum").remove();
    
    if(!$(_this.options.pageBtnDiv).hasClass( _this.options.pageBtnClass)){
        $(_this.options.pageBtnDiv).addClass(_this.options.pageBtnClass);
    }
    var pageNo = _this.options.data.pageNo,totalPages = _this.options.data.totalPages,pageCount =  _this.options.data.pageCount;
    var index  = _this.options.limit%2==0?_this.options.limit/2:(_this.options.limit+1)/2;
    var lastPage = totalPages>0?totalPages:1;
    if(pageNo+index > _this.options.limit  && pageNo <= totalPages ){
        lastPage= index+pageNo<=totalPages?index+pageNo:totalPages;
    }else if(pageNo < _this.options.limit  && totalPages > _this.options.limit){
        lastPage=_this.options.limit;
    }
    var limit =_this.options.limit-1;
    if(lastPage<=limit){
        limit = lastPage-1;
    }
    //language
    
    var homepage = UDP.Public.getmessage('page.pageMessage.homepage',"首页");
    var trailerpage = UDP.Public.getmessage('page.pageMessage.trailerpage',"尾页");
    var skip = UDP.Public.getmessage('page.pageMessage.skip',"跳转");
    if(_this.options.totalpageflag){
    	if(typeof(pageNo)=="undefined" || typeof(pageCount)=="undefined"){
            pageNo = 1;
            pageCount = 0;
        }
        var currentpage = UDP.Public.getmessage('page.pageMessage.currentpage',"当前第 "+pageNo+" 页 / 总共 "+pageCount+" 条",[pageNo,pageCount]);
        $(_this.options.pageBtnDiv).before($("<span class='fontpage'>"+currentpage+"</span>"));
    }

  //select限制数据条数
	if(this.options.setlimitpage){
		var pageStr ="<select class='limitpagenum' id='limitpagenum'>";

		if(_this.options.data.pageSize == 10){
			pageStr += "<option value='10' selected='selected' >10</option>"
		}else{
			pageStr += "<option value='10'>10</option>";
		}
		if(_this.options.data.pageSize == 20){
			pageStr += "<option value='20' selected='selected' >20</option>"
		}else{
			pageStr += "<option value='20'>20</option>";
		}
		if(_this.options.data.pageSize == 30){
			pageStr += "<option value='30' selected='selected' >30</option>"
		}else{
			pageStr += "<option value='30'>30</option>";
		}
		if(_this.options.data.pageSize == 50){
			pageStr += "<option value='50' selected='selected' >50</option>"
		}else{
			pageStr += "<option value='50'>50</option>";
		}
		if(_this.options.data.pageSize == 100){
			pageStr += "<option value='100' selected='selected' >100</option>"
		}else{
			pageStr += "<option value='100'>100</option>";
		}
		pageStr +="</select>";
		var str = $(pageStr);
    	$(this.options.pageBtnDiv).before(str);
    }else{//如果不显示页数选择项，则给一个隐藏的input 值为10
    	var pageStr ="<input type='hidden' id='limitpagenum' value='10'>";
    	$(this.options.pageBtnDiv).before(pageStr);
    };

    //快速跳转
    if(this.options.skipflag){
    	$(this.options.pageBtnDiv).before($("<div class='skipcont'><input type='text' class='skippage'/><input type='submit' class='skipBtn' id='skipBtn' value='"+skip+"'/> </div>"));
    }

    //首页
    $(_this.options.pageBtnDiv).append($("<span class='pageNum pageFirLat pageFirst' page-data='1'>"+homepage+"</span>"));
    var prev=1;

    if(pageNo>1){
        prev = pageNo - 1;
    }
    //前一页
    $(_this.options.pageBtnDiv).append($("<span class='pageNum' page-data='"+prev+"'><i class='bgPreNex'>«</i></span>"));
    for(var i = lastPage - limit;i<=lastPage;i++){
        if(i == pageNo){
            $(_this.options.pageBtnDiv).append($("<span class='pageNum active' page-data='"+ i +"'>"+i+"</span>"));
        }else{
            $(_this.options.pageBtnDiv).append($("<span class='pageNum' page-data='"+ i +"'>"+i+"</span>"));
        }
    }
    var next=pageNo+1;
    if(next>totalPages){
        next = lastPage
    }
    //下一页
    $(_this.options.pageBtnDiv).append($("<span class='pageNum' page-data='"+next+"'><i class='bgPreNex'>»</i></span>"));

    //最后一页
    $(_this.options.pageBtnDiv).append($("<span class='pageNum pageFirLat pageLast' page-data='"+totalPages+"'>"+trailerpage+"</span>"));
    
    $(_this.options.pageBtnDiv).find("span").on('click',function(){
       var p = $(this).attr("page-data");
        _this.gotopage(p);
    });
    
    //快速跳转
   if(_this.options.skipflag){
    	$("#skipBtn").on("click",function(){
    		var pageValue = $(".skipcont .skippage").val();
    		var rel = /^[1-9]\d*$/g;
    		if(!rel.test(pageValue)){
    			throw  "格式不正确!";
    		}
    		_this.gotoskip(pageValue);
    	})
    }
   if(_this.options.setlimitpage){
	   $("#limitpagenum").change(function(){
		   _this.limitpage();
	   });
   }
};


(function(win) {
    if (win["UDP"]) {
        win["UDP"].CommonPage = CommonPage;
    } else {
        win.UDP = {
        	CommonPage: CommonPage
        }
    }
    var page = {
    		"fileType":"plug",
    		"filename":"page"
    }
    win["UDP"].Public.PlugList(page);
    
})(window);








