var del = [];
var add = [];
var cur = [];
$(function () {
    function dataSearch(search) {
        return FetchData({search:search},'POST','/oa/hr/getSearch',false).data;
    }
    var vm = new Vue({
        el: '#app',
        data: {
            search: [],
            result: add
        },
        methods:{
            onSearch: function (search) {
                vm.search = dataSearch(search);
            },
            onAdd: function (id,loginid,lastname) {
                var isExist = false;
                var add_item = {
                    id:id,
                    loginid:loginid,
                    lastname:lastname
                };
                for(var j = 0 ; j < cur.length ; j++){
                    if(cur[j].id === add_item.id){
                        isExist = true;
                        break;
                    }
                }
                for(var i = 0 ; i < add.length ; i++){
                    if(add[i].id === add_item.id){
                        isExist = true;
                        break;
                    }
                }
                if(!isExist){
                    add.push(add_item);
                }
            },
            onDel: function (id) {
                var isExist = false;
                for(var i = 0 ; i < add.length ; i++){
                    if(add[i].id === id){
                        add.splice(i,1);
                        isExist = true;
                        break;
                    }
                }
                if(!isExist){
                    del.push(id);
                }
                console.log(del);
                console.log(add);
            },
            onSubmit:function () {
                var postData = {

                };
                if(FetchData(postData,'POST','/system/roleUser/add',false).code === 0){

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
});
var FetchData = function (data, method, param, async) {
    var response =
        $.ajax({
            async: async,
            url: param,
            type: method,
            dataType: 'json',
            data: data,
            success: function (dataSource) {
                return dataSource;
            }});
    return response.responseJSON;
};