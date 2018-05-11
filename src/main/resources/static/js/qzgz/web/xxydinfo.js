var vm = new Vue({
    el: "#study",
    data: {
        study: {id: 0, title: '', study: '', creatername: '', created: ''}
    },
    created: function () {
        this.gestudy();
    },
    methods: {
        gestudy: function () {
            axios.get("/qzgz/web/study/get" + window.location.search).then(function (response) {
                if (response.data.code === 0) {
                    vm.study = response.data.data;
                    document.getElementById("content").innerHTML = vm.study.content;
                }
                else {
                    alert("发送错误，稍后查看！");
                    console.log(response.data);
                }
            }).catch(function (error) {
                alert("发送错误，稍后查看！");
                console.log(error);
            });
        }
    }
});