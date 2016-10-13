<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/ztree.jsp" %><!-- 弹出框引用 -->


<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>转入余额</title>
	 <script type="text/javascript">
	 
	 </script>
	 
</head>
 <body id="index"  style="background:#fff">
   
        <div class="container-fluid" >
            <div class="row">

			  <div class="col-md-6 content" >
			  
				      <form class="form-horizontal" method="post"  id="accountForm">
						
						
						<div class="form-group">
							<label class="col-sm-3 control-label">账号：</label>
							<div class="col-sm-3">
						 		 <input type="text" class="form-control" id="account" name="account" disabled="disabled"
						 		 value="${alabaoAccount.account }">
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label">真实姓名：</label>
							<div class="col-sm-3">
						 		 <input type="text" class="form-control" id="trueName" name="trueName" disabled="disabled"
						 		 value="${alabaoAccount.trueName }">
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label">证件号：</label>
							<div class="col-sm-3">
						 		 <input type="text" class="form-control" id="identityCardNo" name="identityCardNo" disabled="disabled"
						 		 value="${alabaoAccount.identityCardNo }">
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label">当前余额：</label>
							<div class="col-sm-3">
						 		 <input type="text" class="form-control" id="balance" name="balance" disabled="disabled"
						 		 value="${alabaoAccount.balance }">
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label">选择公司：</label>
							<div class="col-sm-3">
						 		 <select id="companyAccount" name="companyAccount" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
						 		 	<c:forEach items="${companyAccountList }" var="companyAccount">
						 		 		<option value="${companyAccount.account  }">${companyAccount.trueName }</option>
						 		 	</c:forEach>
						 		 </select>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label">转入余额：</label>
							<div class="col-sm-3">
						 		 <input type="text" class="form-control" id="shiftToAmount" name="shiftToAmount"
						 		 		onkeyup="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]" maxlength="19"
   										onafterpaste="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]"   />
						 		 
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label">备注：</label>
							<div class="col-sm-3">
						 		 <input type="text" class="form-control" id="memo" name="memo" />
							</div>
						</div>
						
						
					<%-- 	 <div class="form-group">
                          <label class="col-sm-2 control-label">是否发短息通知：</label>
                                   <div class="col-sm-5">
                                       
                                       <select name="isInform" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
							            <c:forEach items="${dic:getDictByType('FLAG')}" var="dict">
							            	<option value="${dict.value }"  <c:if test="${dict.value=='y' }">selected</c:if> >${dict.label }</option>
							            </c:forEach>
							        </select>
                                       
                                   </div>
                               </div> --%>
                               
                            <div class="form-group">
							<label class="col-sm-3 control-label">选择短信签名：</label>
							<div class="col-sm-3">
						 		 <select id="smsTypeId" name="smsTypeId" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
							 	<%-- 	 <c:forEach items="${smsTypeList }" varStatus="status" var="smsType" >
							 		     <option value="${smsType.id }"  >${smsType.smsTemplate}</option>
							 		 </c:forEach> --%>
							 		 <option value="18" <c:if test="${smsTypeId eq '18' }"> selected='selected' </c:if> >【乾玺】您尾号为[code]的如意消费卡账户,[time]时收入人民币[faceValue]元,活期余额为[balance]元. </option>
							 		 <option value="16" <c:if test="${smsTypeId eq '16' }"> selected='selected' </c:if> >【阿拉丁玛特】该账户:[account]通过如意消费卡给您转账[faceValue]元，请查收。阿拉丁玛特祝您生活愉快。</option>
						 		 </select>
							</div>
						</div>
						
					</form>
       		</div>
       	  </div>
       	</div>
   </body>
</html>