package com.example.myandroidapp

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("users")
    suspend fun getUsers(@Query("page") page: Int, @Query("per_page") perPage: Int): Response<UserResponse>
}

data class UserResponse(
    val data: List<UserData>
)

object ApiClient {
    private const val BASE_URL = "https://reqres.in/api/"

    fun create(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}
