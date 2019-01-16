//会议信息
var pushData;
var pushMid;
var vm = new Vue({
        el: "",
        data: {
            dataSource: [],
            activeItem: {id: 0, title: "", content: ""},
            layItem : null
        },
        created: function () {

            this.getDataSource();

            var grid = $("#grid").kendoGrid({
                selectable:"row",
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
                    pageSizes: true,
                    buttonCount: 5
                },
                toolbar: [{
                    template: '<a role="button" class="k-button k-button-icontext"  href="javascript:;" onclick="vm.add()"><span class="k-icon k-i-add"></span>添加</a>' +
                    '<input type="text" class="k-input" id="search-input"/>' +
                    '<a role="button"  class="k-button k-button-icontext"  href="javascript:;" onclick="vm.search()"><span class="k-icon k-i-search"></span>搜索</a>'
                }],
                columns: [
                    {field: "name", title: "会议主题", headerAttributes: {"class": "grid-algin-center"}, width: '200px'},
                    {field: "begindate", title: "开始时间", headerAttributes: {"class": "grid-algin-center"}, width: '200px'},
                    {field: "enddate", title: "结束时间", headerAttributes: {"class": "grid-algin-center"}, width: '200px'},
                    {field: "deadline", title: "提交截止时间", headerAttributes: {"class": "grid-algin-center"}, width: '200px'},
                    {field: "enddate", title: "状态",template:'<span>#=state#</span>', headerAttributes: {"class": "grid-algin-center"}, width: '100px'},
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
                                    begindate: data.begindate,
                                    enddate: data.enddate,
                                    deadline: data.deadline,
                                    remark: data.remark
                                };
                                vm.edit();
                            }
                        }, {
                            name: "destroy", text: "删除", iconClass: "k-icon k-i-delete"}], title: " ", width: "240px"
                    }
                ]
            });
            // grid.on('click', '.k-grid-content tr', function () {
            //     var row = grid.data("kendoGrid").select();
            //     var data = grid.data("kendoGrid").dataItem(row);
            //     pushMid = data.id;
            //     $('#agendaiframe').attr('src', "hyyc.html?mid="+pushMid);
            // });

            //行项目双击事件
            grid.dblclick('.k-grid-content tr', function () {
                var row = grid.data("kendoGrid").select();
                var data = grid.data("kendoGrid").dataItem(row);
                pushMid = data.id;
                $('#agendaiframe').attr('src', "hyyc.html?mid="+pushMid);

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
                                url: "/mm/meeting/get",
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
                                },
                                error: function (result) {
                                    options.error(result);
                                }
                            });
                        },
                        destroy: function (options) {
                            $.ajax({
                                url: "/mm/meeting/del",
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
                                begindate: {type: "string", nullable: false},
                                enddate: {type: "string", nullable: false},
                                deadline: {type: "string", nullable: false},
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
                    pageSize: 6,
                    serverPaging: true,
                    // serverFiltering: true,
                    serverSorting: true
                });
            },
            add: function () {
                layer.close(vm.layItem);
                pushData = {
                    id : 0
                };
                this.layItem = layer.open({
                    title: '添加会议',
                    type: 2,
                    area: ['500px', '485px'],
                    fixed: false, //不固定
                    maxmin: true,
                    shadeClose: true,
                    content: '/mm/hyxx_update.html',
                    end: function () {
                    }
                });
            },
            edit:function(){
                layer.close(vm.layItem);
                this.layItem = layer.open({
                    title:'编辑会议',
                    type: 2,
                    area: ['500px', '485px'],
                    fixed: false, //不固定
                    maxmin: true,
                    shadeClose: true,
                    content: '/mm/hyxx_update.html',
                    end: function () {
                    }
                });
            },
            search: function () {
                // vm.dataSource.read()
                $("#grid").data("kendoGrid").dataSource.read()
            }
        }
    }
);
$(function () {
    var layout =
        $('body').layout({
        south__size: '50%',
        south__resizable: true,
        resizerDragOpacity: 0.5
    });
    layout.allowOverflow("south");

});


