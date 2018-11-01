var pushMid = window.location.search.substr(5);
var pageSize = 15;
var page = 1;
$(function () {
    $('#loading').fadeOut(0);
    var dataSource = FetchData({
        page: page,
        pageSize: pageSize,
        mid: pushMid
    }, 'POST', '/bus/getAll', false).data;
    for (var i = 0; i < dataSource.data.length; i++) {
        var list1 = list.replace('#start#', dataSource.data[i].start)
            .replace('#end#', dataSource.data[i].end)
            .replace('#firsttime#', dataSource.data[i].firsttime)
            .replace('#endtime#', dataSource.data[i].endtime)
            .replace('#interval#', dataSource.data[i].interval === null ? "" : dataSource.data[i].interval)
            .replace('#runtime#', dataSource.data[i].runtime === null ? "" : dataSource.data[i].interval);
        $('#list').append(list1);
    }
    if (dataSource.data.length < pageSize) {
        $('#tip').html('无更多数据');
    } else {
        $('#tip').html('点击加载更多');
    }
    $("#form_submit").on("keyup", function (e) {
        if (e.keyCode === 13) {
            $('#list').empty();
            list = "<div class='bottom'>" + "<div class='line'>" + "<p class='start'>#start#</p>" + "<p class='arrow'>→</p>" + "<p class='end'>#end#</p>" + "</div>" +
                "<div class='type-time'>" + "<label>首班时间</label>" + "<p class='time'>#firsttime#</p>" + "</div>" +
                "<div class='type-time'>" + "<label>末班时间</label>" + "<p class='time'>#endtime#</p>" + "</div>" +
                "<div class='type-time'>" + "<label >间隔时间</label>" + "<p class='time'>#interval#</p>" + "</div>" +
                "<div class='type-time'>" + "<label>行驶时间</label>" + "<p class='time'>#runtime#</p>" + "</div>" + "</div>";
            dataSource = FetchData({
                page: page,
                pageSize: pageSize,
                mid: pushMid,
                value: $('#searchInput').val()
            }, 'POST', '/bus/getAll', false).data;
            for (var i = 0; i < dataSource.data.length; i++) {
                var list2 = list.replace('#start#', dataSource.data[i].start)
                    .replace('#end#', dataSource.data[i].end)
                    .replace('#firsttime#', dataSource.data[i].firsttime)
                    .replace('#endtime#', dataSource.data[i].endtime)
                    .replace('#interval#', dataSource.data[i].interval === null ? "" : dataSource.data[i].interval)
                    .replace('#runtime#', dataSource.data[i].runtime === null ? "" : dataSource.data[i].interval);
                $('#list').append(list2);
            }
            if (dataSource.data.length < pageSize) {
                $('#tip').html('无更多数据');
            } else {
                $('#tip').html('点击加载更多');
            }
        }
    });
    $('#tip').on("click", function () {
        if (dataSource.data.length === pageSize) {
            $('#loading').fadeIn(0);
            $('#info').fadeOut(0);
            page++;
            dataSource = FetchData({
                page: page,
                pageSize: pageSize,
                mid: pushMid,
                value: $('#searchInput').val()
            }, 'POST', '/bus/getAll', false).data;
            for (var i = 0; i < dataSource.data.length; i++) {
                var list3 = list.replace('#start#', dataSource.data[i].start)
                    .replace('#end#', dataSource.data[i].end)
                    .replace('#firsttime#', dataSource.data[i].firsttime)
                    .replace('#endtime#', dataSource.data[i].endtime)
                    .replace('#interval#', dataSource.data[i].interval === null ? "" : dataSource.data[i].interval)
                    .replace('#runtime#', dataSource.data[i].runtime === null ? "" : dataSource.data[i].runtime);
                $('#list').append(list3);
            }
            page++;
            $('#loading').fadeOut(0);
            $('#info').fadeIn(0);
            if (dataSource.data.length < pageSize) {
                $('#tip').html('无更多数据');
            } else {
                var dataSource1 = FetchData({
                    page: page,
                    pageSize: pageSize,
                    mid: pushMid,
                    value: $('#searchInput').val()
                }, 'POST', '/bus/getAll', false).data;
                if (dataSource1.data.length === 0) {
                    $('#tip').html('无更多数据');
                } else {
                    $('#tip').html('点击加载更多');
                }
            }
        }
    });
});

function clear() {
    $('#searchInput').val('');
    window.location.reload()
}

var list = "<div class='bottom'>" + "<div class='line'>" + "<p class='start'>#start#</p>" + "<p class='arrow'>→</p>" + "<p class='end'>#end#</p>" + "</div>" +
    "<div class='type-time'>" + "<label>首班时间</label>" + "<p class='time'>#firsttime#</p>" + "</div>" +
    "<div class='type-time'>" + "<label>末班时间</label>" + "<p class='time'>#endtime#</p>" + "</div>" +
    "<div class='type-time'>" + "<label >间隔时间</label>" + "<p class='time'>#interval#</p>" + "</div>" +
    "<div class='type-time'>" + "<label>行驶时间</label>" + "<p class='time'>#runtime#</p>" + "</div>" + "</div>";

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