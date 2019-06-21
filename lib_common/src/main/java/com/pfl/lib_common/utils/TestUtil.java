package com.pfl.lib_common.utils;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TestUtil {

    /**
     * api正常返回异常
     */
    public static final int API_ERROR = -1;
    /**
     * 解析数据失败
     */
    public static final int PARSE_ERROR = -2;
    /**
     * 没有网络
     */
    public static final int NOT_NETWORK = -3;
    /**
     * 网络问题
     */
    public static final int BAD_NETWORK = -4;
    /**
     * 连接错误
     */
    public static final int CONNECT_ERROR = -5;
    /**
     * 连接超时
     */
    public static final int CONNECT_TIMEOUT = -6;
    /**
     * 未知错误
     */
    public static final int UNKNOWN_ERROR= -7;

    @IntDef({API_ERROR, PARSE_ERROR, NOT_NETWORK, BAD_NETWORK, CONNECT_ERROR, CONNECT_TIMEOUT, UNKNOWN_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrrorType {
    }
}