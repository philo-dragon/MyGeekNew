package com.pfl.lib_common.wedget;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public abstract class AbsHeaderView<T> {

    protected Activity mActivity;
    protected LayoutInflater mInflate;
    protected T mEntity;

    public AbsHeaderView(Activity activity) {
        this.mActivity = activity;
        mInflate = LayoutInflater.from(activity);
    }

    public boolean fillView(T t, ViewGroup group) {
        this.mEntity = t;
        getView(t, group);
        return true;
    }

    protected abstract void getView(T t, ViewGroup group);

}