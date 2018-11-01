$(document).ready(function(){
    //加载当前页面数据
    //mui.ajax('',{});
    //获取当前系统时间并加载数据
    var year=new Date().getFullYear();
    var month=new Date().getMonth();
    mui(".attendance")[0].innerText=year + "年" + month+"月考勤明细";
    mui(document.body).on('tap', '.mui-bar-nav', function(e) {
        //应为每次使用时初始化，用完清理掉
        var dtpicker = new mui.DtPicker({
            type: "month",//设置日历初始视图模式
            beginDate: new Date(year, month-1),//设置开始日期,可查询上月、当月
            endDate: new Date(year, month),//设置结束日期
            labels: ['年', '月']//设置默认标签区域提示语
        });
        dtpicker.show(function(rs) {
            //将数据填入input，待修改
            year = rs.y.text;
            month = rs.m.text;
            //刷新当前页面数据
            mui(".attendance")[0].innerText=year + "年" + month+"月考勤明细";

            //释放组件资源
            dtpicker.dispose();
        });
    });
});