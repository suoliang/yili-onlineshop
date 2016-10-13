var gulp = require('gulp'),
  gulpLoadPlugins = require('gulp-load-plugins'),
  plugins = gulpLoadPlugins(),
  browserSync = require('browser-sync');

// browser-sync
gulp.task('browser-sync', function() {
  var files = [
    'webapp/*.html',
    'webapp/css/*.css',
    'webapp/js/*.js'
  ];

  browserSync.init(files, {
    server: {
      baseDir: './webapp'
    }
  });
});

// HTML处理队列
gulp.task('html', function() {
  return gulp.src('webapp/*.html')
    .pipe(plugins.htmlhint())
    .pipe(plugins.htmlhint.failReporter());
});

// JS处理队列
gulp.task('js', function() {
  return gulp.src('webapp/js/*.js')
    // .pipe(plugins.jshint())
    // .pipe(plugins.jshint.reporter('default'))
    // .pipe(plugins.uglify())
    // .pipe(plugins.rename(function(path) {
    //   path.basename += ".min";
    // }))
    // .pipe(gulp.dest('webapp/js/min'));
});

// compass处理队列
gulp.task('compass', function() {
  gulp.src('webapp/sass/*.scss')
    .pipe(plugins.compass({
      config_file: './config.rb',
      css: 'webapp/css',
      sass: 'webapp/sass'
    }))
    .pipe(plugins.minifyCss())
    .pipe(plugins.rename(function(path) {
      path.basename += ".min";
    }))
    .pipe(gulp.dest('webapp/css'));
});

// CSS处理队列
// gulp.task('css', function() {
//   return gulp.src('webapp/css/*.css')
//     .pipe(plugins.autoprefixer({
//       browsers: ['last 2 version']
//     }))
//     .pipe(plugins.csslint())
//     .pipe(plugins.csslint.reporter())
//     .pipe(plugins.minifyCss())
//     .pipe(plugins.rename(function(path) {
//       path.basename += ".min";
//     }))
//     .pipe(gulp.dest('webapp/css/min'));
// });

// 图片压缩处理队列
gulp.task('imgmin', ['png', 'jpeg', 'gif']);

gulp.task('png', function() {
  return gulp.src('webapp/images/*.png')
    .pipe(plugins.imagemin({
      use: [pngquant()]
    }))
    .pipe(gulp.dest('webapp/images/min'));
});
gulp.task('jpeg', function() {
  return gulp.src('webapp/images/*.jpg')
    .pipe(plugins.imagemin({
      use: [imageminJpegtran()]
    }))
    .pipe(gulp.dest('webapp/images/min'));
});
gulp.task('gif', function() {
  return gulp.src('webapp/images/*.gif')
    .pipe(plugins.imagemin({
      use: [imageminGifsicle()]
    }))
    .pipe(gulp.dest('webapp/images/min'));
});

// 默认任务
gulp.task('default', ['watch', 'browser-sync', 'html'/*, 'js'*/, 'compass']);

gulp.task('watch', function() {
  gulp.watch('webapp/*.html', ['html']);
  gulp.watch('webapp/js/*.js', ['js']);
  gulp.watch('webapp/sass/*.scss', ['compass']);
});
