var pageSize = 15;
var page = 1;
var index = 1;
var pushMid = window.location.search.substr(4);
$(function () {
    $('#loading').fadeOut(0);
    var dataSource = FetchData({
        page: page,
        pageSize: pageSize,
        mid: pushMid
    }, 'POST', '/receipt/getReceiptInfo', false).data;
    for (var i = 0; i < dataSource.data.length; i++) {
        var list1 = list.replace('#id#', dataSource.data[i].id)
            .replace('#fname#', dataSource.data[i].fname === null ? "" : dataSource.data[i].fname)
            .replace('#name#', dataSource.data[i].name)
            .replace('#post#', dataSource.data[i].post === null ? "" : dataSource.data[i].post)
            .replace('#mobile#', dataSource.data[i].mobile === null ? "" : dataSource.data[i].mobile)
            .replace('#pushMid#', pushMid);
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
            list = "<a class='weui-cell weui-cell_access' href='../mm/web_hzlb_update.html?id=#id#&mid=#pushMid#'>" +
                "<div class='weui-cell__bd'>" +
                "<p>#name#</p>" + "<p>#fname#</p>" + "</div>" +
                "<div class='weui-cell__ft'>" +
                "<p>#mobile#</p>" + "<p>#post#</p>" + "</div>" +
                "</a>";
            dataSource = FetchData({
                page: page,
                pageSize: pageSize,
                mid: pushMid,
                value: $('#searchInput').val()
            }, 'POST', '/receipt/getReceiptInfo', false).data;
            for (var i = 0; i < dataSource.data.length; i++) {
                var list2 = list.replace('#id#', dataSource.data[i].id)
                    .replace('#fname#', dataSource.data[i].fname === null ? "" : dataSource.data[i].fname)
                    .replace('#name#', dataSource.data[i].name)
                    .replace('#post#', dataSource.data[i].post === null ? "" : dataSource.data[i].post)
                    .replace('#mobile#', dataSource.data[i].mobile === null ? "" : dataSource.data[i].mobile)
                    .replace('#pushMid#', pushMid);
                $('#list').append(list2);
            }
        }
    });
    $('#tip').on("click", function () {
        // var scrollTop = $(this).scrollTop();
        // var scrollHeight = $(document).height();
        // var windowHeight = $(this).height();
        // var positionValue = (scrollTop + windowHeight) - scrollHeight;
        // if (positionValue > -1) {
        if (dataSource.data.length === pageSize) {
            $('#loading').fadeIn(0);
            $('#info').fadeOut(0);
            page++;
            dataSource = FetchData({
                page: page,
                pageSize: pageSize,
                mid: window.location.search.substr(4),
                value: $('#searchInput').val()
            }, 'POST', '/receipt/getReceiptInfo', false).data;
            for (var i = 0; i < dataSource.data.length; i++) {
                var list3 = list.replace('#id#', dataSource.data[i].id)
                    .replace('#fname#', dataSource.data[i].fname === null ? "" : dataSource.data[i].fname)
                    .replace('#name#', dataSource.data[i].name)
                    .replace('#post#', dataSource.data[i].post === null ? "" : dataSource.data[i].post)
                    .replace('#mobile#', dataSource.data[i].mobile === null ? "" : dataSource.data[i].mobile)
                    .replace('#pushMid#', pushMid);
                $('#list').append(list3);
            }
            page++;
            $('#loading').fadeOut(0);
            $('#info').fadeIn(0);
            if (dataSource.data.length < pageSize) {
                $('#tip').html('无更多数据');
            }else {
                var dataSource1 = FetchData({
                    page: page,
                    pageSize: pageSize,
                    mid: window.location.search.substr(4),
                    value: $('#searchInput').val()
                }, 'POST', '/receipt/getReceiptInfo', false).data;
                if(dataSource1.data.length === 0){
                    $('#tip').html('无更多数据');
                }else {
                    $('#tip').html('点击加载更多');
                }
            }
        }
        // else {
        //     $('#tip').html('无更多数据');
        // }
        // }
    });


});

function clear() {
    $('#searchInput').val('');
    window.location.reload()
}


var list = "<a class='weui-cell weui-cell_access' href='../mm/web_hzlb_update.html?id=#id#&mid=#pushMid#'>" +
    "<div class='weui-cell__bd'>" +
    "<p>#name#</p>" + "<p>#fname#</p>" + "</div>" +
    "<div class='weui-cell__ft'>" +
    "<p>#mobile#</p>" + "<p>#post#</p>" + "</div>" +
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

