<@compress single_line=true>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>宝贝风采 潮流宝贝 宝贝街头秀|Fushionbaby 像在国外一样任性</title>
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/children.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="stylesheet" href="http://cdn.bootcss.com/font-awesome/4.3.0/css/font-awesome.min.css">
	<#include "/common/link.ftl"/>
</head>
<body>
    <#include "/common/topbar.ftl"/>
	<#include "/common/header.ftl"/>
	<div class="main container">
		<div class="breadcrumb_nav">
			<a href="#">会员增值</a>
			<i class="fa fa-angle-double-right"></i>
			<a href="#">风尚宝贝志</a>
			<i class="fa fa-angle-double-right"></i>
			<a href="#">水杯专题</a>
		</div>
	   <script type="text/javascript" language="javascript">
			function iFrameHeight() {			
			    var ifm = document.getElementById("iframepage");			
			    var subWeb = document.frames ? document.frames["iframepage"].document :ifm.contentDocument;			
			        if(ifm != null && subWeb != null) {			
			           ifm.height = subWeb.body.scrollHeight;		
			        }
			}
	    </script> 		 
       <iframe src="${web_article_url}" marginheight="0" marginwidth="0" width="100%" frameborder="0" scrolling="no"  name="iframepage"  height="500px" id="iframepage"  onLoad="iFrameHeight()"></iframe>
	</div>
	<div class="gap"></div>
	<#include "/common/footer.ftl"/>
	<#include "/common/nav.ftl"/>
</body>
</html>
</@compress>