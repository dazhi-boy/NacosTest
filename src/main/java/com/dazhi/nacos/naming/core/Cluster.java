package com.dazhi.nacos.naming.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cluster extends com.dazhi.nacos.api.naming.pojo.Cluster {
    private static final String CLUSTER_NAME_SYNTAX = "[0-9a-zA-Z-]+";

    private static final long serialVersionUID = 8940123791150907510L;

    private String sitegroup = StringUtils.EMPTY;

    private int defCkport = 80;

    private int defIpPort = -1;

    @JsonIgnore
    private Set<Instance> persistentInstances = new HashSet<>();

    @JsonIgnore
    private Set<Instance> ephemeralInstances = new HashSet<>();

    @JsonIgnore
    private Service service;

    @JsonIgnore
    private volatile boolean inited = false;

    public static String getClusterNameSyntax() {
        return CLUSTER_NAME_SYNTAX;
    }

    public String getSitegroup() {
        return sitegroup;
    }

    public void setSitegroup(String sitegroup) {
        this.sitegroup = sitegroup;
    }

    public int getDefCkport() {
        return defCkport;
    }

    public void setDefCkport(int defCkport) {
        this.defCkport = defCkport;
    }

    public int getDefIPPort() {
        return defIpPort;
    }

    public void setDefIpPort(int defIpPort) {
        this.defIpPort = defIpPort;
    }

    public List<Instance> allIPs() {
        List<Instance> allInstances = new ArrayList<>();
        allInstances.addAll(persistentInstances);
        allInstances.addAll(ephemeralInstances);
        return allInstances;
    }

    public int getDefIpPort() {
        return defIpPort;
    }

    public Set<Instance> getPersistentInstances() {
        return persistentInstances;
    }

    public void setPersistentInstances(Set<Instance> persistentInstances) {
        this.persistentInstances = persistentInstances;
    }

    public Set<Instance> getEphemeralInstances() {
        return ephemeralInstances;
    }

    public void setEphemeralInstances(Set<Instance> ephemeralInstances) {
        this.ephemeralInstances = ephemeralInstances;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void validate() {
        Assert.notNull(getName(), "cluster name cannot be null");
        Assert.notNull(service, "service cannot be null");
        if (!getName().matches(CLUSTER_NAME_SYNTAX)) {
            throw new IllegalArgumentException(
                    "cluster name can only have these characters: 0-9a-zA-Z-, current: " + getName());
        }
    }

    public void init() {
        if (inited) {
            return;
        }
//        checkTask = new HealthCheckTask(this);
//
//        HealthCheckReactor.scheduleCheck(checkTask);
        inited = true;
    }
}
