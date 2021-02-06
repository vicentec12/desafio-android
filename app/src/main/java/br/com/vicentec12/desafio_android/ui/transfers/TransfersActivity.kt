package br.com.vicentec12.desafio_android.ui.transfers

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.vicentec12.desafio_android.ChallengeApp
import br.com.vicentec12.desafio_android.R
import br.com.vicentec12.desafio_android.data.model.Transfer
import br.com.vicentec12.desafio_android.databinding.ActivityTransfersBinding
import br.com.vicentec12.desafio_android.interfaces.OnItemClickListener
import br.com.vicentec12.desafio_android.ui.transfer_details.TransferDetailsActivity
import javax.inject.Inject

class TransfersActivity : AppCompatActivity(), View.OnClickListener, OnItemClickListener {

    private lateinit var mBinding: ActivityTransfersBinding

    @Inject
    lateinit var mFactory: ViewModelProvider.Factory

    private val mViewModel: TransfersViewModel by viewModels(factoryProducer = { mFactory })

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
        (applicationContext as ChallengeApp).mAppComponent
            .transfersComponent().create().inject(this)
        super.onCreate(savedInstanceState)
        mBinding = ActivityTransfersBinding.inflate(layoutInflater).apply {
            setContentView(root)
            lytToolbar.toolbar.setTitle(R.string.title_transfer_activity)
            setSupportActionBar(lytToolbar.toolbar)
            viewModel = mViewModel
            lifecycleOwner = this@TransfersActivity
            lytErrorMessage.btnErrorMessage.setOnClickListener(this@TransfersActivity)
        }
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
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