package com.pfl.geeknews

import androidx.lifecycle.MutableLiveData
import com.pfl.lib_common.base.BaseViewModel
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
            val response = withContext(Dispatchers.IO) { repository.getDaily(2019, 6,23) }
            executeResponse(response) { dailyLiveData.value = response.data }
        }
    }
}