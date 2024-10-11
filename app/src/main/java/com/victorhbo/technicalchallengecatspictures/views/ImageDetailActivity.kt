package com.victorhbo.technicalchallengecatspictures.views

import android.content.res.Resources
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso
import com.victorhbo.technicalchallengecatspictures.databinding.ActivityImageDetailBinding
import com.victorhbo.technicalchallengecatspictures.utils.Constants

class ImageDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        initToolBar()
        initListeners()
        loadImage()
    }

    private fun initComponents() {
        enableEdgeToEdge()
        binding = ActivityImageDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.actImageDetails) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initToolBar() {
        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
    }

    private fun initListeners() {
        binding.btnClose.setOnClickListener {
            finish()
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun loadImage() {
        val imageUrl = intent.getStringExtra(Constants.EXTRAS_NAME)
        binding.tvDetailNome.text = imageUrl.toString()
        if (imageUrl != null) {
            adjustImageSize(binding.ivDetails, imageUrl)
        }
    }

    private fun adjustImageSize(imageView: ImageView, imageUrl: String) {
        Picasso.get()
            .load(imageUrl)
            .resize(getScreenWidth(), getScreenHeight())
            .centerInside()
            .into(imageView)
    }

    private fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    private fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }
}
