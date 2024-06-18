package com.example.dictionary.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.databinding.ItemRecyclerSearchBinding
import com.example.dictionary.db.DictionaryEntity
import com.example.dictionary.utils.showShortToast
import javax.inject.Inject

class SearchAdapter @Inject constructor() : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private lateinit var binding: ItemRecyclerSearchBinding

    private var wordsList = emptyList<DictionaryEntity>()

    class WordsDiffUtils(private val oldItems : List<DictionaryEntity> , private val newItems : List<DictionaryEntity>) : DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldItems.size
        }

        override fun getNewListSize(): Int {
            return newItems.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition] === newItems[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition] === newItems[newItemPosition]
        }

    }

    fun setData(data: List<DictionaryEntity>){
        val moviesDiffUtils = WordsDiffUtils(wordsList , data)
        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtils)
        wordsList = data
        diffUtils.dispatchUpdatesTo(this)
    }

    inner class SearchViewHolder : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun  bindData(item : DictionaryEntity){
            binding.apply {
                nameTxt.text = item.englishWord
                root.setOnLongClickListener {
                    root.context.showShortToast(item.persianWord)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        binding = ItemRecyclerSearchBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return SearchViewHolder()
    }

    override fun getItemCount() = wordsList.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bindData(wordsList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemViewType(position: Int) = position
}
