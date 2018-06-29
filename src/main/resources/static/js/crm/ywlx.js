var pushData;
var pushRid;
var FetchData = function (data, method, param, async) {
    var response =
        $.ajax({
            async: async,
            url: "/crm"+param,
            type: method,
            dataType: 'json',
            data: data,
            success: function (dataSource) {
                return dataSource;
            }});
    return response.responseJSON;
};
function dataSource() {
    return FetchData({},'POST','/business/get',false).data;
}
function dataSourceSearch(name) {
    return FetchData({name: name},'POST','/business/search',false).data;
}
// $(function () {
//     $('#treeview').css('width',$(window).width()-250+'px')
// });


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
        this.data1 = dataSourceSearch("");
    },
    methods: {
        onAdd:function () {
            pushData = {
                id: -1,
            };
            layer.open({
                title:'新增业务部门',
                type: 2,
                area: ['400px', '240px'],
                fixed: false, //不固定
                maxmin: false,
                resize:false,
                content: '/crm/ywlx_update.html',
                end: function () {
                    vm.data1 = dataSource()
                }
            });
        },
        onEdit:function (id,name) {
            pushData = {
                id:id,
                name:name
            };
            layer.open({
                title:'修改业务部门',
                type: 2,
                area: ['400px', '240px'],
                fixed: false, //不固定
                maxmin: false,
                resize:false,
                content: '/crm/ywlx_update.html',
                end: function () {
                    vm.data1 = dataSource()
                }
            });
        },
        onDel:function (id) {
            layer.confirm('确认删除吗？删除后将不可恢复。',{btn:['删除','取消']},
                function () {
                    FetchData({id:id},"POST","/business/del");
                    layer.msg('删除成功！',{time:500,
                        end:function () {
                            vm.data1 = dataSource();
                        }});
                },
                function () {
                });
        },
        onChoose:function (rid) {
            $('#treeview').attr('src','ymlx_user.html?id'+rid);
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



