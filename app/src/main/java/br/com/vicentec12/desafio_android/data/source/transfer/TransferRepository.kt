package br.com.vicentec12.desafio_android.data.source.transfer

import br.com.vicentec12.desafio_android.data.model.Transfer
import br.com.vicentec12.desafio_android.data.source.Remote
import br.com.vicentec12.desafio_android.data.source.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransferRepository @Inject constructor(
    @Remote private val mTransferLocalDataSource: TransferDataSource,
) : TransferDataSource {

    override suspend fun listTransfers(mLimit: String, mOffset: String) =
        mTransferLocalDataSource.listTransfers(mLimit, mOffset)

    override suspend fun getMyBalance() = mTransferLocalDataSource.getMyBalance()

    override suspend fun getTransferDetails(mId: String): Result<Transfer?> =
        mTransferLocalDataSource.getTransferDetails(mId)

}