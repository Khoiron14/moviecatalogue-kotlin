package com.khoiron14.moviecatalogue.ui.movie

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.khoiron14.moviecatalogue.*
import com.khoiron14.moviecatalogue.model.Movie
import com.khoiron14.moviecatalogue.service.RetrofitFactory
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.toast
import retrofit2.HttpException

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        loadMovieDetail()

        supportActionBar?.title = ""
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun loadMovieDetail() {
        val service = RetrofitFactory.service()
        CoroutineScope(Dispatchers.IO).launch {
            progress_bar.visible()
            val response = service.getMovie(intent.getIntExtra(EXTRA_MOVIE, 0))
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        val movie = response.body() as Movie
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
                        tv_text_release.visible()
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
