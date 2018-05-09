var vm = new Vue({
    el: "#content",
    data: {
        list: [],
        skip: {page: 1, pageSize: 15},
        total: 0
    },
    created: function () {
        this.getList();
    },
    methods: {
        getList: function () {
            document.getElementById("loading").style.display = "block";
            document.getElementById("loadmore").style.display = "none";
            axios.post("/qzgz/web/notice/get", JSON.stringify(this.skip), {
                headers: {
                    "Content-Type": "application/json;charset=utf-8"
                }
            }).then(function (response) {
                if (response.data.code == 0) {
                    vm.list= vm.list.concat(response.data.data.data);
                    vm.total = response.data.data.total;
                    if (vm.skip.page * vm.skip.pageSize < vm.total) {
                        document.getElementById("loadmore").style.display = "block";
                    }
                    ++vm.skip.page;
                }
                else {
                    alert("发送错误，稍后查看！");
                    console.log(response.data);
                }
                document.getElementById("loading").style.display = "none";
            }).catch(function (error) {
                alert("发送错误，稍后查看！");
                console.log(error);
                document.getElementById("loading").style.display = "none";
            });

        }
    }
});