$(document).ready(function () {

    var dataSource = FetchData({plusDay:0},'POST','/web/getCanteenHistoryByDay',false);
    if(dataSource.code === 0){
        for (var i = 0; i < dataSource.data.length; i++)
        {
            var list_item0 = list_item
                .replace("#caiming#",dataSource.data[i].name);
            var list_item1 = list_item0
                .replace("#jiage#",dataSource.data[i].price);
            $("#list").append(list_item1);
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
        url: "/qzgz"+param,
        type: method,
        dataType: 'json',
        data: data,
        success: function (dataSource) {
            return dataSource;
        }});
    return response.responseJSON;
};

var list_item =
    "<a class='weui-cell weui-cell_access' href='javascript:;'>" +
    "<div class='weui-cell__bd'>" +
    "<span>#caiming#</span></span>" +
    "</div>" +
    "<span><span class='price'>￥<span>#jiage#</span></span>" +
    "</a>";