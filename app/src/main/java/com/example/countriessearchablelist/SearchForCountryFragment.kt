package com.example.countriessearchablelist

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
import com.example.countriessearchablelist.view.CountriesListAdapter
import com.example.countriessearchablelist.viewmodel.CountriesViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class SearchForCountryFragment : Fragment() {
    private val countriesViewModel by viewModel<CountriesViewModel>()
    private var countryNamesList: MutableList<String> = mutableListOf()
    private var countryFlagsList: MutableList<String> = mutableListOf()
    private val adapter: CountriesListAdapter by inject()

    lateinit var bindingSearchForCountryFragment: FragmentSearchForCountryBinding
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
                }
                countriesViewModel.setCountryNamesList(
                    CountriesAttributes(
                        countryNamesList,
                        countryFlagsList
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
        viewAllCountriesOnClick()


        return bindingSearchForCountryFragment.root
    }

    private fun enableSearchButton(userInput: String, countryNamesList: List<String>) {
        bindingSearchForCountryFragment.searchButton.isClickable = userInput in countryNamesList
    }

    private fun viewAllCountriesOnClick() {
        bindingSearchForCountryFragment.viewAllCountriesButton.setOnClickListener { view ->
            countriesViewModel.countriesAttributes.observe(viewLifecycleOwner, { countriesAttributes ->
                context?.let { adapter.setData(countriesAttributes) }
            })

            Navigation.findNavController(view).navigate(R.id.navigate_to_view_countries_list_fragment)
        }
    }
}