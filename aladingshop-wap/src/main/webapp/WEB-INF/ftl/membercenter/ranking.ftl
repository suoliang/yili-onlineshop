<!DOCTYPE html>
<html>
    <head>
        <title>发表评论</title>
        <!-- 通用部分 开始 -->
        <#include "/common/common.ftl" />
         <script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
        <!-- 通用部分 结束 -->
    </head>
    
    <script> 
         function submitForm(){
          var  val=$(".checked").find('input').val();
            $("#commentLevel").val(val);
            $("#rankingForm").submit();
         }
        
    </script>
    <body id="ranking">

        <div class="container">
		<form id="rankingForm" action="${rc.contextPath}/order/commitComment" method="post">
            <div class="head mB10">
                <a href="javascript:history.go(-1)" class="a-left">
                    <span class="back"></span>
                </a>
                <p>发表评论</p>
            </div>
			
			<input type="hidden" name="skuCode" value="${sku.uniqueCode}">
			<input type="hidden" name="skuColor" value="${sku.color}">
			<input type="hidden" name="skuSize" value="${sku.size}">
			<input type="hidden" name="orderCode" value="${orderLineUser.orderCode}">
			<input type="hidden" name="orderLineId" value="${orderLineUser.id}">
			<input type="hidden" name="commentLevel" id="commentLevel" value="">
			
            <p class="ranking-tit fl wp100">发表内容评价，分享心得！</p>

            <div class="cart-confirm-b fl wp100">
                <a href="detail.html"><img src="${skuImage}"></a>
                <p>${sku.name}</p>
                <#if sku.color !="">
					<span>颜色：${sku.color}</span>
				</#if>
                 <#if sku.size !="">
					<span>规格：${sku.size}</span>
				</#if>
                <i>&yen; ${orderLineUser.unitPrice}</i>
                <div class="modal-c fl wp100">数量: ${orderLineUser.quantity}</div>
            </div>

            <div class="cart-confirm-a fl wp100">
                <span class="icon-house"></span>
                <h4>阿拉丁玛特</h4>
                <p>共计 <i class="count-num">${orderLineUser.quantity}</i> 件</p>
            </div>

            <div class="ranking-wrap fl wp100 radio">
                <div class="check-box fl checked">
                    <i></i>
                    <label for="good">好评</label>
                    <input id="good" class="checkItem" type="radio" checked="checked"  value="1" />
                </div>
                <div class="check-box fl">
                    <i></i>
                    <label for="mid">中评</label>
                    <input id="mid" class="checkItem" type="radio"  value="2" />
                </div>
                <div class="check-box fl">
                    <i></i>
                    <label for="bad">差评</label>
                    <input id="bad" class="checkItem" type="radio"  value="3" />
                </div>
                <textarea name="content" id="content" placeholder="补充评价（选填）："></textarea>

            </div>

            <div class="cart-confirm-e fl wp100 checkbox">
                <button onclick="javascript:submitForm()">发表评价</button>
                <div class="check-box fl checked">
                    <i></i>
                    <label for="niming">匿名评价</label>
                    <input name="isAnonymous" class="checkItem" type="checkbox" checked="checked">
                </div>
            </div>
		</form>
        </div><!-- /.container -->
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>
