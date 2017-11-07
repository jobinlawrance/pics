package com.jobinlawrance.pics.ui.widget

import android.content.Intent
import android.widget.RemoteViewsService

/**
 * Created by jobinlawrance on 10/10/17.
 */
class PicsRemoteViewService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory =
            PicsRemoteViewFactory(application, intent)
}