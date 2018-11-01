package com.hy.utils;

import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;

import java.util.HashMap;
import java.util.Properties;

public class DataProvider implements DestinationDataProvider {
    private DestinationDataEventListener el;
    private HashMap<String, Properties> destinations;
    private static DataProvider provider = new DataProvider();

    private DataProvider() {
        if (provider == null)
            destinations = new HashMap<>();
    }

    public static DataProvider getInstance() {
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
}
