$(document).ready(function () {

    var dataSource = FetchData(null,'POST','/getBusinessTravel',false);
    if(dataSource.code === 0){
        for (var i = 0; i < dataSource.data.length; i++)
        {
            var list_item0 = list_item
                .replace("#title#",dataSource.data[i].title);
            var list_item1 = list_item0
                .replace('#img#',dataSource.data[i].img);
            $("#zoom").append(list_item1);
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
    "<h1 style='text-align: center!important; width: 100%'><b class='title'>#title#</b></h1>" +
    "<section>" +
    "<p>" +
    "<img src='#img#' style='margin: 0 auto; display: block' alt=''>" +
    "</p>" +
    "</section>" +
    "</section>";