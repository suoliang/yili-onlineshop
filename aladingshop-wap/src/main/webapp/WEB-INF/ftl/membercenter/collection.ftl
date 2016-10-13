<!DOCTYPE html>
<html>
    <head>
        <title>我的收藏</title>
         <!-- 通用部分 开始 -->
        <#include "/common/common.ftl" />
         <script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
        <!-- 通用部分 结束 -->
    </head>
    <body id="collection">

        <div class="container">

            <div class="head mB10">
                <a href="javascript:history.go(-1)" class="a-left">
                    <span class="back"></span>
                </a>
                <p>我的收藏</p>
                <a href="javascript:void(0)" class="a-right">
                    <span id="deleteBtn">编辑</span>
                </a>
            </div>

            <div id="collectionNone" style="display:none">
                <div class="noRank"><span></span><p>您还没有收藏商品 ！</p></div>
            </div>
            <div class="cart-wrap fl wp100 checkbox">
 				<#if collectionSkuList?? >
                        <#list collectionSkuList as collect>
			                <div class="cart-item" >
			                    <div class="check-box fl checked">
			                        <i></i>
			                        <input name="checkItem" class="checkItem" type="checkbox" checked="checked">
			                    </div>
			                    <img src="${collect.imgPath}" onclick="javascript:location.href='${rc.contextPath}/sku/skuDetail?skuCode=${collect.skuCode}'">
			                    <div class="cart-item-mid">
			                        <p>${collect.name}<input type="hidden"  value="${collect.skuCode}"></p>
			                        <#if collect.color!="">
			                        	<span>颜色：${collect.color}</span>
			                        </#if>
			                        <#if collect.size!="">
			                        	<span>规格：${collect.size}</span>
			                        </#if>
			                        <div class="price">&yen; ${collect.priceNew}</div>
			                    </div>
			                    <div class="delete">删除</div>
			                </div>
			             </#list> 
                 </#if>  
            </div>

        </div><!-- /.container -->

        <script>
            $(function(){
                if ($('.cart-item').length==0) {
                    $('#collectionNone').show();
                };

                $('#deleteBtn').click(function(event) {
                    if ($(this).html()==="编辑") {
                        $(this).html("取消");
                    } else {
                        $(this).html("编辑");
                    };
                    $('.cart-item').toggleClass('open');
                });

                $('.delete').click(function(event) {
                	var currentbtn=$(this);
	            	var skuCode=$(this).parents('.cart-item').find("input[type=hidden]").val();
	            	var url="${rc.contextPath}/collect/deleteCollect?skuCode="+skuCode+"&time="+new Date().getTime();
	            	$.ajax({
				        type: "POST",
				        async:false,
				        dataType: "json",
				        url: url,
				        success: function (data) {
				    		if (data.responseCode=="0") {
				    			currentbtn.parents('.cart-item').velocity({height:0,opacity:0,padding:0},{duration: 200}).velocity({border:0},{
			                        complete:function(){
			                            $(this).remove();
			                            if ($('.cart-item').length==0) {
			                                $('#collectionNone').show();
			                                hideSomethings();
			                            };
			                        }
			                    });
				    		}
				        }
				    });	
                });
                hideSomethings();

            })
            function hideSomethings(){
                if (!$('#collection').find('.cart-item').length) {
                    $('.a-right').hide();
                    $('#collectionNone').show();
                };
            }
        </script>
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>
