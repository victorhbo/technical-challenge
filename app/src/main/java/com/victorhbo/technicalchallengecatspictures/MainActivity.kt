package com.victorhbo.technicalchallengecatspictures

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.victorhbo.technicalchallengecatspictures.views.ShowImagesActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initLogoFadeIn()

    }

    private fun initLogoFadeIn() {
        val logo: ImageView = findViewById(R.id.logo)
        logo.postDelayed({
            logo.visibility = View.VISIBLE
            logo.alpha = 0f

            logo.animate()
                .alpha(1f)
                .setDuration(2000)
                .withEndAction {
                    val intent = Intent(this, ShowImagesActivity::class.java)
                    startActivity(intent)
                    finish()
                }
        }, 500)
    }
}