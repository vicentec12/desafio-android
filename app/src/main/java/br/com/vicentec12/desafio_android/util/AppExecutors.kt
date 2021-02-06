package br.com.vicentec12.desafio_android.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Singleton

const val THREAD_COUNT = 3

@Singleton
open class AppExecutors @Inject constructor(
    val diskIO: Executor,
    val networkIO: Executor,
    val mainThread: Executor
) {

    class MainThreadExecutor : Executor {

        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}