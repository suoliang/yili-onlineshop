<!DOCTYPE html>
<html>
    <head>
        <title>购物车</title>
        <!-- 通用部分 开始 -->
        <#include "/common/common.ftl" />
        <!-- 通用部分 结束 -->
		<script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
        <link rel="stylesheet" href="${rc.contextPath}/static/shop/farmwork/swiper/swiper.min.css"><!-- swiper CSS-->
    </head>
    <body id="cart">

        <div class="container">

            <div class="head mB10">
                <a href="javascript:history.go(-1)" class="a-left">
                    <span class="back"></span>
                </a>
                <p>购物车</p>
                <a href="javascript:void(0)" class="a-right">
                    <span id="deleteBtn">编辑</span>
                </a>
            </div>

            <div class="cart-wrap fl wp100">
                <div class="cart-tit">
                    <span class="house"></span>
                    <h3>阿拉丁商品</h3>
                    <p>共计<#if cart?exists>${cart.pnumTotal!0}</#if>件</p>
                </div>
				
				<#if cart.items?exists && cart.items?size&gt;0>
				<#list cart.items as item>
                <div class="cart-item">
                    <div class="check-box fl <#if item.isSelected =='y'>checked</#if>">
                        <i></i>
                        <input name="checkItem" class="checkItem" <#if item.isSelected =='y'>checked="checked"</#if> value="${item.skuCode}"  type="checkbox" onchange="modifySelected('${item.skuCode}',this)">
                    </div>
                    <img src="${item.imgPath}">
                    <div class="cart-item-mid">
                        <p>${item.name}</p>
                        <span>
                        	<#if item.color!=''>
                            	<span>颜色：${item.color}</span>
                            </#if>
                            <#if item.size!=''>
                            	<span>类型：${item.size}</span>
                            </#if>
                        </span>
                        <div class="modal-c fl wp100"  id="cart-count-${item.skuCode}">
                            <button class="Add" onclick="setAmountCart.add('${item.skuCode}')">+</button>
                            <i class="count-num count-i">${item.pnum}</i>
                            <button class="Reduce" onclick="setAmountCart.reduce('${item.skuCode}')">-</button>
                            <input type="hidden" value="1">
                        </div>
                    </div>
                    <div class="price">&yen; ${item.price}</div>
                    <div class="delete" onclick="removeCartSku('${item.skuCode}')">删除</div>
                </div>
                </#list>
                </#if>

                <div class="cart-b fl wp100">
                    <div class="check-box fl  checkAll">
                        <i></i>
                        <input id="checkAll" name="checkAll"   type="checkbox"  onchange="modifyAllSelected(this)">
                        <label for="checkAll">全选</label>
                    </div>
                    <div class="cart-b-m fl">
                        <p>合计：<i class="red">&yen; ${cart.priceTotal}</i></p>
                        <span>不含运费</span>
                    </div>
                    <a href="javascript:cartConfirm()"><button>去结算</button></a>
                </div>

            </div>

            <div id="cartNone" class="detail-c fl wp100 like-wrap">
                <div class="cart-none fl wp100">
                    <span></span>
                    <div class="not-login fl wp100">
                        <p>您可以在登录后同步电脑与手机购物车中的商品</p>
                        <div class="btn-wrap">
                            <a href="${rc.contextPath}/login/index">登录</a>
                            <a href="javascript:toRegister();">注册</a>
                        </div>
                    </div>
                    <div class="logined fl wp100" style="display:none">
                        <p>您的购物车是空的！</p>
                        <div class="btn-wrap">
                            <a href="index.html">去逛逛</a>
                        </div>
                    </div>
                </div>
        </div><!-- /.container -->
		
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
        <script src="${rc.contextPath}/static/shop/farmwork/swiper/swiper.jquery.min.js"></script><!-- swiper JS -->
        <script>
        	$(document).ready(function() {
          		 /****校验登录信息**/
			 $.post("${rc.contextPath}/login/isLogin",{time:new Date().getTime()},function(result){
					if(result.data =="y"){
			         	 $('.btn-wrap').hide();
		            }
			 });
          });
          
            $(function(){
            	var chks = $('.checkItem');
            	var isAll = 0;
            	for(var i=0; i<chks.length;i++){
            		if(!chks[i].checked){
            			isAll = 1;
            			break;
            		}
            	}
            	
            	if(isAll == 1){
            		$('#checkAll').removeAttr('checked');
        			$('#checkAll').parents('.check-box').removeClass('checked');
            	}else{
            		$('#checkAll').attr('checked','checked');
        			$('#checkAll').parents('.check-box').addClass('checked');
            	}
            	
            
                $('#deleteBtn').click(function(event) {
                    if ($(this).html()==="编辑") {
                        $(this).html("取消");
                    } else {
                        $(this).html("编辑");
                    };
                    $('.cart-item').toggleClass('open');
                });
                
                if ($('.cart-item').length==0) {
                                $('.cart-wrap').hide();
                                $('#cartNone').show();
                                /*猜你喜欢 滚动初始化*/
                                var swiper = new Swiper('#detailBannerLike',{
                                    grabCursor: true,
                                    slidesPerView: 'auto'
                                });
                            };

                $('.delete').click(function(event) {
                    $(this).parents('.cart-item').velocity({height:0,opacity:0,padding:0},{duration: 200}).velocity({border:0},{
                        complete:function(){
                            $(this).remove();
                            hideSomethings();
                        }
                    });
                });
                
                hideSomethings();

            })
            
            /** 请求后台移除购物车商品*/
			function removeCartSku(skuCode){
				$.post("${rc.contextPath}/cart/removeBath",{
					skuCodes : skuCode,
					time : new Date().getTime()},
					function(data){
						if(data.responseCode!=0){
							openModal('0',data.msg);
						}
						
						window.location.reload();
				});
			}
			
		/**数量设置**/
		setAmountCart = {
			min : 1,
			max : 999,
			add : function(skuCode) {
				var countEl = $("#cart-count-"+skuCode+" .count-i");
				count = countEl.text();
				count >= this.max ? !1 : (count++, countEl.text(count));
		
				//同步数量到服务器,计算金额
				amountModify(skuCode,countEl.text());
			},
			reduce : function(skuCode) {
				var countEl = $("#cart-count-"+skuCode+" .count-i");
				count = countEl.text();
				count <= this.min ? !1 : (count--, countEl.text(count));
				//同步数量到服务器,计算金额
				amountModify(skuCode,countEl.text());
			}
		};
		
		//修改数量异步调用
		function amountModify(skuCode,num){
			$.post("${rc.contextPath}/cart/modifyItemQuantity",
				{
					skuCode : skuCode,
					quantity : num,
					time : new Date().getTime()
				},
				function(data){
					if(data.responseCode==0){
						window.location.reload();
						//修改购物车前端显示数量
						//$(".sidebar-car-num").text(data.data.pnumTotal);
						//$(".total-price i").text(data.data.priceTotal);
						//$("#rows-tprice-"+skuId).text(data.data.rowsCurrentPriceTotal);
					}else{
						openModal('0',data.msg);
					}
				});//end post request
		}
		
		//修改数量,加入购物车前使用,不进行异步调用
		setAmount = {
			min : 1,
			max : 999,
			count : 1,
			add : function() {
				this.count >= this.max ? !1 : ($("#cart-num").val(++this.count));
			},
			reduce : function() {
				this.count <= this.min ? !1 : ($("#cart-num").val(--this.count));
			}
		};
		
		/** 修改购物车选中状态*/
		function modifySelected(skuCode,obj){
			
			var isSelected="n";
			if(obj.checked==true){
				isSelected = "y";
			}
			$.post("${rc.contextPath}/cart/modifyItemSelected",{
				skuCodes : skuCode,
				isSelected : isSelected,
				time : new Date().getTime()},
				function(data){
					if(data.responseCode==0){
						window.location.reload();
					}else{
						openModal('0',data.msg);
					}
					
			   });//end post request
		}
		
		function modifyAllSelected(obj){
			
			var chks = $('.checkItem');
			
			var len = chks.length;
			if(len==null||len==""||len<=0){
				alert("请至少选中一件商品");
				return;
			}
			var codes=[];
			for(i=0;i<len;i++){
				codes.push(chks[i].value);
			}
			
			var isSelected="n";
			if(obj.checked==true){
				isSelected = "y";
			}
			$.post("${rc.contextPath}/cart/modifyItemSelected",{
				skuCodes : codes.join(","),
				isSelected : isSelected,
				time : new Date().getTime()},
				function(data){
					if(data.responseCode==0){
						window.location.reload();
					}else{
						openModal('0',data.msg);
					}
					
			   });//end post request
			
		}
		
		/** 去结算*/
		function cartConfirm(){
			var chks = $('.checkItem[checked=checked]');
			
			var len = chks.length;
			if(len==null||len==""||len<=0){
				openModal('0',"请至少选中一件商品!");
				return;
			}
			var codes=[];
			for(i=0;i<len;i++){
				codes.push(chks[i].value);
			}
			var url = "${rc.contextPath}/order/goOrderCheck";
			$.post(url,{
				time : new Date().getTime()},
				function(data){
					if(data.responseCode==0){
						window.location.href="${rc.contextPath}/order/goCartConfirm?time="+new Date().getTime();
					}else if(data.responseCode==201 || data.responseCode==300){
						window.location.href="${rc.contextPath}/login/index";
					}else{
						openModal('0',data.msg);
					}
			});
		}
		
		function toRegister(){
            	$.post(_ContextPath+'/login/clickRegister',
					{time:new Date().getTime()},
				function(data){
						if (data.responseCode == "0"){
							window.location.href=_ContextPath +"/login/toRegister?time="+new Date().getTime();
						} else {
							alert(data.msg);
						}
					}
				);
         }
         
         function hideSomethings(){
            if (!$('#cart').find('.cart-item').length) {
                $('.a-right').hide();
                $('.cart-wrap').hide();
                $('#cartNone').show();
                /*猜你喜欢 滚动初始化*/
                var swiper = new Swiper('#detailBannerLike',{
                    grabCursor: true,
                    slidesPerView: 'auto'
                });
            };
        }
        </script>

    </body>
</html>
