var vm = new Vue({
        el: '#words',
        data: {
            dataSource: ""
        },
        created: function () {
            this.recommend();
        },
        methods: {
            recommend: function () {
                $.ajax({
                    url: "/qzgz/web/getSuggestion",
                    type: "post",
                    success: function (data) {
                        vm.dataSource = data;
                        console.log(vm.dataSource.data);
                    }
                })
            },
        }
    });
