package com.kulloveth.covid19virustracker.ui.status

import android.content.res.TypedArray
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.kulloveth.covid19virustracker.R
import com.kulloveth.covid19virustracker.data.Injection
import com.kulloveth.covid19virustracker.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : BaseFragment() {
    private var viewModel: StatusViewModel? = null
    val entries = ArrayList<BarEntry>()
    private var totalDeathsTv: TextView? = null
    private var totalRecoveredTv: TextView? = null
    private var totalCasesTv: TextView? = null
    private var newCasesTv: TextView? = null
    private var activeCasesTv: TextView? = null
    private var newDeathsTv: TextView? = null
    private var country: TextView? = null
    private var chart: BarChart? = null


    override fun getLayoutId() = R.layout.fragment_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        app_bar.title = "Status Detail"
        app_bar.setNavigationOnClickListener {
            requireView().findNavController()
                .navigate(R.id.action_detailsFragment_to_statusFragment)
        }
        viewModel = ViewModelProvider(requireActivity(), Injection.provideViewModelFactory()).get(
            StatusViewModel::class.java
        )
        // flagIv = country_flag
        totalDeathsTv = total_deaths_detail
        totalRecoveredTv = total_recovered_detail
        totalCasesTv = total_cases_detail
        newCasesTv = new_cases_detail
        activeCasesTv = active_cases_detail
        newDeathsTv = new_death_detail
        country = country_name
        chart = chart1

        setUpDetails()
    }

    fun setUpDetails() {
        viewModel?.sstatusLiveData?.observe(requireActivity(), Observer {
            entries.clear()
            entries.apply {
                it?.let {
                    mapToBarChart(it.total_cases, 0)
                    mapToBarChart(it.total_recovered, 1)
                    mapToBarChart(it.total_deaths, 2)
                    mapToBarChart(it.new_cases, 3)
                    mapToBarChart(it.active_cases, 4)
                    mapToBarChart(it.new_deaths, 5)
                }
            }

            val dataSet = BarDataSet(entries, "Case Status")
            dataSet.color = fetchPrimaryColor()
            val label = arrayListOf<String>()
            label.add("Total Cases")
            label.add("Total Recovered")
            label.add("Total Deaths")
            label.add("New Cases")
            label.add("Active Cases")
            label.add("New Death")


            val barData = BarData(label, dataSet)
            chart?.apply {
                data = barData
                setDescription("Status by Country")
                animateY(5000)
            }



            totalDeathsTv?.text = it.total_deaths
            totalRecoveredTv?.text = it.total_recovered
            totalCasesTv?.text = it.total_cases
            country?.text = it.country
            newCasesTv?.text = it.new_cases
            activeCasesTv?.text = it.active_cases
            newDeathsTv?.text = it.new_deaths
        })
    }

    //creat barchart entries
    fun mapToBarChart(number: String?, position: Int) {
        number?.replace(",", "")?.toFloat()?.let { it ->
            BarEntry(it, position)
        }?.also {
            entries.add(it)
        }
    }

    //get primary color from attr
    private fun fetchPrimaryColor(): Int {
        val typedValue = TypedValue()
        var color = 0
       if (isAdded) {
            val  a = requireActivity().obtainStyledAttributes(
                    typedValue.data,
                    intArrayOf(R.attr.colorPrimary)
                )
            color = a.getColor(0, 0)
           a.recycle()
        }
        return color
    }


}
