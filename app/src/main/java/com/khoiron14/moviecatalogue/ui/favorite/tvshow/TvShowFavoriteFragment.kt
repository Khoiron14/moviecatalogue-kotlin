package com.khoiron14.moviecatalogue.ui.favorite.tvshow


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.view.*

import com.khoiron14.moviecatalogue.R
import com.khoiron14.moviecatalogue.database.database
import com.khoiron14.moviecatalogue.gone
import com.khoiron14.moviecatalogue.model.favorite.TvShowFavorite
import com.khoiron14.moviecatalogue.ui.detail.TvShowDetailActivity
import com.khoiron14.moviecatalogue.visible
import kotlinx.android.synthetic.main.fragment_tvshow_favorite.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.startActivity

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
        var tvShowList: List<TvShowFavorite> = listOf()

        context?.database?.use {
            val result = select(TvShowFavorite.TABLE_TVSHOW_FAVORITE)
            tvShowList = result.parseList(classParser())
            adapter.setData(tvShowList)
        }

        if (query != null) {
            tvShowList = tvShowList.filter {
                it.tvShowName!!.toLowerCase().contains(query.toLowerCase())
            }
        }

        if (tvShowList.isNotEmpty()) {
            adapter.setData(tvShowList)
            rv_list_tvshow.visible()
            no_favorite.gone()
        } else {
            rv_list_tvshow.gone()
            no_favorite.visible()
        }
    }
}
