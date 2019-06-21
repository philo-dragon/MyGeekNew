package com.pfl.geeknews

import com.pfl.lib_common.base.HttpResponse
import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded


interface LoginServer {

    /**
     * 密码登录
     *
     * @param account
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("tel") account: String, @Field("pwd") password: String): HttpResponse<User>

}
