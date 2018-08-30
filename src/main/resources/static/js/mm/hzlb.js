//回执列表
var pushRid;
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
                    {field: "fname", title: "单位名称", headerAttributes: {"class": "grid-algin-center"}, width: '300px'},
                    {field: "name", title: "客户姓名", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    // {field: "sex", title: "性别",template:'<span>#=sex_display#</span>', headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "sex", title: "性别", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "nationality", title: "国籍", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "post", title: "职位", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "mobile", title: "手机", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "phone", title: "固话", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "vip", title: "贵宾",template:'<input type="checkbox" onclick="return false"  #=vip_display#/>', headerAttributes: {"class": "grid-algin-center"}, width: '100px'},
                    {field: "btname", title: "业务类型", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "driving", title: "是否自驾",template:'<input type="checkbox" onclick="return false"  #=driving_display#/>', headerAttributes: {"class": "grid-algin-center"}, width: '100px'},
                    {field: "pickup", title: "派车接",template:'<input type="checkbox" onclick="return false"  #=pickup_display#/>', headerAttributes: {"class": "grid-algin-center"}, width: '100px'},
                    {field: "arrivalType", title: "到达地点", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "arrivalDate", title: "到达日期", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "arrivalInfo", title: "到达信息", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "arrivalRemark", title: "到达备注", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "sendoff", title: "派车送",template:'<input type="checkbox" onclick="return false"  #=sendoff_display#/>', headerAttributes: {"class": "grid-algin-center"}, width: '100px'},
                    {field: "returnType", title: "回程地点", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "departureDate", title: "回程日期", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "departureInfo", title: "回程信息", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "departureRemark", title: "回程备注", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "uname", title: "负责人", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "remark", title: "备注", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "implement", title: "落实情况", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "modified", title: "修改时间", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "state", title: "已提交",template:'<input type="checkbox" onclick="return false"  #=state_display#/>', headerAttributes: {"class": "grid-algin-center"}, width: '100px'},
                    {
                        command: [{
                            name: "showitem", text: "编辑", iconClass: "k-icon k-i-edit",
                            click: function (e) {
                                e.preventDefault();
                                var tr = $(e.target).closest("tr");
                                var data = this.dataItem(tr);
                                pushRid = data.id;
                                pushMid = window.location.search.substr(4);
                                vm.edit(data.id);
                            }
                        }, {
                            name: "destroy", text: "删除", iconClass: "k-icon k-i-delete"}], title: " ", width: "240px"
                    }]
            });

            //行项目双击事件
            grid.dblclick('.k-grid-content tr', function () {
                var row = grid.data("kendoGrid").select();
                var data = grid.data("kendoGrid").dataItem(row);
                pushRid = data.id;
                layer.open({
                    title: '回执详情',
                    type: 2,
                    area: ['1050px', '650px'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: '/mm/hzlb_detail.html',
                    end: function () {
                    }
                });
            });
        },
        methods: {
            getDataSource: function () {
                this.dataSource = new kendo.data.DataSource({
                    transport: {
                        read: function (options) {
                            $.ajax({
                                url: "/mm/receipt/getReceiptInfo",
                                data: {
                                    'sort': options.data.sort === undefined || options.data.sort[0] === undefined ?
                                        undefined : options.data.sort[0].field,
                                    'dir': options.data.sort === undefined || options.data.sort[0] === undefined ?
                                        undefined : options.data.sort[0].dir,
                                    'page': options.data.page,
                                    'pageSize': options.data.pageSize,
                                    'value': $('#search-input').val(),
                                    'mid': window.location.search.substr(4)
                                },
                                method: 'POST',
                                success: function (result) {
                                    if (result.code === 0) {
                                        //数据转化，checked属性和性别属性，用于显示模板
                                        for(var i = 0 ; i< result.data.data.length ; i++){
                                            result.data.data[i].vip?
                                                result.data.data[i].vip_display = 'checked':
                                                result.data.data[i].vip_display = '';
                                            // result.data.data[i].sex?
                                            //     result.data.data[i].sex_display = '男':
                                            //     result.data.data[i].sex_display = '女';
                                            result.data.data[i].sendoff?
                                                result.data.data[i].sendoff_display = 'checked':
                                                result.data.data[i].sendoff_display = '';
                                            result.data.data[i].pickup?
                                                result.data.data[i].pickup_display ='checked':
                                                result.data.data[i].pickup_display ='';
                                            result.data.data[i].driving?
                                                result.data.data[i].driving_display ='checked':
                                                result.data.data[i].driving_display ='';
                                            result.data.data[i].state?
                                                result.data.data[i].state_display = 'checked':
                                                result.data.data[i].state_display = '';
                                        }

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
                                url: "/mm/receipt/del",
                                data: {
                                    'id': options.data.id
                                },
                                method: 'POST',
                                success: function (result) {
                                    if (result.code === 0) {
                                        options.success(result);
                                        layer.msg('删除成功！', {time: 1000, icon: 1});
                                    }
                                    else{
                                        options.error(result);
                                        layer.msg('删除失败！（'+result.code+result.msg+'）', {time: 2300, icon: 2});
                                    }

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
                                fname: {type: "string", nullable: false},
                                name: {type: "string", nullable: false},
                                sex: {type: "string", nullable: false},
                                nationality: {type: "string", nullable: false},
                                post: {type: "string", nullable: false},
                                mobile: {type: "string", nullable: false},
                                phone: {type: "string", nullable: false},
                                vip: {type: "string", nullable: false},
                                btname: {type: "string", nullable: false},
                                driving: {type: "string", nullable: false},
                                pickup: {type: "string", nullable: false},
                                arrivalType: {type: "string", nullable: false},
                                arrivalDate: {type: "string", nullable: false},
                                arrivalInfo: {type: "string", nullable: false},
                                arrivalRemark: {type: "string", nullable: false},
                                sendoff: {type: "string", nullable: false},
                                returnType: {type: "string", nullable: false},
                                departureDate: {type: "string", nullable: false},
                                departureInfo: {type: "string", nullable: false},
                                departureRemark: {type: "string", nullable: false},
                                uname: {type: "string", nullable: false},
                                remark: {type: "string", nullable: false},
                                implement: {type: "string", nullable: false},
                                modified: {type: "string", nullable: false},
                                state: {type: "string", nullable: false},
                                vip_display:{type:"string", nullable:false},
                                // sex_display:{type:"string", nullable:false},
                                driving_display:{type:"string", nullable:false},
                                pickup_display:{type:"string", nullable:false},
                                sendoff_display:{type:"string", nullable:false},
                                state_display:{type:"string", nullable:false}
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
            },
            edit:function () {
                this.layItem = layer.open({
                    title: '编辑回执',
                    type: 2,
                    area: ['1050px', '650px'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: '/mm/hzlb_update.html',
                    end: function () {
                        $("#grid").data("kendoGrid").dataSource.read()
                    }
                });
                layer.full(this.layItem);
            }
        }
    }
);

