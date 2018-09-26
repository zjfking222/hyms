var data;
var postData;
var index = parent.layer.getFrameIndex(window.name);
$(function () {
    data = parent.$("#agendaiframe")[0].contentWindow.pushData;
    console.log(data)
    laydate.render({
        elem: '#date'
        ,type: 'datetime'
    });
    if(data.id === 0){

    }
    else{
        $('#name').val(data.name);
        $('#date').val(data.date);
        $('#remark').val(data.remark);
    }
    $('#submit').on('click',function () {
        if($('#name').val()!==''&& $('#date').val()!=='')
        {
            postData = {
                id: data.id,
                name: $('#name').val(),
                date:$('#date').val(),
                mid:parent.pushMid,
                remark:$('#remark').val()
            };
            if(data.id === 0){
                if(FetchData(postData,'POST','/agenda/add',false).code === 0){
                    parent.$("#agendaiframe")[0].contentWindow.vm.dataSource.add(postData);
                    parent.$("#agendaiframe")[0].contentWindow.vm.dataSource.sync();
                    parent.layer.msg('添加成功');
                }
                else {
                    parent.layer.msg('添加失败');
                }
                parent.layer.close(index);
            }
            else {
                if(FetchData(postData,'POST','/agenda/set',false).code === 0){
                    parent.$("#agendaiframe")[0].contentWindow.vm.dataSource.pushUpdate(postData);
                    parent.layer.msg('修改成功');
                }
                else {
                    parent.layer.msg('修改失败');
                }
                parent.layer.close(index);
            }
        }
        else {
            alert('标题，时间不能为空！');
        }
    })

});
var FetchData = function (data, method, param, async) {
    var response =
        $.ajax({
            async: async,
            url: "/mm"+param,
            type: method,
            dataType: 'json',
            data: data,
            success: function (dataSource) {
                return dataSource;
            }});
    return response.responseJSON;
};