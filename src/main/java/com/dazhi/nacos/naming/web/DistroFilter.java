package com.dazhi.nacos.naming.web;

import com.dazhi.nacos.api.common.Constants;
import com.dazhi.nacos.api.naming.CommonParams;
import com.dazhi.nacos.core.utils.OverrideParameterRequestWrapper;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class DistroFilter implements Filter {

    private static final int PROXY_CONNECT_TIMEOUT = 2000;

    private static final int PROXY_READ_TIMEOUT = 2000;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("------------- 这里是我的过滤器，在这里对request做一些包装 ------------");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String serviceName = req.getParameter(CommonParams.SERVICE_NAME);
        // For client under 0.8.0:
        if (StringUtils.isBlank(serviceName)) {
            serviceName = req.getParameter("dom");
        }

        if (StringUtils.isNotBlank(serviceName)) {
            serviceName = serviceName.trim();
        }

        String groupName = req.getParameter(CommonParams.GROUP_NAME);
        if (StringUtils.isBlank(groupName)) {
            groupName = Constants.DEFAULT_GROUP;
        }

        String groupedServiceName = serviceName;
        if (StringUtils.isNotBlank(serviceName) && !serviceName.contains(Constants.SERVICE_INFO_SPLITER)) {
            groupedServiceName = groupName + Constants.SERVICE_INFO_SPLITER + serviceName;
        }

        OverrideParameterRequestWrapper requestWrapper = OverrideParameterRequestWrapper.buildRequest(req);
        requestWrapper.addParameter(CommonParams.SERVICE_NAME, groupedServiceName);
        filterChain.doFilter(requestWrapper, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
