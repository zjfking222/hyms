var data;
var index = parent.layer.getFrameIndex(window.name);
$(function () {
    data = parent.pushData;
    var optionList = FetchData(null,'POST','/business/getByUid',false).data;

    for(var i = 0 ; i < optionList.length; i++){
        var temp0 = temp.replace('@btid',optionList[i].id).replace('@btname',optionList[i].name);
        $('#btid').append(temp0);
    }

    var optionList0 = FetchData(null,'POST','/firm/getByUid',false).data;
    for(var j = 0 ; j < optionList0.length; j++){
        var temp1 = temp.replace('@btid',optionList0[j].id).replace('@btname',optionList0[j].name);
        $('#fid').append(temp1);
    }

    if(data.id !== 0){
        $('#name').val(data.name);
        $('#post').val(data.post);
        $('#nationality').val(data.nationality);
        $('#address').val(data.address);
        $('#sex').val(data.sex);
        $('#mobile').val(data.mobile);
        $('#phone').val(data.phone);
        $('#email').val(data.email);
        $('#btid').val(data.btid);
        $('#fid').val(data.fid);
        $('#vip').val(data.vip.toString());
        $('#remark').val(data.remark);
    }else {

    }
    $('#submit').on('click',function () {
        if(data.id === 0){
            postData = {
                name: $('#name').val(),
                post: $('#post').val(),
                nationality: $('#nationality').val(),
                address: $('#address').val(),
                sex: $('#sex').val(),
                mobile: $('#mobile').val(),
                phone: $('#phone').val(),
                email: $('#email').val(),
                btid: $('#btid').val(),
                fid: $('#fid').val() === '' || $('#fid').val() === null ? 0:$('#fid').val(),
                vip: $('#vip').val()  === 'true',
                remark: $('#remark').val()
            };

            var request = FetchData(postData,'POST','/customer/add',false);
            if(request.code === 0){
                parent.layer.msg('添加成功');
                parent.layer.close(index);
                postData.id = request.data;
                postData.btname = $("#btid").find("option:selected").text();
                postData.fname = $("#fid").find("option:selected").text();

                // //对vip与性别显示修改
                // if($('#sex').val() === 'true'){
                //     postData.sex_display = "男";
                // }
                // else {
                //     postData.sex_display = "女";
                // }
                if($('#vip').val()  === 'true'){
                    postData.vip_display = 'checked';
                }
                else {
                    postData.vip_display = '';
                }


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
                name: $('#name').val(),
                post: $('#post').val(),
                nationality: $('#nationality').val(),
                address: $('#address').val(),
                sex: $('#sex').val(),
                mobile: $('#mobile').val(),
                phone: $('#phone').val(),
                email: $('#email').val(),
                btid: $('#btid').val(),
                fid: $('#fid').val() === ''|| $('#fid').val() === null? 0:$('#fid').val(),
                vip: $('#vip').val()  === 'true',
                remark: $('#remark').val()
            };
            if(FetchData(postData,'POST','/customer/set',false).code === 0){
                parent.layer.msg('修改成功');
                parent.layer.close(index);
                postData.btname = $("#btid").find("option:selected").text();
                postData.fname = $("#fid").find("option:selected").text();

                // //对vip与性别显示修改
                // if($('#sex').val() === 'true'){
                //     postData.sex_display = "男";
                // }
                // else {
                //     postData.sex_display = "女";
                // }
                if($('#vip').val()  === 'true'){
                    postData.vip_display = 'checked';
                }
                else {
                    postData.vip_display = '';
                }

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