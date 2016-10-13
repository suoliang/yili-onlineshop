<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>实体卡交易信息打印</title>
	
	<script type="text/javascript">
	
	function  closeBox(){
		parent.location.reload();
		window.parent.window.jBox.close();
	}
	</script>
</head>
     <body class="backWhite">
		<div class="panel pT30 mB0">
              <form class="form-horizontal" method="post" id="handleForm" >
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">卡号：</label>
					<div class="col-sm-4">
						<input name="cardNo" id="cardNo" readonly type="text" class="form-control" value="${entityCard.cardNo}" />
					</div>
				</div>
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">卡内余额：</label>
					<div class="col-sm-4">
						<input name="surplusMoney" id="surplusMoney" readonly type="text" class="form-control" value="${entityCard.surplusMoney}" />
					</div>
				</div>
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">消费时间：</label>
					<div class="col-sm-4">
						<input name="createTime" id="createTime" readonly type="text" class="form-control" value="<fmt:formatDate value='${entityCardUseRecord.createTime}' pattern="yyyy-MM-dd HH:mm:ss" />" />
					</div>
				</div>
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">消费金额：</label>
					<div class="col-sm-4">
						<input name="money" id="money" readonly type="text" class="form-control" value="${entityCardUseRecord.money}"  />
					</div>
				</div>
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">交易流水：</label>
					<div class="col-sm-4">
						<input name="serialNo" id="serialNo" readonly type="text" class="form-control" value="${entityCardUseRecord.serialNo}"  />
					</div>
				</div>
		
			<div class="form-group mL0 mR0">
		    	<div class="col-sm-offset-4 col-sm-8">
		      	    <button class="btn btn-primary" onclick="javascript:closeBox();" >关闭打印页面</button>
		   	 	</div>
			</div>
		</form>
	 </div>
   </body>	
</html>