/**
 * 上传附件扩展
 * $("#btn").relinkUpload({
 * 
 * });
 * 
 * @param $
 */
(function( $ ){
	//添加元素元素扩展
	 $.fn.relinkUpload= function(opts) { 
		 var _this=this;
		 var $this=$(this);
		return attachment.init($this,opts);//初始化
	 };  
	
	 //初始化附件上传
	 var attachment={
			 $btn:null, //上传按钮
			 _file:null, //上传按钮对应的file
			 file_queue:[], //上传中文件队列
			 options:{
				 tenantId : '', //租户id
				 attachEntityId : '', //业务表关联id
				 attachEntityType:'', //关联业务表类型
				 url: ctx+'/api/c-bin/file/upload', //上传文件的后台处理地址
				 del_url:ctx+'/api/c-bin/file/delete', //删除url
				 down_url:ctx+'/api/c-bin/file/download', //下载url
				 list_url:ctx+'/api/c-bin/file/items',  // 回显 附件列表
				 valid_url:ctx+'/api/c-bin/file/verifyUp', //后台验证上传文件地址	
				 multiple : false, // 是否可以多个文件上传
				 btnCss:'relinkUploadBtn', //上传按钮的样式
				 btnText:'上传文件',	//上传文件按钮 的文本值
				 formId:'uploadFilesForm', //上传文件所属form的 id
				 formParams:{},	//额外表单要提交的数据
				 fileId:'uploadFiles', // 上传封装的 原来 真实 input  file 的 id 
				 fileName:'file', // 上传封装的 原来 真实 input  file 的  name
				 maxSize:1024*1024*20, //默认文件最大20M
				 unexts: ['bat','exe','sh'], //不支持上传格式
				 fileListCss:'fileList', //指定文件列表显示所在div 的样式 
				 loadListCss:'loadFileList', //加载已上传文件回显的列表 容器 div 样式 css
				 isShowLoadList:true, //是否显示回显列表
				 isUploadDIY:false,	//上传成功后会 根据该参数 自主定义显示格局
				 remove:function(file){},
				 initListCallback:function(fileList){ }, //文件列表初始化回调
				 uploading:function(file,totalSize,uploadSize,factor){}, //回调上传中进度回调显示
				 uploaded:function(file,attachment){}, //上传完成后回调
				 beforeupload:function(file){},	//文件上传之前回调
				 moment:function(file,step){}, //上传步骤
				 uploadError:function(file,data,errMsg){}, //文件上传失败会 有回掉	
				 uploadSuccess:function(file,data){}, //文件上传成功会 有回掉	
				 newUploadCallback:function(file){}, //新建一个上传线程任务回调
				 nothingUploadCallback:function(file){}, //当前上传组件 所有上传文件任务均已完成 会发送回调
				 verifyUploadError:function(file, err){}, //后台验证错误信息输出
				 uploadTaskEndCallback:function(file){}, //上传任务结束
				 
				 maxLimit:function(file){
					 //文件被限制，不上传此文件
					var size =this.options.maxSize/1024/1024;
					 alert("文件："+file.name+" 超过"+size+"M的限制！");
				 },
				 
				
				 unextCallback: function(file){	//对不支持的文件做提醒回调
					 alert("不支持该类型文件上传");
				 },
				 
			 }, //end options 变量
			 
			 
			//step值表示    -1:校验失败      0:  准备     1:校验成功     2:上传中         3:等待完成      4:最终完成ok
			 steps: [-1,0,1,2,3,4] ,			//这是上传6个步骤的阶段标识
			 
			 // 初始化
			 init:function($this,opts){
				
				 //定义 初始化里的 系统配置
				 this.options=$.extend(this.options,opts);//end options
				 var _options=this.options;
				 var _attachment=this;
				 this.$btn=$this;
				 
				 //***********************************下面执行初始化**************************
				 var this_multiple='';//是否可以同时上传多个
				 if( _options.multiple){
					 this_multiple="multiple";
				 }
				 //初始化页面显示
				 var uploadTemplate='<div  data-type="'+ _options.formId+'" style="display:none"  >'
	    				+'<input type="file"  data-type="'+_options.fileId+'"  name="'+_options.fileName+'"   '+this_multiple+'/>'
	    				+_options.btnText
	        			+'</div>';
				 $this.after(uploadTemplate);
				 $this.attr("class",_options.btnCss);//设置按钮样式
				 
				 //初始化对应file 值
			  	 var filediv=$this.siblings("div [data-type="+_options.formId+"]");
			  	 var fileinput=filediv.find("input[type=file][data-type="+_options.fileId+"]")[0];
				 this._file=fileinput;
				 
				 $this.on("click",function(){
					$(fileinput).trigger("click");
				 });
				 
				 
				 //绑定上传自动触发事件
				  $(fileinput).change(function(){
					  _attachment.toUpload();
				  });
				  
				  //初始化默认加载已上传的文件
				  _attachment.list();
				  return this;
				 
			 },  //end init function
			 
			 
			//判断当前文件是否支持上传
			 judgeCanUpload :function(file){
				 //获取定义的配置对象信息
				 var _options=this.options;
				 var _attachment=this;
				 var unexts=_options.unexts;
				 
				 //1
				 //判断文件大小
				 if(file.size !=undefined && _options.maxSize<file.size){
					 _options.maxLimit.apply(_attachment,[file]);
					 return false;
				 }
				 
				//对不支持的文件格式限制
			  	var fileName=file.name;
			  	var lastPIndex=fileName.lastIndexOf('.');
			  	if(lastPIndex==-1){
			  		_attachment.options.unextCallback(file);
					return false;
				}
			  	
			  	//格式不支持
			  	var fileExt=fileName.substring(lastPIndex+1).toLowerCase();
			  	for(var i=0 ;i<unexts.length;i++){
			  		if(fileExt==unexts[i].toLowerCase()){
			  			_attachment.options.unextCallback(file);
			  			return false;
			  		}
			  	}
				 
				 return true;
			 },
			 
			 //定义上传 触发的事件
			 toUpload:function(){
				 var _options=this.options;
				 var _attachment=this;
				 var $btn=_attachment.$btn;
				 //每次触发上传事件 已当前id 为基础 id
				 var nowtime=new Date().getTime(); 
			  	 //var form=document.getElementById(_options.formId);
			  	 //var files=form.file.files;
				 
				 //获取该上传按钮对应的 input file 值
			  	 var fileinput=_attachment._file;
			  	 var files=fileinput.files;
			  	
			  	//没值时 什么也不执行
			  	if($(fileinput).val()==""){
			  		 return;
			  	 }
			  	 
			  	//异步的
			  	 window.setTimeout(function(){
			  		 for(var i=0;i<files.length;i++){
			  			 var file=files[i];
					  	 var isCanUploadFile=_attachment.judgeCanUpload(file);
					  	 if(!isCanUploadFile){
					  	 	delete fileinput.files[i];
					  	 	continue;
					  	 }
					  	 
					  	 //设置文件id
					  	 file.id=nowtime+""+i;//设置文件id
					  	 
					  	 //后台回调
					  	 _options.moment(file,_attachment.steps[1]);
					  	 //预先后台校验数据
						 var validOk=_attachment.verifyUploadFile(file);
						 if(!validOk){
							delete fileinput.files[i];
						  	 continue;
						 }
						 
					  	 //发起后台上传
					  	 _attachment.loadPreviewFile(file);
					  	 _attachment.uploadFile(file);

					 }

					 //设置选中文件为空，可以再次选择
					 $(fileinput).val("");

			  	},10);

			  
			 }, //end 触发上传 toUpload事件
			 
			 
			 //预览单个文件
			 loadPreviewFile:function(file){
				var _options=this.options;
				var _attachment=this;
				//id编号
				var fileId=file.id; //获取文件id
				var fileType=file.type; //获取文件类型 如果是图片 要显示预览效果
				var previewFileHtml=_attachment.getPreviewHtmlTemplate(file);
				$('.'+_options.fileListCss).append(previewFileHtml);//显示预览图
					
				//如果是图片 要显示预览的效果
				if(fileType.indexOf("image")!=-1){
					//预览图像
					var image = document.createElement("img");
					image.src="";
					image.onload=function(){
						_attachment.render(this);//渲染压缩文件
					}
					var imgData=null;
					var fr = new FileReader();
					fr.readAsDataURL(file); //Base64
					$(fr).load(function(){
						imgData=this.result;
						image.src=imgData;
					});
				}
				//end 预览图像
				$("#ru-details_"+fileId).append(image);
				_options.beforeupload(file);
			 },// end 加载预览图  
			 
			 
			 //可能根据业务需求需要判断是否有权限上传文件
			 verifyUploadFile:function(file){
				 //定义返回
				 var flag=true;
				 //获取配置参数
				 var _options=this.options;
				 var _attachment=this;
				 var validUrl=_options.valid_url;
				 var extraPars=_options.formParams;
				 
				 //获取请求参数
				 //判断文件大小
				 var fileSize=file.size;
				 var fileName=file.name;
				 var attachEntityId= _options.attachEntityId;
				 var attachEntityType=_options.attachEntityType;
				 var params={'fileSize' : fileSize , 'fileName' : fileName ,'attachEntityId' : attachEntityId , 'attachEntityType' : attachEntityType};
				 params=$.extend(params, extraPars);					//扩展上传校验属性
				 $.ajax({
					 url:validUrl,
					 type:'post',
					 data:params,
					 async:false,
					 dataType:"json",
					 success:function(data){
						 if(data.state=='0'){
							 flag=false;
							 _options.verifyUploadError(file,data.msg);
						 }
					 },
				  });
				 
				 //监听上传步骤
				 if(flag){
					 _options.moment(file,_attachment.steps[2]);
				 }else{
					 _options.moment(file,_attachment.steps[0]);
				 }
				 
				 //直接返回
				 return flag;
			 },//end 校验上传文件
			 
			 //上传单个文件
			 uploadFile:function(file){
				 var _options=this.options;
				 var _attachment=this;
				 var fileId=file.id;//文件标识id
				 
				 //定义上传表单参数
				 var uploadUrl=_options.url; //上传地址
				 var formData = new FormData(); //创建请求request 信息参数
				 
				 //设置上传参数
				 formData.append(_options.fileName, file);
				 formData.append("tenantId", _options.tenantId);
				 formData.append("attachEntityId", _options.attachEntityId);
				 formData.append("attachEntityType", _options.attachEntityType);
				 
				 //循环变量外的数据
				 var formParams=_options.formParams;
				 if(formParams){
					 for(pop in formParams){
						 formData.append(pop,  formParams[pop]);
					 }
				 }
				 
				 //定义请求
				 var xhr = new XMLHttpRequest();// XMLHttpRequest 对象
				 xhr.upload.addEventListener("progress", function(evt){ //上传进度
					 //总共文件大小
					 var totalSize=evt.total;
					 var uploadSize=evt.loaded;
					 if (!evt.lengthComputable) {
						return;
					 }
					 //显示进度
					 var factor=(uploadSize/totalSize)*100;
					 
					 
					 //步骤监听
					 if(factor>=100){
						 _options.moment(file,_attachment.steps[4]);
					 }else{
						 _options.moment(file,_attachment.steps[3]);
					 }
					 
					 //上传中回调
					 if(_options.uploading){
						 _options.uploading(file,totalSize,uploadSize,factor);
					 }
					
					 $("#ru-progress_"+fileId).show();
					 $("#ru-progress_"+fileId).find(".ru-upload-bar").css("width",factor+"%");
				 }, false);//end 上传中...
				    
				 //上传完成
				 xhr.addEventListener("load", function(e){
					 //上传后台返回结束视为上传成功
					 _attachment.delFromUpQueue(file);
					 
					 //解析上传结果
					var data=JSON.parse(e.target.responseText); 
					var state=data.state;
					if(state!="1"){
						var errMsg=data.msg;
						_options.uploadError(file,data,errMsg);//文件上传失败会 有回掉	
						return;
					}
					
					//用于前台回调
					_options.moment(file,_attachment.steps[5]);
					_options.uploadSuccess(file,data);
					
					//下面采用系统自带的 方法封装
					//附件
					var attachment=data.data[0];
					_options.uploaded(file,attachment);
					 
					//如果需要自定义
					if(_options.isUploadDIY){
						return;
					}
					
					//下面组件内置封装样式和方法的定义
					 //回显上传成功样式
					 setTimeout(function(){
						 $("#ru-progress_"+fileId).hide(); //隐藏滚动条
				      },1500);
					 
					 var toolsHtm='<a href="#"  id="file-download_'+fileId+'">下载</a><a href="#"  id="file-del_'+fileId+'">删除</a>';
					 $("#ru-tools_"+fileId).html(toolsHtm);
					 
					 //绑定删除方法
					 $("#file-del_"+fileId).click(function(){
						 _attachment.delFile(fileId,attachment);
					 });
					 
					 //绑定下载
					 $("#file-download_"+fileId).click(function(){
					 	_attachment.downloadFile(attachment);
					 });
					 
				 }, false);//end 上传完成
				    
				//上传错误
				xhr.addEventListener("error", function(progressEvent){
					//上传错误说明 不在上传了
					_attachment.delFromUpQueue(file);
					
					var  targetXHR=progressEvent.currentTarget;
					var targetXhrStatus=targetXHR.status;
					if(targetXhrStatus==0){
						var errMsg='当前网络异常,请稍后再试';
						_options.uploadError(file,attachment,errMsg);//文件上传失败会 有回掉	
					}else{
						var errMsg="file:"+fileId+"upload error!";
						_options.uploadError(file,attachment,errMsg);//文件上传失败会 有回掉	
					}
				
				}, false);//end 上传 失败
				    
				//发送数据
				xhr.open("post", uploadUrl,true);
				//xhr.setRequestHeader("X_FILENAME", file.name);
				xhr.setRequestHeader("X_FILENAME", unescape(encodeURIComponent(file.name)));
				
				//添加上传队列
				_attachment.addToUpQueue(file);
				
				//发送上传文件
				xhr.send(formData);
			 }, //end 上传单个文件
			
			//获取预览样式模版html
			getPreviewHtmlTemplate:function(file){
				var _attachment=this;
				var fileSizeObj=_attachment.showFileSize(file.size); //获取文件显示大小
				var fileId=file.id;
				var previewFileHtml='<div class="ru-preview"  id="ru-preview_'+fileId+'">'
					+'<div class="ru-details" id="ru-details_'+fileId+'">'
						+'<div class="ru-filename"><span>'+file.name+'</span></div>'
						+'<div class="ru-size" ><strong>'+fileSizeObj.size+'</strong> '+fileSizeObj.string+'</div>'
					+'</div>'
					+'<div class="ru-progress" id="ru-progress_'+fileId+'" >'
						+'<span style="width: 0%;" class="ru-upload-bar"></span>'
					+'</div>'
					+'<div class="ru-tools"  id="ru-tools_'+fileId+'">'
					+'</div>'
				+'</div>';
				return previewFileHtml;
			},//end getPreviewHtmlTemplate
			
			
			//获取预览样式模版html
			getLoadViewHtmlTemplate:function(file){
				var _attachment=this;
				var fileSizeObj=_attachment.showFileSize(file.size); //获取文件显示大小
				var fileId=file.id;
				
				//如果是图片需要显示预览图
				var ext=file.ext;
				var fileIsImage=false;
				if(ext!=undefined && ext!=null && ext!=""){
					ext=ext.toLowerCase();
					if(ext=='png' || ext=='jpg' ||ext=='gif' ||ext=='bmp'  ||ext=='jpeg'  ||ext=='tiff'){
						fileIsImage=true;
					}
				}
				var imgView='';
				if(fileIsImage){
					imgView='<img src="'+file.path+'"/>';
				}
				//最终预览html
				var previewFileHtml='<div class="ru-preview"  id="ru-preview_'+fileId+'">'
					+'<div class="ru-details" id="ru-details_'+fileId+'">'
						+'<div class="ru-filename"><span>'+file.name+'</span></div>'
						+'<div class="ru-size" ><strong>'+fileSizeObj.size+'</strong> '+fileSizeObj.string+'</div>'
						+imgView
					+'</div>'
					+'<div class="ru-progress" id="ru-progress_'+fileId+'" >'
						+'<span style="width: 0%;" class="ru-upload-bar"></span>'
					+'</div>'
					+'<div class="ru-tools"  id="ru-tools_'+fileId+'">'
					+'<a href="#"  id="file-download_'+fileId+'">下载</a><a href="#"  id="file-del_'+fileId+'">删除</a>'
					+'</div>'
				+'</div>';
				
				return previewFileHtml;
			},//end getPreviewHtmlTemplate
			
			
			
			//获取文件大小
			showFileSize:  function(size){
				var string=null;
				if (size >= 1024 * 1024 * 1024 * 1024) {
					size = size / (1024 * 1024 * 1024 * 1024);
			        string = "Tb";
				} else if (size >= 1024 * 1024 * 1024) {
					size = size / (1024 * 1024 * 1024);
					string = "Gb";
			      } else if (size >= 1024 * 1024) {
			    	  size = size / (1024 * 1024 );
			    	  string = "Mb";
			      } else if (size >= 1024) {
			          size = size / (1024);
			          string = "Kb";
			      } else {
			          size = size;
			          string = "b";
			      }
				//返回大小
			 	 size=size.toFixed(2);
			 	 return {'size':size,'string':string};
			 	 
			 }, //end  showFileSize 文件大小

			 //渲染
			 render: function(image){
			 	var MAX_HEIGHT = 100; 
			 	// 获取 canvas DOM 对象
			 	var canvas = document.createElement("canvas");
			 	// 如果高度超标
			 	if(image.height > MAX_HEIGHT) {
			 	// 宽度等比例缩放 *=
			 	image.width *= MAX_HEIGHT / image.height;
			 	image.height = MAX_HEIGHT;
			 	}
			 	// 获取 canvas的 2d 环境对象,
			 	// 可以理解Context是管理员，canvas是房子
			 	var ctx = canvas.getContext("2d");
			 	// canvas清屏
			 	ctx.clearRect(0, 0, canvas.width, canvas.height);
			 	// 重置canvas宽高
			 	canvas.width = image.width;
			 	canvas.height = image.height;
			 	
			 	// 将图像绘制到canvas上
			 	ctx.drawImage(image, 0, 0, image.width, image.height);
			 	// !!! 注意，image 没有加入到 dom之中
			 	
			 },// end render 渲染
			 
			 //删除文件
			 delFile:function(fileId,attachment){
						 
				 var _options=this.options;
				 var deleteUrl=_options.del_url+"/"+attachment.id;
				 $.ajax({
					 url:deleteUrl,
					 type:'post',
					 data:null,
					 async:false,
					 dataType:"json",
					 success:function(data){
						 //data=JSON.parse(data);
						 var result=data.state;
						 if(result=="1"){
							 //隐藏
							 $("#ru-preview_"+fileId).fadeOut(function(){
								 $("#ru-preview_"+fileId).remove(); 
							 });
							 if(_options.remove){
								 _options.remove.apply(this,[attachment]);
							 }
						 }
					 },
				  });	
			 },//end  delete file
			 
			 //下载
			 downloadFile:function(attachment){
				 var _options=this.options;
				 var downloadUrl=attachment.url;
				 window.location=downloadUrl;
			 },//下载文件
			 
			 //预览文件
			 previewAttachment:function(id){
				 // this is expend preview office file online
			 },//end 预览
			 
			 //回显列表
			 list: function(){
				 var _options=this.options;
				 var _attachment=this;
				 var isShowLoadList=_options.isShowLoadList;
				 if(!isShowLoadList){
					 return;
				 }
				 var listUrl=_options.list_url+"/"+_options.attachEntityId;
				 
				 //没有实体id 不执行下面这个
				 if(!_options.attachEntityId){
					 return;
				 }
				 var callbackFileList=null;//回调文件列表list
				 $.ajax({
					 url:listUrl,
					 type:'post',
					 data:null,
					 async:false,
					 dataType:"json",
					 success:function(data){
						 //data=JSON.parse(data);
						 var result=data.state;
						 if(result=="1"){
							 var list= data.data;
							 callbackFileList=list;
							 for(var i=0;i<list.length;i++){
								 var attach=list[i];
								 var file={'id':attach.id,'name':attach.fileName,'size':attach.size,'ext':attach.ext};
								 file.path=attach.url;
								 var loadFileHtml=_attachment.getLoadViewHtmlTemplate(file);
								 $("."+_options.loadListCss).append(loadFileHtml);
								 
								 var fileId=file.id;
								 //绑定删除方法
								 $("#file-del_"+fileId).click(function(){
									 _attachment.delFile(fileId,attach);
								 });
								 
								 //绑定下载
								 $("#file-download_"+fileId).click(function(){
								 	_attachment.downloadFile(attach);
								 });
							 }//end for
						 }//end if
					 },
				  });//end ajax
				
				 _attachment.initListCallback(callbackFileList);
				 
			 },//end list 回显列表
			 
			 //单个文件上传完回调
			 finishCallback:function(file){ 
				 this.options.finishCallback(file);
			 },
			//初始化列表callback
			 initListCallback:function(fileList){
				 this.options.initListCallback(fileList);
			 },
			
			 //上传中的文件 进入队列
			 addToUpQueue:function(file){
				 var fileId=file.id;
				 var _attachment=this;
				 var _options=_attachment.options;
				 var _quene=_attachment.file_queue;
				 _quene.push(fileId);
				 
				 //发生回调
				 _options.newUploadCallback(file);
				 
			 },
			 
			 //上传中的文件移除队列
			 delFromUpQueue:function(file){
				 var fileId=file.id;
				 var _attachment=this;
				 var _options=_attachment.options;
				 var _quene=_attachment.file_queue;
				 var val=fileId;
				 for (var i = 0; i < _quene.length; i++) {
		             if (_quene[i] == val) {
		            	 _quene.splice(i, 1);	//删除
		             }
		         }
				 
				 //当一个上传任务结束时会回调
				 _options.uploadTaskEndCallback(file);
				 
				 //队列清空时会回调
				 var size=_attachment.getUpQueueSize();
				 if(size<=0){
					 _options.nothingUploadCallback(file);
				 }
				 
			 },
			 
			 //获取上传文件中队列文件大小
			 getUpQueueSize:function(){
				 var _attachment=this;
				 var _quene=_attachment.file_queue;
				 return _quene.length;
			 },
			 
	 }
	 //end attachment
	
 })( jQuery );  