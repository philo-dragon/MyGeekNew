package com.howshea.basemodule.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import com.pfl.lib_common.utils.App

private var toast: Toast? = null

@SuppressLint("ShowToast")
fun Context.toast(text: CharSequence) {
    toast ?: let {
        toast = Toast.makeText(this.applicationContext, text, Toast.LENGTH_SHORT)
    }
    toast?.apply {
        setText(text)
        show()
    }
}

/**
 * @param resId 字符串资源
 */
fun Context.toast(@StringRes resId: Int) {
    toast(getString(resId))
}

@SuppressLint("ShowToast")
fun toast(text: CharSequence) {
    App.getInstance().toast(text)
}

/**
 * @param resId 字符串资源
 */
fun toast(@StringRes resId: Int) {
    toast(App.getInstance().resources.getString(resId))
}