<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>订单详情修改</title>
	 <script type="text/javascript">
			
            $(document).ready(function() {
            	
            	/*表单验证*/
                var validator = $('#findForm').validate({
                	rules: {
                        memberAddress: {
                            required: true,
                        },
                        province: {
                            required: true,
                        },
                        city: {
                            required: true,
                        },
                        district: {
                            required: true,
                        },
                        receiver: {
                            required: true,
                        },
                        receiverMobile: {
                            required: true,
                        },
                        paymentTotalActual: {
                            required: true,
                            number:true,
                        },
                    },
                    messages: {
                        memberAddress: {
                        	required: "请填写详细地址！",
                        },
                        province: {
                        	required: "请选择省份！",
                        },
                        city: {
                        	required: "请选择城市！",
                        },
                        district: {
                        	required: "请选择地区！",
                        },
                        receiver: {
                            required: "请填写收货人",
                        },
                        receiverMobile: {
                            required: "请填写收货人手机",
                        },
                        paymentTotalActual: {
                            required: "请填写订单金额",
                            number:"订单金额必须为数字",
                        },
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
                          <h3 class="panel-title"><i class="fa fa-cog"></i> 订单信息编辑</h3>
                    </div>
				    <div class="panel-body">
				      <form class="form-horizontal" method="post" action="${contextPath }/order/updateOrderDetails" id="findForm" >
				 
				 		<input name="memberId" id="memberId" type="hidden" value="${orderBase.memberId}"/>
						<div class="form-group">
							<label class="col-sm-2 control-label">会员名：</label>
							<div class="col-sm-2">
								<input name="memberName" id="memberName" type="text" class="form-control"
						 			value="${orderBase.memberName}" readonly/>
							</div>
						</div>
				
						<div class="form-group">
							<label class="col-sm-2 control-label">订单编码：</label>
							<div class="col-sm-2">
								<input name="orderCode" id="orderCode" type="text" class="form-control" 
								 	value="${orderBase.orderCode}" readonly/>
							</div>
						</div>
						<input type="hidden" id="province" value="${orderMemberAddress.province}">
						<div class="form-group">
							<label class="col-sm-2 control-label">省：</label>
							<div class="col-sm-2">
								<select name="province" id="editAddress_province"  onchange="findAreaList(this)" class="form-control lg-select" data-placeholder="Choose a province" tabindex="1">
					  	        </select>
							</div>
							<div class="error warning col-sm-2  textL">必填项</div>
						</div>
						<input type="hidden" id="city" value="${orderMemberAddress.city}">
						<div class="form-group">
							<label class="col-sm-2 control-label">市：</label>
							<div class="col-sm-2">
								<select name="city" id="editAddress_city"  onchange="findAreaList(this)" class="form-control lg-select" data-placeholder="Choose a city" tabindex="1">
					       	    </select>
							</div>
							<div class="error warning col-sm-2  textL">必填项</div>
						</div>
						<input type="hidden" id="district" value="${orderMemberAddress.district}">
						<div class="form-group">
							<label class="col-sm-2 control-label">县/区：</label>
							<div class="col-sm-2">
								<select name="district" id="editAddress_district"  onchange="findAreaList(this)" class="form-control lg-select" data-placeholder="Choose a district" tabindex="1">
					            </select>
							</div>
							<div class="error warning col-sm-2  textL">必填项</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">详细地址：</label>
							<div class="col-sm-2">
								<input name="address" id="address" type="text" class="form-control" value="${orderMemberAddress.address}"/>
							</div>
							<div class="error col-sm-2 warning  textL">必填项</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">收货人：</label>
							<div class="col-sm-2">
								<input name="receiver" id="receiver" type="text" class="form-control" value="${orderMemberAddress.receiver}"/>
							</div>
							<div class="error warning col-sm-2  textL">必填项</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">联系电话：</label>
							<div class="col-sm-2">
								<input name="receiverMobile" id="receiverMobile" type="text" class="form-control" value="${orderMemberAddress.receiverMobile}"/>
							</div>
							<div class="error warning col-sm-2  textL">必填项</div>
						</div>
						<c:if test="${isSystemUser }">
							<div class="form-group">
								<label class="col-sm-2 control-label">订单金额：</label>
								<div class="col-sm-2">
									<input name="paymentTotalActual" id="paymentTotalActual" type="text" class="form-control" value="${orderFinance.paymentTotalActual}"/>
								</div>
								<div class="error warning col-sm-2  textL">必填项</div>
							</div>
						</c:if>
						<c:if test="${!isSystemUser }">
							<div class="form-group" style="display:none">
								<label class="col-sm-2 control-label">订单金额：</label>
								<div class="col-sm-2">
									<input name="paymentTotalActual" id="paymentTotalActual" type="text" class="form-control" value="${orderFinance.paymentTotalActual}"/>
								</div>
								<div class="error warning col-sm-2  textL">必填项</div>
							</div>
						</c:if>
						<div class="form-group">
							<label class="col-sm-2 control-label">会员留言：</label>
							<div class="col-sm-2">
								<input name="memo" id="memo" type="text" class="form-control" value="${orderBase.memo}"/>
							</div>
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