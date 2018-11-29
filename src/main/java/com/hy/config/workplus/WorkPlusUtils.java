package com.hy.config.workplus;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/28 9:19
 * @Description:与恒拓app平台对接工具类
 */
@Component
public class WorkPlusUtils {

    private static final Logger logger = LoggerFactory.getLogger(WorkPlusUtils.class);
    private static WorkPlusConfig config;

    @Autowired
    public void setLdapConfig(WorkPlusConfig workPlusConfig) {
        config = workPlusConfig;
    }

    /**
     * @Author 钱敏杰
     * @Description 使用post请求访问url
     * @Date 2018/11/27 16:55
     * @Param [url, param, contentType, timeOut]
     * @return java.lang.String
     **/
    public static String sendPost(String url, String param, String contentType, int timeOut) throws Exception{
        PrintWriter out = null;
        BufferedReader in = null;
        HttpURLConnection conn = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            //打开和URL之间的连接
            conn = (HttpURLConnection)realUrl.openConnection();
            //发送POST请求，需设置以流的形式传输，默认为false
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //设置请求方式为post
            conn.setRequestMethod("POST");
            //post请求缓存设为false
            conn.setUseCaches(false);
            //设置请求数据类型
            conn.setRequestProperty("Content-Type", contentType);
            //设置超时时间10秒
            conn.setConnectTimeout(timeOut);
            //获取对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            //flush输出流的缓冲
            out.flush();
            //定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            throw e;
        } finally {// 使用finally块来关闭输出流、输入流
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if(conn != null){
                    conn.disconnect();
                }
            } catch (IOException ex) {
                throw ex;
            }
        }
        return result;
    }

    /**
     * @Author 钱敏杰
     * @Description 使用get请求访问url
     * @Date 2018/11/27 16:55
     * @Param [url, timeOut]
     * @return java.lang.String
     **/
    public static String sendGet(String url, int timeOut) throws Exception{
        BufferedReader in = null;
        HttpURLConnection conn = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            //打开和URL之间的连接
            conn = (HttpURLConnection)realUrl.openConnection();
            //设置超时时间10秒
            conn.setConnectTimeout(timeOut);
            //定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            throw e;
        } finally {// 使用finally块来关闭输出流、输入流
            try {
                if (in != null) {
                    in.close();
                }
                if(conn != null){
                    conn.disconnect();
                }
            } catch (IOException ex) {
                throw ex;
            }
        }
        return result;
    }

    /**
     * @Author 钱敏杰
     * @Description 接入恒拓app平台，获取AccessToken
     * @Date 2018/11/28 9:46
     * @Param []
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public static Map<String, Object> getAccessToken() throws Exception{
        //添加参数
        Map<String, String> param = new HashMap<>();
        param.put("grant_type", config.getGranttype());
        param.put("scope", config.getScope());
        param.put("domain_id", config.getDomainid());
        param.put("org_id", config.getOrgid());
        param.put("client_id", config.getClientid());
        param.put("client_secret", config.getClientsecret());
        //发送post请求
        String result = sendPost(config.getConnecturl()+"token", JSONObject.toJSONString(param), "application/json", 5000);
        //json数据转map
        Map<String, Object> results = JSONObject.parseObject(result, Map.class);
        return results;
    }

    /**
     * @Author 钱敏杰
     * @Description 恒拓app平台的单点登录验证
     * @Date 2018/11/28 9:54
     * @Param [ticket, token]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public static Map<String, Object> checkTicket(String ticket, String token) throws Exception{
        //生成访问的url
        String url = config.getConnecturl() + "tickets/" + ticket +"?access_token=" + token;
        //发送get请求
        String result = sendGet(url, 5000);
        //json数据转map
        Map<String, Object> results = JSONObject.parseObject(result, Map.class);
        return results;
    }

}
