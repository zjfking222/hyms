$(document).ready(function(){
    mui.init({

    });
    mui.ajax('/index/config',{
        data:{
            fieldType:'1'
        },
        dataType:'json',//服务器返回json格式数据
        type:'post',//HTTP请求类型
        timeout:10000,//超时时间设置为10秒；
        headers:{'Content-Type':'application/json'},
        async: false,
        success:function(data){
            if (data.code == 0) {
                //添加数据到列表中
                var result = data.data;
                if(result){
                    var menus = result.menus;
                    $("#usernamelabel").text(result.username);
                    if(menus){
                        var menustr = "";
                        mui.each(menus, function (index, item) {
                            menustr +='<li class=\"mui-table-view-cell mui-collapse\">';
                            menustr += '<a class="mui-navigate-right" style="font-size: large;" href="#">' + item.name + '</a>';
                            if(item.permissionDtoList){
                                menustr += '<ul class="mui-table-view mui-table-view-chevron">';
                                mui.each(item.permissionDtoList, function (pindex, pitem) {
                                    menustr += '<li class="mui-table-view-cell" urltext="' + pitem.url + '" liname="'+ pitem.name +'">';
                                    menustr += '<a class="mui-navigate-right" style="font-size: large;" href="javascript:void(0);">' + pitem.name + '</a>';
                                    menustr += '</li>';
                                });
                                menustr += '</ul>';
                            }
                            menustr += '</li>';
                        });
                        $("#appmenulist").append(menustr);
                    }
                }
            }else {
                console.log(data.msg)
            }
        },
        error:function(xhr,type,errorThrown){
            //异常处理；
            console.log(type);
        }
    });

    //绑定点击菜单跳转相应页面的事件
    mui(".mui-table-view-cell").on('tap','.mui-table-view-cell',function(){
        //获取iframe的跳转地址
        var url = this.getAttribute("urltext");
        $("#appmaincontent")[0].src = url;
        //获取主页title
        var title = this.getAttribute("liname");
        $("#appmaintitle").text(title);
        //隐藏菜单页面
        mui('.mui-off-canvas-wrap').offCanvas().close();
    });

    //退出登录状态
    $('.appexit').on('tap', function() {
        mui.confirm('确定退出登录？', '提示', ['确定', '取消'], function(e) {
            if(e.index == 0) {
                localStorage.clear();
                window.location.href = '/index/logout';
            }
        }, 'div');
    });
});

var iframeParam;
//提供子页面调用切换新iframe子页面
function loadIframe(title, url, param){
    //新页面标题
    $("#appmaintitle").text(title);
    //新页面url
    $("#appmaincontent")[0].src = url;
    //新页面参数
    iframeParam = param;
}


