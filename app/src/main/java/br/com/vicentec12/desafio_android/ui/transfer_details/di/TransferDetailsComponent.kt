package br.com.vicentec12.desafio_android.ui.transfer_details.di

import br.com.vicentec12.desafio_android.di.ActivityScope
import br.com.vicentec12.desafio_android.ui.transfer_details.TransferDetailsActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [TransferDetailsModule::class])
interface TransferDetailsComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(): TransferDetailsComponent

    }

    fun inject(mActivity: TransferDetailsActivity)

}