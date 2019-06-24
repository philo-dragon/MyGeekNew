package com.pfl.geeknews

import com.pfl.lib_common.base.HttpResponse
import io.reactivex.Observable
import retrofit2.http.*


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

    @GET("day/{year}/{month}/{day}")
    suspend fun getDaily( @Path("year") year: Int,@Path("month") month: Int, @Path("day") day: Int): HttpResponse<Daily>

}
