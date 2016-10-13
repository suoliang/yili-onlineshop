/** 用户退出登录时，执行此函数*/
function exitFun(){
	window.location.href=contextPath+"/cart/list.html";
}

/** 修改购物车选中状态*/
function modifySelected(skuCode,obj){
	
	var isSelected="n";
	if(obj.checked==true){
		isSelected = "y";
	}
	$.post(contextPath+"/cart/modifyItemSelected",{
		skuCodes : skuCode,
		isSelected : isSelected,
		time : new Date().getTime()},
		function(data){
			if(data.responseCode==0){
				window.location.reload();
			}else{
				openAddCartModal('0',data.msg);
			}
			
	   });//end post request
}

function modifyAllSelected(obj){
	
	var chks = $('.checkAll');
	
	var len = chks.length;
	if(len==null||len==""||len<=0){
		alert("请至少选中一件商品");
		return;
	}
	var codes=[];
	for(i=0;i<len;i++){
		codes.push(chks[i].value);
	}
	
	var isSelected=obj.value;
	if(obj.value=="y"){
		isSelected = "n";
	}else{
		isSelected = "y";
	}
	$.post(contextPath+"/cart/modifyItemSelected",{
		skuCodes : codes.join(","),
		isSelected : isSelected,
		time : new Date().getTime()},
		function(data){
			if(data.responseCode==0){
				window.location.reload();
			}else{
				openAddCartModal('0',data.msg);
			}
			
	   });//end post request
	
}



/** 添加商品到购物车*/
function addToCart(skuCode,quantity){
	if(quantity==undefined){
		var t = parseInt($("#cart-num").val());
		quantity = ("" == $("#cart-num").val() || undefined==$("#cart-num").val() || isNaN(parseInt($("#cart-num").val()))) ? 1 : (1>t)?1:(t>999)?999:t;
	}
	$.post(contextPath+"/cart/addToCart",{
		skuCode : skuCode,
		quantity : quantity,
		time : new Date().getTime()},
		function(data){
			if(data.responseCode==0){
				openAddCartModal('1',"成功加入购物车");
				//修改购物车前端显示数量
				$("#mini-cart .cart i").html(data.data.pnum);
				miniCart();//重新加载mini购物车下拉列表
			}else{
				openAddCartModal('0',data.msg);
			}
			
	   });//end post request
}

/** 刷新MINI购物车*/
function miniCart(){
	var url = contextPath + "/cart/miniCart";
	$.ajax({
		type : "POST",
		url : url,
		data : "time="+new Date().getTime(),
		async : false,// 同步请求,本次请求完成后,后边的代码才会执行
		dataType : "json",
		success : function(data) {
			if(data.responseCode==0){
				var pnum = data.data.pnumTotal;
				
				if(pnum == null || pnum == NaN || pnum == undefined){
					pnum = 0;
				}
				var price = data.data.priceTotal;
				if(price == null || price == NaN || price == undefined){
					price = 0;
				}
				$("#mini-cart .cart i").html(pnum);
				var items = data.data.items;
				var len = items.length;
				var itemsHtmls = "";
				for(var i = 0 ; i < len;i++){
					var item =items[i];
					
					var strItem='<li><a href="'+contextPath+'/sku/skuDetail?skuCode='+item.skuCode+'"><img src="'+item.imgPath+'">'+
	            		'<p>'+item.name+'</p></a>'+
	            		'<span>&yen; '+item.price+'</span>'+
	            		'<a class="delete" href="javascript:void(0)" onclick="removeMiniItem(\''+item.skuCode+'\')">删除</a>'+
	            		'</li>';
					itemsHtmls+=strItem;
				}
				$("#mini-cart").hover(function(){
					if(len<1 && data.data.loginStatus=="y"){
						$("#mini-cart .cart-none").show();
						$("#mini-cart .cart-list").hide();
						$("#mini-cart .cart-unlogin").hide();
					}else if(len<1 && data.data.loginStatus=="n"){
						$("#mini-cart .cart-none").hide();
						$("#mini-cart .cart-list").hide();
						$("#mini-cart .cart-unlogin").show();
					}else{
						$("#mini-cart .cart-none").hide();
						$("#mini-cart .cart-list").show();
						$("#mini-cart .cart-unlogin").hide();
					}
					
				},function(){
					$("#mini-cart .cart-none").hide();
					$("#mini-cart .cart-list").hide();
					$("#mini-cart .cart-unlogin").hide();
				});
				
				var p =  '<span>共 '+pnum+'件</span>'+
			             '<a href="'+contextPath+'/cart/list.html?time='+new Date().getTime()+'">去结算</a>';
				$("#mini-cart .cart-list ul").html(itemsHtmls);
				$("#mini-cart .cart-list #cartPnum").html(p);
			}
		}
	});
};

/** MINI购物车删除商品*/
function removeMiniItem(skuCode){
	var url = contextPath + "/cart/removeBath";
	$.post(url,{
		skuCodes : skuCode,
		time : new Date().getTime()},
		function(data){
			if(data.responseCode==0){
				miniCart();
			}else{
				alert(data.msg);
			}
		});
}

