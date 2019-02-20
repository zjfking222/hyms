//回执列表
var pushRid;
var pushMid;

var FetchData = function (data, method, param, async,contentType) {
    var response =
        $.ajax({
            async: async,
            url: param,
            type: method,
            dataType: 'json',
            data: data,
            contentType:contentType? "application/json;charset=utf-8":
                "application/x-www-form-urlencoded;charset=UTF-8",
            success: function (dataSource) {
                return dataSource;
            }});
    return response.responseJSON;
};
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
                        '<a role="button"  class="k-button k-button-icontext"  href="javascript:;" onclick="vm.add()"><span class="k-icon k-i-add"></span>添加客户</a>'+
                    '<a role="button"  class="k-button k-button-icontext"  href="javascript:;" onclick="vm.savaAsExcel()"><span class="k-icon k-i-excel"></span>导出Excel</a>'

                }],
                columns: [
                    {field: "fname", title: "来宾单位", headerAttributes: {"class": "grid-algin-center"}, width: '260px'},
                    {field: "name", title: "客户姓名", headerAttributes: {"class": "grid-algin-center"}, width: '110px'},
                    // {field: "sex", title: "性别",template:'<span>#=sex_display#</span>', headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "sex", title: "性别", headerAttributes: {"class": "grid-algin-center"}, width: '80px'},
                    {field: "nationality", title: "国籍", headerAttributes: {"class": "grid-algin-center"}, width: '80px'},
                    {field: "post", title: "职位", headerAttributes: {"class": "grid-algin-center"}, width: '110px'},
                    {field: "mobile", title: "手机", headerAttributes: {"class": "grid-algin-center"}, width: '110px'},
                    {field: "phone", title: "固话", headerAttributes: {"class": "grid-algin-center"}, width: '120px'},
                    {field: "vip", title: "VIP", headerAttributes: {"class": "grid-algin-center"}, width: '90px'},
                    {field: "btname", title: "所属部门", headerAttributes: {"class": "grid-algin-center"}, width: '110px'},
                    {field: "uname", title: "对接人", headerAttributes: {"class": "grid-algin-center"}, width: '90px'},
                    {field: "remark", title: "备注", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "implement", title: "落实情况", headerAttributes: {"class": "grid-algin-center"}, width: '110px'},
                    {field: "modified", title: "修改时间", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "state", title: "已提交",template:'<input type="checkbox" onclick="return false"  #=state_display#/>', headerAttributes: {"class": "grid-algin-center"}, width: '90px'},
                    {
                        // command: [{
                        //     name: "showitem", text: "编辑", iconClass: "k-icon k-i-edit",
                        //     click: function (e) {
                        //         e.preventDefault();
                        //         var tr = $(e.target).closest("tr");
                        //         var data = this.dataItem(tr);
                        //         pushRid = data.id;
                        //         pushMid = window.location.search.substr(4);
                        //         vm.edit(data.id);
                        //     }
                        // }, {
                        //     name: "destroy", text: "删除", iconClass: "k-icon k-i-delete"}], title: " ", width: "240px"
                        template:
                            '<a role="butto"  class="k-button k-button-icontext"  href="javascript:;" onclick="vm.edit('+'#=id #'+')">' +
                            '<span class="k-icon k-i-edit"></span>编辑</a>' +
                            '<a role="butto" class="k-button k-button-icontext"  href="javascript:;" onclick="vm.delete('+'#=id #'+')">' +
                            '<span class="k-icon k-i-delete"></span>删除</a>' +
                            '# if (state == "false") { #'+
                            '<a role="butto" class="k-button k-button-icontext"  href="javascript:;" onclick="vm.submit('+'#=id #'+')">' +
                            '<span class="k-icon k-i-arrow-chevron-right"></span>提交</a>' +
                            '# } #',
                        title: " ",
                        width: "240px"
                    }]
            });

            //行项目双击事件
            grid.dblclick('.k-grid-content tr', function () {
                var row = grid.data("kendoGrid").select();
                var data = grid.data("kendoGrid").dataItem(row);
                pushRid = data.id;
                this.layItem=layer.open({
                    title: '回执详情',
                    type: 2,
                    area: ['1050px', '650px'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: '/mm/hzlb_detail.html',
                    end: function () {
                    }
                });
                layer.full(this.layItem);
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
                                url: "/mm/receipt/getReceiptInfo",
                                data: {
                                    'filters': allMap === undefined ? "" : JSON.stringify(allMap),
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
                                            // result.data.data[i].vip?
                                            //     result.data.data[i].vip_display = 'checked':
                                            //     result.data.data[i].vip_display = '';
                                            // result.data.data[i].sex?
                                            //     result.data.data[i].sex_display = '男':
                                            //     result.data.data[i].sex_display = '女';
                                            // result.data.data[i].sendoff?
                                            //     result.data.data[i].sendoff_display = 'checked':
                                            //     result.data.data[i].sendoff_display = '';
                                            // result.data.data[i].pickup?
                                            //     result.data.data[i].pickup_display ='checked':
                                            //     result.data.data[i].pickup_display ='';
                                            // result.data.data[i].driving?
                                            //     result.data.data[i].driving_display ='checked':
                                            //     result.data.data[i].driving_display ='';
                                            result.data.data[i].state?
                                                result.data.data[i].state_display = 'checked':
                                                result.data.data[i].state_display = '';
                                        }
                                        for (var i=0;i < result.data.data.length;i++) {
                                            if (result.data.data[i].vip == 1) {
                                                result.data.data[i].vip = '非常重要'
                                            } else if (result.data.data[i].vip == 2) {
                                                result.data.data[i].vip = '重要'
                                            } else if (result.data.data[i].vip == 3) {
                                                result.data.data[i].vip = '一般'
                                            }else {
                                                result.data.data[i].vip = ''
                                            }
                                        }
                                        options.success({data: result.data.data, total: result.data.total});
                                        if(allMap){//由于kendo自身设计原因，使用筛选功能后其总条数显示不正确，故另加语句改回来
                                            var content = $(".k-pager-info.k-label")[0].innerHTML;
                                            content = content.substring(0,content.indexOf("共")+1) + " " + result.data.total + " 条数据";
                                            $(".k-pager-info.k-label")[0].innerHTML = content;
                                        }
                                    }
                                    else
                                        options.error(result);
                                },
                                error: function (result) {
                                    options.error(result);
                                }
                            });
                        },
                        // destroy: function (options) {
                        //     $.ajax({
                        //         url: "/mm/receipt/del",
                        //         data: {
                        //             'id': options.data.id
                        //         },
                        //         method: 'POST',
                        //         success: function (result) {
                        //             if (result.code === 0) {
                        //                 options.success(result);
                        //                 layer.msg('删除成功！', {time: 1000, icon: 1});
                        //             }
                        //             else{
                        //                 options.error(result);
                        //                 layer.msg('删除失败！（'+result.code+result.msg+'）', {time: 2300, icon: 2});
                        //             }
                        //
                        //         },
                        //         error: function (result) {
                        //             options.error(result);
                        //         }
                        //     });
                        // }
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
            edit:function (id) {
                pushRid = id;
                pushMid = window.location.search.substr(4);
                this.layItem = layer.open({
                    title: '编辑回执',
                    type: 2,
                    area: ['1050px', '650px'],
                    fixed: false, //不固定
                    maxmin: true,
                    shadeClose: true,
                    content: '/mm/hzlb_update.html',
                    end: function () {
                        $("#grid").data("kendoGrid").dataSource.read()
                    }
                });
                layer.full(this.layItem);
            },
            submit:function (id) {
                var ids = [];
                ids[0] = {id: id};
                layer.confirm('确认提交吗？',{btn:['提交','取消']},
                    function () {
                        FetchData(JSON.stringify(ids),'POST','/mm/receipt/setState',false, true).code === 0 ?
                            layer.msg('提交成功') :
                            layer.msg('提交失败');
                        $("#grid").data("kendoGrid").dataSource.read();
                    },
                    function () {
                    });
            },
            add:function () {
                pushMid = window.location.search.substr(4);
                this.layItem = layer.open({
                    title: '添加客户',
                    type: 2,
                    area: ['1050px', '95%'],
                    fixed: false, //不固定
                    maxmin: true,
                    shadeClose: true,
                    content: '/mm/hyhz_lb_add.html',
                    end: function () {
                        $("#grid").data("kendoGrid").dataSource.read()
                    }
                });
            },
            delete:function (id) {
                layer.confirm('确认删除吗？删除后将不可恢复。',{btn:['删除','取消']},
                    function () {
                        $.ajax({
                            url: "/mm/receipt/del",
                            data: {
                                'id': id
                            },
                            method: 'POST',
                            success: function (result) {
                                if (result.code === 0) {
                                    layer.msg('删除成功！', {time: 1000, icon: 1});
                                }
                                else{
                                    layer.msg('删除失败！（'+result.code+result.msg+'）', {time: 2300, icon: 2});
                                }
                                $("#grid").data("kendoGrid").dataSource.read();
                            }
                        });
                    },
                    function () {
                    });
            }
        }
    }
);

