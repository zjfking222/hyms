$(function () {
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    $('#submit').on("click",function(){
        var title=$("#title").val();
        var content=$('#content').val();
        if (content!==""&&title!=="") {
            var DataSource = {};
            DataSource.id=parent.uid;
            DataSource.title=title;
            DataSource.content =content;
            if(parent.uid){
                DataSource.creater=parent.udata.creater;
                DataSource.modifier=operator;
                console.log(DataSource);
                ajaxChange("/qzgz/admin/updateNotice",DataSource);
            }else {
                DataSource.creater=operator;
                console.log(DataSource);
                ajaxChange("/qzgz/admin/insertNotice",DataSource)
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
            }
        });
    }
});
