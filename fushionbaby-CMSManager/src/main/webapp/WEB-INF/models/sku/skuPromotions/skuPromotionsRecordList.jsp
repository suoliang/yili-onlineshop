<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 活动库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>活动商品交易记录列表</title>
    <script type="text/javascript">
		
	    function delSkuPromotionsRecord(id){
    		var submit =  function(v,h,f){
 	  			if(v== "ok"){
 	  				$.post("${contextPath }/skuPromotions/deleteSkuPromotionsRecord",{id:id },function(result){
 	  	                if(result == "SUCCESS"){
 	  	                	jBox.tip("删除活动交易记录成功", 'info');
 	  			  				
 	  	  		  			// 3秒后完成操作
 	  	  		  			window.setTimeout(function () {  location.reload(); }, 1500);
 	  	                }else{
 	  	                	jBox.tip("删除活动交易记录失败", 'info');
 	  	                }
 	  	                
 	  	              });
 	  			}
 	  			return true;
 	  		}
    	 
    	 $.jBox.confirm("是否删除当前活动交易记录？", "删除提示",submit);
	    }
    </script>    
    </head>
    <body id="index" class="Cog">

        <div class="container-fluid">
            	<div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-cog"></i>活动商品交易记录列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                <div class="form-group col-md-4 mB15">
                                    <label for="c" class="col-label">订单号：</label>
                                    <input type="text" class="form-control" id="orderCode" name="orderCode" value="${orderCode}" placeholder="请输入订单号">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="c" class="col-label">会员名：</label>
                                    <input type="text" class="form-control" id="loginName" name="loginName" value="${loginName}" placeholder="请输入会员名">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="c" class="col-label">活动编码：</label>
                                    <input type="text" class="form-control" id="pmCode" name="pmCode" value="${pmCode}" placeholder="请输入活动编码">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="c" class="col-label">商品名称：</label>
                                    <input type="text" class="form-control" id="skuName" name="skuName" value="${skuName}" placeholder="请输入商品名称">
                                </div>
                                
                                <div class="form-group col-md-4 mB15">
					    	
			    					<label class="col-label">创建时间开始：</label>
				    					<div class="input-group">
											<input class="timeS form-control form_datetime" readonly name="createTimeFrom"
											 	 type="text" value="${createTimeFrom}">
											<div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
										</div>
									</div>
									
									<div class="form-group col-md-4 mB15">
										<label class="col-label">创建时间结束：</label>
										<div class="input-group">
											<input class="timeE form-control form_datetime"  readonly name="createTimeTo" 
												 type="text" value="${createTimeTo}">
											<div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
				    				    </div>
				    			</div>
                                
                                
                                <button type="submit" class="btn btn-success speBtn">查询</button>
                               
                                <div class="clearfix"></div>
                                
                                <input type="hidden" name="currentPage" value="${page.currentPage}"/>
								<input type="hidden" name="totalPage" value="${page.totalPage}" disabled="disabled"/>  
								<input type="hidden" name="limit" value="${page.limit}"  size="3"/>
								<input type="hidden" name="total" value="${page.total}"/>
                            </form>
                            <!-- table -->
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover" id="">
                                    <thead>
                                        <tr>
                                            <th>序号</th>
                                            <th>订单号</th>
                                            <th>活动编码</th>
                                            <th>活动名</th>
                                            <th>商品名称</th>
                                            <th>会员名</th>
                                            <th>创建时间</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${page.result}" var="skuPromotionsRecord" varStatus="status">
                                    	<tr>
                                            <td scope="row">${status.count}</td>
                                            <td>${skuPromotionsRecord.orderCode}</td>
                                            <td>${skuPromotionsRecord.pmCode}</td>
                                            <td>${skuPromotionsRecord.promotionsName}</td>
                                            <td>${skuPromotionsRecord.skuName}</td>
                                            <td>${skuPromotionsRecord.loginName}</td>
                                            <td><fmt:formatDate value="${skuPromotionsRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                            <td>
                                                <a class="btn btn-info btn-xs" href="${contextPath}/order/orderBaseDetails/${skuPromotionsRecord.memberId}/${skuPromotionsRecord.orderCode}" title="订单详情">	
														订单详情
												</a>
                                            	<button type="button" class="btn btn-default btn-xs edit-role" onclick="delSkuPromotionsRecord('${skuPromotionsRecord.id}')">删除</button>
                                            </td>
                                        </tr>
                                    	</c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                           <tags:page formId="findForm" url="${contextPath}/skuPromotions/logPromotionsRecordList"></tags:page>
                            <!-- 分页 end -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

