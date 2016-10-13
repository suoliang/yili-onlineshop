<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>门店编辑 - CMS</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="renderer" content="webkit">
        <meta name="description" content="阿拉丁后台管理系统">
        <meta name="keywords" content="阿拉丁后台管理系统">
        <link rel="shortcut icon" href="${contextStatic }/images/favicon.ico" />
    </head>
    
    <script type="text/javascript">
    $(function(){	
    	/** 初始化加载地址信息数据 （省市区）*/
    	var url =contextPath + "/store/cell/uploadAddress";
    	 $.post(url,{
    			time : new Date().getTime()},
    			function(data){
    				if(data.responseCode==0){
    					//alert("验证成功");
    					provinceList = data.data;
    					addressInit("editAddress_province","editAddress_city","editAddress_district","${store.provinceCode}","${store.cityCode}","${store.countryCode}",provinceList);
    				}else{
    					alert(data.msg);
    				}
    		});
    			 
    	 
    });	
    
    
    
    
    var addressInit = function(_cmbProvince, _cmbCity, _cmbArea, defaultProvince, defaultCity, defaultArea,provinceList){
    	
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
    		
    		$("input[name='provinceName']").val(item.name);
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
    		changeCountry();
    		cmbArea.onchange = changeCountry;
    		
    		$("input[name='cityName']").val(item.name);
    	}
    	
    	function changeCountry(){
    		var cmbCell = document.getElementById("editAddress_cell");
    		cmbCell.options.length = 0;
    		
    		if(cmbArea.selectedIndex == -1)return;
    		
    		var item = cmbArea.options[cmbArea.selectedIndex].obj;
    		$("input[name='countryName']").val(item.name);
    		
    		
    		 $.post(contextPath +"/store/findCellList",{code:item.code,time:new Date().getTime()},
	    			  function(data){
    			 	
    			 if (data == null || data == "" || data == undefined) {
 					return;
 				}
 				var len=data.length;
 				for(i=0;i<len;i++){
 					var areaObj=data[i];
 					$("#editAddress_cell").append("<option value='"+areaObj.code+"'>"+areaObj.name+"</option>");
 				}	  
 				
 				cmbSelect(cmbCell, "${store.cellCode}");
    			 
	    	});
    		
    	}
    	
    }
    
    function checkIsUnique(){
    	 $.post(contextPath +"/store/storeUnique",
    			 {number:$("#number").val(),numbertmp:$("#numbertmp").val(),id:$("#id").val(),time:new Date().getTime()},
   			  function(data){
    		 
    			 if(data==false){
    				 $("#number").val("");
	    			 jBox.tip("该门店号已存在,请重新输入！");
	    			 return;
    			 }
    		 
    	 });
    	
    }
    
    </script>
    
    <body id="" class="Cog">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-cog"></i> 修改门店</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" id="addStoreForm" action="${contextPath }/store/editStore" method="post">
                               
                                 <input name="code" type="hidden" id="code" class="form-control" value="${store.code }">
                                 <input name="id" type="hidden" id="id" class="form-control" value="${store.id }">
                             
                             
                             <c:if test="${store.id !=null }">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">门店编号：</label>
                                    <div class="col-sm-5">
                                    	<input id="numbertmp" type="hidden" class="form-control" value="${store.number }" >
                                        <input name="number" id="number" readonly="readonly" type="text" class="form-control" value="${store.number }" onblur="checkIsUnique();">
                                    </div>
                                </div>
                            </c:if>
                            
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">门店名称：</label>
                                    <div class="col-sm-5">
                                        <input name="name" type="text" class="form-control" value="${store.name }">
                                    </div>
                                </div>
                         
                                <div class="form-group">
									<label class="col-sm-2 control-label">省：</label>
									<div class="col-sm-2">
										<select name="provinceCode" id="editAddress_province"  class="form-control lg-select" data-placeholder="Choose a province" tabindex="1">
							  	        </select>
							  	        <input name="provinceName" type="hidden" value="${store.provinceName }" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">市：</label>
									<div class="col-sm-2">
										<select name="cityCode" id="editAddress_city"class="form-control lg-select" data-placeholder="Choose a city" tabindex="1">
							       	    </select>
							       	    <input name="cityName" type="hidden" value="${store.cityName }" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">县/区：</label>
									<div class="col-sm-2">
										<select name="countryCode" id="editAddress_district" class="form-control lg-select" data-placeholder="Choose a district" tabindex="1">
							            </select>
							            <input name="countryName"  type="hidden" value="${store.countryName }" />
							            
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">小区：</label>
									<div class="col-sm-2">
										<select name="cellCode" id="editAddress_cell"  class="form-control lg-select" data-placeholder="Choose a district" tabindex="1">
							            
								            <c:forEach items="${cellList }" var="cell">
									           
									            <option value="${cell.code }"   <c:if test="${cell.code==store.cellCode }">selected="selected"	</c:if> >
									            		${cell.name }</option>
									           
								            </c:forEach>
							            </select>
									</div>
								</div>
                                   <div class="form-group">
                                    <label class="col-sm-2 control-label">运营状态：</label>
                                    <div class="col-sm-5">
                                      <select name="status" class="form-control lg-select">
                                      
                                        <c:forEach items="${dic:getDictByType('STORE_STATUS') }" var="dict" >
                                        	<option value="${dict.value }" <c:if test="${dict.value==store.status }">selected="selected"</c:if>  >${dict.label }</option>
                                        </c:forEach>
                                       
                                      </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">纬度：</label>
                                    <div class="col-sm-5">
                                        <input name="latitude" type="text" class="form-control" value="${store.latitude }">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">经度：</label>
                                    <div class="col-sm-5">
                                        <input name="longitude" type="text" class="form-control" value="${store.longitude }">
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="submit" class="btn btn-info">确认</button>
                                        <button type="button" class="btn btn-default" onclick="history.go(-1);">取消</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <!-- /.content -->
            </div>
        </div>
        <!-- /.container-fluid -->
    
        <script type="text/javascript">
            $(document).ready(function() {
                /*表单验证*/
                var validator = $('#addStoreForm').validate({
                    rules: {
                      
                        name: {
                            required: true
                        },
                        provinceCode: {
                            required: true
                        },
                        cityCode: {
                            required: true
                        },
                        countryCode: {
                            required: true
                        }
                    },
                    messages: {
                      
                        name: {
                            required: "请填写门店名称！"
                        },
                        provinceCode: {
                            required: "请选择省！"
                        },
                        cityCode: {
                            required: "请选择市！"
                        },
                        countryCode: {
                            required: "请选择地区！"
                        }
                    },
                    errorPlacement: function(error, element) {
                         error.appendTo(element.parent());
                    }, 
                    submitHandler: function(form) {
                        // 验证成功后操作
                        console.log($(form).serialize());
                        $(form).ajaxSubmit();
                    }
                });

            });
        </script>
    </body>
</html>
