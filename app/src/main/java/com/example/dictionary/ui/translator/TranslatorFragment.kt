package com.example.dictionary.ui.translator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.dictionary.databinding.FragmentTranslatorBinding
import com.example.dictionary.utils.Constants
import com.example.dictionary.viewmodel.TranslatorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TranslatorFragment : Fragment() {
    private lateinit var binding: FragmentTranslatorBinding
    private val args : TranslatorFragmentArgs by navArgs()
    private val viewModel : TranslatorViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTranslatorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            txtEmail.text = args.engWord
            btnRes.setOnClickListener {
                viewModel.getTranslateWord(Constants.API_TOKEN , Constants.GOOGLE , Constants.LANGUAGE_FA , args.engWord)
            }

            viewModel.translateWordLiveData.observe(viewLifecycleOwner){
                txtResponse.text = it.result.toString()
            }
        }
    }
}