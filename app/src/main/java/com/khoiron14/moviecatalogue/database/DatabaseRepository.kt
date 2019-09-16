package com.khoiron14.moviecatalogue.database

import com.khoiron14.moviecatalogue.MovieCatalogueApplication
import com.khoiron14.moviecatalogue.model.favorite.MovieFavorite
import com.khoiron14.moviecatalogue.model.favorite.TvShowFavorite

/**
 * Created by khoiron14 on 9/4/2019.
 */
class DatabaseRepository {

    private fun getDatabase(): AppDatabase? {
        return AppDatabase.getInstance(MovieCatalogueApplication.applicationContext())
    }

    suspend fun getMovieFavorites(callback: RepositoryCallback<List<MovieFavorite>?>) {
        try {
            val movieFavorites = getDatabase()?.movieFavoriteDao()?.getMovieFavorites()
            callback.onDataSuccess(movieFavorites)
        } catch (e: Exception) {
            callback.onDataError(e.message)
        }
    }

    suspend fun getMovieFavorite(movieId: Int?, callback: RepositoryCallback<MovieFavorite?>) {
        try {
            val movieFavorite = getDatabase()?.movieFavoriteDao()?.getMovieFavorite(movieId)
            callback.onDataSuccess(movieFavorite)
        } catch (e: Exception) {
            callback.onDataError(e.message)
        }
    }

    suspend fun addMovieFavorite(
        movieFavorite: MovieFavorite,
        callback: RepositoryCallback<MovieFavorite?>
    ) {
        try {
            getDatabase()?.movieFavoriteDao()?.insertMovieFavorite(movieFavorite)
            callback.onDataSuccess(movieFavorite)
        } catch (e: Exception) {
            callback.onDataError(e.message)
        }
    }

    suspend fun removeMovieFavorite(movieId: Int?, callback: RepositoryCallback<Int?>) {
        try {
            getDatabase()?.movieFavoriteDao()?.deleteMovieFavorite(movieId)
            callback.onDataSuccess(movieId)
        } catch (e: Exception) {
            callback.onDataError(e.message)
        }
    }

    suspend fun getTvShowFavorites(callback: RepositoryCallback<List<TvShowFavorite>?>) {
        try {
            val tvShowFavorites = getDatabase()?.tvShowFavoriteDao()?.getTvShowFavorites()
            callback.onDataSuccess(tvShowFavorites)
        } catch (e: Exception) {
            callback.onDataError(e.message)
        }
    }

    suspend fun getTvShowFavorite(tvShowId: Int?, callback: RepositoryCallback<TvShowFavorite?>) {
        try {
            val tvShowFavorite = getDatabase()?.tvShowFavoriteDao()?.getTvShowFavorite(tvShowId)
            callback.onDataSuccess(tvShowFavorite)
        } catch (e: Exception) {
            callback.onDataError(e.message)
        }
    }

    suspend fun addTvShowFavorite(
        tvShowFavorite: TvShowFavorite?,
        callback: RepositoryCallback<TvShowFavorite?>
    ) {
        try {
            getDatabase()?.tvShowFavoriteDao()?.insertTvShowFavorite(tvShowFavorite)
            callback.onDataSuccess(tvShowFavorite)
        } catch (e: Exception) {
            callback.onDataError(e.message)
        }
    }

    suspend fun removeTvShowFavorite(tvShowId: Int?, callback: RepositoryCallback<Int?>) {
        try {
            getDatabase()?.tvShowFavoriteDao()?.deleteTvShowFavorite(tvShowId)
            callback.onDataSuccess(tvShowId)
        } catch (e: Exception) {
            callback.onDataError(e.message)
        }
    }
}