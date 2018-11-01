$(function () {
    function dataSourceTodays() {
        return FetchData({plusDay:parent.pushPlusDay},'POST','/web/getCanteenHistoryByDay',false).data;
    }
    var vm =
        new Vue({
            el:'#app',
            data: {
                data1:dataSourceTodays()
            },
            methods: {
                ondeletetoday:function (id,meal) {
                    FetchData({id:id,meal:meal,plusDay:parent.pushPlusDay},'POST','/admin/removeTodaysCanteen',false);
                    vm.data1 = dataSourceTodays();
                }
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