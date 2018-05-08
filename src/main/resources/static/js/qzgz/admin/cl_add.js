$(function () {
    var index = parent.layer.getFrameIndex(window.name);
    //初始化
    $('#img').val(parent.pushimg);
    $('#title').val(parent.pushtitle);

    $("#input-b1").fileinput({
        language: 'zh',
        uploadUrl: '/qzgz/admin/setImg',
        allowedFileExtensions : ['jpg', 'png','gif'],
        maxFileCount: 1,
        allowedFileTypes: ['image'],
        //文件名替换
        slugCallback: function(filename) {
            return filename.replace('(', '_').replace(']', '_');
        }
    }).on("fileuploaded", function (event, data) {
        $('#img').attr('value',data.response.data);
    });



    $('#submit').on('click', function () {
        //title必须使用push过来的title 否则会提交篡改后html元素
        if(FetchData({id:parent.pushid, title:parent.pushtitle, img:$('#img').val()},'POST','/admin/setBusinessTravel',false).code===0)
        {
            // parent.layer.msg('提交成功');
            // parent.layer.close(index);
            parent.location.reload();
        }
        else
        {
            parent.layer.msg('提交失败,请重试！');
            parent.layer.close(index);
        }
    })

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