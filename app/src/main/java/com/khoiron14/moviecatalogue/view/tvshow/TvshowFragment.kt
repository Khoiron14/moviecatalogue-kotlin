package com.khoiron14.moviecatalogue.view.tvshow

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khoiron14.moviecatalogue.R
import com.khoiron14.moviecatalogue.model.TvshowData
import com.khoiron14.moviecatalogue.view.DetailActivity
import kotlinx.android.synthetic.main.fragment_tvshow.*

/**
 * A simple [Fragment] subclass.
 *
 */
class TvshowFragment : Fragment() {

    companion object {
        const val SOURCE = "tvshow_fragment"
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

        val tvshowAdapter = TvshowAdapter(TvshowData().getListData()) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_SOURCE, SOURCE)
            intent.putExtra(DetailActivity.EXTRA_MOVIE, it)
            startActivity(intent)
        }
        rv_list_tvshow.apply { adapter = tvshowAdapter }
    }
}
