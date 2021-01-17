package com.dazhi.nacos.naming.controllers;

import com.dazhi.nacos.api.common.Constants;
import com.dazhi.nacos.api.naming.pojo.ServiceView;
import com.dazhi.nacos.api.naming.utils.NamingUtils;
import com.dazhi.nacos.common.utils.JacksonUtils;
import com.dazhi.nacos.naming.core.Service;
import com.dazhi.nacos.naming.core.ServiceManager;
import com.dazhi.nacos.naming.misc.UtilsAndCommons;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(UtilsAndCommons.NACOS_NAMING_CONTEXT + "/catalog")
public class CatalogController {
    @Autowired
    protected ServiceManager serviceManager;

    @GetMapping("/services")
    public Object listDetail(@RequestParam(required = false) boolean withInstances,
                             @RequestParam(defaultValue = Constants.DEFAULT_NAMESPACE_ID) String namespaceId,
                             @RequestParam(required = false) int pageNo, @RequestParam(required = false) int pageSize,
                             @RequestParam(name = "serviceNameParam", defaultValue = StringUtils.EMPTY) String serviceName,
                             @RequestParam(name = "groupNameParam", defaultValue = StringUtils.EMPTY) String groupName,
                             @RequestParam(name = "instance", defaultValue = StringUtils.EMPTY) String containedInstance,
                             @RequestParam(required = false) boolean hasIpCount) {

        String param = StringUtils.isBlank(serviceName) && StringUtils.isBlank(groupName) ? StringUtils.EMPTY
                : NamingUtils.getGroupedNameOptional(serviceName, groupName);

        ObjectNode result = JacksonUtils.createEmptyJsonNode();

        List<Service> services = new ArrayList<>();
        final int total = serviceManager
                .getPagedService(namespaceId, pageNo - 1, pageSize, param, containedInstance, services, hasIpCount);
        if (CollectionUtils.isEmpty(services)) {
            result.replace("serviceList", JacksonUtils.transferToJsonNode(Collections.emptyList()));
            result.put("count", 0);
            return result;
        }

        List<ServiceView> serviceViews = new LinkedList<>();
        for (Service service : services) {
            ServiceView serviceView = new ServiceView();
            serviceView.setName(NamingUtils.getServiceName(service.getName()));
            serviceView.setGroupName(NamingUtils.getGroupName(service.getName()));
            serviceView.setClusterCount(service.getClusterMap().size());
            serviceView.setIpCount(service.allIPs().size());
//            serviceView.setHealthyInstanceCount(service.healthyInstanceCount());
//            serviceView.setTriggerFlag(service.triggerFlag() ? "true" : "false");
            serviceViews.add(serviceView);
        }

        result.replace("serviceList", JacksonUtils.transferToJsonNode(serviceViews));
        result.put("count", total);

        return result;
    }
}
