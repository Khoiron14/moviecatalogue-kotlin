package com.khoiron14.moviecatalogue.ui.detail

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.khoiron14.moviecatalogue.*
import com.khoiron14.moviecatalogue.database.DatabaseRepository
import com.khoiron14.moviecatalogue.database.RepositoryCallback
import com.khoiron14.moviecatalogue.model.favorite.TvShowFavorite
import com.khoiron14.moviecatalogue.model.tvshow.TvShow
import com.khoiron14.moviecatalogue.widget.TvShowFavoriteWidget
import kotlinx.android.synthetic.main.activity_tvshow_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.contentView
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
                tv_text_first_air.visible()
            }
            showLoading(false, progress_bar)
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
                }
                true
            }
            else -> super.onOptionsItemSelected(item!!)
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
        val tvShowFavorite = TvShowFavorite(null, mTvShow.id, mTvShow.name, mTvShow.posterPath)
        CoroutineScope(Dispatchers.IO).launch {
            var msg: String? = resources.getString(R.string.favorite_added)
            DatabaseRepository().addTvShowFavorite(
                tvShowFavorite,
                object : RepositoryCallback<TvShowFavorite?> {
                    override fun onDataSuccess(base: TvShowFavorite?) {
                        isFavorite = !isFavorite
                    }

                    override fun onDataError(message: String?) {
                        msg = message
                    }
                })
            withContext(Dispatchers.Main) {
                setFavorite()
                msg?.let { contentView?.snackbar(it) }
            }
        }
    }

    private fun removeFromFavorite() {
        CoroutineScope(Dispatchers.IO).launch {
            var msg: String? = resources.getString(R.string.favorite_removed)
            DatabaseRepository().removeTvShowFavorite(
                id,
                object : RepositoryCallback<Int?> {
                    override fun onDataSuccess(base: Int?) {
                        isFavorite = !isFavorite
                    }

                    override fun onDataError(message: String?) {
                        msg = message
                    }
                })
            withContext(Dispatchers.Main) {
                setFavorite()
                msg?.let { contentView?.snackbar(it) }
            }
        }

    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorite)
        else
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorite)
        updateWidget()
    }

    private fun favoriteState() {
        CoroutineScope(Dispatchers.IO).launch {
            var msg: String? = null
            DatabaseRepository().getTvShowFavorite(
                id,
                object : RepositoryCallback<TvShowFavorite?> {
                    override fun onDataSuccess(base: TvShowFavorite?) {
                        if (base != null) isFavorite = true
                    }

                    override fun onDataError(message: String?) {
                        msg = message
                    }
                })
            withContext(Dispatchers.Main) {
                msg?.let { contentView?.snackbar(it) }
            }
        }
    }

    private fun updateWidget() {
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val mWidget = ComponentName(this, TvShowFavoriteWidget::class.java)
        val ids = appWidgetManager.getAppWidgetIds(mWidget)
        appWidgetManager.notifyAppWidgetViewDataChanged(ids, R.id.stack_view)
    }
}
