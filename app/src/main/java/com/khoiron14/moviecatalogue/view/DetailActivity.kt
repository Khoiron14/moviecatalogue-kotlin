package com.khoiron14.moviecatalogue.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.khoiron14.moviecatalogue.R
import com.khoiron14.moviecatalogue.model.Movie
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        Glide.with(this).load(movie.poster).into(img_poster)
        tv_rating.text = movie.rating
        tv_title.text = movie.title
        tv_release.text = movie.releaseYear
        tv_overview.text = movie.overview

        supportActionBar?.title = "Movie Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
