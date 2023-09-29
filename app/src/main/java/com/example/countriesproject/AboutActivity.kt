package com.example.countriesproject

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.appcompat.app.AppCompatActivity
import com.example.countriesproject.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.linkCopyrightFlags.movementMethod = LinkMovementMethod.getInstance()
        binding.linkOtherImages.movementMethod = LinkMovementMethod.getInstance()
        binding.linkCopyrightImage.movementMethod = LinkMovementMethod.getInstance()




    }
}