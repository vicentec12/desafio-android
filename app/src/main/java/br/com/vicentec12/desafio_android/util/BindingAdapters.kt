package br.com.vicentec12.desafio_android.util

import android.widget.ViewFlipper
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.vicentec12.desafio_android.data.model.Transfer
import br.com.vicentec12.desafio_android.ui.transfers.TransfersAdapter

object BindingAdapters {

    @BindingAdapter("items")
    @JvmStatic
    fun listTransferItems(mRecyclerView: RecyclerView, mList: List<Transfer>?) {
        val mHomeAdapter = mRecyclerView.adapter as TransfersAdapter?
        mHomeAdapter?.submitList(mList ?: listOf())
    }

    @BindingAdapter("displayChild")
    @JvmStatic
    fun displayChildViewFlipper(mViewFlipper: ViewFlipper, mDisplayChild: Int) {
        if (mViewFlipper.displayedChild != mDisplayChild)
            mViewFlipper.displayedChild = mDisplayChild
    }

}