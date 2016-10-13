
/**
 *		图片轮播效果
 *		@ left 			: 		下面左边按钮
 *		@ right    		:		下面右边按钮
  *		@ uleft 		: 		上面面左边按钮
 *		@ uright    	:		上面右边按钮
 *		@ img 			:		大图image标签
 *		@ con	  		:		列表标签
 *		@ children 		:		列表下面tagName
 *		@ timer 		:		动画完成时间
 *		@ site 			:		默认显示在哪个位置
 *		@ opacity 		:		图片默认透明度
 *		@ width 		:		列表父容器宽度
 *		@ attr 			:		存图片正确路径的属性名，默认dsrc
 *		@ nav 			: 		焦点图(.ul li)
 *		@ navEvent 		: 		焦点图是否加click事件，默认为false
 *		@ addClass 		: 		焦点图添加的class
 *		@ callBack 		: 		下面轮播回调
 *		@ uCallBack 	: 		上面轮播回调
 *		@ intervalTime 	: 		设置定时器时间ms
 *		@ autoPlay 		: 		是否自动滚动，默认为true
 *		@ stopBtn 		：		暂停按钮
 * 		@ stopBtnClass 	: 		暂停按钮新增样式
 * 		@ direction 	: 		暂时可传入center，待以后扩充
 * 		@ isMove 		: 		高亮位置是否跟着移动，默认为true
 * 		@ skip 			: 		是否是跳转过来的，默认为false
 * 		@ moveNum		: 		需要移动个数，暂时最多只支持两个，待改进
 *		调用方式		：
 *							$('#iSlide1').iSlide({
 *								img:'#img'
 *								, con: '.con'
 *								, children: 'li'
 *								, num: 2
 *								, site: 15
 *								, opacity: 0.7
 *								, width: 500
 *								, intervalTime:3000
 *								, navEvent:true
 *							});
 *
 *							$('#iSlide2').iSlide({
 *								con: '.con'
 *								, children:'li'
 *								, num:2
 *								, nav:'.nav li'
 *								, navEvent: true
 *								, addClass:'cur'
							});
 */

