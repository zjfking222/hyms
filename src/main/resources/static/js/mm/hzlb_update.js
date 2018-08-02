//回执列表-编辑
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
        postData:''
    },
    created:function () {
        var dataSource = FetchData({rid:parent.pushRid},'POST','/mm/receipt/getReceiptDetail',false).data;
        this.hotel = FetchData(null,'POST','/bi/hotel/get',false).data;
        this.dines = dataSource.dines;
        this.agenda = dataSource.agenda;
        this.stay = dataSource.stay;
        this.receipt = dataSource.receipt;

    },
    mounted:function () {
        for(var i = 0 ; i < $('select').length ; i++)
        {
            $("select").eq(i).find("option[text='"+this.stay.name+"']").attr("selected",true);
        }
        $('#submit').on('click',function () {
            vm.postData = {
                receipt:{
                    id:vm.receipt.id,
                    cid:vm.receipt.customers.id,
                    driving:vm.receipt.driving,
                    pickup:vm.receipt.pickup,
                    arrivaldate:vm.receipt.arrivaldate,
                    arrivalinfo:vm.receipt.arrivalinfo,
                    arrivalremark:vm.receipt.arrivalremark,
                    sendoff:vm.receipt.sendoff,
                    departuredate:vm.receipt.departuredate,
                    departureinfo:vm.receipt.departureinfo,
                    departureremark:vm.receipt.departureremark,
                    remark:vm.receipt.remark,
                    implement:vm.receipt.implement
                },
                dines:vm.dines,
                agenda:vm.agenda,
                stay:vm.stay
            };
            for (var i = 0 ; i < $('select').length ; i++) {
                vm.stay[i].hid = $("select").eq(i).val();
            }
            console.log(vm.postData);
            var fetchData = FetchData(JSON.stringify(vm.postData),'POST','/mm/receipt/set',false, true).data;
            console.log(fetchData)
        })
    }
});