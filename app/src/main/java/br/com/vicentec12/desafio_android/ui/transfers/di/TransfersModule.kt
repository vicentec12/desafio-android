package br.com.vicentec12.desafio_android.ui.transfers.di

import androidx.lifecycle.ViewModel
import br.com.vicentec12.desafio_android.di.ActivityScope
import br.com.vicentec12.desafio_android.di.ViewModelKey
import br.com.vicentec12.desafio_android.ui.transfers.TransfersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TransfersModule {

    @ActivityScope
    @Binds
    @IntoMap
    @ViewModelKey(TransfersViewModel::class)
    abstract fun bindTransfersViewModel(mViewModel: TransfersViewModel): ViewModel

}