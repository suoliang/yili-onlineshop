<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>团购管理详情</title>
	
	
</head>
 <body id="index">
        <div class="container-fluid">
            <div class="row">
			  <div class="col-md-12 content">
			  
			     <div class="panel panel-info">
                    <div class="panel-heading">
                          <h3 class="panel-title"><i class="fa fa-cog"></i> 团购信息详情</h3>
                    </div>
				    <div class="panel-body">
				      <form class="form-horizontal" method="post"  id="togetherForm" >
				 
						<div class="form-group">
							<label class="col-sm-2 control-label">团购商品编码：</label>
							<div class="col-sm-2">
								${skuTogetherDto.skuCode}
							</div>
						</div>
				
						<div class="form-group">
							<label class="col-sm-2 control-label">团购商品名称：</label>
							<div class="col-sm-2">
								${skuTogetherDto.skuName}
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">省：</label>
							<div class="col-sm-2">
								${skuTogetherDto.province}
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">市：</label>
							<div class="col-sm-2">
								${skuTogetherDto.city}
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">县/区：</label>
							<div class="col-sm-2">
								${skuTogetherDto.district}
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">团购开始时间：</label>
							<div class="col-sm-2">
								${skuTogetherDto.beginTime}
                             </div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">团购结束时间：</label>
							<div class="col-sm-2">
								${skuTogetherDto.endTime}
                             </div>
						</div>
					  
					  	<div class="form-group">
							<label class="col-sm-2 control-label">团购状态：</label>
							<div class="col-sm-2">
								${skuTogetherStatus.status=='y'?'开启':'关闭'}
							</div>
						</div>
					   
						<div class="form-group">
							<label class="col-sm-2 control-label">团购最少人数：</label>
							<div class="col-sm-2">
								${skuTogetherStatus.minNumber}
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">团购最多人数：</label>
							<div class="col-sm-2">
								${skuTogetherStatus.maxNumber}
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">实际参团人数：</label>
							<div class="col-sm-2">
								${skuTogetherStatus.currentActualNumber}
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">显示下单人数：</label>
							<div class="col-sm-2">
								${skuTogetherStatus.showNumber}
							</div>
						</div>
						
						<div class="form-group">
					    	<div class="col-sm-offset-2 col-sm-10">
					      	
								<button class="btn btn-primary" type="button" onclick="javascript:history.go(-1);">返回</button>
					   	 	</div>
						</div>
				  
				</form>
			  </div>
			  </div>
       		</div>
       	  </div>
       	</div>
   </body>
</html>