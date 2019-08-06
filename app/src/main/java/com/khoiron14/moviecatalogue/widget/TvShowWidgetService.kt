package com.khoiron14.moviecatalogue.widget

import android.content.Intent
import android.widget.RemoteViewsService

/**
 * Created by khoiron14 on 8/7/2019.
 */
class TvShowWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(p0: Intent?): RemoteViewsFactory = TvShowRemoteViewsFactory(this.applicationContext)
}