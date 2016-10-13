<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 活动库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>商品活动管理</title>
    <script type="text/javascript">
		$(document).ready(function() {
	  		var isShowAddSuc = $("#addRemark").val();
	  		var isShowModifySuc = $("#modifyRemark").val();
	  		if(isShowAddSuc == '0'){
	  			jBox.tip("添加成功", 'info');
	  		}
	  		
	  		if(isShowModifySuc == '0'){
	  			jBox.tip("修改成功", 'info');
	  		}
	  	});
		
	    function showEditSkuPromotions(pmCode){
	  		var url = "iframe:${contextPath}/skuPromotions/editSkuPromotions?pmCode=" + pmCode;
			$.jBox(url, {
	  		    title: "修改商品活动页面", width: 800,height: 550,
	  		    buttons: { '关闭': false }
			});
	  	}
	    
	    function showAddPromotions(){
	  		var url = "iframe:${contextPath}/skuPromotions/toAddSkuPromotions";
			$.jBox(url, {
	  		    title: "新增商品活动页面", width: 800,height: 550,
	  		    buttons: { '关闭': false }
			});
	  	}
	    function delSkuPromotions(promotionsCode){
    		var submit =  function(v,h,f){
 	  			if(v== "ok"){
 	  				$.post("${contextPath }/skuPromotions/deleteSkuPromotions",{promotionsCode:promotionsCode },function(result){
 	  	                if(result == "SUCCESS"){
 	  	                	jBox.tip("删除活动成功", 'info');
 	  			  				
 	  	  		  			// 3秒后完成操作
 	  	  		  			window.setTimeout(function () {  location.reload(); }, 1500);
 	  	                }else{
 	  	                	jBox.tip("删除活动失败", 'info');
 	  	                }
 	  	                
 	  	              });
 	  			}
 	  			return true;
 	  		}
    	 
    	 $.jBox.confirm("是否删除当前活动？", "删除提示",submit);
	    }
    </script>    
    </head>
    <body id="index" class="Cog">

        <div class="container-fluid">
            	<div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-cog"></i>商品活动列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                <div class="form-group col-md-4 mB15">
                                    <label for="a">活动编码：</label>
                                    <input type="text" class="form-control" id="promotionsCode" name="promotionsCode" value="${skuPromotionsInfoDto.promotionsCode}" placeholder="请输入活动编码">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="c">活动名：</label>
                                    <input type="text" class="form-control" id="promotionsName" name="promotionsName" value="${skuPromotionsInfoDto.promotionsName}" placeholder="请输入活动名">
                                </div>
                                
                                <button type="submit" class="btn btn-success speBtn">查询</button>
                                <button type="button" class="btn btn-success speBtn" onclick="showAddPromotions()">新增</button>
                               
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
                                            <th>活动编码</th>
                                            <th>活动名</th>
                                            <th>创建时间</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${page.result}" var="skuPromotions" varStatus="status">
                                    	<tr>
                                           <td scope="row">${status.count}</td>
                                            <td>${skuPromotions.promotionsCode}</td>
                                            <td>${skuPromotions.promotionsName}</td>
                                            <td><fmt:formatDate value="${skuPromotions.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                            <td>
                                                <a href="javascript:location='${contextPath}/skuPromotions/skuListByPromotions?promotionsCode=${skuPromotions.promotionsCode}'" class="btn btn-success btn-xs edit-role">关联商品</a>
                                                <button type="button" class="btn btn-default btn-xs edit-role" onclick="showEditSkuPromotions('${skuPromotions.promotionsCode}')">修改</button>
                                                <button type="button" class="btn btn-danger btn-xs delete-role" onclick="delSkuPromotions('${skuPromotions.promotionsCode}')">删除</button>
                                                <input type="hidden" id="addRemark" value="${ADD_REMARK}">
									 			<input type="hidden" id="modifyRemark" value="${MODIFY_REMARK}">
                                            </td>
                                        </tr>
                                    	</c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                           <tags:page formId="findForm" url="${contextPath}/skuPromotions/skuPromotionsList"></tags:page>
                            <!-- 分页 end -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

