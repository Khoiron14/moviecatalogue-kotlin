package com.khoiron14.moviecatalogue.ui.movie.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.khoiron14.moviecatalogue.*
import com.khoiron14.moviecatalogue.model.movie.Movie
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    private lateinit var viewModel: MovieDetailViewModel

    private val getMovie =
        Observer<Movie> { movie ->
            if (movie != null) {
                loadMovieDetail(movie)
                showLoading(false)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel::class.java)
        viewModel.getMovie().observe(this, getMovie)
        viewModel.setMovie(intent.getIntExtra(EXTRA_MOVIE, 0))
        showLoading(true)

        supportActionBar?.title = ""
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun loadMovieDetail(movie: Movie) {
        tv_title.text = movie.title
        rating_bar.rating = movie.rating!!.toFloat() / 2
        tv_release.text = convertDate(movie.releaseDate)
        tv_overview.text = movie.overview
        if (movie.backdropPath != null) {
            Glide.with(this@MovieDetailActivity)
                .load(BuildConfig.BASE_IMAGE_PATH_URL + movie.backdropPath)
                .placeholder(R.drawable.ic_launcher_background)
                .into(img_cover)
        } else {
            Glide.with(this@MovieDetailActivity)
                .load(BuildConfig.BASE_IMAGE_PATH_URL + movie.posterPath)
                .placeholder(R.drawable.ic_launcher_background)
                .into(img_cover)
        }
        Glide.with(this@MovieDetailActivity)
            .load(BuildConfig.BASE_IMAGE_PATH_URL + movie.posterPath)
            .placeholder(R.drawable.ic_launcher_background)
            .into(img_poster)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progress_bar.visible()
        } else {
            progress_bar.gone()
            tv_text_release.visible()
            tv_text_overview.visible()
        }
    }
}
