$(document).ready(function () {

    function dataSourceTodays() {
        return FetchData({plusDay:0},'POST','/web/getCanteenHistoryByDay',false).data;
    }
    var vm =
        new Vue({
            el:'#app',
            data: {
                data1:dataSourceTodays()
            }
        });
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