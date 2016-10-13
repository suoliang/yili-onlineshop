<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>团购商品添加</title>
	 <script type="text/javascript">
			 $(function(){
		         $('#beginTime').datetimepicker('setStartDate', new Date());
		
		         $('#beginTime').change(function(event) {
		        	 $('#endTime').val("");
		             var datetimepickerStart_value = $(this).val();
		             $('#endTime').datetimepicker('setStartDate', datetimepickerStart_value);
		         });
		     })
		   function checkSkuCodeAndTime(){
			   if($("#skuCode").val()==""){
					$("#skuCode").parent().siblings('.warning').html('商品不能为空！');
					return false;
				}
				if($("#beginTime").val()==""){
					$("#beginTime").parent().parent().siblings('.warning').html('团购开始时间不能为空！');
					return false;
				}
				if($("#endTime").val()==""){
					$("#endTime").parent().parent().siblings('.warning').html('团购结束时间不能为空！');
					return false;
				}
				return true;
			}
            $(document).ready(function() {
            	
            	/*表单验证*/
                var validator = $('#togetherForm').validate({
                    rules: {
                    	beginTime: {
                            required: true,
                         
                        },
                        endTime: {
                            required: true,
                        },
                        province: {
                            required: true,
                        },
                        city: {
                            required: true,
                        },
                        district: {
                            required: true,
                        },
                        maxNumber: {
                            required: true,
                            digits:true,
                        },
                        minNumber: {
                            required: true,
                            digits:true,
                        },
                        showNumber: {
                            required: true,
                            digits:true,
                        }
                    },
                    messages: {
                    	beginTime: {
                            required: "请填写团购开始时间！",
                           
                        },
                        endTime: {
                        	required: "请填写团购结束时间！",
                        },
                        province: {
                        	required: "请填写团购省份！",
                        },
                        city: {
                        	required: "请填写团购城市！",
                        },
                        district: {
                        	required: "请填写团购区县！",
                        },
                        maxNumber: {
                        	required: "请填写团购最大人数！",
                        	digits:"团购最大人数必须是正整数！",
                        },
                        minNumber: {
                        	required: "请填写团购最少人数！",
                        	digits:"团购最少人数必须是正整数！",
                        },
                        showNumber: {
                        	required: "请填写团购显示下单人数！",
                        	digits:"团购显示下单人数必须是正整数！",
                        }
                    },
                    errorPlacement: function(error, element) {
                        if ( element.is(".form_datetime") ){
                        	element.parent().parent().parent().find('.warning').html('');
                        	error.appendTo(element.parent().parent().parent().find('.warning'));
                        } else {
                        	element.parent().parent().find('.warning').html('');
                        	error.appendTo(element.parent().parent().find('.warning'));
                        }
                    },
                    submitHandler: function(form) {
                        // 验证成功后操作
                       console.log($(form).serialize());
                        if(checkSkuCodeAndTime()){
                        	checkExit(form);
                        }
                       
                    }
                });
            });
            
            function checkExit(form){
            	var skuCode =$("#skuCode").val();
				var district =$("#editAddress_district").val();
				var beginTime =$("#beginTime").val();
				var endTime =$("#endTime").val();
            	$.post('${contextPath}/skuTogether/togetherExit', {
            		skuCode:skuCode,
            		district:district,
            		beginTime:beginTime,
            		endTime:endTime,
    				time : new Date().getTime()
    			}, function(data) {
    				if(!data){
                		form.submit();
                	}else{
                		jBox.tip("该商品在该地区该时段已经存在，请不要重复添加！", 'info');
                	}
    			});
            	
            }
            
            function findEditArea(area_type,area_code,default_code){
    	  		
    	  		$.post('${pageContext.request.contextPath}/order/find_area_list', {
    	  			code :area_code,
    	  			time : new Date().getTime()
    	  		}, function(data) {
    	  			var init_id="#editAddress_province";
    	  			if(area_type==1){
    	  				//省
    	  				init_id="#editAddress_province";
    	  				$("#editAddress_province").empty();//清空
    	  				$("#editAddress_province").append("<option value=''>请选择</option>");
    	  			}else if(area_type==2){
    	  				//市
    	  				init_id="#editAddress_city";
    	  				$("#editAddress_city").empty();//清空
    	  				$("#editAddress_city").append("<option value=''>请选择</option>");
    	  			}else if(area_type==3){
    	  				//区/县
    	  				init_id="#editAddress_district";
    	  				$("#editAddress_district").empty();//清空
    	  				$("#editAddress_district").append("<option value=''>请选择</option>");
    	  			}
    	  			
    	  			var len=data.length;
    	  			for(i=0;i<len;i++){
    	  				var areaObj=data[i];
    	  				if(default_code==areaObj.code){
    	  					$(init_id).append("<option selected='selected' value='"+areaObj.code+"'>"+areaObj.name+"</option>");
    	  				}else{
    	  					$(init_id).append("<option value='"+areaObj.code+"'>"+areaObj.name+"</option>");
    	  				}
    	  			}
    	  		});// ajax管理员取消订单请求end
    	  	}
    	  	
    		//订单地址,市
    		function findAreaList(obj){
    			var obj_id=obj.id;
    			var code=obj.value;
    			var find_id="editAddress_province";
    			if(obj_id=="editAddress_province"){
    				//省发生改动,清空原有的市,区/县
    				$("#editAddress_city").empty();//清空,原有的市
    				$("#editAddress_district").empty();//清空
    				$("#editAddress_city").append("<option value=''>请选择</option>");
    				$("#editAddress_district").append("<option value=''>请选择</option>");
    				
    				find_id="editAddress_city";//加载省旁边的市,下拉框
    			}else if(obj_id=="editAddress_city"){
    				//市发生改动,清空原有的,区/县
    				$("#editAddress_district").empty();//清空,原有的区县
    				$("#editAddress_district").append("<option value=''>请选择</option>");
    				find_id="editAddress_district";
    			}
    			
    			$.post('${pageContext.request.contextPath}/order/find_area_list', {
    				code :code,
    				time : new Date().getTime()
    			}, function(data) {
    				if (data == null || data == "" || data == undefined) {
    					return;
    				}
    				var len=data.length;
    				for(i=0;i<len;i++){
    					var areaObj=data[i];
    					$("#"+find_id).append("<option value='"+areaObj.code+"'>"+areaObj.name+"</option>");
    				}
    			});
    		}
    	  	window.onload=function(){
    	  		var province=$("#province").val();
    		  	findEditArea(1,0,province);
    		};
    		
    		
    		
    		function  addSku(){
    			var url = "iframe:${contextPath}/skuTogether/selectSkuList";
    			$.jBox(url, {
    	  		    title: "商品列表", width: 960,height: 660,
    	  		    buttons: { '确定':true,'关闭': false },
    	  		  	submit: function (v, h, f) {
    	              if (v == 0) {
    	            	  return ;
    	              }

    	         	var contents = h.find("iframe").contents();
    	           
    	           	var skuName = contents.find("#checkSkuName").val();
    	           	var skuCode = contents.find("#checkSkuCode").val();
    	           	$("#skuName").val(skuName);
    	           	$("#skuCode").val(skuCode);
    	          }

    	  		});
    		}
        </script>
	
