package com.dazhi.nacos.core.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class ReuseHttpServletRequest extends HttpServletRequestWrapper implements ReuseHttpRequest {

    private final HttpServletRequest target;

    private byte[] body;

    private Map<String, String[]> stringMap;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public ReuseHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        this.target = request;
        this.body = toBytes(request.getInputStream());
        this.stringMap = toDuplication(request);
    }

    private byte[] toBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int n = 0;
        while ((n = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, n);
        }
        return bos.toByteArray();
    }

    @Override
    public Object getBody() throws Exception {
        return null;
    }

    @Override
    public Map<String, String[]> toDuplication(HttpServletRequest request) {
        return null;
    }
}
