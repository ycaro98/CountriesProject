package com.example.countriesproject

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.appcompat.app.AppCompatActivity
import com.example.countriesproject.databinding.ActivityAboutBinding

/*
* Esta classe representa a Activity que exibe informações sobre o aplicativo
* */
class AboutActivity : AppCompatActivity() {

    //Binding para a interface de usuário
    private lateinit var binding: ActivityAboutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Infla o layout e configura a view
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuração da ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Configura os links para abrir no navegador
        binding.linkCopyrightFlags.movementMethod = LinkMovementMethod.getInstance()
        binding.linkOtherImages.movementMethod = LinkMovementMethod.getInstance()
        binding.linkCopyrightImage.movementMethod = LinkMovementMethod.getInstance()
    }
}