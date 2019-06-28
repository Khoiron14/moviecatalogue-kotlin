package com.khoiron14.moviecatalogue.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import com.khoiron14.moviecatalogue.R
import com.khoiron14.moviecatalogue.model.Movie
import com.khoiron14.moviecatalogue.model.MovieData
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MainAdapter
    private lateinit var movies: ArrayList<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movies = MovieData().getListData()

        adapter = MainAdapter(this, movies)
        lv_list_movie.adapter = adapter
        lv_list_movie.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE, movies[position])
            startActivity(intent)
        }
    }
}
