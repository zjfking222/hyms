$(document).ready(function () {
    //init
    $('#submitted').fadeOut(0);
    $('#iosDialog1').fadeOut(0);
    $('.btn-close').click(function () {
        $('#iosDialog1').fadeOut(500);
    });
    $('#submit').click(function () {
        if($('#name').val()===""||$('#department').val()===""
            ||$('#contact').val()===""||$("#opinion").val()==="")
        {
            $('#alert-text').html('提交信息不能为空！')
        }
        else
        {
            var data = {
                    name: $('#name').val(),
                    department:$('#department').val(),
                    contact:$('#contact').val(),
                    opinion:$("#opinion").val()
                };
            var dataSourse = FetchData(data,'POST','/web/handInOpinion',false);
            if(dataSourse.code === 0 )
            {
                $('#alert-text').html('提交成功');
                $('#submit').fadeOut(0);
                $('#submitted').fadeIn(0);
            }
            else {
                $('#alert-text').html(dataSourse.code);
            }
        }
        $('#iosDialog1').fadeIn(100);
   })
});

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