package br.com.vicentec12.desafio_android.ui.transfer_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.vicentec12.desafio_android.data.model.Transfer
import br.com.vicentec12.desafio_android.data.source.Result
import br.com.vicentec12.desafio_android.data.source.transfer.TransferDataSource
import br.com.vicentec12.desafio_android.di.ActivityScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@ActivityScope
class TransferDetailsViewModel @Inject constructor(
    private val mTransferDataSource: TransferDataSource,
) : ViewModel() {

    companion object {
        private const val CHILD_PROGRESS = 0
        private const val CHILD_RECEIPT = 1
        private const val CHILD_MESSAGE_ERROR = 2
    }

    private val _transfer: MutableLiveData<Transfer> = MutableLiveData()
    val transfer: LiveData<Transfer>
        get() = _transfer

    private val _messageError: MutableLiveData<Int> = MutableLiveData()
    val messageError: LiveData<Int>
        get() = _messageError

    private val _childFlipper: MutableLiveData<Int> = MutableLiveData()
    val childFlipper: LiveData<Int>
        get() = _childFlipper

    fun getTrasferDetails(mTransferId: String?) {
        viewModelScope.launch {
            if (_transfer.value == null) {
                _childFlipper.value = CHILD_PROGRESS
                when (val response = mTransferDataSource.getTransferDetails(mTransferId ?: "")) {
                    is Result.Success -> {
                        _childFlipper.value = CHILD_RECEIPT
                        _transfer.value = response.mData
                    }
                    is Result.Error -> {
                        _childFlipper.value = CHILD_MESSAGE_ERROR
                        _messageError.value = response.mMessage
                    }
                }
            }
        }
    }

}