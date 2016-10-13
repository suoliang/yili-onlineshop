var provinceList = "";
$(function(){	
	/** 初始化加载地址信息数据 （省市区）*/
	var url =contextPath + "/address/uploadAddress";
	 $.post(url,{
			time : new Date().getTime()},
			function(data){
				if(data.responseCode==0){
					//alert("验证成功");
					provinceList = data.data;
				}else{
					alert(data.msg);
				}
		});
			 
	 
});	
/** 编辑默认地址*/
function editDefaultAddress(addressId){
	var url = contextPath + "/address/editDefaultAddress";
	 $.post(url,{
		 	addressId:addressId,
			time : new Date().getTime()},
			function(data){
				if(data.responseCode==0){
					window.location.reload();
				}else{
					alert(data.msg);
				}
		});
}
/** 修改和保存地址*/
function modifySave(t,id){
	
	var formData=$("#"+id).serialize();
	formData += "&t="+t+"&time="+new Date().getTime();
	var url = contextPath + "/address/modifyAddress";
	
	$.ajax({
        type: "POST",
        async:false,
        dataType: "json",
        url: url,
        data:formData,
        success: function (data) {
    		if (data.responseCode=="0") {
    			window.location.reload();
    		}
        }
    });	
	
}
/** 删除地址*/
function deleteAddress(addressId){
	var url = contextPath + "/address/deleteAddress";
	$.post(url,{
	 	addressId:addressId,
		time : new Date().getTime()},
		function(data){
			if(data.responseCode==0){
				window.location.reload();
			}else{
				alert(data.msg);
			}
	});
}
/** 初始化设置地址*/
function initAddress(addressId){
	
	$.ajax({
        type: "POST",
        async:false,
        dataType: "json",
        url: contextPath + '/address/findById?addressId='+addressId,
        success: function (data) {
    		if (data.responseCode=="0") {
    			//$("#choosed_"+id).hide();
    			var result = data.data;
    			$("#addressId").val(addressId);
    			addressInit("cmbProvince","cmbCity","cmbArea",result.province,result.city,result.district);
    			otherAddressInfoInit(result.address,result.contactor,result.mobile);
    		}
        }
    });	
}


/** 初始化设置地址其他信息*/
var otherAddressInfoInit = function(address,contactor,phone){
	$("#cmbAddress").val(address);
	$("#cmbContactor").val(contactor);
	$("#cmbPhone").val(phone);
}

var addressInit = function(_cmbProvince, _cmbCity, _cmbArea, defaultProvince, defaultCity, defaultArea){
	
	var cmbProvince = document.getElementById(_cmbProvince);
	var cmbCity = document.getElementById(_cmbCity);
	var cmbArea = document.getElementById(_cmbArea);
	
	for(var i=0; i<provinceList.length; i++)
	{
		cmbAddOption(cmbProvince, provinceList[i]);
	}
	cmbSelect(cmbProvince, defaultProvince);
	changeProvince();
	cmbProvince.onchange = changeProvince;
	
	
	function cmbAddOption(cmb,item){
		
		var option = document.createElement("OPTION");
		cmb.options.add(option);
		option.innerHTML = item.name;
		option.value = item.code;
		option.obj = item;
	}
	
	function cmbSelect(cmb, str)
	{
		for(var i=0; i<cmb.options.length; i++)
		{
			if(cmb.options[i].value == str)
			{
				cmb.selectedIndex = i;
				return;
			}
		}
	}
	
	function changeProvince(){
		
		cmbCity.options.length = 0;
		cmbCity.onchange = null;
		if(cmbProvince.selectedIndex == -1)return;
		var item = cmbProvince.options[cmbProvince.selectedIndex].obj;
		for(var i=0; i<item.cityList.length; i++)
		{
			cmbAddOption(cmbCity, item.cityList[i]);
		}
		cmbSelect(cmbCity, defaultCity);
		changeCity();
		cmbCity.onchange = changeCity;
	}
	
	function changeCity()
	{
		cmbArea.options.length = 0;
		if(cmbCity.selectedIndex == -1)return;
		var item = cmbCity.options[cmbCity.selectedIndex].obj;
		for(var i=0; i<item.areaList.length; i++)
		{
			cmbAddOption(cmbArea, item.areaList[i], null);
		}
		cmbSelect(cmbArea, defaultArea);
	}
	
	
}
 
 

