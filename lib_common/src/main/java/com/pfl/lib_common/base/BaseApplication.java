package com.pfl.lib_common.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import androidx.multidex.MultiDex;
import com.pfl.lib_common.listener.ActivityLifecycleCallBacksImpl;
import com.pfl.lib_common.utils.AppManager;


public class BaseApplication extends Application {

   // private AppComponent appComponent;

    /**
     * 如果你使用了LayoutInflater.from(getApplicationContext())或者LayoutInflater.from(getApplication())
     * 就需要一下操作，如果没有，以下方法不必重写
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerLifecycleCallbacks();//注册Activity生命周期监听
        initAppComponent();//初始化Dagger2

    }

    protected void initAppComponent() {

       /* if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .networkModule(new NetworkModule())
                    .build();
        }*/

    }


   /* public AppComponent getAppComponent() {
        return appComponent;
    }*/


    private void registerLifecycleCallbacks() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallBacksImpl() {
            @Override
            public void onActivityCreated(final Activity activity, Bundle savedInstanceState) {
                AppManager.getAppManager().addActivity(activity);
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                AppManager.getAppManager().finishActivity(activity);
            }
        });
    }

}