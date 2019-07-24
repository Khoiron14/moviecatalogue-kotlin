package com.khoiron14.moviecatalogue.ui.tvshow

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khoiron14.moviecatalogue.*
import com.khoiron14.moviecatalogue.model.tvshow.Tvshow
import com.khoiron14.moviecatalogue.ui.tvshow.detail.TvshowDetailActivity
import kotlinx.android.synthetic.main.fragment_tvshow.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 *
 */
class TvshowFragment : Fragment() {

    private lateinit var adapter: TvshowAdapter
    private lateinit var viewModel: TvshowViewModel

    private val getTvshowList =
        Observer<List<Tvshow>> { tvshowList ->
            if (tvshowList != null) {
                adapter.setData(tvshowList)
                showLoading(false, progress_bar)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TvshowAdapter {
            startActivity<TvshowDetailActivity>(TvshowDetailActivity.EXTRA_TVSHOW to it.id)
        }
        rv_list_tvshow.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        if (connectionAvaiable(activity as Activity)) {
            connected()
        } else {
            disconnected()
        }
    }

    override fun onStop() {
        super.onStop()
        if (connectionAvaiable(activity as Activity)) {
            connected()
        } else {
            disconnected()
        }
    }

    private fun fetchData() {
        viewModel = ViewModelProviders.of(this).get(TvshowViewModel::class.java)
        viewModel.getTvshowList().observe(this, getTvshowList)
        viewModel.setTvshowList()
    }

    private fun connected() {
        rv_list_tvshow.visible()
        tv_no_connection.gone()
        fetchData()
        showLoading(true, progress_bar)
    }

    private fun disconnected() {
        rv_list_tvshow.gone()
        tv_no_connection.visible()
        showLoading(false, progress_bar)
    }
}
