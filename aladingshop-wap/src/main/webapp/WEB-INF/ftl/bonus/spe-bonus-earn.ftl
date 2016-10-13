<!DOCTYPE html>
<html>
    <head>
        <title>赚红包</title>
        <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
         <script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
        <!-- 通用部分 结束 -->
    </head>
    <body id="spe-bonus-earn">
        <div class="container">
            <div class="head">
                <a href="javascript:history.go(-1)" class="a-left">
                    <span class="back"></span>
                </a>
                <p>赚红包</p>
            </div>

            <div class="spe-bonus-earn-wrap fl wp100">
                <img src="${rc.contextPath}/static/shop/images/bo_01.png">
                <div class="earn-box fl wp100">
                    <img src="${rc.contextPath}/static/shop/images/bo_02.png">
                    <div class="earn-bonus fl wp100">
                        <span>8元</span>
                    </div>
                    <button onclick="openShareModal()"></button>
                </div>
                <img src="${rc.contextPath}/static/shop/images/bo_04.png">
                <img src="${rc.contextPath}/static/shop/images/bo_05.png">
            </div>

        </div><!-- /.container -->

        <div class="modal-wrap" id="shareModal">
            <div class="share-wrap">
                <div class="bdsharebuttonbox">
                    <div class="share-box"><a title="分享到QQ空间" href="#" class="bds_qzone" data-cmd="qzone"></a><p>QQ空间</p></div>
                    <div class="share-box"><a title="分享到新浪微博" href="#" class="bds_tsina" data-cmd="tsina"></a><p>新浪微博</p></div>
                    <div class="share-box"><a title="分享到腾讯微博" href="#" class="bds_tqq" data-cmd="tqq"></a><p>腾讯微博</p></div>
                </div>
                <script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"${title}","bdDesc":"${share.shareContent}","bdUrl":"${share.shareUrl}","bdMini":"2","bdMiniList":[],"bdPic":"","bdStyle":"1","bdSize":"24"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
            </div>
        </div>
        <script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>
