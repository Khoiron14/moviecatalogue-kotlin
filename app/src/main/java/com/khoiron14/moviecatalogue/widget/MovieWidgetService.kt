package com.khoiron14.moviecatalogue.widget

import android.content.Intent
import android.widget.RemoteViewsService

/**
 * Created by khoiron14 on 8/6/2019.
 */
class MovieWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(p0: Intent?): RemoteViewsFactory = MovieRemoteViewsFactory(this.applicationContext)
}