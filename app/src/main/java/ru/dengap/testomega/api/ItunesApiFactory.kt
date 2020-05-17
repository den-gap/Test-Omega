package ru.dengap.testomega.api

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ItunesApiFactory {
    private const val BASE_URL = "https://itunes.apple.com/"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .build()
    val apiService: ItunesApiService = retrofit.create(ItunesApiService::class.java)
}