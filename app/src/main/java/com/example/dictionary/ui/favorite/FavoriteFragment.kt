package com.example.dictionary.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dictionary.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
//    @Inject
//    lateinit var favoritesAdapter: FavoritesAdapter
//    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
//            viewModel.getAllFavoriteListMovies() // its look like call api
//            viewModel.allFavoriteListMoviesLiveData.observe(viewLifecycleOwner) {
//                favoritesAdapter.setData(it)
//                favoriteRecycler.initRecyclerView(LinearLayoutManager(requireContext()), favoritesAdapter)
//            }
//            viewModel.empty.observe(viewLifecycleOwner) {
//                if (it) {
//                    requireContext().showWidget(emptyItemsLay)
//                    requireContext().goneWidget(favoriteRecycler)
//                } else {
//                    requireContext().showWidget(favoriteRecycler)
//                    requireContext().goneWidget(emptyItemsLay)
//                }
//            }
            //onItemClick
//            favoritesAdapter.onItemClickListener {
//                val direction = FavoriteFragmentDirections.actionToDetailFragment(it.id) // FavoriteFragmentDirections origin
//                findNavController().navigate(direction)
//            }
        }
    }

}