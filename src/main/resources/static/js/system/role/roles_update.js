var del = [];
var add = [];
var index = parent.layer.getFrameIndex(window.name);
var postData;
$(function () {
    function dataSearch(search) {
        return FetchData({search:search},'POST','/oa/hr/getSearch',false).data;
    }
    function dataSource(rid) {
        return FetchData({rid:rid},'POST','/system/roleUser/getUser',false).data;
    }
    var vm = new Vue({
        el: '#app',
        data: {
            search: [],
            result: dataSource(parent.pushData.rid)
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
                    'lastname':lastname
                };
                for(var j = 0 ; j < vm.result.length ; j++){
                    if(vm.result[j].id === add_item.id){
                        isExist = true;
                        break;
                    }
                }
                // for(var i = 0 ; i < add.length ; i++){
                //     if(add[i].id === add_item.id){
                //         isExist = true;
                //         break;
                //     }
                // }
                if(!isExist){
                    add.push(add_item);
                    vm.result.push(add_item)
                }
            },
            onDel: function (uid) {
                var isExist = false;
                for(var i = 0 ; i < add.length ; i++){
                    if(add[i].id === uid){
                        add.splice(i,1);
                        isExist = true;
                    }
                }
                if(!isExist){
                    var del_item ={
                        rid: parent.pushData.rid,
                        uid: uid
                    }
                    del.push(del_item);
                }
                //显示
                for(var j = 0 ; j < vm.result.length ; j++){
                    if(vm.result[j].id === uid){
                        vm.result.splice(j,1);
                        break;
                    }
                }
            },
            onSubmit:function () {
                if(parent.pushData.rid === -1)
                {
                    postData = {
                        name:$('#name').val(),
                        nHrmResources:add
                    };
                    FetchData(JSON.stringify(postData),'POST','/system/roleUser/add',false,true).code === 0?
                        parent.layer.msg('添加成功'):
                        parent.layer.msg('登陆失效');
                    parent.layer.close(index);
                }
                else {
                    postData = {
                        rid:parent.pushData.rid,
                        name:$('#name').val(),
                        nHrmResources:add,
                        rHrmResources:del
                    };
                    FetchData(JSON.stringify(postData),'POST','/system/roleUser/set',false,true).code === 0?
                        parent.layer.msg('修改成功'):
                        parent.layer.msg('登陆失效');
                    parent.layer.close(index);
                }
            }
        }
    });
    $('#search').on('click',function () {
        if($('#search-info').val() === ''){

        }else {
            vm.onSearch($('#search-info').val());
        }
    })

    if(parent.pushData.rid === -1)
    {

    }
    else
    {
        $('#name').val(parent.pushData.name);
    }
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