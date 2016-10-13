function ws_carousel_basic(a, b, c) {
    function q(c, e) {
        for (var f = 0; c > f; f++) {
            for (var g = e ? b.length - c + f : f; 0 > g;) g += c - b.length;
            for (; g > b.length - 1;) g -= c - b.length;
            p.push({
                item: d("<div>").append(d(b[g]).clone().css({
                    outline: "1px solid transparent"
                })).css({
                    position: "absolute",
                    overflow: "hidden",
                    top: 0,
                    left: 0,
                    width: "100%",
                    height: "100%"
                }).appendTo(o),
                id: g
            }), a.images && n.push(!a.images[g].noimage)
        }
    }

    function r() {
        var a = d(b[0]);
        a = {
            width: a.width(),
            height: a.height(),
            marginTop: parseFloat(a.css("marginTop")),
            marginLeft: parseFloat(a.css("marginLeft"))
        };
        for (var c in p) p[c].item && d(p[c].item).find("img").css(a)
    }

    function s(a) {
        a *= -1;
        var b = [];
        for (var c in p) {
            var e = m - c;
            b.push(e != a ? a - e : 0)
        }
        return b
    }

    function t(a, b, c, d) {
        wowAnimate(function(c) {
            w(a, b, c)
        }, 0, 1, c, d)
    }

    function u(a, b, c) {
        return a + (b - a) * c
    }

    function v(b, c) {
        a.support.transform ? b.css({
            transform: "scale(" + c.scale + ") translate3d(" + c.offset + "%,0,0) rotateY(" + c.rotate + "deg)",
            zIndex: c.zIndex
        }) : b.css({
            left: 100 * (1 - c.scale) / 2 + .85 * c.offset + "%",
            top: 100 * (1 - c.scale) / 2 + "%",
            width: 100 * c.scale + "%"
        })
    }

    function w(a, b, c, e) {
        e || (c = d.easing.easeInOutQuart(1, c, 0, 1, 1, 1));
        for (var f in a) {
            var g = u(a[f], b[f], c) * l,
                h = i / 100,
                m = 0,
                n = a[f] * (a[f] > 0 ? -1 : 1),
                o = g > 0 ? -1 : 1;
            c > .5 && (n = b[f] * (b[f] > 0 ? -1 : 1)), 0 === a[f] && (h = u(j, i, c) / 100, m = u(0, o * k, c)), 0 === b[f] && (h = u(i, j, c) / 100, m = u(o * k, 0, c)), 0 !== b[f] && 0 !== a[f] && (m = o * k), v(p[f].item, {
                scale: h,
                offset: g,
                rotate: m,
                zIndex: n
            })
        }
    }
    var d = jQuery,
        e = d(this),
        f = d(".ws_list", c).css("opacity", 0),
        i = (/iPhone|iPod|iPad|Android|BlackBerry/.test(navigator.userAgent), b.length, 90),
        j = 100,
        k = 60,
        l = 90,
        m = 2,
        n = [],
        o = d("<div>").addClass("ws_effect ws_carousel_basic").css({
            position: "absolute",
            top: 0,
            left: 0,
            width: "100%",
            height: "100%",
            overflow: "hidden",
            perspective: 2e3
        }).appendTo(c),
        p = [];
    q(m, !0), q(b.length), q(m);
    var x = s(a.startSlide);
    t(x, x, 0), r(), d(window).on("load resize", r), this.go = function(c, d) {
        function h(b) {
            return b.find("img").attr("src", a.images[c].src)
        }
        if (r(), a.images && !n[c + m]) {
            var g = c + m;
            n[g] = !0, h(p[g].item), m > c && h(p[p.length - c].item), c + m >= b.length && h(p[c + m - b.length].item)
        }
        window.XMLHttpRequest ? (d == b.length - 1 && 0 == c && (c = d + 1), 0 == d && c == b.length - 1 && (c = -1), t(s(d), s(c), a.duration, function() {
            e.trigger("effectEnd")
        })) : f.stop(!0).animate({
            left: c ? -c + "00%" : /Safari/.test(navigator.userAgent) ? "0%" : 0
        }, a.duration, "easeInOutExpo", function() {
            e.trigger("effectEnd")
        })
    }, this.step = function(a, b) {
        var c = a + (0 > b ? 1 : -1);
        0 > b && (b *= -1), w(s(a), s(c), b, !0)
    }
}
jQuery.extend(jQuery.easing, {
    easeInOutQuart: function(a, b, c, d, e) {
        return (b /= e / 2) < 1 ? d / 2 * b * b * b * b + c : -d / 2 * ((b -= 2) * b * b * b - 2) + c
    }
}), $(function() {

    if ($('.ie8').length || $('.ie9').length) {
        jQuery("#wowslider-container1").wowSlider({
            effect: "fade",
            prev: "",
            next: "",
            duration: 1200,
            delay: 2e3,
            width: "100%",
            height: "auto",
            autoPlay: !0,
            autoPlayVideo: !1,
            playPause: !0,
            stopOnHover: !0,
            loop: !1,
            bullets: 1,
            caption: !0,
            captionEffect: "parallax",
            controls: !0,
            controlsThumb: !1,
            responsive: 2,
            fullScreen: !0,
            gestures: 2,
            onBeforeStep: 0,
            images: 0
        })
    } else {
        jQuery("#wowslider-container1").wowSlider({
            effect: "carousel_basic",
            prev: "",
            next: "",
            duration: 1200,
            delay: 2e3,
            width: "100%",
            height: "auto",
            autoPlay: !0,
            autoPlayVideo: !1,
            playPause: !0,
            stopOnHover: !0,
            loop: !1,
            bullets: 1,
            caption: !0,
            captionEffect: "parallax",
            controls: !0,
            controlsThumb: !1,
            responsive: 2,
            fullScreen: !0,
            gestures: 2,
            onBeforeStep: 0,
            images: 0
        })
    };
    $("#wowslider-container1").hover(function() {
        $(".ws_prev").stop().animate({
            left: "0"
        }, 400), $(".ws_next").stop().animate({
            right: "0"
        }, 400)
    }, function() {
        $(".ws_prev").stop().animate({
            left: "-26px"
        }, 300), $(".ws_next").stop().animate({
            right: "-26px"
        }, 300)
    })
});
