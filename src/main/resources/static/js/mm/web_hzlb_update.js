//移动端会议回执-编辑
var rid1 = window.location.search.substr(4);
var rid = parseInt(rid1);
var ridLength = rid.toString().length;
var pushMid = window.location.search.substr(9 + ridLength);
var FetchData = function (data, method, param, async, contentType) {
    var response =
        $.ajax({
            async: async,
            url: param,
            type: method,
            dataType: 'json',
            data: data,
            contentType: contentType ? "application/json;charset=utf-8" :
                "application/x-www-form-urlencoded;charset=UTF-8",
            success: function (dataSource) {
                return dataSource;
            }
        });
    return response.responseJSON;
};
var vm = new Vue({
    el: '#mobile',
    data: {
        agenda: '',
        dines: '',
        stay: '',
        receipt: '',
        hotel: '',
        postData: '',
        bus: '',
    },
    created: function () {
        var dataSource = FetchData({rid: rid}, 'POST', '/mm/receipt/getReceiptDetail', false).data;
        this.hotel = FetchData(null, 'POST', '/bi/hotel/get', false).data;
        this.dines = dataSource.dines;
        this.agenda = dataSource.agenda;
        this.stay = dataSource.stay;
        this.receipt = dataSource.receipt;
        if (this.receipt.arrivaldate !== null) {
            var temp_0 = this.receipt.arrivaldate.replace(" ", "T");
            var ss_0 = temp_0.substring(0, temp_0.length - 3);
            this.receipt.arrivaldate = ss_0;
        }
        if (this.receipt.departuredate !== null) {
            var temp_1 = this.receipt.departuredate.replace(" ", "T");
            var ss_1 = temp_1.substring(0, temp_1.length - 3);
            this.receipt.departuredate = ss_1;
        }

        this.bus = FetchData({mid: pushMid}, 'POST', '/mm/bus/getInfo', false).data;
    },
    mounted: function () {
        $('#submit').on('click', function () {
            if (confirm("确定要提交吗？")) {
                var a = $('#arrivaldate').val();
                var adate = a.replace("T", " ");
                var ad = adate + ":00";
                var d = $('#departuredate').val();
                var ddate = d.replace("T", " ");
                var dd = ddate + ":00";
                vm.postData = {
                    receipt: {
                        id: vm.receipt.id,
                        cid: vm.receipt.customers.id,
                        driving: vm.receipt.driving,
                        pickup: vm.receipt.pickup,
                        arrivaltype: $('#arrivaltype').val(),
                        arrivalfollower: $('#arrivalfollower').val(),
                        arrivaldate: ad,
                        arrivalinfo: vm.receipt.arrivalinfo,
                        arrivalremark: vm.receipt.arrivalremark,
                        sendoff: vm.receipt.sendoff,
                        returntype: $('#returntype').val(),
                        returnfollower: $('#returnfollower').val(),
                        departuredate: dd,
                        departureinfo: vm.receipt.departureinfo,
                        departureremark: vm.receipt.departureremark,
                        remark: vm.receipt.remark,
                        implement: vm.receipt.implement
                    },
                    dines: vm.dines,
                    agenda: vm.agenda,
                    stay: vm.stay
                };
                for (var i = 0 ; i < vm.stay.length ; i++) {
                    vm.stay[i].hid = vm.hotel[0].id;
                }

                FetchData(JSON.stringify(vm.postData), 'POST', '/mm/receipt/set', false, true).code === 0 ?
                    alert('修改成功') :
                    alert('修改失败');
            } else {

            }

        })
    },
});
// 到达、回程日期、方式初始化、
$(function () {
    $('#arrivaldate').val(vm.receipt.arrivaldate);
    $('#departuredate').val(vm.receipt.departuredate);
    $('#arrivaltype').val(vm.receipt.arrivaltype);
    $('#returntype').val(vm.receipt.returntype);
    $('#arrivalfollower').val(vm.receipt.arrivalfollower);
    $('#returnfollower').val(vm.receipt.returnfollower);
    // for (var i = 0; i < $('.slt_hotel').length; i++) {
    //     $('.slt_hotel').eq(i).children('option').each(function () {
    //         if ($(this).text() === vm.stay[i].name) {
    //             $(this).prop("selected", true);
    //         }
    //     })
    // }
    var hybus = "<a href='../mm/web_hybc.html?mid=#pushMid#'>查看班车</a>";
    var bus = hybus.replace('#pushMid#', pushMid);
    $('#hybus').append(bus);
});