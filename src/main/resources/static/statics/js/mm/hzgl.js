//回执管理
var vm = new Vue({
        el: "",
        data: {
            dataSource: [],
            activeItem: {id: 0, title: "", content: ""},
            layItem : null
        },
        created: function () {

            this.getDataSource();

            var grid =
                $("#grid").kendoGrid({
                selectable:"row",
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
                pageable: {
                    refresh: true,
                    pageSizes: true,
                    buttonCount: 5
                },
                toolbar: [{
                    template: '<input type="text" class="k-input" id="search-input"/>' +
                    '<a role="button"  class="k-button k-button-icontext"  href="javascript:;" onclick="vm.search()"><span class="k-icon k-i-search"></span>搜索</a>'+
                    '<a role="button"  class="k-button k-button-icontext"  href="javascript:;" onclick="vm.savaAsExcel()"><span class="k-icon k-i-excel"></span>导出Excel</a>'

                }],
                columns: [
                    {field: "name", title: "会议主题", headerAttributes: {"class": "grid-algin-center"}, width: '250px'},
                    {field: "begindate", title: "开始时间", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "enddate", title: "结束时间", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "deadline", title: "提交截止时间", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "rnum", title: "回执数量", headerAttributes: {"class": "grid-algin-center"}, width: '110px'},
                    {field: "enddate", title: "状态",template:'<span>#=state#</span>', headerAttributes: {"class": "grid-algin-center"}, width: '100px'},
                    {field: "remark", title: "备注", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {
                        command: [{
                            name: "showitem", text: "查看详情", iconClass: "k-icon k-i-zoom",
                            click: function (e) {
                                e.preventDefault();
                                var tr = $(e.target).closest("tr");
                                var data = this.dataItem(tr);
                                window.location.href = "/mm/hzlb.html?id="+data.id;

                            }
                        }], title: " ", width: "180px"
                    }]
            });
            grid.dblclick('.k-grid-content tr', function () {
                var row = grid.data("kendoGrid").select();
                var data = grid.data("kendoGrid").dataItem(row);
                window.location.href = "/mm/hzlb.html?id="+data.id;
            });
        },
        methods: {
            getDataSource: function () {
                this.dataSource = new kendo.data.DataSource({
                    transport: {
                        read: function (options) {
                            $.ajax({
                                url: "/mm/receipt/getMeeting",
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
                                begindate: {type: "string", nullable: false},
                                enddate: {type: "string", nullable: false},
                                deadline: {type: "string", nullable: false},
                                rnum: {type: "string", nullable: false},
                                state: {type: "string", nullable: false},
                                remark: {type: "string", nullable: false}
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
            search: function () {
                // vm.dataSource.read()
                $("#grid").data("kendoGrid").dataSource.read()
            },
            savaAsExcel: function () {
                $("#grid").data("kendoGrid").saveAsExcel();
            }
        }
    }
);
