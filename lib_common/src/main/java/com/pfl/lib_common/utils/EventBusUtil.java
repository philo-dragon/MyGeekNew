package com.pfl.lib_common.utils;

import org.greenrobot.eventbus.EventBus;

/**
 * EventBus工具类
 * 简化eventbug的使用
 */
public class EventBusUtil {

    public static void regist(Object object) {
        if (!EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().register(object);
        }
    }

    public static void unregist(Object object) {
        if (EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().unregister(object);
        }
    }

    public static void post(Object object) {
        EventBus.getDefault().post(object);
    }

    public static void postSticky(Object object) {
        EventBus.getDefault().postSticky(object);
    }

}