package br.com.vicentec12.desafio_android.di

import android.content.Context
import br.com.vicentec12.desafio_android.data.source.retrofit_api.RetrofitModule
import br.com.vicentec12.desafio_android.data.source.transfer.TransferDataSourceModule
import br.com.vicentec12.desafio_android.ui.transfer_details.di.TransferDetailsComponent
import br.com.vicentec12.desafio_android.ui.transfers.di.TransfersComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelBuilderModule::class,
        RetrofitModule::class,
        TransferDataSourceModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance mApplicationContext: Context): AppComponent

    }

    fun transfersComponent(): TransfersComponent.Factory

    fun transferDetailsComponent(): TransferDetailsComponent.Factory

}