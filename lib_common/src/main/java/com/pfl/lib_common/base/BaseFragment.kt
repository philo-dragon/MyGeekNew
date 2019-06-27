package com.pfl.lib_common.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jojo.design.common_ui.dialog.LoadingDialog
import com.pfl.lib_common.listener.IActivity


/**
 * Created by rocky on 2018/4/12.
 */

abstract class BaseFragment : Fragment(), IActivity {

    lateinit var mContext: Activity
    lateinit var mView: View

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mContext = activity!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(contentView, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.mView = view
        initData(savedInstanceState)
        initView()
        initListener()
    }

    /**
     * 初始化数据
     */
    override fun initData(savedInstanceState: Bundle?) {}

    /**
     * 初始化view
     */
    override fun initView() {}

    /**
     * 初始化监听
     */
    fun initListener() {}

    /**********************  dialog  **********************/

    private var mDialog: LoadingDialog? = null

    fun showDialog() {
        if (mDialog == null || !mDialog!!.isShowing()) {
            mDialog = LoadingDialog(activity!!)
            mDialog!!.show()
        }
    }

    fun dismissDialog() {
        if (null != mDialog && mDialog!!.isShowing()) {
            mDialog!!.dismiss()
        }
    }
}