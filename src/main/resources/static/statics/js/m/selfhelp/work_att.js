$(document).ready(function(){
    mui.init();
    //跳转工作排班查询结果页面
    mui(document.body).on('tap', '.scheduling-btn', function(e) {
        //获取当前页面的title
        var title = mui(".scheduling-btn")[0].text;
        //调用父页面方法跳转当前子页面
        parent.loadIframe("/m/selfhelp/scheduling.html", null);
        //更改标题
        parent.updateTitle(title);
    });
    //跳转刷卡记录查询结果页面
    mui(document.body).on('tap', '.record-btn', function(e) {
        //获取当前页面的title
        var title = mui(".record-btn")[0].text;
        //调用父页面方法跳转当前子页面
        parent.loadIframe("/m/selfhelp/record.html", null);
        //更改标题
        parent.updateTitle(title);
    });
    //跳转考勤汇总查询结果页面
    mui(document.body).on('tap', '.attendance-btn', function(e) {
        //获取当前页面的title
        var title = mui(".attendance-btn")[0].text;
        //调用父页面方法跳转当前子页面
        parent.loadIframe("/m/selfhelp/attendance.html", null);
        //更改标题
        parent.updateTitle(title);
    });
});