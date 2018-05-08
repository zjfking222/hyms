// $(document).ready(function () {
//     var dataSource = FetchData(null, 'POST','/getBus',false);
//     if(dataSource.code === 0){
//         for (var i = 0; i < dataSource.data.length; i++)
//         {
//             var list_item0 = list_item
//                 .replace("#checi#",dataSource.data[i].number);
//             var list_item1 = list_item0
//                 .replace("#shoufa#",dataSource.data[i].start);
//             var list_item2 = list_item1
//                 .replace('#moban#',dataSource.data[i].end);
//             $(".table-container").append(list_item2);
//         }
//     }
//     else
//     {
//         alert(dataSource.code);
//     }
// });
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
    return response.responseJSON;
};
$(function () {
    var vm = new Vue({
        el:'#app',
        data: {
            data1 : FetchData(null, 'POST','/web/getBus',false).data
        },
        methods: {

        }
    });
});

