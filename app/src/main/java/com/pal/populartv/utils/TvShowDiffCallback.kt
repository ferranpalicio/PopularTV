package com.pal.populartv.utils

import androidx.recyclerview.widget.DiffUtil
import com.pal.populartv.domain.entity.TvShow


class TvShowDiffCallback(
    private val oldList: List<TvShow>,
    private val newList: List<TvShow>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].name == newList[newItemPosition].name
}