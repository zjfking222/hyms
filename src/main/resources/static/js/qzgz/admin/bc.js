var pushData = {id:'new'}
$(function () {
    function dataSource() {
        return FetchData({},'POST','/web/getBus',false).data;
    }
    var vm = new Vue({
        el:'#app',
        data: {
            data1:dataSource()
        },
        methods: {
            onadd:function () {
                pushData = {
                    id:'new'
                };
                layer.open({
                    title:'添加班车',
                    type: 2,
                    area: ['700px', '600px'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: '/qzgz/admin/bc_update.html',
                    end: function () {
                        vm.data1 = dataSource()
                    }
                });
            },
            onedit:function (id,number,line) {
                pushData = {
                    number:number,
                    line:line,
                    id:id
                };
                layer.open({
                    title:'更新班车信息',
                    type: 2,
                    area: ['700px', '600px'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: '/qzgz/admin/bc_update.html',
                    end: function () {
                        vm.data1 = dataSource()
                    }
                });

            },
            ondelete:function (id) {
                layer.confirm('确认删除吗？删除后将不可恢复。',{btn:['删除','取消']},
                    function () {
                        FetchData({id:id},'POST','/admin/delBus',false);
                        layer.msg('删除成功!');
                        vm.data1 = dataSource();
                    },
                    function () {
                    });
            }
        }
    });




});
var FetchData = function (data, method, param, async) {
    var response =
        $.ajax({
            async: async,
            url: "/qzgz"+param,
            type: method,
            dataType: 'json',
            data: data,
            success: function (dataSource) {
                return dataSource;
            }});
    return response.responseJSON;
};