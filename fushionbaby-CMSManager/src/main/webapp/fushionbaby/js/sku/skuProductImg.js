var skuProductImg;
 
$(document).ready(function() {
	skuProductImg = new skuProductMain();
	skuProductImg.init();	
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


	this.edit = function edit(id) {	
		skuProductImg.winOpen({   
			title:'修改产品图片',
			url: _ContextPath + '/sku/getSkuProductImg.do?id=' + id
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
			+'		<button class="btn red" type="button" onclick="skuProductImg.delOK();">确定</button>'
			+'		<button class="btn cancel" type="button" onclick="skuProductImg.winClose();">取消</button>'
			+'	</div>'	
			+'</div>';
			
		$("#my_modal_title").html('提示信息-----------'); 
		$("#my_modal_content").html(delHtml);
		$("#my_modal").show();
	};
	
	this.delOK = function delOK() {
		var ajaxURL= _ContextPath + '/sku/saveSkuProduct.do';       
        $.ajax({
            type: "GET",
            dataType: "json",
            url: ajaxURL+'?flag=delete&id='+delId,
            success: function (data) {
        		if (data.result=="success"){
        			skuProductImg.callBack();
        		} 
            }
        });	
	};
};
