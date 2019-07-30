package com.khoiron14.moviecatalogue.ui.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
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
import com.khoiron14.moviecatalogue.database.database
import com.khoiron14.moviecatalogue.model.favorite.TvShowFavorite
import com.khoiron14.moviecatalogue.model.tvshow.TvShow
import kotlinx.android.synthetic.main.activity_tvshow_detail.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.design.snackbar

class TvShowDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TVSHOW = "extra_tvshow"
    }

    private lateinit var viewModel: TvShowDetailViewModel
    private lateinit var mTvShow: TvShow
    private var id: Int = 0
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    private val getTvShow =
        Observer<TvShow> { tvShow ->
            if (tvShow != null) {
                mTvShow = tvShow
                loadTvShowDetail(tvShow)
                showLoading(false, progress_bar)
                tv_text_first_air.visible()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow_detail)

        id = intent.getIntExtra(EXTRA_TVSHOW, 0)

        supportActionBar?.title = ""
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.add_to_favorite -> {
                if (::mTvShow.isInitialized) {
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
        if (connectionAvaiable(this)) {
            connected()
        } else {
            disconnected()
        }
    }

    private fun fetchData() {
        viewModel = ViewModelProviders.of(this).get(TvShowDetailViewModel::class.java)
        viewModel.getTvShow().observe(this, getTvShow)
        viewModel.setTvShow(intent.getIntExtra(EXTRA_TVSHOW, 0))
        favoriteState()
    }

    private fun loadTvShowDetail(tvShow: TvShow) {
        tv_name.text = tvShow.name
        rating_bar.rating = tvShow.rating!!.toFloat() / 2
        tv_first_air.text = convertDate(tvShow.firstAirDate)
        tv_overview.text = tvShow.overview
        if (tvShow.backdropPath != null) {
            Glide.with(this@TvShowDetailActivity)
                .load(BuildConfig.BASE_IMAGE_PATH_URL + tvShow.backdropPath)
                .placeholder(R.drawable.ic_launcher_background)
                .into(img_cover)
        } else {
            Glide.with(this@TvShowDetailActivity)
                .load(BuildConfig.BASE_IMAGE_PATH_URL + tvShow.posterPath)
                .placeholder(R.drawable.ic_launcher_background)
                .into(img_cover)
        }
        Glide.with(this@TvShowDetailActivity)
            .load(BuildConfig.BASE_IMAGE_PATH_URL + tvShow.posterPath)
            .placeholder(R.drawable.ic_launcher_background)
            .into(img_poster)
    }

    private fun connected() {
        fetchData()
        showLoading(true, progress_bar)
    }

    private fun disconnected() {
        tv_text_first_air.gone()
        showLoading(false, progress_bar)
        contentView?.longSnackbar(R.string.no_connection)
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    TvShowFavorite.TABLE_TVSHOW_FAVORITE,
                    TvShowFavorite.TVSHOW_ID to mTvShow.id,
                    TvShowFavorite.TVSHOW_NAME to mTvShow.name,
                    TvShowFavorite.TVSHOW_POSTER_PATH to mTvShow.posterPath
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
                delete(TvShowFavorite.TABLE_TVSHOW_FAVORITE, "(TVSHOW_ID = {id})", "id" to id)
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
    }

    private fun favoriteState() {
        database.use {
            val result = select(TvShowFavorite.TABLE_TVSHOW_FAVORITE)
                .whereArgs("(TVSHOW_ID = {id})", "id" to id)
            val tvShowFav = result.parseList(classParser<TvShowFavorite>())
            if (tvShowFav.isNotEmpty()) isFavorite = true
        }
    }
}
