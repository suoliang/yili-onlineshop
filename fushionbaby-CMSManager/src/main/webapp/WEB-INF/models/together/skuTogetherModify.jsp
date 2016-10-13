<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>团购管理修改</title>
	 <script type="text/javascript">
			
            $(document).ready(function() {
            	
            	/*表单验证*/
                var validator = $('#togetherForm').validate({
                	rules: {
                        maxNumber: {
                            required: true,
                            digits:true,
                        },
                        minNumber: {
                            required: true,
                            digits:true,
                        },
                        showNumber: {
                            required: true,
                            digits:true,
                        }
                    },
                    messages: {
                        maxNumber: {
                        	required: "请填写团购最大人数！",
                        	digits:"团购最大人数必须是正整数！",
                        },
                        minNumber: {
                        	required: "请填写团购最少人数！",
                        	digits:"团购最少人数必须是正整数！",
                        },
                        showNumber: {
                        	required: "请填写团购显示下单人数！",
                        	digits:"团购显示下单人数必须是正整数！",
                        }
                    },
                    errorPlacement: function(error, element) {
                        if ( element.is(".form_datetime") ){
                        	element.parent().parent().parent().find('.warning').html('');
                        	error.appendTo(element.parent().parent().parent().find('.warning'));
                        } else {
                        	element.parent().parent().find('.warning').html('');
                        	error.appendTo(element.parent().parent().find('.warning'));
                        }
                    },
                    submitHandler: function(form) {
                        // 验证成功后操作
                       console.log($(form).serialize());
                       form.submit();
                       
                    }
                });
            });
            
            function findEditArea(area_type,area_code,default_code){
    	  		
    	  		$.post('${pageContext.request.contextPath}/order/find_area_list', {
    	  			code :area_code,
    	  			time : new Date().getTime()
    	  		}, function(data) {
    	  			var init_id="#editAddress_province";
    	  			if(area_type==1){
    	  				//省
    	  				init_id="#editAddress_province";
    	  				$("#editAddress_province").empty();//清空
    	  				$("#editAddress_province").append("<option value=''>请选择</option>");
    	  			}else if(area_type==2){
    	  				//市
    	  				init_id="#editAddress_city";
    	  				$("#editAddress_city").empty();//清空
    	  				$("#editAddress_city").append("<option value=''>请选择</option>");
    	  			}else if(area_type==3){
    	  				//区/县
    	  				init_id="#editAddress_district";
    	  				$("#editAddress_district").empty();//清空
    	  				$("#editAddress_district").append("<option value=''>请选择</option>");
    	  			}
    	  			
    	  			var len=data.length;
    	  			for(i=0;i<len;i++){
    	  				var areaObj=data[i];
    	  				if(default_code==areaObj.code){
    	  					$(init_id).append("<option selected='selected' value='"+areaObj.code+"'>"+areaObj.name+"</option>");
    	  				}else{
    	  					$(init_id).append("<option value='"+areaObj.code+"'>"+areaObj.name+"</option>");
    	  				}
    	  			}
    	  		});// ajax管理员取消订单请求end
    	  	}
    	  	
    		//订单地址,市
    		function findAreaList(obj){
    			var obj_id=obj.id;
    			var code=obj.value;
    			var find_id="editAddress_province";
    			if(obj_id=="editAddress_province"){
    				//省发生改动,清空原有的市,区/县
    				$("#editAddress_city").empty();//清空,原有的市
    				$("#editAddress_district").empty();//清空
    				$("#editAddress_city").append("<option value=''>请选择</option>");
    				$("#editAddress_district").append("<option value=''>请选择</option>");
    				
    				find_id="editAddress_city";//加载省旁边的市,下拉框
    			}else if(obj_id=="editAddress_city"){
    				//市发生改动,清空原有的,区/县
    				$("#editAddress_district").empty();//清空,原有的区县
    				$("#editAddress_district").append("<option value=''>请选择</option>");
    				find_id="editAddress_district";
    			}
    			
    			$.post('${pageContext.request.contextPath}/order/find_area_list', {
    				code :code,
    				time : new Date().getTime()
    			}, function(data) {
    				if (data == null || data == "" || data == undefined) {
    					return;
    				}
    				var len=data.length;
    				for(i=0;i<len;i++){
    					var areaObj=data[i];
    					$("#"+find_id).append("<option value='"+areaObj.code+"'>"+areaObj.name+"</option>");
    				}
    			});// ajax管理员取消订单请求end
    		}
    	  	window.onload=function(){
    	  		var province=$("#province").val();
    		  	var city=$("#city").val();
    		  	var district=$("#district").val();
    		  	findEditArea(1,0,province);
    			findEditArea(2,province,city);
    			findEditArea(3,city,district);
    		};
    		
        </script>
	
