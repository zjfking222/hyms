var del = [];
var add = [];
var index = parent.layer.getFrameIndex(window.name);
var postData;
$(function () {
    function dataSearch(search) {
        return FetchData({value:search},'POST','/system/users/search',false).data;
    }
    function dataSource(rid) {
        return FetchData({btid:rid},'POST','/crm/businessUser/get',false).data;
    }
    var vm = new Vue({
        el: '#app',
        data: {
            search: [],
            result: dataSource(parent.rid)
        },
        methods:{
            onSearch: function (search) {
                vm.search = dataSearch(search);
            },
            onAdd: function (id,loginid,lastname) {
                var isExist = false;
                var add_item = {
                    'id':id,
                    'loginid':loginid,
                    'lastname':lastname,
                    'uid':id
                };
                for(var j = 0 ; j < vm.result.length ; j++){
                    if(vm.result[j].uid === add_item.id){
                        isExist = true;
                        break;
                    }
                }
                if(!isExist){
                    add.push(add_item);
                    vm.result.push(add_item)
                }
            },
            onDel: function (id) {
                console.log(id)
                var isExist = false;
                for(var i = 0 ; i < add.length ; i++){
                    if(add[i].id === id){
                        add.splice(i,1);
                        isExist = true;
                    }
                }
                if(!isExist){
                    var del_item ={
                        btid: parent.rid,
                        uid: id
                    };
                    del.push(del_item);
                }
                //显示
                for(var j = 0 ; j < vm.result.length ; j++){
                    if(vm.result[j].uid === id){
                        vm.result.splice(j,1);
                        break;
                    }
                }
            },
            onSubmit:function () {
                postData = {
                    btid:parent.rid,
                    nHrmResource:add,
                    rHrmResource:del
                };
                FetchData(JSON.stringify(postData),'POST','/crm/businessUser/set',false,true).code === 0?
                    parent.layer.msg('修改成功'):
                    parent.layer.msg('登陆失效');
                    parent.layer.close(index);
            }
        }
    });
    $('#search').on('click',function () {
        if($('#search-info').val() === ''){

        }else {
            vm.onSearch($('#search-info').val());
        }
    })

});
var FetchData = function (data, method, param, async, contentType) {
    var response =
        $.ajax({
            async: async,
            url: param,
            type: method,
            dataType: 'json',
            data:data,
            contentType:contentType? "application/json;charset=utf-8":
                "application/x-www-form-urlencoded;charset=UTF-8",
            success: function (dataSource) {
                return dataSource;
            }});
    return response.responseJSON;
};