package com.pfl.lib_common.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.pfl.lib_common.R;

import static android.view.View.SYSTEM_UI_FLAG_LOW_PROFILE;
import static android.view.WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_OFF;

public class EyeCareUtil {

    private static WindowManager mWindowManager;
    private static View view;
    private static WindowManager.LayoutParams params;

    public static void openEyeCare() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(App.getInstance())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                App.getInstance().startActivity(intent);
                return;
            } else {
                //绘ui代码, 这里说明6.0系统已经有权限了
                setWindowView(R.layout.eye_care_layout);
            }
        } else {
            //绘ui代码,这里android6.0以下的系统直接绘出即可
            setWindowView(R.layout.eye_care_layout);
        }
    }

    private static void setWindowView(int p) {
        if (null == mWindowManager) {
            mWindowManager = (WindowManager) App.getInstance().getSystemService(Context.WINDOW_SERVICE);
            params = getWindowManagerParams();
        }
        if (view == null) {
            view = LayoutInflater.from(App.getInstance()).inflate(p, null);
            mWindowManager.addView(view, params);
        }
    }

    public static void closeEyeCare() {
        mWindowManager.removeView(view);
        view = null;
    }

    public static WindowManager.LayoutParams getWindowManagerParams() {
        int heightPixels = App.getInstance().getResources().getDisplayMetrics().heightPixels;
        int widthPixels = App.getInstance().getResources().getDisplayMetrics().widthPixels;
        int max = Math.max(heightPixels, widthPixels);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = max + ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
        lp.height = max + ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
        lp.type = (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT <= 24) ? WindowManager.LayoutParams.TYPE_SYSTEM_ERROR : WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        lp.format = PixelFormat.TRANSLUCENT;

        lp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                | WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        lp.flags = 17368856;
        lp.dimAmount = -1f;
        lp.gravity = 8388659;
        lp.buttonBrightness = BRIGHTNESS_OVERRIDE_OFF;
        lp.systemUiVisibility = SYSTEM_UI_FLAG_LOW_PROFILE;
        return lp;
    }
}
