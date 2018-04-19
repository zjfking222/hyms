$(document).ready(function () {

    var dataSource = FetchData(null,'POST','/getBusinessTravel',false);
    if(dataSource.code === 0){
        for (var i = 0; i < dataSource.data.length; i++)
        {
            var list_item0 = list_item
                .replace("#title#",dataSource.data[i].title);
            var list_item1 = list_item0
                .replace("#content#",dataSource.data[i].content);
            var list_item2 = list_item1
                .replace('#img#',dataSource.data[i].img);
            $("#zoom").append(list_item2);
        }
    }
    else
    {
        alert(dataSource.code);
    }
    new RTP.PinchZoom($('#zoom'), {});
});

//建议可以封装一个ajax方法
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

var list_item = "<section>" +
    "<h2 class='title'>#title#</h2>" +
    "<section>" +
    "<p>#content#</p>" +
    "<p>" +
    "<img src='#img#' alt=''>" +
    "</p>" +
    "</section>" +
    "</section>";