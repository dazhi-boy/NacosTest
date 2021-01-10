package com.dazhi.nacos.naming.misc;

import com.dazhi.nacos.api.exception.NacosException;
import com.dazhi.nacos.common.utils.JacksonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class UtilsAndCommons {
    // ********************** Nacos HTTP Context ************************ \\

    public static final String NACOS_SERVER_CONTEXT = "/nacos";

    public static final String NACOS_SERVER_VERSION = "/v1";

    public static final String DEFAULT_NACOS_NAMING_CONTEXT = NACOS_SERVER_VERSION + "/ns";

    public static final String NACOS_NAMING_CONTEXT = DEFAULT_NACOS_NAMING_CONTEXT;

    public static final String UNKNOWN_SITE = "unknown";

    public static final String DEFAULT_CLUSTER_NAME = "DEFAULT";

    public static final String LOCALHOST_SITE = UtilsAndCommons.UNKNOWN_SITE;

    /**
     * 从字符串获取源数据
     * @param metadata
     * @return
     */
    public static Map<String, String> parseMetadata(String metadata) throws NacosException {
        Map<String, String> metadataMap = new HashMap<>(16);

        if (StringUtils.isBlank(metadata)) {
            return metadataMap;
        }

        try {
            metadataMap = JacksonUtils.toObj(metadata, new TypeReference<Map<String, String>>() {
            });
        } catch (Exception e) {
            String[] datas = metadata.split(",");
            if (datas.length > 0) {
                for (String data : datas) {
                    String[] kv = data.split("=");
                    if (kv.length != 2) {
                        throw new NacosException(NacosException.INVALID_PARAM, "metadata format incorrect:" + metadata);
                    }
                    metadataMap.put(kv[0], kv[1]);
                }
            }
        }

        return metadataMap;
    }
}
