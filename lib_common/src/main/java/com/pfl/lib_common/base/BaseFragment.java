package com.pfl.lib_common.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.jojo.design.common_ui.dialog.LoadingDialog;
import com.pfl.lib_common.listener.IActivity;


/**
 * Created by rocky on 2018/4/12.
 */

public abstract class BaseFragment extends Fragment implements IActivity {

    protected Activity mContext;
    protected View mView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getContentView(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mView = view;
       // componentInject(((BaseApplication) getActivity().getApplication()).getAppComponent());
        initData(savedInstanceState);
        initView();
        initListener();
    }

    /**
     * 初始化dagger2数据
     */
   /* @Override
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

    private LoadingDialog mDialog = null;

    public void showDialog() {
        if (mDialog == null || !mDialog.isShowing()) {
            mDialog = new LoadingDialog(getActivity());
            mDialog.show();
        }
    }

    public void dismissDialog() {
        if (null != mDialog && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}