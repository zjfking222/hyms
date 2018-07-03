var pushData;
var vm = new Vue({
        el: "",
        data:{
            dataSource: [],
            activeItem: {id: 0, title: "", content: ""},
        },
        created:function () {
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
                // filterable: true,
                columnMenu: true,
                sortable: true,
                pageable: true,
                toolbar: [{
                    template: '<a role="button" class="k-button k-button-icontext  href="javascript:;" onclick="vm.edit()"><span class="k-icon k-i-add"></span>添加</a>' +
                        '<input type="text" class="k-input" id="search-input"/>'+
                    '<a role="button" class="k-button k-button-icontext  href="javascript:;" onclick="vm.search()"><span class="k-icon k-i-search"></span>搜索</a>'
                }],
                columns: [
                    {field: "name", title: "企业名称", headerAttributes: {"class": "grid-algin-center"},width: '200px'},
                    {field: "phone", title: "企业固话", headerAttributes: {"class": "grid-algin-center"},width: '150px'},
                    {field: "address", title: "地址", headerAttributes: {"class": "grid-algin-center"},width: '200px'},
                    {field: "contacter", title: "联系人", headerAttributes: {"class": "grid-algin-center"},width: '100px'},
                    {field: "cmobile", title: "手机", headerAttributes: {"class": "grid-algin-center"},width: '150px'},
                    {field: "cphone", title: "固话", headerAttributes: {"class": "grid-algin-center"},width: '150px'},
                    {field: "email", title: "邮箱", headerAttributes: {"class": "grid-algin-center"},width: '150px'},
                    {field: "btid", title: "业务类型", headerAttributes: {"class": "grid-algin-center"},width: '200px'},
                    {field: "remark", title: "备注", headerAttributes: {"class": "grid-algin-center"},width: '200px'},
                    {
                        command: [{
                            name: "showitem", text: "编辑", iconClass: "k-icon k-i-edit",
                            click: function (e) {
                                e.preventDefault();
                                var tr = $(e.target).closest("tr");
                                var data = this.dataItem(tr);
                                pushData = {
                                    id:data.id,
                                    name:data.name,
                                    address:data.address,
                                    contacter:data.contacter,
                                    cmobile:data.cmobile,
                                    cphone:data.cphone,
                                    email:data.email,
                                    btid:data.btid,
                                    remark:data.remark
                                };
                                console.log(pushData);
                            }
                        }, {name: "destroy", text: "删除", click:function () {
                                //todo delete
                            }}], title: " ", width: "180px"
                    }
                ]
            });
        },
        methods:{
            getDataSource: function () {
                this.dataSource = new kendo.data.DataSource({
                    transport: {
                        read: function (options) {
                            $.ajax({
                                url: "/crm/firm/get",
                                data: {
                                    'sort': options.data.sort=== undefined|| options.data.sort[0]=== undefined?
                                        undefined:options.data.sort[0].field,
                                    'dir': options.data.sort=== undefined|| options.data.sort[0]=== undefined?
                                        undefined:options.data.sort[0].dir,
                                    'page': options.data.page,
                                    'pageSize': options.data.pageSize,
                                    'value':$('#search-input').val()
                                },
                                method:'POST',
                                success: function (result) {
                                    if (result.code === 0){
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
                                phone: {type: "number", nullable: false},
                                address: {type: "string", nullable: false},
                                contacter: {type: "string", nullable: false},
                                cmobile: {type: "number", nullable: false},
                                cphone: {type: "number", nullable: false},
                                email: {type: "string", nullable: false},
                                btid: {type: "string", nullable: false, from:"btid.name"},
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
            edit:function () {
                layer.open({
                    title:'新增信息',
                    type: 2,
                    area: ['700px', '600px'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: '/crm/qyxx_update.html',
                    end: function () {
                        location.reload()
                    }
                });
            },
            search:function () {
                this.getDataSource($('#search-input').val());
                $("#grid").kendoGrid({
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
                    pageable: true,
                    toolbar: [{
                        template: '<a role="button" class="k-button k-button-icontext  href="javascript:;" onclick="vm.edit()"><span class="k-icon k-i-add"></span>添加</a>' +
                        '<input type="text" class="k-input" id="search-input"/>'+
                        '<a role="button" class="k-button k-button-icontext  href="javascript:;" onclick="vm.search()"><span class="k-icon k-i-search"></span>搜索</a>'
                    }],
                    columns: [
                        {field: "name", title: "企业名称", headerAttributes: {"class": "grid-algin-center"},width: '200px'},
                        {field: "phone", title: "企业固话", headerAttributes: {"class": "grid-algin-center"},width: '150px'},
                        {field: "address", title: "地址", headerAttributes: {"class": "grid-algin-center"},width: '200px'},
                        {field: "contacter", title: "联系人", headerAttributes: {"class": "grid-algin-center"},width: '100px'},
                        {field: "cmobile", title: "手机", headerAttributes: {"class": "grid-algin-center"},width: '150px'},
                        {field: "cphone", title: "固话", headerAttributes: {"class": "grid-algin-center"},width: '150px'},
                        {field: "email", title: "邮箱", headerAttributes: {"class": "grid-algin-center"},width: '150px'},
                        {field: "btid", title: "业务类型", headerAttributes: {"class": "grid-algin-center"},width: '200px'},
                        {field: "remark", title: "备注", headerAttributes: {"class": "grid-algin-center"},width: '200px'},
                        {
                            command: [{
                                name: "showitem", text: "编辑", iconClass: "k-icon k-i-edit",
                                click: function (e) {
                                    e.preventDefault();
                                    var tr = $(e.target).closest("tr");
                                    var data = this.dataItem(tr);
                                    pushData = {
                                        id:data.id,
                                        name:data.name,
                                        address:data.address,
                                        contacter:data.contacter,
                                        cmobile:data.cmobile,
                                        cphone:data.cphone,
                                        email:data.email,
                                        btid:data.btid,
                                        remark:data.remark
                                    }
                                    console.log(pushData);
                                }
                            }, {name: "destroy", text: "删除", click:function () {
                                //todo delete
                                }}], title: " ", width: "180px"
                        }
                    ]
                });
            }
        }

    }
)
