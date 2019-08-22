package com.khoiron14.moviecatalogue.ui.tvshow

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.view.*
import com.khoiron14.moviecatalogue.*
import com.khoiron14.moviecatalogue.model.tvshow.TvShow
import com.khoiron14.moviecatalogue.ui.detail.TvShowDetailActivity
import kotlinx.android.synthetic.main.fragment_tvshow.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 *
 */
class TvShowFragment : Fragment() {

    private lateinit var adapter: TvShowAdapter
    private lateinit var viewModel: TvShowViewModel

    private val getTvShowList =
        Observer<List<TvShow>> { tvShowList ->
            if (tvShowList != null) {
                adapter.setData(tvShowList)
                showLoading(false, progress_bar)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_tvshow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TvShowAdapter {
            startActivity<TvShowDetailActivity>(TvShowDetailActivity.EXTRA_TVSHOW to it.id)
        }
        rv_list_tvshow.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        val searchItem = menu?.findItem(R.id.search)

        if (searchItem != null) {
            val searchView: SearchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    fetchData(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean = false
            })
            searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(p0: MenuItem?): Boolean = true

                override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                    fetchData()
                    return true
                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        if (connectionAvailable(context!!)) {
            connected()
        } else {
            disconnected()
        }
    }

    override fun onStop() {
        super.onStop()
        if (connectionAvailable(context!!)) {
            connected()
        } else {
            disconnected()
        }
    }

    private fun fetchData(query: String? = null) {
        viewModel = ViewModelProviders.of(this).get(TvShowViewModel::class.java)
        viewModel.getTvShowList().observe(this, getTvShowList)
        viewModel.setTvShowList(query)
        showLoading(true, progress_bar)
    }

    private fun connected() {
        rv_list_tvshow.visible()
        tv_no_connection.gone()
        fetchData()
    }

    private fun disconnected() {
        rv_list_tvshow.gone()
        tv_no_connection.visible()
        showLoading(false, progress_bar)
    }
}
