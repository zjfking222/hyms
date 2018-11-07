package com.hy.config.jco;

import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class JcoDestinationDataProvider implements DestinationDataProvider {
    private DestinationDataEventListener el;
    private Map<String, Properties> destinations;
    private static JcoDestinationDataProvider provider = new JcoDestinationDataProvider();

    private JcoDestinationDataProvider() {
        if (provider == null)
            destinations = new ConcurrentHashMap<>();
    }

    public static JcoDestinationDataProvider getInstance() {
        return provider;
    }

    @Override
    public Properties getDestinationProperties(String destinationName) {
        if (destinations.containsKey(destinationName))
            return destinations.get(destinationName);
        else
            throw new RuntimeException("Destination " + destinationName
                    + " is not available");
    }

    @Override
    public boolean supportsEvents() {
        return true;
    }

    @Override
    public void setDestinationDataEventListener(DestinationDataEventListener destinationDataEventListener) {
        this.el = destinationDataEventListener;
    }

    /**
     * Add new destination 添加连接配置属性
     */
    public void addDestination(String destinationName, Properties properties) {
        destinations.put(destinationName, properties);
    }

    /**
     * @Author 钱敏杰
     * @Description 判断当前名称的连接是否已存在
     * @Date 2018/11/6 10:33
     * @Param [destinationName]
     * @return boolean
     **/
    public boolean isExist(String destinationName){
        //判断当前key是否存在属性对象是否存在
        if(destinations.containsKey(destinationName) && destinations.get(destinationName) != null){
            return true;
        }else {
            return false;
        }
    }
}
