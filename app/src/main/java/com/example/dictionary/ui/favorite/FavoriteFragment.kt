package com.example.dictionary.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentFavoriteBinding
import com.example.dictionary.ui.dialog.TranslateDialogFragment
import com.example.dictionary.ui.search.SearchAdapter
import com.example.dictionary.utils.goneWidget
import com.example.dictionary.utils.showWidget
import com.example.dictionary.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    @Inject lateinit var favoritesAdapter: SearchAdapter
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            observeViewModel()
            setupRecyclerView()
            navigateToSearch()
        }
    }

    private fun navigateToSearch() {
        binding.includeFavoriteEmptyState.btnGoToSearch.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavoriteWords()
    }

    private fun setupRecyclerView() {
        binding.favoriteRecycler.apply {
            adapter = favoritesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        favoritesAdapter.onItemClickListener { dictionaryEntity ->
            val dialog = TranslateDialogFragment.newInstance(dictionaryEntity).apply {
                onDismissListener = {
                    viewModel.getFavoriteWords()
                }
            }
            dialog.show(childFragmentManager, TranslateDialogFragment().tag)
        }
    }

    private fun observeViewModel() {
        viewModel.FavoriteWordsLiveData.observe(viewLifecycleOwner) {
            binding.apply {
                if (it.isEmpty()){
                    requireContext().apply {
                        goneWidget(favoriteRecycler)
                        showWidget(favoriteEmptyState)
                    }
                }else{
                    requireContext().apply {
                        goneWidget(favoriteEmptyState)
                        showWidget(favoriteRecycler)
                    }
                    favoritesAdapter.setData(it)
                }
            }
        }
    }
}