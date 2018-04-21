$(function () {

    function dataSource() {
        return FetchData({page:1,number:15},'POST','/getCanteen',false).data;
    }
    function dataSourceState1() {
        return FetchData({page:1,number:15,state:1},'POST','/getCanteen',false).data;
    }
    var isDataSourcePointAll = true;

    var vm =
    new Vue({
        el:'#app',
        data: {
            data1:dataSource(),
            data2:
                {
                    name:'',
                    price:''
                }
        },
        methods: {
            onclick:function (id) {
                console.log(id);
            },
            onshowpart:function () {
                this.$data.data1 = dataSourceState1();
            },
            onshowall:function () {
                this.$data.data1 = dataSource();
            },
            onaddsubmit:function () {
                FetchData({name:this.$data.data2.name,price:this.$data.data2.price},
                    'POST','/addCanteen',false);
                isDataSourcePointAll ?
                this.$data.data1 = dataSource(): this.$data.data1 = dataSourceState1();
                this.$data.data2.name='';
                this.$data.data2.price='';
            },
            oneditstate:function (isGounding, id) {
                isGounding === 0?
                FetchData({id:id,state:0},
                        'POST','/updateCanteenState',false):
                FetchData({id:id,state:1},
                        'POST','/updateCanteenState',false);
                isDataSourcePointAll ?
                    this.$data.data1 = dataSource(): this.$data.data1 = dataSourceState1();
                this.$data.data2.price='';
            }
        }
    });

    //初始化
    $('.tr-add').fadeOut(0);
    $('.btn-showStateAll').fadeOut(0).click(function () {
        isDataSourcePointAll = true;
        $(this).fadeOut(0);
        $('.btn-showState1').fadeIn(500);
    });
    $('.btn-addNew').click(function () {
        $('.tr-add').fadeIn(500);
    });
    $('.btn-addCancel,.btn-addCommit').click(function () {
        $('.tr-add').fadeOut(0);
    });
    $('.btn-showState1').click(function () {
        isDataSourcePointAll = false;
        $(this).fadeOut(0);
        $('.btn-showStateAll').fadeIn(500);
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