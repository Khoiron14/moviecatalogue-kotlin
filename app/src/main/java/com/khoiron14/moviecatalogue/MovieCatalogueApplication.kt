package com.khoiron14.moviecatalogue

import android.app.Application
import android.content.Context

/**
 * Created by khoiron14 on 9/4/2019.
 */
class MovieCatalogueApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private lateinit var instance: MovieCatalogueApplication

        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }
}