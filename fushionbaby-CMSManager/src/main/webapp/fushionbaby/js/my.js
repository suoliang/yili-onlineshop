$(document).ready(function() 
{
	//弹出新增div
	$(".my-add-icon").click(function(event) {		
		$(".my-add").show();
	});
	
	//弹出修改div
	$(".my-edit-icon").click(function(event) {
		$(".my-edit").show();
	});
	
	//弹出删除提示div
	$(".my-del-icon").click(function(event) {
		$(".my-del").show();
	});
	
	//弹出明细div
	$(".my-more-icon").click(function(event) {
		$(".my-more").show();
	});

	//点击关闭新增、删除、修改、明细,弹出框
	$(".red,.my-btn,.blue,.cancel").click(function(event) {
		$(".my-add,.my-edit,.my-del,.my-more").hide();
	});
	
	//点击侧边栏背景色变红
	$('.change-c').click(function(){
		$(this).css('background','#b93437').siblings('.change-c').css('background','#303030');		
	});
});