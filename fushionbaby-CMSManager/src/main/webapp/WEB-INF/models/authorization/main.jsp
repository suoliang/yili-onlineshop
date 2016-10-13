<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>ALADing- CMS</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="renderer" content="webkit">
        <meta name="description" content="阿拉丁后台管理系统">
        <meta name="keywords" content="阿拉丁后台管理系统">
        <link rel="shortcut icon" href="${contextStatic }/images/favicon.ico" />
    </head>
    <script type="text/javascript">
   
    function gotoIframe(url,icon,obj){
    	$('.list-group-item').removeClass('active');
    	$(obj).addClass('active');
    	$("#iframepage").attr("title",icon);
    	$("#iframepage").attr("src",'${contextPath}/'+url);
    }
    
    </script>
    
    
    <body id="" class="main-body">
    <tags:message content="${message }"></tags:message>
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-navbar-collapse">
                    <span class="sr-only">显示/隐藏导航</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#" title="首页"><i class="fa fa-medium text-info"></i> ALADing CMS</a>
                </div>
                <div class="collapse navbar-collapse" id="bs-navbar-collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="javascript:void(0);" class="cursorD"><i class="fa fa-user"></i>  <c:if test="${userName!=null }">${userName }</c:if></a></li>
                        <li><a href="${pageContext.request.contextPath }/login/index" title="退出登录" id="signOut"><i class="fa fa-sign-out"></i> sign out</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="" id="leftMenu">
            <div class="panel-group" id="accordion">
            
             <c:forEach items="${menuPrivilegeList}" var="privilegeMap">
                   <c:forEach items="${privilegeMap}" var="item">
            
            
		                <div class="panel panel-default ${item.key.iconClass }-menu">
		                    <div class="panel-heading collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapse${item.key.id}">
		                        <h4 class="panel-title">
		                        <i class="fa ${item.key.iconClass }"></i><c:out value="${item.key.name}"></c:out>
		                        <span class="badge pull-right"></span>
		                        </h4>
		                    </div>
		                    <div id="collapse${item.key.id}" class="panel-collapse collapse">
		                        <div class="panel-body">
		                            <div class="list-group">
		                            <c:forEach items="${item.value }" var="privilege">
		                               <a class="list-group-item" href="javascript:void(0)" onclick="gotoIframe('${privilege.url}','${item.key.iconClass }',this)" target="iframepage">${privilege.name}</a>
		                            </c:forEach>
		                            </div>
		                        </div>
		                    </div>
		                </div>
                   </c:forEach>
               </c:forEach> 
               
            </div>
            <!-- /.panel-group -->
        </div>
        <div id="page_height" style="padding:0px;margin:0px 0px 0px 0px;" >
	         <iframe title="Cart" class="main-iframe" height="800" name="iframepage" src="" frameborder="0" id="iframepage"   
	        	 style="background-color:#3D3D3D;padding:0px;margin:0px;"
					marginheight="0" marginwidth="0" onload="iFrameHeight()"
	         ></iframe>
         </div>
         <script type="text/javascript" language="javascript">
		  function iFrameHeight(){
			var ifm= document.getElementById("iframepage");
			var h=document.getElementById("page_height").offsetHeight;
			var ih=ifm.contentDocument.body.clientHeight;
			//alert("div高度:"+h+"==iframe高度:"+ih);
			if(h>ih){
				if(h>780){
					ifm.height = ih;
					document.getElementById("page_height").style.height=ih;
				}else{
					ifm.height = h;
					document.getElementById("page_height").style.height=h;
				}
			}else{
				ifm.height = ih;
				document.getElementById("page_height").style.height=ih;
			}
		  }
		  
		  
		</script> 
    </body>
</html>
