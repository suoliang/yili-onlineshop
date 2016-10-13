<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>功能栏列表</title>
  	<script type="text/javascript">

  	function edit(code){
  	
  		
  		$.jBox("iframe:${contextPath}/sysmgr/functionalBlock/editPage?code="+code, { 
  		    title: "编辑全局设置值", width: 800,height: 700,
  		    buttons: { '关闭': false },
  		  	submit: function (v, h, f) {
              if (v == false) {
            	  return ;
              }
//               var contents = h.find("iframe").contents();
//               var url = contents.find("#handleForm").attr("action");
//               var id = contents.find("input[name='id']").val();
//               var code = contents.find("input[name='code']").val();
//               var value = contents.find("input[name='name']").val();
//               var value = contents.find("input[name='linkUrl']").val();
//               $.post(url, {id:id,code:code,value:value,datatime:new Date().getTime()},function(data){
//             	 if(data==true){
//             		 jBox.tip("修改成功");
//             	 }else{
//             	 	jBox.tip("修改失败"); 
//             	 }
//             	 window.location.href = "${contextPath}/globalConfig/globalConfigList";
//               });

  		  	}
		});
  	}
  	</script>
	
</head>
<body id="index">
		<tags:message content="${message }"></tags:message>
		 <div class="col-md-4">
                <a href="javascript:edit('')" class="btn btn-info" >添加</a>
		</div>
	
        <div class="container-fluid">
            <div class="row">
			  <div class="col-md-12 content">
				<div class="panel panel-info">
                   <div class="panel-heading">
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 功能栏列表</h3>
                   </div>
                   <div class="panel-body">
                      <!-- table -->
                      <div class="table-responsive">
                        <table class="table table-bordered table-hover" id="roleTable">
                           <thead>
                             <tr>
                              		<th>序号</th>
                              		<th>编号</th>
									<th>名称</th>
									<th>图标</th>
									<th>链接地址</th>
									<th>使用范围</th>
									<th>排序</th>
									<th>开始时间</th>
									<th>结束时间</th>
									<th>是否显示</th>
									<th>创建时间</th>
									<th>操作</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${results}" var="result" varStatus="status">
								<tr>
								  	<td>${status.count }</td>

									<td>${result.code}</td>
									<td>${result.name}</td>
									<td><img alt="" src="${imagePath }${result.icon}" width="50" height="50" /></td>
									<td>${result.linkUrl}</td>
									<td>${dic:getDictLabel(result.useType, 'BLOCK_USE_TYPE', '-')}</td>
									<td>${result.sortOrder}</td>
									<td><fmt:formatDate value="${result.startTime}" pattern="yyyy-MM-dd"/></td>
									<td><fmt:formatDate value="${result.endTime}" pattern="yyyy-MM-dd"/></td>
									<td>${dic:getDictLabel(result.disable, 'DISABLE', '-')}</td>
									<td><fmt:formatDate value="${result.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
										
									<td class="hidden-480" >
										<button type="button" class="btn btn-success btn-xs delete-role" onclick="edit('${result.code}')">编辑</button>
									</td>
								</tr>
							  </c:forEach>
                            </tbody>
                         </table>
                      </div>
                            
                            
                        </div>
					</div>
                </div>
                <!-- /.content -->
            </div>
		</div>
       
        <!-- /.container-fluid -->
</body>
</html>
