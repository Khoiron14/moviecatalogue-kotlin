package com.khoiron14.moviecatalogue.ui.favorite.movie


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.view.*

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
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        val searchItem = menu?.findItem(R.id.search)

        if (searchItem != null) {
            val searchView: SearchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    showFavorite(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean = false
            })
            searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(p0: MenuItem?): Boolean = true

                override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                    showFavorite()
                    return true
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    private fun showFavorite(query: String? = null) {
        var movieList: List<MovieFavorite> = listOf()

        context?.database?.use {
            val result = select(MovieFavorite.TABLE_MOVIE_FAVORITE)
            movieList = result.parseList(classParser())
        }

        if (query != null) {
            movieList = movieList.filter {
                it.movieTitle!!.toLowerCase().contains(query.toLowerCase())
            }
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
