var udata,uid;
var vm = new Vue({
    el: '#words',
    data: {
        dataSource: "",
        tableData:""
    },
    created: function () {
        this.recommend();
    },
    methods: {
        recommend: function () {
            $.ajax({
                url: "/getNotice",
                type: "post",
                success: function (data) {
                    vm.dataSource = data;
                    // console.log(vm.dataSource.data);
                }
            })
        },
        updateNotice:function (id,data) {
            uid=id,
                udata = data;
            layer.open({
                title:"更新通知",
                type: 2,
                area: ['700px', '450px'],
                fixed: false, //不固定
                maxmin: true,
                content: 'tz_update.html'
            });
        },
        deleteNotice:function (id) {
            $.ajax({
                url:"/deleteNotice",
                type:"post",
                data:{id:id},
                success:function () {
                    alert("删除成功！");
                    vm.recommend();
                }
            });
        }
    }
});
$(function () {

    $("#add").on("click",function () {
        layer.open({
            title:"发布通知",
            type: 2,
            area: ['700px', '450px'],
            fixed: false, //不固定
            maxmin: true,
            content: 'tz_update.html',
        });
    });
    $("#select").on("click",function () {
        $.ajax({
            url: "/selectByCreater",
            type: "post",
            dataType:"json",
            data:{creater:$("#selectNotice").val()},
            success: function (data) {
                vm.dataSource=data;
                console.log(data);
            },
            error:function (jqXHR)
            {
                console.log(jqXHR)
            }
        })
    });
});

