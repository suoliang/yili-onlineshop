<!DOCTYPE html>
<html>
    <head>
        <title>确认订单</title>
        <!-- 通用部分 开始 -->
        <#include "/common/common.ftl" />
        <!-- 通用部分 结束 -->
        <script type="text/javascript">
        	var _ContextPath="${rc.contextPath}";
        </script>
    </head>
    <body id="cart-confirm">

        <div class="container">

            <div class="head mB10">
                <a href="javascript:history.go(-1)" class="a-left">
                    <span class="back"></span>
                </a>
                <p>确认订单</p>
            </div>
			<a href="${rc.contextPath}/address/list?type=chooseAddress&time=${time?c!'1'}">
	            <div class="address">
	                <div class="address-box">
	                    <div class="icon-wrap">
	                        <span class="icon-address"></span>
	                    </div>
	                    <#if addressList?? && addressList?size&gt;0>
		                	<#list addressList as address>
		                	<#if address.isDefault=='y'>
			                        <div class="address-detail fl">
			                            <span class="name">收件人：${address.name}</span>
			                            <span class="phone">电话：${address.phone}</span>
			                            <p>地址：${address.provinceName}${address.cityName}${address.districtName}${address.addressInfo}</p>
			                        </div>
			                        <div class="address-btn">
			                            <span></span>
			                        </div>
			                <input type="hidden" id="addressId" value="${address.addressId}">
		                    </#if>
		                </#list>
		               <#else>
		               	 <p style="margin-top:-4px;"><br><br>请选择地址！</p>
		               </#if>
	                </div>
	            </div>
			</a>
            <div class="cart-confirm-a fl wp100">
                <span class="icon-house"></span>
                <h4>阿拉丁商品</h4>
                <p>共计 <i class="count-num">${gotoOrderDto.quantityTotal}</i> 件</p>
            </div>
			<#if gotoOrderDto??>
                <#list gotoOrderDto.orderLineItems as item>
		            <div class="cart-confirm-b fl wp100">
		                <a href="${rc.contextPath}/sku/skuDetailStatic.htm?skuCode=${item.skuCode}&v=${EnvironmentConstant.DEPLOY_VERSION}"><img src="${item.imgPath}"></a>
		                <p>${item.skuName}</p>
		                <#if item.color!=''>
	                    	<span>颜色：${item.color}</span>
	                    </#if>
	                    <#if item.size!=''>
	                    	<span>类型：${item.size}</span>
	                    </#if>
		                <i>&yen; ${item.currentPrice}</i>
		                <div class="modal-c fl wp100 textR size12">数量:${item.requestedQty}</div>
		            </div>
		    	</#list>
            </#if>
            <div class="cart-confirm-c fl wp100">
                <textarea maxlength="200" name="" id="txtMemo" placeholder="给卖家留言..."></textarea>
            </div>
            <div class="cart-confirm-d fl wp100">
                <div id="" class="form-group">
                    <ul class="list mB0" id="choseTime">
                        <input type="hidden">
                        <li>
                            <p>配送时间：</p>
                            <span>周一至周日（任意时间）</span>
                            <i class="right"></i>
                        </li>
                    </ul>
                    <div class="input-group coupon-wrap">
                        <label for="">代金券：</label>
                        <input name="" id="txtCoupon" type="text" placeholder="请输入代金券号码" autocomplete="off">
                        <div class="ios-btn-wrap">
                            <div class="ios-btn">
                                <input type="checkbox" id="checkDiscount" onchange="useCoupon('txtCoupon','${gotoOrderDto.payOffId}',<#if gotoOrderDto??>'${gotoOrderDto.province!''}'</#if>,this)">
                                <i></i>
                            </div>
                        </div>
                    </div>
                    <div class="input-group coupon-password">
                        <!--<label for="">密码：</label>
                        <input name="" type="password" placeholder="请输入密码" autocomplete="off">-->
                        <div class="cut">-<i id="couponMoneyTxt">0.00</i>元</div>
                    </div>
                    <!--<div class="input-group">
                        <label for=""><i class="label-i">您有${gotoOrderDto.canEpoints}积分可用</i>&emsp;&emsp;-${gotoOrderDto.epointsMoney}元</label>
                        <input id="" name="password" type="password" placeholder="" autocomplete="off">
                        <div class="ios-btn-wrap">
                            <div class="ios-btn">
                                <input type="checkbox" id="txtPointCheckBox" onclick="usePoint('${gotoOrderDto.payOffId}',<#if gotoOrderDto??>'${gotoOrderDto.province!''}'</#if>,this);">
                                <i></i>
                            </div>
                        </div>
                    </div>
                    <div class="input-group">
                        <label for="" class="wp100"><i class="label-i">您有 8 元红包可用</i>&emsp;&emsp;<i class="cut-b">-4元</i></label>
                        <input id="" name="password" type="password" placeholder="" autocomplete="off">
                        <div class="ios-btn-wrap">
                            <div class="ios-btn">
                                <input type="checkbox">
                                <i></i>
                            </div>
                        </div>
                    </div> -->
                </div>
            </div>
            <div class="cart-confirm-e fl wp100">
                <p>合计：<i class="red">&yen;${gotoOrderDto.totalActual}</i></p>
                <span>运费：&yen;<span id="txtFreightPrice">${gotoOrderDto.freight}</span>（满 68 包邮）</span>
                <button id="submitOrderBtn" onclick="createOrder('${gotoOrderDto.payOffId}')">提交订单</button>
            </div>

        </div><!-- /.container -->

        <div id="choseTimeBox" class="modal-wrap">
            <ul class="list mB0 fl wp100 radio">
                <li>
                    <p>周一至周日（任意时间）</p>
                    <div class="check-box fr checked">
                        <i></i>
                        <input id="" class="checkItem" type="checkbox" value="1" checked="checked">
                    </div>
                </li>
                <li>
                    <p>周一至周五（工作日）</p>
                    <div class="check-box fr">
                        <i></i>
                        <input id="" class="checkItem" type="checkbox" value="2">
                    </div>
                </li>
                <li>
                    <p>周六至周日（双休日）</p>
                    <div class="check-box fr">
                        <i></i>
                        <input id="" class="checkItem" type="checkbox" value="3">
                    </div>
                </li>
            </ul>
        </div>

        <script>
        $(function(){
            /*选择收货时间*/
            var timeInput = $('#choseTime').find('input');
            var timeSpan = $('#choseTime').find('span');
            var choseTimeBox = $('#choseTimeBox');
            $('#choseTime').click(function(event) {
                choseTimeBox.fadeIn();
                choseTimeBox.find('.list').velocity({bottom:0});
            });
            choseTimeBox.find('li').click(function(event) {
                timeInput.val($(this).find('input').val());
                timeSpan.html($(this).find('p').html());
            });
            choseTimeBox.click(function(event) {
                choseTimeBox.find('.list').velocity({bottom:"-100%"});
                choseTimeBox.fadeOut();
            });
            
            /*选择优惠*/
	        var coupon_check = $("#checkDiscount");
	        coupon_check.click(function(event) {
	            if(this.checked == true){
	                $(this).attr('checked', 'checked').parents('.check-box').addClass('checked');
	            } else {
	                $(this).removeAttr('checked').parents('.check-box').removeClass('checked');
	            }
	        });
            
        })

        /** 使用积分*/
		function usePoint(payOffId,areaCode,obj){
			var isUseTax = "n";
			var isUsePoint = "n";
			if(obj.checked){
				isUsePoint = "y";
			}
			var url = _ContextPath+"/order/usePoint";
			$.post(url,{
				payOffId : payOffId,
				isUsePoint : isUsePoint,
				areaCode : areaCode,
				isUseTax : isUseTax,
				time : new Date().getTime()},
				function(data){
					if(data.responseCode==0){
						$(".red").text(parseFloat(data.data.totalActual).toFixed(2));
					}
			});
		}
		
		/** 使用优惠券*/
		function useCoupon(txtCoupon,payOffId,areaCode,obj){
			var isUseCouponCard = "n";
			if(obj.checked){
				isUseCouponCard = "y";
			}
			var couponCode = $("#"+txtCoupon).val();
			if($.trim(couponCode) ==''){
				  alert("优惠券编号不能为空！");
				  $("#checkDiscount").removeAttr('checked').parents('.check-box').removeClass('checked');
				  return;
			}

			var url = _ContextPath + "/order/useCoupon";

			$.post(url,{
				couponCode : couponCode,
				payOffId : payOffId,
				areaCode : areaCode,
				isUseCouponCard : isUseCouponCard,
				time : new Date().getTime()},
				function(data){
					if(data.responseCode==0){
						  $("#couponMoneyTxt").html(data.data.couponMoney);
						  $("#txtFreightPrice").html(data.data.freightPrice);
						  var newTotalActual = parseFloat(data.data.totalActual);
						  if(newTotalActual<0){
								newTotalActual = "0.00";
						  }
						  $(".red").text(newTotalActual.toFixed(2));
					}else{
						 alert(data.msg);
						 $("#"+txtCoupon).attr("disable","disable");
						 $("#checkDiscount").removeAttr('checked').parents('.check-box').removeClass('checked');
						 return;
					}
			});
		}

		function createOrder(payOffId){
			var memo = $.trim($("#txtMemo").val());
			var sendDate = $('.check-box.checked').find('input').val();
			var addressId = $("#addressId").val();
			if(addressId == '' || addressId== undefined){
				alert("请选择收货地址！");
				return;
			}
			var  usePoint = $("#txtPointCheckBox").attr('checked');
			var isUsePoint = (usePoint==null || usePoint==undefined || usePoint==''   )?false:true;
			var  useCoupon = $("#checkDiscount").attr('checked');
			var isUseCoupon = (useCoupon==null || useCoupon==undefined || useCoupon==''  )?false:true;
			$("#submitOrderBtn").attr("onclick","");

			$.post(_ContextPath + "/order/createOrder",{
				payOffId : payOffId,
				addressId: addressId,
				isUsePoint:isUsePoint==true?'y':'n',
				isUseCoupon:isUseCoupon==true?'y':'n',
				memo: memo,
				sendDate:sendDate,
				isRedUse:'n',
				time : new Date().getTime()},
				function(data){
					if (data.responseCode=="0") {
						var orderCode = data.data.orderCode;
						if(orderCode == undefined || orderCode==null || $.trim(orderCode).length<=0){
							alert('0',"支付失败!订单号为空!");
							window.location.href=_ContextPath+"/cart/list.html";
						}else{
							var sid = data.data.sid;
							var orderCode = data.data.orderCode;
							var temp = document.createElement("form");
							temp.action = _ContextPath + "/order/goPay?time="+new Date().getTime();
							temp.method = "post";
							temp.style.display = "none";

							var optSid = document.createElement("input");
							optSid.name="sid";
							optSid.value=sid;
							temp.appendChild(optSid);

							var optOrderCode = document.createElement("input");
							optOrderCode.name="orderCode";
							optOrderCode.value=orderCode;
							temp.appendChild(optOrderCode);

							document.body.appendChild(temp);
							temp.submit();
						}
		    		}else if(data.responseCode==201){
						window.location.href=_ContextPath+"/login/index";
					}else{
						window.location.href=_ContextPath+"/cart/list?time="+new Date().getTime();
					}
				});
		}
        </script>
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>
