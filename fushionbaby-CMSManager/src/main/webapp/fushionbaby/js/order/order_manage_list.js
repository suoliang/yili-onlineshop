/**
 * 张明亮
 * 订单管理页面用的的js方法
 */

/**
 * 弹出提示框,确认还是取消、初始化数据
 * type
 * 1.删除
 * 2.发货
 * 3.确认发货
 */
function update_init(type,code){
	$("#update_code").val(code);
	$("#update_type").val(type);
	if(type==1){
		$("#Audit_fail_reason").hide();
		$("#tip_content").text("确定用户已经拒收了吗?请仔细核对奥!!!");
	}
	if(type==2){
		$("#Audit_fail_reason").hide();
		$("#tip_content").text("确定要发货吗？");
	}
	if(type==3){
		$("#Audit_fail_reason").hide();
		$("#tip_content").text("确定客户收到货了吗？");
	}
	if(type==4){
		$("#Audit_fail_reason").show();
		$("#tip_content").text("审核确定不通过吗？");
	}
	if(type==5){
		$("#Audit_fail_reason").hide();
		$("#tip_content").text("审核确定通过吗？");
	}
}

/**
 * 订单更新状态操作
 * @param type
 * 1.删除
 * 2.发货
 * 3.确认发货
 * 4.取消订单
 */
function update_opt(){
	var code = $("#update_code").val();
	var type = $("#update_type").val();
	
	if(type==1){
		//用户拒收
		is_refused(code);
		//删除
		//deleteOrder(code);
	}else if(type==2){
		//发货
		delivering(code);
	}else if(type==3){
		//确定客户收到货了
		confirmReceipt(code);
	}else if(type==4){
		//订单审核不通过
		fail_Audit(code);
	}else if(type==5){
		//订单审核通过
		success_Audit(code);
	}
}

function deleteOrder(code){
	$.post('delete_order.do',
	{code : code,time : new Date().getTime()},
	function(data) {
		if (data.result == "success") {
			alert(data.msg);
			query();// 重新查询
		} else {
			alert(data.msg);
		}
	});// ajax删除请求end
}

//用户拒收产品
function is_refused(code){
	$.post('is_refused.do',
		{code:code,	time:new Date().getTime()},
		function(data){
			if (data.result == "success") {
				alert(data.msg);
				query();//重新查询
			} else {
				alert(data.msg);
			}
		}
	);
}
//订单审核通过
function success_Audit(code){
	$.post('audit_success.do',
	{	code:code,	time:new Date().getTime()},
	function(data){
		if (data.result == "success") {
			alert(data.msg);
			query();//重新查询
		} else {
			alert(data.msg);
		}
	});
}
//订单审核不通过
function fail_Audit(code){
	var auditFailReason = $("#editAudit_fail_reason").val().trim();
	if (auditFailReason.length == 0) {
		alert("必须输入审核不通过原因!");
		return;
	}
	if (auditFailReason.length > 200) {
		alert("审核原因请不要超过200个字");
		return;
	}
	$.post('audit_fail.do',{
		code:code,
		auditFailReason:auditFailReason,
		time:new Date().getTime()
	},function(data){
		if (data.result == "success") {
			alert(data.msg);
			query();//重新查询
		} else {
			alert(data.msg);
		}
	});
}
// 发货
function delivering(code){
	$.post('delivering.do', {
		code : code,
		time : new Date().getTime()
	}, function(data) {
		if (data.result == "success") {
			alert(data.msg);
			query();// 重新查询
		} else {
			alert(data.msg);
		}
	});// ajax删除请求end
}

// 确认收货
function confirmReceipt(code){
	$.post('confirm_receipt.do', {
		code : code,
		time : new Date().getTime()
	}, function(data) {
		if (data.result == "success") {
			alert(data.msg);
			query();// 重新查询
		} else {
			alert(data.msg);
		}
	});// ajax删除请求end
}

function findEditArea(area_type,area_code,default_code){
	$.post('find_area_list.do', {
		code :area_code,
		time : new Date().getTime()
	}, function(data) {
		if (data == null || data == "" || data == undefined) {
			alert("系统没有配置地区参数");
			return;
		}
		
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
			if(default_code==areaObj.areaCode){
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
	$.post('find_area_list.do', {
		code :code,
		time : new Date().getTime()
	}, function(data) {
		if (data == null || data == "" || data == undefined) {
			return;
		}
		var find_id="editAddress_city";
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
		var len=data.length;
		for(i=0;i<len;i++){
			var areaObj=data[i];
			$("#"+find_id).append("<option value='"+areaObj.code+"'>"+areaObj.name+"</option>");
		}
	});// ajax管理员取消订单请求end
}

function editOrderAddress(code) {
	$.ajax({
		type : "POST",
		url : "find_order_address.do",
		data : "orderCode=" + code+"&time="+new Date().getTime(),
		async : false,// 同步请求,本次请求完成后,后边的代码才会执行
		dataType : "json",
		success : function(data) {
			if (data == null || data == "" || data == undefined) {
				alert("系统没有查询到该订单收货地址信息");
				return;
			}
			$("#editAddress_code").val(data.soCode);
			$("#editAddress_receiver").val(data.receiver);
			$("#editAddress_address").val(data.address);
			
			//alert("省:"+data.province+"city="+data.city+"district="+data.district);
			
			findEditArea(1,0,data.province);
			findEditArea(2,data.province,data.city);
			findEditArea(3,data.city,data.district);

			$("#editAddress_receiverMobile").val(data.receiverMobile);
			$("#editAddress_receiverPhone").val(data.receiverPhone);
		}
	});
}

function saveEditOrderAddress() {
	var orderCode = $("#editAddress_code").val();
	var receiver = $("#editAddress_receiver").val();
	var address = $("#editAddress_address").val();
	var province = $("#editAddress_province").val();
	var city = $("#editAddress_city").val();
	var district = $("#editAddress_district").val();
	var receiverMobile = $("#editAddress_receiverMobile").val();
	var receiverPhone = $("#editAddress_receiverPhone").val();
	$.post('edit_order_address.do', {
		soCode : orderCode,
		receiver : receiver,
		address : address,
		province : province,
		city : city,
		district : district,
		receiverMobile : receiverMobile,
		receiverPhone : receiverPhone,
		time : new Date().getTime()
	}, function(data) {
		if (data.result == "success") {
			alert(data.msg);
			query();//重新查询
		} else {
			alert(data.msg);
		}
	});//ajax更新保存请求end
}

function query(){
	$("#queryForm").submit();
}