$(function () {
   var id = getUrlParam('id');
   var vm = new Vue({
        el: '#app',
        data: FetchData({},'POST','/web/getRecruit/'+id,false).data
   });
});

function getUrlParam(name){
    var reg = new RegExp("(^|&)"+name+"=([^&]*)(&|$)");
    var r =  window.location.search.substr(1).match(reg);
    var strValue = "";
    if (r!=null){
        strValue= unescape(r[2]);
    }
    return strValue;
}

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