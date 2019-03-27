//初始化vue对象
var vm = new Vue({
    el:"#recordData",
    data:{//data对象创建后固定不可改，所以必须首先初始化出31条条数据，再去替换数据内容
        1:"",2:"",3:"",4:"",5:"",6:"",7:"",8:"",9:"",10:"",
        11:"",12:"",13:"",14:"",15:"",16:"",17:"",18:"",19:"",20:"",
        21:"",22:"",23:"",24:"",25:"",26:"",27:"",28:"",29:"",30:"",
        31:""
    },
    mounted (){//挂载el
        mui.init();
        //初始化日历表
        var cld = new Calendar({
            el: '#calendar-show',
            value: date,
            callback: function(obj) {
                vm.addDetail(obj);
            },
            prevmonthCallback: function (obj) {
                //重新获取数据
                var data = getRecordInfo(obj.year, obj.month);
                //替换原数据
                vm.updateData(data);
                //清除详情
                vm.clearDetail();
            },
            nextmonthCallback: function (obj) {
                //重新获取数据
                var data = getRecordInfo(obj.year, obj.month);
                //替换原数据
                vm.updateData(data);
                //清除详情
                vm.clearDetail();
            },
            prevyearCallback: function (obj) {
                //重新获取数据
                var data = getRecordInfo(obj.year, obj.month);
                //替换原数据
                vm.updateData(data);
                //清除详情
                vm.clearDetail();
            },
            nextyearCallback:function (obj) {
                //重新获取数据
                var data = getRecordInfo(obj.year, obj.month);
                //替换原数据
                vm.updateData(data);
                //清除详情
                vm.clearDetail();
            }
        });
        //渲染详情数据展示
        var date = new Date();
        var weekday=["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
        mui(".mui-title")[0].innerText="打卡日期："+ date.getFullYear() +"年"+ (date.getMonth() + 1) +"月" + date.getDate() +"日 "+ weekday[date.getDay()];
        //获取数据
        var data = getRecordInfo();
        //加载数据
        this.updateData(data);
        //添加详情内容
        if(this.$data[date.getDate()]){
            mui(".earliest-time")[0].innerText="最早刷卡时间：" + this.$data[date.getDate()].earliestTime;
            mui(".earliest-position")[0].innerText="位置：" + this.$data[date.getDate()].ecity;
            mui(".latest-time")[0].innerText="最晚刷卡时间：" + this.$data[date.getDate()].latestTime;
            mui(".latest-position")[0].innerText="位置：" + this.$data[date.getDate()].lcity;
        }else{
            mui(".earliest-time")[0].innerText="最早刷卡时间：";
            mui(".earliest-position")[0].innerText="位置：";
            mui(".latest-time")[0].innerText="最晚刷卡时间：";
            mui(".latest-position")[0].innerText="位置：";
        }
    },
    methods:{
        updateData: function(data){
            for(var key in this.$data){
                if(data && data[key]){
                    this.$data[key] = data[key];
                    if(data[key].recnum<2){//刷卡记录小于2条，则标红
                        var number = mui(".canChoose")[key-1].innerHTML;
                        number = number.replace("blank-out", "circle-out");
                        number = number.replace("blank-in", "circle-in");
                        mui(".canChoose")[key-1].innerHTML = number;
                    }else{//正常完成每天2次打卡则在数字下加蓝点
                        var number = mui(".canChoose")[key-1].innerHTML;
                        number = number.replace("blank-in", "normal");
                        mui(".canChoose")[key-1].innerHTML = number;
                    }
                }else{
                    this.$data[key] = "";
                }
            }
        },
        //添加详情展示数据
        addDetail:function (obj) {
            //添加详情标题
            mui(".mui-title")[0].innerText="打卡日期："+ obj.year+"年"+ obj.month +"月" + obj.day+"日 "+ obj.week;
            //添加详情内容
            if(vm.$data[obj.day]){
                mui(".earliest-time")[0].innerText="最早刷卡时间：" + vm.$data[obj.day].earliestTime;
                mui(".earliest-position")[0].innerText="位置：" + vm.$data[obj.day].ecity;
                mui(".latest-time")[0].innerText="最晚刷卡时间：" + vm.$data[obj.day].latestTime;
                mui(".latest-position")[0].innerText="位置：" + vm.$data[obj.day].lcity;
            }else{
                mui(".earliest-time")[0].innerText="最早刷卡时间：";
                mui(".earliest-position")[0].innerText="位置：";
                mui(".latest-time")[0].innerText="最晚刷卡时间：";
                mui(".latest-position")[0].innerText="位置：";
            }
        },
        //清除详情展示数据
        clearDetail:function () {
            //添加详情标题
            mui(".mui-title")[0].innerText="打卡日期：";
            //添加详情内容
            mui(".earliest-time")[0].innerText="最早刷卡时间：";
            mui(".earliest-position")[0].innerText="位置：";
            mui(".latest-time")[0].innerText="最晚刷卡时间：";
            mui(".latest-position")[0].innerText="位置：";
        }
    }
});

//从后台获取全部数据
function getRecordInfo(year, month){
    if(!year || !month){
        var date = new Date();
        year = date.getFullYear();
        month = date.getMonth() + 1;
    }
    var result;
    mui.ajax('/app/selfhelp/getRecord',{
        data:{
            year:year,
            month:month
        },
        dataType:'json',//服务器返回json格式数据
        type:'post',//HTTP请求类型
        timeout:10000,//超时时间设置为10秒；
        headers:{'Content-Type':'application/json'},
        async: false,
        success:function(data){
            if (data.code == 0) {
                //添加数据到返回值
                result = data.data;
            }else {
                console.log(data.msg)
            }
        },
        error:function(xhr,type,errorThrown){
            //异常处理；
            console.log(type);
        }
    });
    return result;
}