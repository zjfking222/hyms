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
        stay:''
    },
    created:function () {
        var dataSource = FetchData({rid:parent.pushRid},'POST','/receipt/getReceiptDetail',false).data;
        for(var i = 0 ; i < dataSource.dines.length; i++)
        this.agenda = dataSource.agenda;
        this.dines = dataSource.dines;
        this.stay = dataSource.stay;
    }
});

