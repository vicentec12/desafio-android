package br.com.vicentec12.desafio_android.data.source.retrofit_api

import br.com.vicentec12.desafio_android.data.model.Balance
import br.com.vicentec12.desafio_android.data.model.Transfer
import br.com.vicentec12.desafio_android.data.source.transfer.TransferResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface TransfersService {

    @Headers(value = ["token: ${RetrofitModule.mToken}"])
    @GET("myStatement/{limit}/{offset}")
    fun myStatement(
        @Path("limit") mLimit: String,
        @Path("offset") mOffset: String
    ): Call<TransferResult>

    @Headers(value = ["token: ${RetrofitModule.mToken}"])
    @GET("myBalance")
    fun myBalance(): Call<Balance>

    @Headers(value = ["token: ${RetrofitModule.mToken}"])
    @GET("/myStatement/detail/{id}")
    fun myStatementDetail(@Path("id") mId: String): Call<Transfer?>

}