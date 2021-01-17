package com.dazhi.nacos.core.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public interface ReuseHttpRequest extends HttpServletRequest {

    Object getBody() throws Exception;

    /**
     * 这个就是去个重
     * @param request
     * @return
     */
    default Map<String, String[]> toDuplication(HttpServletRequest request) {
        Map<String, String[]> tmp = request.getParameterMap();
        Map<String, String[]> result = new HashMap<>(tmp.size());
        Set<String> set = new HashSet<>();
        for (Map.Entry<String, String[]> entry : tmp.entrySet()) {
            set.addAll(Arrays.asList(entry.getValue()));
            result.put(entry.getKey(), set.toArray(new String[0]));
            set.clear();
        }
        return result;
    }
}
