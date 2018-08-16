var del = [];
var add = [];
var index = parent.layer.getFrameIndex(window.name);
var data;
var postData;
$(function () {
    data = parent.pushData;

    function dataSearch(search) {
        return FetchData({search: search}, 'POST', '/oa/hr/get', false).data;
    }

    function dataSource() {
        return FetchData(data, 'POST', '/system/users/get', false).data;
    }

    var vm = new Vue({
        el: '#users',
        data: {
            search: [],
            result: dataSource(),
            addResult: []
        },
        methods: {
            onSearch: function (search) {
                vm.search = dataSearch(search);
            },
            onAdd: function (id, loginid, lastname) {
                var isExist = false;
                var add_item = {
                    'id': id,
                    'oaloginid': loginid,
                    'name': lastname
                };
                for (var j = 0; j < vm.result.length; j++) {
                    if (vm.result[j].oaloginid === add_item.oaloginid) {
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    add.push(add_item);
                    vm.result.push(add_item);
                    vm.addResult.push(add_item);
                }
            },
            onDel: function (oauserid) {
                var isExist = false;
                for (var i = 0; i < add.length; i++) {
                    if (add[i].id === oauserid) {
                        add.splice(i, 1);
                        isExist = true;
                    }
                }
                if (!isExist) {
                    var del_item = {
                        oauserid: oauserid
                    };
                    del.push(del_item);
                }
                //显示
                for (var j = 0; j < vm.addResult.length; j++) {
                    if (vm.addResult[j].id === oauserid) {
                        vm.addResult.splice(j, 1);
                        break;
                    }
                }
            },
            onSubmit: function () {
                postData = {
                    nHrmResources: add,
                };
                if (add.length == 0) {
                    console.log(postData);
                    return;
                }
                var postback = FetchData(JSON.stringify(postData), 'POST', '/system/users/add', false, true).code;
                if (postback == 0) {
                    parent.layer.msg('添加成功');
                }
                else {
                    parent.layer.msg('添加失败');
                }
                parent.layer.close(index);
                vm.onSearch($('#search-info').val());
            }
        }
    });
    $('#search').on('click', function () {
        if ($('#search-info').val() === '') {

        } else {
            vm.onSearch($('#search-info').val());
        }
    });
});
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