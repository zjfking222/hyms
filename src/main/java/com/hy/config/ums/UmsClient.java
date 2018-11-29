package com.hy.config.ums;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/20 10:53
 * @Description:
 */
@Component
public class UmsClient {

    private static final Logger logger = LoggerFactory.getLogger(UmsClient.class);
    //配置参数
    private static UmsConfig config;
    //接口操作客户端
    private static SmsPortType service;

    //参数
    public static String UMS_RESULT = "result";
    public static String UMS_DESCRIPTION = "description";
    public static String UMS_TASKID = "taskid";
    public static String UMS_FAILLIST = "faillist";

    @Autowired
    public void setUmsConfig(UmsConfig umsConfig){
        config = umsConfig;
    }

    /**
     * @Author 钱敏杰
     * @Description 创建桐乡联通接口客户端，单例
     * @Date 2018/11/20 14:58
     * @Param []
     * @return com.hy.config.ums.SmsPortType
     **/
    public static SmsPortType getInstance(){
        if(service == null){
            // 代理工厂
            JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
            // 设置代理地址
            jaxWsProxyFactoryBean.setAddress(config.getAddress());
            // 设置接口类型
            jaxWsProxyFactoryBean.setServiceClass(SmsPortType.class);
            // 创建一个代理接口实现
            service = (SmsPortType)jaxWsProxyFactoryBean.create();
        }
        return service;
    }

    /**
     * @Author 钱敏杰
     * @Description 生成发送短信的流水号
     * @Date 2018/11/20 15:25
     * @Param [pre]
     * @return java.lang.String
     **/
    public static String createFlowNumber(String pre){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        //3位前缀加17位的时间字符串组成20位的流水号
        String number = pre + sdf.format(new Date());
        return number;
    }

    /**
     * @Author 钱敏杰
     * @Description 调用sms接口向手机发送短信
     * @Date 2018/11/20 15:54
     * @Param [content：必须使用配置的模板生成的短信内容, phone：可为多个电话号码，用“,”隔开，最多1000]
     * @return java.util.Map<java.lang.String,java.lang.String>
     **/
    public static Map<String, String> sendSms(String content, String phone){
        SmsPortType client = getInstance();
        String fnum = createFlowNumber("sms");
        //传参调用接口，注意传入参数不能为null
        String response = client.sms(config.geteNumber(), config.getUsername(), config.getPassword(), content, phone, fnum, "", "1", "", "", "");
        logger.info("向" + phone + "发送短信，返回结果：" + response);
        if(StringUtils.isNotEmpty(response) && response.contains("&")){
            String[] result = response.split("&");
            if(result.length < 4){
                throw new RuntimeException("未知返回！返回值：" + response);
            }else{
                Map<String, String> resultMap = new HashMap<>();
                //截取出值保存到map
                String[] str = result[0].split("=");
                resultMap.put(UMS_RESULT, str[1]);
                str = result[1].split("=");
                resultMap.put(UMS_DESCRIPTION, str[1]);
                str = result[2].split("=");
                resultMap.put(UMS_TASKID, str[1]);
                str = result[3].split("=");
                if(str.length >1){//存在错误的手机号列表
                    resultMap.put(UMS_FAILLIST, str[1]);
                }
                return resultMap;
            }
        }else{
            throw new RuntimeException("未知返回！");
        }
    }

}
