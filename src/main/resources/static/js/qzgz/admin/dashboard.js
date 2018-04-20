var totalNumber = 0;

$(function() {
    //显示主界面
    $('.main').siblings('.subpage').fadeOut(0);
    //边栏选择
    $('.nav-sidebar li').on('click',function () {
        $(this).addClass('active').siblings().removeClass('active');
        $($(this).attr('data-id')).fadeIn(0).siblings('.subpage').fadeOut(0);
    });

});