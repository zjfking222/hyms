package com.hy.service.m;

import com.hy.common.SecurityUtil;
import com.hy.config.ldap.LdapUtil;
import com.hy.config.ums.UmsClient;
import com.hy.model.LdapStaff;
import com.hy.utils.RSAUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/20 15:58
 * @Description:身份相关操作服务实现
 */
@Service
public class IdentityServiceImpl implements IdentityService {

    private static final Logger logger = LoggerFactory.getLogger(IdentityServiceImpl.class);
    //手机验证码保存key
    private String codeKey = "mobileVerificationCode";
    //RSA加密私钥
    @Value("${rsa.private.key}")
    private String privateKey;

    /**
     * @Author 钱敏杰
     * @Description 向手机发送短信验证码，并保存验证码到缓存
     * @Date 2018/11/20 16:47
     * @Param [code, phone]
     * @return void
     **/
    @Override
    public void sendVerCode(String code, String phone){
        //将验证码添加到模板中，非模板格式的数据不能正常发送短信
        String content = "您本次验证码为" + code + "请在10分钟内使用。";
        //发送验证码
        Map<String, String> result = UmsClient.sendSms(content, phone);
        if(!"0".equals(result.get(UmsClient.UMS_RESULT))){
            throw new RuntimeException("短信发送失败！错误描述：" + result.get(UmsClient.UMS_DESCRIPTION));
        }
        //保存验证码到缓存
        SecurityUtil.setAttribute(codeKey + phone, code);
    }

    /**
     * @Author 钱敏杰
     * @Description 验证输入的验证码与session中保存的验证码是否一致
     * @Date 2018/11/21 8:41
     * @Param [code]
     * @return boolean true：一致，false 不一致
     **/
    @Override
    public boolean checkVerCode(String code, String phone){
        //取出session中的验证码
        String oldCode = (String)SecurityUtil.getAttribute(codeKey + phone);
        if(StringUtils.isNotEmpty(code) && code.equals(oldCode)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 验证输入的密码是否正确
     * @Date 2018/11/21 9:22
     * @Param [password]
     * @return boolean true 一致；false 不一致
     **/
    @Override
    public boolean checkPassword(String password){
        if(StringUtils.isNotEmpty(password)){
            try {
                //使用私钥解密
                byte[] decodedData = RSAUtil.decryptByPrivateKey(password, privateKey);
                password = new String(decodedData);
            } catch (Exception e) {
                logger.error("密码解密失败！", e);
                throw new RuntimeException("密码解密失败！");
            }
            //去ad域中验证密码是否正确
            return LdapUtil.checkAuthentication(SecurityUtil.getLoginid(), password);
        }else{
            return false;
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 更改当前用户AD域中的手机号码信息
     * @Date 2018/11/21 9:31
     * @Param [phone]
     * @return void
     **/
    @Override
    public void savePhone(String phone){
        //根据员工号获取员工信息
        String uid = SecurityUtil.getLoginid();
        LdapStaff staff = LdapUtil.getStaffByUid(uid);
        try {
            LdapUtil.updateAttribute(staff.getDn(), "mobile", phone);
            //更改当前用户手机信息
            SecurityUtil.setPrincipalAttribute("setPhone", phone);
        } catch (Exception e) {
            logger.error("修改手机号到ad域失败！", e);
            throw new RuntimeException("修改手机号到ad域失败！");
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 检查两个密码是否一致
     * @Date 2018/11/22 16:15
     * @Param [password1, password2]
     * @return boolean true：一致 false：不一致
     **/
    @Override
    public boolean checkPasswords(String password1, String password2){
        try {
            if(StringUtils.isNotEmpty(password1) && StringUtils.isNotEmpty(password2)){
                //使用私钥分别解密2个密码
                byte[] decodedData = RSAUtil.decryptByPrivateKey(password1, privateKey);
                password1 = new String(decodedData);
                decodedData = RSAUtil.decryptByPrivateKey(password2, privateKey);
                password2 = new String(decodedData);
                if(password1.equals(password2)){//2个密码一致
                    return true;
                }else{
                    return false;
                }
            }else{//存在为空则错误
                return false;
            }
        } catch (Exception e) {
            logger.error("解密密码失败！", e);
            throw new RuntimeException("解密密码失败！");
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 修改当前用户密码为新密码
     * @Date 2018/11/26 9:16
     * @Param [uid, newPassword]
     * @return void
     **/
    @Override
    public void resetPassword(String uid, String newPassword){
        //获取当前账号信息
        LdapStaff staff = LdapUtil.getStaffByUid(uid);
        try {
            //使用私钥解密
            byte[] decodedData = RSAUtil.decryptByPrivateKey(newPassword, privateKey);
            newPassword = new String(decodedData);
            LdapUtil.resetPassword(staff.getDn(), newPassword);
        } catch (Exception e) {
            logger.error("修改ad域当前账号的密码失败！" + uid, e);
            throw new RuntimeException("修改ad域当前账号的密码失败！");
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 验证员工号与手机号的身份信息
     * @Date 2018/11/23 15:02
     * @Param [uid, phone]
     * @return boolean
     **/
    @Override
    public boolean checkIdPhone(String uid, String phone){
        LdapStaff staff = LdapUtil.getStaffByUid(uid);
        if(StringUtils.isNotEmpty(phone) && staff != null && phone.equals(staff.getPhone())){
            return true;
        }else{
            return false;
        }
    }
}
