package com.jobinlawrance.pics.ui.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

import com.jobinlawrance.pics.R

/**
 * Implementation of App Widget functionality.
 */
class PicAppWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                     appWidgetId: Int) {

            val intent = Intent(context, PicsRemoteViewService::class.java)

            // Construct the RemoteViews object
            val remoteViews = RemoteViews(context.packageName, R.layout.pic_app_widget)

            remoteViews.setRemoteAdapter(R.id.adapter_flipper, intent)
            remoteViews.setEmptyView(R.id.adapter_flipper, R.id.empty_view)


            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
        }
    }
}

