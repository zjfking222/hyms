var totalNumber = 0;

$(function() {
    //显示主界面
    $('.main').siblings('.subpage').fadeOut(0);
    //边栏选择
    $('.nav-sidebar li').on('click',function () {
        $(this).addClass('active').siblings().removeClass('active');
        $($(this).attr('data-id')).fadeIn(0).siblings('.subpage').fadeOut(0);
    });



    var dataSource = FetchData({page:1,number:15},'POST','/getCanteen',false).data;

    new Vue({
        el:'#app',
        data: dataSource,
        methods: {
            onclick:function (id) {
                console.log(id);
            }
        }
    });
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