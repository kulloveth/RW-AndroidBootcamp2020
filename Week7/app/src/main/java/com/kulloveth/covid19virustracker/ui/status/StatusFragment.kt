package com.kulloveth.covid19virustracker.ui.status

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kulloveth.covid19virustracker.R
import com.kulloveth.covid19virustracker.data.db.StatusEntity
import com.kulloveth.covid19virustracker.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_status.*
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 */
class StatusFragment : BaseFragment(), StatusAdapter.StatusITemListener {

    private val TAG = StatusFragment::class.java.simpleName
    private val viewModel: StatusViewModel by viewModel()
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
        getStatus()
    }

    //observe status from livedata
    @FlowPreview
    private fun getStatus() {
        viewModel.getNewStatus().observe(requireActivity(), Observer {
            Log.d(TAG,"$it")
            adapter.submitList(it)
            statusRv?.visibility = View.VISIBLE
            progress?.visibility = View.INVISIBLE
        })
    }


    override fun onStatusListener(statusEntity: StatusEntity) {
        viewModel.setUpStatus(statusEntity)
        requireView().findNavController().navigate(R.id.action_countriesFragment_to_detailsFragment)
    }




}
