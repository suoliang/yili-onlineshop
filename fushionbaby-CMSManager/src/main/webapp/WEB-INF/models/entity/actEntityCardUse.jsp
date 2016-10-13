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
        <title>实体卡使用 - CMS</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="renderer" content="webkit">
        <meta name="description" content="阿拉丁后台管理系统">
        <meta name="keywords" content="阿拉丁后台管理系统">
        <link rel="shortcut icon" href="${contextStatic}/images/favicon.ico" />
    </head>
    <body id="" class="Cog">
    <tags:message content="${message}"></tags:message>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-cog"></i> 使用实体卡</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" id="addRoleForm" action="${contextPath}/actEntityCard/actEntityCardUse" method="post">
                                <table class="table table-bordered table-hover" id="roleTable">
                                        <tr>
											<td>
				                                <div class="form-group">
				                                    <label class="col-sm-2 control-label">卡号：</label>
				                                    <div class="col-sm-5">
				                                        <input name="cardNo" id="cardNo" value="${cardNo }" type="text" class="form-control">
				                                    </div>
				                                </div>
                                			</td>
											<td align="center">
												<button type="button" class="btn btn-info" onclick="findByCardNo()">查看</button>
												<button type="submit" class="btn btn-info">确认使用</button>
											</td>
										</tr>
										<tr>
											<td>
				                                <div class="form-group">
				                                    <label class="col-sm-2 control-label">余额：</label>
				                                    <div class="col-sm-5">
				                                        <input name="cardNo" id="cardNo" value="${actEntityCard.surplusMoney }" disabled type="text" class="form-control">
				                                    </div>
				                                </div>
                                			</td>
											<td>
												<div class="form-group">
				                                    <label class="col-sm-2 control-label">购卡时间：</label>
				                                    <div class="col-sm-5">
				                                        <input name="cardNo" id="cardNo" value="<fmt:formatDate value="${actEntityCard.createTime }" pattern="yyyy-MM-dd" />" disabled type="text" class="form-control">
				                                    </div>
				                                </div>
											</td>
										</tr>
										<tr>
											<td>
				                                <div class="form-group">
				                                    <label class="col-sm-2 control-label">生效时间：</label>
				                                    <div class="col-sm-5">
				                                        <input name="cardNo" id="cardNo" value="<fmt:formatDate value="${actEntityCardConfig.beginTime }" pattern="yyyy-MM-dd" />" disabled type="text" class="form-control">
				                                    </div>
				                                </div>
                                			</td>
											<td>
												<div class="form-group">
				                                    <label class="col-sm-2 control-label">失效时间：</label>
				                                    <div class="col-sm-5">
				                                        <input name="cardNo" id="cardNo" value="<fmt:formatDate value="${actEntityCardConfig.expiration }" pattern="yyyy-MM-dd" />" disabled type="text" class="form-control">
				                                    </div>
				                                </div>
											</td>
										</tr>
										<tr>
											<td>
				                                <div class="form-group">
				                                    <label class="col-sm-2 control-label">使用金额：</label>
				                                    <div class="col-sm-5">
				                                        <input name="money" id="money" type="text" class="form-control">
				                                    </div>
				                                </div>
                                			</td>
											<td>
												<div class="form-group">
				                                    <label class="col-sm-2 control-label">输入密码：</label>
				                                    <div class="col-sm-5">
				                                        <input name="chargePwd" id="chargePwd" type="password" class="form-control">
				                                    </div>
				                                </div>
											</td>
										</tr>
										<tr>
											<td>
												<div class="form-group">
				                                    <label class="col-sm-2 control-label">交易说明：</label>
				                                    <div class="col-sm-5" >
				                                       <textarea  name="memo" id="memo" style="width: 100%">${memmo}</textarea>
				                                    </div>
				                                </div>
											</td>
											<td>
												<div class="form-group">
				                                    <label class="col-sm-2 control-label">     </label>
				                                    <div class="col-sm-5" >
				                                      <button type="button" onclick="javascript:printUseRecord('${actEntityCard.id }');">点击导出待打印小票</button>
				                                    </div>
				                                </div>
											</td>
										</tr>
                                </table>
                            </form>
                        </div>
                    </div>
                </div>
                <!-- /.content -->
            </div>
        </div>
        <!-- /.container-fluid -->
    
        <script type="text/javascript">
         function findByCardNo(){
        	 var cardNo = $("#cardNo").val();
        	 if(cardNo == "" || cardNo == null){
        		 jBox.tip("请输入卡号！！", 'error');
        		 return false;
        	 }
        	 window.location.href = "${contextPath}/actEntityCard/findByCardNo/"+cardNo;
        	/*  var url = "";
        	 $.ajax({
		  		   type: "get",
		  		   url: url,
		  		   data: "cardNo="+cardNo,
		  		   success: function(msg){
		  			   if(msg.responseCode == "500"){
		  				 	jBox.tip(msg.msg, 'error');
		  		    	 	return;
		  			   }
		  			   
		  			   if(msg == false){
		  		    	 	jBox.tip("查询失败！！", 'error');
		  		    	 	return;
		  		       }
		  			   
		  		   }
		  		}); */
         }
