package com.kulloveth.covid19virustracker.ui.status

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.kulloveth.covid19virustracker.R
import com.kulloveth.covid19virustracker.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailsFragment : BaseFragment() {
    private val viewModel: StatusViewModel by viewModel()
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
                    mapToBarChart(it.cases.toString(), 0)
                    mapToBarChart(it.recovered.toString(), 1)
                    mapToBarChart(it.deaths.toString(), 2)
                    mapToBarChart(it.todayCases.toString(), 3)
                    mapToBarChart(it.active.toString(), 4)
                    mapToBarChart(it.critical.toString(), 5)
                }
            }

            val dataSet = BarDataSet(entries, "Case Status")
            dataSet.color = fetchPrimaryColor()
            val label = arrayListOf<String>()
            label.add("Cases")
            label.add("Recovered")
            label.add("Deaths")
            label.add("Today Cases")
            label.add("Active")
            label.add("Critical")


            val barData = BarData(label, dataSet)
            chart?.apply {
                data = barData
                setDescription("Status by Country")
                animateY(5000)
            }



            totalDeathsTv?.text = it.deaths.toString()
            totalRecoveredTv?.text = it.recovered.toString()
            totalCasesTv?.text = it.cases.toString()
            country?.text = it.country
            newCasesTv?.text = it.todayCases.toString()
            activeCasesTv?.text = it.active.toString()
            newDeathsTv?.text = it.critical.toString()
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
