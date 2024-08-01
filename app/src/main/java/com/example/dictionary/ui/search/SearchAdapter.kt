package com.example.dictionary.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.databinding.ItemRecyclerSearchBinding
import com.example.dictionary.db.DictionaryEntity
import javax.inject.Inject

class SearchAdapter @Inject constructor() : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var wordsList = emptyList<DictionaryEntity>()
    private var onItemClick: ((DictionaryEntity) -> Unit)? = null

    fun setData(data: List<DictionaryEntity>) {
        val diffUtil = DiffUtil.calculateDiff(WordsDiffUtils(wordsList, data))
        wordsList = data
        diffUtil.dispatchUpdatesTo(this)
    }

    fun onItemClickListener(listener: (DictionaryEntity) -> Unit) {
        onItemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemRecyclerSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount() = wordsList.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bindData(wordsList[position])
    }

    inner class SearchViewHolder(private val binding: ItemRecyclerSearchBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindData(item: DictionaryEntity) {
            binding.apply {
                nameTxt.text = item.englishWord
                root.setOnClickListener {
                    onItemClick?.invoke(item)
                }
            }
        }
    }

    private class WordsDiffUtils(private val oldItems: List<DictionaryEntity>, private val newItems: List<DictionaryEntity>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition] === newItems[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition] == newItems[newItemPosition]
        }
    }
}
