(function($) {
  var link;
  var prev_link;
  var this_item;

  // 向左滑动，item添加标志类名,向左侧滑
  $('.list_view_item').swipeLeft(function() {
    this_item = $(this);//改写全局变量this_item为当前侧滑item
    prev_link = link;//保存上一个侧滑item的链接
    link = $(this).parent().attr('href');//获取当前侧滑item的链接
    // 如果页面存在已经侧滑item，恢复前一个侧滑item的链接
    if ($('.current_on').length > 0) {
      $('.current_on').parent().attr('href',prev_link);
    }
    $(this).addClass('current_on');// 当前item添加标志类名，侧滑
    $(this).parent().parent().siblings().children('a').children('.list_view_item').removeClass('current_on');// 其他item移除标志类名,恢复原位
    disable_link(1, this_item);//禁用链接
  });

  // 向右滑动，item移除标志类名,恢复原位,恢复链接
  $('.list_view_item').swipeRight(function() {
    $(this).removeClass('current_on');
    disable_link(0, this_item);
  });

  // 点击，item移除标志类名,恢复原位
  $('.list_view_item').tap(function() {
    $(this).removeClass('current_on');
    // 延迟恢复链接
    setTimeout(function(){
      disable_link(0, this_item);
    },500);
  });

  function disable_link(i, this_item) {
    if (i == 1) {
      // 禁用链接
      this_item.parent().attr('href', 'javascript:void(0);');
    } else {
      // 恢复链接
      this_item.parent().attr('href', link);
    }
  }
})(Zepto);