var pushid;
var pushtitle='';
var pushimg='';
$(function () {
    function dataSource() {
        return FetchData({},'POST','/getBusinessTravel',false).data;
    }
    var vm = new Vue({
        el:'#app',
        data: {
            data1 : dataSource()
        },
        methods: {
            onedit:function (id,title,img) {
                pushid = id;
                pushtitle = title;
                pushimg = img;
                layer.open({
                    title:'添加差旅信息',
                    type: 2,
                    area: ['700px', '450px'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: '/qzgz/admin/cl_add.html'
                });
            },
            onbrowse:function (img) {
                layer.open({
                    title:'浏览',
                    type: 2,
                    area: ['700px', '450px'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: img
                });
            }
        }
    });

});
var FetchData = function (data, method, param, async) {
    var response =
        $.ajax({
            async: async,
            url: param,
            type: method,
            dataType: 'json',
            data: data,
            success: function (dataSource) {
                return dataSource;
            }});
    return response.responseJSON;
};