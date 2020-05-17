package ru.dengap.testomega.api

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.dengap.testomega.pojo.RootData

interface ItunesApiService {
    /**
     * Формируем запрос списка альбомов для главного экрана
     */
    @GET(GET_SEARCH)
    fun getAlbums(
        @Query(REQUIRED_TERM_PARAM) term: String,
        @Query(REQUIRED_COUNTRY_PARAM) country: String = "RU",
        @Query(MEDIA_PARAM) media: String = "music",
        @Query(ENTITY_PARAM) entity: String = "album"
    ): Observable<RootData>


    /**
     * Формируем запрос информации о выбранном альбоме по его [id]
     */
    @GET(GET_LOOKUP)
    fun getAlbumInfo(
        @Query(REQUIRED_COUNTRY_PARAM) country: String = "RU",
        @Query(ID_PARAM) id: Long,
        @Query(ENTITY_PARAM) entity: String = "song"
    ): Observable<RootData>

    companion object {
        private const val GET_SEARCH = "search"
        private const val GET_LOOKUP = "lookup"
        private const val MEDIA_PARAM = "media"
        private const val ENTITY_PARAM = "entity"
        private const val ID_PARAM = "id"

        private const val REQUIRED_TERM_PARAM = "term"
        private const val REQUIRED_COUNTRY_PARAM = "country"
    }

}