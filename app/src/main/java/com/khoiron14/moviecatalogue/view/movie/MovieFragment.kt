package com.khoiron14.moviecatalogue.view.movie

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khoiron14.moviecatalogue.R
import com.khoiron14.moviecatalogue.model.MovieData
import com.khoiron14.moviecatalogue.view.DetailActivity
import kotlinx.android.synthetic.main.fragment_movie.*

/**
 * A simple [Fragment] subclass.
 *
 */
class MovieFragment : Fragment() {

    companion object {
        const val SOURCE = "movie_fragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieAdapter = MovieAdapter(MovieData().getListData()) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_SOURCE, SOURCE)
            intent.putExtra(DetailActivity.EXTRA_MOVIE, it)
            startActivity(intent)
        }
        rv_list_movie.apply { adapter = movieAdapter }
    }
}
