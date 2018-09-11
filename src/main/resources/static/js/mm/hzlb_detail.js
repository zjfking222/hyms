//回执列表详情
var FetchData = function (data, method, param, async) {
    var response =
        $.ajax({
            async: async,
            url: "/mm"+param,
            type: method,
            dataType: 'json',
            data: data,
            success: function (dataSource) {
                return dataSource;
            }});
    return response.responseJSON;
};
new Vue({
    el: '#app',
    data:{
        agenda:'',
        dines:'',
        stay:'',
        receipt:''
    },
    created:function () {
        var dataSource = FetchData({rid:parent.pushRid},'POST','/receipt/getReceiptDetail',false).data;
        this.dines = dataSource.dines;
        this.agenda = dataSource.agenda;
        this.stay = dataSource.stay;
        this.receipt = dataSource.receipt;
    }
});

