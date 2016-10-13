<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>标签分类管理</title>
    <script type="text/javascript">
    	/** 显示标签分类关联的修改页 */
	    function showEditSkuLabelCategory(labelCategoryId){
	  		var url = "iframe:${contextPath}/skuLabel/gotoEditLabelCate?labelCategoryId=" + labelCategoryId;
			$.jBox(url, {
	  		    title: "标签分类关联修改页面", width: 800,height: 300,
	  		    buttons: { '关闭': false }
			});
	  	}
    	
	    /** 修改启用禁用状态 */
	    function changeDisable(msg,labelCategoryId,isDisable){
    		var submit =  function(v,h,f){
      			if(v=="ok"){
      				$.ajax({
      					type : "get",
      					url : "${contextPath}/skuLabel/changeIsDisable",
      					dataType:"text",
      					data : {
      						id: labelCategoryId, 
      						disable: isDisable
      					},
      					success : function(data) {
      						var result = data;
      						if(result == "SUCCESS"){
      		  					window.setTimeout(function () {  location.reload(); }, 500);
      		  					jBox.tip("操作成功", 'info');
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
    		$.jBox.confirm(msg, "操作提示",submit);
	    }
	    
	    function delSkuLabelCate(id){
	    	var submit =  function(v,h,f){
      			if(v=="ok"){
      				$.ajax({
      					type : "post",
      					url : "${contextPath}/skuLabel/deleteLabelCategoryById",
      					dataType:"text",
      					data : {
      						id: id
      					},
      					success : function(data) {
      						var result = data;
      						if(result == "SUCCESS"){
      		  					window.setTimeout(function () {  location.reload(); }, 500);
      		  					jBox.tip("操作成功", 'info');
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
    		$.jBox.confirm("你确定要删除吗?", "操作提示",submit);
	    }
    </script>    
    </head>
    <body id="index" class="Cog">
    	<tags:message content="${message }"></tags:message>
        <div class="container-fluid">
            	<div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-cog"></i>标签分类列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                <div class="form-group col-md-4 mB15">
                                    <label for="a">标签编码：</label>
                                    <input type="text" class="form-control" id="labelCode" name="labelCode" value="${labelCategoryDto.labelCode}" placeholder="请输入标签编码">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="c">分类编码：</label>
                                    <input type="text" class="form-control" id="categoryCode" name="categoryCode" value="${labelCategoryDto.categoryCode}" placeholder="请输入分类编码">
                                </div>
                                 <div class="form-group col-md-4 mB15">
                                    <label for="c">标签分类状态：</label>
                                    <select id="disable" name="disable" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
                                        <option value="">所有</option>
                                    	<option value="y" <c:if test="${labelCategoryDto.disable == 'y'}">selected="selected"</c:if> >启用</option>
                                    	<option value="n" <c:if test="${labelCategoryDto.disable == 'n'}">selected="selected"</c:if> >禁用</option>
                                    </select>
                                    <button type="submit" class="btn btn-success speBtn">查询</button>
                                </div>
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
                                        	<th>
                                                <label for="checkAllBtn" class="check-label">
                                                    <input type="checkbox" name="checkAllBtn" id="checkAllBtn">
                                                </label>
                                            </th>
                                            <th>序号</th>
                                            <th>标签编码</th>
                                            <th>标签名称</th>
                                            <th>分类编码</th>
                                            <th>分类名称</th>
                                            <th>显示顺序</th>
                                            <th>创建时间</th>
                                            <th>是否禁用</th>
                                            <th>备注</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${page.result}" var="labelCategory" varStatus="status">
                                    	<tr>
                                    		<th>
                                                <label class="check-label">
                                                    <input type="checkbox" name="checkAll">
                                                </label>
                                            </th>
                                           	<td scope="row">${status.count}</td>
                                            <td>${labelCategory.labelCode}</td>
                                            <td>${labelCategory.labelName}</td>
                                            <td>${labelCategory.categoryCode}</td>
                                            <td>${labelCategory.categoryName}</td>
                                            <td>${labelCategory.sortOrder}</td>
                                            <td><fmt:formatDate value="${labelCategory.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                            <td>
                                            	<c:choose>
                                            		<c:when test="${labelCategory.disable == 'n'}">启用</c:when>
                                            		<c:otherwise>禁用</c:otherwise>
                                            	</c:choose>
                                            </td>
                                            <td>${labelCategory.memo}</td>
                                            <td>
                                            	<button type="button" class="btn btn-default btn-xs edit-role" onclick="showEditSkuLabelCategory('${labelCategory.id}')">修改</button>
                                            	<c:choose>
                                            		<c:when test="${labelCategory.disable =='y'}">
                                            			<button type="button"  class="btn btn-success btn-xs delete-role" onclick="changeDisable('确定要启用吗?','${labelCategory.id}','n')">启用</button>
                                            		</c:when> 
												  	<c:otherwise>
												  		<button type="button" class="btn btn-danger btn-xs delete-role"  onclick="changeDisable('确定要禁用吗?','${labelCategory.id}','y')">禁用</button>
												  	</c:otherwise> 
                                             	</c:choose>
                                             	<button type="button" class="btn btn-danger btn-xs delete-role" onclick="delSkuLabelCate('${labelCategory.id}')">删除</button>
                                            </td>
                                        </tr>
                                    	</c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                           <tags:page formId="findForm" url="${contextPath}/skuLabel/labelCategoryList"></tags:page>
                            <!-- 分页 end -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

