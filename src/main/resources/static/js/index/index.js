var vm = new Vue({
    el: "#app_admin",
    data: {
        lock: false,
        tabsPage: {},
        pageTabs: false,
        menus: [],
        username: ""
    },
    created: function () {
        var vm = this;
        $.ajax({
            url: "/index/config",
            type: "POST",
            async: false,
            success: function (data, state) {
                if (data.code == 0) {
                    vm.username = data.data.username;
                    vm.menus = data.data.menus;
                }
                else {
                    console.log(data.msg)
                }
            }
        });
        layui.use(['layer', 'element'], function (a) {
            vm.resizeSystem();
            $(window).on("resize", vm.resizeSystem);
            $("body").on("mouseenter", "*[lay-tips]", function (e) {
                var e = $(this);
                if (!e.parent().hasClass("layui-nav-item") || $("#app_admin").hasClass("layadmin-side-shrink")) {
                    var s = layer.tips(e.attr("lay-tips"), this, {
                        tips: e.attr("lay-direction") || 1,
                        time: -1,
                        success: function (e, a) {
                            e.css("margin-left", e.attr("lay-offset") + "px")
                        }
                    });
                    e.data("index", s)
                }
            }).on("mouseleave", "*[lay-tips]", function () {
                layer.close($(this).data("index"))
            });
            layui.element.on("tab(layadmin-layout-tabs)", function (e) {
                vm.tabsPage.index = e.index;
            });
            $("body").on("click", "#app_tabsheader>li", function () {
                var e = $(this);
                vm.tabsPage.type = "tab";
                vm.tabsClick(e);
            });
            layui.element.on("tabDelete(layadmin-layout-tabs)", function (e) {
                var i = $("#app_tabsheader>li.layui-this");
                vm.tabsBody(e.index).remove();
                vm.tabsClick(i);
            });
        });
    },
    methods: {
        flexible: function (e) {
            var left = $("#app_flexible").hasClass("layui-icon-spread-left");
            this.sideFlexible(left ? "spread" : null);
        },
        sideFlexible: function (e) {
            var i = $("#app_admin");
            var s = this.screen();
            if ("spread" === e) {
                $("#app_flexible").removeClass("layui-icon-spread-left").addClass("layui-icon-shrink-right");
                s < 2 ? i.addClass("layadmin-side-spread-sm") : i.removeClass("layadmin-side-spread-sm");
                i.removeClass("layadmin-side-shrink");
            }
            else {
                $("#app_flexible").removeClass("layui-icon-shrink-right").addClass("layui-icon-spread-left");
                s < 2 ? i.removeClass("layadmin-side-shrink") : i.addClass("layadmin-side-shrink");
                i.removeClass("layadmin-side-spread-sm");
            }
        },
        screen: function () {
            var e = $(window).width();
            return e >= 1200 ? 3 : e >= 992 ? 2 : e >= 768 ? 1 : 0
        },
        resizeSystem: function () {
            layer.closeAll("tips");
            this.lock || setTimeout(function () {
                vm.sideFlexible(vm.screen() < 2 ? "" : "spread");
                vm.lock = false;
            }, 100);
            this.lock = true;
        },
        tabsClick: function (e) {
            var a = e.attr("lay-id");
            this.tabsPage.index = e.index();
            this.tabsBodyChange(this.tabsPage.index);
            location.hash = a;
        },
        menuClick: function (id, name, url) {
            if (url == null)
                return;
            var y = url.replace(/(^http(s*):)|(\?[\s\S]*$)/g, "");
            var selected = false;
            var tabs = $("#app_tabsheader>li");
            tabs.each(function (e) {
                var layid = $(this).attr("lay-id");
                if (layid == id) {
                    selected = true;
                    vm.tabsPage.index = e;
                }
            });
            if (!selected) {
                $("#app_body").append(['<div class="layui-tab-item layadmin-tabsbody-item layui-show">',
                    '<iframe src="' + url + '" frameborder="0" class="layadmin-iframe"></iframe>',
                    "</div>"].join(""));
                this.tabsPage.index = tabs.length;
                layui.element.tabAdd("layadmin-layout-tabs", {
                    title: "<span>" + name + "</span>",
                    id: id,
                    attr: y
                });
            }
            var u = this.tabsBody(this.tabsPage.index).find(".layadmin-iframe");
            u[0].contentWindow.location.href = url;
            layui.element.tabChange("layadmin-layout-tabs", id);
            this.tabsBodyChange(this.tabsPage.index, {url: url, text: name});
            if (this.screen() < 2)
                this.sideFlexible();
        },
        tabsBody: function (index) {
            return $("#app_body").find(".layadmin-tabsbody-item").eq(index)
        },
        tabsBodyChange: function (index, date) {
            this.tabsBody(index).addClass("layui-show").siblings().removeClass("layui-show");
            this.rollPage("auto", index);
        },
        rollPage: function (e, i) {//e, i
            var t = $("#app_tabsheader");
            var n = t.children("li");
            var s = t.parent().outerWidth();
            var l = parseFloat(t.css("left"));
            if ("left" === e) {
                if (!l && l <= 0)
                    return;
                var o = -l - s;
                n.each(function (e, i) {
                    var n = $(i);
                    var s = n.position().left;
                    if (s >= o) {
                        t.css("left", -s);
                        return false;
                    }
                })
            } else if ("auto" === e) {
                var o = n.eq(i);
                if (o[0]) {
                    var left = o.position().left;
                    if (left < -l)
                        return t.css("left", -left);
                    if (left + o.outerWidth() >= s - l) {
                        var r = left + o.outerWidth() - (s - l);
                        n.each(function (e, i) {
                            var n = $(i);
                            var s = n.position().left;
                            if (s + l > 0 && s + l > r) {
                                t.css("left", -s);
                                return false;
                            }
                        });
                    }
                }
            } else {
                n.each(function (e, i) {
                    var n = $(i);
                    var o = n.position().left;
                    if (o + n.outerWidth() >= s - l) {
                        t.css("left", -o);
                        return false;
                    }
                })
            }
        },
        leftPage: function () {
            this.rollPage("left")
        },
        rightPage: function () {
            this.rollPage()
        },
        closeThisTabs: function () {
            if (this.tabsPage.index)
                $("#app_tabsheader>li").eq(this.tabsPage.index).find(".layui-tab-close").trigger("click")
        },
        closeOtherTabs: function (e) {
            if ("all" === e) {
                $("#app_tabsheader>li:gt(0)").remove();
                $("#app_body").find(".layadmin-tabsbody-item:gt(0)").remove();
                $("#app_tabsheader>li").eq(0).trigger("click")
                delete this.tabsPage.index;
            }
            else {
                var i = "system-pagetabs-remove";
                $("#app_tabsheader>li").each(function (e, t) {
                    if (e > 0 && e != vm.tabsPage.index) {
                        $(t).addClass(i);
                        vm.tabsBody(e).addClass(i)
                    }
                });
                $("." + i).remove()
                this.tabsPage.index = 1;
            }
        },
        closeAllTabs: function () {
            this.closeOtherTabs("all")
        },
        refresh: function () {
            var e = this.tabsBody(this.tabsPage.index).find(".layadmin-iframe");
            e[0].contentWindow.location.reload(false);
        }
    }
});