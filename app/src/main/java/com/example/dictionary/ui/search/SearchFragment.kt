package com.example.dictionary.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.databinding.FragmentSearchBinding
import com.example.dictionary.utils.goneWidget
import com.example.dictionary.utils.showWidget
import com.example.dictionary.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    @Inject lateinit var searchAdapter : SearchAdapter
    private val viewModel : SearchViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            searchEdt.addTextChangedListener {
                if (it.toString().isNotEmpty()){
                    viewModel.getFilteredWords(it.toString())
                }
            }

            //observe
            viewModel.dictionaryEntityLiveData.observe(viewLifecycleOwner){
                wordsRecycler.apply {
                    searchAdapter.differ.submitList(it)
                    adapter = searchAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                }
            }
            //loading
            viewModel.loading.observe(viewLifecycleOwner){
                if (it){
                    requireContext().apply {
                        showWidget(loading)
                        goneWidget(wordsRecycler)
                    }
                }else{
                    requireContext().apply {
                        showWidget(wordsRecycler)
                        goneWidget(loading)
                    }
                }
            }
        }
    }
}