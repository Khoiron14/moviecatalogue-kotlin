package com.khoiron14.moviecatalogue.ui.favorite.tvshow


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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
        return inflater.inflate(R.layout.fragment_tvshow_favorite, container, false)
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    private fun showFavorite() {
        var tvShowList: List<TvShowFavorite> = listOf()

        context?.database?.use {
            val result = select(TvShowFavorite.TABLE_TVSHOW_FAVORITE)
            tvShowList = result.parseList(classParser())
            adapter.setData(tvShowList)
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
