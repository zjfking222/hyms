$(document).ready(function () {

    var input = {'page' : 1,'number' : 7};
    var dataSource = FetchData(input,'POST','/getCanteen',false);
    if(dataSource.code === 0){
        for (var i = 0; i < dataSource.data.canteens.length; i++)
        {
            var list_item0 = list_item
                .replace("#caiming#",dataSource.data.canteens[i].name);
            var list_item1 = list_item0
                .replace("#jiage#",dataSource.data.canteens[i].price);
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
    "<a class='weui-cell weui-cell_access' href='javascript:;'>" +
    "<div class='weui-cell__bd'>" +
    "<span>#caiming#</span>&nbsp<span class='price'>￥<span>#jiage#</span></span>" +
    "</div>" +
    "<span><img data-id='0' class='dianzan' src='/img/qzgz/dianzan.png'></span>" +
    "</a>";