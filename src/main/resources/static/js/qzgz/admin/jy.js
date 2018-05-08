var upage=0;
var vm = new Vue({
        el: '#words',
        data: {
            dataSource: ""
        },
        created: function () {
            this.recommend(upage);
        },
        methods: {
            recommend: function (page) {
                $.ajax({
                    url: "/qzgz/web/getSuggestion",
                    type: "post",
                    data:{pageNum:page},
                    success: function (data) {
                        vm.dataSource = data;
                    }
                })
            },
        }
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
            vm.recommend(upage);
        },
        getPage:function () {
            $.ajax({
                url: "/qzgz/web/totalPageS",
                type: "post",
                success: function (data) {
                    newlist.pages =Math.ceil(data.data/10);
                }
            })
        }
    },
})
