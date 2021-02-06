package br.com.vicentec12.desafio_android.data.source.transfer

import br.com.vicentec12.desafio_android.R
import br.com.vicentec12.desafio_android.data.model.Balance
import br.com.vicentec12.desafio_android.data.model.Transfer
import br.com.vicentec12.desafio_android.data.source.RetrofitApi
import br.com.vicentec12.desafio_android.data.source.TransfersService
import br.com.vicentec12.desafio_android.extensions.toCurrency
import br.com.vicentec12.desafio_android.util.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransferRemoteDataSource private constructor(
    private val mTransfersService: TransfersService,
    private val mAppExecutors: AppExecutors
) : TransferDataSource {

    override fun listTransfers(
        mLimit: String,
        mOffset: String,
        mCallback: TransferDataSource.OnListTransfersCallback
    ) {
        mAppExecutors.networkIO.execute {
            mTransfersService.myStatement(mLimit, mOffset)
                .enqueue(object : Callback<TransferResult> {
                    override fun onResponse(
                        call: Call<TransferResult>,
                        response: Response<TransferResult>
                    ) {
                        if (response.isSuccessful) {
                            val mListTransfers = response.body()?.items
                            mAppExecutors.mainThread.execute {
                                mCallback.onSuccess(
                                    R.string.message_success_list_transfers,
                                    mListTransfers ?: mutableListOf()
                                )
                            }
                        }
                    }

                    override fun onFailure(call: Call<TransferResult>, t: Throwable) {
                        mAppExecutors.mainThread.execute {
                            mCallback.onError(R.string.message_error_server)
                        }
                    }

                })
        }
    }

    override fun getMyBalance(mCallback: TransferDataSource.OnGetBalanceCallback) {
        mAppExecutors.networkIO.execute {
            mTransfersService.myBalance().enqueue(object : Callback<Balance> {
                override fun onResponse(call: Call<Balance>, response: Response<Balance>) {
                    if (response.isSuccessful) {
                        mAppExecutors.mainThread.execute {
                            mCallback.onSuccess(
                                R.string.message_success_get_balance,
                                response.body()?.amount?.toCurrency() ?: 0.0.toCurrency()
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<Balance>, t: Throwable) {
                    mAppExecutors.mainThread.execute {
                        mCallback.onError(R.string.message_error_server)
                    }
                }
            })
        }
    }

    override fun getTransferDetails(
        mId: String,
        mCallback: TransferDataSource.OnGetTransferDetails
    ) {
        mAppExecutors.networkIO.execute {
            RetrofitApi.mTransfersService.myStatementDetail(mId)
                .enqueue(object : Callback<Transfer?> {
                    override fun onResponse(call: Call<Transfer?>, response: Response<Transfer?>) {
                        if (response.isSuccessful) {
                            mAppExecutors.mainThread.execute {
                                mCallback.onSuccess(
                                    R.string.message_success_get_transfer_details,
                                    response.body() ?: Transfer()
                                )
                            }
                        }
                    }

                    override fun onFailure(call: Call<Transfer?>, t: Throwable) {
                        mAppExecutors.mainThread.execute {
                            mCallback.onError(R.string.message_error_server)
                        }
                    }

                })
        }
    }

    companion object {

        @JvmStatic
        private var mINSTANCE: TransferRemoteDataSource? = null

        @JvmStatic
        fun getInstance(
            mTransfersService: TransfersService,
            mAppExecutors: AppExecutors
        ) = mINSTANCE ?: synchronized(TransferRemoteDataSource::class.java) {
            mINSTANCE ?: TransferRemoteDataSource(mTransfersService, mAppExecutors).also {
                mINSTANCE = it
            }
        }

        @JvmStatic
        fun destroyInstance() {
            mINSTANCE = null
        }

    }

}