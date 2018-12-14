$(function () {
    var index = parent.layer.getFrameIndex(window.name);

    $("#input-b1").fileinput({
        language: 'zh',
        uploadUrl: '/qzgz/admin/addCanteen',
        allowedFileExtensions: ['xlsx'],
        maxFileCount: 1,
        slugCallback: function (filename) {
            var date = new Date();
            return $.md5(date.toString()) + filename;
        }
    }).on("fileuploaded", function (event, data) {
        $('#excel').attr('value', data.response.data);
    });

    $("#submit").on("click", function () {
        if ($('#excel').val() === "") {
            parent.layer.msg('文件未上传')
        } else {
            var postback = FetchData({filename: $('#excel').val()}, 'POST', '/qzgz/admin/submit', false);
            //title必须使用push过来的title 否则会提交篡改后html元素
            if (postback.code === 0)
            {
                parent.layer.msg('添加成功');
                parent.layer.close(index);
            }
            else
            {
                parent.layer.msg(postback.msg);
                parent.layer.close(index);
            }
        }

    })
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
            }
        });
    return response.responseJSON;
};