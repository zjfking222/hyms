var pageSize = 15;
var page = 1;

$(function () {
    // $('#loading').fadeOut(0);
    var dataSource = FetchData({page: page, pageSize: pageSize}, 'POST', '/receipt/getMeeting', false).data;
    for (var i = 0; i < dataSource.data.length; i++) {
        if (dataSource.data[i].enddate >= getNowDate()) {
            var list1 = list.replace('#id#', dataSource.data[i].id)
                .replace('#name#', dataSource.data[i].name)
                .replace('#begindate#', dataSource.data[i].begindate)
                .replace('#enddate#', dataSource.data[i].enddate)
                .replace('#state#', dataSource.data[i].state);
            $('#list').append(list1);
        }
    }
    if (list1 === undefined ) {
        $('#tip').html('暂无会议信息');
    }
});
var list = "<a class='weui-cell weui-cell_access' href='web_hzlb.html?id=#id#'>" +
    "<div class='weui-cell__bd'>" +
    "<p>#name#</p>" + "<p class='name'>开始时间:#begindate#</p>" +
    "<span class='name'>结束时间:#enddate#</span>" +
    "</div>" +
    "<div class='weui-cell__ft' style='color: #0bb20c'>#state#</div>" +
    "</a>";
var FetchData = function (data, method, param, async) {
    var response =
        $.ajax({
            async: async,
            url: "/mm" + param,
            type: method,
            dataType: 'json',
            data: data,
            success: function (dataSource) {
                return dataSource;
            }
        });
    return response.responseJSON;
};

function getNowDate() {
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var h = date.getHours();
    var m = date.getMinutes();
    var s = date.getSeconds();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (day >= 1 && day <= 9) {
        day = "0" + day;
    }
    if (h >= 1 && h <= 9) {
        h = "0" + h;
    }
    if (m >= 1 && m <= 9) {
        m = "0" + m;
    }
    if (s >= 1 && s <= 9) {
        s = "0" + s;
    }
    var currentDate = year + "-" + month + "-" + day + " " + h + ":" + m + ":" + s;
    return currentDate;
}