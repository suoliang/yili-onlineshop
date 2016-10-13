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
        <title>优惠券类型列表</title>
    </head>
    <script type="text/javascript">
	    function changeDisable(id,disable){
	  		var submit =  function(v,h,f){
	  			if(v=="ok"){
	  				window.location.href = "${contextPath}/promotion/updateCardTypeDisable/"+id+"/"+disable+"/"+new Date().getTime();
	  			}
	  			return true;
	  		  }
	  		$.jBox.confirm("你确定要修改该优惠券状态吗？", "信息提示",submit);
	    };
	    
    </script>
    <body id="" class="Cog">
           <tags:message content="${message}"></tags:message>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-credit-card"></i>优惠券类型列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">

                                <div class="form-group col-md-3 mB15">
                                    <label for="a" class="col-label">优惠券名称：</label>
                                    <input type="text" class="form-control" id="name" name="cardTypeName" value="${cardTypeDto.cardTypeName}" placeholder="请输入优惠券卡号">
                                </div>
                                  <div class="form-group col-md-3 mB15">
	                                    <label for="a" class="col-label">时间类型：</label>
		                                <select  name="timeType" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
											<option value="1" ${cardTypeDto.timeType == 1 ?'selected':''}>起效时间范围</option>
											<option value="2" ${cardTypeDto.timeType == 2 ?'selected':''}>失效时间范围</option>
									    </select>
                                    </div>
                                
                                  <div class="form-group col-md-3 mB15">
								        <label class="col-label">开始时间：</label>
								        <div class="input-group">
											<input class="timeS form-control form_datetime" readonly name="useTimeFrom" type="text" value="${cardTypeDto.useTimeFrom}">
									        <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
								        </div>
								    </div>
                                    <div class="form-group col-md-3 mB15">
								        <label class="col-label">结束时间：</label>
								        <div class="input-group">
											<input class="timeE form-control form_datetime" readonly name="useTimeTo" type="text" value="${cardTypeDto.useTimeTo}">
									        <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
								        </div>
								   </div>
								   
								    <div class="form-group col-md-3 mB15">
	                                    <label for="a" class="col-label">优惠券类型：</label>
		                                <select  name="cardType" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
											<option value="">所有</option>
											<option value="1" ${cardTypeDto.cardType == 1 ?'selected':''}>通用</option>
											<option value="2" ${cardTypeDto.cardType == 2 ?'selected':''}>品牌列表</option>
											<option value="3" ${cardTypeDto.cardType == 3 ?'selected':''}>分类列表</option>
											<option value="4" ${cardTypeDto.cardType == 4 ?'selected':''}>某些商品</option>
											<option value="5" ${cardTypeDto.cardType == 5 ?'selected':''}>商品标签</option>
									    </select>
                                    </div>
								   
                                <div class="form-group col-md-4 mB15">
                                   <button type="submit" class="btn btn-success speBtn">查 询</button>
                                   <a href="${contextPath}/promotion/toAdd" class="btn btn-info speBtn">新增</a>
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
											<th>优惠券名称</th>
											<th>优惠券起效日期</th>
											<th>优惠券失效日期</th>
											<th>优惠券优惠结果</th>
											<th>优惠券类型</th>
											<th>作用范围</th>
											<th>优惠券可使用次数</th>
											<th>最低消费（元）</th>
											<th>是否可用</th>
                                        </tr>
                                      </thead>
                                    <tbody>
                                  	<c:forEach items="${cardTypeList}" var="cardType" varStatus="status">
											<tr>
												<td>${status.count}</td>
												<td>${cardType.name}</td>
												<td><fmt:formatDate value="${cardType.beginTime}"	pattern="yyyy-MM-dd HH:mm:ss" /></td>
												<td><fmt:formatDate value="${cardType.endTime}"		pattern="yyyy-MM-dd HH:mm:ss" /></td>
												<td>${cardType.discountMoney}</td>
												<td>
													<c:if test="${cardType.cardType=='1'}">通用</c:if>
													<c:if test="${cardType.cardType=='2'}">品牌列表</c:if>
													<c:if test="${cardType.cardType=='3'}">分类列表</c:if>
													<c:if test="${cardType.cardType=='4'}">某些商品</c:if>
													<c:if test="${cardType.cardType=='5'}">商品标签</c:if>
												</td>
												<td>${cardType.idList}</td>
												<td>
													<c:if test="${cardType.useType=='nolimit'}">无限次使用</c:if>
													<c:if test="${cardType.useType== 'once'}">一次使用</c:if>
													<c:if test="${cardType.useType=='more'}">${cardType.useCountLimit}次使用</c:if>
												</td>
												<td>${cardType.conditionSkuPriceAbove}</td>
												<td>
													<c:if test="${cardType.disable=='n' }">
													  <a href="#" onclick="changeDisable(${cardType.id},'y');"  class="btn btn-info speBtn">  可用  </a>
													</c:if>
													<c:if test="${cardType.disable=='y' }">
													  <a href="#" onclick="changeDisable(${cardType.id},'n');"  class="btn btn-info speBtn">  不可用   </a>
													</c:if>
												</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/promotion/cardTypeList"></tags:page>
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
