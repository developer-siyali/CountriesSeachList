package com.example.countriessearchablelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countriessearchablelist.databinding.FragmentCountriesListBinding
import com.example.countriessearchablelist.view.CountriesListAdapter
import org.koin.android.ext.android.inject

class CountriesListFragment : Fragment() {

    lateinit var bindingCountriesListFragment: FragmentCountriesListBinding
    private val adapter: CountriesListAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingCountriesListFragment = FragmentCountriesListBinding.inflate(layoutInflater, container, false)
        val recyclerView = bindingCountriesListFragment.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        return bindingCountriesListFragment.root
    }
}