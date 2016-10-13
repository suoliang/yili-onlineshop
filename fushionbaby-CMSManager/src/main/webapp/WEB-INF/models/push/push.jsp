<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->    
<!DOCTYPE html>
<head>
	<meta charset="utf-8" />
	<title>消息推送</title>
	<style>
	  body {background-color: #3d3d3d;}
	  legend ,#push label ,#push .help-block {color: #fff;}
	  .push_select .controls {float: left;margin-left: 20px;*padding:0;}
	  .input-xlarge {width:306px;}
	  .push_button .btn {margin-right:20px;}
	  .push_button {*margin-left:160px;}
	</style>
</head>
<body>
	<div class="row-fluid activity">
		<!-- BEGIN SIDEBAR -->
		<!-- BEGIN PAGE -->  
		<div class="page-content">
			<div class="container-fluid main-right">
				<div class="row-fluid">
					<div class="span12">
						<!-- BEGIN SAMPLE FORM PORTLET-->   
						<div class="portlet box green">
							<div class="portlet-title">
								<div class="caption"><i class="icon-reorder"></i>消息推送</div>
							</div>
							<div class="portlet-body form">
								<!-- BEGIN FORM-->
								<form class="form-horizontal" action="pushMain.do" method="post">
									<fieldset>
										<div id="legend">
											<legend>消息推送</legend>
										</div>
										<div class="control-group">
											<label class="control-label" for="推送标题">推送标题</label>
											<div class="controls">
												<input class="input-xlarge" name="title" value="${title }" type="text" placeholder="请输入推送标题">
											</div>
										</div>
										
										<div class="control-group">
											<label class="control-label" for="推送标题">推送tag</label>
											<div class="controls">
												<input class="input-xlarge" name="tag" value="${tag }" type="text" placeholder="给所有标签（tag）下的用户推送">
											</div>
										</div>
										
										<div class="control-group">
											<label class="control-label" for="推送标题">推送alias</label>
											<div class="controls">
												<input class="input-xlarge" name="alias" value="${alias }" type="text" placeholder="给所有别名（alias）下的用户推送">
											</div>
										</div>
										
									
										<div class="control-group">
											<!-- Textarea -->
											<label class="control-label">推送内容</label>
											<div class="controls">
												<div class="textarea">
													<textarea name="content" value="${content}" class="input-xlarge"></textarea>
												</div>
											</div>
										</div>
										
										
											<div class="control-group push_select">
											<!-- Select Basic -->
									 		<label class="control-label">手机端显示位置</label>
											<div class="controls">
												<select class="input-medium" name="accessType">
													<option value="0" ${accessType=='0'?'selected':'' }>访问首页</option> 
													<option value="1" ${accessType=='1'?'selected':'' }>商品详情</option>
													<option value="2" ${accessType=='2'?'selected':'' }>商品分类</option>
													<option value="3" ${accessType=='3'?'selected':'' }>活动相关</option>
												</select>
											</div> 
											<div class="controls" >
											     <input  style="width: 135px" class="input-xlarge" name="data" value="${data }" type="text" placeholder="具体id" >
											</div>
										</div>
										
										
										<div class="control-group push_select">
											<!-- Select Basic -->
									 		<label class="control-label">推送消息</label>
											<div class="controls">
												<select class="input-medium" name="type">
													<%-- <option value="0" ${type=='0'?'selected':'' }>发送通知</option>  --%>
													<option value="1" ${type=='1'?'selected':'' }>发送通知</option>
												</select>
											</div> 
											<div class="controls">
												<select class="input-medium" name="device">
													<option value="ios-android" ${device=='ios-android'?'selected':'' }>全部</option>
													<option value="ios" ${device=='ios'?'selected':'' }>IOS</option>
													<option value="android" ${device=='android'?'selected':'' }>安卓</option>
												</select>
											</div>
										</div>
										
										
										
										<div class="control-group">
											<!-- Text input-->
											<label class="control-label" for="input01">推送链接</label>
											<div class="controls">
												<input class="input-xlarge" name="url" value="" type="text" placeholder="请输入推送链接">
												<p class="help-block">
													格式：http://www.fushionbaby.com
												</p>
											</div>
										</div>
										<div class="control-group push_button">
											<!-- Button -->
											<div class="controls">
												<button class="btn btn-primary btn-large" >确定</button>
												<button class="btn btn-large">取消</button>
											</div>
										</div>
									</fieldset>
								</form>
								<!-- END FORM-->       
							</div>
						</div>
						<!-- END SAMPLE FORM PORTLET-->
					</div>
				</div>
				<!-- END PAGE CONTENT-->         
			</div>
			<!-- END PAGE CONTAINER-->
		</div>
		<!-- END PAGE -->  
	</div>
</body>
</html>


