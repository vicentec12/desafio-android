package br.com.vicentec12.desafio_android.data.source.transfer

import br.com.vicentec12.desafio_android.data.model.Transfer

interface TransferDataSource {

    interface OnListTransfersCallback {

        fun onSuccess(mMessage: Int, mTransfers: MutableList<Transfer?>)

        fun onError(mMessage: Int)

    }


    interface OnGetBalanceCallback {

        fun onSuccess(mMessage: Int, mMyBalance: String)

        fun onError(mMessage: Int)

    }

    interface OnGetTransferDetails {

        fun onSuccess(mMessage: Int, mTransfer: Transfer)

        fun onError(mMessage: Int)

    }

    fun listTransfers(mLimit: String, mOffset: String, mCallback: OnListTransfersCallback)

    fun getMyBalance(mCallback: OnGetBalanceCallback)

    fun getTransferDetails(mId: String, mCallback: OnGetTransferDetails)

}