<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <li><a href="javascript:void(0);" class="cursorD"><i class="fa fa-user"></i>
                <c:if test="${userName!=null }">${userName }</c:if></a></li> 
                <li><a href="${pageContext.request.contextPath }/login/index" title="退出登录" id="signOut"><i class="fa fa-sign-out"></i> sign out</a></li> 
            </ul> 
        </div> 
    </div> 
</nav>

<div class="col-md-2" id="leftMenu">
    <div class="panel-group" id="accordion">
    <c:forEach items="${menuPrivilegeList}" var="privilegeMap">
        <c:forEach items="${privilegeMap}" var="item">
        <div class="panel panel-default">
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
                        <a class="list-group-item" href="${pageContext.request.contextPath }/${privilege.url}" >${privilege.name}</a>
                    </c:forEach>
                    </div>
                </div>
            </div>
        </div>
        </c:forEach>
        </c:forEach>
</div>
</div>