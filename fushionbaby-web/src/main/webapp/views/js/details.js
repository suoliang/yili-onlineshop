window.onload = function() {
	var skuId=$("#skuId").val();
	function showSkuComment(data){
		var memberCommentDtoList=data.data.memberCommentDtoList;
		var rate_grid_ul_li="";
		for(var i=0;i<memberCommentDtoList.length;i++){
			var memberCommentDto=memberCommentDtoList[i];
			var memberPicImgUrl="";
			if(memberCommentDto.memberPicUrl==''||memberCommentDto.memberPicUrl==null){
				memberPicImgUrl='<img src="'+_contentPath+'/views/images/default.png" alt="">';
			}else{
				memberPicImgUrl='<img src="'+memberCommentDto.memberPicUrl+'" alt="">';
			}
			var ava_div='<div class="ava">'+memberPicImgUrl+'</div>';
			var memberName=memberCommentDto.memberName;
			
			if(memberName==null||memberName==""){
				memberName="匿名用户";
			}
			var memberName_p='<p><b>'+memberName+'</b></p>';
			var inner_rate_grid_left_div=ava_div+memberName_p;
			var rate_grid_left_div='<div class="rate_grid_left">'+inner_rate_grid_left_div+'</div>';
			
			var rate_grid_right_div="";
			
			var rate_grid_top_div="";
			var rate_grid_describe_p='<p class="rate_grid_describe">'+memberCommentDto.commentContent+'</p>';
			
			var rate_grid_star='<span class="rate_grid_star"><input type="hidden" value="'+memberCommentDto.score+'"><ul>'+
													'<li class="unstar"></li>'+
													'<li class="unstar"></li>'+
													'<li class="unstar"></li>'+
													'<li class="unstar"></li>'+
													'<li class="unstar"></li>'+
												'</ul></span>';
			var inner_rate_grid_detail_span="";
			if(memberCommentDto.skuColor!=""&&memberCommentDto.skuColor!=null){
				inner_rate_grid_detail_span+='颜色：'+memberCommentDto.skuColor;
			}
			if(memberCommentDto.skuColor!=""&&memberCommentDto.skuColor!=null&&memberCommentDto.skuSize!=""&&memberCommentDto.skuSize!=null){
				inner_rate_grid_detail_span+=' / ';
			}
			if(memberCommentDto.skuSize!=""&&memberCommentDto.skuSize!=null){
				inner_rate_grid_detail_span+='尺寸：'+memberCommentDto.skuSize;
			}
			var rate_grid_detail_span='<span class="rate_grid_detail">'+inner_rate_grid_detail_span+'</span>';
			var rate_grid_time_span='<span class="rate_grid_time">'+memberCommentDto.showTime+'</span>';
			
			var rate_grid_top_r_div='<div class="rate_grid_top_r">'+rate_grid_star+rate_grid_detail_span+rate_grid_time_span+'</div>';
			rate_grid_top_div='<div class="rate_grid_top">'+rate_grid_describe_p+rate_grid_top_r_div+'</div>';
			
			
			var inner_rate_grid_middle_div="";
			var commentPicUrl_img="";
			var memberCommentDtoUrlList=memberCommentDto.urlList;
			if(memberCommentDtoUrlList.length>0){
				for(var k=0;k<memberCommentDtoUrlList.length;k++){
					commentPicUrl_img+='<img src="'+memberCommentDtoUrlList[k]+'" alt="">';
				}
				inner_rate_grid_middle_div='<span>晒图：</span>'+commentPicUrl_img+'</span>';
			}
			var rate_grid_middle_div='<div class="rate_grid_middle">'+inner_rate_grid_middle_div+'</div>';
			
			var rate_grid_bottom_div="";
			var assess_list_wrap_div="";
			var assess_list_ul="";
			var assess_list_ul_li="";
			var assess_list_ul_li_p="";
			var assess_li_r_div="";
			for(var j=0;j<memberCommentDto.commentReplyList.length;j++){
				var commentReply=memberCommentDto.commentReplyList[j];
				var commentReplyMemberName=commentReply.memberName;
				if(commentReplyMemberName==null||commentReplyMemberName==""){
					commentReplyMemberName="匿名用户";
				}
				
				assess_list_ul_li_p='<p>'+commentReply.replyContent+'</p>';
				assess_li_r_div='<div class="assess_li_r"><span class="assess_list_name">'+commentReplyMemberName+'</span>'+'<span class="assess_list_time">'+commentReply.showTime+'</span></div>';
				assess_list_ul_li += '<li>' + assess_list_ul_li_p + assess_li_r_div +'</li>';
			}
			assess_list_ul='<ul class="assess_list">'+assess_list_ul_li+'</ul>';
			assess_list_wrap_div='<div class="assess_list_wrap fl">'+assess_list_ul+'</div>'
			
			var rate_grid_replay_a='<a class="rate_grid_replay" href="javascript:void(0);"><i class="fa fa-pencil"></i><input type="hidden" name="commentId" value="'+memberCommentDto.commentId+'"> 回复 （<b>'+memberCommentDto.replyCount+'</b>）</a>';
			
			var rate_grid_zan_a='<a class="rate_grid_zan" href="javascript:void(0);"><i class="fa fa-star"></i><input type="hidden" name="commentIdPraise" value="'+memberCommentDto.commentId+'"> 赞 （<b>'+memberCommentDto.praiseCount+'</b>）</a>';
			
			
			var replay_textarea_div='<div class="replay_textarea"><textarea name="" id="" cols="30" rows="1"></textarea>'+
									'<a class="replay_btn" href="javascript:void(0);">提交回复</a> <label for=""><input type="checkbox">匿名</label></div>';
			
			rate_grid_bottom_div='<div class="rate_grid_bottom">'+assess_list_wrap_div+rate_grid_replay_a+rate_grid_zan_a+replay_textarea_div+'</div>';
			
			rate_grid_right_div='<div class="rate_grid_right">'+rate_grid_top_div+rate_grid_middle_div+rate_grid_bottom_div+'</div>';
			rate_grid_ul_li+='<li>'+rate_grid_left_div+rate_grid_right_div+'</li>';
			
		}
		var rate_grid_ul='<ul class="rate_grid_ul">'+rate_grid_ul_li+'</ul>';
		
		var commentPageIndex_input='<input type="hidden" id="commentPageIndex" value="'+data.data.curPage+'">';
		
		var inner_public_page_wrap_div="";
		var public_page_count_span='<span class="public_page_count">共 <b>'+data.data.totalPage+'</b> 页</span>';
		var public_page_first_a='<a class="public_page_first" href="javascript:void(0);" title="首页"><i class="fa fa-angle-double-left"></i></a>';
		var public_page_prev_a='<a class="public_page_prev" href="javascript:void(0);" title="上一页"><i class="fa fa-angle-left"></i></a>';
		var page_wrap_div="";
		var page_index_a="";
		if(data.data.curPage!=""&&data.data.curPage!=null){
			for(var m=1;m<=data.data.totalPage;m++){
				if(m==data.data.curPage){
					page_index_a+='<a href="javascript:void(0);" class="page_on page_'+data.data.curPage+'">'+data.data.curPage+'</a>';
				}else{
					page_index_a+='<a href="javascript:void(0);" class="page_'+m+'">'+m+'</a>';
				}
			}
		}else{
			page_index_a+='<a href="javascript:void(0);" class="page_on page_1">1</a>';
			if(data.data.totalPage>1){
				for(var n=2;n<=data.data.totalPage;n++){
					page_index_a+='<a href="javascript:void(0);" class="page_'+n+'">'+n+'</a>';
				}
			}
		}
		page_wrap_div='<div class="page_wrap fl">'+page_index_a+'</div>';
		var public_page_next_a='<a class="public_page_next" href="javascript:void(0);" title="下一页"><i class="fa fa-angle-right"></i></a>';
		var public_page_last_a='<a class="public_page_last" href="javascript:void(0);" title="尾页"><i class="fa fa-angle-double-right"></i></a>';
		if(data.data.totalPage>0){
			inner_public_page_wrap_div=public_page_count_span+public_page_first_a+public_page_prev_a+page_wrap_div+public_page_next_a+public_page_last_a;
		}
		public_page_wrap_div='<div class="public_page_wrap fr">'+public_page_count_span+public_page_first_a+public_page_prev_a+page_wrap_div+public_page_next_a+public_page_last_a+'</div>';
		$("#rate_grid_the").html(rate_grid_ul+commentPageIndex_input+public_page_wrap_div);
	}
	$.ajax({
		type: "POST",
		async: false,
		url: _contentPath + "product/getCommentInfo.do",
		data: "skuId=" + skuId+"&time=" + new Date().getTime(),
		success: function(data) {
			if (data.responseCode == 0) {
				showSkuComment(data);
				addCommentJs();
			}
		}   
	});
	//固定nav
	var H = $(".tabbarbox").outerHeight();

	$(".ggcs").click(function(event) {
		$("html,body").animate({
			'scrollTop': $(".attributes-list").offset().top - H
		}, 1000);
	});
	$(".spxq").click(function(event) {
		$("html,body").animate({
			'scrollTop': $(".description").offset().top - H
		}, 1000);
	});
	$(".pjsd").click(function(event) {
		$("html,body").animate({
			'scrollTop': $(".assessment").offset().top - H + 2
		}, 1000);
	});
	$(".cnxh").click(function(event) {
		$("html,body").animate({
			'scrollTop': $(".favour").offset().top - H
		}, 1000);
	});
	$(".ys").click(function(event) {
		$("html,body").animate({
			'scrollTop': $(".good").offset().top - H
		}, 1000);
	});

	var gao = $("#tabbarbox").offset().top;
	$(window).scroll(function(event) {
		var val = $(document).scrollTop();
		if (val > gao) {
			$("#tabbarbox").css({
				"position": "fixed",
				"top": "0"
			});
			$('body').css({
				'padding-top': '68px'
			});
		}
		if (val <= gao) {
			$("#tabbarbox").css({
				"position": "static"
			});
			$('body').css({
				'padding-top': '0'
			});
		}
	});

	//选择颜色大小
	$(".choose").click(function(event) {
		$(this).toggleClass("cs-current").siblings().removeClass('cs-current');
		
	});

	//关闭红色的框
	//$(".esc").click(function(event) {
	//	$(".csbox").css('border', '1px solid #fff');
	//	$(".esc").hide();
	//});

	//banner图片切换
	$(".bottom li").each(function(index) {
		$(this).mouseover(function(event) {
			$(".top li").eq(index).show().siblings().hide();
		});
		$(this).click(function(event) {
			$(".top li").eq(index).show().siblings().hide();
			$(this).css('border-color', '#BABABA').siblings().css('border-color', '#fff');;
		});
	});

	//关闭弹框
	$(".close").click(function(event) {
		$(".pass-check").hide();
		$(".pass-checked").hide();
	});

	$(".collect-main li:nth-child(4n)").css('margin-right', '0');


	// 详情页图片异步加载
	function loadImage(url, callback) {
		var img = new Image();

		img.src = url;

		if (img.complete) { // 如果图片已经存在于浏览器缓存，直接调用回调函数
			callback.call(img);
			return; // 直接返回，不用再处理onload事件
		}

		img.onload = function() {
			img.onload = null;
			callback.call(img);
		}
	}

	// ----------------  评价晒单  ----------------
	
function addCommentJs(){
	// 调整样式
	$('.rate_grid_top_r').each(function() {
		$(this).css({
			'margin-top': ($(this).siblings('p').height() - 18) / 2 + "px"
		});
	})
	$('.assess_li_r').each(function() {
		$(this).css({
			'margin-top': ($(this).siblings('p').height() - 18) / 2 + "px"
		});
	})
	// 总评分，点亮星星
	var rank_num = parseInt($('.bd-l-title span:first-child').html());
	$('.rank_nav_a').css({
		'left': rank_num / 5 * 625
	});
	$('.star_rank ul li').eq(rank_num - 1).removeClass('unstar').addClass('stared').prevAll().removeClass('unstar').addClass('stared');

	// 列表评分，点亮星星
	$('.rate_grid_star').each(function() {
		var this_rank = $(this).children('input').val();
		$(this).children('ul').children('li').eq(this_rank - 1).removeClass('unstar').addClass('stared').prevAll().removeClass('unstar').addClass('stared');
	})

	// 点击放大图片
	$('.jqthumb').click(function() {
		$(this).toggleClass('jqthumb_open').siblings().removeClass('jqthumb_open');
		if ($('.jqthumb_open').length > 0) {
			$(this).parents('.rate_grid_middle').height(200);
		} else {
			$(this).parents('.rate_grid_middle').animate({
				'height': '60px'
			}, 400);
		}
	})

	// 点赞
	$('.rate_grid_zan').click(function() {

		var commentId = $(this).children('input[name="commentIdPraise"]').val();
		var url = _contentPath + "member/commentPraise.do";
		var $this = $(this);
		if ($this.hasClass('zaned')) {
			$.ajax({
				type: "POST",
				async: false,
				url: url,
				data: "commentId=" + commentId + "&isZaned=y&time=" + new Date().getTime(),
				success: function(data) {
					if (data.responseCode == 0) {
						$this.removeClass('zaned').children('b').html(parseInt($this.children('b').html()) - 1);
					} else if (data.msg == "用户未登录") {
						$("#not_login").show(); //显示请登录
					} else {
						alert("系统错误！");
					}
				} //end success
			}); //end ajax
		} else {
			$.ajax({
				type: "POST",
				async: false,
				url: url,
				data: "commentId=" + commentId + "&isZaned=n&time=" + new Date().getTime(),
				success: function(data) {
					if (data.responseCode == 0) {
						$this.addClass('zaned').children('b').html(parseInt($this.children('b').html()) + 1);
					} else if (data.msg == "用户未登录") {
						$("#not_login").show(); //显示请登录
					} else {
						alert("系统错误！");
					}
				} //end success
			}); //end ajax


		}
	})

	// 自定义滚动条
	for (var i = 0; i <= $('.assess_list_wrap').length - 1; i++) {
		$('.assess_list_wrap').eq(i).perfectScrollbar();
	};
	$('.assess_list_wrap').each(function() {
		if ($(this).children('.assess_list').children().length == 0) {
			$(this).hide();
		};
	})

	// 提交评价板块
	$('.rate_grid_replay').each(function() {
		$(this).children('b').html($(this).siblings('.assess_list_wrap').children('.assess_list').children('li').length);
	})
	$('.rate_grid_replay').click(function() {
		$(this).siblings('.replay_textarea').toggleClass('replay_textarea_open');
	})
	$('.replay_btn').click(function() {
		var nowTime = new Date();
		var nowStr = nowTime.format("yyyy-MM-dd hh:mm:ss");
		var textarea_val = $(this).siblings('textarea').val();
		if (textarea_val == "") {
			alert('请输入您的回复信息！');
		}else if(textarea_val.length>200){
			alert('最多输入200字！');
		} else {
			$(this).parent().siblings('.assess_list_wrap').show();
			var commentId = $(this).parents().siblings('.rate_grid_replay').children('input[name="commentId"]').val();
			var url = _contentPath + "member/commentReplySubmit.do";
			var $this = $(this);
			if ($(this).siblings('label').children('input').is(':checked')) {
				$.ajax({
					type: "POST",
					async: false,
					url: url,
					data: "commentId=" + commentId + "&isAnonymous=y&replyContent=" + textarea_val + "&time=" + new Date().getTime(),
					success: function(data) {
						if (data.responseCode == 0) {
							var $li = '<li><p>' + textarea_val + '</p><div class="assess_li_r"><span class="assess_list_name">' + ' 匿名用户' + '</span><span class="assess_list_time">' + nowStr + '</span></div></li>';
							$this.parent().siblings('.assess_list_wrap').children('ul').append($li);
							var $replay_num = $this.parents().siblings('.rate_grid_replay').children('b');
							$replay_num.html(parseInt($replay_num.html()) + 1);
							$this.siblings('textarea').val('');
							var assess_list_h = $this.parent().siblings('.assess_list_wrap').children('ul').outerHeight();
							$this.parent().siblings('.assess_list_wrap').scrollTop(assess_list_h);
							$this.parents().toggleClass('replay_textarea_open');
						} else if (data.msg == "用户未登录") {
							$("#not_login").show(); //显示请登录
						} else {
							alert("系统错误！");
						}

					}
				});
			} else {
				$.ajax({
					type: "POST",
					async: false,
					url: url,
					data: "commentId=" + commentId + "&isAnonymous=n&replyContent=" + textarea_val + "&time=" + new Date().getTime(),
					success: function(data) {
						if (data.responseCode == 0) {
							var replyNickName=data.data;
							if(replyNickName==null||replyNickName==""){
								replyNickName="匿名用户";
							}
							var $li = '<li><p>' + textarea_val + '</p><div class="assess_li_r"  style="margin-top: 1px;"><span class="assess_list_name">' + replyNickName + '</span><span class="assess_list_time">' + nowStr + '</span></div></li>';
							$this.parent().siblings('.assess_list_wrap').children('ul').append($li);
							var $replay_num = $this.parents().siblings('.rate_grid_replay').children('b');
							$replay_num.html(parseInt($replay_num.html()) + 1);
							$this.siblings('textarea').val('');
							var assess_list_h = $this.parent().siblings('.assess_list_wrap').children('ul').outerHeight();
							$this.parent().siblings('.assess_list_wrap').scrollTop(assess_list_h);
							$this.parents().toggleClass('replay_textarea_open');
						} else if (data.msg == "用户未登录") {
							$("#not_login").show(); //显示请登录层

						} else {
							alert("系统错误！");
						}

					}
				});

			}
		}
	})

	
	
	
	
	
	
	
	// 分页
	var public_page_count = parseInt($('.public_page_count b').html());
	if (public_page_count == 1) {
		$('.public_page_wrap').hide();
	} else if (public_page_count < 4) {
		$('.public_page_first,.public_page_last,.public_page_prev,.public_page_next').hide();
	} else {
		$('.page_wrap a:gt(2)').hide();
	}

	$('.page_wrap a').click(function() {
		$(this).addClass('page_on').siblings().removeClass('page_on');
		
		var url = _contentPath + "product/getCommentInfo.do?skuId=" + $('#skuId').val() + "&commentPageIndex=" + $('.page_on').text();
		$.ajax({
			type: "POST",
			async: false,
			url: url,
			data: "time="+new Date().getTime(),
			success: function(data) {
				showSkuComment(data);
				addCommentJs();
				addLessFourJs();
			}
		});
	})
	$('.public_page_prev').click(function() {
		$('.page_on').prev().addClass('page_on').siblings().removeClass('page_on');
		var url = _contentPath + "product/getCommentInfo.do?skuId=" + $('#skuId').val() + "&commentPageIndex=" + $('.page_on').text();
		$.ajax({
			type: "POST",
			async: false,
			url: url,
			data: "time="+new Date().getTime(),
			success: function(data) {
				showSkuComment(data);
				addCommentJs();
			}
		});
	})
	$('.public_page_next').click(function() {
		$('.page_on').next().addClass('page_on').siblings().removeClass('page_on');
		var url = _contentPath + "product/getCommentInfo.do?skuId=" + $('#skuId').val() + "&commentPageIndex=" + $('.page_on').text();
		$.ajax({
			type: "POST",
			async: false,
			url: url,
			data: "time="+new Date().getTime(),
			success: function(data) {
				showSkuComment(data);
				addCommentJs();
			}
		});
	})
	$('.public_page_first').click(function() {
		$('.page_wrap a:first').addClass('page_on').siblings().removeClass('page_on');
		var url = _contentPath + "product/getCommentInfo.do?skuId=" + $('#skuId').val() + "&commentPageIndex=" + $('.page_on').text();
		$.ajax({
			type: "POST",
			async: false,
			url: url,
			data: "time="+new Date().getTime(),
			success: function(data) {
				showSkuComment(data);
				addCommentJs();
			}
		});
	})
	$('.public_page_last').click(function() {
		$('.page_wrap a:last').addClass('page_on').siblings().removeClass('page_on');
		var url = _contentPath + "product/getCommentInfo.do?skuId=" + $('#skuId').val() + "&commentPageIndex=" + $('.page_on').text();
		$.ajax({
			type: "POST",
			async: false,
			url: url,
			data: "time="+new Date().getTime(),
			success: function(data) {
				showSkuComment(data);
				addCommentJs();
			}
		});
	})
	
	
	
	
	$('.page_wrap a,.public_page_last,.public_page_first,.public_page_prev,.public_page_next').click(function() {
		if (1 < parseInt($('.page_on').html()) && parseInt($('.page_on').html()) < public_page_count) {
			$('.page_wrap a').hide();
			$('.page_on').show().prev().show();
			$('.page_on').next().show();
		} else if (parseInt($('.page_on').html()) == 1) {
			$('.page_wrap a').hide();
			$('.page_wrap a:lt(3)').show();
		} else if (parseInt($('.page_on').html()) == public_page_count) {
			$('.page_wrap a').hide();
			$('.page_wrap a').eq(public_page_count - 3).show().nextAll().show();
		}
	});
	if ($('#commentPageIndex').val() != ""&&$('#commentPageIndex').val()!="null") {
		$("html,body").animate({
			'scrollTop': $(".reviews").offset().top - H + 2
		}, 0);
		$('.pjsd').addClass('selected').siblings().removeClass('selected');
	}
}
	addLessFourJs();
	function addLessFourJs(){	
		if (1 < parseInt($('.page_on').html()) && parseInt($('.page_on').html()) < public_page_count) {
			$('.page_wrap a').hide();
			$('.page_on').show().prev().show();
			$('.page_on').next().show();
		} else if (parseInt($('.page_on').html()) == 1) {
			$('.page_wrap a').hide();
			$('.page_wrap a:lt(3)').show();
		} else if (parseInt($('.page_on').html()) == public_page_count) {
			$('.page_wrap a').hide();
			$('.page_wrap a').eq(public_page_count - 3).show().nextAll().show();
		}
	}
};

// 时间格式转换
Date.prototype.format = function(format) {
	var o = {
		"M+": this.getMonth() + 1, //month
		"d+": this.getDate(), //day
		"h+": this.getHours(), //hour
		"m+": this.getMinutes(), //minute
		"s+": this.getSeconds(), //second
		"q+": Math.floor((this.getMonth() + 3) / 3), //quarter
		"S": this.getMilliseconds() //millisecond
	}

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}

	for (var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}