package com.dazhi.nacos.naming.controllers;

import com.dazhi.nacos.api.common.Constants;
import com.dazhi.nacos.api.naming.CommonParams;
import com.dazhi.nacos.core.utils.WebUtils;
import com.dazhi.nacos.naming.core.Instance;
import com.dazhi.nacos.naming.core.ServiceManager;
import com.dazhi.nacos.naming.misc.SwitchDomain;
import com.dazhi.nacos.naming.misc.UtilsAndCommons;
import com.dazhi.nacos.api.naming.utils.NamingUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(UtilsAndCommons.NACOS_NAMING_CONTEXT + "/instance")
public class InstanceController {

    @Autowired
    private SwitchDomain switchDomain;

    @Autowired
    private ServiceManager serviceManager;
    /**
     * 注册一个服务
     * curl -X POST 'http://127.0.0.1:8848/nacos/v1/ns/instance?serviceName=nacos.naming.serviceName&ip=20.18.7.10&port=8080'
     * @return
     */
    @PostMapping
    public String register(HttpServletRequest request) throws Exception {
        //先取到namespace,可选的非必填
        final String namespaceId = WebUtils.optional(request, CommonParams.NAMESPACE_ID, Constants.DEFAULT_NAMESPACE_ID);
        //获取servicename,必填项
        final String serviceName = WebUtils.required(request, CommonParams.SERVICE_NAME);
        //校验service name是否和法
        NamingUtils.checkServiceNameFormat(serviceName);
        //将数据分装到实例中
        final Instance instance = parseInstance(request);
        //将数据放到serviceManager的缓存中
        serviceManager.registerInstance(namespaceId, serviceName, instance);
        return "ok";
    }

    private Instance parseInstance(HttpServletRequest request) throws Exception {
        String serviceName = WebUtils.required(request, CommonParams.SERVICE_NAME);
        String app = WebUtils.optional(request, "app", "DEFAULT");
        Instance instance = getIpAddress(request);
        instance.setApp(app);
        instance.setServiceName(serviceName);
        // Generate simple instance id first. This value would be updated according to
        // INSTANCE_ID_GENERATOR.
        //getIp() + "#" + getPort() + "#" + getClusterName() + "#" + getServiceName();
        instance.setInstanceId(instance.generateInstanceId());
        instance.setLastBeat(System.currentTimeMillis());
        String metadata = WebUtils.optional(request, "metadata", StringUtils.EMPTY);
        if (StringUtils.isNotEmpty(metadata)) {
            instance.setMetadata(UtilsAndCommons.parseMetadata(metadata));
        }

        instance.validate();

        return instance;
    }

    private Instance getIpAddress(HttpServletRequest request) {

        String enabledString = WebUtils.optional(request, "enabled", StringUtils.EMPTY);
        boolean enabled;
        if (StringUtils.isBlank(enabledString)) {
            enabled = BooleanUtils.toBoolean(WebUtils.optional(request, "enable", "true"));
        } else {
            enabled = BooleanUtils.toBoolean(enabledString);
        }

        String weight = WebUtils.optional(request, "weight", "1");
        boolean healthy = BooleanUtils.toBoolean(WebUtils.optional(request, "healthy", "true"));

        Instance instance = getBasicIpAddress(request);
        instance.setWeight(Double.parseDouble(weight));
        instance.setHealthy(healthy);
        instance.setEnabled(enabled);

        return instance;
    }

    private Instance getBasicIpAddress(HttpServletRequest request) {

        final String ip = WebUtils.required(request, "ip");
        final String port = WebUtils.required(request, "port");
        String cluster = WebUtils.optional(request, CommonParams.CLUSTER_NAME, StringUtils.EMPTY);
        if (StringUtils.isBlank(cluster)) {
            cluster = WebUtils.optional(request, "cluster", UtilsAndCommons.DEFAULT_CLUSTER_NAME);
        }
        boolean ephemeral = BooleanUtils.toBoolean(
                WebUtils.optional(request, "ephemeral", String.valueOf(switchDomain.isDefaultInstanceEphemeral())));

        Instance instance = new Instance();
        instance.setPort(Integer.parseInt(port));
        instance.setIp(ip);
        instance.setEphemeral(ephemeral);
        instance.setClusterName(cluster);

        return instance;
    }
}
