package ru.dengap.testomega.api

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ItunesApiFactory {
    private val BASE_URL = "https://itunes.apple.com/"

    //    private val gson = GsonBuilder()
//        .registerTypeAdapter(Result::class.java, ResultDeserializer())
//        .create()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .build()
    val apiService = retrofit.create(ItunesApiService::class.java)
}

//class ResultDeserializer : JsonDeserializer<Result> {
//    override fun deserialize(
//        json: JsonElement?,
//        typeOfT: Type?,
//        context: JsonDeserializationContext?
//    ): Result {
//        val root = json?.asJsonObject
//        val type = root?.get("wrapperType")?.asString
//        when (type) {
//            "collection" -> return Album()
//            "track" -> return Track(T)
//            else -> return Album()
//        }
//    }
//}