package com.example.countriesproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countriesproject.databinding.CountriesMainBinding
import com.example.countriesproject.model.Countries


;

/*
* Adapter personalizado para exibir a lista de países
 */
class AdapterCountries(
    private val context: Context, //contexto da aplicação
    private val countriesList: MutableList<Countries>, // Lista de países a ser exibida
    private val clickAction: (Countries) -> Unit // ação a ser realizada ao clicar em um país
) :
    RecyclerView.Adapter<AdapterCountries.CountryViewHolder>() {

    // Cria uma nova instância do CountryViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val itemList = CountriesMainBinding.inflate(LayoutInflater.from(context), parent, false)
        return CountryViewHolder(itemList)
    }

    // Retorna o número total de itens na lista
    override fun getItemCount() = countriesList.size

    //Vincula os dados de um país ao CountryViewHolder
    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.createCountry(countriesList[position], clickAction)
    }

    //ViewHolder interno para representar a visualização de um país
    inner class CountryViewHolder(private val binding: CountriesMainBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //Método para configurar a visualização com os dados do país
        fun createCountry(country: Countries, clickAction: (Countries) -> Unit) {
            binding.country.text = country.name
            binding.continent.text = country.continentName
            binding.numInhab.text = country.numHab
            binding.moeda.text = country.coin
            binding.city.text = country.city
            binding.languageCity.text = country.language
            binding.sizeCity.text = country.size
            country.flag?.let { binding.flag.setImageResource(it) }
            binding.countryCard.setOnClickListener{clickAction(country)}
        }
    }

}
