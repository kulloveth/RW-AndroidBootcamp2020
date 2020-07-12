package com.kulloveth.covid19virustracker.ui.status

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kulloveth.covid19virustracker.data.Injection
import com.kulloveth.covid19virustracker.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details.*

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsFragment : Fragment() {
    private var viewModel: StatusViewModel? = null
    private var flagIv: ImageView? = null
    private var totalDeathsTv:TextView?=null
    private var totalRecoveredTv:TextView?=null
    private var totalCasesTv:TextView?=null
    private var newCasesTv:TextView?=null
    private var activeCasesTv:TextView?=null
    private var newDeathsTv:TextView?=null
    private var country:TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), Injection.provideViewModelFactory()).get(
            StatusViewModel::class.java
        )
        flagIv = country_flag
        totalDeathsTv = total_deaths_detail
        totalRecoveredTv=total_recovered_detail
        totalCasesTv=total_cases_detail
        newCasesTv = new_cases_detail
        activeCasesTv = active_cases_detail
        newDeathsTv = new_death_detail
        country = country_name

        setUpDetails()
    }

    fun setUpDetails() {
        viewModel?.sstatusLiveData?.observe(requireActivity(), Observer {
            Picasso.get().load(it.flag).error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_background).into(flagIv)
            totalDeathsTv?.text = it.total_deaths
            totalRecoveredTv?.text = it.total_recovered
            totalCasesTv?.text = it.total_cases
            country?.text = it.country
            newCasesTv?.text = it.new_cases
            activeCasesTv?.text = it.active_cases
            newDeathsTv?.text = it.new_deaths
        })
    }
}
