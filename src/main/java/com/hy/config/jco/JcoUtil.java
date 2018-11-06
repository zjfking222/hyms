package com.hy.config.jco;

import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.jco.ext.Environment;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/1 8:57
 * @Description:jco公用方法
 */
@Component
public class JcoUtil {

    private static final Logger logger = LoggerFactory.getLogger(JcoUtil.class);
    private static JcoConfig jcoConfig;

    @Autowired
    public void setJcoConfig(JcoConfig config){
        jcoConfig=config;
    }

   @PostConstruct
    public void init() {
       //环境注册
       Environment.registerDestinationDataProvider(JcoDestinationDataProvider.getInstance());
    }

    /**
     * @Author 钱敏杰
     * @Description 获取jco操作对象
     * @Date 2018/11/2 8:24
     * @Param [destinationName：建立的连接名称，需要控制连接数，不能建立过多，此处一般为null]
     * @return com.sap.conn.jco.JCoDestination
     **/
    public static JCoDestination getInstance(String destinationName){
        JCoDestination destination = null;
        //名称若为空则使用默认值（即使用默认连接）
        if(StringUtils.isEmpty(destinationName)){
            destinationName = jcoConfig.getDestinationName();
        }
        //判断是否有当前名称的属性配置，若无，则添加
        if(!JcoDestinationDataProvider.getInstance().isExist(destinationName)){
            //配置属性
            Properties connectProperties = new Properties();
            connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, jcoConfig.getAshost());
            connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, jcoConfig.getSysnr());
            connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, jcoConfig.getClient());
            connectProperties.setProperty(DestinationDataProvider.JCO_USER, jcoConfig.getUser());
            connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, jcoConfig.getPasswd());
            connectProperties.setProperty(DestinationDataProvider.JCO_LANG, jcoConfig.getLang());
            JcoDestinationDataProvider.getInstance().addDestination(destinationName, connectProperties);
        }
        //判断环境是否已注册，若未注册则注册
        if(!Environment.isDestinationDataProviderRegistered() ){
            Environment.registerDestinationDataProvider(JcoDestinationDataProvider.getInstance());
        }
        try {
            destination = JCoDestinationManager.getDestination(destinationName);
        } catch (JCoException e) {
            logger.error("连接jco异常！", e);
            throw new RuntimeException("连接jco异常！");
        }
        return destination;
    }

    /**
     * @Author 钱敏杰
     * @Description 从对象仓库中获取RFM函数
     * @Date 2018/11/1 10:24
     * @Param [name]
     * @return com.sap.conn.jco.JCoFunction
     **/
    public static JCoFunction getFunction(JCoDestination destination, String name){
        JCoFunction function = null;
        try {
            function = destination.getRepository().getFunction(name);
        } catch (JCoException e) {
            logger.error("从对象仓库中获取RFM函数异常！", e);
            throw new RuntimeException("从对象仓库中获取RFM函数异常！");
        }
        return function;
    }

    /**
     * @Author 钱敏杰
     * @Description 添加请求参数
     * @Date 2018/11/1 14:31
     * @Param [function, name, value]
     * @return void
     **/
    public static void setParameter(JCoFunction function, String name, String value){
        JCoParameterList parameterList = function.getImportParameterList();
        parameterList.setValue(name, value);
    }

    /**
     * @Author 钱敏杰
     * @Description 执行函数
     * @Date 2018/11/1 14:32
     * @Param [function, destination]
     * @return void
     **/
    public static void executeFunction(JCoFunction function, JCoDestination destination){
        try {
            function.execute(destination);
        } catch (JCoException e) {
            logger.error("执行RFM函数异常！", e);
            throw new RuntimeException("执行RFM函数异常！");
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 获取表数据
     * @Date 2018/11/1 14:32
     * @Param [function, tableName]
     * @return com.sap.conn.jco.JCoTable
     **/
    public static JCoTable getTable(JCoFunction function, String tableName){
        JCoTable table = function.getTableParameterList().getTable(tableName);
        return table;
    }

    /**
     * @Author 钱敏杰
     * @Description //TODO
     * @Date 2018/11/1 14:32
     * @Param [function, strName]
     * @return com.sap.conn.jco.JCoStructure
     **/
    public static JCoStructure getStructure(JCoFunction function, String strName){
        JCoStructure structure = function.getImportParameterList().getStructure(strName);
        return structure;
    }

    /**
     * @Author 钱敏杰
     * @Description 获取返回的结果参数
     * @Date 2018/11/1 14:32
     * @Param [function, name]
     * @return java.lang.String
     **/
    public static String getExportParameter(JCoFunction function, String name){
        String str = function.getExportParameterList().getString(name);
        return str;
    }

    /**
     * @Author 钱敏杰
     * @Description 获取table中的单条数据，返回相应dto对象
     * @Date 2018/11/3 8:36
     * @Param [table, target]
     * @return T
     **/
    public static <T>T getInfoFromTable(JCoTable table, T target){
        //获取对象方法
        Method[] methods = target.getClass().getMethods();
        //循环利用反射方法取值并保存到dto对象中
        for (Method method : methods) {
            String name = method.getName();
            //除了dkhj，set方法中的属性都需要取
            if (name.startsWith("set")) {
                //名称需要改为大写
                String valueName = name.substring(3).toUpperCase();
                try {
                    String value = table.getString(valueName);
                    if(StringUtils.isNotEmpty(value)){
                        method.invoke(target, value);
                    }
                } catch (Exception e) {
                    //部分字段无值会报错，跳过即可
                }
            }
        }
        return target;
    }

    /**
     * @Author 钱敏杰
     * @Description 获取table中的所有数据，并以list返回
     * @Date 2018/11/3 8:36
     * @Param [table, target]
     * @return java.util.List<T>
     **/
    public static <T> List<T> getInfoListFromTable(JCoTable table, T target){
        List<T> list = null;
        if(table != null && table.getNumRows() >0){
            list = new ArrayList<>();
            //循环table中的数据，全部取出
            for(int i=0;i<table.getNumRows();i++){
                table.setRow(i);
                //调用单条数据获取方法
                T rtarget = getInfoFromTable(table, target);
                list.add(rtarget);
            }
        }
        return list;
    }
}
