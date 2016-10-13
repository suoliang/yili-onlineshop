<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 活动库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>商品活动新增</title>
	<script type="text/javascript">
	$(document).ready(function() {
		
		/*表单验证*/
	    var validator = $('#handleForm').validate({
	        rules: {
	        	promotionsName: {
	                required: true,
	                rangelength: [2, 50]
	            },
	            promotionsCode: {
	                required: true,
	                rangelength: [2, 20],
	                remote :{
	                	url:"${contextPath}/skuPromotions/existSkuPromotionsCode",
	                	type:"get",
	                	dataType:"json",
	                	data: {                     //要传递的数据
	                		 code: function() {
	                             return $("#promotionsCode").val();
	                         }
	                    }
	                }
	            },
	            startDate: {
	                required: true,
	                
	            },
	            endDate: {
	                required: true,
	               
	            },
	            timeRange: {
	                required: true,
	                
	            },
	            salesPrice: {
	                required: true,
	                number:true
	            }
	            
	        },
	        messages: {
	        	promotionsName: {
		                required: "请填写活动名称！",
		                rangelength: "活动名称长度在2-50之间"
		            },
		            promotionsCode: {
		                required: "请输入活动编码！",
		                rangelength: "活动编码长度在2-20之间",
		                remote:"活动编码已存在"
		            },
		            startDate: {
		                required: "请输入活动开始时间！",
		                
		            },
		            endDate: {
		                required:"请输入活动结束时间！",
		               
		            },
		            timeRange: {
		                required: "请输入活动时间段！",
		                
		            },
		            salesPrice: {
		                required: "请输入活动统一价格！",
		                number:"活动统一价格必须为数字！"
		            }
	           
	        },
	        
	        submitHandler: function(form) {
	            // 验证成功后操作
	            console.log($(form).serialize());
	            $(".form_datetime").each(function(){
	            	if($(this).val()==""){
		            	$(this).parents('.form-group').find(".error").html("时间不能为空");
		            	return;
		            }else{
		            	$(this).parents('.form-group').find(".error").html("");
		            }
	            })
	            
	            //表单转json
	            var o = {};  
	            var a = $(form).serializeArray();  
	            $.each(a, function() {  
	                if (o[this.name]) {  
	                    if (!o[this.name].push) {  
	                        o[this.name] = [o[this.name]];  
	                    }  
	                    o[this.name].push(this.value || '');  
	                } else {  
	                    o[this.name] = this.value || '';  
	                }  
	            });  
	            //json转字符串
	            var jsonStr=JSON.stringify(o);
	            
	            $.post("${contextPath }/skuPromotions/addSkuPromotions",{jsonStr:jsonStr },function(result){
	                if(result == "SUCCESS"){
	                	parent.location.reload();
	                	window.parent.window.jBox.close();
	                }else{
	                	jBox.tip("添加失败", 'info');
	                }
	                
	              });
	        }
	    });
	});
	</script>
</head>
 <body class="backWhite">
		<div class="pT30 mB0">
              <form class="form-horizontal" method="post"  id="handleForm">
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">活动名称：</label>
					<div class="col-sm-4">
						<input name="promotionsName" id="promotionsName" type="text" class="form-control" />
					</div>
					<div class="error col-sm-4  textL">必填项</div>
				</div>
		
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">活动编码：</label>
					<div class="col-sm-4">
						<input name="promotionsCode" id="promotionsCode" type="text" class="form-control" />
					</div>
					<div class="error col-sm-4  textL">必填项</div>
				</div>
				
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">活动开始时间：</label>
					<div class="col-sm-4">
						<div class="input-group">
							<input class="form-control form_datetime"  readonly name="startDate" 
										 type="text" value="${startDate}">
							<div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
						</div>
					</div>
					<div class="error col-sm-4  textL">必填项</div>
				</div>
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">活动结束时间：</label>
					<div class="col-sm-4">
						<div class="input-group">
							<input class="form-control form_datetime"  readonly name="endDate" 
										 type="text" value="${endDate}">
							<div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
						</div>
					</div>
					<div class="error col-sm-4  textL">必填项</div>
				</div>
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">活动时间范围：</label>
					<div class="col-sm-4">
						<input name="timeRange" id="timeRange" type="text" class="form-control" />
					</div>
					<div class="error col-sm-4  textL">必填项</div>
					
				</div>
				<div class="form-group mL0 mR0">
				<label class="col-sm-4 control-label"></label>
				<div class="col-sm-6">
					（格式：8：00-12：00，13：00-15：00）
				</div>
				</div>
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">是否可重复购买：</label>
					<div class="col-sm-4">
						<select name="isRepeatBuy" id="isRepeatBuy" class="form-control"
						data-placeholder="Choose a Category" tabindex="1">
							<option value="y" ${isRepeatBuy=='y'?'selected':'' }>是</option>
							<option value="n" ${isRepeatBuy=='n'?'selected':'' }>否</option>
						</select>
					</div>
				</div>
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">统一售价：</label>
					<div class="col-sm-4">
						<input name="salesPrice" id="salesPrice" type="text" class="form-control" />
					</div>
					<div class="error col-sm-4  textL">必填项</div>
				</div>
				
				<div class="form-group mL0 mR0">
			    	<div class="col-sm-offset-4 col-sm-8">
			      	    <button class="btn btn-primary" type="submit">新增</button>
			   	 	</div>
				</div>
		</form>
	 </div>
   </body>
</html>