$(function () {
    var vm = new Vue({
        el: '#list',
        data: {
            dataSource:"",
        },
        methods:{
            recommend:function () {
                $.ajax({
                    url:"/getSuggestion",
                    type:"post",
                    success:function (data){
                        vm.dataSource=data;
                        console.log(vm.dataSource);
                    }
                })
            }
        }
    });
    vm.recommend();
});