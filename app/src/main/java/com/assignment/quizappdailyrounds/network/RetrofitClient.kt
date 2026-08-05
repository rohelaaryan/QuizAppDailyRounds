package com.assignment.quizappdailyrounds.network

import com.assignment.quizappdailyrounds.data.api.QuizApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val BASE_URL =
        "https://gist.githubusercontent.com/"


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: QuizApi = retrofit.create(QuizApi::class.java)

}