$(function () {

    function dataSource() {
        return FetchData({page:1,number:15},'POST','/getCanteen',false).data;
    }
    function dataSourceState1() {
        return FetchData({page:1,number:15,state:1},'POST','/getCanteen',false).data;
    }
    function dataSourceTodays() {
        return FetchData({},'POST','/getTodaysCanteen',false).data;
    }
    //对dataSourceTodays进行包装，用于data1访问
    function dataSourceTodaysPack() {
        return {canteens:dataSourceTodays()}
    }
    function dataSourceSearch(name) {
        return {canteens:FetchData({name:name},'POST','/getCanteenByName',false).data}
    }
    //按钮是否只显示已上架的菜
    var isDataSourcePointAll = false;
    //按钮是否只显示今日菜单
    var isTodaysCanteen = false;

    var vm =
    new Vue({
        el:'#app',
        data: {
            data1:dataSourceState1(),
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
            onclick:function (id) {
                console.log(id);
            },
            onshowpart:function () {
                this.$data.data1 = dataSourceState1();
                isDataSourcePointAll = false;
            },
            onshowall:function () {
                this.$data.data1 = dataSource();
                isDataSourcePointAll = true;
            },
            onaddsubmit:function () {
                FetchData({name:this.$data.data2.name,price:this.$data.data2.price},
                    'POST','/addCanteen',false);
                isTodaysCanteen ?
                    this.$data.data1 = dataSourceTodaysPack() :
                    (isDataSourcePointAll ?
                        this.$data.data1 = dataSource(): this.$data.data1 = dataSourceState1());
                this.$data.data2.name='';
                this.$data.data2.price='';
            },
            oneditstate:function (isGounding, id) {
                isGounding === 0?
                FetchData({id:id,state:0},
                        'POST','/updateCanteenState',false):
                FetchData({id:id,state:1},
                        'POST','/updateCanteenState',false);

                isTodaysCanteen ?
                    this.$data.data1 = dataSourceTodaysPack() :
                    (isDataSourcePointAll ?
                        this.$data.data1 = dataSource(): this.$data.data1 = dataSourceState1());
            },
            ondeletetoday:function (id) {
                FetchData({id:id},'POST','/removeTodaysCanteen',false);

                isTodaysCanteen ?
                    this.$data.data1 = dataSourceTodaysPack() :
                (isDataSourcePointAll ?
                    this.$data.data1 = dataSource(): this.$data.data1 = dataSourceState1());
            },
            oninserttoday:function (id) {
                FetchData({id:id},'POST','/addTodaysCanteen',false);

                isTodaysCanteen ?
                    this.$data.data1 = dataSourceTodaysPack() :
                    (isDataSourcePointAll ?
                        this.$data.data1 = dataSource(): this.$data.data1 = dataSourceState1());
            },
            oneditinfo:function (name,price,id) {
                if (FetchData({name:name,price:price,id:id},'POST','/updateCanteen',false).code===0)
                {
                    layer.open({
                        title: '成功'
                        ,content: '修改已经生效！'
                    });
                }
                else
                {
                    layer.open({
                        title: '失败'
                        ,content: '注意：价格必须输入数字（如：11.5）'
                    });
                }

                isTodaysCanteen ?
                    this.$data.data1 = dataSourceTodaysPack() :
                    (isDataSourcePointAll ?
                        this.$data.data1 = dataSource(): this.$data.data1 = dataSourceState1());

            },
            onshowtodays:function () {
                this.$data.data1 = dataSourceTodaysPack();
                isTodaysCanteen = true;
            },
            onunshowtodays:function () {
                isTodaysCanteen = false;
                isDataSourcePointAll ?
                    this.$data.data1 = dataSource(): this.$data.data1 = dataSourceState1()
            },
            onsearch:function (name) {
                this.$data.data1 = dataSourceSearch(name)
            }
        }
    });

    /*
    ** 按钮组件初始化与显示控制
    ** 不参与逻辑
     */
    $('.tr-add').fadeOut(0);
    $('.btn-showStateAll').click(function () {
        $(this).fadeOut(0);
        $('.btn-showState1').fadeIn(500);
    });
    $('.btn-addNew').click(function () {
        $('.tr-add').fadeIn(500);
    });
    $('.btn-addCancel,.btn-addCommit').click(function () {
        $('.tr-add').fadeOut(0);
    });
    $('.btn-showState1').fadeOut(0).click(function () {
        $(this).fadeOut(0);
        $('.btn-showStateAll').fadeIn(500);
    });
    $('.btn-showTodays').click(function () {
       $(this).fadeOut(0);
       $('.btn-unshowTodays').fadeIn(500);
       layer.open({
            title: '提示'
            ,content: '当前显示今日的菜单，如需查看其他类型菜单请先点击［关闭查看今日菜单］！'
        });
    });
    $('.btn-unshowTodays').fadeOut(0).click(function () {
        $(this).fadeOut(0);
        $('.btn-showTodays').fadeIn(500);
    })
    
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