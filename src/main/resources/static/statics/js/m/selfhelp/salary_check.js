$(document).ready(function(){
    mui.init();
    //跳转考勤汇总查询结果页面
    mui(document.body).on('tap', '.mui-btn-primary', function(e) {
        //获取当前页面的title
        var title = document.title;
        //调用父页面方法跳转当前子页面
        parent.loadIframe("/m/selfhelp/salary.html", null);
        //更改标题
        parent.updateTitle(title);
    });
});