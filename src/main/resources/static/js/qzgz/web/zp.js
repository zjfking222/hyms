const pageCount = 12;
var index = 1;
$(function(){
    $('#loading').fadeOut(0);
    var dataSource = FetchData({page:index,number:pageCount},'POST','/web/getRecruit',false).data;
    for (var i = 0 ; i < dataSource.recruits.length ; i ++)
    {
        var list_item0 = list_item
                        .replace('#id#',dataSource.recruits[i].id)
                        .replace('#name#',dataSource.recruits[i].name)
                        .replace('#edu#',dataSource.recruits[i].educate)
                        .replace('#place#',dataSource.recruits[i].work_place)
                        .replace('#salary#',dataSource.recruits[i].salary);
        $('#list').append(list_item0);
    }
    index ++;


    $(window).scroll(function() {
        var scrollTop = $(this).scrollTop();
        var scrollHeight = $(document).height();
        var windowHeight = $(this).height();
        var positionValue = (scrollTop + windowHeight) - scrollHeight;
        if (positionValue > 100) {
            if(index <= dataSource.totalPage)
            {
                $('#loading').fadeIn(0);
                $('#info').fadeOut(0);
                dataSource = FetchData({page:index,number:pageCount},'POST','/web/getRecruit',false).data;
                for (var i = 0 ; i < dataSource.recruits.length ; i ++)
                {
                    var list_item0 = list_item
                            .replace('#id#',dataSource.recruits[i].id)
                            .replace('#name#',dataSource.recruits[i].name)
                            .replace('#edu#',dataSource.recruits[i].educate)
                            .replace('#place#',dataSource.recruits[i].work_place)
                            .replace('#salary#',dataSource.recruits[i].salary);
                    $('#list').append(list_item0);
                }
                index ++;
                $('#loading').fadeOut(0);
                $('#info').fadeIn(0);
            }
            else {
                $('#tip').html('无更多数据');
            }
        }
    })
});
var list_item = "<a class='weui-cell weui-cell_access' href='zp0.html?id=#id#'>"+
                "<div class='weui-cell__bd'>"+
                "<p>#name#</p>" +
                "<span class='name'>#edu#&nbsp;#place#</span>"+
                "</div>"+
                "<div class='weui-cell__ft' style='color: #cc2222'>#salary#</div>"+
                "</a>";
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