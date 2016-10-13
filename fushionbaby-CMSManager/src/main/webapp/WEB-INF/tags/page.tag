<%@ tag language="java" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/bootstrap/js/page.js"></script>
<%@ attribute name="url" type="java.lang.String" required="true" description="请求地址"%>
<%@ attribute name="formId" type="java.lang.String" required="true" description="表单ID"%>
<script type="text/javascript">

$(document).ready(function(){
	pageUtil.init('${url}',$('#${formId}'));

});

</script>
<table width="100%" border="0" bordercolor="#c8c8c8" cellspacing="0" cellpadding="0">
	            <tr>
	     			<td align="right">总计 ${page.total} 条记录 当前第
	     			<input class="input-page" value="${page.currentPage}" size="3" id="page_currentPage" type="text"
	     				onkeyup="this.value=this.value.replace(/\D/g,'')"
	     				onafterpaste="this.value=this.value.replace(/\D/g,'')">
	     			/${page.totalPage} 页,每页显示
	     			<input class="input-page" id="page_limit" type="text" name="page.limit" value="${page.limit}" size="3"
	     				style="width:25px;text-align:center;"
	     				onkeyup="this.value=this.value.replace(/\D/g,'')"
	     				onafterpaste="this.value=this.value.replace(/\D/g,'')" /> 条
		     			<button class="btn-page mL10" onclick="pageUtil.goFirst()">首页</button>
		     			<button class="btn-page" onclick="pageUtil.goPrev()">上一页</button>
		     			<button class="btn-page" onclick="pageUtil.goNext()">下一页</button>
		     			<button class="btn-page" onclick="pageUtil.goLast()">末页</button>
	     			</td>
	            </tr>
</table>


