var data;
var index = parent.layer.getFrameIndex(window.name);
$(function () {
    data = parent.pushData;
    var optionList = FetchData(null,'POST','/business/get',false).data;

    for(var i = 0 ; i < optionList.length; i++){
        var temp0 = temp.replace('@btid',optionList[i].id).replace('@btname',optionList[i].name);
        $('#btid').append(temp0);
    }

    if(data.id !== 0){
        $('#name').val(data.name);
        $('#phone').val(data.phone);
        $('#address').val(data.address);
        $('#contacter').val(data.contacter);
        $('#cmobile').val(data.cmobile);
        $('#cphone').val(data.cphone);
        $('#email').val(data.email);
        $('#btid').val(data.btid);
        $('#remark').val(data.remark);
    }else {

    }
    $('#submit').on('click',function () {
        if(data.id === 0){
            postData = {
                name:$('#name').val(),
                phone:$('#phone').val(),
                address:$('#address').val(),
                contacter:$('#contacter').val(),
                cmobile:$('#cmobile').val(),
                cphone:$('#cphone').val(),
                email:$('#email').val(),
                btid:$('#btid').val(),
                remark:$('#remark').val()
            };
            var request = FetchData(postData,'POST','/firm/add',false);
            if(request.code === 0){
                parent.layer.msg('添加成功');
                parent.layer.close(index);
                postData.id = request.data;
                postData.btname = $("#btid").find("option:selected").text();

                parent.vm.dataSource.add(postData);
                parent.vm.dataSource.sync();
            }else {
                parent.layer.msg('添加失败');
                parent.layer.close(index);
            }

        }
        else {
            postData = {
                id:data.id,
                name:$('#name').val(),
                phone:$('#phone').val(),
                address:$('#address').val(),
                contacter:$('#contacter').val(),
                cmobile:$('#cmobile').val(),
                cphone:$('#cphone').val(),
                email:$('#email').val(),
                btid:$('#btid').val(),
                remark:$('#remark').val()
            };
            if(FetchData(postData,'POST','/firm/set',false).code === 0){
                parent.layer.msg('修改成功');
                parent.layer.close(index);
                postData.btname = $("#btid").find("option:selected").text();
                parent.vm.dataSource.pushUpdate(postData);
            }else {
                parent.layer.msg('修改失败');
                parent.layer.close(index);
            }

        }

    })
});

var temp = "<option value='@btid'>@btname</option>";

var FetchData = function (data, method, param, async) {
    var response =
        $.ajax({
            async: async,
            url: "/crm"+param,
            type: method,
            dataType: 'json',
            data: data,
            success: function (dataSource) {
                return dataSource;
            }});
    return response.responseJSON;
};