var vm = new Vue({
    el: "#app_admin",
    data: {
        dataSource: [],
        parentmenus: []
    },
    created: function () {
        this.getParentmenus();
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
            filterable: true,
            columnMenu: true,
            sortable: true,
            pageable: true,
            toolbar: ["create"],
            columns: [
                {field: "name", title: "名称", width: "140px", headerAttributes: {"class": "grid-algin-center"}},
                {field: "url", title: "URL", width: "260px"},
                {field: "sort", title: "排序", width: "100px"},
                {field: "parentid", title: "父菜单", width: "120px", values: this.parentmenus},// editor: workersCapStatusDropDownEditor, template: workersCapStatusText
                {field: "enable", title: "启用", values: [{text: "启用", value: true}, {text: "禁用", value: false}]},
                {field: "fieldType", title: "域类型", width: "60px", values: [{text: "PC", value: 0}, {text: "mobile", value: 1}, {text: "兼容", value: 2}]},
                {field: "icon", title: "图标"},
                {field: "permission", title: "权限标识", width: "120px"},
                {
                    command: [{name: "edit", text: {edit: "编辑", cancel: "取消", update: "更新"}},
                        {name: "destroy", text: "删除"}], title: " ", width: "180px"
                }
            ],
            edit: function (e) {
                var editWindow = e.container.data("kendoWindow");
                if (e.model.isNew()) {
                    editWindow.title('新增');
                }
                else {
                    editWindow.title('编辑');
                }
            }
        });
    },
    methods: {
        getParentmenus: function () {
            vm = this;
            $.ajax({
                type: "POST",
                url: "/system/menus/get",
                data: {"p": 1},
                async: false,
                success: function (data, state) {
                    if (data.code == 0) {
                        for (var i = 0; i < data.data.length; i++) {
                            if (data.data[i].parentid == 0)
                                vm.parentmenus.push({text: data.data[i].name, value: data.data[i].id});
                        }
                    }
                    else {
                        console.log(data.msg)
                    }
                }
            });
        },
        getDataSource: function () {
            this.dataSource = new kendo.data.DataSource({
                transport: {
                    read: function (options) {
                        $.ajax({
                            url: "/system/menus/get",
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
                    update: function (options) {
                        $.ajax({
                            url: "/system/menus/update",
                            data: options.data,
                            success: function (result) {
                                if (result.code == 0) {
                                    options.success(result);
                                    layer.msg('更新成功！', {time: 1000, icon: 1});
                                }
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
                            url: "/system/menus/delete",
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
                    create: function (options) {
                        $.ajax({
                            url: "/system/menus/add",
                            data: options.data,
                            success: function (result) {
                                if (result.code == 0) {
                                    options.success(result);
                                    layer.msg('添加成功！', {time: 1000, icon: 1});
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
                schema: {
                    data: "data",
                    total: "total",
                    // errors: function (response) {
                    //     console.log(response);
                    //     // console.log("errors as function", response.errors[0])
                    //     return response;
                    // },
                    model: {
                        id: "id",
                        fields: {
                            id: {editable: false, nullable: true},
                            name: {
                                type: "string", nullable: false, validation: {required: {message: "名称不能为空"}}
                            },
                            url: {type: "string", nullable: true},
                            sort: {type: "number", nullable: true, defaultValue: 0},
                            parentid: {type: "number", nullable: false, defaultValue: 0},
                            enable: {nullable: false, defaultValue: true},
                            fieldType: {type: "number", nullable: false, defaultValue: 0},
                            icon: {type: "string", nullable: true},
                            permission: {type: "string", nullable: true},
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
        }
    }
});