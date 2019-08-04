package com.khoiron14.moviecatalogue.ui.movie

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.khoiron14.moviecatalogue.currentLocale
import com.khoiron14.moviecatalogue.model.movie.Movie
import com.khoiron14.moviecatalogue.model.movie.MovieResponse
import com.khoiron14.moviecatalogue.service.RetrofitFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

/**
 * Created by khoiron14 on 7/23/2019.
 */
class MovieViewModel : ViewModel() {

    private val movieList = MutableLiveData<List<Movie>>()

    fun setMovieList(query: String? = null) {
        val service = RetrofitFactory.service()
        CoroutineScope(Dispatchers.IO).launch {

            val response = if (query == null) {
                service.getMovieList(currentLocale.toLanguageTag())
            } else {
                service.getMovieSearchList(currentLocale.toLanguageTag(), query)
            }

            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let { movieList.postValue(it.results) }
                    } else {
                        error("Error ${response.code()}")
                    }
                } catch (e: HttpException) {
                    error("Exception ${e.message()}")
                } catch (e: Throwable) {
                    error("Oops something went wrong")
                }
            }
        }
    }

    fun getMovieList(): LiveData<List<Movie>> = movieList
}