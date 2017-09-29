package com.jobinlawrance.pics.ui.home

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.jobinlawrance.pics.R
import com.jobinlawrance.pics.application.GlideApp
import com.jobinlawrance.pics.data.retrofit.model.PhotoResponse
import com.jobinlawrance.pics.utils.inflate
import kotlinx.android.synthetic.main.home_viewholder.view.*


/**
 * Created by jobinlawrance on 12/9/17.
 */
class HomeAdapter(context: Context, val onPhotoClick: (photo: PhotoResponse) -> Unit) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private val NO_POSITION = -1

    private var items: List<PhotoResponse>? = null
    var shotLoadingPlaceholders: ArrayList<ColorDrawable>

    init {
        val a = context.obtainStyledAttributes(R.styleable.PicsFeed)
        val loadingColorArrayId =
                a.getResourceId(R.styleable.PicsFeed_shotLoadingPlaceholderColors, 0)
        if (loadingColorArrayId !== 0) {
            val placeholderColors = context.resources.getIntArray(loadingColorArrayId)
            shotLoadingPlaceholders = ArrayList(placeholderColors.size)
            placeholderColors.forEach {
                shotLoadingPlaceholders.add(ColorDrawable(it))
            }
        } else {
            shotLoadingPlaceholders = arrayListOf(ColorDrawable(Color.DKGRAY))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(parent.inflate(R.layout.home_viewholder))
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: HomeViewHolder?, position: Int) {
        holder?.bind(items!![position])
    }

    fun setItems(items: List<PhotoResponse>) {
        val oldItems = this.items
        this.items = items

        if (oldItems == null) {
            notifyDataSetChanged()
        } else {
            //TODO: use DiffUtils for pagination
        }
    }

    inner class HomeViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun bind(photoResponse: PhotoResponse) = with(itemView) {
            GlideApp.with(itemView.context)
                    .load(photoResponse.urls?.regular)
                    .centerCrop()
                    .placeholder(shotLoadingPlaceholders[position % shotLoadingPlaceholders.size])
                    .transition(withCrossFade(250))
                    .into(imageView)

            // need both placeholder & background to prevent seeing through shot as it fades in
            imageView.background = shotLoadingPlaceholders[position % shotLoadingPlaceholders.size]

            imageView.setOnClickListener {
                val position = adapterPosition

                // since recyclerView is asynchronous there is a possibility the data has been deleted and before recyclerView could
                // refresh, user clicks on the view
                if (position != NO_POSITION)
                    onPhotoClick.invoke(items!!.get(position))
            }

        }
    }
}