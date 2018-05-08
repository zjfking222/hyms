$(function () {
    function dataSourceTodays() {
        return FetchData({},'POST','/web/getTodaysCanteen',false).data;
    }
    //对dataSourceTodays进行包装，用于data1访问
    function dataSourceTodaysPack() {
        return {canteens:dataSourceTodays()}
    }
    var vm =
        new Vue({
            el:'#app',
            data: {
                data1:dataSourceTodaysPack(),
                data2:
                    {
                        name:'',
                        price:'',
                        search:''
                    },
                show:function (id) {
                    var dataTodays = dataSourceTodays();
                    for(var i = 0 ; i < dataTodays.length ; i++)
                    {
                        if(id === dataTodays[i].id)
                        {
                            return true;
                        }
                    }
                    return false;
                }
            },
            methods: {
                ondeletetoday:function (id) {
                    FetchData({id:id},'POST','/admin/removeTodaysCanteen',false);
                    this.$data.data1 = dataSourceTodaysPack();
                },
                oninserttoday:function (id) {
                    FetchData({id:id},'POST','/admin/addTodaysCanteen',false);
                    this.$data.data1 = dataSourceTodaysPack();
                },
                oneditinfo:function (name,price,id) {
                    FetchData({name:name,price:price,id:id},'POST','/admin/updateCanteen',false);
                    this.$data.data1 = dataSourceTodaysPack();
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