var pushData = {id:'new'}
$(function () {
    var current_page = 1;
    function dataSource(page) {
        return FetchData({page:page,number:8},'POST','/web/getRecruit',false).data;
    }

    var vm = new Vue({
        el: '#app',
        data: {
            data1:dataSource(1)
        },
        methods: {
            onadd:function () {
                pushData = {
                    id:'new'
                };
                layer.open({
                    title:'添加岗位',
                    type: 2,
                    area: ['700px', '650px'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: '/qzgz/admin/zp_update.html',
                    end: function () {
                        vm.data1 = dataSource(current_page)
                    }
                });
            },
            onedit:function (name,edu,place,descr,age,sala,id,number) {
                pushData = {
                    name:name,
                    edu:edu,
                    place:place,
                    descr:descr,
                    age:age,
                    sala:sala,
                    id:id,
                    number:number
                };
                layer.open({
                    title:'更新岗位',
                    type: 2,
                    area: ['700px', '650px'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: '/qzgz/admin/zp_update.html',
                    end: function () {
                        vm.data1 = dataSource(current_page)
                    }
                });
                
            },
            ondelete:function (id) {
                layer.confirm('确认删除吗？删除后将不可恢复。',{btn:['删除','取消']},
                    function () {
                        FetchData({id:id},"POST","/admin/delRecruit");
                        layer.msg('删除成功！',{
                            end:function () {
                                vm.data1 = dataSource(current_page);
                        }});
                    },
                    function () {
                    });
            }
        }
    });

    //页码控制
    var pageBar = new Vue({
        el: '#page-bar',
        data: {
            cur: 1,//当前页码
            all: dataSource(1).totalPage //总页数
        },
        watch: {
            cur: function(oldValue , newValue){
                /*
                **
                ** 页码切换
                **
                 */
                vm.$data.data1 = dataSource(arguments[0]);
                current_page = arguments[0];
            }
        },
        methods: {
            btnClick: function(data){//页码点击事件
                if(data !== this.cur){
                    this.cur = data
                }
            },
            pageClick: function(){

            }
        },

        computed: {
            indexs: function(){
                var left = 1;
                var right = this.all;
                var ar = [];
                if(this.all>= 5){
                    if(this.cur > 3 && this.cur < this.all-2){
                        left = this.cur - 2
                        right = this.cur + 2
                    }else{
                        if(this.cur<=3){
                            left = 1
                            right = 5
                        }else{
                            right = this.all
                            left = this.all -4
                        }
                    }
                }
                while (left <= right){
                    ar.push(left)
                    left ++
                }
                return ar
            }

        }
    })
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