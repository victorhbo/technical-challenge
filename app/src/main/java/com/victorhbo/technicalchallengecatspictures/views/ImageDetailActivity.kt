package com.victorhbo.technicalchallengecatspictures.views

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso
import com.victorhbo.technicalchallengecatspictures.R

class ImageDetailActivity : AppCompatActivity() {
    private lateinit var btnBack: ImageView
    private lateinit var imageView: ImageView
    private lateinit var btnClose: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initComponents()
        initToolBar()
        initListeners()
        loadImage()
    }

    private fun initComponents() {
        enableEdgeToEdge()
        setContentView(R.layout.activity_image_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.act_image_details)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imageView = findViewById(R.id.iv_details)
        btnClose = findViewById(R.id.btnClose)
        btnBack = findViewById(R.id.btnBack)

    }

    private fun initToolBar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun initListeners() {
        btnClose.setOnClickListener {
            finish()
        }
        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun loadImage() {
        val imageUrl = intent.getStringExtra("IMAGE_URL")
        if (imageUrl != null) {
            Picasso.get()
                .load(imageUrl)
                .into(imageView)
        }
    }
}
