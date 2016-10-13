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
        <title>生成优惠券</title>
    </head>
    <script type="text/javascript">
    function  createCard(){
    	var typeId=$('#card_type_id').val();
    	var num=$("#card_num").val();
    	var isUse=$("#is_use_password").val();
    	if(num==null){
    		num=1;
    	}
  		var submit =  function(v,h,f){
  			if(v=="ok"){
  				window.location.href = "${contextPath}/promotion/addCard/"+typeId+"/"+num+"/"+isUse+"/"+new Date().getTime();
  			}
  			return true;
  		}
  		$.jBox.confirm("你确定要生成优惠券吗？", "信息提示",submit);
    }
    </script>

    <body id="" class="Cog">
           <tags:message content="${message }"></tags:message>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-credit-card"></i>生成优惠券</h3>
                        </div>
                        <div class="panel-body">
                        
                            <!-- table -->
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover" id="roleTable">
                                    <thead>
                                        <tr>
											<th width="60%">优惠券类型</th>
											<th>优惠券数量</th>
											<th>是否需要密码</th>
											<th>操作</th>
										</tr>
                                    </thead>
                                    <tbody>
										<tr>
											<td>
										       <select name="card_type_id"  id="card_type_id" class="form-control lg-select">
												   <c:forEach items="${cardTypeList}" var="cardType">
												      <option value="${cardType.id}" > ${cardType.name}</option>
												   </c:forEach>
											   </select>
											</td>
											
											<td>
											   <input type="number" name="num" value="${num}" min=1 id="card_num">
											</td>
											
											<td>
											<select name="is_use_password" id="is_use_password">
											   <option value="y">需要密码</option>
											   <option value="n">不需要密码</option>
											</select>
											</td>
											
                                            <td>
												<button type="button" class="btn btn-info btn-xs delete-role"  onclick="javascript:createCard();">确认生成</button>
                                            </td>
                                            
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
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
