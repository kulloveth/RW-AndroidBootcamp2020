package com.kulloveth.covid19virustracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kulloveth.covid19virustracker.Injection
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
    private var deathsTv:TextView?=null
    private var recoveredTv:TextView?=null
    private var confirmedTv:TextView?=null
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
        deathsTv = deaths_detail
        recoveredTv=recovered_detail
        confirmedTv=confirmed_detail
        country = country_name
        setUpDetails()
    }

    fun setUpDetails() {
        viewModel?.sstatusLiveData?.observe(requireActivity(), Observer {
            Picasso.get().load(it.flag).error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_background).into(flagIv)
            deathsTv?.text = it.total_deaths
            recoveredTv?.text = it.total_recovered
            confirmedTv?.text = it.total_cases
            country?.text = it.country
        })
    }
}
