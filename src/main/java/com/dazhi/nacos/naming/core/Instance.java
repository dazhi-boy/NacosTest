package com.dazhi.nacos.naming.core;

import com.dazhi.nacos.api.exception.NacosException;
import com.dazhi.nacos.common.utils.IPUtil;
import com.dazhi.nacos.naming.misc.Loggers;
import com.dazhi.nacos.naming.misc.UtilsAndCommons;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Instance extends com.dazhi.nacos.api.naming.pojo.Instance implements Comparable {
    private static final double MAX_WEIGHT_VALUE = 10000.0D;

    private static final double MIN_POSITIVE_WEIGHT_VALUE = 0.01D;

    private static final double MIN_WEIGHT_VALUE = 0.00D;

    private volatile long lastBeat = System.currentTimeMillis();

    private volatile boolean marked = false;

    private String app;

    private static final Pattern ONLY_DIGIT_AND_DOT = Pattern.compile("(\\d|\\.)+");

    private static final String SPLITER = "_";

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public long getLastBeat() {
        return lastBeat;
    }

    public void setLastBeat(long lastBeat) {
        this.lastBeat = lastBeat;
    }

    @Override
    public String toString() {
        return getDatumKey() + SPLITER + getWeight() + SPLITER + isHealthy() + SPLITER + marked + SPLITER
                + getClusterName();
    }

    @JsonIgnore
    public String getDatumKey() {
        if (getPort() > 0) {
            return getIp() + ":" + getPort() + ":" + UtilsAndCommons.LOCALHOST_SITE + ":" + getClusterName();
        } else {
            return getIp() + ":" + UtilsAndCommons.LOCALHOST_SITE + ":" + getClusterName();
        }
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Instance)) {
            Loggers.SRV_LOG.error("[INSTANCE-COMPARE] Object is not an instance of IPAdress, object: {}", o.getClass());
            throw new IllegalArgumentException("Object is not an instance of IPAdress,object: " + o.getClass());
        }

        Instance instance = (Instance) o;
        String ipKey = instance.toString();

        return this.toString().compareTo(ipKey);
    }

    public String generateInstanceId() {
        return getIp() + "#" + getPort() + "#" + getClusterName() + "#" + getServiceName();
    }

    //判断该实例是否有效
    public void validate() throws NacosException {
        if (onlyContainsDigitAndDot()) {
            if (!IPUtil.containsPort(getIp() + IPUtil.IP_PORT_SPLITER + getPort())) {
                throw new NacosException(NacosException.INVALID_PARAM,
                        "instance format invalid: Your IP address is spelled incorrectly");
            }
        }

        if (getWeight() > MAX_WEIGHT_VALUE || getWeight() < MIN_WEIGHT_VALUE) {
            throw new NacosException(NacosException.INVALID_PARAM,
                    "instance format invalid: The weights range from " + MIN_WEIGHT_VALUE + " to " + MAX_WEIGHT_VALUE);
        }
    }

    private boolean onlyContainsDigitAndDot() {
        Matcher matcher = ONLY_DIGIT_AND_DOT.matcher(getIp());
        return matcher.matches();
    }
}
