package com.example.githubissuetracker.core.networking

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private val okHttpClient = OkHttpClient
        .Builder()
        .readTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .build()

    private val gsonConverterFactory = GsonConverterFactory.create()


    private val retrofit = Retrofit.Builder()
        .baseUrl(EndPoints.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()

    val githubService = retrofit.create(GithubService::class.java)
}