package com.example.dictionary.ui.info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dictionary.databinding.FragmentInfoBinding
import com.example.dictionary.utils.showLongSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : Fragment() {
    private lateinit var binding: FragmentInfoBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentInfoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            /*imageViewGithub.setOnClickListener {
                openGithub()
            }
            txtGithub.setOnClickListener {
                openGithub()
            }*/
            imageViewContact.setOnClickListener {
                openCallScreen()
            }
            txtPhoneNumber.setOnClickListener {
                openCallScreen()
            }
            imageViewEmail.setOnClickListener {
                openEmailApp()
            }
            txtEmail.setOnClickListener {
                openEmailApp()
            }
        }
    }

    private fun openGithub(){
        val url = "https://github.com/aminghorbany"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun openCallScreen(){
        val url = "+989037052133"
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel" , url , null))
        startActivity(intent)
    }

    private fun openEmailApp() {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "m.a.ghorbany73@gmail.com" , null
            )
        )
        startActivity(Intent.createChooser(emailIntent, ""))
    }
}