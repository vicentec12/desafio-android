package br.com.vicentec12.desafio_android.data.source.transfer

import br.com.vicentec12.desafio_android.data.model.Transfer
import br.com.vicentec12.desafio_android.data.source.Result

interface TransferDataSource {

    suspend fun listTransfers(mLimit: String, mOffset: String): Result<MutableList<Transfer?>>

    suspend fun getMyBalance(): Result<String>

    suspend fun getTransferDetails(mId: String): Result<Transfer?>

}