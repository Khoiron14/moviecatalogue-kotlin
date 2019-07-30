package com.khoiron14.moviecatalogue.ui.favorite.movie


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.khoiron14.moviecatalogue.R
import com.khoiron14.moviecatalogue.database.database
import com.khoiron14.moviecatalogue.gone
import com.khoiron14.moviecatalogue.model.favorite.MovieFavorite
import com.khoiron14.moviecatalogue.ui.detail.MovieDetailActivity
import com.khoiron14.moviecatalogue.visible
import kotlinx.android.synthetic.main.fragment_movie_favorite.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 *
 */
class MovieFavoriteFragment : Fragment() {

    private lateinit var adapter: MovieFavoriteAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = MovieFavoriteAdapter {
            startActivity<MovieDetailActivity>(MovieDetailActivity.EXTRA_MOVIE to it.movieId)
        }
        rv_list_movie.adapter = adapter

        showFavorite()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false)
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    private fun showFavorite() {
        var movieList: List<MovieFavorite> = listOf()

        context?.database?.use {
            val result = select(MovieFavorite.TABLE_MOVIE_FAVORITE)
            movieList = result.parseList(classParser())
        }

        if (movieList.isNotEmpty()) {
            adapter.setData(movieList)
            rv_list_movie.visible()
            no_favorite.gone()
        } else {
            rv_list_movie.gone()
            no_favorite.visible()
        }
    }
}
