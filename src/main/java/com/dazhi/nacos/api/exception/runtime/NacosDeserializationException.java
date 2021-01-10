package com.dazhi.nacos.api.exception.runtime;

import java.lang.reflect.Type;

public class NacosDeserializationException extends NacosRuntimeException {
    public static final int ERROR_CODE = 101;

    private static final long serialVersionUID = -2742350751684273728L;

    private static final String DEFAULT_MSG = "Nacos deserialize failed. ";

    private static final String MSG_FOR_SPECIFIED_CLASS = "Nacos deserialize for class [%s] failed. ";

    private Class<?> targetClass;

    public NacosDeserializationException() {
        super(ERROR_CODE);
    }

    public NacosDeserializationException(Class<?> targetClass) {
        super(ERROR_CODE, String.format(MSG_FOR_SPECIFIED_CLASS, targetClass.getName()));
        this.targetClass = targetClass;
    }

    public NacosDeserializationException(Type targetType) {
        super(ERROR_CODE, String.format(MSG_FOR_SPECIFIED_CLASS, targetType.toString()));
    }

    public NacosDeserializationException(Throwable throwable) {
        super(ERROR_CODE, DEFAULT_MSG, throwable);
    }

    public NacosDeserializationException(Class<?> targetClass, Throwable throwable) {
        super(ERROR_CODE, String.format(MSG_FOR_SPECIFIED_CLASS, targetClass.getName()), throwable);
        this.targetClass = targetClass;
    }

    public NacosDeserializationException(Type targetType, Throwable throwable) {
        super(ERROR_CODE, String.format(MSG_FOR_SPECIFIED_CLASS, targetType.toString()), throwable);
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }
}
