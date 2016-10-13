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
        <title>优惠券列表</title>
    </head>
    <script type="text/javascript">
    function  deleteCard(id){

  		var submit =  function(v,h,f){
  			if(v=="ok"){
  				window.location.href = "${contextPath}/promotion/deleteCard/"+id+"/"+new Date().getTime();
  			}
  			return true;
  		}
  		$.jBox.confirm("你确定要删除该优惠券吗？", "删除提示",submit);
    }
    function  cancelledCard(id){

  		var submit =  function(v,h,f){
  			if(v=="ok"){
  				window.location.href = "${contextPath}/promotion/useCard/"+id+"/"+new Date().getTime();
  			}
  			return true;
  		}
  		$.jBox.confirm("你确定要注销该优惠券吗？", "删除提示",submit);
    }
    </script>

    <body id="" class="Cog">
           <tags:message content="${message }"></tags:message>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-credit-card"></i>优惠券列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">

                                <div class="form-group col-md-3 mB15">
                                    <label for="a" class="col-label">优惠券卡号：</label>
                                    <input type="text" class="form-control" id="cardNo" name="cardNo" value="${cardDto.cardNo}" placeholder="请输入优惠券卡号">
                                </div>
                                    <div class="form-group col-md-3 mB15">
								        <label class="col-label">使用开始时间：</label>
								        <div class="input-group">
											<input class="timeS form-control form_datetime" readonly name="useTimeFrom" type="text" value="${cardDto.useTimeFrom}">
									        <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
								        </div>
								    </div>
                                    <div class="form-group col-md-3 mB15">
								        <label class="col-label">使用结束时间：</label>
								        <div class="input-group">
											<input class="timeE form-control form_datetime" readonly name="useTimeTo" type="text" value="${cardDto.useTimeTo}">
									        <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
								        </div>
								   </div>
                                <div class="form-group col-md-3 mB15">
                                    <label for="a" class="col-label">使用类型：</label>
	                                <select  name="useType" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
										<option value="">所有</option>
										<option value="once" ${param.useType == 'once' ?'selected':''}>一次使用</option>
										<option value="more" ${param.useType == 'more' ?'selected':''}>多次使用</option>
										<option value="noLimit" ${param.useType == 'noLimit' ?'selected':''}>无限制使用</option>
								    </select>
                                </div>
							 


                                <div class="form-group col-md-4 mB15">
                                <button type="submit" class="btn btn-success speBtn">查 询</button>
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
											<th>优惠券卡号</th>
											<th>优惠券类型名称</th>
											<th>最后使用时间</th>
											<th>已使用次数</th>
											<th>总可使用次数</th>
											<th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${cardList}" var="card" varStatus="status">
										<tr>
											<td>${status.count }</td>
											<td>${card.cardNo }</td>
											<td>${card.cardTypeName }</td>
											<td><fmt:formatDate value="${card.usedTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td>
												 ${card.useCount} 
											</td>
											<td>
												<c:if test="${card.useType=='nolimit'}">无限次使用</c:if>
												<c:if test="${card.useType=='once'}">1</c:if>
												<c:if test="${card.useType=='more'}">${card.maxCount}</c:if>
											</td>
											<c:if test="${card.useStatus==4 }">
											   <td>该卡已被禁止使用</td>
											</c:if>
											<c:if test="${card.useStatus==3 }">
											         <td>    该卡已失效
												<button type="button" class="btn btn-danger btn-xs delete-role"  onclick="javascript:deleteCard('${card.id}');">删除</button>
											</td>
											</c:if>
											<c:if test="${card.useStatus==2 }">
											<td>
											           该卡已使用过
												<button type="button" class="btn btn-danger btn-xs delete-role"  onclick="javascript:deleteCard('${card.id}');">删除</button>
											 </td>
											</c:if>
											<c:if test="${card.useStatus==1}">
											<td>
											    <button type="button" class="btn btn-danger btn-xs delete-role"  onclick="javascript:cancelledCard('${card.id}');">注销使用</button>
											</td>
											</c:if>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/promotion/cardList"></tags:page>
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
