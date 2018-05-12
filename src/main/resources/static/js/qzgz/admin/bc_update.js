var index = parent.layer.getFrameIndex(window.name);
var pointer = 0;
$(function () {
    if(parent.pushData.id === 'new')
    {

    }
    else
    {
        $('#number').val(parent.pushData.number);
        $('#line').val(parent.pushData.line);
    }

    $('#submit').on('click',function () {
        if(parent.pushData.id === 'new'){
            FetchData({
                number:$('#number').val(),
                line:$('#line').val()
            },'POST','/admin/addBus',false).code === 0?
                parent.layer.msg('添加成功'):
                parent.layer.msg('添加失败')
            parent.layer.close(index);
        }
        else {
            FetchData({
                number:$('#number').val(),
                line:$('#line').val(),
                id:parent.pushData.id
            },'POST','/admin/setBus',false).code === 0?
                parent.layer.msg('修改成功'):
                parent.layer.msg('修改失败')
            parent.layer.close(index);
        }
    });

    $('#number').focus(function () {
        pointer = 1;
    });

    $('#line').focus(function () {
        pointer = 2;
    });

    $('.btn-left').on('click',function () {
        if (pointer === 1) {
            var temp = $('#number').val();
            $('#number').val(temp + '→').focus();
        }
        else if(pointer === 2) {
            var temp0 = $('#line').val();
            $('#line').val(temp0 + '→').focus();
        }
        else {

        }
    });
    $('.btn-dba').on('click',function () {
        if (pointer === 1) {
            var temp = $('#number').val();
            $('#number').val(temp + '⇋').focus();
        }
        else if(pointer === 2) {
            var temp0 = $('#line').val();
            $('#line').val(temp0 + '⇋').focus();
        }
        else {

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