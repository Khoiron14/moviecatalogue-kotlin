package com.khoiron14.moviecatalogue.ui.favorite.movie


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.khoiron14.moviecatalogue.R
import com.khoiron14.moviecatalogue.database.DatabaseRepository
import com.khoiron14.moviecatalogue.database.RepositoryCallback
import com.khoiron14.moviecatalogue.gone
import com.khoiron14.moviecatalogue.model.favorite.MovieFavorite
import com.khoiron14.moviecatalogue.ui.detail.MovieDetailActivity
import com.khoiron14.moviecatalogue.visible
import kotlinx.android.synthetic.main.fragment_movie_favorite.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.support.v4.startActivity
import java.util.*

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
        CoroutineScope(Dispatchers.IO).launch {
            var movieList: List<MovieFavorite>? = emptyList()
            DatabaseRepository().getMovieFavorites(object :
                RepositoryCallback<List<MovieFavorite>?> {
                override fun onDataSuccess(base: List<MovieFavorite>?) {
                    movieList = filteredData(query, base)
                }

                override fun onDataError(message: String?) {
                    Log.e("DATA_ERROR", message!!)
                }
            })
            withContext(Dispatchers.Main) {
                movieList?.let {
                    if (it.isNotEmpty()) {
                        adapter.setData(it)
                        rv_list_movie.visible()
                        no_favorite.gone()
                    } else {
                        rv_list_movie.gone()
                        no_favorite.visible()
                    }
                }
            }
        }
    }

    private fun filteredData(
        query: String?,
        movieFavorites: List<MovieFavorite>?
    ): List<MovieFavorite>? {
        return if (query != null) {
            movieFavorites?.filter {
                it.title!!.toLowerCase(Locale.getDefault())
                    .contains(query.toLowerCase(Locale.getDefault()))
            }
        } else {
            movieFavorites
        }
    }
}
