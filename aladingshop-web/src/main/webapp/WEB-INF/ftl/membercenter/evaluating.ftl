<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>商品评价</title>
        <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
    </head>
    <body>
        <!-- 顶部导航 开始 -->
        <#include "/common/topbar.ftl" />
        <!-- 顶部导航 结束 -->
        <div class="order-tetail-tit container">
            <div class="detail-status">
                <p>&nbsp;</p>
                <ul>
                    <i></i>
                    <li class="active">
                        <span></span>
                        <p>提交订单</p>
                    </li>
                    <li class="active">
                        <span></span>
                        <p>付款</p>
                    </li>
                    <li class="active">
                        <span></span>
                        <p>卖家发货</p>
                    </li>
                    <li class="active">
                        <span></span>
                        <p>确认收货</p>
                    </li>
                    <li class="active">
                        <span></span>
                        <p>评价</p>
                    </li>
                </ul>
                <div class="tit-btn">
                    <p>您的订单已经完成啦，去给个好评吧！</p>
                </div>
            </div>
        </div>

        <div class="assessing-wrap container oh">
            <div class="assessing-tit">
                <p>商品</p>
                <span>评论</span>
            </div>
            <div class="product fl">
                <a href="${rc.contextPath}/sku/skuDetailStatic.htm?skuCode=${skuDto.skuCode}" target="_blank"><img src="${skuDto.imgPath}" height="183" width="183" alt=""></a>
                <div class="product-r">
                    <p>${skuDto.name}宝洁海飞丝去屑清爽去油洗发750m宝洁海飞丝去屑清爽去油洗发750m</p>
                    <span>颜色：${skuDto.color}</span>
                    <span>尺寸：${skuDto.size}</span>
                    <i>好评率：${percent}%</i>
                </div>
            </div>
            <div class="assessing">
                <span>评分：</span>
                <ul class="ranking">
                    <input id="rankNum" type="hidden" name="score">
                    <li>
                        <span>太失望了，差评！差评！</span>
                    </li>
                    <li>
                        <span>此次购物体验很失望！</span>
                    </li>
                    <li>
                        <span>还不错，中评吧！</span>
                    </li>
                    <li>
                        <span>很满意，好评！</span>
                    </li>
                    <li>
                        <span>超级满意，五星好评！</span>
                    </li>
                </ul>
                <span class="mT10">评论：</span>
                <textarea placeholder="我还有更多的评论" id="commentContent" name="commentContent"></textarea>
            </div>
            <div class="assessing-btn">
                <a id="assessingBtn" href="javascript:void(0)">发表评论</a>
                <div class="check-box fr">
                    <i></i>
                    <input   class="" type="checkbox">
                    <input name="isAnonymous" id="isAnonymous"   type="hidden">
                    
                    <span>匿名评价</span>
                </div>
            </div>
        </div>

        <!-- 底部 开始 -->
        <#include "/common/footer.ftl">
        <!-- 底部 结束 -->

        <!-- js -->
        <script>
        $(function(){
            /*匿名*/
            var hideNameCheck = $('.assessing-btn').find('input[type=checkbox]');
            hideNameCheck.click(function(event) {
                if (this.checked) {
                    $(this).attr('checked', 'checked').parents('.check-box').addClass('checked');
                    $("#isAnonymous").val('y');
                } else {
                    $(this).removeAttr('checked').parents('.check-box').removeClass('checked');
                    $("#isAnonymous").val('n');
                }
            });

            /*评星*/
            $('.ranking').find('li').click(function(event) {
                $(this).addClass('ranked').siblings().removeClass('ranked');
                $('#rankNum').val($(this).index());
                var words = $(this).find('span').text();
                var textarea = $('.assessing').find('textarea');
                textarea.html(words);
            });

            $('.ranking').find('li').hover(function() {
                $(this).addClass('active').prevAll('li').addClass('active');
                $(this).nextAll('li').removeClass('active');
            }, function() {
                if ($('.ranked').length!==0) {
                    $('.ranked').addClass('active').prevAll('li').addClass('active');
                    $('.ranked').nextAll('li').removeClass('active');
                } else {
                    $('.ranking').find('li').removeClass('active');
                }
            });

            /*未评论提示框*/
            $('#assessingBtn').click(function(event) {
                if ($('#rankNum').val()=="") {
                    openAddCartModal(0,'请对商品做出评分！');
                } else {
                debugger;
                   var skuCode='${skuDto.skuCode}';
                   var score=$("#rankNum").val();
                   var commentContent=$("#commentContent").val();
                   var orderLineId='${orderLineId}';
                   var isAnonymous=$("#isAnonymous").val();
                   $.post("${rc.contextPath}/memberComment/submitComment",
                   {skuCode:skuCode,score:score,commentContent:commentContent,orderLineId:orderLineId,isAnonymous:isAnonymous,time:new Date().getTime()},
                    function(data){
                    if(data=='true'){
                       openAddCartModal(1,'操作完成，返回我的评论');
                       setTimeout(function(){
                       window.location.href='${rc.contextPath}/myAld/commentList.htm';
                    },2300);
                    }else{
                    openAddCartModal(1,'操作失败，请登陆重试');
                       setTimeout(function(){
                       window.location.href='${rc.contextPath}/login/index.htm';
                    },2300);
                    }
                    });
                    /**
                    openAddCartModal(1,'操作完成，返回我的评论列表');
                    setTimeout(function(){
                       window.location.href='${rc.contextPath}/myAld/commentList.htm';
                    },2300);*/
                }
            });

        })
        </script>
    </body>
</html>
