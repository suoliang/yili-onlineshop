$(document).ready(function() {
//获取焦点变色
$("input").focus(function(event) {
	$(this).css('border', '1px solid #7BDFF5');
});
//失去焦点变色
$("input").blur(function(event) {
	$(this).css('border', '1px solid #CFCFCF');
});
//按钮变色
$("#login").hover(function() {
	$(this).css({"background-color":"#fff","color":"#7BDFF5"});
}, function() {
	$(this).css({"background-color":"#7BDFF5","color":"#fff"});
});
$("#register").hover(function() {
	$(this).css({"background-color":"#fff","color":"#A5A5A5"});
}, function() {
	$(this).css({"background-color":"#A5A5A5","color":"#fff"});
});

});
