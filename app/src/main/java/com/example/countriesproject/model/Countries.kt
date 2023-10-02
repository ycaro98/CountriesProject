package com.example.countriesproject.model

/*
* Define uma data class chamada Countries para representar informações sobre países
* */
data class Countries(
    val name: String? = null,
    val continentName: String? = null,
    val numHab: String? = null,
    val coin: String? = null,
    val city: String? = null,
    val language: String? = null,
    val size: String? = null,
    val flag: Int? = null,
    val imageCountry: Int? = null,
    val imageAuthor: String? = null,
    val imageCopyright: String? = null,
    val imageName: String? = null,
    val description: String? = null
)
