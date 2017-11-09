package com.jobinlawrance.pics.ui.detail

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jakewharton.rxbinding2.view.RxView
import com.jobinlawrance.pics.R
import com.jobinlawrance.pics.application.GlideApp
import com.jobinlawrance.pics.businesslogic.download.DownloadInteractor
import com.jobinlawrance.pics.data.retrofit.model.PhotoResponse
import com.jobinlawrance.pics.utils.plusAssign
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import kotlinx.android.synthetic.main.fragment_detail.*
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : MviFragment<DetailContract.View, DetailContract.Presenter>(), DetailContract.View {

    lateinit var photoResponse: PhotoResponse

    /**
     * Using [ReplaySubject] or [BehaviorSubject] here is important since we are emitting the [photoResponse] in [onCreate]
     * The presenter is not yet created at this point, so it isn't subscribed to [loadDetailsSubject],
     * hence it'll miss out on [photoResponse] if we use [PublishSubject]
     */
    val loadDetailsSubject = ReplaySubject.create<PhotoResponse>()
    val downloadStatusIntentSubject = BehaviorSubject.create<String>()

    val downloadPicIntentSubject: PublishSubject<Boolean> = PublishSubject.create<Boolean>()

    var isPhotoReponseLoaded = false

    lateinit var permissionSubject: PublishSubject<Permission>
    var disposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            photoResponse = arguments.getParcelable(ARG_PARAM1)

            loadDetailsSubject.onNext(photoResponse)
            downloadStatusIntentSubject.onNext(photoResponse.id!!)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onStart() {
        super.onStart()
        disposable +=
                RxView.clicks(download_button)
                        .flatMap { checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, rationaleDialogObservable()) }
                        .doOnNext {
                            if (it.revoked)
                                disposable +=
                                        settingObservable().subscribe()
                        }
                        .subscribe({
                            if (it.granted)
                                downloadPicIntentSubject.onNext(true)
                        }, {
                            Timber.e(it, "Permission Error")
                        })

    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    //TODO - use dagger
    override fun createPresenter(): DetailContract.Presenter = DetailPresenterImpl(DownloadInteractor(activity))

    override fun downloadPic(): Observable<Boolean> = downloadPicIntentSubject

    override fun getDownloadStatus(): Observable<String> = downloadStatusIntentSubject

    override fun render(viewState: DetailViewState) {
        Timber.d("render - $viewState")

        if (!isPhotoReponseLoaded && viewState.isDownloading.not() && viewState.photoResponse != null) {
            GlideApp.with(image_view.context)
                    .load(viewState.photoResponse.urls?.regular)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            activity.startPostponedEnterTransition()
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            activity.startPostponedEnterTransition()
                            return false
                        }
                    })
                    .dontAnimate()
                    .into(image_view)

            image_view.transitionName = viewState.photoResponse.id

            GlideApp.with(user_avatar.context)
                    .load(viewState.photoResponse.user?.profileImage?.medium)
                    .circleCrop()
                    .placeholder(R.drawable.avatar_placeholder)
                    .transition(withCrossFade())
                    .into(user_avatar)

            user_name.text = viewState.photoResponse.user?.name

            isPhotoReponseLoaded = true
        }

        if (viewState.isDownloading) {
            download_progress.text = String.format("%d %", viewState.downloadProgress)
        }
    }

    override fun loadDetailsIntent(): Observable<PhotoResponse> = loadDetailsSubject

    private fun checkPermission(permission: String, rationaleDialogObservable: Observable<Boolean>? = null): Observable<Permission> {
        if (shouldShowRequestPermissionRationale(permission)) {
            if (rationaleDialogObservable != null) {
                return rationaleDialogObservable.flatMap {
                    if (it)
                        requestPermissionSubject(permission)
                    else
                        Observable.just(Permission(false, true, false))
                }
            } else {
                return requestPermissionSubject(permission)
            }
        } else
            return requestPermissionSubject(permission)
    }

    private fun requestPermissionSubject(permission: String): Observable<Permission> {
        permissionSubject = PublishSubject.create()
        requestPermissions(arrayOf(permission), 2)
        Timber.d("Requesting permission")
        return permissionSubject
    }

    private fun settingObservable() =
            Observable.create<Boolean> { emitter ->
                val alertDialog =
                        AlertDialog.Builder(activity)
                                .setTitle(getString(R.string.permission_title))
                                .setMessage(getString(R.string.permission_settings_message))
                                .setPositiveButton(android.R.string.ok, { _, _ ->
                                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                    val uri = Uri.fromParts("package", activity.packageName, null)
                                    intent.data = uri
                                    startActivity(intent)
                                    emitter.onComplete()
                                })
                                .setNegativeButton(android.R.string.cancel, { _, _ ->
                                    emitter.onNext(false)
                                    emitter.onComplete()
                                })
                                .setCancelable(false)
                                .create()
                alertDialog.show()

                emitter.setCancellable {
                    Timber.e("Dialog is closed")
                    alertDialog.cancel()
                }
            }

    private fun rationaleDialogObservable(): Observable<Boolean> =
            Observable.create<Boolean> { emitter ->
                val alertDialog =
                        AlertDialog.Builder(activity)
                                .setTitle(getString(R.string.permission_title))
                                .setMessage(getString(R.string.permission_rationale_message))
                                .setPositiveButton(android.R.string.ok, { _, _ ->
                                    emitter.onNext(true)
                                    emitter.onComplete()
                                })
                                .setNegativeButton(android.R.string.cancel, { _, _ ->
                                    emitter.onNext(false)
                                    emitter.onComplete()
                                })
                                .setCancelable(false)
                                .create()
                alertDialog.show()

                emitter.setCancellable {
                    Timber.e("Dialog is closed")
                    alertDialog.cancel()
                }
            }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Timber.d("Permission granted")
            permissionSubject.onNext(Permission(true, false, false))
        } else {
            //check if permission is revoked
            if (shouldShowRequestPermissionRationale(permissions[0]))
                permissionSubject.onNext(Permission(false, true, false))
            else
                permissionSubject.onNext(Permission(false, false, true))
        }

    }

    companion object {
        private val ARG_PARAM1 = "param1"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param photoResponse Parameter.
         * @return A new instance of fragment DetailFragment.
         */

        fun newInstance(photoResponse: PhotoResponse): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putParcelable(ARG_PARAM1, photoResponse)
            fragment.arguments = args
            return fragment
        }
    }

    data class Permission(
            val granted: Boolean,
            val denied: Boolean,
            val revoked: Boolean
    )
}
