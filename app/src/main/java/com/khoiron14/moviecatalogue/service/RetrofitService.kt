package com.khoiron14.moviecatalogue.service

import com.khoiron14.moviecatalogue.BuildConfig
import com.khoiron14.moviecatalogue.model.movie.Movie
import com.khoiron14.moviecatalogue.model.movie.MovieResponse
import com.khoiron14.moviecatalogue.model.tvshow.TvShow
import com.khoiron14.moviecatalogue.model.tvshow.TvShowResponse
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
    suspend fun getTvShowList(@Query("language") language: String = "en-US"): Response<TvShowResponse>

    @GET("movie/{id}?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovie(@Path("id") id: Int, @Query("language") language: String = "en-US"): Response<Movie>

    @GET("tv/{id}?api_key=${BuildConfig.API_KEY}")
    suspend fun getTvShow(@Path("id") id: Int, @Query("language") language: String = "en-US"): Response<TvShow>

    @GET("search/movie?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovieSearchList(@Query("language") language: String = "en-US", @Query("query") query: String): Response<MovieResponse>

    @GET("search/tv?api_key=${BuildConfig.API_KEY}")
    suspend fun getTvShowSearchList(@Query("language") language: String = "en-US", @Query("query") query: String): Response<TvShowResponse>
}