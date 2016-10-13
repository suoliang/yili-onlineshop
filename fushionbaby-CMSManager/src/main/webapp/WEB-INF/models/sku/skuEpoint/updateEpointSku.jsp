<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>积分商品编辑</title>
	 <script type="text/javascript">
			
            $(document).ready(function() {
            	
            	/*表单验证*/
                var validator = $('#findForm').validate({
                	rules: {
                      
                        needEpoint: {
                            required: true,
                            number:true,
                        },
                    },
                    messages: {
                        paymentTotalActual: {
                            required: "请填写需要的积分数",
                            number:"积分必须为数字",
                        },
                    },
                    
                    submitHandler: function(form) {
                        // 验证成功后操作
                       console.log($(form).serialize());
                       form.submit();
                       
                    }
                });
            });
            
           
    	  	
    		
    		
        </script>
	
</head>
 <body id="index">
        <div class="container-fluid">
            <div class="row">
			
			  <div class="col-md-12 content">
			  
			     <div class="panel panel-info">
                    <div class="panel-heading">
                          <h3 class="panel-title"><i class="fa fa-cog"></i> 积分商品编辑</h3>
                    </div>
				    <div class="panel-body">
				      <form class="form-horizontal" method="post" action="${contextPath }/skuEpoint/updateSkuEpoint" id="findForm" >
				 
						<input type="hidden" name="id" id="id" value="${skuEpoint.id}">
						<div class="form-group">
							<label class="col-sm-2 control-label">商品名：</label>
							<div class="col-sm-2">
								<input  id="skuName" type="text" class="form-control" value="${skuEpoint.skuName}" readonly/>
							</div>
						</div>
				
						
						<div class="form-group">
							<label class="col-sm-2 control-label">商品标签：</label>
							<div class="col-sm-2">
								<select  name="labelCode" id="labelCode" class="form-control lg-select" data-placeholder="Choose a district" tabindex="1">
									<option value="1" <c:if test="${skuEpoint.labelCode=='1' }">selected="selected"</c:if>  >热门兑换</option>
					            	<option value="2" <c:if test="${skuEpoint.labelCode=='2' }">selected="selected"</c:if>  >可以兑换</option>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">兑换商品需要的积分：</label>
							<div class="col-sm-2">
								<input name="needEpoint" id="needEpoint" type="text" class="form-control" value="${skuEpoint.needEpoint}"/>
							</div>
							<div class="error warning col-sm-2  textL">必填项</div>
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