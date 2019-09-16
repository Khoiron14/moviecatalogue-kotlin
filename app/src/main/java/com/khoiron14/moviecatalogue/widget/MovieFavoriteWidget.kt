package com.khoiron14.moviecatalogue.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import android.widget.Toast
import com.khoiron14.moviecatalogue.R


/**
 * Implementation of App Widget functionality.
 */
class MovieFavoriteWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        // There may be multiple widgets active, so update all of them

        for (appWidgetId in appWidgetIds!!) {
            updateAppWidget(context!!, appWidgetManager!!, appWidgetId)
        }
    }

    override fun onEnabled(context: Context?) {
        // Enter relevant functionality for when the first widget is created

    }

    override fun onDisabled(context: Context?) {
        // Enter relevant functionality for when the last widget is disabled

    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent?.action != null) {
            if (intent.action == TOAST_ACTION) {
                val viewIndex = intent.getIntExtra(EXTRA_ITEM, 0)
                Toast.makeText(context, "Touched view $viewIndex", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val TOAST_ACTION = "MOVIE_TOAST_ACTION"
        const val EXTRA_ITEM = "MOVIE_EXTRA_ITEM"

        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val intent = Intent(context, MovieWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))

            val views = RemoteViews(context.packageName, R.layout.movie_favorite_widget)
            views.setRemoteAdapter(R.id.stack_view, intent)
            views.setEmptyView(R.id.stack_view, R.id.tv_empty)

            val toastIntent = Intent(context, MovieFavoriteWidget::class.java)
            toastIntent.action = TOAST_ACTION
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            val toastPendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    0,
                    toastIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}