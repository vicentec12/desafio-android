package br.com.vicentec12.desafio_android.data.source

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApi {

    private const val BASE_URL = "https://desafio-mobile-bff.herokuapp.com"

    const val mToken =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"

    private val mOkHttpClient: OkHttpClient by lazy {
        val mInterceptor = HttpLoggingInterceptor()
        mInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(mInterceptor).build()
    }

    private val mRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(mOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val mTransfersService: TransfersService by lazy {
        mRetrofit.create(TransfersService::class.java)
    }

}