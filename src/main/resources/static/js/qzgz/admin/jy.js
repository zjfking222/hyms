$(function () {

    function dataSource(page) {
        return FetchData({page:page,number:8},'POST','/admin/getSuggestion',false).data;
    }

    var vm = new Vue({
        el: '#app',
        data: {
            data:dataSource(1)
        },
        methods: {
            onread:function (id) {
                FetchData({id:id,state:'0'},'POST',"/admin/setStateOfSuggestion",false);
                this.$data.data = dataSource(pageBar.$data.cur);
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
                vm.$data.data = dataSource(arguments[0]);
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