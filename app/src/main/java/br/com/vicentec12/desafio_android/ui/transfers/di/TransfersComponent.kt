package br.com.vicentec12.desafio_android.ui.transfers.di

import br.com.vicentec12.desafio_android.di.ActivityScope
import br.com.vicentec12.desafio_android.ui.transfers.TransfersActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [TransfersModule::class])
interface TransfersComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(): TransfersComponent

    }

    fun inject(mActivity: TransfersActivity)

}