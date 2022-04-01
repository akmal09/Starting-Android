package com.example.submission3project.data.api

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getSearchQuery(
        @Query("q") login: String
    ): Call<ResponseData>

    @GET("users/{login}/following")
    fun getUsersFollowing(
        @Path("login") login: String?
    ): Call<List<UserDataObject>>

    @GET("users/{login}/followers")
    fun getUserFollowers(
        @Path("login") login:String?
    ): Call<List<UserDataObject>>
}