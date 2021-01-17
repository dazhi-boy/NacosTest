package com.dazhi.nacos.api.naming.pojo;

public class ServiceView {
    private String name;

    private String groupName;

    private int clusterCount;

    private int ipCount;

    private int healthyInstanceCount;

    private String triggerFlag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getClusterCount() {
        return clusterCount;
    }

    public void setClusterCount(int clusterCount) {
        this.clusterCount = clusterCount;
    }

    public int getIpCount() {
        return ipCount;
    }

    public void setIpCount(int ipCount) {
        this.ipCount = ipCount;
    }

    public int getHealthyInstanceCount() {
        return healthyInstanceCount;
    }

    public void setHealthyInstanceCount(int healthyInstanceCount) {
        this.healthyInstanceCount = healthyInstanceCount;
    }

    public String getTriggerFlag() {
        return triggerFlag;
    }

    public void setTriggerFlag(String triggerFlag) {
        this.triggerFlag = triggerFlag;
    }

    @Override
    public String toString() {
        return "ServiceView{" + "name='" + name + '\'' + ", groupName='" + groupName + '\'' + ", clusterCount="
                + clusterCount + ", ipCount=" + ipCount + ", healthyInstanceCount=" + healthyInstanceCount
                + ", triggerFlag='" + triggerFlag + '\'' + '}';
    }
}
