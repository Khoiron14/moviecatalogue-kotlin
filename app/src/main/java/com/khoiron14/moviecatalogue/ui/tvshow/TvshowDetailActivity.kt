package com.khoiron14.moviecatalogue.ui.tvshow

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.khoiron14.moviecatalogue.*
import com.khoiron14.moviecatalogue.model.Tvshow
import com.khoiron14.moviecatalogue.service.RetrofitFactory
import kotlinx.android.synthetic.main.activity_tvshow_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.toast
import retrofit2.HttpException

class TvshowDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TVSHOW = "extra_tvshow"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow_detail)

        loadTvshowDetail()

        supportActionBar?.title = ""
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun loadTvshowDetail() {
        val service = RetrofitFactory.service()
        CoroutineScope(Dispatchers.IO).launch {
            progress_bar.visible()
            val response = service.getTvshow(intent.getIntExtra(EXTRA_TVSHOW, 0))
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        val tvshow = response.body() as Tvshow
                        tv_name.text = tvshow.name
                        rating_bar.rating = tvshow.rating!!.toFloat() / 2
                        tv_first_air.text = convertDate(tvshow.firstAirDate)
                        tv_overview.text = tvshow.overview
                        if (tvshow.backdropPath != null) {
                            Glide.with(this@TvshowDetailActivity)
                                .load(BuildConfig.BASE_IMAGE_PATH_URL + tvshow.backdropPath)
                                .placeholder(R.drawable.ic_launcher_background)
                                .into(img_cover)
                        } else {
                            Glide.with(this@TvshowDetailActivity)
                                .load(BuildConfig.BASE_IMAGE_PATH_URL + tvshow.posterPath)
                                .placeholder(R.drawable.ic_launcher_background)
                                .into(img_cover)
                        }
                        Glide.with(this@TvshowDetailActivity)
                            .load(BuildConfig.BASE_IMAGE_PATH_URL + tvshow.posterPath)
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(img_poster)
                        tv_text_first_air.visible()
                        tv_text_overview.visible()
                        progress_bar.gone()
                    } else {
                        toast("Error ${response.code()}")
                        progress_bar.gone()
                    }
                } catch (e: HttpException) {
                    toast("Exception ${e.message()}")
                    progress_bar.gone()
                } catch (e: Throwable) {
                    toast("Oops something went wrong")
                    progress_bar.gone()
                }
            }
        }
    }
}
