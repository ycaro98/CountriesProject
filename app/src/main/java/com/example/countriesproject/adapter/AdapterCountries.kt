package com.example.countriesproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countriesproject.databinding.CountriesMainBinding
import com.example.countriesproject.model.Countries

class AdapterCountries(
    private val context: Context,
    private val countriesList: MutableList<Countries>,
    private val clickAction: (Countries) -> Unit
) :
    RecyclerView.Adapter<AdapterCountries.CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val itemList = CountriesMainBinding.inflate(LayoutInflater.from(context), parent, false)
        return CountryViewHolder(itemList)
    }

    override fun getItemCount() = countriesList.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.createCountry(countriesList[position], clickAction)
    }

    inner class CountryViewHolder(private val binding: CountriesMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun createCountry(country: Countries, clickAction: (Countries) -> Unit) {
            binding.country.text = country.name
            binding.continent.text = country.continentName
            binding.numInhab.text = country.numHab
            binding.moeda.text = country.coin
            binding.city.text = country.city
            binding.languageCity.text = country.language
            binding.sizeCity.text = country.size
            // country.flag.setImageResource(country.flag)
            country.flag?.let { binding.flag.setImageResource(it) }
            binding.countryCard.setOnClickListener{clickAction(country)}
        }
    }

}
