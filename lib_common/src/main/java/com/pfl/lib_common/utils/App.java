package com.pfl.lib_common.utils;

import android.app.Application;
import android.util.Log;

/**
 * 工具类
 * 获取Application
 */
public class App {

    private static final Application INSTANCE;

    static {
        Application app = null;
        try {
            app = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null);
            if (app == null)
                throw new IllegalStateException("Static initialization of Applications must be on main thread.");
        } catch (final Exception e) {
            Log.e("App", "Failed to get current application from AppGlobals." + e.getMessage());
            try {
                app = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null);
            } catch (final Exception ex) {
                Log.e("App", "Failed to get current application from ActivityThread." + e.getMessage());
            }
        } finally {
            INSTANCE = app;
        }
    }

    public static Application getInstance() {
        return INSTANCE;
    }

    public static <T> T getInstance(Class<T> clazz) {

        return (T) INSTANCE;
    }
}