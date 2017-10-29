package com.jobinlawrance.pics.businesslogic.download

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.Environment
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import com.jobinlawrance.downloadprogressinterceptor.ProgressEventBus
import com.jobinlawrance.pics.PicToDownload
import com.jobinlawrance.pics.Queue
import com.jobinlawrance.pics.R
import com.jobinlawrance.pics.application.MyApplication
import com.jobinlawrance.pics.data.retrofit.services.PhotoService
import com.jobinlawrance.pics.utils.plusAssign
import com.jobinlawrance.pics.utils.random
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import okio.Okio
import retrofit2.Retrofit
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Inject

//TODO - network interruption error handling, runtime permission error handling
class DownloadServiceImpl @Inject constructor() : Service(), DownloadService {

    @Inject lateinit var progressEventBus: ProgressEventBus
    @Inject lateinit var retrofit: Retrofit

    private val binder = DownloadBinder()
    private val downloadQueue = Queue<PicToDownload>()
    private var isCurrentlyDownloading = false

    private val compositeDisposable = CompositeDisposable()

    var binderCounter: Int = 0
    var currentDownloadCounter = 0

    // The id of the channel.
    val channelId = "download_channel"
    // The user-visible name of the channel.
    val channelName: CharSequence = "Downloads"
    // The user-visible description of the channel.
    val description = "Notification about ongoing downloads"

    lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()
        (application as MyApplication).getAppComponent().inject(this)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        setUpNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("onStartCommand")

        compositeDisposable +=
                progressEventBus.observable()
                        .throttleLast(100, TimeUnit.MILLISECONDS)
                        .map { DownloadStatus(it.progress, currentDownloadCounter - downloadQueue.count(), currentDownloadCounter) }
                        .subscribe({
                            Timber.d("Notification progress - $it")
                            notifyDownloadProgress(it)
                        }, {
                            Timber.e(it, "Notification progress error")
                        })

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Timber.e("onDestroy")
        super.onDestroy()
        compositeDisposable.clear()
    }

    override fun onBind(intent: Intent): IBinder? {
        Timber.w("onBind() bindCounter - $binderCounter")
        binderCounter++
        return binder
    }

    /**
     * [onBind] onBind() method is called to retrieve the IBinder only when the first client binds.
     * The system then delivers the same IBinder to any additional clients that bind, without calling onBind() again.
     * ref - https://stackoverflow.com/a/11287912/6448823
     *
     * This also means the [onUnbind] method is called only when the first client unbinds.
     * To overcome this, we return true in [onUnbind] such that for subsequent binds and unbinds [onRebind] and [onUnbind] is called respectively
     */
    override fun onUnbind(intent: Intent?): Boolean {
        binderCounter--
        Timber.e("On Unbind() binderCounter - $binderCounter")
        if (!isCurrentlyDownloading) {
            Timber.d("Nothing is being downloaded")
            stopSelf()
        }
        return true
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        binderCounter++
        Timber.w("On Rebind() + binderCounter - $binderCounter")
    }

    override fun addToDownloadQueue(picToDownload: PicToDownload) {
        downloadQueue.add(picToDownload)
        currentDownloadCounter++

        if (!isCurrentlyDownloading)
            download()
    }

    override fun getDownloadProgress(identifier: String): Observable<Int> =
            progressEventBus.observable()
                    .filter { it.downloadIdentifier == identifier && it.percentIsAvailable }
                    .map { it.progress }

    fun download() {
        if (downloadQueue.isEmpty()) {
            currentDownloadCounter = 0
            if (binderCounter == 0) {
                Timber.w("Download Queue is empty and no components are bound")
                stopSelf()
            }
            return
        }

        isCurrentlyDownloading = true

        val picToDownload = downloadQueue.remove()

        compositeDisposable +=
                retrofit.create(PhotoService::class.java)
                        .downloadPhoto(picToDownload.url, picToDownload.identifier)
                        .subscribe(
                                {
                                    val fileName = "${picToDownload.fileName}.${it.contentType()?.subtype()}"

                                    Timber.d("Image Download Started -" + fileName)
                                    val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absoluteFile, fileName)
                                    val bufferedSink = Okio.buffer(Okio.sink(file))
                                    bufferedSink.write(it.source(), it.contentLength()).close()

                                    Timber.d("File Link - ${file.absolutePath}")
                                    notifyDownloadCompletion(DownloadedFile(picToDownload.fileName, file.absolutePath))
                                },
                                {
                                    Timber.e(it, "Error in downloading")
                                    isCurrentlyDownloading = false
                                    download()
                                },
                                {
                                    Timber.d("Download Complete")
                                    isCurrentlyDownloading = false
                                    download()
                                }
                        )

    }

    inner class DownloadBinder : Binder() {
        val service: DownloadServiceImpl
            get() = this@DownloadServiceImpl
    }


    private fun setUpNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_LOW
            val notificationChannel = NotificationChannel(channelId, channelName, importance)
            notificationChannel.description = description
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun notifyDownloadProgress(downloadStatus: DownloadStatus) {
        val notificationId = 867

        val notificationBuilder = NotificationCompat.Builder(applicationContext, channelId)
        notificationBuilder.color = Color.BLACK

        if (downloadStatus.progress == 100 && downloadStatus.currentDownloadNumber == downloadStatus.totalNumberOfDownloads) {
            stopForeground(true)
        } else {
            notificationBuilder.setSmallIcon(R.drawable.ic_file_download)
                    .setContentTitle("Downloading - ${downloadStatus.currentDownloadNumber} of ${downloadStatus.totalNumberOfDownloads}")
                    .setContentText("${downloadStatus.progress} %")
                    .setProgress(100, downloadStatus.progress, false)
            startForeground(notificationId, notificationBuilder.build())
        }
    }

    private fun notifyDownloadCompletion(downloadedFile: DownloadedFile) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.setDataAndType(Uri.parse("file://${downloadedFile.filepath}"), "image/*")
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val notificationBuilder = NotificationCompat.Builder(applicationContext, channelId)
        notificationBuilder.color = Color.BLACK

        notificationBuilder.setSmallIcon(R.drawable.ic_done_black_24dp)
                .setContentTitle(downloadedFile.name)
                .setContentText("Image Downloaded")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setCategory(Notification.CATEGORY_STATUS)

        notificationManager.notify((0..100).random(), notificationBuilder.build())
    }


    data class DownloadStatus(val progress: Int, val currentDownloadNumber: Int, val totalNumberOfDownloads: Int)
    data class DownloadedFile(val name: String, val filepath: String)
}
