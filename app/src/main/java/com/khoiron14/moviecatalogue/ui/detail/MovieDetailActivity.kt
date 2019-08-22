package com.khoiron14.moviecatalogue.ui.detail

import android.appwidget.AppWidgetManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ComponentName
import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.khoiron14.moviecatalogue.*
import com.khoiron14.moviecatalogue.R.menu.detail_menu
import com.khoiron14.moviecatalogue.database.database
import com.khoiron14.moviecatalogue.model.favorite.MovieFavorite
import com.khoiron14.moviecatalogue.model.movie.Movie
import com.khoiron14.moviecatalogue.widget.MovieFavoriteWidget
import kotlinx.android.synthetic.main.activity_movie_detail.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.design.snackbar


class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var mMovie: Movie
    private var id: Int = 0
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    private val getMovie =
        Observer<Movie> { movie ->
            if (movie != null) {
                mMovie = movie
                loadMovieDetail(mMovie)
                showLoading(false, progress_bar)
                tv_text_release.visible()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        id = intent.getIntExtra(EXTRA_MOVIE, 0)

        supportActionBar?.title = ""
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.add_to_favorite -> {
                if (::mMovie.isInitialized) {
                    if (isFavorite) removeFromFavorite() else addToFavorite()
                    isFavorite = !isFavorite
                    setFavorite()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
        if (connectionAvailable(this)) {
            connected()
        } else {
            disconnected()
        }
    }

    private fun fetchData() {
        viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel::class.java)
        viewModel.getMovie().observe(this, getMovie)
        viewModel.setMovie(intent.getIntExtra(EXTRA_MOVIE, 0))
        favoriteState()
    }

    private fun loadMovieDetail(movie: Movie) {
        tv_title.text = movie.title
        rating_bar.rating = movie.rating!!.toFloat() / 2
        tv_release.text = convertDate(movie.releaseDate)
        tv_overview.text = movie.overview
        if (movie.backdropPath != null) {
            Glide.with(this@MovieDetailActivity)
                .load(BuildConfig.BASE_IMAGE_PATH_URL + movie.backdropPath)
                .placeholder(R.drawable.ic_launcher_background)
                .into(img_cover)
        } else {
            Glide.with(this@MovieDetailActivity)
                .load(BuildConfig.BASE_IMAGE_PATH_URL + movie.posterPath)
                .placeholder(R.drawable.ic_launcher_background)
                .into(img_cover)
        }
        Glide.with(this@MovieDetailActivity)
            .load(BuildConfig.BASE_IMAGE_PATH_URL + movie.posterPath)
            .placeholder(R.drawable.ic_launcher_background)
            .into(img_poster)
    }

    private fun connected() {
        fetchData()
        showLoading(true, progress_bar)
    }

    private fun disconnected() {
        tv_text_release.gone()
        showLoading(false, progress_bar)
        contentView?.longSnackbar(R.string.no_connection)
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    MovieFavorite.TABLE_MOVIE_FAVORITE,
                    MovieFavorite.MOVIE_ID to mMovie.id,
                    MovieFavorite.MOVIE_TITLE to mMovie.title,
                    MovieFavorite.MOVIE_POSTER_PATH to mMovie.posterPath
                )
            }
            contentView?.snackbar(getString(R.string.favorite_added))?.show()
        } catch (e: SQLiteConstraintException) {
            contentView?.snackbar(e.localizedMessage)?.show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(MovieFavorite.TABLE_MOVIE_FAVORITE, "(MOVIE_ID = {id})", "id" to id)
            }
            contentView?.snackbar(getString(R.string.favorite_removed))?.show()
        } catch (e: SQLiteConstraintException) {
            contentView?.snackbar(e.localizedMessage)?.show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorite)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorite)
        updateWidget()
    }

    private fun favoriteState() {
        database.use {
            val result = select(MovieFavorite.TABLE_MOVIE_FAVORITE)
                .whereArgs("(MOVIE_ID = {id})", "id" to id)
            val movie = result.parseList(classParser<MovieFavorite>())
            if (movie.isNotEmpty()) isFavorite = true
        }
    }

    private fun updateWidget() {
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val mWidget = ComponentName(this, MovieFavoriteWidget::class.java)
        val ids = appWidgetManager.getAppWidgetIds(mWidget)
        appWidgetManager.notifyAppWidgetViewDataChanged(ids, R.id.stack_view)
    }
}