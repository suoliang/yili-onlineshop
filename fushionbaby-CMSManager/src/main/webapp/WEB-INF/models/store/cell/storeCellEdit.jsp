<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>小区编辑 - CMS</title>
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
    					addressInit("editAddress_province","editAddress_city","editAddress_district","${storeCell.provinceCode}","${storeCell.cityCode}","${storeCell.countryCode}",provinceList);
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
    </script>
    
    <body id="" class="Cog">
        <div class="container-fluid">
            <div class="row">
         <%--    <div id="menu">
             <script src="${contextStatic }/bootstrap/js/leftMenu.js"></script><!-- 公共左侧菜单 -->
            </div> --%>
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-cog"></i> 编辑小区</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" id="addStoreForm" action="${contextPath }/store/cell/editStoreCell" method="post">
                               <input type="hidden" name="id"  value="${storeCell.id }" />
                             
                                <div class="form-group">
									<label class="col-sm-2 control-label">省：</label>
									<div class="col-sm-2">
										<select name="provinceCode" id="editAddress_province" class="form-control lg-select" data-placeholder="Choose a province" tabindex="1">
							  	        </select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">市：</label>
									<div class="col-sm-2">
										<select name="cityCode" id="editAddress_city"  class="form-control lg-select" data-placeholder="Choose a city" tabindex="1">
							       	    </select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">县/区：</label>
									<div class="col-sm-2">
										<select name="countryCode" id="editAddress_district"  class="form-control lg-select" data-placeholder="Choose a district" tabindex="1">
							            </select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">小区：</label>
									<div class="col-sm-2">
										 <input type="text" class="form-control" id="cellName" name="cellName" value="${storeCell.cellName}" placeholder="请输入小区名称">
									</div>
								</div>
								<c:if test="${storeCell.id ==null}">
									<div class="form-group">
										<label class="col-sm-2 control-label">小区2：</label>
										<div class="col-sm-2">
											 <input type="text" class="form-control" name="cellName2" placeholder="请输入小区名称">
										</div>
										<div class="error col-sm-4  textL">选填</div>
									</div>	
									<div class="form-group">
										<label class="col-sm-2 control-label">小区3：</label>
										<div class="col-sm-2">
											 <input type="text" class="form-control"  name="cellName2"  placeholder="请输入小区名称">
										</div>
										<div class="error col-sm-4  textL">选填</div>
									</div>	
									<div class="form-group">
										<label class="col-sm-2 control-label">小区4：</label>
										<div class="col-sm-2">
											 <input type="text" class="form-control"  name="cellName2"  placeholder="请输入小区名称">
										</div>
										<div class="error col-sm-4  textL">选填</div>
									</div>	
									<div class="form-group">
										<label class="col-sm-2 control-label">小区5：</label>
										<div class="col-sm-2">
											 <input type="text" class="form-control"  name="cellName2"  placeholder="请输入小区名称">
										</div>
										<div class="error col-sm-4  textL">选填</div>
									</div>	
								</c:if>
							
								
                                   <div class="form-group">
                                    <label class="col-sm-2 control-label">是否禁用：</label>
                                    <div class="col-sm-2">
                                      <select name="isDisable" class="form-control lg-select">
                                        <option value="n">启用</option>
                                        <option value="y">禁用</option>
                                      </select>
                                    </div>
                                </div>
                                
                                <div class="form-group">
									<label class="col-sm-2 control-label">显示顺序：</label>
									<div class="col-sm-2">
										 <input type="number" class="form-control" id="showOrder" name="showOrder" value="${storeCell.showOrder}" placeholder="显示顺序">
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
                	cellName: {
                        required: true
                    }
                   
                },
                messages: {
                	cellName: {
                        required: "请填写小区名称！"
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
