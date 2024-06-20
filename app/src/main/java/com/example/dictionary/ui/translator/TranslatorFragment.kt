package com.example.dictionary.ui.translator

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.dictionary.databinding.FragmentTranslatorBinding
import com.example.dictionary.utils.Constants
import com.example.dictionary.utils.showShortToast
import com.example.dictionary.viewmodel.TranslatorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TranslatorFragment : Fragment() {
    private lateinit var binding: FragmentTranslatorBinding
    private val args : TranslatorFragmentArgs by navArgs()
    private val viewModel : TranslatorViewModel by viewModels()
    private lateinit var enginList: ArrayList<String>
    private var service = ""
    private var serviceID = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTranslatorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            enginListItem()
            txtEnglish.text = args.engWord
            btnTranslateWord.setOnClickListener {
                viewModel.getTranslateWord(Constants.API_TOKEN , Constants.GOOGLE , Constants.LANGUAGE_FA , args.engWord)
            }

            viewModel.translateWordLiveData.observe(viewLifecycleOwner){
                requireContext().showShortToast(it.result.toString())
            }
        }
    }

    private fun enginListItem() {
        val google = getString(com.example.dictionary.R.string.google)
        val microsoft = getString(com.example.dictionary.R.string.microsoft)
        enginList = arrayListOf(google , microsoft)
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, enginList)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.apply {
            enginSpinner.adapter = adapter
            enginSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    service = enginList[position]
                    serviceID = id.toInt()
                    when(serviceID){
                        0 -> {
                            imgTranslatorEngin.load(com.example.dictionary.R.drawable.google_translate){
                                crossfade(true)
                                crossfade(300)
                            }
                        }
                        1 -> {
                            imgTranslatorEngin.load(com.example.dictionary.R.drawable.microsoft_translate){
                                crossfade(true)
                                crossfade(300)
                            }
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
    }
}