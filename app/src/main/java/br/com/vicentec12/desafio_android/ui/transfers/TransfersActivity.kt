package br.com.vicentec12.desafio_android.ui.transfers

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.vicentec12.desafio_android.data.model.Transfer
import br.com.vicentec12.desafio_android.data.source.AppSharedPreferences
import br.com.vicentec12.desafio_android.data.source.transfer.TransferRemoteDataSource
import br.com.vicentec12.desafio_android.data.source.transfer.TransferRepository
import br.com.vicentec12.desafio_android.databinding.ActivityTransfersBinding
import br.com.vicentec12.desafio_android.interfaces.OnItemClickListener
import br.com.vicentec12.desafio_android.ui.transfer_details.TransferDetailsActivity
import br.com.vicentec12.desafio_android.util.Inject

class TransfersActivity : AppCompatActivity(), View.OnClickListener, OnItemClickListener {

    private lateinit var mBinding: ActivityTransfersBinding

    private val mViewModel: TransfersViewModel by viewModels(
        factoryProducer = {
            TransfersViewModel.HomeViewModelFactory(
                Inject.provideTransferRepository(),
                AppSharedPreferences.getInstance(this@TransfersActivity)
            )
        }
    )

    private val mRecyclerScrollListener: RecyclerView.OnScrollListener by lazy {
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (linearLayoutManager != null)
                    mViewModel.listTransfers(
                        linearLayoutManager.itemCount,
                        linearLayoutManager.findLastVisibleItemPosition()
                    )
            }
        }
    }

    private lateinit var mAdapter: TransfersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityTransfersBinding.inflate(layoutInflater).apply {
            setContentView(root)
            setSupportActionBar(lytToolbar.toolbar)
            viewModel = mViewModel
            lifecycleOwner = this@TransfersActivity
            lytErrorMessage.btnErrorMessage.setOnClickListener(this@TransfersActivity)
        }
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        TransferRepository.destroyInstance()
        TransferRemoteDataSource.destroyInstance()
        mBinding.rvwHome.removeOnScrollListener(mRecyclerScrollListener)
    }

    private fun init() {
        setupRecyclerView()
        mViewModel.listTransfers()
        mViewModel.getMyBalance()
    }

    private fun setupRecyclerView() {
        mBinding.rvwHome.apply {
            mAdapter = TransfersAdapter()
            mAdapter.mOnItemClickListener = this@TransfersActivity
            adapter = mAdapter
            addOnScrollListener(mRecyclerScrollListener)
        }
    }

    override fun onClick(v: View?) {
        mViewModel.getMyBalance()
        mViewModel.listTransfers(mForceLoad = true)
    }

    override fun onItemClick(v: View, item: Any?, position: Int) {
        val mTransfer = item as Transfer
        val mIntent = Intent(this, TransferDetailsActivity::class.java)
        mIntent.putExtra("extra_transfer_id", mTransfer.id)
        startActivity(mIntent)
    }

}