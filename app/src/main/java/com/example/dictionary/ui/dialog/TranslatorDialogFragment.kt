package com.example.dictionary.ui.dialog

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentDialogTranslateBinding
import com.example.dictionary.databinding.FragmentDialogTranslatorBinding
import com.example.dictionary.db.DictionaryEntity
import com.example.dictionary.utils.showShortSnackBar
import com.example.dictionary.utils.showShortToast
import com.example.dictionary.viewmodel.SearchViewModel
import com.example.dictionary.viewmodel.TranslatorViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TranslatorDialogFragment : BottomSheetDialogFragment(){

    private lateinit var binding: FragmentDialogTranslatorBinding
    private val viewModel: TranslatorViewModel by activityViewModels()
    private var isFavorite = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDialogTranslatorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val englishWord = arguments?.getString(ENG_WORD)
        val persianWord = arguments?.getString(PERSIAN_WORD)
        binding.apply {
            txtEnglish.text = englishWord.toString()
            txtPersian.text = persianWord.toString()
            visibleViewWithAnimation(txtPersian)
            btnCancel.setOnClickListener {
                dismiss()
            }
            imgFavorite.setOnClickListener {
                isFavorite = !isFavorite
                if (isFavorite){
                    imgFavorite.setImageResource(R.drawable.ic_heart_fill_red_24)
                }else{
                    imgFavorite.setImageResource(R.drawable.ic_heart_empty_red_24)
                }
            }
            btnAddToLocal.setOnClickListener {
                viewModel.insertWord(DictionaryEntity(englishWord = englishWord.toString() , persianWord = persianWord.toString() , isFavorite = isFavorite))
                requireContext().showShortToast(getString(R.string.successfullyInserted))
                dismiss()
            }
        }
    }

    companion object {
        private const val ENG_WORD = "eng_word"
        private const val PERSIAN_WORD = "persian_word"

        fun newInstance(engWord: String , persianWord : String): TranslatorDialogFragment {
            val fragment = TranslatorDialogFragment()
            val bundle = Bundle().apply {
                putString(ENG_WORD, engWord)
                putString(PERSIAN_WORD, persianWord)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    private fun visibleViewWithAnimation(view: View) {
        view.visibility = View.VISIBLE
        val scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 0f, 1f)
        val scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0f, 1f)
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY)
        animatorSet.duration = 1000
        animatorSet.interpolator = AccelerateDecelerateInterpolator()
        animatorSet.start()
    }
}