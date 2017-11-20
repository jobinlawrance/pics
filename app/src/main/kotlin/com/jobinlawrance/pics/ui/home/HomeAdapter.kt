package com.jobinlawrance.pics.ui.home

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.NO_POSITION
import android.util.Pair
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.jobinlawrance.pics.R
import com.jobinlawrance.pics.application.GlideApp
import com.jobinlawrance.pics.data.retrofit.model.PhotoResponse
import com.jobinlawrance.pics.ui.custom.SixteenNineImageView
import com.jobinlawrance.pics.utils.inflate
import kotlinx.android.synthetic.main.home_viewholder.view.*


/**
 * Created by jobinlawrance on 12/9/17.
 */
class HomeAdapter(context: Context, val onPhotoClick: (photo: PhotoResponse, sharedElementsPair: Pair<View, String>) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val NO_POSITION = -1
    private val VIEW_TYPE_LOADING = 1
    private val VIEW_TYPE_IMAGES = 2

    private var items: List<PhotoResponse> = ArrayList<PhotoResponse>()
    private var picLoadingPlaceholders: ArrayList<ColorDrawable>
    var isloadingNextPage = false
        private set

    init {
        val a = context.obtainStyledAttributes(R.styleable.PicsFeed)
        val loadingColorArrayId =
                a.getResourceId(R.styleable.PicsFeed_shotLoadingPlaceholderColors, 0)
        if (loadingColorArrayId != 0) {
            val placeholderColors = context.resources.getIntArray(loadingColorArrayId)
            picLoadingPlaceholders = ArrayList(placeholderColors.size)
            placeholderColors.forEach {
                picLoadingPlaceholders.add(ColorDrawable(it))
            }
        } else {
            picLoadingPlaceholders = arrayListOf(ColorDrawable(Color.DKGRAY))
        }
        a.recycle()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_IMAGES)
            return HomeViewHolder(parent.inflate(R.layout.home_viewholder))
        else
            return LoadingViewHolder(parent.inflate(R.layout.loadmore_viewholder))
    }

    override fun getItemCount(): Int = items.size + (if (isloadingNextPage) 1 else 0)

    override fun getItemViewType(position: Int): Int {
        if (isloadingNextPage && position == items.size)
            return VIEW_TYPE_LOADING
        else
            return VIEW_TYPE_IMAGES
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_IMAGES)
            (holder as HomeViewHolder).bind(items[position], position, picLoadingPlaceholders, onPhotoClick)
    }

    /**
     * @return true if value has changed since last invocation
     */
    fun setNextPageLoading(loadingNextPage: Boolean): Boolean {
        val hasLoadingMoreChanged = loadingNextPage != isloadingNextPage

        val notifyInserted = loadingNextPage && hasLoadingMoreChanged
        val notifyRemoved = !loadingNextPage && hasLoadingMoreChanged
        isloadingNextPage = loadingNextPage

        if (notifyInserted)
            notifyItemInserted(items.size)
        else if (notifyRemoved)
            notifyItemRemoved(items.size)

        return hasLoadingMoreChanged
    }

    fun setItems(newItems: List<PhotoResponse>) {
        val oldItems = this.items
        this.items = newItems

        if (oldItems.isEmpty()) {
            notifyDataSetChanged()
        } else {
            DiffUtil.calculateDiff(object : DiffUtil.Callback() {

                override fun getOldListSize(): Int = oldItems.size

                override fun getNewListSize(): Int = newItems.size

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                        oldItems[oldItemPosition].id == newItems[newItemPosition].id

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                        oldItems[oldItemPosition].equals(newItems[newItemPosition])

            }).dispatchUpdatesTo(this)
        }
    }

    class HomeViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val imageView: SixteenNineImageView

        init {
            imageView = item.imageView
        }

        fun bind(photoResponse: PhotoResponse,
                 position: Int, picLoadingPlaceholders: ArrayList<ColorDrawable>,
                 onPhotoClick: (photo: PhotoResponse, sharedElementsPair: Pair<View, String>) -> Unit) {

            GlideApp.with(itemView.context)
                    .load(photoResponse.urls?.regular)
                    .centerCrop()
                    .placeholder(picLoadingPlaceholders[position % picLoadingPlaceholders.size])
                    .transition(withCrossFade(250))
                    .into(imageView)

            // need both placeholder & background to prevent seeing through image as it fades in
            imageView.background = picLoadingPlaceholders[position % picLoadingPlaceholders.size]

            imageView.transitionName = photoResponse.id

            imageView.setOnClickListener {
                val pos = adapterPosition

                // since recyclerView is asynchronous there is a possibility the data has been deleted and before recyclerView could
                // refresh, user clicks on the view
                if (pos != NO_POSITION)
                    onPhotoClick.invoke(photoResponse, Pair.create(imageView, imageView.transitionName))
            }

        }
    }

    private class LoadingViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView)

}