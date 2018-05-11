
var FetchData = function (data, method, param, async) {
    var response =
        $.ajax({
            async: async,
            url: "/qzgz"+param,
            type: method,
            dataType: 'json',
            data: data,
            success: function (dataSource) {
                return dataSource;
            }});
    var result = response.responseJSON.data;
    for(var i = 0 ; i < result.length ; i++)
    {
        result[i].line = result[i].line.replace(/\n/g,'<br/>');
        console.log(result[i].line.replace('\n','<br/>'));
    }
    return result;
};

$(function () {
    var vm = new Vue({
        el:'#app',
        data: {
            data1 : FetchData(null, 'POST','/web/getBus',false)
        },
        methods: {

        }
    });
});

