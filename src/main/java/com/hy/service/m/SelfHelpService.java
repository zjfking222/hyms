package com.hy.service.m;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/1 17:16
 * @Description:个人自助模块公用方法接口
 */
public interface SelfHelpService {

    /**
     * @Author 钱敏杰
     * @Description 获取图片验证码并将验证码存入session
     * @Date 2018/11/5 16:32
     * @Param [response]
     * @return void
     **/
    public void getVerificationCode(HttpServletResponse response);

    /**
     * @Author 钱敏杰
     * @Description 验证验证码
     * @Date 2018/11/5 16:37
     * @Param [code]
     * @return boolean
     **/
    public boolean authentication(String code);

    public Map<String, Object> getSalaryData(String id, String username, String password, String year, String month);

    public void getRecord();
}
