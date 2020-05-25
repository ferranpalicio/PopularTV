package com.pal.populartv.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pal.populartv.R
import com.pal.populartv.domain.entity.TvShow
import com.pal.populartv.utils.TvShowDiffCallback
import com.squareup.picasso.Picasso


class TvShowsAdapter : RecyclerView.Adapter<TvShowsAdapter.ViewHolder>() {

    private var items: MutableList<TvShow> = mutableListOf()

    fun addItems(newItems: List<TvShow>) {
        val diffCallback =
            TvShowDiffCallback(this.items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.items.clear()
        this.items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.tv_show_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: TvShow) {
            val image = itemView.findViewById<ImageView>(R.id.image_background)
            val context = itemView.context
            Picasso.with(context).load(item.image).into(image)
            itemView.findViewById<TextView>(R.id.tvshow_name).text = item.name
            itemView.findViewById<TextView>(R.id.tvshow_score).text =
                String.format(context.getString(R.string.tv_show_rating), item.score)

        }
    }

}