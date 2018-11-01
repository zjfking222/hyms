var data;
var index = parent.layer.getFrameIndex(window.name);

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


var vm = new Vue({
    el: "#app",
    data: {
        crm : []
    },
    created : function () {
        this.crm =  FetchData({value:$('.search-input').val()}, 'POST','/firm/getByLike',false).data;
    },
    methods:{
        click : function () {
            this.crm =  FetchData({value:$('.search-input').val()}, 'POST','/firm/getByLike',false).data;
        },
        onchoose:function (name, id) {
            $('#fid').val(name).attr("data-id",id);
            $('.search-block').fadeOut(0);
        },

    }
});

$(function () {

    $('#mask').fadeOut(0);


    $('.search-block').fadeOut(0);
    $('#fid').on('click',function () {
        $('#mask').fadeIn(0);
        $('.search-block').fadeIn(500);
    });
    // $('.form-control').click(function () {
    //     if($(this).attr('id') !== 'fid'){
    //         $('.search-block').fadeOut(500);
    //     }
    // });

    $("#mask").on("click",function () {
        $('#mask').fadeOut(0);
        $('.search-block').fadeOut(0);
    })

    // $("body :not(input)").click(function () {
    //     if($(this).attr('id') !== 'fid'){
    //         $('.search-block').fadeOut(500);
    //     }
    // });

    $("body").on("keyup",function (e) {
        if (e.keyCode === 13){
            vm.click();
        }
    });

    
    data = parent.pushData;
    var optionList = FetchData(null,'POST','/business/getByUid',false).data;

    for(var i = 0 ; i < optionList.length; i++){
        var temp0 = temp.replace('@btid',optionList[i].id).replace('@btname',optionList[i].name);
        $('#btid').append(temp0);
    }

    // var optionList0 = FetchData(null,'POST','/firm/getByUid',false).data;
    // for(var j = 0 ; j < optionList0.length; j++){
    //     var temp1 = temp.replace('@btid',optionList0[j].id).replace('@btname',optionList0[j].name);
    //     $('#fid').append(temp1);
    // }

    var crm1 = FetchData({value:$('.search-input').val()}, 'POST','/firm/getByLike',false).data;
    var fname = '';
    for(var j = 0; j < crm1.length; j++){
        if(data.fid == crm1[j].id ){
             fname = crm1[j].name;
        }
    }
    if(data.id !== 0){
        var vipname ;
        if(data.vip == '一般'){
            vipname = 3
        }else if(data.vip == '重要'){
            vipname = 2
        }else if(data.vip == '非常重要'){
            vipname = 1
        }else {
            vipname = ''
        }
        $('#name').val(data.name);
        $('#post').val(data.post);
        $('#nationality').val(data.nationality);
        $('#address').val(data.address);
        $('#sex').val(data.sex);
        $('#mobile').val(data.mobile);
        $('#phone').val(data.phone);
        $('#email').val(data.email);
        $('#btid').val(data.btid);
        $('#fid').val(fname);
        $('#vip').val(vipname);
        $('#remark').val(data.remark);
    }else {

    }
    $('#submit').on('click',function () {
        var firmid = '';
        var firm = $('#fid').val();
        if(firm !== "" && firm !== null){
            var crm2 = FetchData({value:firm}, 'POST','/firm/getByLike',false).data;
            for(var x = 0;x < crm2.length;x++){
                if(firm === crm2[x].name){
                    firmid = crm2[x].id
                }
            }
        }else{
            firmid = -1
        }
        if ($('#name').val() !== null && $('#name').val() !== '') {
            if (data.id === 0) {
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
                    fid: firmid,
                    vip: $('#vip').val()==='' || $('#vip').val() === null ? 0 :$('#vip').val(),
                    remark: $('#remark').val()
                };

                var request = FetchData(postData, 'POST', '/customer/add', false);
                if (request.code === 0) {
                    parent.layer.msg('添加成功');
                    parent.layer.close(index);
                    postData.id = request.data;
                    postData.btname = $("#btid").find("option:selected").text();
                    postData.fname = $("#fid").val();
                    postData.vip = $("#vip").find("option:selected").text();

                    // //对vip与性别显示修改
                    // if($('#sex').val() === 'true'){
                    //     postData.sex_display = "男";
                    // }
                    // else {
                    //     postData.sex_display = "女";
                    // }
                    // if($('#vip').val()  === 'true'){
                    //     postData.vip_display = 'checked';
                    // }
                    // else {
                    //     postData.vip_display = '';
                    // }


                    parent.vm.dataSource.add(postData);
                    parent.vm.dataSource.sync();

                } else {
                    parent.layer.msg('添加失败');
                    parent.layer.close(index);
                }

            }
            else {
                postData = {
                    id: data.id,
                    name: $('#name').val(),
                    post: $('#post').val(),
                    nationality: $('#nationality').val(),
                    address: $('#address').val(),
                    sex: $('#sex').val(),
                    mobile: $('#mobile').val(),
                    phone: $('#phone').val(),
                    email: $('#email').val(),
                    btid: $('#btid').val(),
                    fid: firmid,
                    vip: $('#vip').val() ===''|| $('#vip').val() === null ? 0 :$('#vip').val(),
                    remark: $('#remark').val()
                };
                if (FetchData(postData, 'POST', '/customer/set', false).code === 0) {
                    parent.layer.msg('修改成功');
                    parent.layer.close(index);
                    postData.btname = $("#btid").find("option:selected").text();
                    postData.fname = $("#fid").val();
                    postData.vip = $("#vip").find("option:selected").text();
                    // //对vip与性别显示修改
                    // if($('#sex').val() === 'true'){
                    //     postData.sex_display = "男";
                    // }
                    // else {
                    //     postData.sex_display = "女";
                    // }

                    parent.vm.dataSource.pushUpdate(postData);
                } else {
                    parent.layer.msg('修改失败');
                    parent.layer.close(index);
                }

            }
        }else {
            alert('客户姓名必填！')
        }

    })
});
var temp = "<option value='@btid'>@btname</option>";






