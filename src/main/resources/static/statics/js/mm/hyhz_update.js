//会议回执-编辑
var index = parent.layer.getFrameIndex(window.name);
var FetchData = function (data, method, param, async,contentType) {
    var response =
        $.ajax({
            async: async,
            url: param,
            type: method,
            dataType: 'json',
            data: data,
            contentType:contentType? "application/json;charset=utf-8":
                "application/x-www-form-urlencoded;charset=UTF-8",
            success: function (dataSource) {
                return dataSource;
            }});
    return response.responseJSON;
};
var vm = new Vue({
    el: '#app',
    data:{
        agenda:'',
        dines:'',
        stay:'',
        receipt:'',
        hotel:'',
        postData:'',
        validator: null,
        bus:'',
    },
    created:function () {
        var dataSource = FetchData({rid:parent.pushRid},'POST','/mm/receipt/getReceiptDetail',false).data;
        this.hotel = FetchData(null,'POST','/bi/hotel/get',false).data;
        this.dines = dataSource.dines;
        this.agenda = dataSource.agenda;
        this.stay = dataSource.stay;
        this.receipt = dataSource.receipt;
        this.bus = FetchData({mid:parent.pushMid},'POST','/mm/bus/getInfo',false).data;


    },
    mounted:function () {
        var errorTemplate = '<div class="k-widget k-tooltip k-tooltip-validation"' + 'style="margin: 0.6em -1.8em"><span class="k-icon k-i-warning"> </span>' + '#=message#<div class="k-callout k-callout-n"></div></div>'

        this.validator = $(".form-num").kendoValidator({
            validate: function(e) {
            },
            //验证样式 默认为default
            invalidMessageType : "default",
            //自定义错误模板
            errorTemplate: errorTemplate
        }).data("kendoValidator");

        laydate.render({
            elem: '#arrivaldate'
            ,type: 'datetime'
        });
        laydate.render({
            elem: '#departuredate'
            ,type: 'datetime'
        });

        if (this.receipt.customers.vip == 1) {
            this.receipt.customers.vip = '非常重要'
        } else if (this.receipt.customers.vip == 2) {
            this.receipt.customers.vip = '重要'
        } else if (this.receipt.customers.vip == 3) {
            this.receipt.customers.vip = '一般'
        } else {
            this.receipt.customers.vip = ''
        }

        $('#submit').one('click',function () {
            vm.postData = {
                receipt:{
                    id:vm.receipt.id,
                    cid:vm.receipt.customers.id,
                    driving:vm.receipt.driving,
                    pickup:vm.receipt.pickup,
                    arrivaltype:$('#arrivaltype').val(),
                    arrivalfollower:$('#arrivalfollower').val(),
                    // arrivaldate:vm.receipt.arrivaldate,
                    arrivaldate:$('#arrivaldate').val(),
                    arrivalinfo:vm.receipt.arrivalinfo,
                    arrivalremark:vm.receipt.arrivalremark,
                    sendoff:vm.receipt.sendoff,
                    returntype:$('#returntype').val(),
                    returnfollower:$('#returnfollower').val(),
                    // departuredate:vm.receipt.departuredate,
                    departuredate:$('#departuredate').val(),
                    departureinfo:vm.receipt.departureinfo,
                    departureremark:vm.receipt.departureremark,
                    remark:vm.receipt.remark,
                    implement:vm.receipt.implement
                },
                dines:vm.dines,
                agenda:vm.agenda,
                stay:vm.stay
            };
            for (var i = 0 ; i < vm.stay.length ; i++) {
                vm.stay[i].hid = vm.hotel[0].id;
            }
            if(vm.validator.validate()) {
                FetchData(JSON.stringify(vm.postData), 'POST', '/mm/receipt/set', false, true).code === 0 ?
                    parent.layer.msg('修改成功') :
                    parent.layer.msg('修改失败');
                parent.layer.close(index);
                $("#grid").data("kendoGrid").dataSource.read()
            }
            else {
                parent.layer.msg('无法提交,请检查格式');
            }
        })
    }
});
// 到达回程日期初始化、住宿选择初始化
$(function () {
    $('#arrivaldate').val(vm.receipt.arrivaldate);
    $('#departuredate').val(vm.receipt.departuredate);
    // for(var i = 0 ; i < $('select').length ; i++)
    // {
    //     $('select').eq(i).children('option').each(function () {
    //         if($(this).text() === vm.stay[i].name){
    //             $(this).prop("selected",true);
    //         }
    //     })
    // }
    $('#arrivaltype').val(vm.receipt.arrivaltype);

    $('#returntype').val(vm.receipt.returntype);
    $('#arrivalfollower').val(vm.receipt.arrivalfollower);
    $('#returnfollower').val(vm.receipt.returnfollower);

});