package br.com.vicentec12.desafio_android.data.source.transfer

import br.com.vicentec12.desafio_android.R
import br.com.vicentec12.desafio_android.data.model.Transfer
import br.com.vicentec12.desafio_android.data.source.Result
import br.com.vicentec12.desafio_android.data.source.retrofit_api.TransfersService
import br.com.vicentec12.desafio_android.extensions.toCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransferRemoteDataSource @Inject constructor(private val mTransfersService: TransfersService) :
    TransferDataSource {

    override suspend fun listTransfers(
        mLimit: String,
        mOffset: String,
    ): Result<MutableList<Transfer?>> = withContext(Dispatchers.IO) {
        try {
            val response = mTransfersService.myStatement(mLimit, mOffset)
            if (response.isSuccessful)
                Result.Success(response.body()?.items!!,
                    R.string.message_success_list_transfers)
            else
                Result.Error(R.string.message_error_server)
        } catch (e: Exception) {
            Result.Error(R.string.message_error_server)
        }
    }

    override suspend fun getMyBalance(): Result<String> = withContext(Dispatchers.IO) {
        try {
            val response = mTransfersService.myBalance()
            if (response.isSuccessful)
                Result.Success(response.body()?.amount?.toCurrency() ?: 0.0.toCurrency(),
                    R.string.message_success_get_balance)
            else
                Result.Error(R.string.message_error_server)
        } catch (e: Exception) {
            Result.Error(R.string.message_error_server)
        }
    }

    override suspend fun getTransferDetails(mId: String): Result<Transfer?> =
        withContext(Dispatchers.IO) {
            try {
                val response = mTransfersService.myStatementDetail(mId)
                if (response.isSuccessful)
                    Result.Success(response.body(), R.string.message_success_get_transfer_details)
                else
                    Result.Error(R.string.message_error_server)
            } catch (e: Exception) {
                Result.Error(R.string.message_error_server)
            }
        }
}