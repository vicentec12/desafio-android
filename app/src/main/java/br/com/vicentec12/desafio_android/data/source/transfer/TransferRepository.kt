package br.com.vicentec12.desafio_android.data.source.transfer

class TransferRepository private constructor(
    private val mTransferLocalDataSource: TransferDataSource
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

    companion object {

        private var INSTANCE: TransferRepository? = null

        @JvmStatic
        fun getInstance(mTransferLocalDataSource: TransferDataSource) =
            INSTANCE ?: synchronized(TransferRepository::class.java) {
                INSTANCE ?: TransferRepository(mTransferLocalDataSource).also { INSTANCE = it }
            }


        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }

    }

}