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
    val differ = AsyncListDiffer(this , object : DiffUtil.ItemCallback<DictionaryEntity>(){
        override fun areItemsTheSame(oldItem: DictionaryEntity, newItem: DictionaryEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DictionaryEntity, newItem: DictionaryEntity): Boolean {
            return oldItem == newItem
        }
    })

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

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bindData(differ.currentList[position])
        holder.setIsRecyclable(false) //for prevent duplicate item
    }

    override fun getItemViewType(position: Int) = position // for prevent confusing
}
