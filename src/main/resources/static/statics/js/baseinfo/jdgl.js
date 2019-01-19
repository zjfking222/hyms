//酒店管理
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
            filterable: true,
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
                {field: "name", title: "酒店名称", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                {field: "phone", title: "酒店固话", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                {field: "address", title: "酒店地址", headerAttributes: {"class": "grid-algin-center"}, width: '250px'},
                {field: "contacter", title: "业务员", headerAttributes: {"class": "grid-algin-center"}, width: '100px'},
                {field: "cmobile", title: "手机", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                {field: "cphone", title: "固话", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                {field: "email", title: "邮箱", headerAttributes: {"class": "grid-algin-center"}, width: '230px'},
                {field: "star", title: "星级", headerAttributes: {"class": "grid-algin-center"}, width: '80px'},
                {field: "remark", title: "备注", headerAttributes: {"class": "grid-algin-center"}, width: '200px'},
                {
                    command: [{
                        name: "showitem", text: "编辑", iconClass: "k-icon k-i-edit",
                        click: function (e) {
                            e.preventDefault();
                            var tr = $(e.target).closest("tr");
                            var data = this.dataItem(tr);
                            pushData = {
                                id: data.id,
                                name: data.name,
                                phone: data.phone,
                                address: data.address,
                                contacter: data.contacter,
                                cmobile: data.cmobile,
                                cphone: data.cphone,
                                email: data.email,
                                star: data.star,
                                remark: data.remark
                            };
                            vm.edit();
                        }
                    }, {name: "destroy", text: "删除", iconClass: "k-icon k-i-delete"}],
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
                        //取kendoGrid的当前过滤条件
                        var allMap;
                        var grid = $("#grid").data("kendoGrid");
                        if(grid){
                            allMap = getFilters(grid);
                        }
                        $.ajax({
                            url: "/bi/hotel/getAll",
                            data: {
                                'filters': allMap === undefined ? "" : JSON.stringify(allMap),
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
                            url: "/bi/hotel/delete",
                            data: {
                                'id': options.data.id
                            },
                            method: 'POST',
                            success: function (result) {
                                if (result.code === 0) {
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
                            phone: {type: "string", nullable: false},
                            address: {type: "string", nullable: false},
                            contacter: {type: "string", nullable: false},
                            cmobile: {type: "string", nullable: false},
                            cphone: {type: "string", nullable: false},
                            email: {type: "string", nullable: false},
                            star: {type: "string", nullable: false},
                            remark: {type: "string", nullable: false},
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
                // serverFiltering: true,
                serverSorting: true
            });
        },
        add: function () {
            layer.close(vm.layItem);
            pushData = {
                id: 0
            };
            this.layItem = layer.open({
                title: '添加酒店',
                type: 2,
                shadeClose:true,
                area: ['450px', '80%'],
                fixed: false, //不固定
                maxmin: true,
                content: '/baseinfo/jdgl_update.html',
                end: function () {
                }
            });
        },
        edit: function () {
            layer.close(vm.layItem);
            this.layItem = layer.open({
                title: '编辑酒店',
                type: 2,
                shadeClose:true,
                area: ['450px', '80%'],
                fixed: false, //不固定
                maxmin: true,
                content: '/baseinfo/jdgl_update.html',
                end: function () {
                }
            });
        },
        search: function () {
            // vm.dataSource.read()
            $("#grid").data("kendoGrid").dataSource.read()
        }
    }
});
