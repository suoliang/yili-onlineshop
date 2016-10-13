<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>优惠券类型添加 - CMS</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="renderer" content="webkit">
        <meta name="description" content="阿拉丁后台管理系统">
        <meta name="keywords" content="阿拉丁后台管理系统">
        <link rel="shortcut icon" href="${contextStatic }/images/favicon.ico" />
        
        
  <script type="text/javascript">
   function  showCount(va){
   if(va==0){
	   $("#count_div").html("<input type='number' id='count_number' class='form-control' name='count_limit' min='2'  alt='请输入使用次数'/>");
		    }
         }
   </script>
    </head>
    
    <body id="" class="Cog">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-credit-card"></i> 优惠券类型添加页面</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" id="addRoleForm" action="${contextPath }/promotion/addCardType" method="post" >
                               
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">优惠券类型名称：</label>
                                    <div class="col-sm-4">
                                        <input name="name" type="text" class="form-control">
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">优惠券类型：</label>
                                    <div class="col-sm-4">
                                    	<select name="cardType" class="form-control lg-select">
											<option value="1" ${param.cardType==1?'selected':'' }>通用</option>
											<option value="2" ${param.cardType==2?'selected':'' }>品牌列表</option>
											<option value="3" ${param.cardType==3?'selected':'' }>分类列表</option>
											<option value="4" ${param.cardType==4?'selected':'' }>某些商品</option>
											<option value="5" ${param.cardType==5?'selected':'' }>商品标签</option>
										</select>
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">作用范围id字符串：</label>
                                    <div class="col-sm-4">
                                        <input name="idList" type="text" class="form-control" placeholder="请输入以逗号分割的id字符串,通用不用输入">
                                    </div>
                                </div>
                                
                                   <div class="form-group">
	                                    <label class="col-sm-2 control-label">优惠券优惠金额：</label>
	                                    <div class="col-sm-4">
	                                        <input name="discountMoney" type="text" class="form-control" >
	                                    </div>
                                    </div>
                                
                                  <div class="form-group">
	                                    <label class="col-sm-2 control-label">优惠券使用次数：</label>
	                                    <div class="col-sm-4">
	                                        <select name="useCountLimit" onchange="showCount(this.value)" id="value_select" class="form-control lg-select">
	                                            <option value="-1" >无限次</option>
												<option value="1">使用1次</option>
												<option onclick="showCount()" value="0">其他自定义次数</option>
	                                        </select>
	                                        <div id="count_div"></div>
	                                    </div>
                                    </div>
                                    
                                    <input name="count" id="makesure_count" type="hidden">
                                    
                                    
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">有效开始时间：</label>
                                    
                                    <div class="col-sm-4">
                                    <div class="input-group">
                                        <input class="form-control form_datetime "  name="beginTime" type="text" value="${beginTime}" />
                                        <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
								    </div>
								    </div>
                                </div>
                              
                                
                                  <div class="form-group">
                                    <label class="col-sm-2 control-label">有效结束时间：</label>
                                    <div class="col-sm-4">
                                     <div class="input-group">
                                          <input class="form-control form_datetime" name="endTime"  type="text"  value="${endTime }"/>
                                          <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
								    </div>
                                    </div>
                                    
                                </div>
                                
                                
                                 <div class="form-group">
	                                    <label class="col-sm-2 control-label">最低消费金额：</label>
	                                    <div class="col-sm-4">
	                                        <input name="conditionSkuPriceAbove" type="text" class="form-control" >
	                                    </div>
                                 </div>
                                    
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">是否可用：</label>
                                    <div class="col-sm-4">
                                        <select name="disable" class="form-control lg-select">
											<option value="n">可用</option>
											<option value="y">不可用</option>
										</select> 
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="submit" class="btn btn-info">确认新建优惠券类型</button>
                                        <button type="button" class="btn btn-default" onclick="history.go(-1);">取消</button>
                                    </div>
                                </div>
                                
                            </form>
                        </div>
                    </div>
                </div>
                <!-- /.content -->
            </div>
        </div>
        <!-- /.container-fluid -->
    
        <script type="text/javascript">
            $(document).ready(function() {
                /*表单验证*/
                var validator = $('#addRoleForm').validate({
                    rules: {
                    	name: {
                            required: true,
                        },
                      discountMoney: {
                            required: true
                        } 
                    },
                    messages: {
                    	name: {
                            required: "请填写优惠券类型名！"
                            /* rangelength: "角色名长度应该为2-10位" */
                        },
                        discountMoney: {
                            required: "请填写优惠券优惠金额！"
                        } 
                    },
                 /*    errorPlacement: function(error, element) {
                        if ( element.is(":checkbox") ){
                            error.appendTo($("#addRoleAlert") );
                        } else {
                            error.appendTo(element.parent());
                        }
                    },  */
                    submitHandler: function(form) {
                    	var count=$("#count_number").val();
                    	if(count!=null){
                    		$("#makesure_count").val(count);
                    	}else{
                    		$("#makesure_count").val(0);
                    	}
                        // 验证成功后操作
                        console.log($(form).serialize());
                        $(form).ajaxSubmit();
                    }
                });

            });
        </script>
    </body>
</html>
