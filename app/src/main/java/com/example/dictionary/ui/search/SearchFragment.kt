package com.example.topmovies.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topmovies.R
import com.example.topmovies.databinding.FragmentSearchBinding
import com.example.topmovies.ui.favorite.FavoriteFragmentDirections
import com.example.topmovies.ui.home.adapters.HomeLastMoviesAdapter
import com.example.topmovies.utils.goneWidget
import com.example.topmovies.utils.initRecyclerView
import com.example.topmovies.utils.showWidget
import com.example.topmovies.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    @Inject lateinit var homeLastMoviesAdapter : HomeLastMoviesAdapter
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
                    viewModel.getSearchMoviesList(it.toString())
                }
            }
            viewModel.getSearchMoviesListLiveData.observe(viewLifecycleOwner){
                homeLastMoviesAdapter.setData(it.data)
                moviesRecycler.initRecyclerView(LinearLayoutManager(requireContext() ,
                    RecyclerView.VERTICAL , false) , homeLastMoviesAdapter)
            }
            viewModel.searchLoading.observe(viewLifecycleOwner){
                if (it){
                    requireContext().goneWidget(moviesRecycler)
                    requireContext().showWidget(searchLoading)
                }else{
                    requireContext().goneWidget(searchLoading)
                    requireContext().showWidget(moviesRecycler)
                }
            }
            viewModel.empty.observe(viewLifecycleOwner){
                if (it){
                    requireContext().goneWidget(moviesRecycler)
                    requireContext().showWidget(emptyItemsLay)
                }else{
                    requireContext().goneWidget(emptyItemsLay)
                    requireContext().showWidget(moviesRecycler)
                }
            }
            //onItemClick
            homeLastMoviesAdapter.onItemClickListener {
                val direction = SearchFragmentDirections.actionToDetailFragment(it.id!!) // SearchFragmentDirections origin
                findNavController().navigate(direction)
            }
        }
    }
}