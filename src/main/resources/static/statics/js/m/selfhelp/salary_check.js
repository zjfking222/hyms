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

        //刷新验证码图片
        mui(document.body).on('tap', '.changeCodeImg-btn', function() {
            vm.changeCodeImg();
        });
        //提交后台验证
        mui(document.body).on('tap', '.submitCheck-btn', function() {
            vm.submitCheck();
        });
        //重置验证码与密码
        mui(document.body).on('tap', '.clearPassword-btn', function() {
            vm.clearPassword();
        });
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
                        }else{//恒拓app展示需要直接跳转
                            //将获取到的薪资数据保存到本地，便于下一个页面获取并展示（修改成保存到后台系统缓存？），此处需要确保app本身支持
                            var salaryData = JSON.stringify(data.data.salaryData);
                            window.sessionStorage.setItem("salaryData", salaryData);
                            window.location.href="/m/selfhelp/salary.html";
                        }
                    }else {
                        mui.alert(data.msg);
                        vm.changeCodeImg();
                    }
                },
                error:function(xhr,type,errorThrown){
                    //异常处理；
                    console.log(type);
                    vm.changeCodeImg();
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