var pushData;
var vm = new Vue({
        el: "",
        data: {
            dataSource: [],
            activeItem: {id: 0, title: "", content: ""},
            layItem : null
        },
        created: function () {

            this.getDataSource();

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
                filterable: true,
                columnMenu: true,
                sortable: true,
                // pageable: true,
                pageable: {
                    refresh: true,
                    pageSizes: true,
                    buttonCount: 5
                },
                toolbar: [{
                    template: '<a role="button" class="k-button k-button-icontext"  href="javascript:;" onclick="vm.add()"><span class="k-icon k-i-add"></span>添加</a>' +
                        '<a role="button" class="k-button k-button-icontext"  href="/down/excel?url='+ encodeURIComponent('/crm/template/customer.xlsx')+'"><span class="k-icon k-i-download"></span>模板文件</a>' +
                        '<a role="button" class="k-button k-button-icontext"  href="javascript:;" onclick="vm.batchadd()"><span class="k-icon k-i-add"></span>批量添加</a>'+
                    '<input type="text" class="k-input" id="search-input"/>' +
                    '<a role="button"  class="k-button k-button-icontext"  href="javascript:;" onclick="vm.search()"><span class="k-icon k-i-search"></span>搜索</a>' +
                        '<a role="button"  class="k-button k-button-icontext"  href="javascript:;" onclick="vm.savaAsExcel()"><span class="k-icon k-i-excel"></span>导出Excel</a>'
                }],
                columns: [
                    {field: "name", title: "姓名", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "post", title: "职位", headerAttributes: {"class": "grid-algin-center"}, width: '120px'},
                    {field: "nationality", title: "国籍", headerAttributes: {"class": "grid-algin-center"}, width: '120px'},
                    {field: "address", title: "地址", headerAttributes: {"class": "grid-algin-center"}, width: '200px'},
                    // {field: "sex", title: "性别",template:'<span>#=sex_display#</span>', headerAttributes: {"class": "grid-algin-center"}, width: '80px'},
                    {field: "sex", title: "性别", headerAttributes: {"class": "grid-algin-center"}, width: '80px'},
                    {field: "mobile", title: "手机", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "phone", title: "固话", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
                    {field: "email", title: "邮箱", headerAttributes: {"class": "grid-algin-center"}, width: '200px'},
                    {field: "btid", headerAttributes: {"class": "grid-algin-center"}, width: '0px'},
                    {field: "btname", title: "所属部门", headerAttributes: {"class": "grid-algin-center"}, width: '200px'},
                    {field: "fid", headerAttributes: {"class": "grid-algin-center"}, width: '0px'},
                    {field: "fname", title: "企业单位", headerAttributes: {"class": "grid-algin-center"}, width: '200px'},
                    {field: "vip", title: "客户等级", headerAttributes: {"class": "grid-algin-center"}, width: '120px'},
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
                                    post: data.post,
                                    nationality: data.nationality,
                                    address:data.address,
                                    sex:data.sex,
                                    mobile:data.mobile,
                                    phone:data.phone,
                                    email:data.email,
                                    btid:data.btid,
                                    fid:data.fid,
                                    vip:data.vip,
                                    remark:data.remark
                                };
                                vm.edit();
                            }
                        }, {
                            name: "destroy", text: "删除", iconClass: "k-icon k-i-delete"}], title: " ", width: "240px"
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
                                url: "/crm/customer/get",
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
                                        //数据转化，checked属性和性别属性，用于显示模板
                                        // for(var i = 0 ; i< result.data.data.length ; i++){
                                            // result.data.data[i].vip?
                                            //     result.data.data[i].vip_display = 'checked':
                                            //     result.data.data[i].vip_display = '';
                                            // result.data.data[i].sex?
                                            //     result.data.data[i].sex_display = '男':
                                            //     result.data.data[i].sex_display = '女';
                                        // }
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
                                url: "/crm/customer/del",
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
                                name: {type: "string", nullable: false},
                                post: {type: "string", nullable: false},
                                nationality: {type: "string", nullable: false},
                                address: {type: "string", nullable: false},
                                sex: {type: "string", nullable: false},
                                mobile: {type: "string", nullable: false},
                                phone: {type: "string", nullable: false},
                                email: {type: "string", nullable: false},
                                btname: {type: "string", nullable: false, from: "btid.name"},
                                btid: {type: "string", nullable: false, from: "btid.id"},
                                fname: {type: "string", nullable: false, from : "fid.name"},
                                fid: {type: "string", nullable: false, from: "fid.id"},
                                vip: {type:"string", nullable: false},
                                remark: {type: "string", nullable: false},
                                vip_display:{type:"string", nullable:false},
                                // sex_display:{type:"string", nullable:false}
                            }
                        }
                    },
                    error: function (e) {
                        this.cancelChanges();
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
                layer.close(vm.layItem)
                pushData = {
                    id : 0
                };
                this.layItem = layer.open({
                    title: '新增信息',
                    type: 2,
                    area: ['700px', '80%'],
                    fixed: false, //不固定
                    maxmin: true,
                    shadeClose: true,
                    content: '/crm/khxx_update.html',
                    end: function () {
                    }
                });
            },
            edit:function(){
                layer.close(vm.layItem)
                this.layItem = layer.open({
                    title:'编辑信息',
                    type: 2,
                    area: ['700px', '80%'],
                    fixed: false, //不固定
                    maxmin: true,
                    shadeClose: true,
                    content: '/crm/khxx_update.html',
                    end: function () {
                    }
                });
            },
            search: function () {
                // vm.dataSource.read()
                $("#grid").data("kendoGrid").dataSource.read()
            },
            savaAsExcel: function () {
                $("#grid").data("kendoGrid").saveAsExcel();
            },
            batchadd: function () {
                layer.close(vm.layItem)
                pushData = {
                    id : 0
                };
                this.layItem = layer.open({
                    title: '批量添加用户',
                    type: 2,
                    area: ['700px', '80%'],
                    fixed: false, //不固定
                    maxmin: true,
                    shadeClose: true,
                    content: '/crm/khxx_batch.html',
                    end: function () {
                        $("#grid").data("kendoGrid").dataSource.read()
                    }
                });
            }
        }
    }
);
