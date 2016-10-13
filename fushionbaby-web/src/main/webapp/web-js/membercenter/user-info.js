//个人中心 -- 基础信息模块
$(document).ready(function(){
	/***显示图片*/
	var image_path = $('#image_path').val();
	if (!(image_path == undefined || image_path == "")) {
		$("#show_image").attr("src",_ContextPath +"/membercenter/showPhoto.do?fileName="+image_path);
	}
	
	$.ajax({
		type:"POST",
		url: _ContextPath + '/login/getUserInfo.do?time='+new Date().getTime(),
		async:false,
		dataType:"json",
		success: function(data){
			if (data.responseCode == "0") {
				if (data.data.login_name != null) {
					$("#get_userName").html(data.data.login_name);//登录名称
					//$("#get_userName").val(data.data.login_name);
				}
			} else {
				$("#get_userName").html("您还未登录或登录名称显示有误，请<a href='"+_ContextPath+"/login/index.do' target='_parent' style='color:red;cursor: pointer;'>重新登录</a>");
			}
		}
	});
});

/**********修改信息*********/
function update_userInfo(){
	
	var nick_name = $.trim($("#nick_name").val());
	var baby_fm = null;
	var baby_gender = null;
	var baby_birthday = $("#baby_birthday").val();
	var weixin_no = $.trim($("#weixin_no").val());
	/****昵称不能超过8位*/
	if (nick_name.length > 8) {
		$("#check_nick_name").html("昵称请不要超过8位");
		$("#show_nick_name").show();
		$("#show_nick_name").fadeOut(5000);
		return;
	}
	/**微信号不要超过30位*/
	if (weixin_no.length > 30) {
		$("#check_weixin_no").html("微信号请不要超过30位");
		$("#show_weixin_no").show();
		$("#show_weixin_no").fadeOut(5000);
		return;
	}
	
	if($("input[name='parent']").is(":checked")){
		baby_fm = $("input[name='parent']:checked").val();//宝宝的爸爸或妈妈
	}

	if ($("input[name='children']").is(":checked")) {
		baby_gender = $("input[name='children']:checked").val();//宝宝性别
	}
	
	$.post(_ContextPath + '/membercenter/save_user_info.do',{
			nickname:nick_name,
			baby_fm:baby_fm,
			baby_gender:baby_gender,
			baby_birthday:baby_birthday,
			imgUrl:$("#file_photo").val(),
			weixinNo:weixin_no,
			time:new Date().getTime()
		},function(data){
			if (data.responseCode == "0") {//修改成功!
				alert("修改成功!");
				//刷新
				window.parent.document.getElementById("iframepage").src = _ContextPath + "/membercenter/user_info.do?time="+new Date().getTime();
			} else {
				alert("修改失败!");
			}
		}
	);
}

/***图片上传*/
$(function(){
	setTimeout(function(){
		//var login_name = $("#get_userName").val();
		$('#fileField').uploadify({
			'swf': _ContextPath + '/web-js/uploadify/uploadify.swf?time='+new Date().getTime(),
			'method':'post',
		    'fileObjName':'photo',
		    'uploader': _ContextPath + '/membercenter/loadPhoto.do',//?login_name='+login_name,
			'buttonText':"<input type='button' class='btn mod-btn info-desc' value='浏览...' />",
			'fileSizeLimit':'2MB',
			'fileTypeExts':'*.jpg;*.jpeg;*.gif;*.png;',
			'simUploadLimit' : 1, 
			//自动上传
			'auto':true,
			//设置多文件
			'multi':false,  
			//防止缓存
			'preventCaching':true,
			//进度
			'progressData':'percentage',
			
		 /*	'onUploadStart':function(file){
		
					$('.file_upload').uploadify('settings','forData',array);
			}, */
		   'onFallback':function(){
	            alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
	        },
	       'onSelectError':function(file, errorCode, errorMsg){
	            switch(errorCode) {
	                case -100:
	                    alert("上传的文件数量已经超出系统限制的"+$('.file_upload').uploadify('settings','queueSizeLimit')+"个文件！");
	                    break;
	                case -110:
	                    alert("文件 ["+file.name+"] 大小超出系统限制的"+$('.file_upload').uploadify('settings','fileSizeLimit')+"大小！");
	                    break;
	                case -120:
	                    alert("文件 ["+file.name+"] 大小异常！");
	                    break;
	                case -130:
	                    alert("文件 ["+file.name+"] 类型不正确！");
	                    break;
	            }
	        }, 

			'onUploadSuccess':function(file,data,response){
				 $("#file_photo").val(data);//给隐藏域传文件名
				 $("#show_image").show();//显示图片
				 $("#show_image").attr("src",_ContextPath +"/membercenter/showPhoto.do?fileName="+data+'&time='+new Date().getTime());
				 $(".upload-pic").hide();//上传图片成功后隐藏
			}
		});
	 },100);
}); 


