package com.pfl.geeknews

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.howshea.basemodule.utils.toast


class MainActivity : AppCompatActivity(), LifecycleOwner {

    private val mLifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): Lifecycle {
        return this.mLifecycleRegistry
    }

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        startObserve()
    }

    fun startObserve() {
        loginViewModel.apply {

            loginLiveData.observe(this@MainActivity, Observer {
                toast("请求成功")
            })

            getErrorObserve().observe(this@MainActivity, Observer {

            })
        }
    }

    fun requestData(view: View) {
        loginViewModel.login("13488747197", "12345678a")
    }


}
