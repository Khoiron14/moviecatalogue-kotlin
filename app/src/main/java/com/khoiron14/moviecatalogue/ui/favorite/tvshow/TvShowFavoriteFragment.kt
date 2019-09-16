package com.khoiron14.moviecatalogue.ui.favorite.tvshow


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.khoiron14.moviecatalogue.R
import com.khoiron14.moviecatalogue.database.DatabaseRepository
import com.khoiron14.moviecatalogue.database.RepositoryCallback
import com.khoiron14.moviecatalogue.gone
import com.khoiron14.moviecatalogue.model.favorite.TvShowFavorite
import com.khoiron14.moviecatalogue.ui.detail.TvShowDetailActivity
import com.khoiron14.moviecatalogue.visible
import kotlinx.android.synthetic.main.fragment_tvshow_favorite.*
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
class TvShowFavoriteFragment : Fragment() {

    private lateinit var adapter: TvShowFavoriteAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = TvShowFavoriteAdapter {
            startActivity<TvShowDetailActivity>(TvShowDetailActivity.EXTRA_TVSHOW to it.tvShowId)
        }
        rv_list_tvshow.adapter = adapter

        showFavorite()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_tvshow_favorite, container, false)
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
            var tvShowList: List<TvShowFavorite>? = emptyList()
            DatabaseRepository().getTvShowFavorites(object :
                RepositoryCallback<List<TvShowFavorite>?> {
                override fun onDataSuccess(base: List<TvShowFavorite>?) {
                    tvShowList = filteredData(query, base)
                }

                override fun onDataError(message: String?) {
                    Log.e("DATA_ERROR", message!!)
                }
            })
            withContext(Dispatchers.Main) {
                tvShowList?.let {
                    if (it.isNotEmpty()) {
                        adapter.setData(it)
                        rv_list_tvshow.visible()
                        no_favorite.gone()
                    } else {
                        rv_list_tvshow.gone()
                        no_favorite.visible()
                    }
                }
            }
        }
    }

    private fun filteredData(
        query: String?,
        tvShowFavorites: List<TvShowFavorite>?
    ): List<TvShowFavorite>? {
        return if (query != null) {
            tvShowFavorites?.filter {
                it.name!!.toLowerCase(Locale.getDefault())
                    .contains(query.toLowerCase(Locale.getDefault()))
            }
        } else {
            tvShowFavorites
        }
    }
}
