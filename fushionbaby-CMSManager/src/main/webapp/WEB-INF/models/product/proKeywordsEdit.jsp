<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/ztree.jsp" %><!-- 弹出框引用 -->


<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>商品管理</title>
	 <script type="text/javascript">
	 
	 $(function(){
         var tagsBtn = $('#tagsBtn');
         var tagsInput = $('.tags-input');
         var tagsClose = $('.tags-close');
         
         tagsBtn.click(function(event) {
        	 var inputValue = $.trim(tagsInput.find('input').val());
             /*验证关键字格式*/
             var regex=/^([\u4e00-\u9fa5A-Za-z0-9]+\s?)*[\u4e00-\u9fa5A-Za-z0-9]$/; //特殊字符校验
             if(!regex.test(inputValue)){
             	alert("关键字只能输入中文、英文或数字");
             	return;
             }
             
             if (inputValue!=="") {
                 var tag = '<div class="tag"><input name="keyWords" type="text" style="border:none;width:80px;height:28px;" readonly="readonly" value="' + inputValue + '" /><a class="tags-close" href="javascript:void(0)"><i class="fa fa-times"></i></a></div>';
                 /*添加标签*/
                 tagsInput.before(tag);
                 /*清空 value*/
                 tagsInput.find('input').val("");

                 /*绑定删除事件*/
                 $('.tags-close').bind('click', function(event) {
                     $(this).parents('.tag').remove();
                     removeTagsRemoveBtn();
                 });
                 removeTagsRemoveBtn();
             };
         });
         
         
         /*删除按钮*/
         removeTagsRemoveBtn();
         $('.tags-close').bind('click', function(event) {
             $(this).parents('.tag').remove();
             removeTagsRemoveBtn();
         });

         /*删除全部*/
         $('#tagsRemove').click(function(event) {
             $('.tag').remove();
         });

         function removeTagsRemoveBtn(){
             if ($('.tag').length==0) {
                 $('#tagsRemove').hide();
             } else {
                 $('#tagsRemove').show();
             }
         }
         
	 });
	 </script>
	 
</head>
 <body id="index"  style="background:#fff">
   
        <div class="container-fluid" >
            <div class="row">

			  <div class="col-md-10 content" >
			  
				      <form class="form-horizontal" method="post"  id="skuKeywordsForm">
				 		<input type="hidden" name="id" value="${word.id }" />
				 		<input type="hidden" name="productCode" value="${word.productCode}" />
						<div class="form-group">
							<label class="col-sm-2 control-label">关键字：</label>
							<div class="col-sm-2">
						 		<div class="tags-wrap">
		                      	   <c:if test="${!empty word.keyWords}">
			                      	   <c:forEach items="${word.keyWords}" var="kw" varStatus="status">
			                          			<div class="tag">
			                          				<input name="keyWords" type="text" style="border:none;width:80px;height:28px;" readonly="readonly" value="${kw}" />
			                          				<a class="tags-close" href="javascript:void(0)"><i class="fa fa-times"></i></a>
			                          			</div>
		                          	  </c:forEach>
	                          	  </c:if>
		                          <div class="input-group tags-input">
		                          	  
		                              <input type="text" class="form-control" maxlength="5">
		                              <div id="tagsBtn" class="input-group-addon">添加</div>
		                          </div>
		
		                          <a id="tagsRemove" class="btn btn-warning" href="javascript:void(0)">删除全部</a>
		                      </div>
						 		
							</div>
						</div>
					</form>
       		</div>
       	  </div>
       	</div>
   </body>
</html>