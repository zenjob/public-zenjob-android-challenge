package com.zenjob.android.browsr.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {
    @GET("movie/popular")
    fun getPopularTvShows(
        @Query("language") query: String? = null,
        @Query("page") page: Int? = null
    ): Single<PaginatedListResponse<Movie>>


    @GET("movie/{movie_id}")
    fun getDetails(
        @Path("movie_id") movieId: Long,
        @Query("language") query: String? = null
    ): Single<Movie>
}
