package com.khoiron14.moviecatalogue.ui.movie

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khoiron14.moviecatalogue.*
import com.khoiron14.moviecatalogue.model.movie.Movie
import com.khoiron14.moviecatalogue.ui.detail.MovieDetailActivity
import kotlinx.android.synthetic.main.fragment_movie.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 *
 */
class MovieFragment : Fragment() {

    private lateinit var adapter: MovieAdapter
    private lateinit var viewModel: MovieViewModel

    private val getMovieList =
        Observer<List<Movie>> { movieList ->
            if (movieList != null) {
                adapter.setData(movieList)
                showLoading(false, progress_bar)
            }
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

        adapter = MovieAdapter {
            startActivity<MovieDetailActivity>(MovieDetailActivity.EXTRA_MOVIE to it.id)
        }
        rv_list_movie.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        if (connectionAvaiable(activity as Activity)) {
            connected()
        } else {
            disconnected()
        }
    }

    private fun fetchData() {
        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        viewModel.getMovieList().observe(this, getMovieList)
        viewModel.setMovieList()
    }

    private fun connected() {
        rv_list_movie.visible()
        tv_no_connection.gone()
        fetchData()
        showLoading(true, progress_bar)
    }

    private fun disconnected() {
        rv_list_movie.gone()
        tv_no_connection.visible()
        showLoading(false, progress_bar)
    }
}
