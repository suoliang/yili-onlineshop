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
        <title>积分列表</title>
        <script type="text/javascript">
        
        function deleteIntegral(memberId,registerType){
    	    var result;
    		var submit =  function(v,h,f){
      			if(v=="ok"){
      				$.ajax({
      					type : "post",
      					url : contextPath+"/integral/deleteIntegral",
      					data : {
      						memberId: memberId
      					},
      					success : function(data) {
      						result  = data;
      						if(result == "success"){
      		  					jBox.tip("操作成功", 'info');
      		  					window.setTimeout(function () {  location.reload(); }, 1500);
      		  				}else{
      		  					jBox.tip("操作失败", 'info');
      		  	  			}
      					},
      					error : function() {
      						alert("请求后台数据异常");
      					}
      				});
      			}
      			return true;
      		} 
    		$.jBox.confirm("确定要删除该用户吗？", "操作提示",submit);
    	}
	     
        function exportIntegralExcel(){
			$("#findForm").attr("action",contextPath+"/integral/export_excel_integral_list");
			$("#findForm").submit();
			$("#findForm").attr("action",contextPath+"/integral/epointOrderList");
			
		}
        
        
        </script>
    </head>
    <body id="" class="User">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i>积分列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">商品名称：</label>
                                    <input type="text" class="form-control" id="skuName" name="skuName" value="${integralDto.skuName}" placeholder="请输入商品名称">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="b" class="col-label">商品编码：</label>
                                    <input type="text" class="form-control" id="skuCode" name="skuCode" value="${integralDto.skuCode}" placeholder="请输入商品编码">
                                </div>    
                                <div class="form-group col-md-4 mB15">
                                    <label class="col-label">订单来源：</label>
                                    <select name="sourceCode" id="sourceCode" class="form-control lg-select">
                                        <option value="">所有</option>
                                       <c:forEach var="source" items="${sourceMap}">
                                       		<option value="${source.key}" <c:if test="${source.key == integralDto.sourceCode }"> selected="selected" </c:if>  > ${source.value}</option>
                                       </c:forEach>
                                    </select>
                                </div>
                                
                                <div class="form-group col-md-4 mB15">
		                            <label for="d" class="col-label">创建开始时间：</label>
		                             <div class="input-group">
		                                 <input type="text" class="timeS form-control form_datetime" readonly name="createTimeFrom" value="${integralDto.createTimeFrom}">
		                                 <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
		                             </div>
		                         </div>
		                         <div class="form-group col-md-4 mB15">
		                             <label for="e" class="col-label">创建结束时间：</label>
		                             <div class="input-group">
		                                 <input type="text" class="timeE form-control form_datetime" readonly name="createTimeTo" value="${integralDto.createTimeTo}">
		                                 <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
		                             </div>  
		                         </div>
 

                                <div class="col-md-8">
	                                <button type="submit" class="btn btn-info speBtn">查 询</button>
									<button type="button" onclick="exportIntegralExcel()" class="btn btn-success speBtn">导出积分表</button> 
                                </div>
                                
                                <div class="clearfix"></div>
                                <input type="hidden" name="currentPage" value="${page.currentPage}"/>
								<input type="hidden" name="totalPage" value="${page.totalPage}" disabled="disabled"/>  
								<input type="hidden" name="limit" value="${page.limit}"  size="3"/>
								<input type="hidden" name="total" value="${page.total}"/>
                            </form>
                            <!-- table -->
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover" id="roleTable">
                                    <thead>
                                        <tr>
                                            <th>序号</th>
                                            <th>订单编号</th>
                                            <th>商品编码</th>
                                            <th>商品名称</th>
                                            <th>商品数量</th>
                                            <th>商品状态</th>
                                            <th>订单来源</th>
                                            <th>使用积分</th>
                                            <th>创建时间</th>
                                            <th width="150px">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${orderEpointList}" var="orderEpoint" varStatus="status">
                                    	<tr>
                                            <td scope="row">${status.count}</td>
                                            <td>${orderEpoint.orderCode}</td>
                                            <td>${orderEpoint.skuCode}</td>
                                            <td>${orderEpoint.skuName}</td>
                                            <td>${orderEpoint.quantity}</td>
                                            <td>
                                            	<c:choose>
                                            		<c:when test="${orderEpoint.orderStatus =='2'}">交易完成</c:when> 
												  	<c:otherwise>代发货</c:otherwise> 
                                             	</c:choose>
                                            </td>
                                            <td>${sourceMap[orderEpoint.sourceCode]}</td>
                                            <td>${orderEpoint.totalEpointUsed}</td>
                                            <td><fmt:formatDate value="${orderEpoint.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>  
                                            <td>
                                            <c:choose>
                                            	<c:when test="${member.registerType !='isOther'}">
                                            			<button type="button" class="btn btn-danger btn-xs delete-role"  onclick="deleteIntegral('${orderEpoint.id}');">删除</button>
                                            	</c:when> 
                                            	<c:otherwise></c:otherwise> 
                                             </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/integral/epointOrderList"></tags:page>
                            <!-- 分页 end -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                </div>
                <!-- /.content -->
            </div>
        </div>
        <!-- /.container-fluid -->

        <!-- 删除角色提示框 -->
        <div class="modal fade" id="modalDeleteRole">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title"><i class="fa fa-info-circle"></i> 确认操作</h4>
                    </div>
                    <div class="modal-body text-danger">
                        是否确认删除此角色？
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-danger">确认删除</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.modal -->
    </body>
</html>