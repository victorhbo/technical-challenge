package com.victorhbo.technicalchallengecatspictures.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victorhbo.technicalchallengecatspictures.R
import com.victorhbo.technicalchallengecatspictures.adapters.ImageAdapter
import com.victorhbo.technicalchallengecatspictures.retrofit.RetrofitInstance
import com.victorhbo.technicalchallengecatspictures.models.Image
import com.victorhbo.technicalchallengecatspictures.utils.ProgressDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowImagesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ImageAdapter
    private lateinit var imageList: MutableList<Image>
    private lateinit var btnReloadImages: Button
    private lateinit var btnExit: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        initToolBar()
        initListeners()
        initAdapter()
        initRecycler()
        fetchImages()
    }

    private fun initComponents() {
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_show_images)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.act_show_images)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnReloadImages = findViewById(R.id.btnReloadImages)
        btnExit = findViewById(R.id.btnExit)
        recyclerView = findViewById(R.id.recyclerView)
    }

    private fun initToolBar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun initListeners() {
        btnReloadImages.setOnClickListener {
            enableDisableButton(false)
            fetchImages()
        }

        btnExit.setOnClickListener {
            finish()
        }
    }

    private fun initAdapter() {
        imageList = mutableListOf()
        adapter = ImageAdapter(imageList)
    }

    private fun initRecycler() {
        recyclerView.layoutManager = GridLayoutManager(this, 4)
        recyclerView.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchImages() {
        ProgressDialog.show(this)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.searchCats("cats")
                withContext(Dispatchers.Main) {
                    imageList.clear()
                    response.data.forEach { imageData ->
                        if (imageData.images.isNotEmpty()) {
                            imageData.images.forEach { image ->
                                if (image.link.endsWith(".jpg") || image.link.endsWith(".png")) {
                                    imageList.add(image)
                                }
                            }
                        }
                    }

                    Log.d("Filtered Image Count", "Total images: ${imageList.size}")
                    adapter.notifyDataSetChanged()
                }

            } catch (e: Exception) {
                Log.e("API Error", "Erro desconhecido: ${e?.printStackTrace()}")
            } finally {
                withContext(Dispatchers.Main) {
                    ProgressDialog.dismiss()
                    enableDisableButton(true)
                }
            }
        }
    }

    private fun enableDisableButton(isEnabled: Boolean, alpha: Float = if (isEnabled) 1.0f else 0.9f) {
        btnReloadImages.isEnabled = isEnabled
        btnReloadImages.alpha = alpha
    }

}