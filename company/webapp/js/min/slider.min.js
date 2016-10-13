// 首页banner下方slider
;(function($){
	$.fn.baisonSlider = function(o){
		var o = $.extend(
			{
			animation:'slide', //轮显方式，两种：'slide（左右滑动）'和'fade（淡入淡出）'；
			uniform:false,//左右滑动方式，ture为匀速，false为渐变
			s_interval:20,//滑动方式为“渐变”时，每次运动的时间间隔（考虑到动画效果，取值为1-100为宜）
			prev:null, //上一张按钮
			next:null,//下一张按钮
			box:'ul',//滚动元素
			nav:null,//轮播导航，null为自动生成。
			slideSpeed:800,//滑动的速度
			fadeSpeed:600,//淡入淡入出的速度
			auto:true,//是否自动轮显
			interval:6000//定时器时间间隔，
			},
			o||{}
		);
		return this.each(function(){
			var c,cUl,list,cOl,btn_prev,btn_next,width,ID,n=0,nli ='',index;
			c = $(this);
			cUl = $(o.box,c);
			btn_prev = $(o.prev,c);
			btn_next = $(o.next,c)
			ID = new Date().getTime()+Math.random(1,99)
			cUl.attr('id',ID);
			list = cUl.children();
			list.css('float','left');
			width = list.outerWidth();
			len = list.size();
			//轮播导航
			if(o.nav == null){
				for(var i=1;i<=len;i++){
					nli+="<li>"+i+"</li>"
				}
				cOl = document.createElement('ol');
				cOl.className = 'scroll_nav';
				cOl.innerHTML = nli;
				nli = $("li",cOl)
				$(cOl).appendTo(c).find('li:first').addClass("current");
			}else{
				cOl = $(o.nav,c);
				nli = $("li",cOl)
			}
			if(o.animation == 'slide'){
				c.css('position','relative');
				cUl.css({
					'width':width*len*2,
					'overflow':'hidden',
					'marginLeft':0
				}).append(list.clone(true));
				var xpos = parseInt(cUl.css('marginLeft'));
				nli.hover(
					function(){
						if(o.auto){
							clearInterval(c[0].Interval);
						};
						navFun1($(this));

					},
					function(){
						if(o.auto){
							c[0].Interval =  setInterval(nextFun,o.interval);
						}
					}
				);
			}else{
				list.css({
					'width':'100%',
					'margin-right':'-100%'
				}).not(":first").hide();
				nli.hover(
					function(){
						if(o.auto){
							clearInterval(c[0].Interval);
						};
						navFun2($(this));

					},
					function(){
						if(o.auto){
							c[0].Interval =  setInterval(nextFun,o.interval);
						}
					}
				);
			}
			function navFun1(obj){
				index = obj.index();
				obj.addClass('current').siblings().removeClass('current');
				var nxpos1= -index*width-len*width;
				var nxpos2 = -index*width;

				if(n>=len){
					if(!o.uniform){
						moveElement(ID,nxpos1,o.s_interval);
					}else{
						cUl.stop().animate({marginLeft:nxpos1},o.slideSpeed);
					};
					xpos = nxpos1;
					n = index+len;
				}else{
					if(!o.uniform){
						moveElement(ID,nxpos2,o.s_interval);
					}else{
						cUl.stop().animate({marginLeft:nxpos2},o.slideSpeed);
					};
					xpos = nxpos2;
					n = index;
				};
				nxpos1 = null;
				nxpos2 = null;
			};
			function navFun2(obj){
				n = index = obj.index();
				obj.addClass('current').siblings().removeClass('current');
				list.stop(false,true).fadeOut(o.fadeSpeed).eq(index).fadeIn(o.fadeSpeed);
			}
			btn_prev.click(function(){
				if(o.auto){
					clearInterval(c[0].Interval);
					prevFun();
					c[0].Interval =  setInterval(nextFun,o.interval);
				}else{
					prevFun();
				}
			});
			function prevFun(){
				n--;
				var f = -len*width;
				if(o.animation == 'slide'){
					if(xpos == 0){
						cUl.prepend($("list:gt("+(len-1)+")",cUl)).css("marginLeft",f+"px");
						xpos= f;
					} ;
					if(n==-1){
						n = 2*len-1;
					};
					if(!o.uniform){
						moveElement(ID,xpos += width,o.s_interval);
					}else{
						cUl.stop().animate({marginLeft:xpos += width},o.slideSpeed);
					};
				}else{
					list.eq(n+1).stop(false,true).fadeOut(o.fadeSpeed);
					if(n==-1){
						n=len-1;
					};
					list.eq(n).stop(false,true).fadeIn(o.fadeSpeed);
				}
				nli.eq(n%len).addClass('current').siblings().removeClass("current");
				f = null;
			};
			btn_next.click(function(){
				if(o.auto){
					clearInterval(c[0].Interval);
					nextFun();
					c[0].Interval =  setInterval(nextFun,o.interval);
				}else{
					nextFun();
				}
			});
			function nextFun(){
				n ++;
				var g = -(len-1)*width;
				var h = 2*len;
				if(o.animation == 'slide'){
					if(xpos == -(h-1)*width){
						cUl.append($("list:lt("+len+")",cUl)).css("marginLeft",g+"px");
						xpos = g;
					}
					if(!o.uniform){
						moveElement(ID,xpos -= width,o.s_interval);
					}else{
						cUl.stop().animate({marginLeft:xpos -= width},o.slideSpeed);
					};
				}else{
					list.eq(n-1).stop(false,true).fadeOut(o.fadeSpeed);
					if(n==len){
						n=0;
					}
					list.eq(n).stop(false,true).fadeIn(o.fadeSpeed);
				}
				if(n==h){
					n=0;
				}
				nli.eq(n%len).addClass('current').siblings().removeClass("current");
				g = null;
				h =null;
			}
			//定时器
			if(o.auto){
				c[0].Interval =  setInterval(nextFun,o.interval);
			}
		});
	};
})(jQuery);
function moveElement(elementID,gap,s_interval) {
	var elem = document.getElementById(elementID);
	if(elem.movement) {
		clearTimeout(elem.movement);
	}
	var xpos = parseInt(elem.style.marginLeft);
	if(xpos == gap) return true;
	if(xpos > gap) {
		var glist = Math.ceil((xpos-gap)/10);
		xpos = xpos - glist;
	}
	if(xpos < gap) {
		var glist = Math.ceil((gap - xpos)/10);
		xpos = xpos + glist;
	}
	elem.style.marginLeft = xpos + "px";
	var move = "moveElement('"+elementID+"',"+gap+","+s_interval+")";
	elem.movement = setTimeout(move,s_interval);
}