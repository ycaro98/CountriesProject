package com.example.countriesproject

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.countriesproject.R
import com.example.countriesproject.databinding.ActivityDetailsBinding
import com.example.countriesproject.databinding.ActivityLayoutCustomDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var mDialog: AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val continent = intent?.extras?.getString("continentName")
        val city = intent?.extras?.getString("cityName")
        val language = intent?.extras?.getString("languageName")
        val numHab = intent?.extras?.getString("numHab")
        val geoExt = intent?.extras?.getString("geoExtension")
        val currency = intent?.extras?.getString("currency")
        val country = intent?.extras?.getString("countryName")
        val description = intent?.extras?.getString("description")

        setSupportActionBar(binding.toolbarDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = country
        binding.continentName.text = continent
        binding.cityDetails.text = city
        binding.languageDetails.text = language
        binding.numhabDetails.text = numHab
        binding.sizeDetails.text = geoExt
        binding.coin.text = currency
        binding.descriptionCountry.text = description

        getImageCountry()?.let {
            binding.countryDetailImage.setImageResource(it)
        }

        initDialog()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.informationIcon -> mDialog.show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initDialog() {
        val layoutInflater = LayoutInflater.from(this)
        val binding2 = ActivityLayoutCustomDialogBinding.inflate(layoutInflater, null, false)

        val imageAuthor = intent?.extras?.getString("imageAuthor")
        val imageCopyright = intent?.extras?.getString("imageCopyright")
        val imageName = intent?.extras?.getString("imageName")

        binding2.authorImage.text = "Author: $imageAuthor"
        binding2.imageName.text = "$imageName"
        binding2.linkImageCopyright.setOnClickListener{
            val uri = Uri.parse("$imageCopyright")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
//            val intent = Intent(Intent.ACTION_SEND)
//            intent.setType("text/html")
//            intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(imageCopyright))
//            startActivity(Intent.createChooser(intent, "share"))
        }

        val nameImage =  SpannableString(binding2.imageName.text)
        val authorImage = SpannableString(binding2.authorImage.text)
        val bold = StyleSpan(Typeface.BOLD)

        authorImage.setSpan(bold, 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        nameImage.setSpan(bold, 0, imageName?.length?:0, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding2.authorImage.text = authorImage
        binding2.imageName.text = nameImage

        getImageCountry()?.let {
            binding2.countryImageDialog.setImageResource(it)
        }

        mDialog = MaterialAlertDialogBuilder(this)
            .setView(binding2.root)
            .setCancelable(true)
            .create()


        binding2.buttonOk.setOnClickListener{
            mDialog.dismiss()
        }
    }

    private fun getImageCountry() = intent?.extras?.getInt("imageCountry")
}


