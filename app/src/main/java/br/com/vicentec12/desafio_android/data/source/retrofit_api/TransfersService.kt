package br.com.vicentec12.desafio_android.data.source.retrofit_api

import br.com.vicentec12.desafio_android.data.model.Balance
import br.com.vicentec12.desafio_android.data.model.Transfer
import br.com.vicentec12.desafio_android.data.source.transfer.TransferResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface TransfersService {

    @Headers(value = ["token: ${RetrofitModule.mToken}"])
    @GET("myStatement/{limit}/{offset}")
    suspend fun myStatement(
        @Path("limit") mLimit: String,
        @Path("offset") mOffset: String,
    ): Response<TransferResult>

    @Headers(value = ["token: ${RetrofitModule.mToken}"])
    @GET("myBalance")
    suspend fun myBalance(): Response<Balance>

    @Headers(value = ["token: ${RetrofitModule.mToken}"])
    @GET("/myStatement/detail/{id}")
    suspend fun myStatementDetail(@Path("id") mId: String): Response<Transfer?>

}