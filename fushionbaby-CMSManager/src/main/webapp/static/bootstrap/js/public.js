/**
 * public.js
 */

$(function() {

    var win_H = $(window).height();
    var win_W = $(window).width();

    /* 自定义checkbox与radio样式 */
    if ($('.iCheck-futurico').length) {
        $('.iCheck-futurico').iCheck({
            checkboxClass: 'icheckbox_futurico',
            radioClass: 'iradio_futurico'
        });
    };
    if ($('.iCheck-square').length) {
        $('.iCheck-square').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue'
        });
    };

    /* 设置菜单下子菜单个数 */
    $('#leftMenu').find('.panel').each(function() {
        var list_length = $(this).find('.list-group-item').length;
        $(this).find('.badge').html(list_length);
    });

    /* 表格全选 */
    // if ($('#checkAllBtn').length) {
    //     var checkAllBtn = document.getElementById('checkAllBtn');
    //     var checkAll = document.getElementsByName("checkAll");
    //     checkAllBtn.onclick = function() {
    //         if (checkAllBtn.checked) {
    //             for (var i = 0; i < checkAll.length; i++) {
    //                 checkAll[i].checked = true;
    //             }
    //         } else {
    //             for (var i = 0; i < checkAll.length; i++) {
    //                 checkAll[i].checked = false;
    //             }
    //         }
    //     };
    // };
    $("#checkAllBtn").click(function(){
        if($(this).attr('checked') == "checked"){
            $("[name='checkAll']").attr("checked",'true');//全选
        } else {
            $("[name='checkAll']").removeAttr("checked");//取消全选
        }
    });

    /*复选框选中不选中事件*/
    $(".checkCurrentBtn").each(function(){
        $(this).click(function(){
            if($(this).attr('checked') == "checked"){
                $(this).attr('checked','checked');
            } else {
                $(this).removeAttr("checked");
            }
        });
    });

    /* 时间选择器 */
    if ($('.form_datetime').length) {
        $(".form_datetime").not(".form_datetime_0").datetimepicker({
            format: "yyyy-mm-dd",
            autoclose: true,
            /*选择完自动关闭弹窗*/
            todayBtn: true,
            /*显示今日按钮*/
            pickerPosition: "bottom-left",
            /*弹窗位置*/
            startDate: "2013-02-14",
            /*开始时间*/
            language: "zh-CN",
            /*中文*/
            minView: "2" /*精确度:天*/
        });
    };

    if ($('.form_datetime_his').length) {
        $(".form_datetime_his").datetimepicker({
            format: "yyyy-mm-dd hh:ii:ss",
            autoclose: true,
            /*选择完自动关闭弹窗*/
            todayBtn: true,
            /*显示今日按钮*/
            pickerPosition: "bottom-left",
            /*弹窗位置*/
            startDate: "2013-02-14",
            /*开始时间*/
            language: "zh-CN",
            /*中文*/
            minView: "0" /*精确度:秒*/
        });
    };
    /*清空已选时间*/
    if ($('.form_datetime_addon').length) {
        $('.form_datetime_addon').click(function(event) {
            $(this).siblings('input').val('');
        });
    };

    $('.timeS').change(function(){
       var timeS_val = $(this).val();
       $('.timeE').datetimepicker('setStartDate', timeS_val);
    });
    $('.timeE').change(function(){
       var timeE_val = $(this).val();
       $('.timeS').datetimepicker('setEndDate', timeE_val);
    });

    /*调整input宽度*/
    if ($('.form-control').length) {
        var input_w = $('.form-control').not('.form_datetime').eq(0).outerWidth();
        if ($('.lg-select').length) {
            $('.lg-select').css('min-width', input_w + 2);
        };
        if ($('.form_datetime') && $(window).width() > 768) {
            $('.form_datetime').css('width', input_w - 35);
        };
    };

    /*调整.col-label宽度*/
    if ($('.col-label').length) {
        var labelArry = [];
        for (var i = 0; i < $('.col-label').length; i++) {
            labelArry.push($('.col-label').eq(i).outerWidth());
        };
        var labelMax = labelArry.reduce(function(x, y) {
            return (x > y) ? x : y
        });
        $('.col-label').width(labelMax);
    };

    /*根据<iframe>的"title"值给左侧对应菜单添加选择状态*/
    var iframeClass = $('#iframepage').attr('title');
    var menuClass = '.' + iframeClass + '-menu';
    $(menuClass).find('.collapse ').addClass('in');
    $(menuClass).find('.panel-heading').removeClass('collapsed');

    /*调整页面高宽*/
    if (win_W >= 978) {
        $('.main-body').css('min-height', win_H+1);
    };
    Adjust_W();
    $(window).resize(function() {
        Adjust_W();
    });

    /*去除表单值前后空格*/
    $('input').each(function() {
        $(this).blur(function(event) {
            var thisValue = $(this).val();
            $(this).val($.trim(thisValue));
        });
    });

})

function Adjust_W(){
    var win_W = $(window).width();
    var win_H = $(window).height();
    var body_H = $('body').height();
    var leftMenu_W = 238;
    var leftMenu_H = $('#leftMenu').outerHeight();
    if (win_W >= 978) {
        $('.main-iframe').css({
            width: win_W - leftMenu_W,
            'min-height': body_H
        });
    } else {
        $('.main-iframe').css({
            width: '100%'
        });
    }
}

function iFrameHeight() {
    var ifm= document.getElementById("iframepage");
    var subWeb = document.frames ? document.frames["iframepage"].document :ifm.contentDocument;
    if(ifm != null && subWeb != null) {
       ifm.height = subWeb.body.scrollHeight;
    }
}
