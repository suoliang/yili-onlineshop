<!-- 对话框 -->
<div class="public_modal_backup">
    <div class="public_modal">
        <div class="modal_body">加入收藏失败，请使用 Ctrl+D 进行添加！</div>
        <div class="modal_foot">
            <button class="modal_confirm only_confirm">确定</button>
        </div>
    </div>
</div>
<script type="text/javascript" src="${rc.contextPath}/static/shop/js/jquery.bttrlazyloading.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script><!-- 图片延迟加载 -->
<!--[if IE 8]>
    <script>
        $(function(){
            $('.bttrlazyloading').each(function(){
            $(this).attr({'src':$(this).attr('data-bttrlazyloading-lg-src')});
        })
        })
    </script>
<![endif]-->
<script type="text/javascript">
  $(".bttrlazyloading").bttrlazyloading({placeholder:'${rc.contextPath}/static/shop/images/lazyload.gif'});
</script>
        
