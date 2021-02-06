package br.com.vicentec12.desafio_android.ui.transfer_details

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.vicentec12.desafio_android.ChallengeApp
import br.com.vicentec12.desafio_android.R
import br.com.vicentec12.desafio_android.databinding.ActivityTransferDetailsBinding
import br.com.vicentec12.desafio_android.util.ShareUtil
import javax.inject.Inject

class TransferDetailsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivityTransferDetailsBinding

    @Inject
    lateinit var mFactory: ViewModelProvider.Factory

    private val mViewModel: TransferDetailsViewModel by viewModels(factoryProducer = { mFactory })

    private var mTransferId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as ChallengeApp).mAppComponent
            .transferDetailsComponent().create().inject(this)
        super.onCreate(savedInstanceState)
        mBinding = ActivityTransferDetailsBinding.inflate(layoutInflater).apply {
            setContentView(root)
            setSupportActionBar(lytToolbar.toolbar)
            viewModel = mViewModel
            lifecycleOwner = this@TransferDetailsActivity
        }
        init()
    }

    override fun onResume() {
        super.onResume()
        if (!mBinding.btnShare.isEnabled)
            mBinding.btnShare.isEnabled = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mTransferId = intent.getStringExtra("extra_transfer_id")
        mViewModel.getTrasferDetails(mTransferId)
        mBinding.btnShare.setOnClickListener(this)
        mBinding.lytErrorMessage.btnErrorMessage.setOnClickListener(this)
    }

    private fun shareReceipt(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    EXTERNAL_STORED_PERMISSION_CODE
                )
            } else
                ShareUtil.share(view)
        } else
            ShareUtil.share(view)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == EXTERNAL_STORED_PERMISSION_CODE) {
            for (i in permissions.indices) {
                val permission = permissions[i]
                val grantResult = grantResults[i]
                if (permission == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED)
                        ShareUtil.share(mBinding.llcTransferDetails)
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_share -> {
                mBinding.btnShare.isEnabled = false
                shareReceipt(mBinding.llcTransferDetails)
            }
            R.id.btn_error_message -> mViewModel.getTrasferDetails(mTransferId)
        }
    }

    companion object {

        const val EXTERNAL_STORED_PERMISSION_CODE = 128

    }

}