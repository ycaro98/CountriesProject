package com.example.countriesproject.adapter

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countriesproject.databinding.CountriesMainBinding
import com.example.countriesproject.model.Countries


;
import java.text.DecimalFormat

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
            binding.numInhab.text = country.numHab?.addSpaceSeparator()
            binding.moeda.text = country.coin
            binding.city.text = country.city
            binding.languageCity.text = country.language
            binding.sizeCity.text = country.size
            country.flag?.let { binding.flag.setImageResource(it) }
            binding.countryCard.setOnClickListener { clickAction(country) }

            //adiciona negrito ao nome do país na CardView
            val bold = StyleSpan(Typeface.BOLD)
            val nameCountry = SpannableString(country.name)
            nameCountry.setSpan(bold, 0, nameCountry.length?:0, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.country.text = nameCountry

        }

        //metodo que retorna um espaçamento a cada 3 caracteres
        fun String.addSpaceSeparator():String {
            val regex = "(\\d)(?=(\\d{3})+\$)".toRegex()
            return this.replace(regex, "\$1 ")
        }
    }
}
