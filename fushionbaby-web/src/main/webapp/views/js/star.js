var Class = {
	create: function() {
		return function() { this.initialize.apply(this, arguments); }
	}
}
var Extend = function(destination, source) {
	for (var property in source) {
		destination[property] = source[property];
	}
}
function stopDefault( e ) {
	 if ( e && e.preventDefault ){
		e.preventDefault();
	}else{
		window.event.returnValue = false;
	}
	return false;
} 
/**
 * ÐÇÐÇ´ò·Ö×é¼þ
 *
 * @author	Yunsd
 * @date		2010-7-5
 */
var Stars = Class.create();
Stars.prototype = {
	initialize: function(star,options) {
		this.SetOptions(options); //Ä¬ÈÏÊôÐÔ
		var flag = 999; //¶¨ÒåÈ«¾ÖÖ¸Õë
		var isIE = (document.all) ? true : false; //IE?
		var starlist = document.getElementById(star).getElementsByTagName('a'); //ÐÇÐÇÁÐ±í
		var input = document.getElementById(this.options.Input) || document.getElementById(star+"-input"); // Êä³ö½á¹û
		var tips = document.getElementById(this.options.Tips) || document.getElementById(star+"-tips"); // ´òÓ¡ÌáÊ¾
		var nowClass = " " + this.options.nowClass; // ¶¨ÒåÑ¡ÖÐÐÇÐÇÑùÊ½Ãû
		var tipsTxt = this.options.tipsTxt; // ¶¨ÒåÌáÊ¾ÎÄ°¸
		var len = starlist.length; //ÐÇÐÇÊýÁ¿
		

		for(i=0;i<len;i++){ // °ó¶¨ÊÂ¼þ µã»÷ Êó±ê»¬¹ý
			starlist[i].value = i;
			starlist[i].onclick = function(e){
				stopDefault(e);
				this.className = this.className + nowClass;
				flag = this.value;
				input.value = this.getAttribute("star:value");
				tips.innerHTML = tipsTxt[this.value]
			}
			starlist[i].onmouseover = function(){
				if (flag< 999){
					var reg = RegExp(nowClass,"g");
					starlist[flag].className = starlist[flag].className.replace(reg,"")
				}
			}
			starlist[i].onmouseout = function(){
				if (flag< 999){
					starlist[flag].className = starlist[flag].className + nowClass;
				}
			}
		};
		if (isIE){ //FIX IEÏÂÑùÊ½´íÎó
			var li = document.getElementById(star).getElementsByTagName('li');
			for (var i = 0, len = li.length; i < len; i++) {
				var c = li[i];
				if (c) {
					c.className = c.getElementsByTagName('a')[0].className;
				}
			}
		}
	},
	//ÉèÖÃÄ¬ÈÏÊôÐÔ
	SetOptions: function(options) {
		this.options = {//Ä¬ÈÏÖµ
			Input:			"",//ÉèÖÃ´¥±£´æ·ÖÊýµÄINPUT
			Tips:			"",//ÉèÖÃÌáÊ¾ÎÄ°¸ÈÝÆ÷
			nowClass:	"current-rating",//Ñ¡ÖÐµÄÑùÊ½Ãû
			tipsTxt:		["1·Ö-ÑÏÖØ²»ºÏ¸ñ","2·Ö-²»ºÏ¸ñ","3·Ö-ºÏ¸ñ","4·Ö-ÓÅÐã","5·Ö-ÍêÃÀ"]//ÌáÊ¾ÎÄ°¸
		};
		Extend(this.options, options || {});
	}
}

/* For TEST */

var Stars1 = new Stars("stars1",{nowClass:"current-rating",tipsTxt:["100·Ö-ÑÏÖØ²»ºÏ¸ñ","200·Ö-²»ºÏ¸ñ","300·Ö-ºÏ¸ñ","400·Ö-ÓÅÐã","500·Ö-ÍêÃÀ"]})
var Stars2 = new Stars("stars2")

