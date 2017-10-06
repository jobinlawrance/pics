package com.jobinlawrance.pics.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jobinlawrance.pics.R
import com.jobinlawrance.pics.application.GlideApp
import com.jobinlawrance.pics.data.retrofit.model.PhotoResponse
import io.reactivex.Observable
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
     * Using [ReplaySubject] here is important since we are emitting the [photoResponse] in [onCreate]
     * The presenter is not yet created at this point, so it isn't subscribed to [loadDetailsSubject],
     * hence it'll miss out on [photoResponse] if we use [PublishSubject]
     */
    val loadDetailsSubject = ReplaySubject.create<PhotoResponse>()

    var isPhotoReponseLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            photoResponse = arguments.getParcelable(ARG_PARAM1)
            loadDetailsSubject.onNext(photoResponse)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_detail, container, false)
    }

    //TODO - use dagger
    override fun createPresenter(): DetailContract.Presenter = DetailPresenterImpl()

    override fun render(viewState: DetailViewState) {
        Timber.d("render - $viewState")
        if (!isPhotoReponseLoaded) {
            GlideApp.with(image_view.context)
                    .load(viewState.photoResponse?.urls?.regular)
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

            image_view.transitionName = viewState.photoResponse?.id

            GlideApp.with(user_avatar.context)
                    .load(viewState.photoResponse?.user?.profileImage?.medium)
                    .circleCrop()
                    .placeholder(R.drawable.avatar_placeholder)
                    .transition(withCrossFade())
                    .into(user_avatar)

            user_name.text = viewState.photoResponse?.user?.name

            isPhotoReponseLoaded = true
        }
    }

    override fun loadDetailsIntent(): Observable<PhotoResponse> = loadDetailsSubject

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
}
