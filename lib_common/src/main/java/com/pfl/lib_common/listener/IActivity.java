package com.pfl.lib_common.listener;


import android.os.Bundle;

/**
 * Activity公共函数接口
 * 按方法书写顺序执行
 */
public interface IActivity {

    int getContentView();

    void initData(Bundle savedInstanceState);

    void initView();

}