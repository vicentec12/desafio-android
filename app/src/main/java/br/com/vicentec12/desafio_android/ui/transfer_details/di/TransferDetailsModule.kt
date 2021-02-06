package br.com.vicentec12.desafio_android.ui.transfer_details.di

import androidx.lifecycle.ViewModel
import br.com.vicentec12.desafio_android.di.ActivityScope
import br.com.vicentec12.desafio_android.di.ViewModelKey
import br.com.vicentec12.desafio_android.ui.transfer_details.TransferDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TransferDetailsModule {

    @ActivityScope
    @Binds
    @IntoMap
    @ViewModelKey(TransferDetailsViewModel::class)
    abstract fun bindTransferDetailsViewModel(mViewModel: TransferDetailsViewModel): ViewModel

}