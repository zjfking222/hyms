var data;
var postData;
var index = parent.layer.getFrameIndex(window.name);
$(function () {
   data = parent.pushData;

    laydate.render({
        elem: '#begin'
        ,type: 'datetime'
    });
    laydate.render({
        elem: '#end'
        ,type: 'datetime'
    });
    laydate.render({
       elem: '#deadline',
        type:'datetime'
    });
   if(data.id === 0){

   }
   else{
       $('#name').val(data.name);
       $('#begin').val(data.begindate);
       $('#end').val(data.enddate);
       $('#deadline').val(data.deadline);
       $('#remark').val(data.remark);
   }
   $('#submit').on('click',function () {
       if($('#name').val()!==''&& $('#begin').val()!==''&& $('#end').val()!==''&& $('#deadline').val()!=='')
       {
           postData = {
               id: data.id,
               name: $('#name').val(),
               begindate:$('#begin').val(),
               enddate:$('#end').val(),
               deadline:$('#deadline').val(),
               remark:$('#remark').val()
           };
           if(data.id === 0){
               var postback = FetchData(postData,'POST','/meeting/add',false);
               if(postback.code === 0){
                   parent.vm.dataSource.add(postback.data);
                   parent.vm.dataSource.sync();
                   parent.layer.msg('添加成功');
               }
               else {
                   parent.layer.msg('添加失败');
               }
               parent.layer.close(index);
           }
           else {
               if(FetchData(postData,'POST','/meeting/set',false).code === 0){
                   parent.vm.dataSource.pushUpdate(postData);
                   parent.layer.msg('修改成功');
               }
               else {
                   parent.layer.msg('修改失败');
               }
               parent.layer.close(index);
           }
       }
       else {
           alert('标题，开始时间，结束时间,提交截止日期不能为空！');
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