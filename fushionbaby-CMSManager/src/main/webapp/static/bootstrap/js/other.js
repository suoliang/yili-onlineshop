/**
 * 额外添加的js 孟少博
 */
/* 将空文本框值为0*/
$(document).ready(function() {
	$(".txt-zero").blur(function(){
		if(this.value == ''){
			this.value = 0;
		} 
	});
});
/* 序列化form表单为JSON字符串*/
$.fn.serializeObject = function()  
{     
	var o = {};
	var a = this.serializeArray();     
	$.each(a, function() {         
		if (o[this.name]) {             
			if (!o[this.name].push) {                 
				o[this.name] = [o[this.name]];             
			}             
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';        
		}     
	});     
	return o;  
};