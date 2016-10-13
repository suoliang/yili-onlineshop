<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->


<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>新增银行卡信息</title>
	 <script type="text/javascript">
            $(document).ready(function() {
            	
            	/*表单验证*/
                var validator = $('#dictForm').validate({
                    rules: {
                    	bankName: {
                            required: true,
                          
                        },
                        cardHolder: {
                            required: true,
                            rangelength: [2, 100],
                        },
                        cardNo: {
                            required: true,
                            rangelength: [10, 25],
                           
                        }
                        
                    },
                    messages: {
                    	bankName: {
                            required: "请选择银行名称！",
                        },
                        cardHolder: {
                        	required: "请填写姓名！",
                        	rangelength: "姓名长度在2-5之间！"
                        },
                        cardNo: {
                        	required: "请填写银行卡号！",
                        	rangelength: "您输入的银行卡号有误！"
                           
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
                          <h3 class="panel-title"><i class="fa fa-cog"></i> 银行卡信息编辑</h3>
                    </div>
				    <div class="panel-body">
				      <form class="form-horizontal" method="post" action="${contextPath }/storeSpoBank/updateBankCard" id="dictForm" >
				 
				 		<input name="id" id="id" type="hidden" class="form-control"
						 			value="${storeSponsorsBank.id}"/>
				 		<input name="storeCode" id="storeCode" type="hidden" class="form-control"
						 			value="${storeSponsorsBank.storeCode}"/>
						
						
							 <div class="form-group">
                                   <label for="a" class="col-sm-2 control-label">银行名称：</label>
                                 	<div class="col-sm-2">
	                                   <select name="bankName" id="bankName" class="form-control lg-select">
	                                      <option value="中国工商银行" ${storeSponsorsBank.bankName=='中国工商银行'?'selected':''} >中国工商银行</option>
	                                      <option value="中国农业银行" ${storeSponsorsBank.bankName=='中国农业银行'?'selected':''} >中国农业银行</option>
	                                      <option value="中国建设银行" ${storeSponsorsBank.bankName=='中国建设银行'?'selected':''} >中国建设银行</option>
	                                      <option value="中国银行" ${storeSponsorsBank.bankName=='中国银行'?'selected':''} >中国银行</option>
	                                      <option value="招商银行" ${storeSponsorsBank.bankName=='招商银行'?'selected':''} >招商银行</option>
	                                   </select>
                                   </div>
                                   <div class="error col-sm-2  textL" style="padding-top:7px;">必填项</div>
                               </div>
				
						<div class="form-group">
							<label class="col-sm-2 control-label">姓名：</label>
							<div class="col-sm-2">
								<input name="cardHolder" id="cardHolder" type="text" class="form-control"
						 			value="${storeSponsorsBank.cardHolder}"/>
							</div>
							<div class="error col-sm-2  textL" style="padding-top:7px;">必填项</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">卡号：</label>
							<div class="col-sm-2">
								<input name="cardNo" id="cardNo" type="text" class="form-control" 
								 	value="${storeSponsorsBank.cardNo}"/>
							</div>
							<div class="error col-sm-2  textL" style="padding-top:7px;">必填项</div>
						</div>
						
				
						<div class="form-group">
							<label class="col-sm-2 control-label">分行名称：</label>
							<div class="col-sm-2">
								<input name="bankBranchName" id="bankBranchName" type="text" class="form-control" 
								 	value="${storeSponsorsBank.bankBranchName}"/>
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