package com.rwbuild.bagtest.Services
import com.rwbuild.bagtest.Models.UserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiServices {

    @Headers("Accept: application/json")
    @GET("/api/?results=10")
    suspend fun getUsers(): Response<UserModel>
}