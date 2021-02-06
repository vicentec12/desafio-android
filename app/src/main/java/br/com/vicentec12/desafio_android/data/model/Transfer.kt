package br.com.vicentec12.desafio_android.data.model

import androidx.recyclerview.widget.DiffUtil
import br.com.vicentec12.desafio_android.extensions.getFormattedDate
import br.com.vicentec12.desafio_android.extensions.getFormattedDateTime
import br.com.vicentec12.desafio_android.extensions.toCurrency

data class Transfer(
    val id: String = "",
    val description: String = "",
    val to: String = "",
    val from: String = "",
    val bankName: String = "",
    val createdAt: String = "",
    val authentication: String = "",
    val tType: String = "",
    val amount: Double = 0.0
) {

    val receiver: String
        get() = if (this.to.isEmpty()) this.from else this.to

    val amountCurrency: String
        get() = this.amount.toCurrency()

    val amountSignalCurrency: String
        get() = if (this.isOutTransfer) "-${this.amount.toCurrency()}" else this.amount.toCurrency()

    val formattedDate: String
        get() = this.createdAt.getFormattedDate()

    val formattedDateTime: String
        get() = this.createdAt.getFormattedDateTime()

    val isOutTransfer: Boolean
        get() = this.tType.contains("OUT")

    val isPixTransfer: Boolean
        get() = this.tType.contains("PIX")

    companion object {

        fun getDiffItemCallback() = object : DiffUtil.ItemCallback<Transfer?>() {
            override fun areItemsTheSame(oldItem: Transfer, newItem: Transfer) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Transfer, newItem: Transfer) =
                oldItem == newItem
        }

    }
}