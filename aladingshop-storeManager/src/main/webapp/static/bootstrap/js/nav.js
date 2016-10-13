var nav = ''+
'<nav class="navbar navbar-default">'+
    '<div class="container-fluid">'+
        '<div class="navbar-header">'+
            '<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-navbar-collapse">'+
            '<span class="sr-only">显示/隐藏导航</span>'+
            '<span class="icon-bar"></span>'+
            '<span class="icon-bar"></span>'+
            '<span class="icon-bar"></span>'+
            '</button>'+
            '<a class="navbar-brand" href="index.html" title="首页"><i class="fa fa-medium text-info"></i> ALADing CMS</a>'+
        '</div>'+
        '<div class="collapse navbar-collapse" id="bs-navbar-collapse">'+
            '<ul class="nav navbar-nav navbar-right">'+
                '<li><a href="javascript:void(0);" class="cursorD"><i class="fa fa-user"></i> admin</a></li>'+
                '<li><a href="#" title="退出登录" id="signOut"><i class="fa fa-sign-out"></i> sign out</a></li>'+
            '</ul>'+
        '</div>'+
    '</div>'+
'</nav>';

document.write(nav);
