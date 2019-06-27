package com.pfl.lib_common.utils;

/**
 * Created by rocky on 2017/12/28.
 */

public class BaseUrlManager {

    public static String RELEASE_URL;
    public static boolean isDebug;

    public static void init(String base_url, boolean is_debug) {
        RELEASE_URL = base_url;
        isDebug = is_debug;
    }

    public static String getBaseUrl() {
        return RELEASE_URL;
    }
}
