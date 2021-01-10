package com.dazhi.nacos.api.naming.pojo;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class Cluster {
    private static final long serialVersionUID = -7196138840047197271L;

    /**
     * Name of belonging service.
     */
    private String serviceName;

    /**
     * Name of cluster.
     */
    private String name;

//    private AbstractHealthChecker healthChecker = new Tcp();

    /**
     * Default registered port for instances in this cluster.
     */
    private int defaultPort = 80;

    /**
     * Default health check port of instances in this cluster.
     */
    private int defaultCheckPort = 80;

    /**
     * Whether or not use instance port to do health check.
     */
    private boolean useIPPort4Check = true;

    private Map<String, String> metadata = new HashMap<String, String>();

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDefaultPort() {
        return defaultPort;
    }

    public void setDefaultPort(int defaultPort) {
        this.defaultPort = defaultPort;
    }

    public int getDefaultCheckPort() {
        return defaultCheckPort;
    }

    public void setDefaultCheckPort(int defaultCheckPort) {
        this.defaultCheckPort = defaultCheckPort;
    }

    public boolean isUseIPPort4Check() {
        return useIPPort4Check;
    }

    public void setUseIPPort4Check(boolean useIPPort4Check) {
        this.useIPPort4Check = useIPPort4Check;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }
}
