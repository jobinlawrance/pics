package com.jobinlawrance.pics.ui.widget

import android.app.Application
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.jobinlawrance.pics.R
import com.jobinlawrance.pics.application.GlideApp
import com.jobinlawrance.pics.application.MyApplication
import com.jobinlawrance.pics.data.retrofit.model.PhotoResponse
import com.jobinlawrance.pics.data.retrofit.services.PhotoService
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by jobinlawrance on 10/10/17.
 */
class PicsRemoteViewFactory(val application: Application, val intent: Intent) : RemoteViewsService.RemoteViewsFactory {


    @Inject lateinit var retrofit: Retrofit

    val compositeDisposables = CompositeDisposable()
    private var photoResponseItems: List<PhotoResponse> = emptyList<PhotoResponse>()

    /**
     * In [onCreate] you setup any connections / cursors to your data source.
     * Heavy lifting, for example downloading or creating content etc, should be deferred to [onDataSetChanged] or [getViewAt]
     * Taking more than 20 seconds in this call will result in an ANR.
     */
    override fun onCreate() {
        (application as MyApplication).getAppComponent().inject(this)
        Timber.d("On create()")
    }

    /**
     * In [onDestroy] you should tear down anything that was setup for your data source, eg. cursors, connections, etc.
     */
    override fun onDestroy() {
        compositeDisposables.clear()
    }


    /**
     *  You can create a custom loading view (for instance when [getViewAt] is slow.) If you
     *  return null here, you will get the default loading view.
     */
    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = position.toLong()

    /**
     * This is triggered when you call AppWidgetManager notifyAppWidgetViewDataChanged
     * on the collection view corresponding to this factory. You can do heaving lifting in here, synchronously.
     * For example, if you need to process an image, fetch something from the network, etc., it is ok to do it here, synchronously.
     * The widget will remain in its current state while work is being done here, so you don't need to worry about locking up the widget.
     */
    override fun onDataSetChanged() {

        retrofit.create(PhotoService::class.java)
                .getPhotos()
                .blockingSubscribe(
                        {
                            Timber.d("Successfully retrieved ")
                            photoResponseItems = it
                        },
                        {
                            Timber.e(it, "Error")
                        })

        Timber.d("Block worked")
    }

    override fun hasStableIds(): Boolean = true

    override fun getViewAt(position: Int): RemoteViews {

        val remoteView = RemoteViews(application.applicationContext.packageName, R.layout.pic_app_widget_flipper)
        val imagebitmap =
                GlideApp.with(application.applicationContext)
                        .asBitmap()
                        .load(photoResponseItems[position].urls!!.full)
                        .centerCrop()
                        .submit(300, 300)
                        .get()
        val userThumbImage =
                GlideApp.with(application.applicationContext)
                        .asBitmap()
                        .centerCrop()
                        .circleCrop()
                        .load(photoResponseItems[position].user!!.profileImage!!.small)
                        .submit(200, 200)
                        .get()

        remoteView.setImageViewBitmap(R.id.widget_item_image, imagebitmap)
        remoteView.setImageViewBitmap(R.id.widget_item_user, userThumbImage)


        return remoteView
    }

    override fun getCount(): Int = photoResponseItems.size

    override fun getViewTypeCount(): Int = 1


}