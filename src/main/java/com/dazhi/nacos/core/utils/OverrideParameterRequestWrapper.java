package com.dazhi.nacos.core.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

public class OverrideParameterRequestWrapper extends HttpServletRequestWrapper {

    private Map<String, String[]> params = new HashMap<>();

    public OverrideParameterRequestWrapper(HttpServletRequest request) {
        super(request);
        this.params.putAll(request.getParameterMap());
    }

    public static OverrideParameterRequestWrapper buildRequest(HttpServletRequest request) {
        return new OverrideParameterRequestWrapper(request);
    }

    /**
     * build OverrideParameterRequestWrapper and addParameter.
     *
     * @param request origin HttpServletRequest
     * @param name    name
     * @param value   value
     * @return {@link OverrideParameterRequestWrapper}
     */
    public static OverrideParameterRequestWrapper buildRequest(HttpServletRequest request, String name, String value) {
        OverrideParameterRequestWrapper requestWrapper = new OverrideParameterRequestWrapper(request);
        requestWrapper.addParameter(name, value);
        return requestWrapper;
    }

    /**
     * build OverrideParameterRequestWrapper and addParameter.
     *
     * @param request          origin HttpServletRequest
     * @param appendParameters need to append to request
     * @return {@link OverrideParameterRequestWrapper}
     */
    public static OverrideParameterRequestWrapper buildRequest(HttpServletRequest request,
                                                               Map<String, String[]> appendParameters) {
        OverrideParameterRequestWrapper requestWrapper = new OverrideParameterRequestWrapper(request);
        requestWrapper.params.putAll(appendParameters);
        return requestWrapper;
    }

    @Override
    public String getParameter(String name) {
        String[] values = params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return params;
    }

    @Override
    public String[] getParameterValues(String name) {
        return params.get(name);
    }

    /**
     * addParameter.
     *
     * @param name  name
     * @param value value
     */
    public void addParameter(String name, String value) {
        if (value != null) {
            params.put(name, new String[] {value});
        }
    }
}
