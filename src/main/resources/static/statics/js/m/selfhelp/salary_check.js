//初始化vue对象
var vm = new Vue({
    el:"#salary_check",
    data:function(){
        return {
            codeImg:'',
        }
    },
    mounted (){
        mui.init();
        if(!parent){
            //获取app应用中的access_token
            mui.ajax('https://api3.workplus.io/v1/token',{
                data:{
                    password: password,
                    code: code
                },
                dataType:'application/json',
                type:'post',//HTTP请求类型
                timeout:10000,//超时时间设置为10秒；
                headers:{'Content-Type':'application/json'},
                async: false,
                success:function(data){

                },
                error:function(){
                    //返回
                    window.history.back(-1);
                }
            });
        }
    },
    created(){
        this.codeImg = '/app/selfhelp/getVerificationCode';
    },
    methods:{
        changeCodeImg:function(){
            var num = Math.ceil(Math.random()*10);//防止缓存
            this.codeImg = '/app/selfhelp/getVerificationCode?'+num;
        },
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
                        if(parent){
                            //获取当前页面的title
                            var title = data.data.year + "年" + data.data.month + "月薪资明细";
                            //调用父页面方法跳转当前子页面
                            parent.loadIframe("/m/selfhelp/salary.html", data.data.salaryData);
                            //更改标题
                            parent.updateTitle(title);
                        }else{//直接跳转？
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
        clearPassword:function(){
            mui(".mui-input-password")[0].value="";
            mui(".mui-input-clear")[0].value="";
        }
    }
});
