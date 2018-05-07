$(function () {
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    $('#submit').on("click",function(){
        var title=$("#title").val();
        var operator=$('#operator').val();
        var nodifiedPerson=$('#nodifiedPerson').val();
        var content=$('#content').val();
        if (operator!==""&&content!==""&&title!=="") {
            var DataSource = {};
            DataSource.id=parent.uid;
            DataSource.title=title;
            DataSource.nodifiedPerson =nodifiedPerson;
            DataSource.content =content;
            if(parent.uid){
                DataSource.creater=parent.udata.creater;
                DataSource.modifier=operator;
                console.log(DataSource);
                ajaxChange("/admin/updateNotice",DataSource);
            }else {
                DataSource.creater=operator;
                console.log(DataSource);
                ajaxChange("/admin/insertNotice",DataSource)
            }

        } else {
            alert("请输入标题姓名和建议");
        }
    });
    function ajaxChange(url,dataSource) {
        $.ajax({
            method : "POST",
            url : url,
            dataType : 'json',
            data : dataSource,
            success : function() {
                parent.layer.close(index);
                parent.location.reload();
            }
        });
    }
});
