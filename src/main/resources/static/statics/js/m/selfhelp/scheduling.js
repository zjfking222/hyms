//初始化vue对象
var vm = new Vue({
    el:"#schedulingData",
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
                var data = getSchedulingInfo(obj.year, obj.month);
                //替换原数据
                vm.updateData(data);
                //清除详情
                vm.clearDetail();
            },
            nextmonthCallback: function (obj) {
                //重新获取数据
                var data = getSchedulingInfo(obj.year, obj.month);
                //替换原数据
                vm.updateData(data);
                //清除详情
                vm.clearDetail();
            },
            prevyearCallback: function (obj) {
                //重新获取数据
                var data = getSchedulingInfo(obj.year, obj.month);
                //替换原数据
                vm.updateData(data);
                //清除详情
                vm.clearDetail();
            },
            nextyearCallback:function (obj) {
                //重新获取数据
                var data = getSchedulingInfo(obj.year, obj.month);
                //替换原数据
                vm.updateData(data);
                //清除详情
                vm.clearDetail();
            }
        });
        var date = new Date();
        var weekday=["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
        mui(".mui-title")[0].innerText="排班日期："+ date.getFullYear() +"年"+ (date.getMonth() + 1) +"月" + date.getDate() +"日 "+ weekday[date.getDay()];
        //获取数据
        var data = getSchedulingInfo(date.getFullYear(), date.getMonth() + 1);
        //加载数据
        this.updateData(data);
        //添加详情内容
        if(this.$data[date.getDate()]){
            mui(".startTime")[0].innerText="班次开始时间：" + this.$data[date.getDate()].begda;
            mui(".endTime")[0].innerText="班次结束时间：" + this.$data[date.getDate()].endda;
            mui(".workTime")[0].innerText="计划工作时间：" + this.$data[date.getDate()].stdaz;
            mui(".description")[0].innerText="班次描述：" + this.$data[date.getDate()].ztext;
        }else{
            mui(".startTime")[0].innerText="班次开始时间：";
            mui(".endTime")[0].innerText="班次结束时间：";
            mui(".workTime")[0].innerText="计划工作时间：";
            mui(".description")[0].innerText="班次描述：";
        }
    },
    methods:{
        updateData: function(data){
            for(var key in this.$data){
                if(data && data[key]){
                    this.$data[key] = data[key];
                }else{
                    this.$data[key] = "";
                }
            }
        },
        //添加详情展示数据
        addDetail:function (obj) {
            //添加详情标题
            mui(".mui-title")[0].innerText="排班日期："+ obj.year+"年"+ obj.month +"月" + obj.day+"日 "+ obj.week;
            //添加详情内容
            if(vm.$data[obj.day]){
                mui(".startTime")[0].innerText="班次开始时间：" + vm.$data[obj.day].begda;
                mui(".endTime")[0].innerText="班次结束时间：" + vm.$data[obj.day].endda;
                mui(".workTime")[0].innerText="计划工作时间：" + vm.$data[obj.day].stdaz;
                mui(".description")[0].innerText="班次描述：" + vm.$data[obj.day].ztext;
            }else{
                mui(".startTime")[0].innerText="班次开始时间：";
                mui(".endTime")[0].innerText="班次结束时间：";
                mui(".workTime")[0].innerText="计划工作时间：";
                mui(".description")[0].innerText="班次描述：";
            }
        },
        //清除详情展示数据
        clearDetail:function () {
            //添加详情标题
            mui(".mui-title")[0].innerText="排班日期：";
            //添加详情内容
            mui(".startTime")[0].innerText="班次开始时间：";
            mui(".endTime")[0].innerText="班次结束时间：";
            mui(".workTime")[0].innerText="计划工作时间：";
            mui(".description")[0].innerText="班次描述：";
        }
    }
});

//从后台获取全部数据
function getSchedulingInfo(year, month){
    if(!year || !month){
        var date = new Date();
        year = date.getFullYear();
        month = "9";//date.getMonth() + 1;
    }
    var result;
    mui.ajax('/app/selfhelp/getSchedulingInfo',{
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