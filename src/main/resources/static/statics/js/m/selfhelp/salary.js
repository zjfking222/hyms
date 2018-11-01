$(document).ready(function(){
    //从父页面取回参数
    var param = window.parent.iframeParam;
    //加载当前页面数据
    //mui.ajax('',{});
    //更改标题
    parent.updateTitle("2018年9月薪资明细");
});