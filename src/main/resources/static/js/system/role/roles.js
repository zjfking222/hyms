var pushData;
$(function () {
    function dataSource() {
        return FetchData({},'POST','/roles/getAll',false).data;
    }
    var vm = new Vue({
        el: '#left',
        data: {
            data1: dataSource(),
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
            }
        }

    });
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
