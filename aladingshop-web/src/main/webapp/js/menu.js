$(document).ready(function(){
	var str=location.href; //取得整个地址栏 
	str.replace(/\\/g, "/");
	var check = str.substring(0, str.lastIndexOf("/"));
	if(check.indexOf("category") <= 0){
		return;
	}
	
	var array = str.split("/");
	var template = array[array.length-1].replace(".htm","");
	$(".menu-ul-f").find('li').removeClass('active');
	$("#menu_"+template).addClass('active');
});