/**
 注意：
 1、该插件依赖于 public-method.js 用之前要引用
 2、要将对应的 template 模板写在 script 标签里

 使用案例：
 var entityId = "1234567890";                   //业务的id
 $(".file_list_cont").UploadFile({
         btnId:"#upload",
         url:"/file/api/upload/"+entityId,           //上传地址
         listUrl:"/file/api/list/"+entityId,         //文件列表地址
         delUrl:"/file/api/del/",                    //删除地址
         fileType:"txt,zip,png,jpg",                 //允许上传的文件类型
         fileSize:10*1024,                           //单位以kb为基础
         template:function(){                        //加载模板（需引用public-method.js）
             var str=UDP.Public.template($("#fileTemplate").html(),{variable: "data"})(data);
             $("#uploadfile_list").append(str);
         },
         uploadsuccess:function(data){       //上传成功的回调
            alert("上传成功")
         },
         uploaderror:function(errMsg){       //上传失败的回调
            alert(errMsg);
         }
     });
 * */



;(function($){
    $.fn.UploadFile= function(opts) {
        return attachment.init(opts);//初始化
    };
    var errorNetwork = "";//网络异常无法调用方法获取键名，所以定义一个全局变量，在初始化时获取键值，在网络异常时调用
    var attachment = {
        _file:null, 															//上传按钮对应的file
        btnId:"#upload",                                                      //上传按钮的id
        uploadUrl:"",                                                                //服务器请求地址
        listUrl:"",                                                            //列表地址
        delUrl:"",                                                              //删除地址
        fileType:"",                                                            //文件类型
        fileSize:"20M",                                                         //文件最大限制
        formParams:{},
        fileId:"fileId",                                                        //上传文件的id
        formId:'formId',                                                         //上传按钮所属的form id
        businessId:"",                                                          //业务id
        filepath:"upload",
        moreFile:false,                                                         //是否多文件上传
        uploadsuccess:function(data){},                                         //上传成功的回掉
        uploaderror:function(errMsg){},                                         //上传失败的回调
        templateInit:function(tpldata){},                                                   //使用template的模板函数
        //初始化
        init:function(opt){
            var that = this;
            $.extend(this,opt);
            var uploadTemplate='<div id="file_box" style="display:none">'
                +'<input type="file"  id="fileId"/>'
                +'</div>';
            var $this= $(that.btnId);
            $this.after(uploadTemplate);

            var filediv=$this.siblings("#file_box");
            var fileinput=filediv.find("input[type=file][id=fileId]")[0];
            this._file=fileinput;
            $this.on("click",function(){
                $(fileinput).trigger("click");
            });
            //绑定上传自动触发事件
            $(fileinput).change(function(){
                that.uploadevent(fileinput);
            });

            that.initList();//初始化文件列表
            //网络异常无法调用方法获取键名，所以定义一个全局变量，在初始化时获取键值，在网络异常时调用
           
            errorNetwork = UDP.Public.getmessage("upload.errorMsg",that.filepath,"当前网络异常,请稍后再试!")
            return this;
        },
        //上传触发的事件
        uploadevent:function(fileinput){
            var that = this;
            var files=fileinput.files;
            //没值时 什么也不执行
            if($(fileinput).val()==""){
                return;
            }
            //异步
            window.setTimeout(function(){
                var fileSize = fileinput.files[0].size;
                for(var i=0;i<files.length;i++){
                    var isuploadFormat = that.limitFormat(fileinput);
                    var isuploadSize = that.limitFileSize(fileSize);
                    if(!isuploadFormat || !isuploadSize || !isuploadFormat && !isuploadSize){           //判断文件大小、类型是否符合条件
                        delete fileinput.files[i];
                        return;
                    }
                    that.upload(fileinput);
                }
            },10);
        },
        //文件大小限制
        limitFileSize:function(size){
            var that = this;
            var limitsize = (parseFloat(that.fileSize))*1024 ;  //限制的文件大小
            UDP.Public.Bytetransform(size);
            if(size> limitsize){
                var errMsg = UDP.Public.getmessage("upload.sizeerrMsg",that.filepath,"文件太大无法上传！");
                that.uploaderror(errMsg);
                return false;
            }
            return  true;
        },
        //文件类型限制
        limitFormat:function(file){
            var that = this;
            var fileName = file.files[0].name;
            var Index = fileName.indexOf(".");
            var fileExt=fileName.substring(Index+1);
            var typeflag = that.fileType.indexOf(fileExt);     //上传文件的后缀与参数设置的去匹配
            if(typeflag < 0){
                var errMsg = UDP.Public.getmessage("upload.formaterrMsg",that.filepath,"上传格式不正确！");
                that.uploaderror(errMsg);
                return false;
            }
            return true;
        },
        //进度条
        uploadprogress:function(){
            //进度条的模板
            var uploadpro = '<li class="filepro_cont"><div class="filesize"><span class="upload_num">0M</span> / <span class="file_num">0M</span></div>'
                +'<div class="pregress_cont"> <div class="upload_progress"></div></div></li>';
            $("#uploadfile_list").prepend(uploadpro);
        },
        //文件上传
        upload:function(file){
            var that = this;
            var uploadUrl = that.uploadUrl;   //上传地址
            var formData = new FormData();
            formData.append("file",file.files[0]);
            that.uploadprogress();
            var xhr = new XMLHttpRequest();
            //监听进度
            xhr.upload.addEventListener("progress",function(ev){
                var filenum = UDP.Public.Bytetransform(ev.total);                //总的文件大小
                var uploadnum = UDP.Public.Bytetransform(ev.loaded);             //下载的文件大小
                var filrprogress = (ev.loaded/ev.total)*100;       //进度条的百分比
                $(".upload_num").html(uploadnum);
                $(".file_num").html(filenum);
                $(".pregress_cont").find(".upload_progress").css("width",filrprogress+"%");
            },false);

            //上传成功
            xhr.addEventListener("load",function(ev){
                var data = ""
                var str   = ev.target.responseText;
                if (typeof str == 'string') {
                    try {
                        data=JSON.parse(str);
                    } catch(e) {
                        alert("上传失败，请重试!");
                        $(".filepro_cont").remove();
                        return false;
                    }
                }
                if(data["code"]==="1") {
                    var listpanel = that.fileListtemplate(data.data[0]);
                    $("li[data-type]").remove();
                    $("#uploadfile_list").append(listpanel);
                    //绑定删除事件
                    $("[del]","#uploadfile_list").on("click",function(){
                        that.delFile($(this).attr('del'));
                    });
                    //绑定下载事件
                    $("[load]","#uploadfile_list").on("click",function(){
                        that.loadFile($(this).attr('fileurl'));
                    });
                }else if(data["msg"] === "nologin"){        //msg:后台处理的登录异常字段（正常应该是在error里面处理401）
                    var errorMsg = UDP.Public.getmessage("upload.loaderrorMsg.loginerrorMsg",that.filepath,"登陆异常，请重新登录!");
                    that.uploaderror(errorMsg);
                    $(".filepro_cont").remove();
                    return false;
                }else{                                      //其他的
                    var errorMsg = UDP.Public.getmessage("upload.loaderrorMsg.othererrorMsg",that.filepath,"操作失败，请重试!");
                    that.uploaderror(errorMsg);
                    $(".filepro_cont").remove();
                    return false;
                }
                // 上传成功后，将进度条隐藏
                $(".filepro_cont").remove();
                that.uploadsuccess(data);
            },false);
            //上传错误

            xhr.addEventListener("error", function(progressEvent){
                var  targetXHR=progressEvent.currentTarget;
                var targetXhrStatus=targetXHR.status;
                if(targetXhrStatus == 0){
                    that.uploaderror(errorNetwork);
                    $(".filepro_cont").remove();
                }else{
                    var errMsg="file:upload error!";
                    that.uploaderror(errMsg);
                    $(".filepro_cont").remove();
                }
            }, false);//end 上传 失败
            //发送数据
            xhr.open("post", uploadUrl,true);
            var fileNmae = file.files[0].name;
            xhr.setRequestHeader("X_FILENAME", unescape(encodeURIComponent(fileNmae)));
            xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
            xhr.send(formData);
        },
        //文件列表的模板
        fileListtemplate:function(data,callBack){
            var that = this;
            that.templateInit(data);
        },
        //初始化文件列表
        initList:function(){
            var listurl = this.listUrl;
            var that = this;
            var datajson="";
            $.ajax({
                url:listurl,
                type:"post",
                async:false,
                dataType:"json",
                success:function(data){
                    // alert(1234);
                    //无数据的情况
                    if(data.data == ""){
                        that.noFiledata();
                    }
                    var result = data.code;//code:状态
                    if(result == "1"){
                        var datalist = data.data;
                        that.templateInit(datalist);
                        //文件删除
                        $("[del]","#uploadfile_list").on("click",function(){
                            that.delFile($(this).attr('del'));
                        });
                        //文件下载
                        $("[load]","#uploadfile_list").on("click",function(){
                            that.loadFile($(this).attr('fileurl'));
                        });
                    }
                    return datajson = data;
                }
            });
            return datajson;
        },
        //删除文件
        delFile:function(fileid){
            var that = this;
            var delurl = that.delUrl+"/"+fileid
            $.ajax({
                url:delurl,
                type:"post",
                async:false,
                dataType:"json",
                success:function(data){
                    var result = data.code;
                    if(result == "1"){
                        $("#file"+fileid).fadeOut(function(){
                            $("#file"+fileid).remove();
                            if($("#uploadfile_list li").length == 0){
                                that.noFiledata();
                            }
                        });
                    }
                }
            });
        },
        //文件下载
        loadFile:function(loadurl){
            window.location = loadurl;
        },
        //无数据的情况
        noFiledata:function(){
        	var that = this;
            var str = '<li data-type="no-datas">'+UDP.Public.getmessage("upload.nodataMsg",that.filepath,"暂无数据")+'</li>';
            $("#uploadfile_list").append(str);
            return str;
        }
    };
    (function(win) {
        if (win["UDP"]) {
            win["UDP"].Uploadfile = $.fn.UploadFile;
        } else {
            win.UDP = {
            	Uploadfile: $.fn.UploadFile
            }
        }
        
        var upload = {
        		"fileType":"plug",
        		"filename":"upload"
        }
        win["UDP"].Public.PlugList(upload);
        
    })(window);
})(jQuery);