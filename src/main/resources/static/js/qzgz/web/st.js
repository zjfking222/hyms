$(document).ready(function () {

    var inputData = {"username" : "admin", "userpass" : "admin"};
    var dataSource = FetchData(inputData ,'GET','/login',false);

    console.log(dataSource.code);
});

var FetchData = function (data, method, param, async) {
    var response =
        $.ajax({
        async: async,
        url: param,
        type: method,
        dataType: 'json',
        data: data,
        success: function (data) {
            return data;
        },
        error:function (e) {

        }});
    return response.responseJSON;
};

var list_item =
    "<a class='weui-cell weui-cell_access' href='javascript:;'>" +
    "<div class='weui-cell__bd'>" +
    "<span>#caiming#</span>&nbsp<span class='price'>￥<span>#jiage#</span></span>" +
    "</div>" +
    "<span><img data-id='0' class='dianzan' src='/img/qzgz/dianzan.png'></span>" +
    "</a>";