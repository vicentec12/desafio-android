package br.com.vicentec12.desafio_android.ui.transfers

import androidx.recyclerview.widget.RecyclerView
import br.com.vicentec12.desafio_android.data.model.Transfer
import br.com.vicentec12.desafio_android.databinding.ItemTransferBinding
import br.com.vicentec12.desafio_android.interfaces.OnItemClickListener

class TransfersHolder(
    private val mBinding: ItemTransferBinding,
    private val mOnItemClickListener: OnItemClickListener?
) : RecyclerView.ViewHolder(mBinding.root) {

    private lateinit var mTransfer: Transfer

    init {
        mBinding.root.setOnClickListener { view ->
            mOnItemClickListener?.onItemClick(view, mTransfer, adapterPosition)
        }
    }

    fun bind(mTransfer: Transfer?) {
        this.mTransfer = mTransfer ?: Transfer()
        mBinding.transfer = this.mTransfer
    }

}