package com.khoiron14.moviecatalogue.ui.tvshow.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.khoiron14.moviecatalogue.*
import com.khoiron14.moviecatalogue.model.tvshow.Tvshow
import kotlinx.android.synthetic.main.activity_tvshow_detail.*

class TvshowDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TVSHOW = "extra_tvshow"
    }

    private lateinit var viewModel: TvshowDetailViewModel

    private val getTvshow =
        Observer<Tvshow> { tvshow ->
            if (tvshow != null) {
                loadTvshowDetail(tvshow)
                showLoading(false)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow_detail)

        viewModel = ViewModelProviders.of(this).get(TvshowDetailViewModel::class.java)
        viewModel.getTvshow().observe(this, getTvshow)
        viewModel.setTvshow(intent.getIntExtra(EXTRA_TVSHOW, 0))
        showLoading(true)

        supportActionBar?.title = ""
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun loadTvshowDetail(tvshow: Tvshow) {
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
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progress_bar.visible()
        } else {
            progress_bar.gone()
            tv_text_first_air.visible()
            tv_text_overview.visible()
        }
    }
}
