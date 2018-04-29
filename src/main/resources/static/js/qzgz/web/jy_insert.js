$(function(){
    $('#submit').on("click",function(){
        var title=$("#title").val()
        var username=$('#username').val();
        var department=$('#department').val();
        var contact=$('#contact').val()
        var content=$('#content').val();
        if (username!=""&&content!=""&&title!="") {
            var DataSource = new Object;
            DataSource.title=title;
            DataSource.creater =username;
            DataSource.department =department;
            DataSource.contact =contact;
            DataSource.content =content;
            console.log(DataSource);
            $.ajax({
                method : "POST",
                url : "/insertSuggestion",
                dataType : 'json',
                data : DataSource,
                success : function() {
                    alert("建议提交成功");
                }
            });
        } else {
            alert("请输入标题姓名和建议");
        }
    });
});