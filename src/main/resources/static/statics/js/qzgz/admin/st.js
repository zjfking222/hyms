var pushData;
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
                template: '<span class="date">日期</span><input type="date" class="calendar-grid" id="calendar"/>' +
                '<span class="type">类型</span><select class="k-search"  id="classify"><option value="" selected></option><option value="1">早餐</option><option value="2">午餐</option><option value="3">晚餐</option><option value="4">夜宵</option></select>' +
                '<input type="text" class="k-input" id="search-input"/>' +
                '<a role="button"  class="k-button k-button-icontext"  href="javascript:;" onclick="vm.search()"><span class="k-icon k-i-search"></span>搜索</a>' +
                '<a role="button" class="k-button k-button-icontext"  href="/down/excel?url=' + encodeURIComponent('/qzgz/template/st.xlsx') + '"><span class="k-icon k-i-download"></span>模板文件</a>' +
                '<a role="button" class="k-button k-button-icontext"  href="javascript:;" onclick="vm.batchadd()"><span class="k-icon k-i-excel"></span>导入EXCEL</a>'
            }],
            columns: [
                {field: "name", title: "名称", headerAttributes: {"class": "grid-algin-center"}, width: '250px'},
                {field: "meal", title: "类型", headerAttributes: {"class": "grid-algin-center"}, width: '180px'},
                {field: "type", title: "品种", headerAttributes: {"class": "grid-algin-center"}, width: '180px'},
                // {field: "price", title: "价格", headerAttributes: {"class": "grid-algin-center"}, width: '100px'},//价格先不用
                {
                    command: [{
                        name: "destroy", text: "删除", iconClass: "k-icon k-i-delete"
                    }], title: "操作", width: "150px"
                },
                {field: "", title: "", headerAttributes: {"class": "grid-algin-center"}}
            ]
        })
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
                            url: "/qzgz/admin/getCanteen",
                            data: {
                                'filters': allMap === undefined ? "" : JSON.stringify(allMap),
                                'sort': options.data.sort === undefined || options.data.sort[0] === undefined ?
                                    undefined : options.data.sort[0].field,
                                'dir': options.data.sort === undefined || options.data.sort[0] === undefined ?
                                    undefined : options.data.sort[0].dir,
                                'page': options.data.page,
                                'pageSize': options.data.pageSize,
                                'value': $('#search-input').val(),
                                'date': $('#calendar').val(),
                                'meal': $('#classify').val()
                            },
                            method: 'POST',
                            success: function (result) {
                                if (result.code === 0) {
                                    for (var i = 0; i < result.data.data.length; i++) {
                                        switch (result.data.data[i].meal) {
                                            case 1:
                                                result.data.data[i].meal = '早餐';
                                                break;
                                            case 2:
                                                result.data.data[i].meal = '午餐';
                                                break;
                                            case 3:
                                                result.data.data[i].meal = '晚餐';
                                                break;
                                            case 4:
                                                result.data.data[i].meal = '夜宵';
                                                break;
                                        }
                                    }
                                    options.success({data: result.data.data, total: result.data.total});
                                }
                                else {
                                    options.error(result);
                                }
                            },
                            error: function (result) {
                                options.error(result);
                            }
                        });
                    },
                    destroy: function (options) {
                        $.ajax({
                            url: "/qzgz/admin/deleteCanteen",
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
                            meal: {type: "string", nullable: false},
                            type: {type: "string", nullable: false}
                            // price: {type: "string", nullable: false}
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
                serverSorting: true
            });
        },
        search: function () {
            $("#grid").data("kendoGrid").dataSource.read();
        },
        batchadd:function () {
            layer.close(vm.layItem);
            this.layItem = layer.open({
                title: '添加菜单',
                type: 2,
                area: ['700px', '80%'],
                fixed: false, //不固定
                maxmin: true,
                shadeClose: true,
                content: '/qzgz/admin/st_batch.html',
                end: function () {
                    $("#grid").data("kendoGrid").dataSource.read()
                }
            })
        }
    }
});
var FetchData = function (data, method, param, async) {
    var response = $.ajax({
        async: async,
        url:  param,
        type: method,
        dataType: 'String',
        data: data,
        success: function (dataSource) {
            return dataSource
        }
    });
    return response.responseJSON;
};
$(function () {
    var date= new Date($.ajax({async: false}).getResponseHeader("Date"));
    var yy, mm, dd;
    yy = date.getFullYear();
    mm = ((date.getMonth() + 1) > 9) ? (date.getMonth() + 1) : ("0" + (date.getMonth() + 1));
    dd = (date.getDate() <= 9) ? ("0" + date.getDate()) : (date.getDate());
    var dateString = yy + "-" + mm + "-" + dd;
    $('#calendar').val(dateString);
});

