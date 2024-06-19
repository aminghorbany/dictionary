package com.example.dictionary.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.databinding.FragmentSearchBinding
import com.example.dictionary.ui.dialog.TranslateDialogFragment
import com.example.dictionary.utils.goneWidget
import com.example.dictionary.utils.showWidget
import com.example.dictionary.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    @Inject lateinit var searchAdapter: SearchAdapter
    private val viewModel: SearchViewModel by viewModels()
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchEditText()
        observeViewModel()
        setupRecyclerView()
    }

    private fun setupSearchEditText() {
        binding.searchEdt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchJob?.cancel()
            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    searchJob = lifecycleScope.launch {
                        delay(1000)
                        if (it.toString().isNotEmpty()) {
                            viewModel.getFilteredWords(it.toString())
                        } else {
                            viewModel.clearDictionaryData()
                            searchAdapter.setData(emptyList())
                        }
                    }
                }
            }
        })
    }

    private fun observeViewModel() {
        viewModel.dictionaryEntityLiveData.observe(viewLifecycleOwner) {
            searchAdapter.setData(it)
        }

        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            if (loading) {
                requireContext().showWidget(binding.loading)
                requireContext().goneWidget(binding.wordsRecycler)
            } else {
                requireContext().showWidget(binding.wordsRecycler)
                requireContext().goneWidget(binding.loading)
            }
        }
    }

    private fun setupRecyclerView() {
        searchAdapter = SearchAdapter() // Initialize adapter only once

        binding.wordsRecycler.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        searchAdapter.onItemClickListener { dictionaryEntity ->
            val dialog = TranslateDialogFragment.newInstance(dictionaryEntity)
            dialog.show(childFragmentManager, TranslateDialogFragment().tag)
        }
    }
}
