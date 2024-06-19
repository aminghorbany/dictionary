package com.example.dictionary.ui.dialog

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.activityViewModels
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentDialogTranslateBinding
import com.example.dictionary.db.DictionaryEntity
import com.example.dictionary.utils.showShortSnackBar
import com.example.dictionary.utils.showShortToast
import com.example.dictionary.viewmodel.SearchViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TranslateDialogFragment : BottomSheetDialogFragment(){

    private lateinit var binding: FragmentDialogTranslateBinding
    private val viewModel: SearchViewModel by activityViewModels()
    var onDismissListener: (() -> Unit)? = null
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
                imgFavorite.setImageResource(if (it.isFavorite) R.drawable.ic_heart_fill_red_24 else R.drawable.ic_heart_empty_red_24)
                imgFavorite.setOnClickListener { _ ->
                    it.isFavorite = !it.isFavorite
                    viewModel.updateFavorite(it)
                    if (it.isFavorite){
                        imgFavorite.setImageResource(R.drawable.ic_heart_fill_red_24)
                        requireContext().showShortToast(getString(R.string.addToFavorite))
                    }else{
                        imgFavorite.setImageResource(R.drawable.ic_heart_empty_red_24)
                        requireContext().showShortToast(getString(R.string.removeFromFavorite))
                    }
                }
            }
        }

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.invoke()
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