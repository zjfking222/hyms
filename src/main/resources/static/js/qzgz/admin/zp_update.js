var index = parent.layer.getFrameIndex(window.name);
$(function () {
    if(parent.pushData.id==='new')
    {

    }
    else
    {
        $('#name').val(parent.pushData.name);
        $('#educate').val(parent.pushData.edu);
        $('#number').val(parent.pushData.number),
        $('#workPlace').val(parent.pushData.place);
        $('#description').val(parent.pushData.descr);
        $('#salary').val(parent.pushData.sala);
        $('#age').val(parent.pushData.age);
    }

   $('#submit').on('click',function () {
       if(parent.pushData.id==='new'){
       FetchData({
           name:$('#name').val(),
           educate:$('#educate').val(),
           number:$('#number').val(),
           work_place:$('#workPlace').val(),
           description:$('#description').val(),
           salary:$('#salary').val(),
           age:$('#age').val()
       },'POST','/admin/addRecruit',false).code === 0?
           parent.layer.msg('添加成功'):
           parent.layer.msg('添加失败')
           parent.layer.close(index);
       }
       else {
           FetchData({
               name:$('#name').val(),
               educate:$('#educate').val(),
               number:$('#number').val(),
               work_place:$('#workPlace').val(),
               description:$('#description').val(),
               salary:$('#salary').val(),
               age:$('#age').val(),
               id:parent.pushData.id
           },'POST','/admin/setRecruit',false).code === 0?
               parent.layer.msg('修改成功'):
               parent.layer.msg('修改失败')
           parent.layer.close(index);
       }
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