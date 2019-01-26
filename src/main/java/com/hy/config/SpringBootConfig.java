package com.hy.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * @Auther: 钱敏杰
 * @Date: 2019/1/26 9:09
 * @Description:配置springboot系统必要的相关参数
 */
@Configuration
public class SpringBootConfig {

    /**
     * @Author 钱敏杰
     * @Description 配置文件上传的文件大小限制
     * @Date 2019/1/26 9:11
     * @Param []
     * @return javax.servlet.MultipartConfigElement
     **/
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize("20MB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("102400KB");
        return factory.createMultipartConfig();
    }
}
