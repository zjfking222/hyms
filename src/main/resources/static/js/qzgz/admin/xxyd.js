var vm = new Vue({
    el: "#app_admin",
    data: {
        dataSource: [],
        activeItem: {id: 0, title: "", content: ""},
        layItem: null
    },
    created: function () {
        this.getDataSource();
        $("#grid").kendoGrid({
            dataSource: this.dataSource,
            editable: {
                confirmation: true,
                mode: "popup",
                window: {
                    title: "新增"
                }
            },
            // filterable: true,
            columnMenu: true,
            sortable: true,
            pageable: true,
            toolbar: [{
                template: '<a role="button" class="k-button k-button-icontext  href="javascript:;" onclick="vm.new()"><span class="k-icon k-i-plus"></span>新增</a>'
            }],
            columns: [
                {field: "title", title: "标题", headerAttributes: {"class": "grid-algin-center"}},
                {field: "creatername", title: "创建人", headerAttributes: {"class": "grid-algin-center"}, width: "120px"},
                {field: "created", title: "创建时间", headerAttributes: {"class": "grid-algin-center"}, width: "200px"},
                {
                    command: [{
                        name: "showitem", text: "编辑", iconClass: "k-icon k-i-edit",
                        click: function (e) {
                            e.preventDefault();
                            var tr = $(e.target).closest("tr");
                            var data = this.dataItem(tr);
                            vm.activeItem.id = data.id;
                            vm.activeItem.title = data.title;
                            vm.activeItem.content = data.content;
                            vm.showitem("编辑");
                        }
                    }, {name: "destroy", text: "删除"}], title: " ", width: "180px"
                }
            ],
        });
    },
    methods: {
        new: function () {
            vm.activeItem.id = 0;
            vm.activeItem.title = "";
            vm.activeItem.content = "";
            this.showitem('新增');
        },
        getDataSource: function () {
            this.dataSource = new kendo.data.DataSource({
                transport: {
                    read: function (options) {
                        $.ajax({
                            url: "/qzgz/admin/study/get",
                            data: options.data,
                            success: function (result) {
                                if (result.code == 0)
                                    options.success({data: result.data.data, total: result.data.total});
                                else
                                    options.error(result);
                            },
                            error: function (result) {
                                options.error(result);
                            }
                        });
                    },
                    destroy: function (options) {
                        $.ajax({
                            url: "/qzgz/admin/study/delete",
                            data: options.data,
                            success: function (result) {
                                if (result.code == 0) {
                                    options.success(result);
                                    layer.msg('删除成功！', {time: 1000, icon: 1});
                                }
                                else
                                    options.error(result);
                            },
                            error: function (result) {
                                options.error(result);
                            }
                        });
                    },
                },
                schema: {
                    data: "data",
                    total: "total",
                    model: {
                        id: "id",
                        fields: {
                            id: {editable: false, nullable: true},
                            title: {type: "string", nullable: false},
                            content: {type: "string", nullable: false},
                            creatername: {type: "string", nullable: false},
                            created: {type: "string", nullable: false}
                        }
                    }
                },
                error: function (e) {
                    this.cancelChanges();
                    console.log(e);
                    if (e.errors) {
                        layer.alert(e.errors.msg, {icon: 2});
                    }
                    else if (e.xhr.msg) {
                        layer.alert(e.xhr.msg, {icon: 2});
                    }
                    else {
                        layer.alert("发生错误，请联系管理员", {icon: 2});
                    }
                },
                requestEnd: function (e) {
                    var response = e.response;
                    if (response) {
                        response.type = e.type;
                    }
                },
                pageSize: 15,
                serverPaging: true,
                serverFiltering: true,
                serverSorting: true
            });
        },
        showitem: function (title) {
            this.layItem = layer.open({
                title: title,
                id: "layerItem",
                maxmin: true,
                area: ["800px", "350px"],
                type: 2,
                content: "/qzgz/admin/xxydedit.html",
                btn: ['提交', '取消'],
                yes: function (index, layero) {
                    var body = layer.getChildFrame('body', index);
                    var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                    var model = iframeWin.getValue();
                    if (model == null)
                        layer.alert("标题不能为空！", {icon: 2});
                    else {
                        var ur = "/qzgz/admin/study/update";
                        if (model.id == 0)
                            ur = "/qzgz/admin/study/add";
                        $.ajax({
                            url: ur,
                            data: model,
                            success: function (result) {
                                if (result.code == 0) {
                                    if (model.id == 0) {
                                        vm.dataSource.add(result.data);
                                        vm.dataSource.sync();
                                    } else {
                                        vm.dataSource.pushUpdate(model);
                                    }
                                    layer.close(vm.layItem);
                                    layer.msg('操作成功！', {time: 1500, icon: 1});
                                }
                                else
                                    options.error(result);
                            },
                            error: function (result) {
                                options.error(result);
                            }
                        });
                    }
                },
                btn2: function (index, layero) {
                },
                cancel: function () {
                    //右上角关闭回调
                }, success: function (layero, index) {
                    var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                    iframeWin.setValue(vm.activeItem);
                }
            });
            layer.full(this.layItem);
        }
    }
});