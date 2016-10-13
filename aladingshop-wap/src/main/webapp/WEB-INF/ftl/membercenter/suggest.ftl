<!DOCTYPE html>
<html>
    <head>
        <title>意见反馈</title>
         <!-- 通用部分 开始 -->
        <#include "/common/common.ftl" />
         <script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
        <!-- 通用部分 结束 -->
    </head>
    <body id="suggest">
	        <div class="container">
	
	            <div class="head mB10">
	                <a href="javascript:history.go(-1)" class="a-left">
	                    <span class="back"></span>
	                </a>
	                <p>意见反馈</p>
	            </div>
	
	            <ul class="list">
	                <li>
	                    <p>欢迎留下您宝贵的意见</p>
	                </li>
	            </ul>
	
	            <div class="suggest-a fl wp100">
	                <textarea name="content" id="" placeholder="请输入您对产品的意见，我们将不断优化体验" maxlength="100"></textarea>
	                <span><i>0</i>/100</span>
	            </div>
	
	            <input type="text" name="contactInformation" id="contactInformation" class="suggest-b" placeholder="请输入您的手机号/邮箱（选填）">
	
	            <div class="btn-wrap">
	                <button id="suggestBtn">提交</button>
	            </div>
	
	        </div><!-- /.container -->
	
	        <div class="modal-wrap" id="suggestModal">
	            <div class="modal">
	                <p>请填写您的意见反馈</p>
	                <div class="btn-wrap">
	                    <a class="cancel" href="javascript:void(0)">确定</a>
	                </div>
	            </div>
	        </div>
        <script>

        $(function(){
            var $textarea = $('.suggest-a').find('textarea');
            var $suggestModal = $('#suggestModal');
            var $textareaI = $('.suggest-a').find('i');
            $textarea.keyup(function(event) {
                $textareaI.html($(this).val().length);
            });
            $('#suggestBtn').click(function(event) {
                if ($textarea.val() === "") {
                    $suggestModal.fadeIn();
                } else {
                    /*提交*/
                 	$.ajax({
						type: "POST",
						url : "${rc.contextPath}/member/feedback",
						data: {'content':$textarea.val(),'contactInformation':$("#contactInformation").val(),'time':new Date().getTime()},
						async:false,
						dataType: "json",
					    success: function (data) {
					    	if(data.responseCode==0){
					    		alert('提交成功');
					    	}else if(data.responseCode=="201"){
					    		alert("请先登录");
					    	}else{
					    		alert('提交失败');
					    	}
					  	}
					});
                }
            
            });
          
        })
        </script>
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>
