$(document).ready(function () {

    var input = {'page' : 1,'number' : 7};
    var dataSource = FetchData(input,'POST','/getNotice',false);
    if(dataSource.code === 0){
        for (var i = 0; i < dataSource.data.notices.length; i++)
        {
            var list_item0 = list_item
                .replace("#title#",dataSource.data.notices[i].title);
            var list_item1 = list_item0
                .replace("#creator#",dataSource.data.notices[i].creater);
            var list_item2 = list_item1
                .replace("#created",dataSource.data.notices[i].created)
            $("#list").append(list_item2);
        }
    }
    else
    {
        alert(dataSource.msg);
    }
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

var list_item =
     "<a class='weui-cell weui-cell_access' href='tzx0.html'>"+
     "<div class='weui-cell__bd'>"+
     "<p>#title#</p><span class='name'>#creator#</span>"+
     "</div>"+
     "<div class='weui-cell__ft'>#created#</div>"+
     "</a>";