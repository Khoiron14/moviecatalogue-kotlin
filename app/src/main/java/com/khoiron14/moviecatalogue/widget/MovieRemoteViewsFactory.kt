package com.khoiron14.moviecatalogue.widget

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.khoiron14.moviecatalogue.BuildConfig
import com.khoiron14.moviecatalogue.R
import com.khoiron14.moviecatalogue.database.DatabaseRepository
import com.khoiron14.moviecatalogue.database.RepositoryCallback
import com.khoiron14.moviecatalogue.model.favorite.MovieFavorite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by khoiron14 on 8/6/2019.
 */
class MovieRemoteViewsFactory(var context: Context) : RemoteViewsService.RemoteViewsFactory {
    private var movieList: List<MovieFavorite> = listOf()

    override fun onCreate() {}

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(p0: Int): Long = 0

    override fun onDataSetChanged() {
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseRepository().getMovieFavorites(object :
                RepositoryCallback<List<MovieFavorite>?> {
                override fun onDataSuccess(base: List<MovieFavorite>?) {
                    movieList = base!!
                }

                override fun onDataError(message: String?) {
                }
            })
        }
    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(p0: Int): RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.item_widget)

        try {
            val bitmap =
                Glide.with(context).asBitmap()
                    .load(BuildConfig.BASE_IMAGE_PATH_URL + movieList[p0].posterPath)
                    .submit().get()

            remoteViews.setImageViewBitmap(R.id.img_poster, bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val extras = Bundle()
        extras.putInt(MovieFavoriteWidget.EXTRA_ITEM, p0)
        val fillInIntent = Intent().putExtras(extras)

        remoteViews.setOnClickFillInIntent(R.id.img_poster, fillInIntent)
        return remoteViews
    }

    override fun getCount(): Int = movieList.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {}
}