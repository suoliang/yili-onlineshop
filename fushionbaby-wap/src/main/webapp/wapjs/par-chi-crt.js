/** 分页*/
		var curPage = $("#curPage").val();
		function comment(familyId,curPageNew){
			$.ajax({
			   type: "POST",
			   url: _ContextPath+"/parchiart/comment.do",
			   data: "familyId="+familyId+"&cur_page="+curPageNew+"&time="+new Date().getTime(),
			   success: function(msg){
				  	var str = eval(msg);
				  	$("#curPage").val(str.data.curPage);
				  	$("#totalPage").html(str.data.totalPage);
				  	var comments = str.data.commentList;
				  	var commentHtml = "";
				  	for(var i = 0 ; i < comments.length;i++){
				  		var nickName = comments[i].nickName == null ? "" : comments[i].nickName   ;
				  		commentHtml +="<li>"+
										"<p>"+
											"<a href='javascript:'  class='user-name'>#"+nickName+"#</a>"+comments[i].content+
										"</p>"+
										"<p class='par-user-info'><span class='time'><span class='date'>"+comments[i].commentDate+"</span> <span class='hour'>"+comments[i].commentTime+"</span></span><span class='from'>来自于<em>爱兜宝</em></span></p>"+
									"</li>";
				  	}
				  $(".par-chi-main-r ul").html(commentHtml);
				  var totalPage = str.data.totalPage;
				  var pageGoHtml = "";
				  if(curPageNew > limit && totalPage > limit){
					  for(var j = curPageNew ; j < Number(curPageNew)+limit ; j++){
						  	if(j <=totalPage){
						  		if(j == curPageNew ){
						  			pageGoHtml+= "<a href='javascript:' onclick='onPageGo("+j+")' class='page-list page-list-current'>"+j+"</a>";
						  		}else{
						  			pageGoHtml+= "<a href='javascript:' onclick='onPageGo("+j+")' class='page-list'>"+j+"</a>";
						  		}
						  	}
					  }
					}else{
						for(var j = 1 ; j <= limit; j++){
						  	if(j <=totalPage){
						  		if(j == curPageNew ){
						  			pageGoHtml+= "<a href='javascript:' onclick='onPageGo("+j+")' class='page-list page-list-current'>"+j+"</a>";
						  		}else{
						  			pageGoHtml+= "<a href='javascript:' onclick='onPageGo("+j+")' class='page-list'>"+j+"</a>";
						  		}
						  	}
						}
					}
				  $("#pagego").html(pageGoHtml);
			   }
			});
		}
		comment(familyId,curPage);
		
		function onPageGo(pageIndex){
			comment(familyId,pageIndex);
		} 
		function onPageTop(){
			var curP = $("#curPage").val();
			if( (Number(curP) - 1 ) <= 0){
				$("#curPage").val(1);
			}else{
				$("#curPage").val(Number(curP) - 1);
			}
			comment(familyId,$("#curPage").val());
		}
		function onPageNext(){
			var totalP = $("#totalPage").html();
			var curP  = $("#curPage").val();
			if( ( Number(curP) + 1 ) >= totalP){
				$("#curPage").val(totalP);
			}else{
				$("#curPage").val(Number(curP) + 1);
			}
			comment(familyId,$("#curPage").val());
		}
		/** 发表评论*/	
		
		$(".ann-btn button").click(function(){
			var content = $.trim($("#eval-content").val());
			if (content.length == 0) {
				$(".error").show();
				$("#check_content").html("请输入发表的内容");
				return;
			}
			if (content.length > 200) {
				$(".error").show();
				$("#check_content").html("发表的内容请控制在200字以内");
				return;
			}
			var memberId = $("#memberId").val();
			var userName = $("#userName").val();
			
			$.ajax({
				   type: "POST",
				   url: _ContextPath+"/parchiart/addComment.do",
				   data: "familyId="+familyId+"&content="+content+"&userName="+userName+"&memberId="+memberId+"&time="+new Date().getTime(),
				   dataType : "json",
				   success: function(msg){
					   if(msg.responseCode == "0"){
						   comment(familyId, $("#curPage").val());
						   $(".error").hide();
						   $("#eval-content").val();
					   }
				   }
			});
		});
		
		
		