</head>
 <body id="index">
        <div class="container-fluid">
            <div class="row">
			
			  <div class="col-md-12 content">
			  
			     <div class="panel panel-info">
                    <div class="panel-heading">
                          <h3 class="panel-title"><i class="fa fa-cog"></i> 团购信息添加</h3>
                    </div>
				    <div class="panel-body">
				      <form class="form-horizontal" method="post" action="${contextPath }/skuTogether/addTogether" id="togetherForm" >
				 
				 		<input name="id" id="id" type="hidden" value="${skuTogether.id}"/>
						<div class="form-group">
							<label class="col-sm-2 control-label">团购商品编码：</label>
							<div class="col-sm-2">
								<input name="skuCode" id="skuCode" type="text" class="form-control"  readonly placeholder="请选择商品"/>
							</div>
							<button type="button" class="btn btn-default pull-left" aria-label="Left Align"
									id="add-sku-attr" onclick="addSku()">
  								<span class="glyphicon glyphicon-plus" aria-hidden="true">+</span>
							</button>
							<div  class="error warning col-sm-2  textL">必填项</div>
						</div>
				
						<div class="form-group">
							<label class="col-sm-2 control-label">团购商品名称：</label>
							<div class="col-sm-2">
								<input name="skuName" id="skuName" type="text" class="form-control" readonly/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">省：</label>
							<div class="col-sm-2">
								<select name="province" id="editAddress_province" onchange="findAreaList(this)" class="form-control lg-select" data-placeholder="Choose a province" tabindex="1">
					  	        </select>
							</div>
							<div class="error warning col-sm-2  textL">必填项</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">市：</label>
							<div class="col-sm-2">
								<select name="city" id="editAddress_city" onchange="findAreaList(this)" class="form-control lg-select" data-placeholder="Choose a city" tabindex="1">
					       	    </select>
							</div>
							<div class="error warning col-sm-2  textL">必填项</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">县/区：</label>
							<div class="col-sm-2">
								<select name="district" id="editAddress_district" onchange="findAreaList(this)" class="form-control lg-select" data-placeholder="Choose a district" tabindex="1">
					            </select>
							</div>
							<div class="error warning col-sm-2  textL">必填项</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">团购开始时间：</label>
							<div class="col-sm-2">
								<div class="input-group">
                                <input type="text" name="beginTime" id="beginTime" class="form-control form_datetime" readonly>
                                <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                             </div>
							</div>
							<div class="error warning col-sm-2  textL">必填项</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">团购结束时间：</label>
							<div class="col-sm-2">
								<div class="input-group">
                                <input type="text" name="endTime" id="endTime" class="form-control form_datetime" readonly>
                                <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                             </div>
							</div>
							<div class="error col-sm-2 warning  textL">必填项</div>
						</div>
					  
					    <div class="form-group">
							<label class="col-sm-2 control-label">团购状态：</label>
							<div class="col-sm-2">
								<select name="status" id="editStatus" class="form-control lg-select" data-placeholder="Choose a status" tabindex="1">
					            	<option value="y">开启</option>
					            	<option value="n">关闭</option>
					            </select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">团购最少人数：</label>
							<div class="col-sm-2">
								<input name="minNumber" id="minNumber" type="text" class="form-control" />
							</div>
							<div class="error col-sm-2 warning  textL">必填项</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">团购最多人数：</label>
							<div class="col-sm-2">
								<input name="maxNumber" id="maxNumber" type="text" class="form-control" />
							</div>
							<div class="error col-sm-2 warning  textL">必填项</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">显示下单人数：</label>
							<div class="col-sm-2">
								<input name="showNumber" id="showNumber" type="text" class="form-control"/>
							</div>
							<div class="error col-sm-2 warning  textL">必填项</div>
						</div>
						<div class="form-group">
					    	<div class="col-sm-offset-2 col-sm-10">
					      	
					      		<button class="btn btn-success " type="submit">确定</button>
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