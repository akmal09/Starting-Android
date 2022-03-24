package com.example.submission2project

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
        @Path("login") login: String
    ): Call<List<UserDataObject>>

    @GET("users/{login}/followers")
    fun getUserFollowers(
        @Path("login") login:String
    ): Call<List<UserDataObject>>
}