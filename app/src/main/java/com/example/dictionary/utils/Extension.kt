package com.example.dictionary.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.android.material.snackbar.Snackbar

fun Context.showShortToast(txt : String){
    Toast.makeText(this, txt, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(txt : String){
    Toast.makeText(this, txt, Toast.LENGTH_LONG).show()
}

fun View.showShortSnackBar(txt : String){
    Snackbar.make(this.context , this , txt , Snackbar.LENGTH_SHORT ).show()
}

fun View.showLongSnackBar(txt : String){
    Snackbar.make(this.context , this , txt , Snackbar.LENGTH_LONG ).show()
}

fun Context.showWidget(view : View){
    view.visibility = View.VISIBLE
}

fun Context.hideWidget(view : View){
    view.visibility = View.INVISIBLE
}

fun Context.goneWidget(view : View){
    view.visibility = View.GONE
}

