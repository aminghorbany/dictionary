package com.example.dictionary.ui.dialog

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import com.example.dictionary.databinding.FragmentDialogTranslateBinding
import com.example.dictionary.db.DictionaryEntity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TranslateDialogFragment : BottomSheetDialogFragment(){

    private lateinit var binding: FragmentDialogTranslateBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDialogTranslateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val translationData = arguments?.getParcelable<DictionaryEntity>(ARG_TRANSLATION_DATA)
        binding.apply {
            translationData?.let {
                txtEnglish.text = it.englishWord
                txtPersian.text = it.persianWord
                visibleViewWithAnimation(txtPersian)
            }
        }

    }

    companion object {
        private const val ARG_TRANSLATION_DATA = "translation_data"

        fun newInstance(data: DictionaryEntity): TranslateDialogFragment {
            val fragment = TranslateDialogFragment()
            val bundle = Bundle().apply {
                putParcelable(ARG_TRANSLATION_DATA, data)
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