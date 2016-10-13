<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>清除静态化页面</title>

<#include "/common/common.ftl" />
<script>
	function delDetailStatic(){
		var skuCode = $("#skuCode").val();
		window.location.href="${rc.contextPath}/webtest/delSkuDetailStatic?skuCode="+skuCode;
	}
</script>
</head>
<body>
	<div>
		<div>
			<a style="color:blue" href="${rc.contextPath}/webtest/delIndexStatic" >移除首页静态化</a>
		</div>
		<div>
			<a style="color:blue" href="javascript:delDetailStatic()">移除商品详情静态化</a>
			输入商品编号：<input type="text" id="skuCode" />
		</div>
	</div>
</body>
</html>