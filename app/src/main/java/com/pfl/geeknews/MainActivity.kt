package com.pfl.geeknews

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.howshea.basemodule.utils.toast
import com.pfl.lib_common.base.BaseActivity


class MainActivity : BaseActivity() {

    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    private lateinit var loginViewModel: LoginViewModel

    override fun initData(savedInstanceState: Bundle?) {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        startObserve()
    }

    fun startObserve() {
        loginViewModel.apply {

            loginLiveData.observe(this@MainActivity, Observer {
                dismissDialog()
                toast("请求成功")
            })

            getErrorObserve().observe(this@MainActivity, Observer {
                dismissDialog()
                toast(it.message)
            })
        }
    }

    fun requestData(view: View) {
        showDialog()
        loginViewModel.login("13488747197", "12345678a")
    }


}
