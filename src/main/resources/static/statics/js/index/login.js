/**
 * Created by zjfki on 2018/3/17.
 */
function submitLogin() {
    var loginid = $('#loginid').val();
    var password = $('#password').val();
    var code = $("#code").val();
    var res = false;
    //加密
    var encrypt=new JSEncrypt();
    encrypt.setPublicKey(RSA_PUBLIC_KEY);
    password = encrypt.encrypt(password);
    $.ajax({
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({"loginid": loginid, "password": password, "code":code}),
        url: '/index/login',
        async: false,
        success: function (data, textStatus) {
            if (data.code == 0) {
                goPAGE();
                res = true;
            } else {
                $('.alert-danger').removeClass('display-hide');
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if (XMLHttpRequest.status != 0) {
                try {
                    var result = $.parseJSON($.trim(XMLHttpRequest.responseText));
                    if (result && result.msg) {
                        alert(result.msg);
                    }
                } catch (e) {
                    alert('致命错误,请联系管理员,查看调试！');
                    console.error("-------------------------------")
                    console.error("Ajax访问发生错误：" + XMLHttpRequest.status);
                    console.error(opt);
                    console.error(result);
                    console.error(e);
                    console.error("-------------------------------")
                }
            }
        }

    });
    return res;
}

function goPAGE() {
    if ((navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i))) {
        $(".login-form").attr("action", "/m/index.html")
    }
}
//改变验证码
$(document).ready(function(){
    $("#imgcode").click(function(){
        $("#imgcode").attr("src", '/index/getLoginCode?time='+ new Date().getTime());
    });
    $("#imgcode").touch(function(){
        $("#imgcode").attr("src", '/index/getLoginCode?time='+ new Date().getTime());
    });
});

