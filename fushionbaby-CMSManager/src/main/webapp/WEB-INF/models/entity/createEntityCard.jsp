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
        <title>生成实体卡</title>
    </head>
    <script type="text/javascript">
    function  createCard(){
    	var id=$('#card_config_id').val();
    	var num=$("#card_num").val();
    	var storeCode=$("#storeCode").val();
    	if(num==null){
    		num=1;
    	}
  		var submit =  function(v,h,f){
  			if(v=="ok"){
  				window.location.href = "${contextPath}/actEntityCard/actEntityCardAdd/"+id+"/"+num+"/"+storeCode+"/"+new Date().getTime();
  			}
  			return true;
  		}
  		$.jBox.confirm("你确定要生成实体卡吗？", "信息提示",submit);
    }
    </script>

    <body id="" class="Cog">
           <tags:message content="${message }"></tags:message>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-credit-card"></i>生成实体卡</h3>
                        </div>
                        <div class="panel-body">
                        
                            <!-- table -->
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover" id="roleTable">
                                    <thead>
                                        <tr>
											<th width="30%">选择门店</th>
											<th width="20%">实体卡类型</th>
											<th width="30%">实体卡数量</th>
											<th width="20%">操作</th>
										</tr>
                                    </thead>
                                    <tbody>
										<tr>
											<td>
										       <select name="storeCode"  id="storeCode" class="form-control lg-select">
												   <c:forEach items="${storeList}" var="store">
												      <option value="${store.code}" > ${store.name}</option>
												   </c:forEach>
											   </select>
											</td>
											
											<td>
										       <select name="card_config_id"  id="card_config_id" class="form-control lg-select">
												   <c:forEach items="${cardConfigList}" var="cardConfig">
												      <option value="${cardConfig.id}" > ${cardConfig.name}</option>
												   </c:forEach>
											   </select>
											</td>
											
											<td>
											   <input type="number" name="num" value="${num}" min=1 id="card_num"  class="form-control">
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
