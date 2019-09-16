package com.khoiron14.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khoiron14.moviecatalogue.currentLocale
import com.khoiron14.moviecatalogue.model.tvshow.TvShow
import com.khoiron14.moviecatalogue.service.RetrofitFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/**
 * Created by khoiron14 on 7/23/2019.
 */
class TvShowDetailViewModel : ViewModel() {

    private val tvShow = MutableLiveData<TvShow>()

    fun setTvShow(id: Int) {
        val service = RetrofitFactory.service()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getTvShow(id, currentLocale.toLanguageTag())
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let { tvShow.postValue(it) }
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

    fun getTvShow(): LiveData<TvShow> = tvShow
}