package com.ridhojagis.githubuserapi.api

import com.ridhojagis.githubuserapi.data.model.UserDetailResponse
import com.ridhojagis.githubuserapi.data.model.UserListItem
import com.ridhojagis.githubuserapi.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization : token 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")

    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @Headers("Authorization: token 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")
    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @Headers("Authorization: token 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")
    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String?
    ): Call<List<UserListItem>>

    @Headers("Authorization: token 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")
    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String?
    ): Call<List<UserListItem>>
}