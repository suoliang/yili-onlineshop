<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>订单清单打印</title>
        <script src="${pageContext.request.contextPath}/static/bootstrap/js/print/jquery.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/static/bootstrap/js/print/jquery.printFinal.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(function() {
				function print(preview) {
					$("#content").printFinal({
						preview : preview,//打印预览
						impcss : true
					//引入css文件						 
					});
	
				}
	
				$("#printbtn").click(function() {//打印按钮
					print(false);
				});
	
				$("#previewbtn").click(function() {//预览按钮
					print(true);
				});
	
			});
		</script>
		<style>
			.oh {overflow:hidden;}
			.fl {float:left;}
		</style>
    </head>
    <body>
	   	<input type='button' name='previewbtn' id='previewbtn' value='预览' />
		<input type='button' name='printbtn' id='printbtn' value='打印' />
        <div class="container" id='content'>
            <div class="row">
                <div class="col-md-8 content col-md-offset-2 mT0 pT0" id="orderPrint">
                    <div class="page-header pB0 mT0">
                        <h3 class="textC">订单清单</h3>
                    </div>
                    <div class="page-header pB0 mT0">
                        <h4><i class="fa fa-shopping-cart"></i> 基本信息</h4>
                    </div>
                    <ul class="list-group">
                        <li class="list-group-item">
                            <strong>收&ensp;货&ensp;人：</strong>
                            <span>${orderMemberAddress.receiver}</span>
                        </li>
                        <li class="list-group-item">
                            <strong>订单编号：</strong>
                            <span>${orderBase.orderCode}</span>
                        </li>
                        <li class="list-group-item">
                            <strong>送货时间：</strong>
                            <span>${orderTrans.sendDate}</span>
                        </li>
                        <li class="list-group-item">
                            <strong>联系电话：</strong>
                            <span>${orderMemberAddress.receiverMobile}</span>
                        </li>
                        <li class="list-group-item">
                            <strong>下单时间：</strong>
                            <span><fmt:formatDate value="${orderBase.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
                        </li>
                        <li class="list-group-item">
                            <strong>邮政编码：</strong>
                            <span>${orderMemberAddress.zipcode}</span>
                        </li>
                        <li class="list-group-item width100">
                            <strong>收货地址：</strong>
                            <span>${province}${city}${district}${orderMemberAddress.address}</span>
                        </li>
                        <li class="list-group-item width100">
                            <strong>用户备注：</strong>
                            <span>${orderBase.memo}</span>
                        </li>
                    </ul>
                    <div class="clearfix"></div>
                    <div class="page-header pB0 mT0">
                        <h4><i class="fa fa-list"></i> 商品列表</h4>
                    </div>
                    <!-- table -->
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover" id="">
                            <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>商品条形码</th>
                                    <th>商品名称</th>
                                    <th>单价</th>
                                    <th>数量</th>
                                    <th>小计</th>
                                </tr>
                            </thead>
                            <tbody>
                               <input id="totalLine" type="hidden" value="${orderLineList.size()}">
                         	   <c:forEach items="${orderLineDtoList}" var="orderLine" varStatus="status">
	                         	   	<tr>
	                                    <td scope="row">${status.count}</td>
	                                    <td>${orderLine.barCode}</td>
	                                    <td>${orderLine.skuName}</td>
	                                    <td>${orderLine.unitPrice}</td>
		                                <td id="quantity${status.count}">${orderLine.quantity}</td>
	                                    <td>${orderLine.totalPrice}</td>
	                                </tr>
                         	   </c:forEach>
                            </tbody>
                            <tr>
                            	<td colspan="6" align="right">
                            		合计数量：${totalQuantity }件&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总价：${totalPrice }
                            	</td>
                            </tr>
                        </table>
                    </div>
                    <ul class="list-group">
                        <li class="list-group-item">
                            <strong>优&ensp;惠&ensp;券：</strong>
                            <span>${orderFee.cardAmount}</span>
                        </li>
                        <li class="list-group-item">
                            <strong>积分折扣：</strong>
                            <span>${orderFee.epointsRedeemMoney}</span>
                        </li>
                        <li class="list-group-item">
                            <strong>红包：</strong>
                            <span>${redEnvlopeUseAmount}</span>
                        </li>
                        <li class="list-group-item">
                            <strong>运费：</strong>
                            <span>${orderFee.actualTransferFee}</span>
                        </li>
                        <li class="list-group-item">
                            <strong></strong>
                            <span></span>
                        </li>
                        <li class="list-group-item">
                            <strong>订单总计：</strong>
                            <span>${orderFee.totalActual}</span>
                        </li>
                    </ul>
                    <ul class="list-group fl oh" style="margin-bottom:50px;">
                        <li class="list-group-item">
                          	  <h4>感谢您选择阿拉丁玛特！</h4>
                          	  <h5>温馨提示：</h5>
                           		 如发现商品破损或与描述不符等任何问题，请及时联系我们售后热线4000-021-060。
                           		 如配送人员没有送货上门或态度恶劣，您可通过我们售后热线进行投诉。
                           		 欢迎对我们的工作进行监督，我们将不断改进，直至您满意！祝您生活愉快！
                        </li>
                        <li class="list-group-item">
                        	<img alt="" src="${pageContext.request.contextPath}/media/image/code.jpg" width="150px">
                           	<br> &nbsp;&nbsp;&nbsp;&nbsp;扫一扫，下载APP
                        </li>
                        
                    </ul>
                </div>
                <!-- /.content -->
            </div>
        </div>
        <!-- /.container -->
        <!-- javascript -->
     
        <script type="text/javascript">
        	
        </script>
    </body>
</html>
