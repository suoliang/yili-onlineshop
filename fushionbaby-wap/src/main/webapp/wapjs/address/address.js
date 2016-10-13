
 
	$(document).ready(function() {
		init();
	});
	
//	 var code = function (eventObject) { 
//		if (eventObject.preventDefault) { 
//		    eventObject.preventDefault(); 
//		} else if (window.event) /* for ie */ { 
//		    window.event.returnValue = false; 
//		} 
//		return true; 
//	};
//	var element = $("#form"); 
//	if(element!=null){
//		if (element.addEventListener) { 
//		    element.addEventListener("submit", code, false); 
//		} else if (element.attachEvent) { 
//		    element.attachEvent("onsubmit", code); 
//		} 
//	}

	var ajaxProvinceURL= _ContextPath + '/address/areaList.do?area_id=';
	var ajaxCityURL= _ContextPath + '/address/areaList.do?area_id=';
	var ajaxDistrictURL= _ContextPath + '/address/areaList.do?area_id=';
	function init() {
		//加载省数据
		$('.myProvince').each(function(index) {
			var defaultCodeP = $(this).attr('pvalue');
			loadSelData(ajaxProvinceURL,$(this),new Object([$(this).next(),$(this).next().next()]),defaultCodeP);
		});
		
		//加载市数据
		$('.myCity').each(function(index) {
			var defaultCodeC = $(this).attr('cvalue');
			var province_id = $(this).prev().attr('pvalue');
			loadSelData(ajaxCityURL + province_id,$(this),new Object([$(this).next()]),defaultCodeC);
		});
		
		//加载区数据
		$('.myDistrict').each(function(index) {
			var defaultCodeD = $(this).attr('dvalue');
			var city_id = $(this).prev().attr('cvalue');
			loadSelData(ajaxDistrictURL + city_id,$(this),new Object(),defaultCodeD);
		});
		
		//绑定所有省下拉列表对应的onchange事件
		$('.myProvince').each(function(index) {
			$(this).bind("change",function() {
				var provinceId = $(this).children('option:selected').val();
				loadSelData(ajaxCityURL+provinceId,$(this).next(),new Object([$(this).next().next()]),'');
			});
		});
		//绑定所有市下拉列表对应的onchange事件
		$('.myCity').each(function(index) {
			$(this).bind("change",function() { 
				var cityId = $(this).children('option:selected').val();
				loadSelData(ajaxDistrictURL+cityId,$(this).next(),new Object(),'');
			});
		});
		
	};
	

	/**
	 * js验证手机号
	 */
	function validatemobile(mobileId,mobile) {
        if(mobile.length==0) {
           showErrorMsg(mobileId,'请输入手机号码！');
           return false;
        }    
        if(mobile.length!=11) {
        	showErrorMsg(mobileId,'请输入有效的手机号码！');
            return false;
        }
        var myreg = /^1\d{10}$/;
        if(!myreg.test(mobile)) {
        	showErrorMsg(mobileId,'请输入有效的手机号码！');
            return false;
        }
        $('#'+mobileId).css({"display":"none"});
        return true;
    };
	
	//显示错误信息
	function showErrorMsg(id,msg) {
		var obj = $("#"+id);
		if(obj.css("display")=="none") {
		   $('#'+id+'>span').html(msg);
		   obj.css({"display":"block"});
		   obj.focus();
		}
	};
	
	//js验证只能输入中文、英文、数字、@符号和.符号
	function valChars(id,mychars) {
		var myreg = /[a-z|A-Z|0-9|\u4E00-\u9FA5|\@\.\,]/;
		 if(myreg.test(mychars)==false || mychars == '') {
			 showErrorMsg(id,'只能输入中文英文数字');
	         return false; 
		 }
		 
		 $('#'+id).css({"display":"none"});
		 return true;
	};
	
	//js验证添加操作
	 function validateAdd() {
		var flag = true;
		var province_id = $('#addProvinceId').children('option:selected').val();
		var city_id = $('#addCityId').children('option:selected').val();
		var district_id = $('#addDistrictId').children('option:selected').val();
		if(province_id=='-1'||city_id=='-1'||district_id=='-1') {
			this.showErrorMsg('error_PCD_id','请选择省市县');
			return false;
		} else {
			$('#error_PCD_id').css({"display":"none"});
		}
		flag = this.valChars('error_deliverAddress_id',$('#deliverAddress').val());
		if(flag==false) {
			return flag;
		}
		flag = this.valChars('error_deliverName_id',$('#deliverName').val());
		if(flag==false){
			return flag;
		}
		flag = this.validatemobile('error_phone_id',$('#phone').val());
		return flag;
	};
	
	 function address_add() {			
		var province_id = $('#addProvinceId').children('option:selected').val();
		var city_id = $('#addCityId').children('option:selected').val();
		var district_id = $('#addDistrictId').children('option:selected').val();
		var address = $('#deliverAddress').val();
		var name = $('#deliverName').val();
		var phone = $('#phone').val();
		if(this.validateAdd()==false) {
			$('.add-address').css({"display":"block"});
			//$(".address").find("form").attr("id","form");
			
			return false;
		}
		//$(".address").find("form").attr("id","");
		$.ajax({
            url: _ContextPath + '/address/add.do',
            data:{'province_id':province_id,'city_id':city_id,'district_id':district_id,
            	 'address':address,'name':name,'phone':phone},
            type: "POST",
            dataType: "json",
            async:false,
            success: function (data) {
        		if (data.responseCode=="0") {
        			var url = _ContextPath+'/address/list_address.do?time='+new Date().getTime();
        			$.ajax({
        				type : "POST",
        				url  : url,
        				data : "",
        				dataType : "html",
        				success : function(data) {
        					location.reload();
        				}	
        			});
        		}
            }
        });	
	};
	
    var addressId = '';
	function  address_edit(id) {		
		
		addressId = id;
	};
	
	//js验证edit操作
	function validateEdit() {
		var flag = true;
		var province_id = $('#updateProvinceId'+addressId).children('option:selected').val();
		var city_id = $('#updateCityId'+addressId).children('option:selected').val();
		var district_id = $('#updateDistrictId'+addressId).children('option:selected').val();
		if(province_id=='-1'||city_id=='-1'||district_id=='-1') {
			this.showErrorMsg('error_PCD_'+addressId,'请选择省市县');
			return false;
		} else {
			$('#error_PCD_'+addressId).css({"display":"none"});
		}
		flag = this.valChars('error_deliverAddress_'+addressId,$('#deliverAddress'+addressId).val());
		if(flag==false){ 
			return flag;
		}
		flag = this.valChars('error_deliverName_'+addressId,$('#deliverName'+addressId).val());
		if(flag==false){ 
			return flag;
		}
		flag = this.validatemobile('error_phone_'+addressId,$('#phone'+addressId).val());
		return flag;
	};
	
	function address_editSubmit() {
		var province_id = $('#updateProvinceId'+addressId).children('option:selected').val();
		var city_id = $('#updateCityId'+addressId).children('option:selected').val();
		var district_id = $('#updateDistrictId'+addressId).children('option:selected').val();
		var address = $('#deliverAddress'+addressId).val();
		var name = $('#deliverName'+addressId).val();
		var phone = $('#phone'+addressId).val();
		if(this.validateEdit()==false) {
			$('#edit-addr-'+addressId).css({"display":"block"});
			return false;
		}

		$.ajax({
            url: _ContextPath + '/address/change.do',
            data:{'province_id':province_id,'city_id':city_id,'district_id':district_id,
            	 'address':address,'name':name,'phone':phone,'address_id':addressId},
            type: "POST",
            dataType: "json",
            async:false,
            success: function (data) {
        		if (data.responseCode =="0") {
        	
        			location.reload();
        		}
            }
        });
	};
	
	 function address_del(id) {
		if(window.confirm("你确定要删除该地址吗？")){
			$.ajax({
	            type: "GET",
	            async:false,
	            dataType: "json",
	            url: _ContextPath + '/address/delete.do?address_id='+id,
	            success: function (data) {
	        		if (data.responseCode=="0") {
	        			$("#choosed_"+id).hide();
	        		}
	            }
	        });	
		
		}
	};
	function loadSelData(url,selObj,arrayObj,defaultCode) {
        $.ajax({
            type: "GET",
            dataType: "json",
            url: url,
            async:false,
            success: function (data) {
        		if (data.responseCode=="0") {
        			selObj.empty();
        			selObj.append("<option value='-1'>请选择</option>");        			
        			for(var j=0; j < arrayObj.length;j++) {
        				arrayObj[j].empty();
        				arrayObj[j].append("<option value='-1'>请选择</option>");
        			}        			
        			var array= data.data;
        			var len= array.length;
        			for(var i=0; i < len;i++) {
        				var areaObj = array[i];
        				selObj.append("<option value='"+areaObj.code+"'>"+areaObj.name+"</option>");
        				
        			}
        			if(defaultCode!=null && defaultCode!='') {
        				selObj.val(defaultCode);
        			}
        		} 
            }
        });	
	};

 