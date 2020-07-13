package com.kulloveth.covid19virustracker.ui.status

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kulloveth.covid19virustracker.R
import com.kulloveth.covid19virustracker.data.Injection
import com.kulloveth.covid19virustracker.model.CountryStatus
import com.kulloveth.covid19virustracker.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_status.*


/**
 * A simple [Fragment] subclass.
 */
class StatusFragment : BaseFragment(), StatusAdapter.StatusITemListener {

    private val TAG = StatusFragment::class.java.simpleName
    private var viewModel: StatusViewModel? = null
    val adapter = StatusAdapter(this)
    private var statusRv: RecyclerView? = null
    private var progress: ProgressBar? = null


    override fun getLayoutId() = R.layout.fragment_status


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        app_bar.title = getString(R.string.covid_status)
        statusRv = list
        progress = progress_bar
        statusRv?.adapter = adapter
        viewModel = ViewModelProvider(
            requireActivity(),
            Injection.provideViewModelFactory()
        ).get(StatusViewModel::class.java)

        getStatus()
    }

    //observe status from livedata
    private fun getStatus() {
        viewModel?.getStatus()?.observe(requireActivity(), Observer {
            adapter.submitList(it)
            statusRv?.visibility = View.VISIBLE
            progress?.visibility = View.INVISIBLE
        })
    }


    override fun onStatusListener(countryStatus: CountryStatus) {
        viewModel?.setUpStatus(countryStatus)
        requireView().findNavController().navigate(R.id.action_countriesFragment_to_detailsFragment)
    }




}
