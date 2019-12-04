package com.pfl.geeknews

import androidx.lifecycle.MutableLiveData
import com.howshea.basemodule.utils.toast
import com.pfl.lib_common.base.BaseViewModel
import com.pfl.lib_common.utils.request
import com.pfl.lib_common.utils.start
import com.pfl.lib_common.utils.then
import kotlinx.coroutines.*

class LoginViewModel : BaseViewModel() {

    val loginLiveData: MutableLiveData<User> = MutableLiveData()
    val dailyLiveData: MutableLiveData<Daily> = MutableLiveData()

    private val repository by lazy { LoginRepository() }

    fun login(userName: String, passWord: String) {
        launch {
            val response = withContext(Dispatchers.IO) { repository.login(userName, passWord) }
            executeResponse(response) { loginLiveData.value = response.data }
        }
    }

    fun getDaily(userName: String, passWord: String) {
        launch {
            val response = withContext(Dispatchers.IO) { repository.getDaily(2019, 6, 23) }
            executeResponse(response) { dailyLiveData.value = response.data }
        }
    }

    fun anyRequest(userName: String, passWord: String) {
        start {
            toast("开始请求")
        }.request {
            repository.login(userName, passWord)
        }.then(onSuccess = {
            loginLiveData.value = it.data
        }, onError = {
            toast(it)
        }, onComplete = {
            toast("请求完成")
        })
    }
}