package br.com.vicentec12.desafio_android

import android.app.Application
import br.com.vicentec12.desafio_android.di.AppComponent
import br.com.vicentec12.desafio_android.di.DaggerAppComponent

class ChallengeApp : Application() {

    lateinit var mAppComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        mAppComponent = DaggerAppComponent.factory().create(this)
    }

}