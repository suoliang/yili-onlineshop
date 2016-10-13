<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>阿拉丁玛特 - 购物车</title>
		 <#include "/common/common.ftl" />
	  	 <script type="text/javascript" src="${rc.contextPath}/static/web-js/shoppingCart.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	  	 <script type="text/javascript" src="${rc.contextPath}/static/web-js/amountCart.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	     <script type="text/javascript" src="${rc.contextPath}/static/web-js/order.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
    </head>
    <body class="cart-body">
      	<!-- 快速登陆框 -->
        <#include "/common/quickLogin.ftl" />
        
            <script src="${rc.contextPath}/static/shop/js/jquery.validate.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script><!-- 表单验证 -->
            <script type="text/javascript">
                $(document).ready(function() {
                    /*表单验证*/
                    var validator = $('#loginForm').validate({
                        rules: {
                            userName: {
                                required: true,
                                rangelength: [2, 10]/*,
                                remote: {
                                    url: "remote.json",
                                    type: "post",
                                    data: {
                                        loginTime: function() {
                                            return +new Date;
                                        }
                                    }
                                }*/
                            },
                            password: {
                                required: true,
                                rangelength: [2, 10]
                            }
                        },
                        messages: {
                            userName: {
                                required: "请填写用户名！",
                                rangelength: "账户名长度应该为2-10位！"/*,
                                remote: "用户名不存在！"*/
                            },
                            password: {
                                required: "请填写密码！",
                                rangelength: "密码长度应该为2-10位！"
                            }
                        },
                        submitHandler: function(form) {
                            // 验证成功后操作
                            console.log($(form).serialize());
                            $(form).ajaxSubmit();
                        },
                    });
                });
            </script>
        </div>
        <!-- 登录框 结束 -->

        <!-- 右侧悬浮菜单 开始-->
       <#include "/common/rightMenu.ftl" />
        <!-- 右侧悬浮菜单 结束-->

        <!-- 顶部导航 开始 -->
       <#include "/common/topbar.ftl" />
        <!-- 顶部导航 结束 -->
        
   

        <div class="cart-top container">
            <a href="${rc.contextPath}/home" class="logo"></a>
            <ul>
                <li class="active">我的购物车<span></span></li>
                <li>&emsp;填写订单信息<span></span></li>
                <li>购物成功</li>
            </ul>
        </div>

        <div class="cart-table container">
            <table>
                <thead>
                    <tr>
                        <th class="w64">
                            <div class="check-box">
                                <i></i>
                                <input id="checkAllBtn" type="checkbox"  onchange="modifyAllSelected(this)">
                                <span>全选</span>
                            </div>
                        </th>
                        <th class="w460">商品信息</th>
                        <th class="w190">单价</th>
                        <th class="w240">数量</th>
                        <th class="w246">操作</th>
                    </tr>
                </thead>
                <tbody>
               <#if cart??> 
                <#list cart.items as item>
                    <tr>
                        <td>
                            <div class="check-box fl <#if item.isSelected =='y'>checked</#if>">
                                <i></i>
                                <input name="checkAll" class="checkAll" <#if item.isSelected =='y'>checked="checked"</#if> value="${item.skuCode}"  type="checkbox" onchange="modifySelected('${item.skuCode}',this)">
                            </div>
                        </td>
                        <td>
                            <div class="product fl">
                                <a href="${rc.contextPath}/sku/skuDetail?skuCode=${item.skuCode}" target="_blank"><img src="${item.imgPath}" height="183" width="183" alt="${item.name}"></a>
                                <div class="product-r">
                                    <p>${item.name}</p>
                                    <!--<span>品牌：潘婷</span>
                                    <span>类型：柔顺</span>-->
                                    <#if item.color!=''>
                                    	<span>颜色：${item.color}</span>
                                    </#if>
                                    <#if item.size!=''>
                                    	<span>类型：${item.size}</span>
                                    </#if>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="price">
                               <!-- <p>&yen; 188.00</p> -->
                                <span>&yen; ${item.price}</span>
                            </div>
                        </td>
                        <td>
                            <div class="count" id="cart-count-${item.skuCode}">
                                <div class="reduce" onclick="setAmountCart.reduce('${item.skuCode}')">-</div>
                                <input class="num" value="${item.pnum}"
                                	onkeyup="setAmountCart.modify('${item.skuCode}');" 
                                	onblur="setAmountCart.modify('${item.skuCode}');" 
									autocomplete="off" value="${item.pnum!''}" >
                                <div class="add" onclick="setAmountCart.add('${item.skuCode}')">+</div>
                            </div>
                        </td>
                        <td>
                            <div class="action">
                                <a class="addFavorite" href="javascript:void(0)" onclick="moveFaorite('${item.skuCode}');">移入收藏夹</a>
                                <a class="remove" href="javascript:removeItem('${item.skuCode}')">移除</a>
                            </div>
                        </td>
                    </tr>
                   </#list>
                </#if>    
                    
                  
                </tbody>
                <tfoot>
                    <tr>
                        <th>
                            <div class="check-box">
                                <i></i>
                                <input id="checkAllBtn2" type="checkbox"  onchange="modifyAllSelected(this)" >
                                <span>全选</span>
                            </div>
                        </th>
                        <td>
                            <div class="tfoot-action">
                                <a class="remove-checked" href="javascript:removeSelect()">删除选中商品</a>
                                <a href="javascript:faoriteSelected()">将选中商品全部移动到收藏夹</a>
                            </div>
                        </td>
                        <td></td>
                        <td></td>
                        <td>
                            <div class="tfoot-money">
                                <span>共 ${cart.pnumTotal} 件</span>
                                <p>总价：&yen; ${cart.priceTotal}</p>
                                <i>（满68包邮）</i>
                                <a href="javascript:cartConfirm()">去结算</a>
                            </div>
                        </td>
                    </tr>
                </tfoot>
            </table>

            <!-- 购物车为空 开始 -->
            <div class="cart-none">
                <span class="cart-none-img"></span>
                <p class="mT50">您的购物车空空如也！</p>
                <p><span>如您未登录，可能导致您的购物车为空</span><a class="open-loginBox" href="${rc.contextPath}/login/index.htm">登录</a></p>
                <p><span>您可以</span><a href="http://www.aladingshop.com" />去逛逛</a></p>
            </div>
            <!-- 购物车为空 结束 -->
        </div>

        <!-- 猜你喜欢 开始-->
    <!--    <div id="daily" class="container mB50">
            <div class="daily-tit">
                <span class="like-span"></span>
                <a href=""></a>
            </div>
            <div id="daily-banner">
                <ul>
                    <li>
                        <div class="daily-box">
                            <a class="daily-a" href="">
                                <div class="verticalAlign">
                                    <img src="images/example.jpg" alt="">
                                </div>
                                <span>&yen; 29.9</span>
                            </a>
                            <a class="daily-word" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                                <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                            </a>
                             <a class="buy" href="">加入购物车</a>
                        </div>
                        <div class="daily-box">
                            <a class="daily-a" href="">
                                <div class="verticalAlign">
                                    <img src="picture/daily-1.jpg" alt="">
                                </div>
                                <span>&yen; 29.9</span>
                            </a>
                            <a class="daily-word" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                                <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                            </a>
                             <a class="buy" href="">加入购物车</a>
                        </div>
                        <div class="daily-box">
                            <a class="daily-a" href="">
                                <div class="verticalAlign">
                                    <img src="picture/daily-2.jpg" alt="">
                                </div>
                                <span>&yen; 29.9</span>
                            </a>
                            <a class="daily-word" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                                <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                            </a>
                             <a class="buy" href="">加入购物车</a>
                        </div>
                        <div class="daily-box">
                            <a class="daily-a" href="">
                                <div class="verticalAlign">
                                    <img src="picture/daily-3.jpg" alt="">
                                </div>
                                <span>&yen; 29.9</span>
                            </a>
                            <a class="daily-word" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                                <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                            </a>
                             <a class="buy" href="">加入购物车</a>
                        </div>
                    </li>
                    <li>
                        <div class="daily-box">
                            <a class="daily-a" href="">
                                <div class="verticalAlign">
                                    <img src="images/example.jpg" alt="">
                                </div>
                                <span>&yen; 29.9</span>
                            </a>
                            <a class="daily-word" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                                <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                            </a>
                             <a class="buy" href="">加入购物车</a>
                        </div>
                        <div class="daily-box">
                            <a class="daily-a" href="">
                                <div class="verticalAlign">
                                    <img src="images/example.jpg" alt="">
                                </div>
                                <span>&yen; 29.9</span>
                            </a>
                            <a class="daily-word" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                                <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                            </a>
                             <a class="buy" href="">加入购物车</a>
                        </div>
                        <div class="daily-box">
                            <a class="daily-a" href="">
                                <div class="verticalAlign">
                                    <img src="images/example.jpg" alt="">
                                </div>
                                <span>&yen; 29.9</span>
                            </a>
                            <a class="daily-word" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                                <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                            </a>
                             <a class="buy" href="">加入购物车</a>
                        </div>
                        <div class="daily-box">
                            <a class="daily-a" href="">
                                <div class="verticalAlign">
                                    <img src="images/example.jpg" alt="">
                                </div>
                                <span>&yen; 29.9</span>
                            </a>
                            <a class="daily-word" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                                <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                            </a>
                             <a class="buy" href="">加入购物车</a>
                        </div>
                    </li>
                </ul>
                <a href="javascript:void(0)" class="daily-unslider-arrow prev">Previous slide</a>
                <a href="javascript:void(0)" class="daily-unslider-arrow next">Next slide</a>
            </div>
        </div> -->
        <!-- 猜你喜欢 结束-->

        <!-- 底部 开始 -->
        <#include "/common/footer.ftl" />
        <!-- 底部 结束 -->

        <!-- 对话框 -->
       

        <!-- javascript -->
        <script>
            $(function(){
                /*购物车空*/
                carNone();

                /*移除按钮*/
               /* $('.remove').click(function(event) {
                    $(this).parents('tr').remove();
                    carNone();
                });*/
                /*多选移除按钮*/
              /*  $('.remove-checked').click(function(event) {
                    $('.checkAll[checked=checked]').parents('tr').remove();
                    carNone();
                });*/
                
                /*移动到收藏夹按钮
                $('.addFavorite, .addFavoriteAll').click(function() {
                    carNone();
                });*/

                function carNone() {
                    if ($('tbody').find('tr').length==0) {
                        $('tfoot').hide();
                        $('.cart-none').fadeIn();/*显示购物车为空版块*/
                    };
                }
                /*全选
                var checkAllBtn = document.getElementById('checkAllBtn');
                var checkAllBtn2 = document.getElementById('checkAllBtn2');
                var checkAll = document.getElementsByName("checkAll");
                

                for (var i = 0; i < checkAll.length; i++) {
                    checkAll[i].onclick = function(){
                        if (this.checked) {
                            $(this).attr('checked', 'checked').parents('.check-box').addClass('checked');
                        } else {
                            $(this).removeAttr('checked').parents('.check-box').removeClass('checked');
                            checkAllBtn.checked = false;
                            $('#checkAllBtn').removeAttr('checked').parents('.check-box').removeClass('checked');
                            checkAllBtn2.checked = false;
                            $('#checkAllBtn2').removeAttr('checked').parents('.check-box').removeClass('checked');
                        }
                        if ($('.checkAll[checked=checked]').length==checkAll.length) {
                            checkAllBtn.checked = true;
                            checkAllBtn2.checked = true;
                            $('#checkAllBtn').attr('checked','checked').parents('.check-box').addClass('checked');
                            $('#checkAllBtn2').attr('checked','checked').parents('.check-box').addClass('checked');
                        };
                    };
                }

                checkAllBtn.onclick = function() {
                    if (checkAllBtn.checked) {
                        $(checkAllBtn).attr('checked','checked').parents('.check-box').addClass('checked');
                        for (var i = 0; i < checkAll.length; i++) {
                            checkAll[i].checked = true;
                            $('.checkAll').eq(i).attr('checked', 'checked').parents('.check-box').addClass('checked');
                        }
                        checkAllBtn2.checked = true;
                        $(checkAllBtn2).parents('.check-box').addClass('checked');
                    } else {
                        $(checkAllBtn).removeAttr('checked').parents('.check-box').removeClass('checked');
                        for (var i = 0; i < checkAll.length; i++) {
                            checkAll[i].checked = false;
                            $('.checkAll').eq(i).removeAttr('checked').parents('.check-box').removeClass('checked');
                        }
                        checkAllBtn2.checked = false;
                        $(checkAllBtn2).parents('.check-box').removeClass('checked');
                    }
                };
                checkAllBtn2.onclick = function() {
                    if (checkAllBtn2.checked) {
                        $(checkAllBtn2).attr('checked','checked').parents('.check-box').addClass('checked');
                        for (var i = 0; i < checkAll.length; i++) {
                            checkAll[i].checked = true;
                            $('.checkAll').eq(i).attr('checked', 'checked').parents('.check-box').addClass('checked');
                        }
                        checkAllBtn.checked = true;
                        $(checkAllBtn).parents('.check-box').addClass('checked');
                    } else {
                        $(checkAllBtn2).removeAttr('checked').parents('.check-box').removeClass('checked');
                        for (var i = 0; i < checkAll.length; i++) {
                            checkAll[i].checked = false;
                            $('.checkAll').eq(i).removeAttr('checked').parents('.check-box').removeClass('checked');
                        }
                        checkAllBtn.checked = false;
                        $(checkAllBtn).parents('.check-box').removeClass('checked');
                    }
                };*/
            })
        </script>
        
        <script>
        		var checkAllBtn = document.getElementById('checkAllBtn');
                var checkAllBtn2 = document.getElementById('checkAllBtn2');
                var checkAll = document.getElementsByName("checkAll");
        		var checkSelected = $(".checkAll:checked");
        		if(checkAll.length == checkSelected.length){
        			  $(checkAllBtn).parents('.check-box').addClass('checked');
        			  $(checkAllBtn).attr("checked",true);
        			  $(checkAllBtn).val("y");
        			  $(checkAllBtn2).parents('.check-box').addClass('checked');
        			  $(checkAllBtn2).val("y");
        			  $(checkAllBtn2).attr("checked",true);
        		}else{
        			 $(checkAllBtn).parents('.check-box').removeClass('checked');
        			 $(checkAllBtn).val("n");
        			 $(checkAllBtn).attr("checked",false);
        			 $(checkAllBtn2).parents('.check-box').removeClass('checked');
        			 $(checkAllBtn2).val("n");
        			 $(checkAllBtn2).attr("checked",false);
        		}
        		
        </script>

        <script src="${rc.contextPath}/static/shop/js/unslider.js"></script><!-- banner JS -->
        <script>
            $(function(){
                /*#daily-banner*/
                var unslider_daily = $('#daily-banner').unslider({
                    autoplay:false,
                    speed: 500,
                    dots: true
                });
                $('#daily-banner .prev').click(function(event) {
                    var data = unslider_daily.data('unslider');
                    data.prev();
                });
                $('#daily-banner .next').click(function(event) {
                    var data = unslider_daily.data('unslider');
                    data.next();
                });

            })
        </script>

       <#include "/common/other.ftl" />
    </body>
</html>
