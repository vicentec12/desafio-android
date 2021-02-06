package br.com.vicentec12.desafio_android.data.source.transfer

import br.com.vicentec12.desafio_android.data.source.Remote
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransferRepository @Inject constructor(
    @Remote private val mTransferLocalDataSource: TransferDataSource
) : TransferDataSource {

    override fun listTransfers(
        mLimit: String,
        mOffset: String,
        mCallback: TransferDataSource.OnListTransfersCallback
    ) {
        mTransferLocalDataSource.listTransfers(mLimit, mOffset, mCallback)
    }

    override fun getMyBalance(mCallback: TransferDataSource.OnGetBalanceCallback) {
        mTransferLocalDataSource.getMyBalance(mCallback)
    }

    override fun getTransferDetails(
        mId: String,
        mCallback: TransferDataSource.OnGetTransferDetails
    ) {
        mTransferLocalDataSource.getTransferDetails(mId, mCallback)
    }

}