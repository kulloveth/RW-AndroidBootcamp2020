package com.kulloveth.covid19virustracker.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kulloveth.covid19virustracker.Injection
import com.kulloveth.covid19virustracker.R
import com.kulloveth.covid19virustracker.model.CountryStatus
import com.kulloveth.covid19virustracker.ui.StatusViewModel
import com.kulloveth.covid19virustracker.ui.adapter.StatusAdapter
import com.kulloveth.covid19virustracker.utils.ProgressListener
import kotlinx.android.synthetic.main.fragment_countries.*


/**
 * A simple [Fragment] subclass.
 */
class CountriesFragment : Fragment(), StatusAdapter.StatusITemListener, ProgressListener {

    private val TAG = CountriesFragment::class.java.simpleName
    private var viewModel: StatusViewModel? = null
    val adapter = StatusAdapter(this)
    private var statusRv: RecyclerView? = null
    private var progress: ProgressBar? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_countries, container, false)


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        statusRv = list
        progress = progress_bar
        statusRv?.adapter = adapter
        viewModel = ViewModelProvider(
            requireActivity(),
            Injection.provideViewModelFactory()
        ).get(StatusViewModel::class.java)
        viewModel?.setUpProgress(this)

        getStatus()

    }

    private fun getStatus() {
        viewModel?.getStatus()?.observe(requireActivity(), Observer {
            adapter.submitList(it)
        })
    }


    override fun onStatusListener(countryStatus: CountryStatus) {
        viewModel?.setUpStatus(countryStatus)
        requireView().findNavController().navigate(R.id.action_countriesFragment_to_detailsFragment)
    }

    override fun loading() {
       progress?.visibility = View.VISIBLE
        statusRv?.visibility = View.INVISIBLE
    }

    override fun success() {
        progress?.visibility = View.INVISIBLE
        statusRv?.visibility = View.VISIBLE
    }


}
