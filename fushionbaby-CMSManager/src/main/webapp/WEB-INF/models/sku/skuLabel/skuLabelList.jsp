<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>商品标签管理</title>
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
		
	    function showEditSkuLabel(labelCode){
	  		var url = "iframe:${contextPath}/skuLabel/editSkuLabel?labelCode=" + labelCode;
			$.jBox(url, {
	  		    title: "修改商品标签页面", width: 800,height: 350,
	  		    buttons: { '关闭': false }
			});
	  	}
	    
	    function showAddSkuLabel(){
	  		var url = "iframe:${contextPath}/skuLabel/addSkuLabel";
			$.jBox(url, {
	  		    title: "新增商品标签页面", width: 800,height: 400,
	  		    buttons: { '关闭': false }
			});
	  	}
	    
	    function delSkuLabel(labelCode){
    		var submit =  function(v,h,f){
 	  			if(v== "ok"){
 	  				$.post("${contextPath }/skuLabel/deleteSkuLabel",{labelCode:labelCode },function(result){
 	  	                if(result == "SUCCESS"){
 	  	                	jBox.tip("删除标签成功", 'info');
 	  			  				
 	  	  		  			// 3秒后完成操作
 	  	  		  			window.setTimeout(function () {  location.reload(); }, 1500);
 	  	                }else{
 	  	                	jBox.tip("删除标签失败", 'info');
 	  	                }
 	  	                
 	  	              });
 	  			}
 	  			return true;
 	  		}
    	 
    	 $.jBox.confirm("是否删除当前标签？", "删除提示",submit);
	    }
	   
	    /** 添加标签分类关联 */
	    function addSkuLabelCategory(labelCode){
	    	var url = "iframe:${contextPath}/skuCategory/selectCheckCategoryList?labelCode="+labelCode;
			$.jBox(url, {
	  		    title: "关联分类", width: 800,height: 600,
	  		    buttons: { '关闭': false },
			});
	    }
	    
	    function importLabelSku(){
	  		var url = "iframe:${contextPath}/skuLabel/batchSkuLabel";
			$.jBox(url, {
	  		    title: "EXCEL批量导入标签商品", width: 500,height: 300,
	  		    buttons: { '关闭': false },
	  		  	submit: function (v, h, f) {
	              if (v == 0) {
	            	  return ;
	              }
	              
	  		  	}
			});
	  	}
	    
	   
    </script>    
    </head>
    <body id="index" class="Cog">

        <div class="container-fluid">
            	<div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-cog"></i>商品标签列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                <div class="form-group col-md-4 mB15">
                                    <label for="a">标签编码：</label>
                                    <input type="text" class="form-control" id="code" name="code" value="${skuLabelDto.code}" placeholder="请输入标签编码">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="c">标签名：</label>
                                    <input type="text" class="form-control" id="name" name="name" value="${skuLabelDto.name}" placeholder="请输入标签名">
                                </div>
                                
                                 <div class="form-group col-md-4 mB15">
                                    <label for="c">标签类型：</label>
                                    <input type="text" class="form-control" id="type" name="type" value="${skuLabelDto.type}" placeholder="请输入标签类型">
                                </div>
                                
                                 <div class="form-group col-md-4 mB15">
                                    <label for="c">标签状态：</label>
                                    <select id="disabled" name="disabled" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
                                        <option value="">所有</option>
                                    	<option value="y" <c:if test="${skuLabelDto.disabled == 'y'}">selected="selected"</c:if> >启用</option>
                                    	<option value="n" <c:if test="${skuLabelDto.disabled == 'n'}">selected="selected"</c:if> >禁用</option>
                                    </select>
                                </div>
                                <button type="submit" class="btn btn-info speBtn">查询</button>
                                <button type="button" class="btn btn-primary speBtn" onclick="showAddSkuLabel()">新增</button>
                                <button type="button" class="btn btn-warning" onclick="importLabelSku()">批量导入标签商品</button>
                                <button type="button" class="btn btn-success" onclick="downLoadExcel('批量导入标签商品模板\.xls')">下载标签商品模板</button>
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
                                            <th>标签编码</th>
                                            <th>标签名</th>
                                            <th>类型</th>
                                            <th>添加分类数量</th>
                                            
                                            <th>标签状态</th>
                                            <th>创建时间</th>
                                            <th>备注</th>
                                            
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${page.result}" var="skuLabel" varStatus="status">
                                    	<tr>
                                            <td scope="row">${status.count}</td>
                                            <td>${skuLabel.code}</td>
                                            <td>${skuLabel.name}</td>
                                            <td>${skuLabel.type}</td>
                                            <td>${skuLabel.maxCategoryNum }
                                            <td>
                                            	<c:choose>
                                            		<c:when test="${skuLabel.disable == 'y'}">启用</c:when>
                                            		<c:otherwise>禁用</c:otherwise>
                                            	</c:choose>
                                            </td>
                                            <td><fmt:formatDate value="${skuLabel.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                            <td>${skuLabel.memo}</td>
                                            <td>
                                                <a href="javascript:location='${contextPath}/skuLabel/skuListByLabel?labelCode=${skuLabel.code}'" class="btn btn-success btn-xs edit-role">关联商品</a>
                                                <button type="button" class="btn btn-default btn-xs edit-role" onclick="showEditSkuLabel('${skuLabel.code}')">修改</button>
                                                <button type="button" class="btn btn-danger btn-xs delete-role" onclick="delSkuLabel('${skuLabel.code}')">删除</button>
                                                <button type="button" class="btn btn-success btn-xs edit-role" onclick="addSkuLabelCategory('${skuLabel.code}')">关联分类</button>
                                                 <a href="javascript:location='${contextPath}/skuLabel/image/skuLabelImageList?labelCode=${skuLabel.code}'" class="btn btn-success btn-xs edit-role">图片</a>
                                                <input type="hidden" id="addRemark" value="${ADD_REMARK}">
									 			<input type="hidden" id="modifyRemark" value="${MODIFY_REMARK}">
                                            </td>
                                        </tr>
                                    	</c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                           <tags:page formId="findForm" url="${contextPath}/skuLabel/skuLabelList"></tags:page>
                            <!-- 分页 end -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

