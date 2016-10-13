<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>门店商户银行卡管理</title>
  	<script type="text/javascript">
	  	function modifyBankCard(cardNo,id){
	  		if(cardNo == null || cardNo == ''){
	  			window.location.href="${contextPath }/storeSpoBank/goAddCard?time="+new Date().getTime();
	  		}else{
	  			window.location.href="${contextPath }/storeSpoBank/updateBank/"+id+"?time="+new Date().getTime();
	  		}
	  	}
  	</script>
	
</head>
<body>
		<tags:message content="${message }"></tags:message>
            <div class="row">
			  <div class="col-md-12 content">
				<div class="panel panel-info">
                   <div class="panel-heading">
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 商户银行信息</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post" action="${contextPath }/storeSpoBank/myBankInfo">

				   		<div class="clearfix"></div>
				   		
					  </form>
                      <!-- table -->
                      <div class="table-responsive">
                        <table class="table table-bordered table-hover" id="roleTable">
                           <thead>
                             <tr>
                           	  
                           	  <th>银行</th>
                           	  <th>卡号</th>
							  <th>姓名</th>
							  <th>分行</th>
							  <th>银行归属省份</th>
							  <th>银行归属市</th>
							  <th>状态</th>
							  <th>更新时间</th>
							  <th>操作</th>
							 </tr>
                            </thead>
                            
                            <tbody>
								<tr>
				
								   <td>${storeSponsorsBank.bankName }</td>
								   <td>${storeSponsorsBank.cardNo}</td>	
								   <td>${storeSponsorsBank.cardHolder}</td>	
								   <td>${storeSponsorsBank.bankBranchName}</td>	
								   <td>${storeSponsorsBank.province}</td>	
								   <td>${storeSponsorsBank.city}</td>	
								   <td><c:if test="${storeSponsorsBank.isValidate == '1'}">未认证</c:if>
								   	   <c:if test="${storeSponsorsBank.isValidate == '2'}">认证失败</c:if>
								       <c:if test="${storeSponsorsBank.isValidate == '3'}">已认证</c:if>
								   </td>	
								   <td><fmt:formatDate value="${storeSponsorsBank.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								   <td>
								   	<c:if test="${empty  storeSponsorsBank.cardNo }">
									 <a class="btn btn-warning btn-xs edit-role" href="javascript:void(0)"  onclick="modifyBankCard('${storeSponsorsBank.cardNo }','${storeSponsorsBank.id }')">
									 	添加银行卡
									 	<%-- <c:if test="${not empty  storeSponsorsBank.cardNo }">修改银行卡</c:if> --%>
									 </a>
									 </c:if>
								   </td>	
								  </tr>
                                </tbody>
                              </table>
                           </div>
                            
                        </div>
					</div>
                </div>
            </div>
        <!-- /.container-fluid -->
</body>
</html>
