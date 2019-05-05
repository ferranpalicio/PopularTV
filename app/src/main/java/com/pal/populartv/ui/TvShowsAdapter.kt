package com.pal.populartv.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pal.populartv.databinding.TvShowItemBinding
import com.pal.populartv.entity.TvShow


class TvShowsAdapter : RecyclerView.Adapter<TvShowsAdapter.ViewHolder>() {

    private var items: MutableList<TvShow> = mutableListOf()

    fun addItems(newItems : List<TvShow>) {
        val currentItems = itemCount
        this.items.addAll(newItems)
        notifyItemRangeInserted(currentItems, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TvShowItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(private val binding: TvShowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TvShow) {
            binding.tvShow = item
            binding.executePendingBindings()
        }
    }

}