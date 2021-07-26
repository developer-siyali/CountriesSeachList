package com.example.countriessearchablelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countriessearchablelist.databinding.FragmentCountriesListBinding
import com.example.countriessearchablelist.util.ProgressDialog
import com.example.countriessearchablelist.view.CountriesListAdapter
import com.example.countriessearchablelist.view.ItemOnClickListener
import com.example.countriessearchablelist.viewmodel.CountriesViewModel
import org.koin.android.ext.android.inject

class CountriesListFragment : Fragment(), ItemOnClickListener {

    lateinit var bindingCountriesListFragment: FragmentCountriesListBinding
    private val countriesViewModel: CountriesViewModel by inject()
    private val dialog: ProgressDialog by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingCountriesListFragment = FragmentCountriesListBinding.inflate(
            layoutInflater,
            container,
            false
        )
        val recyclerView = bindingCountriesListFragment.recyclerView
        val adapter = CountriesListAdapter(this)
        countriesViewModel.countriesAttributes.observe(viewLifecycleOwner, {
            adapter.setData(it, dialog)
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        return bindingCountriesListFragment.root
    }

    override fun onClickListener(countryCode: String) {
        val action = CountriesListFragmentDirections.viewCountriesListFragmentNavigateToCountriesAttributeViewFragment(
            countryCode
        )
        view?.let { Navigation.findNavController(it).navigate(action) }
    }

}