package com.pfl.geeknews

import com.pfl.lib_common.base.BaseRepository
import com.pfl.lib_common.base.HttpResponse
import com.pfl.lib_common.utils.App
import com.pfl.lib_common.utils.RetrofitClient

class LoginRepository : BaseRepository() {

    suspend fun login(userName: String, passWord: String): HttpResponse<User> {
        return apiCall { RetrofitClient.getInstance(App.getInstance()).create(LoginServer::class.java).login(userName, passWord)}
    }

}