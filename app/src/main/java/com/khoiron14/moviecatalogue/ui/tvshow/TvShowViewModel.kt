package com.khoiron14.moviecatalogue.ui.tvshow

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
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
class TvShowViewModel : ViewModel() {

    private val tvShowResponse = MutableLiveData<List<TvShow>>()

    fun setTvShowList() {
        val service = RetrofitFactory.service()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getTvShowList(currentLocale.toLanguageTag())
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let { tvShowResponse.postValue(it.results) }
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

    fun getTvShowList(): LiveData<List<TvShow>> = tvShowResponse
}