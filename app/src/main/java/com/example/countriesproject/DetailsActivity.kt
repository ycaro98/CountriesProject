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
import com.example.countriesproject.databinding.ActivityDetailsBinding
import com.example.countriesproject.databinding.ActivityLayoutCustomDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.NumberFormat
import java.util.Locale

/*
* Esta classe representa a Activity que exibe os detalhes de um país
* */
class DetailsActivity : AppCompatActivity() {

    //Binding para a interface do usuário
    private lateinit var binding: ActivityDetailsBinding

    //chama uma tela de mensagem interativa customizada
    private lateinit var mDialog: AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Infla o layout e configura a view
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Obtém os dados passados pela Intent
        val continent = intent?.extras?.getString("continentName")
        val city = intent?.extras?.getString("cityName")
        val language = intent?.extras?.getString("languageName")
        val numHab = intent?.extras?.getString("numHab")
        val geoExt = intent?.extras?.getString("geoExtension")
        val currency = intent?.extras?.getString("currency")
        val country = intent?.extras?.getString("countryName")
        val description = intent?.extras?.getString("description")

        val locale = Locale("pt", "BR")
        val nf = NumberFormat.getNumberInstance(locale)

        // Configura a ActionBar e os elementos da tela
        setSupportActionBar(binding.toolbarDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = country
        binding.continentName.text = continent
        binding.cityDetails.text = city
        binding.languageDetails.text = language
        binding.numhabDetails.text = nf.format(numHab?.toDouble()).replace("."," ")
        binding.sizeDetails.text = nf.format(geoExt?.toDouble())+(" km²")
        binding.coin.text = currency
        binding.descriptionCountry.text = description

        //Obtém a imagem do país e a exibe
        getImageCountry()?.let {
            binding.countryDetailImage.setImageResource(it)
        }
        // Inicia o metodo
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

    /*
    * Metodo responsavel pela customização do AlertDialog
    * */
    private fun initDialog() {
        val layoutInflater = LayoutInflater.from(this)
        val binding2 = ActivityLayoutCustomDialogBinding.inflate(layoutInflater, null, false)

        //recebe os dados a serem atribuidos no alertDialog
        val imageAuthor = intent?.extras?.getString("imageAuthor")
        val imageCopyright = intent?.extras?.getString("imageCopyright")
        val imageName = intent?.extras?.getString("imageName")

        //configura os elementos recebidos pelo alertDialog
        binding2.authorImage.text = "Author: $imageAuthor"
        binding2.imageName.text = "$imageName"

        //Configura o link para a licença de imagem
        binding2.linkImageCopyright.setOnClickListener {
            val uri = Uri.parse("$imageCopyright")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        //Estiliza o texto do author no alertDialog
        val nameImage = SpannableString(binding2.imageName.text)
        val authorImage = SpannableString(binding2.authorImage.text)
        val bold = StyleSpan(Typeface.BOLD)

        authorImage.setSpan(bold, 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        nameImage.setSpan(bold, 0, imageName?.length ?: 0, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding2.authorImage.text = authorImage
        binding2.imageName.text = nameImage

        //Obtém a imagem do país e a exibe no alertDialog
        getImageCountry()?.let {
            binding2.countryImageDialog.setImageResource(it)
        }

        //Configura o botão "OK" no alert Dialog
        mDialog = MaterialAlertDialogBuilder(this)
            .setView(binding2.root)
            .setCancelable(true)
            .create()

        binding2.buttonOk.setOnClickListener {
            mDialog.dismiss()
        }
    }

    /*
    * Obtém o ID da imagem do país passado pela intent.
    * Está como GET para que possa ser reutilizado por outros métodos da classe
     */
    private fun getImageCountry() = intent?.extras?.getInt("imageCountry")
}


