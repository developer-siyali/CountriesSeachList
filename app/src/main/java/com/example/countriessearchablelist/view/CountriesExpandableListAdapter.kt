package com.example.countriessearchablelist.view

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.example.countriessearchablelist.R
import com.example.countriessearchablelist.databinding.CurrencyItemBinding
import com.example.countriessearchablelist.databinding.GroupItemBinding
import com.example.countriessearchablelist.databinding.LanguageItemBinding

class CountriesExpandableListAdapter: BaseExpandableListAdapter() {

    private var informationList: Map<String, List<String>> = emptyMap()
    private var groupItems: List<String> = emptyList()

    override fun getGroupCount(): Int {
        return groupItems.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return informationList[groupItems[groupPosition]]?.size ?: 0
    }

    override fun getGroup(groupPosition: Int): Any {
        return groupItems[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any? {
        return informationList[groupItems[groupPosition]]?.get(childPosition)
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val bindingGroupItem = GroupItemBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        bindingGroupItem.groupViewTitle.setTypeface(null, Typeface.BOLD)
        bindingGroupItem.groupViewTitle.text = groupItems[groupPosition]
        return bindingGroupItem.root
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        return if (groupPosition == 0) {
            val bindingChildItem = LanguageItemBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
            bindingChildItem.languageItem.setTypeface(null, Typeface.NORMAL)
            bindingChildItem.languageItem.text = informationList[groupItems[groupPosition]]?.get(childPosition)
            bindingChildItem.root
        }else {
            val bindingChildItem = CurrencyItemBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
            val splitCurrencyInfo = informationList[groupItems[groupPosition]]?.get(childPosition)?.split(";")
            bindingChildItem.currencyCode.setTypeface(null, Typeface.BOLD)
            bindingChildItem.currencyCode.text = parent?.context?.resources?.getString(R.string.currency_details, "Code: \t${splitCurrencyInfo?.get(2).toString()}")
            bindingChildItem.currencyName.setTypeface(null, Typeface.BOLD)
            bindingChildItem.currencyName.text = parent?.context?.resources?.getString(R.string.currency_details, "Currency Name: \t${splitCurrencyInfo?.get(0).toString()}")
            bindingChildItem.currencySymbol.setTypeface(null, Typeface.BOLD)
            bindingChildItem.currencySymbol.text = parent?.context?.resources?.getString(R.string.currency_details, "Currency Symbol: \t${splitCurrencyInfo?.get(1).toString()}")
            bindingChildItem.root
        }
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    fun setData(groupItemsList: List<String>, childItemInfoList: Map<String, List<String>>) {
        this.groupItems = groupItemsList
        this.informationList = childItemInfoList
        notifyDataSetChanged()
    }
}