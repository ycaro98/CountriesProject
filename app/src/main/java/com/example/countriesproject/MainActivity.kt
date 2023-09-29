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

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapterCountries: AdapterCountries
    private var listCountries: MutableList<Countries> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerViewCountries = binding.recycler
        recyclerViewCountries.layoutManager = LinearLayoutManager(this)
        recyclerViewCountries.setHasFixedSize(true)
//        itemList()

        listCountries = getFileObjectList(readFile())

        //faz um imageView responder como button na view e adiciona cria um gancho de item para a tela DetailsActivity
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
            intent.putExtra("description",it.description)

            startActivity(intent)
        }
        recyclerViewCountries.adapter = adapterCountries

        //cria um intent button para o botão sobre
        binding.sobre.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }


    }

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

    private fun getFileObjectList(content: String): MutableList<Countries> {
        val countriesList = mutableListOf<Countries>()
        val portions = content.split("#").filter { it.isNotBlank() }

        for (p in portions) {
            val lines = p.split("\n").filter { it.isNotBlank() }

            val nameValue = lines[10].split(": ")[1]
            val continentValue =  lines[2].split(": ")[1]
            val numHabValue =  lines[11].split(": ")[1]
            val coinValue =  lines[3].split(": ")[1]
            val cityValue =  lines[1].split(": ")[1]
            val languageValue =  lines[9].split(": ")[1]
            val sizeValue =  lines[0].split(": ")[1]
            val flagValue = resources.getIdentifier(getImageName(lines[5].split(": ")[1]), "drawable",packageName)
            val imageCountryValue = resources.getIdentifier(getImageName(lines[6].split(": ")[1]), "drawable",packageName)
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

    private fun getImageName(image: String): String{
        return image.replace('-','_').substring(0,image.indexOf('.'))

    }


//    private fun itemList() {
//        val country1 = Countries(
//            "Brazil",
//            "South America",
//            "2 817 068",
//            "Real",
//            "Brasilia",
//            "Portuguese",
//            "5.779 km² ",
//            R.drawable.brazil,
//            R.drawable.bra,
//            "Revol Web",
//            "https://www.flickr.com/photos/revolweb/15956587634",
//            "Ilha do Campeche - Santa Catarina, Brazil"
//        )
//        listCountries.add(country1)
//
//        val country2 = Countries(
//            "England",
//            "Europa",
//            "8 982 000",
//            "Libra",
//            "London",
//            "English",
//            "1.572 km²",
//            R.drawable.united_kingdom,
//            R.drawable.uk,
//            "Giuseppe Milo",
//            "https://www.flickr.com/photos/giuseppemilo/45679070022",
//            "Tower bridge - London, United Kingdom"
//        )
//        listCountries.add(country2)
//
//        val country3 =
//            Countries(
//                "China",
//                "Asia",
//                "21 000 054",
//                "Reiminbi",
//                "Pequim",
//                "Mandarim",
//                "16.411 km²",
//                R.drawable.china,
//                R.drawable.chn,
//                "Diego Aviles",
//                "https://www.flickr.com/photos/diegoaviles/9521940433",
//                "Great Wall of China"
//
//            )
//        listCountries.add(country3)
//
//        listCountries.sortBy { it.name }
//    }
}