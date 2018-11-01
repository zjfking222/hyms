var data;
var postData;
var index = parent.layer.getFrameIndex(window.name);
$(function () {
    data = parent.pushData;
    if (data.id === 0) {

    } else {
        $('#name').val(data.name);
        $('#phone').val(data.phone);
        $('#address').val(data.address);
        $('#contacter').val(data.contacter);
        $('#cphone').val(data.cphone);
        $('#cmobile').val(data.cmobile);
        $('#email').val(data.email);
        $('#star').val(data.star);
        $('#remark').val(data.remark);
    }
    $('#submit').on('click', function () {
        if ($('#name').val() !== '' && $('#phone').val() !== '' && $('#address').val() !== '' && $('#star').val() !== '') {
            postData = {
                id: data.id,
                name: $('#name').val(),
                phone: $('#phone').val(),
                address: $('#address').val(),
                contacter: $('#contacter').val(),
                cphone: $('#cphone').val(),
                cmobile: $('#cmobile').val(),
                email: $('#email').val(),
                star: $('#star option:selected').val(),
                remark: $('#remark').val(),
            };
            if (data.id === 0) {
                    var postback=FetchData(postData, 'POST', '/hotel/add', false);
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
                if (FetchData(postData, 'POST', '/hotel/update', false).code === 0) {
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
            alert('酒店名称、固话、地址、星级必填，其他选填');
        }
    })
});
var FetchData = function (data, method, param,async) {
    var response = $.ajax({
        async: async,
        url: "/bi" + param,
        type: method,
        dataType: 'json',
        data: data,
        success: function (dataSource) {
            return dataSource
        }
    });
    return response.responseJSON;
};