package com.jobinlawrance.pics.home

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jobinlawrance.pics.R
import com.jobinlawrance.pics.retrofit.data.PhotoResponse
import com.jobinlawrance.pics.utils.inflate
import kotlinx.android.synthetic.main.home_viewholder.view.*

/**
 * Created by jobinlawrance on 12/9/17.
 */
class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var items: List<PhotoResponse>? = null

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

    class HomeViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun bind(photoResponse: PhotoResponse) = with(itemView) {
            Glide.with(itemView.context)
                    .load(photoResponse.urls?.regular)
                    .apply(RequestOptions.centerCropTransform())
                    .into(imageView)
        }
    }
}