package br.com.vicentec12.desafio_android.ui.transfers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.vicentec12.desafio_android.data.model.Transfer
import br.com.vicentec12.desafio_android.data.source.AppSharedPreferences
import br.com.vicentec12.desafio_android.data.source.Result
import br.com.vicentec12.desafio_android.data.source.transfer.TransferDataSource
import br.com.vicentec12.desafio_android.di.ActivityScope
import br.com.vicentec12.desafio_android.extensions.toCurrency
import kotlinx.coroutines.launch
import javax.inject.Inject

@ActivityScope
class TransfersViewModel @Inject constructor(
    private val mTransferRepository: TransferDataSource,
    private val mAppSharedPreferences: AppSharedPreferences,
) : ViewModel() {

    companion object {
        private const val CHILD_PROGRESS = 0
        private const val CHILD_RECYCLER = 1
        private const val CHILD_MESSAGE_ERROR = 2

        private const val CHILD_BALANCE_PROGRESS = 0
        private const val CHILD_BALANCE_LABEL = 1
        private const val CHILD_BALANCE_INVISIBLE = 2
    }

    private val _transfers: MutableLiveData<MutableList<Transfer?>> = MutableLiveData()
    val transfers: LiveData<MutableList<Transfer?>>
        get() = _transfers

    private val _visibilityBalance: MutableLiveData<Int> = MutableLiveData()
    val visibilityBalance: LiveData<Int>
        get() = _visibilityBalance

    private val _myBalance: MutableLiveData<String> = MutableLiveData()
    val myBalance: LiveData<String>
        get() = _myBalance

    private val _messageError: MutableLiveData<Int> = MutableLiveData()
    val messageError: LiveData<Int>
        get() = _messageError

    private val _childFlipperBalance: MutableLiveData<Int> = MutableLiveData()
    val childFlipperBalance: LiveData<Int>
        get() = _childFlipperBalance

    private val _childFlipper: MutableLiveData<Int> = MutableLiveData()
    val childFlipper: LiveData<Int>
        get() = _childFlipper

    private val _transfersOffset: MutableLiveData<Int> = MutableLiveData()

    // Adapter

    private val _isTransfersFinish: MutableLiveData<Boolean> = MutableLiveData()

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()

    init {
        _visibilityBalance.value = mAppSharedPreferences.getShowMyBalance()
        _transfers.value = mutableListOf()
        _transfersOffset.value = 0
        _isTransfersFinish.value = false
        _isLoading.value = false
    }

    fun listTransfers(mShowLoading: Boolean = true, mForceLoad: Boolean = false) {
        viewModelScope.launch {
            if (transfers.value?.size == 0 || mForceLoad) {
                if (mShowLoading)
                    _childFlipper.value = CHILD_PROGRESS
                when (val result =
                    mTransferRepository.listTransfers("10", "${_transfersOffset.value!!}")) {
                    is Result.Success -> {
                        val mTransfers = result.mData
                        _transfersOffset.value = _transfersOffset.value?.plus(1)
                        _isLoading.value = false
                        _isTransfersFinish.value = mTransfers.size < 10
                        val mNewTransfers = mutableListOf<Transfer?>()
                        mNewTransfers.addAll(transfers.value as MutableList<Transfer?>)
                        mNewTransfers.addAll(mTransfers)
                        if (mNewTransfers.contains(null))
                            mNewTransfers.remove(null)
                        _transfers.value = mNewTransfers
                        _childFlipper.value = CHILD_RECYCLER
                    }
                    is Result.Error -> {
                        _messageError.value = result.mMessage
                        _childFlipper.value = CHILD_MESSAGE_ERROR
                    }
                }
            }
        }
    }

    fun listTransfers(mTotalItemCount: Int, mLastVisibleItem: Int) {
        if (!_isTransfersFinish.value!! && !_isLoading.value!! && mLastVisibleItem == mTotalItemCount - 2) {
            _isLoading.value = true
            val mCurrentTransfers = mutableListOf<Transfer?>()
            mCurrentTransfers.addAll(transfers.value as MutableList<Transfer?>)
            mCurrentTransfers.add(null)
            _transfers.value = mCurrentTransfers
            listTransfers(mShowLoading = false, mForceLoad = true)
        }
    }

    fun getMyBalance() {
        viewModelScope.launch {
            _childFlipperBalance.value = CHILD_BALANCE_PROGRESS
            when (val result = mTransferRepository.getMyBalance()) {
                is Result.Success -> {
                    _myBalance.value = result.mData
                    _childFlipperBalance.value =
                        getVisibilityBalance(visibilityBalance.value ?: 0)
                }
                is Result.Error -> {
                    _myBalance.value = 0.0.toCurrency()
                    _childFlipperBalance.value = CHILD_BALANCE_LABEL
                }
            }
        }
    }

    fun setBalanceVisibility() {
        val visibility = if (visibilityBalance.value == 0) 1 else 0
        _visibilityBalance.value = visibility
        mAppSharedPreferences.setShowMyBalance(visibility)
        if (_childFlipperBalance.value != CHILD_BALANCE_PROGRESS)
            _childFlipperBalance.value = getVisibilityBalance(visibility)
    }

    private fun getVisibilityBalance(mVisibility: Int) =
        if (mVisibility == 0) CHILD_BALANCE_INVISIBLE else CHILD_BALANCE_LABEL

}