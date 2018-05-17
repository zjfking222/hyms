var pushData;
var ListData;
$(function () {
    ListData = [
        { id: 1, text: "系统管理",checked:false, items: [
                { id: 2, text: "Tables & Chairs" ,checked:true},
                { id: 3, text: "Sofas" ,checked:true},
                { id: 4, text: "Occasional Furniture" ,checked:true}
            ] },
        { id: 5, text: "衢州公众", items: [
                { id: 6, text: "Bed Linen" },
                { id: 7, text: "Curtains & Blinds" },
                { id: 8, text: "Carpets" }
            ] },
    ];
    function dataSource() {
        return FetchData({},'POST','/roles/getAll',false).data;
    }
    function dataSourceSearch(name) {
        return FetchData({name: name},'POST','/roles/search',false).data;
    }


    var vm = new Vue({
        el: '#left',
        data: {
            data0:'',
            data1: dataSourceSearch()
        },
        watch: {
            data0:function (newV,oldV) {
                this.data1 = dataSourceSearch(newV);
            }
        },
        methods: {
            onAdd:function () {
                pushData = {
                    rid: -1,
                };
                layer.open({
                    title:'新增角色信息',
                    type: 2,
                    area: ['700px', '600px'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: '/system/role/roles_update.html',
                    end: function () {
                        vm.data1 = dataSource()
                    }
                });
            },
            onEdit:function (id,name) {
                pushData = {
                    rid:id,
                    name:name
                };
                layer.open({
                    title:'修改角色信息',
                    type: 2,
                    area: ['700px', '600px'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: '/system/role/roles_update.html',
                    end: function () {
                        vm.data1 = dataSource()
                    }
                });
            },
            onDel:function (id) {
                layer.confirm('确认删除吗？删除后将不可恢复。',{btn:['删除','取消']},
                    function () {
                        FetchData({id:id},"POST","/roles/delete");
                        layer.msg('删除成功！',{time:500,
                            end:function () {
                                vm.data1 = dataSource();
                            }});
                    },
                    function () {
                    });
            }
            // onSearch:function () {
            //     console.log(this.data0);
            //     this.data1 = dataSourceSearch(this.data0);
            // }
        }
    });
    function checkedNodeIds(nodes, checkedNodes) {
        for (var i = 0; i < nodes.length; i++) {
            if (nodes[i].checked) {

                checkedNodes.push(nodes[i].id);
            }

            if (nodes[i].hasChildren) {
                checkedNodeIds(nodes[i].children.view(), checkedNodes);
            }
        }
    }
    function onCheck() {
        var checkedNodes = [],
            treeView = $("#treeview").data("kendoTreeView");

        checkedNodeIds(treeView.dataSource.view(), checkedNodes);
        console.log(checkedNodes)
    }

    $("#treeview").kendoTreeView({
        checkboxes: {
            checkChildren: true
        },
        dataSource: ListData,
        check: onCheck
    })

});
var FetchData = function (data, method, param, async) {
    var response =
        $.ajax({
            async: async,
            url: "/system"+param,
            type: method,
            dataType: 'json',
            data: data,
            success: function (dataSource) {
                return dataSource;
            }});
    return response.responseJSON;
};
