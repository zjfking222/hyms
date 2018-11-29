package com.hy.service.m;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/20 15:58
 * @Description:身份相关操作服务接口
 */
public interface IdentityService {

    /**
     * @Author 钱敏杰
     * @Description 向手机发送短信验证码，并保存验证码到缓存
     * @Date 2018/11/20 16:47
     * @Param [code, phone]
     * @return void
     **/
    void sendVerCode(String code, String phone);

    /**
     * @Author 钱敏杰
     * @Description 验证输入的验证码与session中保存的验证码是否一致
     * @Date 2018/11/26 15:19
     * @Param [code, phone]
     * @return boolean
     **/
    boolean checkVerCode(String code, String phone);

    /**
     * @Author 钱敏杰
     * @Description 验证输入的密码是否正确
     * @Date 2018/11/21 9:22
     * @Param [password]
     * @return boolean true 一致；false 不一致
     **/
    boolean checkPassword(String password);

    /**
     * @Author 钱敏杰
     * @Description 更改当前用户AD域中的手机号码信息
     * @Date 2018/11/21 9:31
     * @Param [phone]
     * @return void
     **/
    void savePhone(String phone);

    /**
     * @Author 钱敏杰
     * @Description 检查两个密码是否一致
     * @Date 2018/11/22 16:15
     * @Param [password1, password2]
     * @return boolean true：一致 false：不一致
     **/
    boolean checkPasswords(String password1, String password2);

    /**
     * @Author 钱敏杰
     * @Description 修改当前用户密码为新密码
     * @Date 2018/11/26 9:16
     * @Param [uid, newPassword]
     * @return void
     **/
    void resetPassword(String uid, String newPassword);

    /**
     * @Author 钱敏杰
     * @Description 验证员工号与手机号的身份信息
     * @Date 2018/11/23 15:02
     * @Param [uid, phone]
     * @return boolean
     **/
    boolean checkIdPhone(String uid, String phone);
}
