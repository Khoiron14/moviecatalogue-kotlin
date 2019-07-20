package com.khoiron14.moviecatalogue.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.khoiron14.moviecatalogue.R
import com.khoiron14.moviecatalogue.model.Movie
import com.khoiron14.moviecatalogue.model.Tvshow
import com.khoiron14.moviecatalogue.view.movie.MovieFragment
import com.khoiron14.moviecatalogue.view.tvshow.TvshowFragment
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SOURCE = "extra_source"
        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        when (intent.getStringExtra(EXTRA_SOURCE)) {
            MovieFragment.SOURCE -> loadMovieDetail()
            TvshowFragment.SOURCE -> loadTvshowDetail()
        }

        supportActionBar?.title = ""
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun loadMovieDetail() {
        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        Glide.with(this).load(movie.poster).into(img_cover)
        Glide.with(this).load(movie.poster).into(img_poster)
        tv_title.text = movie.title
        tv_rating.text = movie.rating
        tv_release.text = movie.releaseYear
        tv_overview.text = movie.overview
    }

    private fun loadTvshowDetail() {
        val tvshow = intent.getParcelableExtra<Tvshow>(EXTRA_MOVIE)
        Glide.with(this).load(tvshow.poster).into(img_cover)
        Glide.with(this).load(tvshow.poster).into(img_poster)
        tv_title.text = tvshow.title
        tv_rating.text = tvshow.rating
        tv_release.text = tvshow.releaseYear
        tv_overview.text = tvshow.overview
    }
}
