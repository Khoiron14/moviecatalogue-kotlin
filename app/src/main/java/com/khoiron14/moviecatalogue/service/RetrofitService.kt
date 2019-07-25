package com.khoiron14.moviecatalogue.service

import com.khoiron14.moviecatalogue.BuildConfig
import com.khoiron14.moviecatalogue.model.movie.Movie
import com.khoiron14.moviecatalogue.model.movie.MovieResponse
import com.khoiron14.moviecatalogue.model.tvshow.Tvshow
import com.khoiron14.moviecatalogue.model.tvshow.TvshowResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by khoiron14 on 7/22/2019.
 */
interface RetrofitService {
    @GET("discover/movie?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovieList(@Query("language") language: String = "en-US"): Response<MovieResponse>

    @GET("discover/tv?api_key=${BuildConfig.API_KEY}")
    suspend fun getTvshowList(@Query("language") language: String = "en-US"): Response<TvshowResponse>

    @GET("movie/{id}?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovie(@Path("id") id: Int, @Query("language") language: String = "en-US"): Response<Movie>

    @GET("tv/{id}?api_key=${BuildConfig.API_KEY}")
    suspend fun getTvshow(@Path("id") id: Int, @Query("language") language: String = "en-US"): Response<Tvshow>
}