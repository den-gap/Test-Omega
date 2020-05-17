package ru.dengap.testomega.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Базовый ответ с сервера
 */
data class RootData(
    @SerializedName("resultCount")
    @Expose
    var resultCount: Int? = null,

    @SerializedName("results")
    @Expose
    var results: List<Result>? = null
)