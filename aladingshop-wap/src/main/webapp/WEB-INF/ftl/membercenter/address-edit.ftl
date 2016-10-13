<!DOCTYPE html>
<html>
    <head>
        <title>修改地址</title>
        <!-- 通用部分 开始 -->
        <#include "/common/common.ftl" />
        <script type="text/javascript" language="javascript">
            var _ContextPath = "${rc.contextPath}";
        </script>
        <!-- 通用部分 结束 -->
    </head>
    <body id="">

        <div class="container">

            <div class="head mB10">
                <a href="javascript:history.go(-1)" class="a-left">
                    <span class="back"></span>
                </a>
                <p>修改地址</p>
                <a href="javascript:submitForm();" class="a-right">
                    <span>保存</span>
                </a>
            </div>
            <form id="addressAddForm" class="form-group" action=""  method="post">
                <input type="hidden" name="addressId" value="${addressDto.addressId}">
                <div class="input-group key-btn-wrap">
                    <label for="name">收货人姓名：</label>
                    <input name="name" id="name" type="text" value="${addressDto.name}" autocomplete="off">
                </div>
                <div class="input-group">
                    <label for="name">手机号码：</label>
                    <input name="phone" id="phone" type="number" value="${addressDto.phone}" autocomplete="off">
                </div>
                <div class="input-group" id="area">
                    <label for="name">所在地区：</label>
                    <input name="area" id="area" type="text" value="${addressDto.provinceName}  ${addressDto.cityName}  ${addressDto.districtName}" autocomplete="off">
                    <input name="provinceId" id="province" type="hidden" value="${addressDto.provinceId}">
                    <input name="cityId" id="city" type="hidden" value="${addressDto.cityId}">
                    <input name="districtId" id="district"type="hidden" value="${addressDto.districtId}">
                    <i class="right"></i>
                    <s></s>
                </div>
                <div class="input-group">
                    <label for="name">详细地址：</label>
                    <input name="addressInfo" id="addressInfo" type="text" autocomplete="off" value="${addressDto.addressInfo}">
                </div>
                <div class="input-group">
                    <label for="name">设置默认地址</label>
                    <input type="text" autocomplete="off">
                    <div class="ios-btn-wrap">
                        <div class="ios-btn">
                            <input id="isDefault" name="isDefault" type="checkbox"  value="${addressDto.isDefault}">
                            <i></i>
                        </div>
                    </div>
                </div>
            </form>

            <ul class="province address-ul"></ul>
            <ul class="city address-ul"></ul>
            <ul class="county address-ul"></ul>

        </div><!-- /.container -->

    <script>
        $(function(){
            var isDefault = $('#isDefault');
            if("y"==isDefault.val()){
                $('.ios-btn').addClass("thisChecked");
                $('.ios-btn').find('input').attr('checked','checked');
            }
        });
        $('.ios-btn').click(function(){
            var isDefault = $('#isDefault');
            if(isDefault.attr('checked')=="checked"){
                isDefault.val('y');
            } else {
                isDefault.val('n');
            }
        });
        
    
        $('#area').click(function() {
            showProvince();
        });

        function showAddressAddForm() {
            var item = $('#addressAddForm, .province, .city, .county');
            var addressAddForm = $('#addressAddForm');
            item.hide();
            addressAddForm.show();
        }
        function showProvince() {
            var item = $('#addressAddForm, .province, .city, .county');
            var province = $('.province');
            item.hide();
            var list=loadAddress(0);
            province.empty().append(list);
            $('.province').find('li').bind('click',function() {
                provinceCode=$(this).find('input').val();
                provinceName=$(this).find('div').text();
                showCity(provinceCode,provinceName);
                
            });
            province.show();
        }
        function showCity(provinceCode,provinceName) {
            var item = $('#addressAddForm, .province, .city, .county');
            var city = $('.city');
            item.hide();
            var list=loadAddress(provinceCode);
            city.empty().append(list);
            $('.city').find('li').bind('click',function() {
                cityCode=$(this).find('input').val();
                cityName=$(this).find('div').text();
                showCounty(cityCode,cityName,provinceCode,provinceName);
            });
            city.show();
        }
        function showCounty(cityCode,cityName,provinceCode,provinceName) {
            var item = $('#addressAddForm, .province, .city, .county');
            var county = $('.county');
            item.hide();
            var list=loadAddress(cityCode);
            county.empty().append(list);
            $('.county').find('li').bind('click',function() {
                countyCode=$(this).find('input').val();
                countyName=$(this).find('div').text();
                $('#area').find('input').val(provinceName+"  "+cityName+"  "+countyName);
                $('#province').val(provinceCode);
                $('#city').val(cityCode);
                $('#district').val(countyCode);
                showAddressAddForm();
            });
            county.show();
        }
        
        function loadAddress(pCode){
            var areaString="";
            $.ajax({
                    type: "POST",
                    url : _ContextPath + "/address/findAddressByParent",
                    data: {'pCode':pCode,'time':new Date().getTime()},
                    async:false,
                    dataType: "json",
                    success: function (data) {
                        if(data.responseCode==0){
                        provinceList = data.data;
                        jQuery.each(provinceList, function(i,item){
                            var areaCode=item.code;
                            var areaName=item.name;
                            areaString+='<li><div>'+areaName+'</div><input type="hidden" value='+areaCode+'><i class="right"></i></li>';
                        }); 
                           
                    }
                  }
            });
            return areaString;
        }
        
        /**获取浏览器参数*/
        function GetQueryString(name)
		{ 
	       var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); 
	       var r = window.location.search.substr(1).match(reg); 
	       if(r!=null)return  unescape(r[2]); return null; 
		}
		
		function submitForm(){
			var name = $('#name').val();
			var phone = $('#phone').val();
			var finalArea = $('#finalArea').val();
			var addressInfo = $('#addressInfo').val();
			var userPhone = $.trim(phone);
			if(name==""){
				alert("请输入收货人姓名");
				return;
			}
			if(userPhone.length == 0 || !/^1\d{10}$/.test(userPhone)){
				alert("手机号不正确");
				return;
			}
			if(finalArea==""){
				alert("请输入所在地区");
				return;
			}
			
			if(addressInfo==""){
				alert("请输入详细地址");
				return;
			}
			var type=GetQueryString("type");
			if(type !=null && type.toString().length>0){
				$('#addressAddForm').attr("action","${rc.contextPath}/address/modifyAddress?type=chooseAddress");
			} else {
				$('#addressAddForm').attr("action","${rc.contextPath}/address/modifyAddress");
			}
			$('#addressAddForm').submit();
		}
        </script>
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>

