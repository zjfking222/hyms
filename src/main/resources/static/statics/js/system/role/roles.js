var pushData;
var pushRid;
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
function dataSource() {
    return FetchData({},'POST','/roles/getAll',false).data;
}
function dataSourceSearch(name) {
    return FetchData({name: name},'POST','/roles/search',false).data;
}
function dataSourceList(rid) {
    return FetchData({rid: rid},'POST','/rolesPm/get',false).data;
}
//适应火狐浏览器重载页面时会加载地址的问题，手动初始化清空
$('#treeview').attr('src', '');
var vm = new Vue({
    el: '#left',
    data: {
        data0:'',
        data1: ''
    },
    // watch: {
    //     data0:function (newV,oldV) {
    //         this.data1 = dataSourceSearch(newV);
    //     }
    // },
    created:function () {
      this.data1 = dataSourceSearch();
    },
    methods: {
        onAdd:function () {
            pushData = {
                rid: -1,
            };
            layer.open({
                title:'新增角色信息',
                type: 2,
                area: ['700px', '85%'],
                fixed: false, //不固定
                maxmin: true,
                shadeClose: true,
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
                area: ['700px', '85%'],
                fixed: false, //不固定
                maxmin: true,
                shadeClose: true,
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
        },
        onChoose:function (rid) {
            $('#treeview').attr('src','tree.html?id'+rid);
            pushRid = rid;
            $("#rolenav li[data-id="+rid+"]").addClass('active')
                .siblings("li").removeClass('active');
        }
    }
});
$(function () {
    $(document).keyup(function(event){
        if(event.keyCode === 13){
            vm.data1 = dataSourceSearch($("#search").val());
        }
    });
});




