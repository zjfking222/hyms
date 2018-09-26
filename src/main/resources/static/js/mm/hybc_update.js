var data;
var postData;
var index = parent.layer.getFrameIndex(window.name);
$(function () {
    data = parent.pushData;
    if (data.id === 0) {

    } else {
        $('#firsttime').val(data.firsttime);
        $('#endtime').val(data.endtime);
        $('#interval').val(data.interval);
        $('#start').val(data.start);
        $('#end').val(data.end);
        $('#runtime').val(data.runtime);
        $('#remark').val(data.remark);
    }
    $('#submit').on('click', function () {
        if ($('#firsttime').val() !== '' && $('#endtime').val() !== ''  && $('#busnum').val() !== '' && $('#start').val() !== '' && $('#end').val() !== '') {
            postData = {
                id: data.id,
                mid: parent.window.location.search.substr(4),
                firsttime: $('#firsttime').val(),
                endtime: $('#endtime').val(),
                interval: $('#interval').val(),
                start: $('#start').val(),
                end: $('#end').val(),
                runtime: $('#runtime').val(),
                remark: $('#remark').val(),
            };
            if (data.id === 0) {
                var postback = FetchData(postData, 'POST', '/bus/add', false);
                if (postback.code === 0) {
                    postData.id = postback.data;
                    parent.vm.dataSource.add(postData);
                    parent.vm.dataSource.sync();
                    parent.layer.msg('添加成功');
                }
                else {
                    parent.layer.msg('添加失败');
                }
                parent.layer.close(index);

            }
            else {
                if (FetchData(postData, 'POST', '/bus/update', false).code === 0) {
                    parent.vm.dataSource.pushUpdate(postData);
                    parent.layer.msg('修改成功');
                }
                else {
                    parent.layer.msg('修改失败');
                }
                parent.layer.close(index);
            }
        }
        else {
            alert('首班车时间、末班车时间、起点、终点必填！');
        }
    });
    laydate.render({
        elem: '#firsttime'
        , type: 'datetime'
        , format: 'yyyy-MM-dd HH:mm:ss'
        , trigger: 'click'
        , btns: ['clear','now','confirm']
        , calendar: true
    });
    laydate.render({
        elem: '#endtime'
        , type: 'datetime'
        , format: 'yyyy-MM-dd HH:mm:ss'
        , trigger: 'click'
        , btns: ['clear','now','confirm']
        , calendar: true
        , isclear: true
    });
    laydate.render({
        elem: '#interval'
        , type: 'time'
        ,format: 'H小时m分钟'
        , trigger: 'click'
        , btns: ['clear','now','confirm']
    });
    laydate.render({
        elem: '#runtime'
        , type: 'time'
        ,format: 'H小时m分钟'
        , trigger: 'click'
        , btns: ['clear','now','confirm']
    })
});
var FetchData = function (data, method, param, async) {
    var response = $.ajax({
        async: async,
        url: "/mm" + param,
        type: method,
        dataType: 'json',
        data: data,
        success: function (dataSource) {
            return dataSource
        }
    });
    return response.responseJSON;
};