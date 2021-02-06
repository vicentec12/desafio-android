package br.com.vicentec12.desafio_android.util

import br.com.vicentec12.desafio_android.data.source.RetrofitApi
import br.com.vicentec12.desafio_android.data.source.transfer.TransferRemoteDataSource
import br.com.vicentec12.desafio_android.data.source.transfer.TransferRepository

object Inject {

    fun provideTransferRepository(): TransferRepository =
        TransferRepository.getInstance(
            TransferRemoteDataSource.getInstance(
                RetrofitApi.mTransfersService, AppExecutors()
            )
        )

}