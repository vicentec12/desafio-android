package br.com.vicentec12.desafio_android.ui.transfers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.vicentec12.desafio_android.data.model.Transfer
import br.com.vicentec12.desafio_android.databinding.ItemLoadingBinding
import br.com.vicentec12.desafio_android.databinding.ItemTransferBinding
import br.com.vicentec12.desafio_android.interfaces.OnItemClickListener
import br.com.vicentec12.desafio_android.ui.LoadingHolder

class TransfersAdapter :
    ListAdapter<Transfer?, RecyclerView.ViewHolder>(Transfer.getDiffItemCallback()) {

    lateinit var mOnItemClickListener: OnItemClickListener

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) == null) ITEM_LOADING else ITEM_TRANSFER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_LOADING) {
            val mBinding = ItemLoadingBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            LoadingHolder(mBinding)
        } else {
            val mBinding = ItemTransferBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            TransfersHolder(mBinding, mOnItemClickListener)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TransfersHolder)
            holder.bind(currentList[position])
    }

    companion object {
        private const val ITEM_LOADING = 0
        private const val ITEM_TRANSFER = 1
    }

}