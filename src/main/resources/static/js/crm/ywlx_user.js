var rid;
var vm = new Vue({
    el: "",
    data:{
        dataSource: [],
        activeItem: {id: 0, title: "", content: ""},
    },
    created:function () {
        rid = parent.pushRid;
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
            // filterable: true,
            columnMenu: true,
            sortable: true,
            // pageable: false,
            toolbar: [{
                template: '<a role="button" class="k-button k-button-icontext"  href="javascript:;" onclick="vm.edit()"><span class="k-icon k-i-edit"></span>编辑</a>'
            }],
            columns: [
                {field: "loginid", title: "账户名", headerAttributes: {"class": "grid-algin-center"}},
                {field: "lastname", title: "姓名", headerAttributes: {"class": "grid-algin-center"},},
                // {
                //     command: [ {name: "destroy", text: "删除"}], title: "操作",width:"180px"
                // }
            ]
        });
    },
    methods:{
        getDataSource: function () {
            this.dataSource = new kendo.data.DataSource({
                transport: {
                    read: function (options) {
                        $.ajax({
                            url: "/crm/businessUser/get",
                            data: {
                                'sort': options.data.sort=== undefined|| options.data.sort[0]=== undefined?
                                    undefined:options.data.sort[0].field,
                                'dir': options.data.sort=== undefined|| options.data.sort[0]=== undefined?
                                    undefined:options.data.sort[0].dir,
                                'btid': rid
                            },
                            method:'POST',
                            success: function (result) {
                                if (result.code === 0){
                                    options.success({data: result.data});
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
                            btid: {type: "string", nullable: false},
                            uid: {type: "string", nullable: false},
                            loginid: {type: "string", nullable: false},
                            lastname: {type: "string", nullable: false}
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
                // pageSize: 15,
                // serverPaging: true,
                // serverFiltering: true,
                serverSorting: true
            });
        },
        edit:function () {
            layer.open({
                title:'编辑人员',
                type: 2,
                area: ['700px', '600px'],
                fixed: false, //不固定
                maxmin: true,
                content: '/crm/ywlx_user_update.html',
                end: function () {
                    location.reload()
                }
            });
        }
    }

    }
)
