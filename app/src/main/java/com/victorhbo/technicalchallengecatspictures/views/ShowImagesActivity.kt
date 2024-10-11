package com.victorhbo.technicalchallengecatspictures.views

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private var currentPage = 1
    private var isLoading = false

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
            isLoading = false
        }

        viewModel.timeoutOccurred.observe(this) { hasTimedOut ->
            if (hasTimedOut) {
                ProgressDialog.dismiss()
                AlertDialogUtil.showErrorAlertDialog(this,
                    getString(R.string.timeout_message))
                enableDisableButton(true)
                isLoading = false
            }
        }
        viewModel.errorMessage.observe(this) { errorMgs ->
            ProgressDialog.dismiss()
            if (errorMgs != null) {
                AlertDialogUtil.showErrorAlertDialog(this, errorMgs)
            }
            enableDisableButton(true)
            isLoading = false
        }
    }

//    private fun initRecycler() {
//        val gridLayoutManager = GridLayoutManager(this, 4)
//        binding.recyclerView.layoutManager = gridLayoutManager
//        binding.recyclerView.adapter = adapter
//
//        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                if (dy > 0) {
//                    val totalItemCount = gridLayoutManager.itemCount
//                    val lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition()
//
//                    if (!isLoading && totalItemCount <= (lastVisibleItemPosition + 5)) {
//                        isLoading = true
//                        currentPage++
//                        fetchImages()
//                    }
//                }
//            }
//        })
//
//    }

    private fun initRecycler() {
        val displayMetrics = Resources.getSystem().displayMetrics
        val screenWidth = displayMetrics.widthPixels // Largura da tela em pixels

        // Defina a largura desejada para cada item em pixels
        val desiredItemWidth = resources.getDimensionPixelSize(R.dimen.item_width) // Defina isso no seu arquivo dimens.xml

        // Calcule o número de colunas
        val numberOfColumns = screenWidth / desiredItemWidth

        // Crie o GridLayoutManager com o número calculado de colunas
        val gridLayoutManager = GridLayoutManager(this, numberOfColumns)
        binding.recyclerView.layoutManager = gridLayoutManager
        binding.recyclerView.adapter = adapter

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val totalItemCount = gridLayoutManager.itemCount
                    val lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition()

                    if (!isLoading && totalItemCount <= (lastVisibleItemPosition + 5)) {
                        isLoading = true
                        currentPage++
                        fetchImages()
                    }
                }
            }
        })
    }


    private fun fetchImages() {
        if (NetworkUtil.isInternetAvailable(this)) {
            enableDisableButton(false)
            ProgressDialog.show(this)

            viewModel.errorMessage.observe(this) { errorMessage ->
                if (errorMessage != null) {
                    AlertDialogUtil.showErrorAlertDialog(this, errorMessage)
                    ProgressDialog.dismiss()
                    enableDisableButton(true)
                    isLoading = false
                }
            }

            viewModel.fetchImages(currentPage)
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
