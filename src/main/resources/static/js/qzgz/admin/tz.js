var udata,uid,upage=0;
var vm = new Vue({
    el: '#words',
    created: function () {
        this.getNotice(upage);
    },
    data: {
        dataSource: "",
        tableData:""
    },
    methods: {
        getNotice:function(page){
            $.ajax({
                url: "/qzgz/web/getNotice",
                type: "post",
                dataType:"json",
                data:{pageNum:page},
                success: function (data) {
                    vm.dataSource=data;
                },
                error:function (jqXHR)
                {
                    console.log(jqXHR)
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
                url:"/qzgz/admin/deleteNotice",
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
            url: "/qzgz/admin/selectByCreater",
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
var newlist = new Vue({//分页
        el: '#app',
        created: function () {
            this.getPage();
        },
        data: {
            current_page: 1, //当前页
            pages: '', //总页数
            changePage:'',//跳转页
            nowIndex:0
        },
        computed:{
            show:function(){
                return this.pages && this.pages !==1
            },
            efont: function() {
                if (this.pages <= 7) return false;
                return this.current_page > 5
            },
            indexs: function() {
                var left = 1,
                    right = this.pages,
                    ar = [];
                if (this.pages >= 7) {
                    if (this.current_page > 5 && this.current_page < this.pages - 4) {
                        left = Number(this.current_page) - 3;
                        right = Number(this.current_page) + 3;
                    } else {
                        if (this.current_page <= 5) {
                            left = 1;
                            right = 7;
                        } else {
                            right = this.pages;
                            left = this.pages - 6;
                        }
                    }
                }
                while (left <= right) {
                    ar.push(left);
                    left++;
                }
                return ar;
            },
        },
        methods: {
            jumpPage: function(id) {
                this.current_page = id;
                upage=this.current_page-1;
                vm.getNotice(upage);
            },
            getPage:function () {
                $.ajax({
                    url: "/qzgz/web/totalPage",
                    type: "post",
                    success: function (data) {
                        newlist.pages =Math.ceil(data.data/10)+1 ;
                    }
                })
            }
        },
    })
