<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>我的订单 - 收货地址</title>
       	<#include "/common/common.ftl" />
       	<script type="text/javascript" src="${rc.contextPath}/static/web-js/adress.js"></script>
    </head>
    <body class="assess">

        <!-- 顶部导航 开始 -->
        <#include "/common/topbar.ftl" />
        <!-- 顶部导航 结束 -->

        <!-- 头部搜索版块 开始 -->
       <#include "/common/header.ftl" />
        <!-- 头部搜索版块 结束 -->

        <!-- 菜单 开始 -->
       <#include "/common/menu.ftl" />

        <div class="container oh">
            <div class="mT20 order-wrap fl mB20">
                <!-- 个人中心左侧菜单开始 -->
                <#include "/common/centerMenu.ftl" />
            	<!-- 个人中心左侧菜单开始 -->

                <div class="cart-table">
                    <table>
                        <thead>
                            <tr>
                                <th class="fl w106 textL mL64">收货人</th>
                                <th class="fl w380 textL">收货地址</th>
                                <th class="fl w196 textL">联系电话</th>
                                <th class="fl textL mL10">操作</th>
                            </tr>
                        </thead>
                    </table>

                    <div class="cart-confirm-top fl wp100">
                        <ul>
                        
                       <#if addressList?? >
                        <#list addressList as address>
                            <li>
                                <div class="address-box">
                                    <div class="check-box fl <#if address.isDefault=='y'> checked</#if>"">
                                        <i></i>
                                        <input name="chose-address" class="" type="radio"<#if address.isDefault=='y'>checked="checked"</#if> value="${address.addressId}" />
                                    </div>
                                    <p><span class="name">${address.name}</span></p>
                                    <p><span class="address">${address.addressInfo}</span></p>
                                    <p><span class="phone">${address.phone}</span></p>
                                    <div class="action">
                                        <a href="javascript:editDefaultAddress('${address.addressId}')">设为默认地址</a>
                                        <a class="edit-btn" href="javascript:initAddress('${address.addressId}')">修改</a>
                                        <a class="cancel" href="javascript:void(0)">删除</a>
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
                        <div class="address-add">
                            <div class="address-add-btn">
                                <div class="check-box fl">
                                    <i></i>
                                    <input name="chose-address" class="" type="radio">
                                </div>
                                <p>使用新地址</p>
                            </div>
                            <div class="add-box add-address-box">
                              <form id="createAddressForm">
                                <p>
                                    <label for=""><span class="red">*</span>&emsp;所在地：</label>
                                    <select name="provinceId" id="newProvince" />
			                        </select>
			                        <select name="cityId" id="newCity" />
			                        </select>
			                        <select name="districtId" id="newArea" />
			                        </select>
                                    <span class="error">* 请选择地址！</span>
                                </p>
                                <p>
                                    <label for=""><span class="red">*</span>&emsp;详细地址：</label>
                                    <input class="detail-address-input" type="text" name="addressInfo" />
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
                                    <a class="yes" href="javascript:void(0)">保存并使用</a>
                                    <a class="no" href="javascript:void(0)">取消</a>
                                    <a class="setDefault" href="javascript:void(0)">设为默认地址</a>
                                </p>
                              </form>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>

        <!-- 底部 开始 -->
        <#include "/common/footer.ftl" />
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

        <!-- javascript -->
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
        </script>
    </body>
</html>
