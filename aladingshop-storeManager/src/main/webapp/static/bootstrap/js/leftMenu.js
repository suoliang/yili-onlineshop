$(document).ready(function(){
	$.ajax({
		type:"get",
		url:contextPath+"/user/getAllMenu",
		data:"time="+new Date().getTime(),
		dataType:"text",
		success:function(data){
			 $("#menu").prepend(data);
		}
	});
})

/*
 var leftMenu = ''+
'<div class="col-md-2" id="leftMenu">'+
    '<div class="panel-group" id="accordion">'+
        '<div class="panel panel-default">'+
            '<div class="panel-heading" data-toggle="collapse" data-parent="#accordion" href="#collapseA">'+
                '<h4 class="panel-title">'+
                '<i class="fa fa-cog"></i> 系统管理'+
                '<span class="badge pull-right"></span>'+
                '</h4>'+
            '</div>'+
            '<div id="collapseA" class="panel-collapse collapse in">'+
                '<div class="panel-body">'+
                    '<div class="list-group">'+
                        '<a class="list-group-item" href="index.html">系统角色</a>'+
                        '<a class="list-group-item" href="">系统权限</a>'+
                        '<a class="list-group-item" href="">系统用户</a>'+
                        '<a class="list-group-item" href="">版本管理</a>'+
                    '</div>'+
                '</div>'+
            '</div>'+
        '</div>'+
        '<div class="panel panel-default">'+
            '<div class="panel-heading collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseB">'+
                '<h4 class="panel-title">'+
                '<i class="fa fa-user"></i> 会员管理'+
                '<span class="badge pull-right"></span>'+
                '</h4>'+
            '</div>'+
            '<div id="collapseB" class="panel-collapse collapse">'+
                '<div class="panel-body">'+
                    '<div class="list-group">'+
                        '<a class="list-group-item" href="">会员列表</a>'+
                        '<a class="list-group-item" href="">会员评论管理</a>'+
                        '<a class="list-group-item" href="">会员意见反馈</a>'+
                        '<a class="list-group-item" href="">会员手机列表</a>'+
                        '<a class="list-group-item" href="">短信批量发送</a>'+
                        '<a class="list-group-item" href="">会员地址管理</a>'+
                        '<a class="list-group-item" href="">会员邮箱列表</a>'+
                    '</div>'+
                '</div>'+
            '</div>'+
        '</div>'+
        '<div class="panel panel-default">'+
            '<div class="panel-heading collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseC">'+
                '<h4 class="panel-title">'+
                '<i class="fa fa-database"></i> 商品管理'+
                '<span class="badge pull-right"></span>'+
                '</h4>'+
            '</div>'+
            '<div id="collapseC" class="panel-collapse collapse">'+
                '<div class="panel-body">'+
                    '<div class="list-group">'+
                        '<a class="list-group-item" href="">商品类型管理</a>'+
                        '<a class="list-group-item" href="">商品属性管理</a>'+
                        '<a class="list-group-item" href="">商品库存管理</a>'+
                        '<a class="list-group-item" href="">商品抢购管理</a>'+
                    '</div>'+
                '</div>'+
            '</div>'+
        '</div>'+
        '<div class="panel panel-default">'+
            '<div class="panel-heading collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseD">'+
                '<h4 class="panel-title">'+
                '<i class="fa fa-shopping-cart"></i> 订单管理'+
                '<span class="badge pull-right"></span>'+
                '</h4>'+
            '</div>'+
            '<div id="collapseD" class="panel-collapse collapse">'+
                '<div class="panel-body">'+
                    '<div class="list-group">'+
                        '<a class="list-group-item" href="">订单列表</a>'+
                    '</div>'+
                '</div>'+
            '</div>'+
        '</div>'+
        '<div class="panel panel-default">'+
            '<div class="panel-heading collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseE">'+
                '<h4 class="panel-title">'+
                '<i class="fa fa-diamond "></i> 品牌管理'+
                '<span class="badge pull-right"></span>'+
                '</h4>'+
            '</div>'+
            '<div id="collapseE" class="panel-collapse collapse">'+
                '<div class="panel-body">'+
                    '<div class="list-group">'+
                        '<a class="list-group-item" href="">品牌管理</a>'+
                    '</div>'+
                '</div>'+
            '</div>'+
        '</div>'+
        '<div class="panel panel-default">'+
            '<div class="panel-heading collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseF">'+
                '<h4 class="panel-title">'+
                '<i class="fa fa-flag-o"></i> 广告管理'+
                '<span class="badge pull-right"></span>'+
                '</h4>'+
            '</div>'+
            '<div id="collapseF" class="panel-collapse collapse">'+
                '<div class="panel-body">'+
                    '<div class="list-group">'+
                        '<a class="list-group-item" href="">Banner管理</a>'+
                    '</div>'+
                '</div>'+
            '</div>'+
        '</div>'+
        '<div class="panel panel-default">'+
            '<div class="panel-heading collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseG">'+
                '<h4 class="panel-title">'+
                '<i class="fa fa-mobile size18"></i> 短信管理'+
                '<span class="badge pull-right"></span>'+
                '</h4>'+
            '</div>'+
            '<div id="collapseG" class="panel-collapse collapse">'+
                '<div class="panel-body">'+
                    '<div class="list-group">'+
                        '<a class="list-group-item" href="">类型管理</a>'+
                        '<a class="list-group-item" href="">推送历史</a>'+
                    '</div>'+
                '</div>'+
            '</div>'+
        '</div>'+
        '<div class="panel panel-default">'+
            '<div class="panel-heading collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseH">'+
                '<h4 class="panel-title">'+
                '<i class="fa fa-edit"></i> 文章管理'+
                '<span class="badge pull-right"></span>'+
                '</h4>'+
            '</div>'+
            '<div id="collapseH" class="panel-collapse collapse">'+
                '<div class="panel-body">'+
                    '<div class="list-group">'+
                        '<a class="list-group-item" href="">文章管理</a>'+
                    '</div>'+
                '</div>'+
            '</div>'+
        '</div>'+
        '<div class="panel panel-default">'+
            '<div class="panel-heading collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseI">'+
                '<h4 class="panel-title">'+
                '<i class="fa fa-th"></i> 分类管理'+
                '<span class="badge pull-right"></span>'+
                '</h4>'+
            '</div>'+
            '<div id="collapseI" class="panel-collapse collapse">'+
                '<div class="panel-body">'+
                    '<div class="list-group">'+
                        '<a class="list-group-item" href="">商品分类</a>'+
                    '</div>'+
                '</div>'+
            '</div>'+
        '</div>'+
        '<div class="panel panel-default">'+
            '<div class="panel-heading collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseJ">'+
                '<h4 class="panel-title">'+
                '<i class="fa fa-tags"></i> 标签管理'+
                '<span class="badge pull-right"></span>'+
                '</h4>'+
            '</div>'+
            '<div id="collapseJ" class="panel-collapse collapse">'+
                '<div class="panel-body">'+
                    '<div class="list-group">'+
                        '<a class="list-group-item" href="">商品标签</a>'+
                    '</div>'+
                '</div>'+
            '</div>'+
        '</div>'+
        '<div class="panel panel-default">'+
            '<div class="panel-heading collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseK">'+
                '<h4 class="panel-title">'+
                '<i class="fa fa-gift"></i> 促销管理'+
                '<span class="badge pull-right"></span>'+
                '</h4>'+
            '</div>'+
            '<div id="collapseK" class="panel-collapse collapse">'+
                '<div class="panel-body">'+
                    '<div class="list-group">'+
                        '<a class="list-group-item" href="">商品促销</a>'+
                    '</div>'+
                '</div>'+
            '</div>'+
        '</div>'+
        '<div class="panel panel-default">'+
            '<div class="panel-heading collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseL">'+
                '<h4 class="panel-title">'+
                '<i class="fa fa-credit-card"></i> 代金券管理'+
                '<span class="badge pull-right"></span>'+
                '</h4>'+
            '</div>'+
            '<div id="collapseL" class="panel-collapse collapse">'+
                '<div class="panel-body">'+
                    '<div class="list-group">'+
                        '<a class="list-group-item" href="">代金券类型</a>'+
                        '<a class="list-group-item" href="">生成代金券</a>'+
                    '</div>'+
                '</div>'+
            '</div>'+
        '</div>'+
        '<div class="panel panel-default">'+
            '<div class="panel-heading collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseM">'+
                '<h4 class="panel-title">'+
                '<i class="fa fa-picture-o"></i> 商品图片管理'+
                '<span class="badge pull-right"></span>'+
                '</h4>'+
            '</div>'+
            '<div id="collapseM" class="panel-collapse collapse">'+
                '<div class="panel-body">'+
                    '<div class="list-group">'+
                        '<a class="list-group-item" href="">商品图片</a>'+
                    '</div>'+
                '</div>'+
            '</div>'+
        '</div>'+
        '<div class="panel panel-default">'+
            '<div class="panel-heading collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseN">'+
                '<h4 class="panel-title">'+
                '<i class="fa fa-bar-chart"></i> 统计管理'+
                '<span class="badge pull-right"></span>'+
                '</h4>'+
            '</div>'+
            '<div id="collapseN" class="panel-collapse collapse">'+
                '<div class="panel-body">'+
                    '<div class="list-group">'+
                        '<a class="list-group-item" href="">短信统计</a>'+
                        '<a class="list-group-item" href="">注册统计</a>'+
                        '<a class="list-group-item" href="">销售统计</a>'+
                    '</div>'+
                '</div>'+
            '</div>'+
        '</div>'+
        '<div class="panel panel-default">'+
            '<div class="panel-heading collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseO">'+
                '<h4 class="panel-title">'+
                '<i class="fa fa-info-circle"></i> 消息推送管理'+
                '<span class="badge pull-right"></span>'+
                '</h4>'+
            '</div>'+
            '<div id="collapseO" class="panel-collapse collapse">'+
                '<div class="panel-body">'+
                    '<div class="list-group">'+
                        '<a class="list-group-item" href="">消息推送</a>'+
                        '<a class="list-group-item" href="">推送历史</a>'+
                    '</div>'+
                '</div>'+
            '</div>'+
        '</div>'+
    '</div>'+
    '<!-- /.panel-group -->'+
'</div>';

document.write(leftMenu);
*/


	
