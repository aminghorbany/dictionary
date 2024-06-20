package com.example.dictionary.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.dictionary.databinding.FragmentDialogDeleteBinding
import com.example.dictionary.db.DictionaryEntity
import com.example.dictionary.utils.showLongSnackBar
import com.example.dictionary.utils.showLongToast
import com.example.dictionary.viewmodel.SearchViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DeleteDialogFragment : DialogFragment(){

    private lateinit var binding: FragmentDialogDeleteBinding
    private val viewModel: SearchViewModel by activityViewModels()
    var onDismissListener: (() -> Unit)? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDialogDeleteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val entityData = arguments?.getParcelable<DictionaryEntity>(ARG_DELETE_DATA)
        binding.apply {
            entityData?.let { data ->
                btnYes.setOnClickListener {
                    viewModel.deleteWord(data.englishWord)
                    requireContext().showLongToast("با موفقیت حذف شد")
                    dismiss()
                    onDismissListener?.invoke()
                }
                btnNo.setOnClickListener {
                    dismiss()
                }
            }
        }
    }

    companion object {
        private const val ARG_DELETE_DATA = "delete_data"

        fun newInstance(data: DictionaryEntity): DeleteDialogFragment {
            val fragment = DeleteDialogFragment()
            val bundle = Bundle().apply {
                putParcelable(ARG_DELETE_DATA, data)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}