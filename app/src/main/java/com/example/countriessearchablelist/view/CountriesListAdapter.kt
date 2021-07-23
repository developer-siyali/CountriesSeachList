package com.example.countriessearchablelist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.countriessearchablelist.databinding.AdapterCountryRowBinding
import com.example.countriessearchablelist.model.CountriesAttributes
import com.example.countriessearchablelist.util.loadSvgOrOthers


class CountriesListAdapter: RecyclerView.Adapter<CountriesListAdapter.CountriesListViewHolder>() {
    private lateinit var bindingAdapterCountyRow: AdapterCountryRowBinding
    private var countriesAttributes: CountriesAttributes = CountriesAttributes(emptyList(), emptyList())

    inner class CountriesListViewHolder(private val binding: AdapterCountryRowBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(name: String, flagUrl: String) {
            val countryFlag: ImageView = binding.countryFlag
            val countryName: TextView = binding.countryName

            countryName.text = name
            countryFlag.loadSvgOrOthers(flagUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesListViewHolder {
        bindingAdapterCountyRow = AdapterCountryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountriesListViewHolder(bindingAdapterCountyRow)
    }

    override fun onBindViewHolder(holder: CountriesListViewHolder, position: Int) {
        if (position < itemCount) {
            holder.bind(countriesAttributes.countryNames[position], countriesAttributes.countryFlags[position])
        }
    }

    override fun getItemCount(): Int {
        return countriesAttributes.countryNames.size
    }

    fun setData(countriesAttributes: CountriesAttributes) {
        this.countriesAttributes = countriesAttributes
        notifyDataSetChanged()
    }
}