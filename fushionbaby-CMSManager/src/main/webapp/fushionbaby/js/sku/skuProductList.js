var skuProduct;
 
$(document).ready(function() {
	skuProduct = new skuProductMain();
	skuProduct.init();
});

 
skuProductMain = function() {
	
	//init()
	this.init = function init() {
	};
	
    //打开弹出框
    this.winOpen = function winOpen(option) {
	   $.ajax({
		   type:'POST',
		   url:option.url,
		   data:option.data,
		   dataType:'html',
		   success:function(result) {
			  $("#my_modal_title").html(option.title); 
			  $("#my_modal_content").html(result);
			  $("#my_modal").show();
		   }
		});
	};

	//关闭弹出窗口
	this.winClose = function winClose() {
		$("#my_modal").hide();
	};

	this.callBack = function callBack() {
		$("#findForm").submit();
	};
	
	this.add = function add() {			
		skuProduct.winOpen({   
			title: '添加商品产品',
			url: _ContextPath + '/sku/getSkuProduct.do?flag=add'
	    });
	};

	this.edit = function edit(id) {		
		skuProduct.winOpen({   
			title:'修改商品产品',
			url: _ContextPath + '/sku/getSkuProduct.do?flag=update&id=' + id
	    });
	};
	
	var delId = '';
	var delHtml ='';
	this.del = function del(id) {
		delId = id;
		delHtml = '<div style="display: block; width: auto; min-height: 0px; max-height: none; height: 50px; line-height:50px;" class="hide ui-dialog-content ui-widget-content dialog_confirm">'
			+'你确定要删除吗？'
			+'</div>'
			+'<div class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix">'
			+'<div class="ui-dialog-buttonset">'
			+'		<button class="btn red" type="button" onclick="skuProduct.delOK();">确定</button>'
			+'		<button class="btn cancel" type="button" onclick="skuProduct.winClose();">取消</button>'
			+'	</div>'	
			+'</div>';
			
		$("#my_modal_title").html('提示信息---------------'); 
		$("#my_modal_content").html(delHtml);
		$("#my_modal").show();

	};
	
	this.delOK = function delOK() {
		var ajaxURL= _ContextPath + '/sku/editSkuProduct.do';       
		$.ajax({
            type: "post",
            dataType: "json",
            url: ajaxURL+'?flag=delete&id='+delId,
            success: function (data) {
        		if (data.result=="success"){
        			skuProduct.callBack();
        		} else{
        			$("#my_modal").hide();
        			alert("该产品不能删除");
        		}
            }
        });	
	};
	
	
	
	
};

 