function modifySkuStatus(){
	  		var cardNo = $("#cardNo").val();
	  		var chargePwd = $("#chargePwd").val();
	  		var money = $("#money").val();
	  		var memo = $("#memo").val();
	  		var submit =  function(v,h,f){
	  			if(v=="ok"){
			  		var url = "${contextPath}/actEntityCard/actEntityCardUse";
			  		$.ajax({
			  		   type: "POST",
			  		   url: url,
			  		   data: "money="+money+"&chargePwd="+chargePwd+"&cardNo="+cardNo+"&memo="+memo+"&time="+new Date().getTime(),
			  		   success: function(data){
			  			   if(data.responseCode == "500"){
			  				 	jBox.tip(data.msg, 'error');
			  		    	 	return;
			  			   }else{
			  				 jBox.tip("消费成功，余额为："+data.data, 'success');
			  			   }
			  			   if(data == false){
			  		    	 	jBox.tip("使用失败！！", 'error');
			  		    	 	return;
			  		       }
			  			   
			  		       setTimeout(function () {  window.location.reload()}, 1500);
			  		   }
			  		});
	  			}
	  			return true;
	  		}
	  		$.jBox.confirm("你确定使用实体卡？", "操作提示",submit);
	  	}
        
            $(document).ready(function() {
                	//表单验证
                	/* var cardNo = $("#cardNo").val();
	  		var chargePwd = $("#chargePwd").val();
	  		var money = $("#money").val(); */
                var validator = $('#addRoleForm').validate({
                      rules: {
                    	  cardNo: {
                            required: true,
                        },
                        money: {
                            required: true,
                        	number:true
                        },
                        chargePwd: {
                        	required: true,
                        }
                    },
                    messages: {
                    	cardNo: {
                            required: "请填写卡号！"
                        },
                        money: {
                        	required: "请填写消费金额！",
                        	number: "必须是合法的数字"
                        },
                        chargePwd: {
                            required: "请填写密码！",
                        }
                       	
                    }, 
                    submitHandler: function(form) {
                        // 验证成功后操作
                        console.log($(form).serialize());
                        modifySkuStatus();
                    } 
                });
				
            });
        </script>
        
        <script type="text/javascript">
          function printUseRecord(id){
        	  if(id==null||id==undefined){
        		  alert("您还没有输入卡号，请输入");
        	  }else{
        		  window.location.href="${contextPath}/actEntityCard/export?id="+id+"&time="+new Date().getTime();
        	  }
        	/*   
	        	var url = "iframe:${contextPath}/actEntityCard/print?id="+id;
				$.jBox(url, {
		  		    title: "交易信息打印", width: 200,height: 600,
		  		    buttons: { '关闭': false },
				});
			} */
			}
        </script>
        
    </body>
</html>
