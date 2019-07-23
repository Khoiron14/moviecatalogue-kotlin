package com.khoiron14.moviecatalogue.service

import com.khoiron14.moviecatalogue.BuildConfig
import com.khoiron14.moviecatalogue.model.Movie
import com.khoiron14.moviecatalogue.model.MovieResponse
import com.khoiron14.moviecatalogue.model.Tvshow
import com.khoiron14.moviecatalogue.model.TvshowResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by khoiron14 on 7/22/2019.
 */
interface RetrofitService {
    @GET("discover/movie?api_key=${BuildConfig.API_KEY}&language=en-US")
    suspend fun getMovieList(): Response<MovieResponse>

    @GET("discover/tv?api_key=${BuildConfig.API_KEY}&language=en-US")
    suspend fun getTvshowList(): Response<TvshowResponse>

    @GET("movie/{id}?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovie(@Path("id") id: Int): Response<Movie>

    @GET("tv/{id}?api_key=${BuildConfig.API_KEY}")
    suspend fun getTvshow(@Path("id") id: Int): Response<Tvshow>
}