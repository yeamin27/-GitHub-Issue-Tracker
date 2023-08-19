package com.example.githubissuetracker.core.networking

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitInstance {
    private val okHttpClient = OkHttpClient
        .Builder()
        .readTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .build()

    private val gson = GsonBuilder()
        .setLenient()
        .create()
    private val gsonConverterFactory = GsonConverterFactory.create(gson)

    private val scalarsConverterFactory = ScalarsConverterFactory.create()


    private val retrofit = Retrofit.Builder()
        .baseUrl(EndPoints.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(scalarsConverterFactory)
        .addConverterFactory(gsonConverterFactory)
        .build()

    val githubService: GithubService = retrofit.create(GithubService::class.java)
}