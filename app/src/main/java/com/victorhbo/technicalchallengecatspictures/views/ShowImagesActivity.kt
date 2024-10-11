package com.victorhbo.technicalchallengecatspictures.views

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.victorhbo.technicalchallengecatspictures.R
import com.victorhbo.technicalchallengecatspictures.adapters.ImageAdapter
import com.victorhbo.technicalchallengecatspictures.databinding.ActivityShowImagesBinding
import com.victorhbo.technicalchallengecatspictures.retrofit.viewmodels.ShowImagesViewModel
import com.victorhbo.technicalchallengecatspictures.utils.AlertDialogUtil
import com.victorhbo.technicalchallengecatspictures.utils.NetworkUtil
import com.victorhbo.technicalchallengecatspictures.utils.ProgressDialog

class ShowImagesActivity : AppCompatActivity() {

    private val adapter = ImageAdapter()
    private lateinit var binding: ActivityShowImagesBinding
    private val viewModel: ShowImagesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        initToolBar()
        initListeners()
        initAdapter()
        initObserver()
        initRecycler()
        fetchImages()
    }

    private fun initComponents() {
        binding = ActivityShowImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.act_show_images)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initToolBar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun initListeners() {
        binding.btnReloadImages.setOnClickListener {

            fetchImages()
        }

        binding.btnExit.setOnClickListener {
            finishAffinity()
        }
    }

    private fun initAdapter() {
        binding.recyclerView.adapter = adapter

    }

    private fun initObserver() {
        viewModel.imageList.observe(this) { images ->
            adapter.submitList(images)
            ProgressDialog.dismiss()
            enableDisableButton(true)
        }

        viewModel.timeoutOccurred.observe(this) { hasTimedOut ->
            if (hasTimedOut) {
                ProgressDialog.dismiss()
                AlertDialogUtil.showErrorAlertDialog(this,
                    getString(R.string.timeout_message))
                enableDisableButton(true)
            }
        }
    }

    private fun initRecycler() {
        binding.recyclerView.layoutManager = GridLayoutManager(this, 4)
        binding.recyclerView.adapter = adapter
    }

    private fun fetchImages() {
        if (NetworkUtil.isInternetAvailable(this)) {
            enableDisableButton(false)
            ProgressDialog.show(this)
            viewModel.fetchImages()
        } else {
            AlertDialogUtil.showErrorAlertDialog(this,
                getString(R.string.check_your_internet))
        }
    }


    private fun enableDisableButton(isEnabled: Boolean, alpha: Float = if (isEnabled) 1.0f else 0.9f) {
        binding.btnReloadImages.isEnabled = isEnabled
        binding.btnReloadImages.alpha = alpha
    }
}
