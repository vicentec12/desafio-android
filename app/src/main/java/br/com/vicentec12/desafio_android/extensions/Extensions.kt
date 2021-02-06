package br.com.vicentec12.desafio_android.extensions

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun Double.toCurrency(): String {
    val formatter: NumberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
    return formatter.format(this)
}

fun String.getFormattedDate() =
    formatDate(this, "yyyy-MM-dd'T'HH:mm:ss'Z'", "dd/MM")

fun String.getFormattedDateTime() =
    formatDate(this, "yyyy-MM-dd'T'HH:mm:ss'Z'", "dd/MM/yyyy - HH:mm:ss")

fun formatDate(mDateString: String, mInputFormat: String, mOutputFormat: String): String {
    val mInputSimpleFormat = SimpleDateFormat(mInputFormat, Locale.getDefault())
    val mOutputSimpleFormat = SimpleDateFormat(mOutputFormat, Locale.getDefault())
    val mDate = mInputSimpleFormat.parse(mDateString)
    return mOutputSimpleFormat.format(mDate!!)
}