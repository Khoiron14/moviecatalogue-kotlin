package com.khoiron14.moviecatalogue.ui.detail

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
import org.jetbrains.anko.contentView
import org.jetbrains.anko.design.longSnackbar

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    private lateinit var viewModel: MovieDetailViewModel

    private val getMovie =
        Observer<Movie> { movie ->
            if (movie != null) {
                loadMovieDetail(movie)
                showLoading(false, progress_bar)
                tv_text_release.visible()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        supportActionBar?.title = ""
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onStart() {
        super.onStart()
        if (connectionAvaiable(this)) {
            connected()
        } else {
            disconnected()
        }
    }

    override fun onStop() {
        super.onStop()
        if (connectionAvaiable(this)) {
            connected()
        } else {
            disconnected()
        }
    }

    private fun fetchData() {
        viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel::class.java)
        viewModel.getMovie().observe(this, getMovie)
        viewModel.setMovie(intent.getIntExtra(EXTRA_MOVIE, 0))
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

    private fun connected() {
        fetchData()
        showLoading(true, progress_bar)
    }

    private fun disconnected() {
        tv_text_release.gone()
        showLoading(false, progress_bar)
        contentView?.longSnackbar(R.string.no_connection)
    }
}
