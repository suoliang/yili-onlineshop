<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <title>Fushionbaby触屏版-充值</title>
    <meta name="apple-mobile-web-app-title" content="兜士宝-充值">
    <script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	 </script>
    <script type="text/javascript" src="${rc.contextPath}/wap/js/public-headTag.js"></script><!-- 公共头部便签，css -->
  	
  </head>
  <body id="deposit">
    <div class="container">
      <div class="public_header fl width100">
        <div class="public_header_wrap fl width100">
          <a class="public_back" href="javascript:void(0);"  onclick="history.go(-1);">返回</a>
          充值
          <a class="public_btn_r disabled" href="javascript:void(0);"></a>
        </div>
      </div>
      <div class="public_form_wrap">
        <form action="">
          <div class="public_input_wrap">
            <input id="cardNo" class="required" type="text" name="" placeholder="请输入卡号" data-toggle="tooltip" title="请输入卡号">
          </div>
          <div class="public_input_wrap">
            <input id="cardPassword" class="required" type="text" name="" placeholder="请输入卡号密码" data-toggle="tooltip" title="请输入卡号密码">
          </div>
          <button id="deposit_btn" class="public_button" type="button">使用礼品卡充值</button>
        </form>
      </div>
    </div>

    <!-- 对话框 -->
    <div class="public_modal_backup">
      <div class="public_modal">
        <div class="modal_body" id="modal_body">充值成功</div>
        <div class="modal_foot">
          <button class="modal_confirm only_confirm">好</button>
        </div>
      </div>
    </div>

    <script type="text/javascript" src="${rc.contextPath}/wap/js/footer.js"></script>
    <script>
    $(function(){
      /*  表单提交，最终验证  */
      $("#deposit_btn").click(function() {
        $(".required").trigger('blur');
        var numError = $('form .has-error').length;
        if (numError) {
          return false;
        }
        
        var url = "${rc.contextPath}/membercenter/chargeFund.do";
			$.ajax({
				type:"POST",
	            async:false,
	            url:url,
	            data:"cardNo="+$("#cardNo").val()+"&cardPassword="+$("#cardPassword").val()+"&time="+new Date().getTime(),
				success : function(data) {
					if(data.responseCode==0){
						$("#modal_body").text(data.data);
						$(this).addClass('public_loading');
				        $('.public_modal_backup').fadeIn();
				          $(".modal_confirm").click(function() {
				            $('.public_modal_backup').fadeOut();
				            $("#deposit_btn").removeClass('public_loading');
				          });
				       
					} else {
						alert("系统异常，充值未成功，请稍后再试!");
					}
					
				}//end success
			});//end ajax
        
      })
    })
    </script>
  </body>
</html>