</head>
 <body id="index">
        <div class="container-fluid">
            <div class="row">
			
			  <div class="col-md-12 content">
			  
			     <div class="panel panel-info">
                    <div class="panel-heading">
                          <h3 class="panel-title"><i class="fa fa-cog"></i> 团购信息编辑</h3>
                    </div>
				    <div class="panel-body">
				      <form class="form-horizontal" method="post" action="${contextPath }/skuTogether/updateTogether" id="togetherForm" >
				 
				 		<input name="id" id="id" type="hidden" value="${skuTogether.id}"/>
						<div class="form-group">
							<label class="col-sm-2 control-label">团购商品编码：</label>
							<div class="col-sm-2">
								<input name="skuCode" id="skuCode" type="text" class="form-control"
						 			value="${skuTogether.skuCode}" disabled/>
							</div>
						</div>
				
						<div class="form-group">
							<label class="col-sm-2 control-label">团购商品名称：</label>
							<div class="col-sm-2">
								<input name="skuName" id="skuName" type="text" class="form-control" 
								 	value="${skuTogether.skuName}" disabled/>
							</div>
						</div>
						<input type="hidden" id="province" value="${skuTogether.province}">
						<div class="form-group">
							<label class="col-sm-2 control-label">省：</label>
							<div class="col-sm-2">
								<select name="province" id="editAddress_province" disabled onchange="findAreaList(this)" class="form-control lg-select" data-placeholder="Choose a province" tabindex="1">
					  	        </select>
							</div>
						</div>
						<input type="hidden" id="city" value="${skuTogether.city}">
						<div class="form-group">
							<label class="col-sm-2 control-label">市：</label>
							<div class="col-sm-2">
								<select name="city" id="editAddress_city" disabled onchange="findAreaList(this)" class="form-control lg-select" data-placeholder="Choose a city" tabindex="1">
					       	    </select>
							</div>
						</div>
						<input type="hidden" id="district" value="${skuTogether.district}">
						<div class="form-group">
							<label class="col-sm-2 control-label">县/区：</label>
							<div class="col-sm-2">
								<select name="district" id="editAddress_district" disabled onchange="findAreaList(this)" class="form-control lg-select" data-placeholder="Choose a district" tabindex="1">
					            </select>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">团购开始时间：</label>
							<div class="col-sm-2">
								<input name="beginTime" id="beginTime" type="text" class="form-control"
						 			value="<fmt:formatDate value='${skuTogether.beginTime}' pattern='yyyy-MM-dd' />" disabled/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">团购结束时间：</label>
							<div class="col-sm-2">
								<input name="endTime" id="endTime" type="text" class="form-control"
						 			value="<fmt:formatDate value="${skuTogether.endTime}" pattern="yyyy-MM-dd" />" disabled/>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">团购状态：</label>
							<div class="col-sm-2">
								<select name="status" id="editStatus" class="form-control lg-select" data-placeholder="Choose a status" tabindex="1">
					            	<option value="y" ${skuTogetherStatus.status=='y'?'selected':'' }>开启</option>
					            	<option value="n" ${skuTogetherStatus.status=='n'?'selected':'' }>关闭</option>
					            </select>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">团购最少人数：</label>
							<div class="col-sm-2">
								<input name="minNumber" id="minNumber" type="text" class="form-control" value="${skuTogetherStatus.minNumber}"/>
							</div>
							<div class="error col-sm-2 warning  textL">必填项</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">团购最多人数：</label>
							<div class="col-sm-2">
								<input name="maxNumber" id="maxNumber" type="text" class="form-control"  value="${skuTogetherStatus.maxNumber}"/>
							</div>
							<div class="error col-sm-2 warning  textL">必填项</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">显示下单人数：</label>
							<div class="col-sm-2">
								<input name="showNumber" id="showNumber" type="text" class="form-control"  value="${skuTogetherStatus.showNumber}"/>
							</div>
							<div class="error col-sm-2 warning  textL">必填项</div>
						</div>
						
						<div class="form-group">
					    	<div class="col-sm-offset-2 col-sm-10">
					      	
					      		<button class="btn btn-success " type="submit">确定</button>
								<button class="btn btn-primary" type="button" onclick="javascript:history.go(-1);">返回</button>
					   	 	</div>
						</div>
				  
				</form>
			  </div>
			  </div>
       		</div>
       	  </div>
       	</div>
   </body>
</html>