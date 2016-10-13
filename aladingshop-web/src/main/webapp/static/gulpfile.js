var gulp = require('gulp'),
  gulpLoadPlugins = require('gulp-load-plugins'),
  plugins = gulpLoadPlugins(),
  browserSync = require('browser-sync');

// browser-sync
gulp.task('browser-sync', function() {
  var files = [
    'shop/*.html',
    'shop/css/*.css',
    'shop/js/*.js'
  ];

  browserSync.init(files, {
    server: {
      baseDir: './shop'
    }
  });
});

// HTML处理队列
gulp.task('html', function() {
  return gulp.src('shop/*.html')
    .pipe(plugins.htmlhint())
    .pipe(plugins.htmlhint.failReporter());
});

// JS处理队列
gulp.task('js', function() {
  return gulp.src('shop/js/*.js')
    // .pipe(plugins.jshint())
    // .pipe(plugins.jshint.reporter('default'))
    // .pipe(plugins.uglify())
    // .pipe(plugins.rename(function(path) {
    //   path.basename += ".min";
    // }))
    // .pipe(gulp.dest('shop/js/min'));
});

// compass处理队列
gulp.task('compass', function() {
  gulp.src('shop/sass/*.scss')
    .pipe(plugins.compass({
      config_file: './config.rb',
      css: 'shop/css',
      sass: 'shop/sass'
    }))
    .pipe(plugins.minifyCss())
    .pipe(plugins.rename(function(path) {
      path.basename += ".min";
    }))
    .pipe(gulp.dest('shop/css'));
});

// CSS处理队列
// gulp.task('css', function() {
//   return gulp.src('shop/css/*.css')
//     .pipe(plugins.autoprefixer({
//       browsers: ['last 2 version']
//     }))
//     .pipe(plugins.csslint())
//     .pipe(plugins.csslint.reporter())
//     .pipe(plugins.minifyCss())
//     .pipe(plugins.rename(function(path) {
//       path.basename += ".min";
//     }))
//     .pipe(gulp.dest('shop/css/min'));
// });

// 图片压缩处理队列
gulp.task('imgmin', ['png', 'jpeg', 'gif']);

gulp.task('png', function() {
  return gulp.src('shop/images/*.png')
    .pipe(plugins.imagemin({
      use: [pngquant()]
    }))
    .pipe(gulp.dest('shop/images/min'));
});
gulp.task('jpeg', function() {
  return gulp.src('shop/images/*.jpg')
    .pipe(plugins.imagemin({
      use: [imageminJpegtran()]
    }))
    .pipe(gulp.dest('shop/images/min'));
});
gulp.task('gif', function() {
  return gulp.src('shop/images/*.gif')
    .pipe(plugins.imagemin({
      use: [imageminGifsicle()]
    }))
    .pipe(gulp.dest('shop/images/min'));
});

// 默认任务
gulp.task('default', ['watch', 'browser-sync', 'html'/*, 'js'*/, 'compass']);

gulp.task('watch', function() {
  gulp.watch('shop/*.html', ['html']);
  gulp.watch('shop/js/*.js', ['js']);
  gulp.watch('shop/sass/*.scss', ['compass']);
});
