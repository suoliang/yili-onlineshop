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
        <title>阿拉丁卡配置列表</title>
    </head>
    <script type="text/javascript">
    function updateStatus(id){
    	var submit =  function(v,h,f){
  			if(v=="ok"){
  				window.location.href = "${contextPath}/memberCardConfig/updateStatus/"+id+"/"+new Date().getTime();
  			}
  			return true;
  		}
  		$.jBox.confirm("你确定要修改该阿拉丁卡配置状态吗？", "修改提示",submit);
    }
    

    function configEdit(id){
    	window.location.href = "${contextPath}/memberCardConfig/configEditJsp/"+id+"/"+new Date().getTime();
    }
    
    
    function  deleteconfig(id){
		
  		var submit =  function(v,h,f){
  			if(v=="ok"){
  				window.location.href = "${contextPath}/memberCardConfig/configDel/"+id+"/"+new Date().getTime();
  			}
  			return true;
  		} 
  		$.jBox.confirm("你确定要删除该阿拉丁卡配置吗？", "删除提示",submit);
    }
    </script>
    
    <body id="" class="Cog">
           <tags:message content="${message }"></tags:message>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i>阿拉丁卡配置列表 </h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                
                               <div class="form-group col-md-4 mB15">
                                   <label for="a" class="col-label">卡类型：</label>
                                   <select name="type" class="form-control lg-select">
                                      <option value="" >--请选择--</option>
                                      <option value="1" >季卡</option>
                                      <option value="2" >双季卡</option>
                                      <option value="3" >年卡</option>
                                   </select>
                               </div>
                                
                                <div class="form-group col-md-4 mB15">
		                            <label for="a">创建开始时间：</label>
		                            <div class="input-group">
		                             <input type="text" name="createTimeFrom" value="${createTimeFrom}" class="timeS form-control form_datetime" readonly>
		                             <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
		                        	</div>
		                        </div>
		                        <div class="form-group col-md-4 mB15">
		                            <label for="a">创建结束时间：</label>
		                            <div class="input-group">
		                             <input type="text" name="createTimeTo" value="${createTimeTo}" class="timeE form-control form_datetime" readonly>
		                             <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
		                        	</div>
		                        </div>
                                <div class="form-group col-md-4 mB15">
                                <button type="submit" class="btn btn-success speBtn">查 询</button>
                                <a href="${contextPath}/memberCardConfig/configAddJsp" class="btn btn-info speBtn">新增</a>
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
                                            <th>卡类型</th>
											<th>赠劵利率（年化）</th>
											<th>最低起充金额</th>
											<th>时间期限（月）</th>
											<th>创建时间</th>
											<th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${list}" var="list" varStatus="status">
                                    	<tr>
                                            <td>${ status.count}</td>
											<td>
												<c:if test="${list.type eq '1' }">季卡</c:if>
											    <c:if test="${list.type eq '2' }">双季卡</c:if>
											    <c:if test="${list.type eq '3' }">年卡</c:if>
											</td>
											<td>${ list.rebate}</td>
											<td>${ list.lowestMoney}</td>
											<td>${list.timeLine }</td>
											<td><fmt:formatDate value="${list.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                            <td>
                                            			<button type="button"  class="btn btn-success btn-xs delete-role" onclick="javascript:configEdit('${list.id }');">修改</button>
												  		<c:choose>
															<c:when test="${list.isDisabled == 'n'}">
																<a class="btn btn-warning btn-xs" href="javascript:updateStatus('${list.id}');" >
																	禁用	
																</a>
															</c:when>
															<c:when test="${list.isDisabled == 'y'}">
																<a class="btn btn-warning btn-xs" href="javascript:updateStatus('${list.id}');" >
																	解禁	
																</a>
															</c:when>
														</c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/memberCardConfig/configList"></tags:page>
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