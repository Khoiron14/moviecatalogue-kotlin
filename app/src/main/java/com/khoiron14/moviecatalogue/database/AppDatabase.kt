package com.khoiron14.moviecatalogue.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.khoiron14.moviecatalogue.database.dao.MovieFavoriteDao
import com.khoiron14.moviecatalogue.database.dao.TvShowFavoriteDao
import com.khoiron14.moviecatalogue.model.favorite.MovieFavorite
import com.khoiron14.moviecatalogue.model.favorite.TvShowFavorite

/**
 * Created by khoiron14 on 9/4/2019.
 */
@Database(
    entities = [MovieFavorite::class, TvShowFavorite::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieFavoriteDao(): MovieFavoriteDao
    abstract fun tvShowFavoriteDao(): TvShowFavoriteDao

    companion object {
        private const val DATABASE_NAME = "movie_catalogue"
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context?): AppDatabase? {
            if (instance == null && context != null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return instance
        }
    }
}