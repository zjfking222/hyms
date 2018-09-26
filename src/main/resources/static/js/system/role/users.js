//系统用户管理
var vm = new Vue({
    el: "",
    data: {
        dataSource: [],
        activeItem: {id: 0, title: "", content: ""},
        layItem: null
    },
    created: function () {
        this.getDataSource();
        $("#grid").kendoGrid({
            selectTable: "row",
            dataSource: this.dataSource,
            editable: {
                confirmation: true,
                mode: "popup",
                window: {
                    title: "新增"
                }
            },
            columnMenu: true,
            sortable: true,
            pageable: {
                refresh: true,
                pageSize: true,
                buttonCount: 5
            },
            toolbar: [{
                template: '<a role="button" class="k-button k-button-icontext" href="javascript:;" onclick="vm.add()"><span class="k-icon k-i-add"></span>添加</a>' +
                '<input type="text" class="k-input" id="search-input"/>' +
                '<a role="button" class="k-button k-button-icontext" href="javascript:;" onclick="vm.search()"><span class="k-icon k-i-search"></span>搜索</a>'
            }],
            columns: [
                {field: "oaloginid", title: "登录账号", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                {field: "name", title: "姓名", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                {
                    command: [{name: "destroy", text: "删除", iconClass: "k-icon k-i-delete"}],
                    title: " ", width: "240px"
                }
            ]
        });
    },
    methods: {
        getDataSource: function () {
            this.dataSource = new kendo.data.DataSource({
                transport: {
                    read: function (options) {
                        $.ajax({
                            url: "/system/users/getAll",
                            data: {
                                'sort': options.data.sort === undefined || options.data.sort[0] === undefined ?
                                    undefined : options.data.sort[0].field,
                                'dir': options.data.sort === undefined || options.data.sort[0] === undefined ?
                                    undefined : options.data.sort[0].dir,
                                'page': options.data.page,
                                'pageSize': options.data.pageSize,
                                'value': $('#search-input').val()
                            },
                            method: 'POST',
                            success: function (result) {
                                if (result.code === 0) {
                                    options.success({data: result.data.data, total: result.data.total});
                                }
                                else
                                    options.error(result);
                            }
                        });
                    },
                    destroy: function (options) {
                        $.ajax({
                            url: "/system/users/delete",
                            data: {
                                'id': options.data.id
                            },
                            method: 'POST',
                            success: function (result) {
                                if (result.code === 0) {
                                    options.success(result);
                                    layer.msg('删除成功',{time: 1000,icon:1});
                                    $("#grid").data("kendoGrid").dataSource.read();
                                }
                                else
                                    options.error(result);
                            },
                            error:function (result) {
                                options.error(result);
                            }
                        });
                    }
                },
                schema: {
                    data: "data",
                    total: "total",
                    model: {
                        id: "id",
                        fields: {
                            id: {editable: false, nullable: true},
                            name: {type: "string", nullable: false},
                            oaloginid:{type:"string", nullable:false},
                        }
                    }
                },
                error:function (e) {
                    this.cancelChanges();
                    console.log(e);
                    if(e.errors){
                        layer.alert(e.errors.msg,{icon:2});
                    }
                    else if(e.xhr.msg){
                        layer.alert(e.xhr.msg, {icon: 2});
                    }
                    else {
                        layer.alert("发生错误，请联系管理员", {icon: 2});
                    }
                },
                requestEnd:function (e) {
                    var response = e.response;
                    if (response) {
                        response.type = e.type;
                    }
                },
                pageSize: 15,
                serverPaging: true,
                serverSorting: true
            });
        },
        add: function () {
            layer.close(vm.layItem);
            pushData = {
                id: 0
            };
            this.layItem = layer.open({
                title: '添加系统用户',
                type: 2,
                area: ['450px', '600px'],
                fixed: false, //不固定
                maxmin: true,
                shadeClose: true,
                content: '/system/role/users_update.html',
                end: function () {
                    $("#grid").data("kendoGrid").dataSource.read()
                }
            });
        },
        search: function () {
            $("#grid").data("kendoGrid").dataSource.read()
        }
    }
});