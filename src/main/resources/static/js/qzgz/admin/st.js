$(function () {

    function dataSource() {
        return FetchData({page:1,number:100},'POST','/web/getCanteen',false).data;
    }
    function dataSourceState1() {
        return FetchData({page:1,number:100,state:1},'POST','/web/getCanteen',false).data;
    }
    function dataSourceTodays() {
        return FetchData({},'POST','/web/getTodaysCanteen',false).data;
    }
    //对dataSourceTodays进行包装，用于data1访问
    function dataSourceTodaysPack() {
        return {canteens:dataSourceTodays()}
    }
    function dataSourceSearch(name) {
        return {canteens:FetchData({name:name},'POST','/admin/getCanteenByName',false).data}
    }
    //按钮是否只显示已上架的菜
    var isDataSourcePointAll = false;

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
                    'POST','/admin/addCanteen',false);
                    isDataSourcePointAll ?
                        this.$data.data1 = dataSource(): this.$data.data1 = dataSourceState1();
                this.$data.data2.name='';
                this.$data.data2.price='';
            },
            oneditstate:function (isGounding, id) {
                isGounding === 0?
                FetchData({id:id,state:0},
                        'POST','/admin/updateCanteenState',false):
                FetchData({id:id,state:1},
                        'POST','/admin/updateCanteenState',false);

                isDataSourcePointAll ?
                    this.$data.data1 = dataSource(): this.$data.data1 = dataSourceState1();
            },
            ondeletetoday:function (id) {
                FetchData({id:id},'POST','/admin/removeTodaysCanteen',false);

                isDataSourcePointAll ?
                    this.$data.data1 = dataSource(): this.$data.data1 = dataSourceState1();
            },
            oninserttoday:function (id) {
                FetchData({id:id},'POST','/admin/addTodaysCanteen',false);

                isDataSourcePointAll ?
                    this.$data.data1 = dataSource(): this.$data.data1 = dataSourceState1();
            },
            oneditinfo:function (name,price,id) {
                if (FetchData({name:name,price:price,id:id},'POST','/admin/updateCanteen',false).code===0)
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

                isDataSourcePointAll ?
                    this.$data.data1 = dataSource(): this.$data.data1 = dataSourceState1();

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
        layer.open({
            title:'今日食堂',
            type: 2,
            area: ['700px', '450px'],
            fixed: false, //不固定
            maxmin: true,
            content: '/qzgz/admin/st_today.html',
            end: function () {
                location.reload();
            }
        });
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