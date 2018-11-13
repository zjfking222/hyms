/*与恒拓的app对接所需实现的单点登录验证及其其他对接操作函数文件*/
//
function ht_access_check(){
    if("hyms" == parent.parentFlag){//本系统

    }else{//恒拓的app，跳转需要做一系列验证
        //检查access_token
        var token = checkAccessToken();
        //检查单点登录
        //获取app中带入的ticket
        var url = window.location.href;
        url = url.substr(url.lastIndexOf("?"));
        var params = url.split("&");
        var ticket = "";
        for(var i=0;i<params.length;i++){
            if(params[i].includes("ticket")){
                ticket = params[i];
            }
        }
        ticket = ticket.substr(ticket.lastIndexOf("=")+1);
        //单点登录验证
        checkTicket(token, ticket);
    }
}

//验证access_token
function checkAccessToken(){
    var token = "";
    //判断当前系统中是否存在access_token
    mui.ajax('/app/selfhelp/checkAccessToken',{
        dataType:'json',
        type:'post',//HTTP请求类型
        timeout:10000,//超时时间设置为10秒；
        headers:{'Content-Type':'application/json'},
        async: false,
        success:function(data){
            //若不存在缓存的access_token，则需要重新从app应用上获取
            if(data.code == 0) {
                token = data.data;
            }else{
                token = getAccessToken();
            }
        },
        error:function(){
            //返回
            window.location.href = '/index/logout';
        }
    });
    return token;
}

//从app应用中获取access_token
function getAccessToken() {
    var token = "";
    //获取app应用中的access_token
    mui.ajax('http://amiba.huayou.com:9010/api/v1/token', {
        data: {
            grant_type: "client_credentials",
            scope: "app",
            domain_id: "hygy",
            org_id: "9ebd0f06-7730-452c-8bd0-9581fd083381",
            client_id: "c_fUmDAKUDxKHVamLTlfty",
            client_secret: "g2quHCI5JECgHl6KBRCAmsSJCfECpFOJ"
        },
        dataType: 'application/json',
        type: 'post',//HTTP请求类型
        timeout: 10000,//超时时间设置为10秒；
        headers: {'Content-Type': 'application/json'},
        async: false,
        success: function (data) {
            data = JSON.parse(data);
            var result = data.result;
            token = result.access_token;
            saveAccessToken(result);
        },
        error: function () {
            //返回
            window.location.href = '/index/logout';
        }
    });
    return token;
}

//保存access_token到系统中
function saveAccessToken(data){
    //判断当前系统中是否存在access_token
    mui.ajax('/app/selfhelp/saveAccessToken',{
        data:data,
        dataType:'json',
        type:'post',//HTTP请求类型
        timeout:10000,//超时时间设置为10秒；
        headers:{'Content-Type':'application/json'},
        async: false,
        success:function(data){
            if(data.code != "0"){
                window.location.href = '/index/logout';
            }
        },
        error:function(){
            //返回
            window.location.href = '/index/logout';
        }
    });
}

//验证ticket,app的单点登录验证
function checkTicket(token, ticket){
    var url = "http://amiba.huayou.com:9010/api/v1/tickets/" + ticket +"?access_token=" + token;
    //判断当前系统中是否存在access_token
    mui.ajax(url,{
        dataType: 'application/json',
        type: 'get',//HTTP请求类型
        timeout: 10000,//超时时间设置为10秒；
        headers: {'Content-Type': 'application/json'},
        async: false,
        success:function(data){
            data = JSON.parse(data);
            //若不存在缓存的access_token，则需要重新从app应用上获取
            if("0" != data.status) {
                //app中未登录，退出本系统的登录
                window.location.href = '/index/logout';
            }else {
                login(data.result.client_id);
            }
        },
        error:function(){
            //返回
            window.location.href = '/index/logout';
        }
    });
    return token;
}

//登录本系统
function login(id){
    //判断当前系统中是否存在access_token
    mui.ajax('/index/app/login',{
        data:{
            loginid: id
        },
        dataType: 'application/json',
        type: 'post',//HTTP请求类型
        timeout: 10000,//超时时间设置为10秒；
        headers: {'Content-Type': 'application/json'},
        async: false,
        success:function(data){
            data = JSON.parse(data);
            if(data.code != "0"){
                window.location.href = '/index/logout';
            }
        },
        error:function(){
            //返回
            window.location.href = '/index/logout';
        }
    });
    return token;
}