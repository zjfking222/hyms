//初始化vue对象
var vm = new Vue({
    el:"#appmenulist",
    data:{
        menus:getMenuList()
    },
    mounted (){
        mui.init();
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
    }
});

//从后台获取菜单列表
function getMenuList() {
    var result = [];
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
                result = data.data.menus;
            }else {
                console.log(data.msg)
            }
        },
        error:function(xhr,type,errorThrown){
            //异常处理；
            console.log(type);
        }
    });
    //genggai
    return result;
}

var iframeParam;
//提供子页面调用切换新iframe子页面
function loadIframe(url, param){
    //新页面url
    $("#appmaincontent")[0].src = url;
    //新页面参数
    iframeParam = param;
}
//提供子页面调用更改主页的标题
function updateTitle(title){
    //新页面标题
    $("#appmaintitle").text(title);
}

//竖屏处理
function landscape(config){
    var color = config && config.color ? config.color : "#000",
        txt = config && config.txt ? config.txt : "为了更好的体验，请使用竖屏浏览",
        images = config && config.images ? config.images : "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIYAAADaCAMAAABU68ovAAAAXVBMVEUAAAD29vb////x8fH////////x8fH5+fn29vby8vL////5+fn39/f6+vr////x8fH////////+/v7////09PT////x8fH39/f////////////////////x8fH///+WLTLGAAAAHXRSTlMAIpML+gb4ZhHWn1c2gvHBvq1uKJcC6k8b187lQ9yhhboAAAQYSURBVHja7d3blpowFIDhTUIAOchZDkre/zE7ycySrbUUpsRN2/1fzO18KzEqxEVgTiZNfgmmtxRc8iaR8HNe8x4BtjQePKayYCIoyBSgvNNE1AkNSHqZyLqk97EgUCCHBzZ5mkg7ScvIJuIyOyXBRFxgpqWZyGsAZLB1KjsJi8nutHU4JCRbFRH8tmirI9k8Jx2sqNs8K/m0LQkrktO2crgcgXGB4AiTEsB0hJfo9MGgX7CGcYiYwQxmMOOvZwRhBG8tCoMXjBDeXvWCEcHbi14wgCBmMIMZzGAGM5jxETNwzMAxA8cMHDNwzMAxA8cMHDNwzMAxA8cMHDNwzMAxY6E2rUQxnH2tz9cirlJFwFBJedaPnUv0M7++egPDE8iAJcIDmxwH5wwv9vUviw2kLbVO3TJU5uul/EyB0FoLp4x60PdGUd3qPurrWyjGGTc05u+1dcgI7/+tCCPARWGhH7o5Y7RCf+bH9ctXLp6v2BVDxfqz0oPXeSVaNtINo/1SXDv4dck8IIkbhtC2ol+iouEonTBCbYvVMnXOjxww6s/RFrBUpXHh/gw1rHj5d/qhYn9Gpk2FWh6xRBRX5Oj3Znh2Sq49/L6+y8pB26q9GbE2dbA2mVbx6I+7MfBglLCttm73ZQi7AD3iL4HqjFYJHSPRppqaUaJ3ATpGa+ckpGak2hRRMyqjGMkvl+xyFeSMwjAqcsZgGDdyhl0oNTnDN4yenJGZFGxNChP5/Y3efh6SM2rDOJMzboYxkDMqwyjIGcIw6F+io2FU1IxIm1JqRmgXSkvNKNCXeTpGrU0JNSO2c6LIGPgCS8AuDHz9ta0SXWDtxoDRH+MqlbC2Dt2G2JFRadtQZt2qq/orGowdGb2euxYiqWEpVWhTBnszoNAPdStuQwxqf0aocdWKW4Z+DfszIh8pxJqbuCE4YAC+4bm0evtipjpgJHeFnyyt1Ku2xa0bhjxr27p75rECNwyI9ZwvXkHq+7aTaMEV44YYy/spfgjgjNHaWW+GeUhGEX7tLlVinIFDDSgnOwhi1V6bU0b6tVS9eAERe863g4dRrtiHdc6o+nn5vtyVVgR79Cqt4uL6gfHPQyGqtP2vf7HADGbcYwaOGThm4JiBYwaOGThm4JiBYwaOGThm4JiBYwaOGThm4JiBYwaOGThm4JjhtOM+J/AgT008yDMkN/dPP9hzS8zAMQN3OEYeekp5YU7KOKXwVXqiY+QS7smcinGKABWdiBgpPJTSMHJ4KidhhPBUSMLw4CmPhKHgKUXCkHsygum71ftNSgCX6bsl8FQyfbcL5EdYsDk0R3j7aiA5wpt5AjKg/2gLJEBD/0Hf2OOf/vRrj6z/7GtP4B3nMKyjHA12kIPSjnJs3FEO0TvKkYJHOWCR+rjJH0Vn6fI5PjNbAAAAAElFTkSuQmCC";
    $('body').append('<style type="text/css">@-webkit-keyframes rotation{10%{transform: rotate(90deg); -webkit-transform: rotate(90deg)} 50%, 60%{transform: rotate(0deg); -webkit-transform: rotate(0deg)} 90%{transform: rotate(90deg); -webkit-transform: rotate(90deg)} 100%{transform: rotate(90deg); -webkit-transform: rotate(90deg)} } @keyframes rotation{10%{transform: rotate(90deg); -webkit-transform: rotate(90deg)} 50%, 60%{transform: rotate(0deg); -webkit-transform: rotate(0deg)} 90%{transform: rotate(90deg); -webkit-transform: rotate(90deg)} 100%{transform: rotate(90deg); -webkit-transform: rotate(90deg)} } #orientLayer{display: none; z-index: 999999;} @media screen and (min-aspect-ratio: 12/7){#orientLayer{display: block;} } .mod-orient-layer{display: none; position: fixed; height: 100%; width: 100%; left: 0; top: 0; right: 0; bottom: 0; background: '+color+'; z-index: 9997} .mod-orient-layer__content{position: absolute; width: 100%; top: 45%; margin-top: -75px; text-align: center} .mod-orient-layer__icon-orient{background-image: url('+images+'); display: inline-block; width: 67px; height: 109px; transform: rotate(90deg); -webkit-transform: rotate(90deg); -webkit-animation: rotation infinite 1.5s ease-in-out; animation: rotation infinite 1.5s ease-in-out; -webkit-background-size: 67px; background-size: 67px} .mod-orient-layer__desc{margin-top: 20px; font-size: 15px; color: #fff}</style><div id="orientLayer" class="mod-orient-layer"> <div class="mod-orient-layer__content"> <i class="icon mod-orient-layer__icon-orient"></i> <div class="mod-orient-layer__desc">'+txt+'</div> </div></div>');
}
//竖屏提示
landscape();