<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>产品管理</title>
	
</head>
<body id="index">
		<tags:message content="${message }"></tags:message>
	
        <div class="container-fluid">
            <div class="row">
			  <div class="col-md-12 content">
					<div class="panel panel-info">
                   		<div class="panel-heading">
                      		<h3 class="panel-title"><i class="fa fa-cog"></i> 银行图片上传</h3>
                   		</div>
                   		<div class="panel-body">
				     		<form class="form-horizontal page" id="findForm" method="post" action="${contextPath }/alabaoBankConfig/updateBankImage">
				     		<input type="hidden" name="id" id="id" value="${alabaoBankConfig.id}" >
							   <div class="form-group col-md-2 mB15">
			    					<label for="skuNo">银行编码：</label>
			      					<input type="text" id="bankCode" name="bankCode" class="form-control" value="${alabaoBankConfig.bankCode}" readonly="readonly"
			      						 placeholder="银行编码">
			  					</div>
				    
							    <div class="form-group col-md-8 mB15">
			    					<label for="name">银行名称：</label>
			      					<input type="text" id="bankName" name="bankName" class="form-control" value="${alabaoBankConfig.bankName}" readonly="readonly"
			      						placeholder="银行名称" >
			  					</div>
			  					
			  					<div class="form-group">
								    	<div class="col-sm-offset-2 col-sm-10">
								      		<button class="btn btn-primary col-md-2" type="button" onclick="javascript:history.go(-1);">返回上一页</button>
								   	 	</div>
								</div>
				    	
				   				<div class="clearfix"></div>
	
						  		<div class="panel panel-default">
						  		<div class="panel-heading">
		                      		<h3 class="panel-title"> 上传图片</h3>
		                   		</div>
						  			
							  		<div class="form-group">
										<div class="col-sm-4">
											<input type="hidden" id="bankIconUrl" name="bankIconUrl"  value="${imagePath}${alabaoBankConfig.bankIconUrl}"/>
											<input type="hidden" id="oldbankIconUrl" name="oldbankIconUrl" value="${imagePath}${alabaoBankConfig.bankIconUrl}" >
											<!-- <input type="hidden" id="id" name="id" > -->
											
											<tags:ckfinder input="bankIconUrl" type="images" 
													uploadPath="/bank/" selectMultiple="false" />
										</div>
									</div>
									
									
									<div class="form-group">
								    	<div class="col-sm-offset-2 col-sm-10">
								      		<button class="btn btn-success col-md-2" type="submit">确认上传</button>
								   	 	</div>
									</div>
								</div>
								</form>
								
								
								  <!-- table -->
                      <div class="table-responsive">
                        <table class="table table-bordered table-hover" id="roleTable">
                           <thead>
                             <tr>
							  <th>图片展示</th>
							  <th>创建时间</th>
							 </tr>
                            </thead>
                            <tbody>
								<tr>
								   <td>
								   	<a href="${imagePath}${alabaoBankConfig.bankIconUrl}"  class="fancybox" rel="gallery">
								  	 <img alt="" src="${imagePath}${alabaoBankConfig.bankIconUrl}" kesrc ="${imagePath}${alabaoBankConfig.bankIconUrl}" width="70" height="50"  />
								    </a>
								   </td>	
								   
								   <td><fmt:formatDate value="${alabaoBankConfig.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>	
								  </tr>
                                </tbody>
                              </table>
							</div>	
							</div>
                        </div>
					</div>
                </div>
                <!-- /.content -->
            </div>
       
        <!-- /.container-fluid -->
</body>
</html>
