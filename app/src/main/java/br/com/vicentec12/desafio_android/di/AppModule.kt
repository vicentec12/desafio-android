package br.com.vicentec12.desafio_android.di

import br.com.vicentec12.desafio_android.ui.transfer_details.di.TransferDetailsComponent
import br.com.vicentec12.desafio_android.ui.transfers.di.TransfersComponent
import br.com.vicentec12.desafio_android.util.AppExecutors
import br.com.vicentec12.desafio_android.util.THREAD_COUNT
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module(
    subcomponents = [TransfersComponent::class,
        TransferDetailsComponent::class]
)
object AppModule {

    @Provides
    @Singleton
    @JvmStatic
    fun provideAppExecutors() = AppExecutors(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(THREAD_COUNT),
        AppExecutors.MainThreadExecutor()
    )

}