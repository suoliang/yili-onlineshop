<!DOCTYPE html>
<html>
    <head>
        <title>设置</title>
        <!-- 通用部分 开始 -->
        <#include "/common/common.ftl" />
         <script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
        <!-- 通用部分 结束 -->
		 <script>
        
            function logout(){
            	var url="${rc.contextPath}/memberCenter/logout?time="+new Date().getTime();
            	$.ajax({
			        type: "POST",
			        async:false,
			        dataType: "json",
			        url: url,
			        success: function (data) {
			    		if (data.responseCode == "0") {
						    window.location.href = "${rc.contextPath}/memberCenter/toMemberCenter?time="+new Date().getTime();
						} else {
					      	alert(data.msg);
						}
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
                <p>设置</p>
            </div>

            <ul class="list">
                <a href="${rc.contextPath}/member/toModifyPassword">
                    <li>
                        <p>修改密码</p>
                        <i class="right"></i>
                    </li>
                </a>
                <a href="${rc.contextPath}/member/toFeedback">
                    <li>
                        <p>意见反馈</p>
                        <i class="right"></i>
                    </li>
                </a>
            </ul>

            <ul class="list">
                <a href="${rc.contextPath}/memberCenter/toAboutUs">
                    <li>
                        <p>关于我们</p>
                        <i class="right"></i>
                    </li>
                </a>
            </ul>

            <div class="btn-wrap">
                <button onclick="javascript:logout();">退出当前账号</button>
            </div>

        </div><!-- /.container -->
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>
