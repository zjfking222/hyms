var pushPlusDay;
$(function () {

    $('.choosePage').fadeOut(0);

    function dataSource() {
        return FetchData({page:1,number:1000},'POST','/web/getCanteen',false).data;
    }
    function dataSourceState1() {
        return FetchData({page:1,number:1000,state:1},'POST','/web/getCanteen',false).data;
    }
    function dataSourceTodays(plusDay) {
        return FetchData({plusDay:plusDay},'POST','/web/getTodaysCanteen',false).data;
    }
    function dataSourceSearch(name,state) {
        return {canteens:FetchData({name:name,state:state},'POST','/admin/getCanteenByName',false).data}
    }
    //按钮是否只显示已上架的菜
    var isDataSourcePointAll = false;

    var vm =
    new Vue({
        el:'#app',
        data: {
            data1:dataSourceState1(),
            day: 0,
            data2:
                {
                    name:'',
                    type:'',
                    search:'',
                    dataTodays: dataSourceTodays(0)
                },
            show:function (id, meal) {
                for(var i = 0 ; i < this.data2.dataTodays.length ; i++)
                {
                    if(id === this.data2.dataTodays[i].canteen_id && meal === this.data2.dataTodays[i].meal)
                    {
                        return false;
                    }
                }
                return true;
            }
        },
        methods: {
            onclick:function (val) {
                this.$data.day = val;
                vm.$data.data1 = dataSourceState1();
                vm.data2.dataTodays = dataSourceTodays(this.$data.day);
            },
            onshowpart:function () {
                // this.$data.data1 = dataSourceState1();
                this.$data.data1 = dataSourceSearch(vm.data2.search,"1");
                isDataSourcePointAll = false;
            },
            onshowall:function () {
                // this.$data.data1 = dataSource();
                this.$data.data1 = dataSourceSearch(vm.data2.search);
                isDataSourcePointAll = true;
            },
            onaddsubmit:function () {
                FetchData({name:this.$data.data2.name,type:this.$data.data2.type},
                    'POST','/admin/addCanteen',false);
                    isDataSourcePointAll ?
                        this.$data.data1 = dataSource(): this.$data.data1 = dataSourceState1();
                this.$data.data2.name='';
                this.$data.data2.type='';
            },
            oneditstate:function (isGounding, id) {
                isGounding === 0?
                FetchData({id:id,state:0},
                        'POST','/admin/updateCanteenState',false):
                FetchData({id:id,state:1},
                        'POST','/admin/updateCanteenState',false);

                // isDataSourcePointAll ?
                //     this.$data.data1 = dataSource(): this.$data.data1 = dataSourceState1();
                isDataSourcePointAll ?
                    this.$data.data1 = dataSourceSearch(vm.data2.search):this.$data.data1 = dataSourceSearch(vm.data2.search,"1");
                vm.data2.dataTodays = dataSourceTodays(this.$data.day);
            },
            ondeletetoday:function (id,meal) {
                FetchData({id:id,meal:meal,plusDay:this.$data.day},'POST','/admin/removeTodaysCanteen',false);

                // isDataSourcePointAll ?
                //     this.$data.data1 = dataSource(): this.$data.data1 = dataSourceState1();
                isDataSourcePointAll ?
                    this.$data.data1 = dataSourceSearch(vm.data2.search):this.$data.data1 = dataSourceSearch(vm.data2.search,"1");
                vm.data2.dataTodays = dataSourceTodays(this.$data.day);
            },
            oninserttoday:function (id,meal) {
                FetchData({id:id,meal:meal,plusDay:this.$data.day},'POST','/admin/addTodaysCanteen',false);

                // isDataSourcePointAll ?
                //     this.$data.data1 = dataSource(): this.$data.data1 = dataSourceState1();
                isDataSourcePointAll ?
                    this.$data.data1 = dataSourceSearch(vm.data2.search):this.$data.data1 = dataSourceSearch(vm.data2.search,"1");
                vm.data2.dataTodays = dataSourceTodays(this.$data.day);
            },
            oneditinfo:function (name,type,id) {
                if (FetchData({name:name,type:type,id:id},'POST','/admin/updateCanteen',false).code===0)
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
                        ,content: '验证失效，请重新登陆！'
                    });
                }

                // isDataSourcePointAll ?
                //     this.$data.data1 = dataSource(): this.$data.data1 = dataSourceState1();
                isDataSourcePointAll ?
                    this.$data.data1 = dataSourceSearch(vm.data2.search):this.$data.data1 = dataSourceSearch(vm.data2.search,"1");
            },
            onsearch:function (name) {
                isDataSourcePointAll ?
                    this.$data.data1 = dataSourceSearch(name):this.$data.data1 = dataSourceSearch(name,"1");
            },
            onreset:function () {
                $('#search-input').val('');
                vm.data2.search = '';
                isDataSourcePointAll ?
                    this.$data.data1 = dataSourceSearch(name):this.$data.data1 = dataSourceSearch(name,"1");
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
        pushPlusDay = vm.$data.day;
        layer.open({
            title:'明日食堂',
            type: 2,
            area: ['700px', '600px'],
            fixed: false, //不固定
            maxmin: true,
            content: '/qzgz/admin/st_today.html',
            end: function () {
                isDataSourcePointAll ?
                    vm.$data.data1 = dataSource(): vm.$data.data1 = dataSourceState1();
                vm.data2.dataTodays = dataSourceTodays(vm.$data.day);
            }
        });
    });

    // $("[name='checkbox']").bootstrapSwitch({
    //     onText:"今日菜单",
    //     offText:"明日菜单",
    //     onColor:"info",
    //     offColor:"warning",
    //     handleWidth:70
    // }).on('switchChange.bootstrapSwitch', function(event, state) {
    //     if(state){
    //         vm.$data.day = 0;
    //         vm.$data.data1 = dataSourceState1();
    //         vm.data2.dataTodays = dataSourceTodays(vm.$data.day);
    //     }
    //     else {
    //         vm.$data.day = 1;
    //         vm.$data.data1 = dataSourceState1();
    //         vm.data2.dataTodays = dataSourceTodays(vm.$data.day);
    //     }
    // });
    laydate.render({
        elem: '#date'
        ,trigger: 'click'
        ,btns: ['confirm']
        ,calendar: true
        ,done: function(value, date){
            vm.$data.day = DateDiff(new Date().toLocaleDateString(),value);
            vm.$data.data1 = dataSourceState1();
            vm.data2.dataTodays = dataSourceTodays(vm.$data.day);
        }
    });

    function DateDiff(sDate1, sDate2){
        var aDate,oDate1,oDate2,iDays;
        aDate = sDate1.split("/");
        oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);
        aDate = sDate2.split("-");
        oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);
        iDays = parseInt((oDate2 - oDate1)/ 1000/ 60/ 60/ 24);
        return iDays
    }

    $('.nav-tab li').on('click',function () {
        $(this).addClass('active').siblings().removeClass('active');
        $('.'+$(this).attr('data-id')).fadeIn(0).siblings('div').fadeOut(0);
    })
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