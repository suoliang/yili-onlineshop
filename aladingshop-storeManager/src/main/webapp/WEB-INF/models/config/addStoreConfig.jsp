<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->


<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>配置门店参数</title>
	 <script type="text/javascript">
            $(document).ready(function() {
            	
            	/*表单验证*/
                var validator = $('#dictForm').validate({
                    rules: {
                    	bussinessStartTime: {
                            required: true,
                          
                        },
                        bussinessEndTime: {
                            required: true,
                        },
                        freeFreightAmount: {
                            required: true,
                            digits:true,
                        },
                        freightAmount:{
                        	required:true,
                        	digits:true
                        }
                        
                    },
                    messages: {
                    	bussinessStartTime: {
                            required: "请选择开店时间！",
                        },
                        bussinessEndTime: {
                        	required: "请选择休息时间！",
                        },
                        freeFreightAmount: {
                        	required: "请填写免运费最低订单金额！",
                        	digits: "免运费金额输入有误！"
                        },  
                        freightAmount:{
                        	required: "请填配送费！",
                        	digits: "配送费输入有误！"
                        }
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
 		  <tags:message content="${message}"></tags:message>
        <div class="container-fluid">
            <div class="row">

           
			  <div class="col-md-12 content">
			  
			     <div class="panel panel-info">
                    <div class="panel-heading">
                          <h3 class="panel-title"><i class="fa fa-cog"></i> 配置门店参数</h3>
                    </div>
				    <div class="panel-body">
				      <form class="form-horizontal" method="post" action="${contextPath }/storeConfig/insert" id="dictForm">
				 
					    <div class="form-group">
                                   <label for="a" class="col-sm-2 control-label">开店时间：</label>
                                 	<div class="col-sm-3">
	                                   <select name="businessStartTime" id="businessStartTime" class="form-control lg-select">
	                                      <option value="00:00:00" ${storeConfig.businessStartTime=='00:00:00'?'selected':''} >00:00:00</option>
	                                      <option value="07:00:00" ${storeConfig.businessStartTime=='07:00:00'?'selected':''} >07:00:00</option>
	                                      <option value="08:00:00" ${storeConfig.businessStartTime=='08:00:00'?'selected':''} >08:00:00</option>
	                                      <option value="09:00:00" ${storeConfig.businessStartTime=='09:00:00'?'selected':''} >09:00:00</option>
	                                      <option value="10:00:00" ${storeConfig.businessStartTime=='10:00:00'?'selected':''} >10:00:00</option>
	                                   </select>
                                   </div>
                                   <div class="error col-sm-2  textL" style="padding-top:7px;">必填项</div>
                        </div>
				
					    <div class="form-group">
                                   <label for="a" class="col-sm-2 control-label">关店时间：</label>
                                 	<div class="col-sm-3">
	                                   <select name="businessEndTime" id="businessEndTime" class="form-control lg-select">
	                                      <option value="19:00:00" ${storeConfig.businessEndTime=='19:00:00'?'selected':''} >19:00:00</option>
	                                      <option value="20:00:00" ${storeConfig.businessEndTime=='20:00:00'?'selected':''} >20:00:00</option>
	                                      <option value="21:00:00" ${storeConfig.businessEndTime=='21:00:00'?'selected':''} >21:00:00</option>
	                                      <option value="22:00:00" ${storeConfig.businessEndTime=='22:00:00'?'selected':''} >22:00:00</option>
	                                      <option value="23:00:00" ${storeConfig.businessEndTime=='23:00:00'?'selected':''} >23:00:00</option>
	                                      <option value="00:00:00" ${storeConfig.businessEndTime=='00:00:00'?'selected':''} >00:00:00</option>
	                                   </select>
                                   </div>
                                   <div class="error col-sm-2  textL" style="padding-top:7px;">必填项</div>
                        </div>
				
						<div class="form-group">
							<label class="col-sm-2 control-label">免配送费最低订单金额：</label>
							<div class="col-sm-3">
								<input name="freeFreightAmount" id="freeFreightAmount" type="number" class="form-control"  value="${storeConfig.freeFreightAmount}"/>
							</div>
							<div class="error col-sm-2  textL" style="padding-top:7px;">必填项</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">配送费：</label>
							<div class="col-sm-3">
								<input name="freightAmount" id="freightAmount" type="number"  class="form-control" 	value="${storeConfig.freightAmount}"/>
							</div>
							<div class="error col-sm-2  textL" style="padding-top:7px;">必填项</div>
						</div>
						
					  
						<div class="form-group">
					    	<div class="col-sm-offset-2 col-sm-10">
					      	
					      		<button class="btn btn-success " type="submit" >确定</button>
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