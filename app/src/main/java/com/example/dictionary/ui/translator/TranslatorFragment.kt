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
import com.example.dictionary.ui.dialog.TranslateDialogFragment
import com.example.dictionary.ui.dialog.TranslatorDialogFragment
import com.example.dictionary.utils.Constants
import com.example.dictionary.utils.goneWidget
import com.example.dictionary.utils.showShortToast
import com.example.dictionary.utils.showWidget
import com.example.dictionary.viewmodel.TranslatorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TranslatorFragment : Fragment() {
    private lateinit var binding: FragmentTranslatorBinding
    private val args: TranslatorFragmentArgs by navArgs()
    private val viewModel: TranslatorViewModel by viewModels()
    private lateinit var enginList: ArrayList<String>
    private var enginID = 0
    private var translateWord = ""

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
                when (enginID) {
                    0 -> viewModel.getTranslateWord(Constants.API_TOKEN, Constants.GOOGLE, Constants.LANGUAGE_FA, args.engWord)
                    1 -> viewModel.getTranslateWord(Constants.API_TOKEN, Constants.MICROSOFT, Constants.LANGUAGE_FA, args.engWord)
                }
            }

            viewModel.translateWordLiveData.observe(viewLifecycleOwner) {
                translateWord = it.result.toString()
            }

            viewModel.isSuccessful.observe(viewLifecycleOwner) {
                val dialog = TranslatorDialogFragment.newInstance(args.engWord , translateWord)
                dialog.onDismissListener = {
//                    val currentString = binding.searchEdt.text.toString()
//                    viewModel.getFilteredWords(currentString)
                }
                dialog.show(childFragmentManager, TranslatorDialogFragment().tag)
            }
            //observe loading
            viewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    requireContext().apply {
                        showWidget(loading)
                        goneWidget(txtBlueBtn)
                    }
                } else {
                    requireContext().apply {
                        showWidget(txtBlueBtn)
                        goneWidget(loading)
                    }
                }
            }
        }
    }

    private fun enginListItem() {
        val google = getString(com.example.dictionary.R.string.google)
        val microsoft = getString(com.example.dictionary.R.string.microsoft)
        enginList = arrayListOf(google, microsoft)
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, enginList)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.apply {
            enginSpinner.adapter = adapter
            enginSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    enginID = id.toInt()
                    when (enginID) {
                        0 -> {
                            imgTranslatorEngin.load(com.example.dictionary.R.drawable.google_translate) {
                                crossfade(true)
                                crossfade(300)
                            }
                        }
                        1 -> {
                            imgTranslatorEngin.load(com.example.dictionary.R.drawable.microsoft_translate) {
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