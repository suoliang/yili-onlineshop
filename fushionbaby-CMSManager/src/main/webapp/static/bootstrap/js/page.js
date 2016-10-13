var pageUtil={};
//分页地址
pageUtil.targetUrl='';
pageUtil.formObj ;

pageUtil.init=function(targetUrl,formObj){
	pageUtil.targetUrl=targetUrl;
	pageUtil.formObj = formObj ;
	/*回车绑定*/
    $("#page_currentPage,#page_limit").keypress(function(event){
    	if (event.keyCode == '13') {
	    	pageUtil.goPage();
  		}
    });
};
//数据同步
pageUtil.synchro=function(){
	$("form.page input[name='currentPage']").val($("#page_currentPage").val());
	$("form.page input[name='limit']").val($("#page_limit").val());
};
pageUtil.binding=function(){	
	$("#page_currentPage").val($("form.page input[name='currentPage']").val());
	$("#page_limit").val($("form.page input[name='limit']").val());
};

pageUtil.preCheck=function(){
	return true;
};
pageUtil.goFirst=function(){
    		var target=$("form.page input[name='currentPage']");
    		target.val(1);
    		pageUtil.binding();
    		pageUtil.goPage();
};
    	
pageUtil.goLast=function(){
    var target=$("form.page input[name='currentPage']");
    var totalPage=$("form.page input[name='totalPage']").val();
  
    target.val(totalPage);
    
    pageUtil.binding();
    pageUtil.goPage();
};
    	
pageUtil.goPrev=function(){
    var target=$("form.page input[name='currentPage']");
	target.val(Number(target.val())-1);
	if(target.val() < 1 ){
		target.val(1);
	}
	pageUtil.binding();
    pageUtil.goPage();
};
    	
pageUtil.goNext=function(){

    var target=$("form.page input[name='currentPage']");
    target.val(Number(target.val())+1);
    var totalPage =  $("form.page input[name='totalPage']").val();
    if(totalPage < Number(target.val()))
    {
    	 target.val(totalPage);
    }
    pageUtil.binding();
    pageUtil.goPage();
};
    	
pageUtil.goTo=function(index){
    var target=$("form.page input[name='currentPage']");
    target.val(index);
    pageUtil.goPage();
};
    	
pageUtil.goPage=function(){
	if(!pageUtil.preCheck()){ return;}
	pageUtil.synchro();

	pageUtil.formObj.submit();
//    var params='a='+new Date().getTime();
//    $("form.page").each(function(){
//			params+="&"+$(this).serialize();    		
//    });
    //alert(params+"##########");
   //params=encodeURI(params);    
  
 //   alert(pageUtil.targetUrl+"?"+params+"11111");
  //  window.location=pageUtil.targetUrl+"?"+params;
   // alert(pageUtil.targetUrl+"?"+params);
    //alert($("form.page input[name='page.currentPage']").val());
};
