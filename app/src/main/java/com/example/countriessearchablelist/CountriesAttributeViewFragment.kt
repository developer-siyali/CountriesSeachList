package com.example.countriessearchablelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.countriessearchablelist.databinding.FragmentCountriesAttributeViewFragmentBinding
import com.example.countriessearchablelist.util.ProgressDialog
import com.example.countriessearchablelist.util.loadSvgOrOthers
import com.example.countriessearchablelist.view.CountriesExpandableListAdapter
import com.example.countriessearchablelist.viewmodel.CountriesViewModel
import org.koin.android.ext.android.inject
import timber.log.Timber


class CountriesAttributeViewFragment : Fragment() {

    private lateinit var bindingAttributesView: FragmentCountriesAttributeViewFragmentBinding
    private val args: CountriesAttributeViewFragmentArgs by navArgs()
    private val countriesViewModel: CountriesViewModel by inject()
    private lateinit var expandableListView: ExpandableListView
    private val adapterCountries: CountriesExpandableListAdapter by inject()
    private val dialog: ProgressDialog by inject()
    private var languageList: MutableList<String> = mutableListOf()
    private var currencyList: MutableList<String> = mutableListOf()
    private var expandableInformationMap: MutableMap<String, List<String>> = mutableMapOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingAttributesView = FragmentCountriesAttributeViewFragmentBinding.inflate(
            layoutInflater,
            container,
            false
        )
        countriesViewModel.setCountryApiResponse(args.countryCode)
        countriesViewModel.countryApiResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                val responseBody = response.body()
                bindingAttributesView.countryFlag.loadSvgOrOthers(responseBody?.flag)
                bindingAttributesView.countryName.text = responseBody?.name
                bindingAttributesView.capitalName.text = responseBody?.capital
                expandableListView = bindingAttributesView.languages
                responseBody?.languages?.filter { language ->
                    languageList.add(language.nativeName)
                }
                responseBody?.currencies?.filter { currency ->
                    val convertToString = "${currency.name};${currency.symbol};${currency.code}"
                    currencyList.add(convertToString)
                }
                val countryList = listOf(
                    requireContext().resources.getString(R.string.language),
                    requireContext().resources.getString(
                        R.string.currency
                    )
                )
                expandableInformationMap[countryList[0]] = languageList
                expandableInformationMap[countryList[1]] = currencyList
                adapterCountries.setData(countryList, expandableInformationMap)
                expandableListView.setAdapter(adapterCountries)
            } else {
                Timber.i("Response error code ${response.code()}, error message ${response.message()}")
            }

        })
        return bindingAttributesView.root
    }


}