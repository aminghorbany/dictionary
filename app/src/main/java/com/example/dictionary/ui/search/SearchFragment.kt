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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentSearchBinding
import com.example.dictionary.ui.dialog.TranslateDialogFragment
import com.example.dictionary.utils.goneWidget
import com.example.dictionary.utils.showShortToast
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
        goToOnlineTranslate()
    }

    private fun goToOnlineTranslate() {
        binding.includeEmptyState.btnTranslate.setOnClickListener {
            val word = binding.searchEdt.text.toString()
            val direction = SearchFragmentDirections.actionSearchFragmentToTranslatorFragment(word)
            findNavController().navigate(direction)
        }
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
        binding.apply {
            viewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    requireContext().apply {
                        showWidget(loading)
                        goneWidget(wordsRecycler)
                    }
                } else {
                    requireContext().apply {
                        showWidget(wordsRecycler)
                        goneWidget(loading)
                    }
                }
            }

            viewModel.emptyState.observe(viewLifecycleOwner) {
                val currentChar = binding.searchEdt.text.toString()
                if (it && currentChar.isNotEmpty()) {
                    //show emptyState
                    requireContext().apply {
                        showWidget(emptyState)
                        goneWidget(wordsRecycler)
                    }
                } else {
                    //hide emptyState
                    requireContext().apply {
                        showWidget(wordsRecycler)
                        goneWidget(emptyState)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.wordsRecycler.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        searchAdapter.onItemClickListener { dictionaryEntity ->
            val dialog = TranslateDialogFragment.newInstance(dictionaryEntity)
            dialog.onDismissListener = {
                val currentString = binding.searchEdt.text.toString()
                viewModel.getFilteredWords(currentString)
            }
            dialog.show(childFragmentManager, TranslateDialogFragment().tag)
        }
    }
}
