<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->    
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>如意宝转入转出列表</title>
    </head>
    <script type="text/javascript">
    

    function Edit(id){
    	window.location.href = "${contextPath}/alabaoTurnOut/alabaoTurnOutEditJsp/"+id+"/"+new Date().getTime();
    }
    
    
    function  Del(id){
		
  		var submit =  function(v,h,f){
  			if(v=="ok"){
  				window.location.href = "${contextPath}/alabaoTurnOut/alabaoTurnOutDel/"+id+"/"+new Date().getTime();
  			}
  			return true;
  		} 
  		$.jBox.confirm("你确定要删除吗？", "删除提示",submit);
    }
    
    function alabaoTurnOutListExport(){
		$("#findForm").attr("action",contextPath+"/alabaoTurnOut/alabaoTurnOutListExport");
		$("#findForm").submit();
		$("#findForm").attr("action",contextPath+"/alabaoTurnOut/alabaoTurnOutList");
		
	}
    </script>
    
    <body id="" class="Cog">
           <tags:message content="${message}"></tags:message>
        <div class="container-fluid">
            <div class="row">
               <%--  <div id="menu">
                <script src="${contextStatic}/bootstrap/js/leftMenu.js"></script><!-- 公共左侧菜单 -->
                </div> --%>
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i>如意宝转入转出列表 </h3>
                        </div>
                        <div class="panel-body">
                          	<b>转入列表</b> 
                            <!-- table -->
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover" id="roleTable">
                                    <thead>
                                        <tr>
                                            <th>序号</th>
                                            <th>如意消费卡账户</th>
                                            <th>转移金额</th>
                                            <th>转入账户类型</th>
                                            <th>转入时间</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${listShiftToAll}" var="list" varStatus="status">
                                    	<tr>
                                            <td>${status.count}</td>
                                            <td>${account}</td>
	                                        <td>${list.transferMoney}</td>
	                                        <td>
												<c:if test="${list.shiftToAccountType== '1'}">阿拉宝</c:if>
	                                        	<c:if test="${list.shiftToAccountType== '2'}">银联</c:if>
	                                        	<c:if test="${list.shiftToAccountType== '3'}">微信</c:if>
	                                        	<c:if test="${list.shiftToAccountType== '4'}">支付宝</c:if>
	                                        	<c:if test="${list.shiftToAccountType== '5'}">益多宝</c:if>
	                                        	<c:if test="${list.shiftToAccountType== '7'}">退款</c:if>
	                                        	<c:if test="${list.shiftToAccountType== '8'}">实体卡</c:if>
	                                        </td>
	                                        <td><fmt:formatDate value="${list.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            
                          	 <b>转出列表</b> 
                            <!-- table -->
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover" id="roleTable">
                                    <thead>
                                        <tr>
                                            <th>序号</th>
                                            <th>如意消费卡账户</th>
                                            <th>转移金额</th>
                                            <th>转出账户类型</th>
                                            <th>转出时间</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${listRollOffAll}" var="list" varStatus="status">
                                    	<tr>
                                            <td>${status.count}</td>
                                            <td>${account}</td>
	                                        <td>${list.transferMoney}</td>
	                                        <td>
	                                     	   <c:if test="${list.rollOffAccountType== '1'}">阿拉宝</c:if>
	                                     	   <c:if test="${list.rollOffAccountType== '2'}">银联</c:if>
	                                        </td>
	                                        <td><fmt:formatDate value="${list.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <button class="btn btn-primary" type="button" onclick="javascript:history.go(-1);">返回</button>
                        <!-- /.panel-body -->
                    </div>
                </div>
                <!-- /.content -->
            </div>
        </div>
        <!-- /.container-fluid -->

    </body>
</html>