$(document).ready(function(){
    //加载当前页面数据
    //mui.ajax('',{});
    //获取当前系统时间并加载数据
    var date=new Date();
    mui(".mui-title")[0].innerText="刷卡日期：2018年10月31日 星期三";
    var cld = new Calendar({
        el: '#calendar-show',
        value: date, // 默认为new Date();
        callback: function(obj) {
            //添加详情标题
            mui(".mui-title")[0].innerText="刷卡日期："+ obj.year+"年"+ obj.month +"月" + obj.day+"日 "+ obj.week;
            //添加详情内容
            //console.log(JSON.stringify(obj));
        }
    })
});