package com.example.countriesproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countriesproject.databinding.ActivityMainBinding
import com.example.countriesproject.model.Countries
import com.example.countriesproject.adapter.AdapterCountries
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

/*
* Esta é a classe principal que representa a tela inicial do aplicativo
* */
class MainActivity : AppCompatActivity() {

    // Binding para a interface de usuário
    private lateinit var binding: ActivityMainBinding

    //  Adaptador para a lista de países
    private lateinit var adapterCountries: AdapterCountries

    // Lista de países
    private var listCountries: MutableList<Countries> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Infla o layout e configura a view
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuração do RecyclerView
        val recyclerViewCountries = binding.recycler
        recyclerViewCountries.layoutManager = LinearLayoutManager(this)
        recyclerViewCountries.setHasFixedSize(true)

        // Lê o conteúdo do arquivo e popula a lista de países
        listCountries = getFileObjectList(readFile())

        //Configuração do Adapter para o RecyclerView
        adapterCountries = AdapterCountries(this, listCountries) {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("countryName", it.name)
            intent.putExtra("continentName", it.continentName)
            intent.putExtra("cityName", it.city)
            intent.putExtra("languageName", it.language)
            intent.putExtra("numHab", it.numHab)
            intent.putExtra("geoExtension", it.size)
            intent.putExtra("currency", it.coin)
            intent.putExtra("imageCountry", it.imageCountry)
            intent.putExtra("imageAuthor", it.imageAuthor)
            intent.putExtra("imageCopyright", it.imageCopyright)
            intent.putExtra("imageName", it.imageName)
            intent.putExtra("description", it.description)

            startActivity(intent)
        }
        recyclerViewCountries.adapter = adapterCountries

        //cria um intent para o botão "Sobre"
        binding.sobre.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }

    /*
    * Lê o conteúdo do arquvio "info.txt" e retorna como uma String
    * */
    private fun readFile(): String {
        val stream = assets.open("info.txt")
        val br = BufferedReader(InputStreamReader(stream, StandardCharsets.UTF_8))
        val builder = StringBuilder()

        while (true) {
            val str = br.readLine() ?: break
            builder.append(str).append("\n")
        }

        br.close()

        return builder.toString()
    }

    /*
    * Converte o conteúdo do arquivo "info.txt" em uma lista de objetos[Countries]
    */
    private fun getFileObjectList(content: String): MutableList<Countries> {
        val countriesList = mutableListOf<Countries>()
        val portions = content.split("#").filter { it.isNotBlank() }

        for (p in portions) {
            val lines = p.split("\n").filter { it.isNotBlank() }

            val nameValue = lines[10].split(": ")[1]
            val continentValue = lines[2].split(": ")[1]
            val numHabValue = lines[11].split(": ")[1]
            val coinValue = lines[3].split(": ")[1]
            val cityValue = lines[1].split(": ")[1]
            val languageValue = lines[9].split(": ")[1]
            val sizeValue = lines[0].split(": ")[1]
            val flagValue = resources.getIdentifier(
                getImageName(lines[5].split(": ")[1]), "drawable", packageName
            )
            val imageCountryValue = resources.getIdentifier(
                getImageName(lines[6].split(": ")[1]), "drawable", packageName
            )
            val imageAuthorValue = lines[7].split(": ")[1]
            val imageCopyrightValue = lines[12].split(": ")[1]
            val imageNameValue = lines[8].split(": ")[1]
            val descriptionValue = lines[4].split(": ")[1]

            val countriesObject = Countries(
                name = nameValue,
                continentName = continentValue,
                numHab = numHabValue,
                coin = coinValue,
                city = cityValue,
                language = languageValue,
                size = sizeValue,
                flag = flagValue,
                imageCountry = imageCountryValue,
                imageAuthor = imageAuthorValue,
                imageCopyright = imageCopyrightValue,
                imageName = imageNameValue,
                description = descriptionValue
            )
            countriesList.add(countriesObject)
        }
        return countriesList
    }

    /*
    *Obtém o nome da imagem a partir de um determinado caminho e muda o caracter selecionado
     */
    private fun getImageName(image: String): String {
        return image.replace('-', '_').substring(0, image.indexOf('.'))
    }
}