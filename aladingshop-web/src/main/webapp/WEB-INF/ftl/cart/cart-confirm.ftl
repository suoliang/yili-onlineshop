<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>阿拉丁玛特 - 确认订单</title>
        <#include "common/common.ftl" />
        <script type="text/javascript">
        	var contextPath="${rc.contextPath}";
        </script>
        <script type="text/javascript" src="${rc.contextPath}/static/web-js/adress.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/static/web-js/order.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/static/web-js/hex_md5.js"></script>
    </head>
    <body class="cart-confirm">

        <!-- 顶部导航 开始 -->
       	<#include "/common/topbar.ftl" />
        <!-- 顶部导航 结束 -->

        <div class="cart-top container">
            <a href="${rc.contextPath}/home" class="logo"></a>
            <ul>
                <li>我的购物车<span></span></li>
                <li class="active">&emsp;&emsp;填写订单信息<span></span></li>
                <li>购物成功</li>
            </ul>
        </div>
        <div class="container">
	        <div class="cart-confirm-top fl wp100">
	            <h3>确认收货信息</h3>
	            <ul>
	               <#if addressList?? >
	                <#list addressList as address>
	                  <li>
	                    <div class="address-box">
	                        <div class="check-box fl <#if address.isDefault=='y'> checked</#if>">
	                            <i></i>
	                            <input name="chose-address" class="" type="radio" <#if address.isDefault=='y'>checked="checked"</#if> value="${address.addressId}"  />
	                        </div>
	                        <p>地址：<span class="address">${address.addressInfo}</span></p>
	                        <p>姓名：<span class="name">${address.name}</span></p>
	                        <p>电话：<span class="phone">${address.phone}</span></p>
	                        <div class="action">
	                          	<a class="cancel" href="javascript:void(0)">删除</a>
	                            <a class="edit-btn" href="javascript:initAddress('${address.addressId}')">修改</a>
	                            <a href="javascript:editDefaultAddress('${address.addressId}')">设为默认</a>
	                            <div class="confirm-box">
	                                <p>确定删除该地址吗？</p>
	                                <a class="confirm-no" href="javascript:void(0)">取消</a>
	                                <a href="javascript:deleteAddress('${address.addressId}')">确定</a>
	                            </div>
	                        </div>
	                    </div>
	                 </li>
	                </#list> 
	               </#if> 
	                
	              
	            </ul>
	            <div class="address-add oh">
	                <div class="address-add-btn">
	                    <div class="check-box fl">
	                        <i></i>
	                        <input name="chose-address" class="" type="radio" >
	                    </div>
	                    <p>使用新地址</p>
	                </div>
	                <div class="add-box add-address-box">
	                  <form id="createAddressForm">
	                    <p>
	                        <label for=""><span class="red">*</span>&emsp;所在地：</label>
	                        <select name="provinceId" id="newProvince">
	                        </select>
	                        <select name="cityId" id="newCity">
	                        </select>
	                        <select name="districtId" id="newArea">
	                        </select>
	                        <span class="error">* 请选择地址！</span>
	                    </p>
	                    <p>
	                        <label for=""><span class="red">*</span>&emsp;详细地址：</label>
	                        <input class="detail-address-input" type="text" name="addressInfo">
	                        <span class="error">* 请填写详细地址！</span>
	                    </p>
	                    <p>
	                        <label for=""><span class="red">*</span>&emsp;收件人：</label>
	                        <input type="text" name="name">
	                        <span class="error">* 请填写收件人！</span>
	                    </p>
	                    <p>
	                        <label for=""><span class="red">*</span>&emsp;联系电话：</label>
	                        <input type="text" name="phone">
	                        <span class="error">* 请填写联系电话！</span>
	                    </p>
	                    <p>
	                        <a class="yes" href="javascript:void(0)">保存</a> 
	                        <a class="no" href="javascript:void(0)">取消</a>
	                      <!--  <a class="setDefault" href="javascript:modifySave(1,'createAddressForm')">设为默认地址</a>-->
	                    </p>
	                   </form>
	                </div>
	            </div>
	        </div>
	    </div>

        <div class="pay-way container">
            <input id="payWay" type="hidden" checked="checked" value="alipay">
            <h3>支付方式</h3>
            <a class="alipay active" href="javascript:void(0)" title="alipay">
                <span></span>
                <p>支付宝</p>
                <i>推荐有支付宝账户者使用</i>
                <s></s>
            </a>
          	<a class="weichat" href="javascript:void(0)" title="weichat">
                <span></span>
                <p>微信支付</p>
                <i>推荐使用</i>
                <s></s>
            </a> 
            <a class="bank" href="javascript:void(0)" title="bank">
                <span></span>
                <p>银联支付</p>
                <i>方便快捷</i>
                <s></s>
            </a>
        </div>

        <div class="cart-table container mT0">
            <table>
                <thead>
                    <tr>
                        <th class="w460">商品信息</th>
                        <th class="w190">单价</th>
                        <th class="w240">数量</th>
                        <th class="w246">小计</th>
                    </tr>
                </thead>
                <tbody>
              <#if gotoOrderDto??>
                <#list gotoOrderDto.orderLineItems as item>  
                    <tr>
                        <td>
                            <div class="product fl">
                                <a href="${rc.contextPath}/sku/skuDetail?skuCode=${item.skuCode}" target="_blank"><img src="${item.imgPath}" height="183" width="183" alt="${item.skuName}"></a>
                                <div class="product-r">
                                    <p>${item.skuName}</p>
                                 <!--   <span>品牌：潘婷</span>
                                    <span>类型：柔顺</span> -->
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
                              <!--  <p>&yen; 188.00</p>-->
                                <span>&yen; ${item.currentPrice}</span>
                            </div>
                        </td>
                        <td>
                            <div class="count">${item.requestedQty}</div>
                        </td>
                        <td>
                            <div class="action">&yen; ${item.currentPriceTotal}</div>
                        </td>
                    </tr>
               </#list>
              </#if>     
                  
                </tbody>

                <tfoot>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>
                            <div class="tfoot-money">
                                <span>共 ${gotoOrderDto.quantityTotal} 件</span>
                                <p>实付金额：&yen; <b id="totalActual">${gotoOrderDto.totalActual}</b></p>
                                <a href="javascript:void(0)" id="submitOrderBtn" onclick="createOrder('${gotoOrderDto.payOffId}')">提交订单</a>
                            </div>
                        </td>
                    </tr>
                </tfoot>
                <tfoot>
                    <tr>
                        <td colspan="2">
                            <div class="discount-wrap">
                            
                             	<div class="discount-box oh">
                                    <div class="check-box fl">
                                        <i></i>
                                        <input name="" class="" id="txtPointCheckBox" type="checkbox" onclick="usePoint('${gotoOrderDto.payOffId}','${gotoOrderDto.province}',this);">
                                    </div>
                                    <b>您有&nbsp;${gotoOrderDto.canEpoints}&nbsp;积分可用</b>
                                    <span>-${gotoOrderDto.epointsMoney}&nbsp;元</span>
                                </div>
                            
                                <div class="discount-box oh">
                                    <div class="check-box fl">
                                        <i></i>
                                    	<input name="" class="" type="checkbox" id="checkDiscount"  value="" onchange="useCoupon('txtCoupon','${gotoOrderDto.payOffId}','${gotoOrderDto.province}',this)" />
                                    </div>
                                    <b>
                                        <label for="">使用优惠券：</label>
                                        <input type="text" placeholder="请输入优惠码" id="txtCoupon" />
                                      <!--  <button >确定</button>-->
                                        <!-- <label for="" class="red mR20">优惠码错误！</label> -->
                                    </b>
                                    <span>-<span id="couponMoneyTxt">0.00</span>&nbsp;元</span>
                                </div>
                               	 <div id="aladingCard">
                               	 	<!--div class="discount-box oh">
                                        <div class="check-box fl">
                                            <i></i>
                                            <input name="" class="" type="checkbox" colspan="2" id="checkInterest" onchange="useInterest('interestCardNo','interestPassword','totalActual','${gotoOrderDto.payOffId}','${gotoOrderDto.province}',this)">
                                        </div>
                                        <b>
                                            <label for="">使用益多宝收益：</label>
                                            <select name="" id="interestCardNo">
                                            	<#list cardVo.interestCards as card>
                                            		<option value="${card.code}">${card.cardNo}</option>
                                            	</#list>
                                            </select>
                                            <input type="password" id="interestPassword" maxlength="6" placeholder="请输入卡密码">
                                        </b>
                                        <b id="inSubduction">&nbsp;-&nbsp;0.00&nbsp;元</b>
                                    </div-->
                                    <#if cardVo.rebateCards?exists && cardVo.rebateCards?size&gt;0>
                                    <div class="discount-box oh">
                                        <div class="check-box fl">
                                            <i></i>
                                            <input name="" class="" type="checkbox" id="checkRebate" onchange="useRebate('rebateCardNo','rebatePassword','totalActual','${gotoOrderDto.payOffId}','${gotoOrderDto.province}',this)">
                                        </div>
                                        <b>
                                            <label for="">使用益多宝赠券：</label>
                                             <select name="" id="rebateCardNo">
                                            	<#list cardVo.rebateCards as card>
                                            		<option value="${card.code}">${card.cardNo}</option>
                                            	</#list>
                                            </select>
                                            <input type="password" id="rebatePassword" maxlength="6" placeholder="请输入卡密码">
                                        </b>
                                        <b id="reSubduction">&nbsp;-&nbsp;0.00&nbsp;元</b>
                                    </div>
                                    </#if>
                                </div>
                                <p>运费：<span id="txtFreightPrice">${gotoOrderDto.freight}</span>元，满68包邮</p>
                            </div>
                        </td>
                        <td colspan="2">
                            <div class="message">
                                <label for="">买家留言：</label>
                                <textarea name="" id="txtMemo" cols="" rows="" placeholder="您有什么特殊要求么，可以告诉我们哦！"
                                	size="200"></textarea>
                            </div>
                        </td>
                    </tr>
                </tfoot>
            </table>
        </div>

        <!-- 底部 开始 -->
        <script src="${rc.contextPath}/static/shop/public/footer.js"></script>
        <!-- 底部 结束 -->

        <!-- 对话框修改地址 -->
        <div class="modal_backup edit-address">
            <div class="add-box">
              <form id="editAddressForm">
            	<input type="hidden" name="addressId" id="addressId" value="" />
                <h3>修改收货地址</h3>
                <p>
                    <label for=""><span class="red">*</span>&emsp;所在地：</label>
                    <select name="provinceId" id="cmbProvince">
                    </select>
                    <select name="cityId" id="cmbCity">
                    </select>
                    <select name="districtId" id="cmbArea">
                    </select>
                    <span class="error">* 请选择地址！</span>
                </p>
                <p>
                    <label for=""><span class="red">*</span>&emsp;详细地址：</label>
                    <input class="detail-address-input" id="cmbAddress" type="text"  name="addressInfo" />
                    <span class="error">* 请填写详细地址！</span>
                </p>
                <p>
                    <label for=""><span class="red">*</span>&emsp;收件人：</label>
                    <input type="text" id="cmbContactor" name="name" />
                    <span class="error">* 请填写收件人！</span>
                </p>
                <p>
                    <label for=""><span class="red">*</span>&emsp;联系电话：</label>
                    <input type="text" id="cmbPhone" name="phone" />
                    <span class="error">* 请填写联系电话！</span>
                </p>
                <p>
                    <a class="yes" href="javascript:void(0)">保存</a>
                    <a class="no" href="javascript:void(0)">取消</a>
                   <!-- <a class="setDefault" href="javascript:void(0)">设为默认地址</a>-->
                </p>
              </form>
            </div>
        </div>

        <!-- js -->
        <script>
            $(function(){
                /*选择与新增地址*/
                var address_check = $('.cart-confirm-top').find('input[type=radio]');
                var add_new_address = $('.address-add-btn').find('input[type=radio]');
                var old_address = $('.cart-confirm-top ul').find('input[type=radio]');
                var toggle = $('.add-address-box');
                var add_box = $('.add-box');
                var edit_address = $('.edit-address');

                address_check.click(function(event) {
                    address_check.checked = false;
                    address_check.removeAttr('checked').parents('.check-box').removeClass('checked');

                    this.checked = true;
                    $(this).attr('checked', 'checked').parents('.check-box').addClass('checked');
                });

                add_new_address.click(function(event) {
                	addressInit("newProvince","newCity","newArea");
                    toggle.slideDown();
                });
                old_address.click(function(event) {
                    toggle.slideUp();
                });

                /*保存按钮*/
                add_box.find('.yes').click(function(event) {
                    var thisVal = add_box.find('select,input');
                    thisVal.each(function(index, el) {
                        if($(this).val()==""){
                            $(this).addClass('thisError').siblings('.error').fadeIn();
                        } else {
                            $(this).removeClass('thisError').siblings('.error').fadeOut();
                        }
                    });
                });

                /*新增地址保存按钮*/
                toggle.find('.setDefault').click(function(event) {
                    var error_num = toggle.find('.thisError').length;
                    if (error_num==0) {
                        toggle.slideUp();
                        modifySave(1,'createAddressForm');
                    };
                });
                /*新增地址保存并设为默认地址按钮*/
                toggle.find('.yes').click(function(event) {
                    var error_num = toggle.find('.thisError').length;
                    if (error_num==0) {
                        toggle.slideUp();
                        modifySave(1,'createAddressForm');
                    };
                });
                
                
                /*新增地址取消按钮*/
                toggle.find('.no').click(function(event) {
                    toggle.slideUp();
                    add_new_address.checked = false;
                    $(add_new_address).removeAttr('checked').parents('.check-box').removeClass('checked');
                });

                /*修改地址按钮*/
                $('.edit-btn').click(function(event) {
                    edit_address.css('visibility', 'visible').stop().animate({'opacity':'1'}, 200);
                    edit_address.find('.add-box').stop().animate({'top':'40%'}, 200);
                 //   alert(edit_address.find('.detail-address-input').val());
                    
                });

                /*修改地址保存按钮*/
                edit_address.find('.yes').click(function(event) {
                    var error_num = edit_address.find('.thisError').length;
                    if (error_num==0) {
                        // 修改成功后操作
                        hide_edit_box();
                        
                        modifySave(0,'editAddressForm');
                    };
                });
                /*修改地址取消按钮*/
                function hide_edit_box(){
                    edit_address.find('.add-box').stop().animate({'top':'20%'}, 200);
                    edit_address.stop().animate({'opacity':'0'}, 200);
                    setTimeout(function(){
                        edit_address.css('visibility', 'hidden')
                    },200);
                }
                edit_address.find('.no').click(function(event) {
                    hide_edit_box();
                });
            })

            /*选择支付方式*/
            $('.pay-way').find('a').click(function(event) {
                $(this).addClass('active').siblings().removeClass('active');
                $('#payWay').val($(this).attr('title'));
            });

            /*选择优惠*/
            var discount_check = $('.discount-box').find('input[type=checkbox]');
            discount_check.click(function(event) {
                if(this.checked == true){
                    $(this).attr('checked', 'checked').parents('.check-box').addClass('checked');
                } else {
                    $(this).removeAttr('checked').parents('.check-box').removeClass('checked');
                }
            });
        </script>

    </body>
</html>
