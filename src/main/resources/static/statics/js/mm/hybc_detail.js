//会议班车
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
                {field: "firsttime", title: "首班车时间", headerAttributes: {"class": "grid-algin-center"}, width: '200px'},
                {field: "endtime", title: "末班车时间", headerAttributes: {"class": "grid-algin-center"}, width: '200px'},
                {field: "interval", title: "每班间隔时间", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                {field: "start", title: "起点", headerAttributes: {"class": "grid-algin-center"}, width: '160px'},
                {field: "end", title: "终点", headerAttributes: {"class": "grid-algin-center"}, width: '160px'},
                {field: "runtime", title: "行驶时间", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
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
                                mid: window.location.search.substr(4),
                                firsttime: data.firsttime,
                                endtime: data.endtime,
                                interval: data.interval,
                                start: data.start,
                                end: data.end,
                                runtime: data.runtime,
                                remark: data.remark,
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
                            url: "/mm/bus/getAll",
                            data: {
                                'filters': allMap === undefined ? "" : JSON.stringify(allMap),
                                'sort': options.data.sort === undefined || options.data.sort[0] === undefined ?
                                    undefined : options.data.sort[0].field,
                                'dir': options.data.sort === undefined || options.data.sort[0] === undefined ?
                                    undefined : options.data.sort[0].dir,
                                'page': options.data.page,
                                'pageSize': options.data.pageSize,
                                'value': $('#search-input').val(),
                                'mid': window.location.search.substr(4),
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
                            url: "/mm/bus/delete",
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
                            firsttime: {type: "string", nullable: false},
                            endtime: {type: "string", nullable: false},
                            interval: {type: "string", nullable: false},
                            start: {type: "string", nullable: false},
                            end: {type: "string", nullable: false},
                            runtime: {type: "string", nullable: false},
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
                title: '添加班车',
                type: 2,
                area: ['450px', '80%'],
                fixed: false, //不固定
                maxmin: true,
                content: '/mm/hybc_update.html',
                shadeClose: true,
                end: function () {
                    $("#grid").data("kendoGrid").dataSource.read()
                }
            });
        },
        edit: function () {
            layer.close(vm.layItem);
            this.layItem = layer.open({
                title: '编辑班车',
                type: 2,
                area: ['450px', '80%'],
                fixed: false, //不固定
                maxmin: true,
                content: '/mm/hybc_update.html',
                shadeClose: true,
                end: function () {
                    $("#grid").data("kendoGrid").dataSource.read()
                }
            });
        },
        search: function () {
            // vm.dataSource.read()
            $("#grid").data("kendoGrid").dataSource.read()
        }
    }
});