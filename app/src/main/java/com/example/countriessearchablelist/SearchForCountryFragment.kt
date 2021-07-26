package com.example.countriessearchablelist

import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.countriessearchablelist.databinding.FragmentSearchForCountryBinding
import com.example.countriessearchablelist.model.CountriesAttributes
import com.example.countriessearchablelist.util.ProgressDialog
import com.example.countriessearchablelist.util.makeViewScrollable
import com.example.countriessearchablelist.viewmodel.CountriesViewModel
import org.koin.android.ext.android.inject
import timber.log.Timber


class SearchForCountryFragment : Fragment(){
    private val countriesViewModel: CountriesViewModel by inject()
    private var countryNamesList: MutableList<String> = mutableListOf()
    private var countryFlagsList: MutableList<String> = mutableListOf()
    private var countryCodesList: MutableList<String> = mutableListOf()
    private lateinit var bindingSearchForCountryFragment: FragmentSearchForCountryBinding
    private val dialog: ProgressDialog by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingSearchForCountryFragment = FragmentSearchForCountryBinding.inflate(
            layoutInflater,
            container,
            false
        )

        countriesViewModel.setCountriesApiResponse()
        countriesViewModel.countriesListApiResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                response.body()?.filter { country ->
                    countryNamesList.add(country.name)
                    countryFlagsList.add(country.flag)
                    countryCodesList.add(country.alpha3Code)
                }
                countriesViewModel.setCountryAttributesModel(
                    CountriesAttributes(
                        countryNamesList,
                        countryFlagsList,
                        countryCodesList
                    )
                )
                val listAdapter = ArrayAdapter(
                    requireContext(),
                    R.layout.adapter_county_name,
                    countryNamesList.toTypedArray()
                )
                bindingSearchForCountryFragment.autoCompleteView.setAdapter(listAdapter)
                bindingSearchForCountryFragment.autoCompleteView.addTextChangedListener(object :
                    TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        enableSearchButton(s.toString(), countryNamesList)
                    }

                    override fun afterTextChanged(s: Editable?) {
                    }

                })
            } else {
                Timber.i("Response error code ${response.code()}, error message ${response.message()}")
            }
        })
        goButtonOnclick()
        viewAllCountriesOnClick(activity as MainActivity)

        return bindingSearchForCountryFragment.root
    }

    private fun enableSearchButton(userInput: String, countryNamesList: List<String>) {
        bindingSearchForCountryFragment.searchButton.isClickable = userInput in countryNamesList
    }

    private fun viewAllCountriesOnClick(activity: MainActivity) {
        bindingSearchForCountryFragment.viewAllCountriesButton.setOnClickListener { view ->
            activity.let { dialog.showProgressBar(it) }
            dialog.showDialog()
            Navigation.findNavController(view).navigate(R.id.search_input_fragment_navigate_to_view_countries_list_fragment)
        }
    }

    private fun goButtonOnclick(){
        bindingSearchForCountryFragment.searchButton.setOnClickListener {
            val indexOfSearchCountry = countryNamesList.indexOf(bindingSearchForCountryFragment.autoCompleteView.text.toString())
            val action = SearchForCountryFragmentDirections.searchInputFragmentNavigateToCountriesAttributeViewFragment(countryCodesList[indexOfSearchCountry])
            Navigation.findNavController(it).navigate(action)
        }
    }
}