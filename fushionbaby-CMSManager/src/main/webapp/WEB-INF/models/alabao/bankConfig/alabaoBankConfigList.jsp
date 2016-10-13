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
        <title>权限列表</title>
    </head>
    <script type="text/javascript">
    

    function Edit(id){
    	window.location.href = "${contextPath}/alabaoBankConfig/alabaoBankConfigEditJsp/"+id+"/"+new Date().getTime();
    }
    
    
    function  Del(id){
		
  		var submit =  function(v,h,f){
  			if(v=="ok"){
  				window.location.href = "${contextPath}/alabaoBankConfig/alabaoBankConfigDel/"+id+"/"+new Date().getTime();
  			}
  			return true;
  		} 
  		$.jBox.confirm("你确定要删除吗？", "删除提示",submit);
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
                            <h3 class="panel-title"><i class="fa fa-user"></i>如意宝银行配置列表 </h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                
					   <div class="form-group col-md-4 mB15">
	    					<label for="code" class="col-label">银行编码：</label>
	      					<input type="text" id="bankCode" name="bankCode" class="form-control" value="${bankCode}"
	      						 placeholder="银行编码">
	  					</div>
				    
					    <div class="form-group col-md-4 mB15">
	    					<label for="name" class="col-label">银行名称：</label>
	      					<input type="text" id="bankName" name="bankName" class="form-control" value="${bankName}"  
	      						placeholder="银行名称" >
	  					</div>
                                <div class="form-group col-md-4 mB15">
                                <button type="submit" class="btn btn-success speBtn">查 询</button>
                                <a href="${contextPath}/alabaoBankConfig/alabaoBankConfigAddJsp" class="btn btn-info speBtn">新增</a>
                                </div>
                                
                                <input type="hidden" name="currentPage" value="${page.currentPage}"/>
								<input type="hidden" name="totalPage" value="${page.totalPage}" disabled="disabled"/>  
								<input type="hidden" name="limit" value="${page.limit}"  size="3"/>
								<input type="hidden" name="total" value="${page.total}"/>
								
                                <div class="clearfix"></div>
                        </form>   
                            <!-- table -->
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover" id="roleTable">
                                    <thead>
                                        <tr>
                                            <th>序号</th>
                                            <th>银行名称</th>
                                            <th>银行配置编码</th>
                                            <th>银行图片</th>
                                            <th>描述</th>
                                            <th>创建时间</th>
										<th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${list}" var="list" varStatus="status">
                                    	<tr>
                                            <td>${status.count}</td>
                                            
	                                            <td>${list.bankName}</td>
	                                            <td>${list.bankCode}</td>
	                                            <td><a href="${imagePath}${list.bankIconUrl}"  class="fancybox" rel="gallery">
											   		<img src="${imagePath}${list.bankIconUrl}"
											   		kesrc ="${imagePath}${list.bankIconUrl}" width="50" height="50" /></a>
											   	</td>
	                                            <td>${list.bankDesc}</td>
	                                            <td><fmt:formatDate value="${list.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                            <td>
                                            	<button type="button"  class="btn btn-success btn-xs delete-role" onclick="javascript:Edit('${list.id}');">修改</button>
                                            	<a class="btn btn-success btn-xs edit-role" href="${contextPath}/alabaoBankConfig/findBankImage/${list.bankCode }" title="图片" >图片</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/alabaoBankConfig/alabaoBankConfigList"></tags:page>
                            <!-- 分页 end -->
                             
                        </div>
                        <!-- /.panel-body -->
                    </div>
                </div>
                <!-- /.content -->
            </div>
        </div>
        <!-- /.container-fluid -->

    </body>
</html>