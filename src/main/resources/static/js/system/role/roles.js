$(function () {
    function dataSource() {
        return FetchData({},'POST','/roles/getAll',false).data;
    }
    var vm = new Vue({
        el: '#left',
        data: {
            data1: dataSource(),
        },


    });
});
var FetchData = function (data, method, param, async) {
    var response =
        $.ajax({
            async: async,
            url: "/system"+param,
            type: method,
            dataType: 'json',
            data: data,
            success: function (dataSource) {
                return dataSource;
            }});
    return response.responseJSON;
};
