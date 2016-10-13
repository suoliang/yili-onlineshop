$(window).load(function() {
  // 星星评分
  $('.rank_ul li').hover(function() {
    $(this).addClass('stared').prevAll().addClass('stared');
    $(this).nextAll().removeClass('stared');
  }, function() {
    if ($('.starThis').length > 0) {
      $('.starThis').addClass('stared').prevAll().addClass('stared');
      $('.starThis').nextAll().removeClass('stared');
    } else {
      $('.rank_ul li').removeClass('stared');
    }
  });
  $('.rank_ul li').click(function() {
    $(this).addClass('starThis stared').prevAll().addClass('stared');
    $(this).siblings().removeClass('starThis');
    $(this).nextAll().removeClass('stared');
    $('#envaluating_rank_num').val($('.stared').length);
  })

})

//预览图片
function previewImage(file) {
  //标准浏览器,FF、谷歌
  if (file["files"] && file["files"][0]) {
    var reader = new FileReader();
    reader.onload = function(evt) {
      // 添加图片
      $('#filelist').append('<a href="javascript:void(0);"><span class="del">删除</span><img class="envaluating_jqthumb" src="' + evt.target.result + '"></a>');
      // 缩略图调整
      $('.envaluating_jqthumb').jqthumb({
        width: 60,
        height: 60
      });
      // 点击删除
      $('.del').unbind("click").click(function(event) {
        var img_wrap_num = $(this).parents('a').index();
        $('.img_wrap input').eq(img_wrap_num).remove();
        $('.img_wrap').append('<input type="file" onchange="previewImage(this)" />');
        $(this).parents('a').remove();
      })
    }
    reader.readAsDataURL(file.files[0]);
  }
  //IE
  else {
    file.select();
    var path = document.selection.createRange().text;
    // 添加图片
    $('#filelist').append('<a href="javascript:void(0);"><span class="del">删除</span><img src="' + path + '"></a>');
    // 点击删除
    $('.del').unbind("click").click(function(event) {
      var img_wrap_num = $(this).parents('a').index();
      $('.img_wrap input').eq(img_wrap_num).remove();
      $('.img_wrap').append('<input type="file" onchange="previewImage(this)" />');
      $(this).parents('a').remove();
    })
  }
}
//选择图片
function selectImage() {
  if ($('#filelist a').length == 3) {
    alert('最多可上传 3 张图片');
  } else {
    $('.img_wrap input').eq($('#filelist a').length).click();
  }
}