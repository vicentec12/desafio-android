package br.com.vicentec12.desafio_android.data.source.transfer

import br.com.vicentec12.desafio_android.data.source.Remote
import dagger.Binds
import dagger.Module

@Module
abstract class TransferDataSourceModule {

    @Binds
    @Remote
    abstract fun bindTransferRemoteDataSource(mTransferRemoteDataSource: TransferRemoteDataSource): TransferDataSource

    @Binds
    abstract fun bindTransferRepository(mTransferRepository: TransferRepository): TransferDataSource

}