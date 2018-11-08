//初始化vue对象
var vm = new Vue({
    el:"#salary_check",
    data:function(){
        return {
            codeImg:'',
        }
    },
    mounted (){//挂载el
        mui.init();
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
    },
    created(){//创建vue之后立即执行
        this.codeImg = '/app/selfhelp/getVerificationCode';
    },
    methods:{//方法定义
        //刷新验证码图片
        changeCodeImg:function(){
            var num = Math.ceil(Math.random()*10);//防止缓存
            this.codeImg = '/app/selfhelp/getVerificationCode?'+num;
        },
        //提交后台验证
        submitCheck:function(){
            var password = mui(".mui-input-password")[0].value;
            var code = mui(".mui-input-clear")[0].value;
            mui.ajax('/app/selfhelp/authentication',{
                data:{
                    password: password,
                    code: code
                },
                dataType:'json',//服务器返回json格式数据
                type:'post',//HTTP请求类型
                timeout:10000,//超时时间设置为10秒；
                headers:{'Content-Type':'application/json'},
                async: false,
                success:function(data){
                    if (data.code == 0) {//成功即跳转到薪资页面
                        if("hyms" == parent.parentFlag){
                            //获取当前页面的title
                            var title = data.data.year + "年" + data.data.month + "月薪资明细";
                            //调用父页面方法跳转当前子页面
                            parent.loadIframe("/m/selfhelp/salary.html", data.data.salaryData);
                            //更改标题
                            parent.updateTitle(title);
                        }else{//直接跳转？--------------------------------------------------------------------------------------------------------------------
                            mui.openWindow({
                                url:"/m/selfhelp/salary.html",
                                id:"/m/selfhelp/salary.html"
                            });
                        }
                    }else {
                        console.log(data.msg)
                        mui.alert(data.msg);
                        vm.changeCodeImg();
                    }
                },
                error:function(xhr,type,errorThrown){
                    //异常处理；
                    console.log(type);
                }
            });
        },
        //重置验证码与密码
        clearPassword:function(){
            mui(".mui-input-password")[0].value="";
            mui(".mui-input-clear")[0].value="";
        }
    }
});

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
function getAccessToken(access_token) {
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
            token = data.result;
            saveAccessToken(token);
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