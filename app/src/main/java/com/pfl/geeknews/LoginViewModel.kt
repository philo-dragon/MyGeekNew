package com.pfl.geeknews

import androidx.lifecycle.MutableLiveData
import com.pfl.lib_common.base.BaseViewModel
import kotlinx.coroutines.*

class LoginViewModel : BaseViewModel() {

    val loginLiveData: MutableLiveData<User> = MutableLiveData()

    private val repository by lazy { LoginRepository() }

    fun login(userName: String, passWord: String) {
        launch {
            var response = withContext(Dispatchers.IO) { repository.login(userName, passWord) }
            executeResponse(response, { loginLiveData.value = response.data }, { })
        }
    }
}