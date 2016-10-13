<!DOCTYPE html>
<html>
    <head>
        <title>搜索</title>
        <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
         <script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
        <script>
        	function search(){
        		var keywords=$("#keywords").val();
        		if(""==keywords){
        			alert("关键字不能为空");
        			return;
        		}
        		window.location.href="${rc.contextPath}/search/toSkuSearch?keywords="+keywords;
        	}
        	
        	function deleteHistory(){
        		 $.post("${rc.contextPath}/search/clear",{time: new Date().getTime()},function(result){
			         if(result.responseCode == 0){
		         		  $('.search-bottom').hide();
		         		  $('.delete').hide();
		         		  $('.search-top').append('&nbsp;&nbsp;&nbsp;<span style="color:black;">暂无记录</span>');
		         		  openModal('0','清空搜索历史记录成功');
			         }else{
			              openModal('1','清空搜索历史记录失败');
			         }
				});
        	}
        </script>
    </head>
    <body id="">

        <div class="container">

            <div class="head mB10">
                <a href="javascript:history.go(-1)" class="a-left">
                    <span class="back"></span>
                </a>
                <div class="head-input-wrap">
                    <span class="search"></span>
                    <input type="text" id="keywords" placeholder="搜索您想要的产品">
                </div>
                <a href="javascript:search()" class="a-right">
                    <span class="">搜索</span>
                </a>
            </div>

            <div class="search-top">
                <span class="fl">搜索历史</span>
                <#if kwhistory?exists && kwhistory?size&gt;0>
                	<div class="search-bottom">
                		<#list kwhistory as ke>
                			<a href="${rc.contextPath}/search/toSkuSearch?keywords=${ke}">${ke}</a>
                		</#list>
                	</div>
                	<span class="delete fr" onclick="deleteHistory()">清空</span>
                	<#else>&nbsp;&nbsp;&nbsp;<span style="color:black;">暂无记录</span>
                </#if>
            </div>
            <div class="search-bottom">
                
            </div>

        </div><!-- /.container -->
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>