/** 购物车中删除单个商品*/
function removeItem(skuCode){
	 var modal = '<div class="modal_backup" id="modalPublic"><div class="Modal modal-body error"><h3>删除</h3><div class="modal-body-wrap"><span></span><p>' + '确认移除该商品吗？' + '</p></div><button class="yes">确定</button><button class="no">取消</button></div></div>';

     $('body').append(modal);
     $('#modalPublic').fadeIn(400);
     $('.modal-body').css('visibility', 'visible').stop().animate({'opacity':'1','top':'40%'}, 450);
     $('#modalPublic').find('.no').bind('click', function(event) {
         $('#modalPublic').fadeOut(300);
         $('.modal-body').stop().animate({'opacity':'0','top':'20%'}, 400);
         setTimeout(function(){
             $('body').find('#modalPublic').remove();
         },600);
     });
     
     $('#modalPublic').find('.yes').bind('click', function(event) {	
		removeCartSku(skuCode);
     });
}
/** 请求后台移除购物车商品*/
function removeCartSku(skuCode){
	var url = contextPath + "/cart/removeBath";
	$.post(url,{
		skuCodes : skuCode,
		time : new Date().getTime()},
		function(data){
			if(data.responseCode==0){
				window.location.reload();
			}else{
				alert(data.msg);
			}
	});
	
}
/** 删除选中的商品*/
function removeSelect(){
	var chks = $('.checkAll[checked=checked]');
	
	var len = chks.length;
	if(len==null||len==""||len<=0){
		alert("请至少选中一件商品");
		return;
	}
	var codes=[];
	for(i=0;i<len;i++){
		codes.push(chks[i].value);
	}
	//if(confirm("删除选中商品？","删除")){
		
	//}
	 var modal = '<div class="modal_backup" id="modalPublic"><div class="Modal modal-body error"><h3>删除</h3><div class="modal-body-wrap"><span></span><p>' + '确认移除该商品吗？' + '</p></div><button class="yes">确定</button><button class="no">取消</button></div></div>';

      $('body').append(modal);
      $('#modalPublic').fadeIn(400);
      $('.modal-body').css('visibility', 'visible').stop().animate({'opacity':'1','top':'40%'}, 450);
      $('#modalPublic').find('.no').bind('click', function(event) {
          $('#modalPublic').fadeOut(300);
          $('.modal-body').stop().animate({'opacity':'0','top':'20%'}, 400);
          setTimeout(function(){
              $('body').find('#modalPublic').remove();
          },600);
      });
      
      $('#modalPublic').find('.yes').bind('click', function(event) {	
    	  
    	  removeCartSku(codes.join(","));
    	  
      });
		
		
}

/**  从购物车中移入收藏夹*/
function moveFaorite(skuCode){
	
	  var modal = '<div class="modal_backup" id="modalPublic"><div class="Modal modal-body error"><h3>收藏夹</h3><div class="modal-body-wrap"><span></span><p>' + '移到收藏夹后商品将不在购物车中？' + '</p></div><button class="yes">确定</button><button class="no">取消</button></div></div>';

      $('body').append(modal);
      $('#modalPublic').fadeIn(400);
      $('.modal-body').css('visibility', 'visible').stop().animate({'opacity':'1','top':'40%'}, 450);
      $('#modalPublic').find('.no').bind('click', function(event) {
          $('#modalPublic').fadeOut(300);
          $('.modal-body').stop().animate({'opacity':'0','top':'20%'}, 400);
          setTimeout(function(){
              $('body').find('#modalPublic').remove();
          },600);
      });
      
      $('#modalPublic').find('.yes').bind('click', function(event) {
          /*确定操作*/
    	/*  if(confirm("移到收藏夹后选中商品将不在购物车中？","收藏夹"){	*/
    			 $.post(contextPath+"/sku/collect",{skuCodes:skuCode,type:0,time : new Date().getTime()},function(result){
    				  if(result.responseCode==201){
    			         	if(confirm("当前用户未登录，是否立即登录？")){
    			         		$('#loginBox').fadeIn();
    							$('#loginBox').find('.login-left').animate({'margin-left': '-194px', 'opacity': '1'}, 300);
    							$('#opeation').val(".favorite");
    			         	}
    			         	return;
    			         }else if(result.responseCode==500){
    			         	alert("移入收藏夹失败");
    			         	return;
    			         }else if(result.responseCode==0){
    			        	 removeCartSku(skuCode);
    			         }
    				  	
    			});
    		  /*}*/
      });
	
	
	
	
	

}
/** 从购物车中批量移入收藏*/
function faoriteSelected(){
	
	var chks = $('.checkAll[checked=checked]');
	
	var len = chks.length;
	if(len==null||len==""||len<=0){
		alert("请至少选中一件商品");
		return;
	}
	var codes=[];
	for(i=0;i<len;i++){
		codes.push(chks[i].value);
		
	}
	moveFaorite(codes.join(","));
}
