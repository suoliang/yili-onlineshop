<!DOCTYPE html>
<html>
    <head>
        <title>选择地址</title>

        <!-- 通用部分 开始 -->
        <#include "/common/common.ftl" />
         <script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
        <!-- 通用部分 结束 -->
    </head>
    <body id="address">

        <div class="container">

            <div class="head mB10">
                <a href="javascript:history.go(-1)" class="a-left">
                    <span class="back"></span>
                </a>
                <p>收货地址</p> 
                <a href="javascript:void(0)" class="a-right">
                    <span class="edit">编辑</span>
                </a>
            </div>

            <div class="address radio">
            	 <#if addressList?? >
                        <#list addressList as address>
			                <div class="address-box">
			                    <div class="check-box fl <#if address.isDefault=='y'> checked</#if>"">
                                    <i></i>
                                    <input onclick="jumpToConfirm('${address.addressId}');" name="chose-address" class="checkItem" type="radio"<#if address.isDefault=='y'>checked="checked"</#if> value="${address.addressId}" />
                                </div>
                                <a href="javascript:void(0)" onclick="jumpToConfirm('${address.addressId}');">
			                        <div class="address-detail fl">
			                            <span class="name">收件人：${address.name}</span>
			                            <span class="phone">电话：${address.phone}</span>
			                            <p>地址：${address.provinceName}${address.cityName}${address.districtName}&nbsp;${address.addressInfo}</p>
			                        </div>
			                        <div class="address-btn">
			                            <span></span>
			                        </div>
		                        </a>
			                    <a href="javascript:void(0)" class="address-delete-btn"><input type="hidden" value="${address.addressId}">删除</a>
			                    <a href="javascript:toModifyAddress('${address.addressId}');" class="address-edit-btn">编辑</a>
			                </div>
	                 </#list> 
                 </#if>
            </div>

            <div class="btn-wrap">
                <a href="javascript:toAddAddress()">新增地址</a>
            </div>

        </div><!-- /.container -->

        <script>
        $(function(){
        	hideRightBtn();
            $('#address').find('.edit').click(function(event) {
                if ($(this).html()==="编辑") {
                    $(this).html("取消");
                } else {
                    $(this).html("编辑");
                };
                $('.address-box').toggleClass('open');
            });

            $('.address-delete-btn').click(function(event) {
            	var currentbtn=$(this);
            	var addressId=$(this).find("input").val();
            	var url="${rc.contextPath}/address/deleteAddress?addressId="+addressId+"&time="+new Date().getTime();
            	$.ajax({
			        type: "POST",
			        async:false,
			        dataType: "json",
			        url: url,
			        success: function (data) {
			    		if (data.responseCode=="0") {
			    			currentbtn.parents('.address-box').velocity({height:0,opacity:0},{duration: 200}).velocity({border:0},{complete:function(){$(this).remove();hideRightBtn();}});
			    		}
			        }
			    });	
            });
        })
        
        function toModifyAddress(addressId){
        	var type=GetQueryString("type");
			if(type !=null && type.toString().length>0){
	        	window.location.href="${rc.contextPath}/address/toModifyAddress?addressId="+addressId+"&type=chooseAddress&time="+new Date().getTime();
				return;
			}
        	window.location.href="${rc.contextPath}/address/toModifyAddress?addressId="+addressId+"&time="+new Date().getTime();
        }
        
        function toAddAddress(){
        	var type=GetQueryString("type");
			if(type !=null && type.toString().length>0){
	        	window.location.href="${rc.contextPath}/address/toAddAddress?type=chooseAddress&time="+new Date().getTime();
				return;
			}
        	window.location.href="${rc.contextPath}/address/toAddAddress?time="+new Date().getTime();
        } 
        
        /**获取浏览器参数*/
        function GetQueryString(name)
		{ 
	       var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); 
	       var r = window.location.search.substr(1).match(reg); 
	       if(r!=null)return  unescape(r[2]); return null; 
		}
        
        function jumpToConfirm(addressId){
        	var type=GetQueryString("type");
			if(type !=null && type.toString().length>0){
			   if(type == 'chooseAddress'){
			   		$.ajax({
				        type: "POST",
				        async:false,
				        dataType: "json",
				        url: '${rc.contextPath}/address/default?addressId='+addressId+'&time='+new Date().getTime(),
				        success: function (data) {
				    		if (data.responseCode=="0") {
				    			window.location.href="${rc.contextPath}/order/goCartConfirm?time="+new Date().getTime();
				    		}
				        }
			    	});	
			   }
			   return;
			}
        	
        }
        function hideRightBtn(){
            if (!$('#address').find('.address-box').length) {
                $('.a-right').hide();
            };
        }
        </script>
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>
