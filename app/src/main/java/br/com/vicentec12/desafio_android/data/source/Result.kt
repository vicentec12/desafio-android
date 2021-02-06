package br.com.vicentec12.desafio_android.data.source

sealed class Result<out R> {
    data class Success<out T>(val mData: T, val mMessage: Int) : Result<T>()
    data class Error(val mMessage: Int) : Result<Nothing>()
}
