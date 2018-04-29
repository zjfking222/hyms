/**
 * Created by zjfki on 2018/3/17.
 */
function submitLogin() {
    var loginid = $('#loginid').val();
    var password = $('#password').val();
    var res = false;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({"loginid": loginid, "password": md5(password)}),
        url: '/index/login',
        async: false,
        success: function (data, textStatus) {
            if (data.code == 0) {
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
