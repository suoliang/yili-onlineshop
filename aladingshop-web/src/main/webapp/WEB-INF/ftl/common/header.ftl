
   	<script src="${rc.contextPath}/static/shop/js/jquery-ui.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
   	<script type="text/javascript" src="${rc.contextPath}/static/web-js/shoppingCart.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script>
          $(function(){

          		miniCart(); /** 初始化购物车*/
          		
                $("#search .searchBtn").click(function(){
					var searchWord = $.trim($("#search .txtKeywords").val());
					var	url = "${rc.contextPath}/search/q?keywords=";
					if(searchWord == ''){
						return ;
					}else{
						window.location.href=url+encodeURI(searchWord);
					}
					
				});
				
				
				/**IE，火狐都支持  -- 回车搜索*/
				$(".txtKeywords").keydown(function(e){
					var curKey = e.which;
					if(curKey == 13){
						$("#search .searchBtn").click();
						return false;
					   }
				});
                
                
                $(".txtKeywords").autocomplete({
                    source: function( request, response ) {  
				                $.ajax({  
				                	type: 'POST',
				                    url: "${rc.contextPath}/search/suggest",  
				                    dataType: "json",  
				                    data: {  
				                        wt:"json",  
				                        q: request.term  
				                    },  
				                    success: function( data ) {  
				                        response(eval(data));
				                    }  
				                });  
				    },
                    autoFocus: true,
                    minLength: 1
                });
                
                
                
                $("#mini-cart-link").click(function(){
                	window.location.href="${rc.contextPath}/cart/list.html?time="+new Date().getTime();
                
                });
                
            });
      </script>
        
        <!-- 头部搜索版块 结束 -->

       <div id="header">
            <div class="container">
                <a href="http://www.aladingshop.com" class="fl logo"></a>
                <div id="mini-cart" class="fr">
                    <div class="cart">
                        <i>0</i>
                    </div>
                    <span id="mini-cart-link">购物车</span>
                    
                    <div class="cart-list">
                        <h6>购物车商品</h6>
                   
                        <ul>
                        </ul>
                
                        <p id="cartPnum">
	                    	<span>共 0 件</span>
	                    	<a href="${rc.contextPath}/cart/list.html">去结算</a>
	                	</p>
                    </div>
                    <div class="cart-unlogin">
                        <p>请<a href="${rc.contextPath}/login/index.htm?v=${EnvironmentConstant.DEPLOY_VERSION}">&ensp;登 录&ensp;</a>查看您的的购物车！</p>
                    </div>
                    <div class="cart-none">
                        <p>购物车空空空！</p>
                    </div>
                </div>
                <div id="search" class="fr">
                    <span></span>
                    <input type="text" class="txtKeywords" placeholder="阿拉丁商城，搜你想要的！"  value="${keywords!''}" />
                    <button class="searchBtn">搜索</button> 
                </div>
                <div class="close"></div>
            </div>
        </div>
