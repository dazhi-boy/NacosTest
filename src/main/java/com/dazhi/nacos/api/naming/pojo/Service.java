package com.dazhi.nacos.api.naming.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Service implements Serializable {
    private static final long serialVersionUID = -3470985546826874460L;
    private String name;
    private float protectThreshold = 0.0F;
    private String appName;
    private String groupName;
    private Map<String, String> metadata = new HashMap<String, String>();

    public Service() {
    }

    public Service(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getProtectThreshold() {
        return protectThreshold;
    }

    public void setProtectThreshold(float protectThreshold) {
        this.protectThreshold = protectThreshold;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }
}
