//添加客户
var index = parent.layer.getFrameIndex(window.name);
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
            layItem : null,
            grid: ''
        },
        created: function () {

            this.getDataSource();

            this.grid = $("#grid").kendoGrid({
                // selectable:"row",
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
                persistSelection: true,
                pageable: {
                    refresh: true,
                    pageSizes: true,
                    buttonCount: 5
                },
                toolbar: [{
                    template: '<input type="text" class="k-input" id="search-input"/>' +
                    '<a role="button"  class="k-button k-button-icontext  href="javascript:;" onclick="vm.search()"><span class="k-icon k-i-search"></span>搜索</a>'+
                    '<a role="button"  class="k-button k-button-icontext  href="javascript:;" onclick="vm.submit()"><span class="k-icon k-i-arrow-chevron-right"></span>提交</a>'

                }],
                columns: [
                    { selectable:true, width: '50px'},
                    {field: "fname", title: "来宾单位", headerAttributes: {"class": "grid-algin-center"}, width: '250px'},
                    {field: "name", title: "客户姓名", headerAttributes: {"class": "grid-algin-center"}, width: '100px'},
                    {field: "sex", title: "性别", headerAttributes: {"class": "grid-algin-center"}, width: '80px'},
                    {field: "nationality", title: "国籍", headerAttributes: {"class": "grid-algin-center"}, width: '80px'},
                    {field: "post", title: "职位", headerAttributes: {"class": "grid-algin-center"}, width: '120px'},
                    {field: "mobile", title: "手机", headerAttributes: {"class": "grid-algin-center"}, width: '120px'},
                    {field: "vip", title: "贵宾等级", headerAttributes: {"class": "grid-algin-center"}, width: '120px'},
                    // {field: "btid", title: "固话", headerAttributes: {"class": "grid-algin-center"}, width: '150px'},
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
                                url: "/crm/customerfirm/get",
                                data: {
                                    'filters': allMap === undefined ? "" : JSON.stringify(allMap),
                                    'sort': options.data.sort === undefined || options.data.sort[0] === undefined ?
                                        undefined : options.data.sort[0].field,
                                    'dir': options.data.sort === undefined || options.data.sort[0] === undefined ?
                                        undefined : options.data.sort[0].dir,
                                    'page': options.data.page,
                                    'pageSize': options.data.pageSize,
                                    'value': $('#search-input').val(),
                                    'mid': parent.pushMid,
                                },
                                method: 'POST',
                                success: function (result) {
                                    if (result.code === 0) {

                                        // for(var i = 0 ; i< result.data.data.length ; i++){
                                        //     result.data.data[i].vip?
                                        //         result.data.data[i].vip_display = 'checked':
                                        //         result.data.data[i].vip_display = '';
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
                        }
                        // destroy: function (options) {
                        //     $.ajax({
                        //         url: "///\\/\\/\\/\\\/\/\\\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/",
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
                                mobile: {type: "string" , nullable: false},
                                vip: {type: "string", nullable: false},
                                btid: {type: "string", nullable: false},
                                vip_display:{type:"string", nullable:false}
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
            submit:function () {
                var addList = [];
                var row = this.grid.data("kendoGrid").select();
                for(var i = 0; i < row.length ; i++){
                    addList.push(this.grid.data("kendoGrid").dataItem(row[i]))
                    addList[i].mid = parseInt(parent.pushMid);
                    addList[i].cid = addList[i].id;
                }
                FetchData(JSON.stringify(addList),'POST','/mm/receipt/add',false, true).code === 0 ?
                    parent.layer.msg('添加成功') :
                    parent.layer.msg('添加失败');
                // parent.layer.close(index);
                parent.location.reload();
            }

        }
    }
);

