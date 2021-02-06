package br.com.vicentec12.desafio_android.interfaces

import android.view.View

// Using MAD interface
fun interface OnItemClickListener {

    fun onItemClick(v: View, item: Any?, position: Int)

}