(function($){

	$.fn.iSlide = function(options){
		var self = $(this)
			, config = {
				left: '.left'
				, right: '.right'
				, uLeft:'.left'
				, uRight:'.right'
				, img: null
				, con: null
				, children:'li'
				, timer: 500
				, site: 1
				, opacity: 1
				, width: 0
				, attr: 'dsrc'
				, nav: null
				, navEvent: false
				, addClass: null
				, callBack: null
				, uCallBack:null
				, intervalTime: 5000
				, autoPlay: true
				, stopBtn:null
				, stopBtnClass:null
				, direction:null
				, isMove:true
				, skip:false
				, moveNum:1
				//****************
				, opacityImg:'img'
			}

		$.extend(config, options);

		var img = config.img
			, con = config.con
			, chiLi = config.children
			, num = config.num
			, timer = config.timer
			, site = config.site
			, opacity = config.opacity
			, width = config.width
			, attrName = config.attr
			, addClass = 'iSlide'
			, nav = config.nav
			, callBack = config.callBack
			, dataTimer = timer
			, uCallBack = config.uCallBack
			, opacityImg = config.opacityImg
			, stopBtn = config.stopBtn
			, stopBtnClass = config.stopBtnClass
			, direction = config.direction
			, isMove = config.isMove
			, skip = config.skip
			, moveNum = config.moveNum <= 0 ? 1 : config.moveNum;

		if(!con) return false;
		var _con = con.split(',');
		con = $(_con[0], self);		

		if(!img){
			self.append('<img src=\"\" id="iSlideHidden" style="display:none;" />');
			img = $('#iSlideHidden', self);
		}else{
			img = $(img, self);
		}
		
		$(chiLi, con).eq(0).addClass(addClass);

		var conHtml = con.html()
			, li = $(chiLi, con)
			, liW = li.outerWidth()
			, cl = li.length
			, isEnd = true
			, isGo = true;

		var $left = $(config.left, self)
			, $right = $(config.right, self)
			, $uLeft = $(config.uLeft, self)
			, $uRight = $(config.uRight, self)
			, center = Math.ceil(width/liW/2)
			, diff = (center - 1)*liW
			, dist = center*liW
			, opaIndex = 1;

		con.prepend(conHtml);
		con.append(conHtml);		
		
		li = $(con, self).find(chiLi);

		$.uStartInit = function(){
			uCon.css('marginLeft', uStartM);
			uCurNum = uCl;
			uIsEnd = true;
		}
		var uStartM = 0, uCurNum = 0, uIsEnd = true, uCon = null;
		if(_con[1] != undefined){
			uCon = $(_con[1], self);
			$(chiLi, uCon).eq(0).addClass(addClass);
			var uConHtml = uCon.html()
				, uLi = $(chiLi, uCon)
				, uLiW = uLi.outerWidth()
				, uCl = uLi.length;
			uCurNum = uCl;
			uCon.prepend(uConHtml);
			uCon.append(uConHtml);
			uStartM = ~(uCl * uLiW) + 1;
			$.uStartInit();
		}

		var startM = ~(cl * liW) + 1, curNum = cl;
		var isFirst = null;
		
		$.init = function(init){
			//页面加载时调用
			var newNum = Math.abs(curNum);
			if(init=='init'){
				isFirst = 'init';
				con.css('marginLeft', startM);
				isEnd = false;
				curNum = site%cl - 2;
				++curNum;
				var curM = ~(curNum * liW) + 1 + startM;
				$.setImage();
				if(direction && direction == 'center'){
					curM += diff;
					if(skip) curM += liW;
					con.css('marginLeft', curM);
				}else if(skip){
					curM += diff + liW;
					con.css('marginLeft', curM);
				}
				$.animate(curM);
			}else if(newNum >= cl){
				con.css('marginLeft', nav ? startM : startM + diff - newNum%cl*liW);
				curNum = curNum%cl;
			}
			nav && $.addNav(curNum);
			callBack && $.isFunction(callBack) && callBack(curNum<0?cl+curNum:curNum);
		}
		
		$.addNav = function(cur){
			nav = $(nav);
			var _class = config.addClass || '';
			nav.removeClass(_class);
			nav.eq(cur).addClass(_class);
		}
		
		$.refresh = function(){
			$.init();
			isEnd = true;
			opaIndex = 1;
			$.setImage();
			isGo = true;
		}
		
		$._proxy = function(curM, click){
			var oldM = parseInt(con.css('marginLeft'))
				, curM = parseInt(curM)
				, mc = Math.abs(oldM - curM);
			if(click){
				curM = curM + diff;
				if(click == 'left'){
					$.animate(curM, click);
					return;
				}
			}

			if(mc>=dist){
				opaIndex = opaIndex >= center ? $('img:opacity', li).eq(1).parent().index()%cl - center : opaIndex;
				con.css('marginLeft', curM + opaIndex*liW);
				$.animate(curM);
			}else{
				$.refresh();
			}
		}
		
		//$._setOpacity = function(){}

		$.setOpacity = function(val){
			var _src = img.attr('src');

			$('img:first', li).css('opacity', 1);
			$('img:last', li).css('opacity', opacity);
			
			var $img = $(opacityImg+'['+(val ? 'src' : attrName)+'="'+_src+'"]').parents(chiLi);
			$('img:last', $img).css('opacity', 1);
			
		}
		$.setImage = function(){
			var curImg = $('img', li.eq(curNum));
			var src = curImg.attr(attrName)
				, val = src == undefined;
			src = val ? curImg.attr('src') : src;
			img.attr('src', src);
			if(!isFirst && !isMove) return;
			$.setOpacity(val);
		}
		
		$.animate = function(curM, click){
			if(curM >= startM && click && click == 'left'){
				curM = curM + startM - Math.abs(opaIndex)*liW;
				if(curM<=startM*2){
					curM = curM - startM;
				}
				if(Math.abs(curNum)>=cl+1){
					curNum = cl - Math.abs(curNum)%cl;
					curM = curM + startM;
				}
				con.css('marginLeft', curM);
				curM = curM + Math.abs(opaIndex)*liW;

				if(curM - parseInt(con.css('marginLeft')) > diff){
					con.css('marginLeft', curM + diff);
					curM = curM;
				}				
			}
			var Ml = parseInt(con.css('marginLeft'));
			var _Ml = curM - Ml;
			if(Ml > 0 && _Ml > diff){
				con.css('marginLeft', curM - diff);
			}else if(_Ml < 0 && Ml - curM > Math.abs(startM)){
				con.css('marginLeft', curM + diff);
			}
			con.animate({
				marginLeft: curM
			}, {
				queue: false
				, duration: timer
				, complete: function(){
					$.refresh();					
				}
			});
		}
		$.uAnimate = function(curM, der){
			uCon.animate({
				marginLeft: curM
			}, {
				queue: false
				, duration: timer
				, complete: function(){
						if(der == 'left' && curM == 0) $.uStartInit();
						else if(der == 'right' && curM == uStartM*2) $.uStartInit();
						else uIsEnd = true;
					uCallBack && $.isFunction(uCallBack) && uCallBack(uCurNum%uCl);
				}
			});
		}
		$.extend($.expr[':'], {
	        opacity:function(elem){
	        	return $(elem).css('opacity') == 1;
	        }
	    });

		$.init('init');
		var timmer = null;
		
		$left.click(function(){
			if(!isEnd) return false;
			isEnd = false;
			isFirst = null;
			opaIndex = -moveNum;
			for(var i = 0;i<moveNum;i++){
				--curNum;
			}
			var curM = ~(curNum * liW) + 1 + startM;
			nav ? $.animate(curM) : $._proxy(curM, 'left');
			return false;
		});

		$right.click(function(){
			if(!isEnd) return false;
			isEnd = false;
			isFirst = null;
			opaIndex = moveNum;
			for(var i = 0;i<moveNum;i++){
				++curNum;
			}
			var curM = ~(curNum * liW) + 1 + startM;
			nav ? $.animate(curM) : $._proxy(curM, 'right');
			
			return false;
		});
		
		$uLeft.click(function(){			
			if(!uIsEnd) return false;
			uIsEnd = false;
			isFirst = null;
			--uCurNum;
			var curM = ~(uCurNum * uLiW) + 1;
			$.uAnimate(curM, 'left');
			return false;
		});

		$uRight.click(function(){
			if(!uIsEnd) return false;
			uIsEnd = false;
			isFirst = null;
			++uCurNum;
			var curM = ~(uCurNum * uLiW) + 1;
			$.uAnimate(curM, 'right');
			return false;
		});

		if(config.navEvent){
			if(nav){
				nav.click(function(){
					var self = $(this)
						, index = self.index();
					isFirst = null;
					curNum = index;
					var curM = ~(curNum * liW) + 1 + startM;
					$.animate(curM);
				});
			}else{
				li.click(function(){
					if(!isEnd) return false;
					isEnd = false;
					isFirst = null;
					var self = $(this)
						, index = self.index()
						, newMl = -index*liW + diff
						, oldMl = parseInt(con.css('marginLeft'));
					var imgOpaIndex = $('img:opacity', li).eq(1).parent().index();
						opaIndex = index - imgOpaIndex;

					if(opaIndex != 0){
						var curM = 0;
						if(opaIndex > 0){
							for(var i=0;i<opaIndex;i++){
								++curNum;
								curM = ~(curNum * liW) + 1 + startM;
							}
							$._proxy(curM, 'right');
						}
						else{
							for(var i=0;i<Math.abs(opaIndex);i++){
								--curNum;
								curM = ~(curNum * liW) + 1 + startM;
							}
							$._proxy(curM, 'left');
						}
					}else{
						isEnd = true;
					}
				});
			}
		}

		//是否自动播放
		var autoPlay = function(right){
			if(!isGo) return;
			isGo = false;
			timmer = setInterval(function(){
				right.click();
			}, config.intervalTime);
		}
		var autoStop = function(){
			window.clearInterval(timmer);
			timmer = null;
		}
		if(config.autoPlay){
			var sz = [], der = null;
			if(_con[1] != undefined){
				der = $uRight;
				sz = [$uLeft, $uRight, uCon, img];
			}else{
				der = $right;
				sz = [$left, $right, con, img];
			}
			autoPlay(der);
			$.each(sz, function(i, j){
				j.hover(function(){
					autoStop();
				}, function(){
					isGo = true;
					!$(stopBtn, self).hasClass(stopBtnClass) && autoPlay(der);
				});
			});
		}
		
		if(stopBtn){
			$(stopBtn, self).click(function(){
				var btnSelf = $(this);
				//暂停
				if(!btnSelf.hasClass(stopBtnClass)){
					//var video_=$("#video-con_").html();			//获取视频播放的内容	
					//if(video_==''){	
					//	$("#video-con_").html(video_content);
					//}
					autoStop();
					btnSelf.addClass(stopBtnClass);
				}else{
					//video_content=$("#video-con_").html();			//获取视频播放的内容
					//$("#video-con_").empty();
					autoPlay($uRight.length==0?$right:$uRight);
					btnSelf.removeClass(stopBtnClass);
				}
			});
		}

	}

})(jQuery);

var getURLParameter = function(key,frame){
    var param = (frame || window).location.search,reg = new RegExp("[&\?]+"+key+"=([^&]*)"),str = "";
    if(reg.test(param))str = RegExp.$1;
    return str;
}