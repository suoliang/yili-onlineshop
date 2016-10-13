/* ==========================================================
 * sco.modal.js
 * http://github.com/terebentina/sco.js
 * ==========================================================
 * Copyright 2013 Dan Caragea.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================== */

/*jshint laxcomma:true, sub:true, browser:true, jquery:true, devel:true, eqeqeq:false */
/*global Spinner:true */

;(function($, undefined) {
	"use strict";

	var pluginName = 'scojs_modal';

	function Modal(options) {
		this.options = $.extend({}, $.fn[pluginName].defaults, options);
		this.$modal = $(this.options.target).attr('class', 'modal fade Modal_news').hide();
		var self = this;

		// function init() {
		// 	if (self.options.title === '') {
		// 		self.options.title = '&nbsp;';
		// 	}
		// };
		function init() {
			if (self.options.src === '') {
				self.options.src = 'images/news_left.jpg';
			}
		};

		init();
	}


	$.extend(Modal.prototype, {
		show: function() {
			var self = this
				,$backdrop;

			if (!this.options.nobackdrop) {
				$backdrop = $('.modal-backdrop');
			}
			if (!this.$modal.length) {
				this.$modal = $('<div class="modal fade Modal_news" id="' + this.options.target.substr(1) + '"><div class="news_left"><img src="images/news_left.jpg"></div><div class="news_right"><div class="news_close" data-dismiss="modal"></div><div class="news_inner"></div></div></div>').appendTo(this.options.appendTo).hide();
			}

			// this.$modal.find('.modal-header h3').html(this.options.title);
			this.$modal.find('.news_left img').attr({'src':this.options.src});

			if (this.options.cssclass !== undefined) {
				this.$modal.attr('class', 'modal fade Modal_news' + this.options.cssclass);
			}

			if (this.options.width !== undefined) {
				this.$modal.width(this.options.width);
			}

			if (this.options.left !== undefined) {
				this.$modal.css({'left': this.options.left});
			}

			if (this.options.height !== undefined) {
				this.$modal.height(this.options.height);
			}

			if (this.options.top !== undefined) {
				this.$modal.css({'top': this.options.top});
			}

			if (this.options.keyboard) {
				this.escape();
			}

			if (!this.options.nobackdrop) {
				if (!$backdrop.length) {
					$backdrop = $('<div class="modal-backdrop fade" />').appendTo(this.options.appendTo);
				}
				$backdrop[0].offsetWidth; // force reflow
				$backdrop.addClass('in');
			}

			this.$modal.off('close.' + pluginName).on('close.' + pluginName, function() {
				self.close.call(self);
			});
			if (this.options.remote !== undefined && this.options.remote != '' && this.options.remote !== '#') {
				var spinner;
				if (typeof Spinner == 'function') {
					spinner = new Spinner({color: '#3d9bce'}).spin(this.$modal[0]);
				}
				this.$modal.find('.news_inner').load(this.options.remote, function() {
					if (spinner) {
						spinner.stop();
					}
					if (self.options.cache) {
						self.options.content = $(this).html();
						delete self.options.remote;
					}
				});
			} else {
				this.$modal.find('.news_inner').html(this.options.content);
			}

			this.$modal.show().addClass('in');
			return this;
		}

		,close: function() {
			this.$modal.hide().off('.' + pluginName).find('.news_inner').html('');
			if (this.options.cssclass !== undefined) {
				this.$modal.removeClass(this.options.cssclass);
			}
			$(document).off('keyup.' + pluginName);
			$('.modal-backdrop').remove();
			if (typeof this.options.onClose === 'function') {
				this.options.onClose.call(this, this.options);
			}
			return this;
		}

		,destroy: function() {
			this.$modal.remove();
			$(document).off('keyup.' + pluginName);
			$('.modal-backdrop').remove();
			this.$modal = null;
			return this;
		}

		,escape: function() {
			var self = this;
			$(document).on('keyup.' + pluginName, function(e) {
				if (e.which == 27) {
					self.close();
				}
			});
		}
	});


	$.fn[pluginName] = function(options) {
		return this.each(function() {
			var obj;
			if (!(obj = $.data(this, pluginName))) {
				var  $this = $(this)
					,data = $this.data()
					,opts = $.extend({}, options, data)
					;
				if ($this.attr('href') !== '' && $this.attr('href') != '#') {
					opts.remote = $this.attr('href');
				}
				obj = new Modal(opts);
				$.data(this, pluginName, obj);
			}
			obj.show();
		});
	};


	$[pluginName] = function(options) {
		return new Modal(options);
	};


	$.fn[pluginName].defaults = {
		title: '&nbsp;'		// modal title
		,target: '#modal'	// the modal id. MUST be an id for now.
		,content: ''		// the static modal content (in case it's not loaded via ajax)
		,appendTo: 'body'	// where should the modal be appended to (default to document.body). Added for unit tests, not really needed in real life.
		,cache: false		// should we cache the output of the ajax calls so that next time they're shown from cache?
		,keyboard: false
		,nobackdrop: false
	};


	$(document).on('click.' + pluginName, '[data-trigger="modal"]', function() {
		$(this)[pluginName]();
		if ($(this).is('a')) {
			return false;
		}
	}).on('click.' + pluginName, '[data-dismiss="modal"]', function(e) {
		e.preventDefault();
		$(this).closest('.modal').trigger('close');
	});
})(jQuery);
