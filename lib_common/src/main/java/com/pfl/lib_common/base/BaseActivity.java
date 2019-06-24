package com.pfl.lib_common.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import com.jojo.design.common_ui.dialog.LoadingDialog;
import com.pfl.lib_common.R;
import com.pfl.lib_common.listener.IActivity;
import com.pfl.lib_common.utils.StatusBarUtil;

/**
 * Activity 基类 设置公用的方法,属性
 */
public abstract class BaseActivity extends AppCompatActivity implements IActivity , LifecycleOwner {

    protected AppCompatActivity mContext;
    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    public Lifecycle getLifecycle() {
        return this.mLifecycleRegistry;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView();
        immersive();
        darkMode();
        //componentInject(((BaseApplication) getApplication()).getAppComponent());
        initData(savedInstanceState);
        initView();
        initListener();
    }

    /**
     * 初始化使用：
     * <p>
     * 沉浸式
     */
    private void immersive() {
        if (isImmersive()) {
            StatusBarUtil.immersive(this);
        }
    }

    /**
     * 初始化使用：
     * <p>
     * 设置深色模式
     */
    private void darkMode() {
        doDrakMode(isDarkMode());
    }

    /**
     * 设置深色模式
     * 给子类使用可以动态设置深色或浅色模式
     */
    protected void doDrakMode(boolean isDarkMode) {
        StatusBarUtil.darkMode(this, isDarkMode);
    }

    /**
     * @return 是否深色模式
     */
    public boolean isDarkMode() {
        return true;
    }

    /**
     * @return 是否沉浸式
     */
    public boolean isImmersive() {
        return true;
    }

    /**
     * @param color 状态栏背景颜色
     * @param alpha 透明度
     */
    protected void setStatusBarBgColor(int color, float alpha) {
        StatusBarUtil.immersive(this, color, alpha);
    }

    /**
     * 初始化dagger2数据
     */
    /*@Override
    public void componentInject(AppComponent appComponent) {
    }*/

    /**
     * 初始化数据
     */
    @Override
    public void initData(Bundle savedInstanceState) {
    }

    /**
     * 初始化view
     */
    @Override
    public void initView() {
    }

    /**
     * 初始化监听
     */
    public void initListener() {
    }

    /**
     * @return 窗口默认背景颜色
     */
    protected int getBackgroundColorRes() {
        return R.color.background;
    }

    private void setContentView() {
        getWindow().setBackgroundDrawableResource(getBackgroundColorRes());
        setContentView(getContentView());
    }


    private LoadingDialog mDialog = null;

    public void showDialog() {
        if (mDialog == null || !mDialog.isShowing()) {
            mDialog = new LoadingDialog(this);
            mDialog.show();
        }
    }

    public void dismissDialog() {
        if (null != mDialog && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

}