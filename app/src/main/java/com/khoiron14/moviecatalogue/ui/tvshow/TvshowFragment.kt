package com.khoiron14.moviecatalogue.ui.tvshow

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khoiron14.moviecatalogue.R
import com.khoiron14.moviecatalogue.gone
import com.khoiron14.moviecatalogue.service.RetrofitFactory
import com.khoiron14.moviecatalogue.visible
import kotlinx.android.synthetic.main.fragment_tvshow.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.HttpException

/**
 * A simple [Fragment] subclass.
 *
 */
class TvshowFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvshowAdapter = TvshowAdapter(listOf()) {
            startActivity<TvshowDetailActivity>(TvshowDetailActivity.EXTRA_TVSHOW to it.id)
        }
        rv_list_tvshow.apply { adapter = tvshowAdapter }

        val service = RetrofitFactory.service()
        CoroutineScope(Dispatchers.IO).launch {
            progress_bar.visible()
            val response = service.getTvshowList()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        tvshowAdapter.tvshows = response.body()?.results
                        tvshowAdapter.notifyDataSetChanged()
                        progress_bar.gone()
                    } else {
                        toast("Error ${response.code()}")
                        progress_bar.gone()
                    }
                } catch (e: HttpException) {
                    toast("Exception ${e.message()}")
                    progress_bar.gone()
                } catch (e: Throwable) {
                    toast("Oops something went wrong")
                    progress_bar.gone()
                }
            }
        }
    }
}
