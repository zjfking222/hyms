$(function(){
	//点赞按钮
    $(".dianzan").on("click",function () {
    	var imgId=$(this).attr("data-id");
    	var count=parseInt($('#'+imgId).text());
	    $('#'+imgId).text(count+1);
    });
});

