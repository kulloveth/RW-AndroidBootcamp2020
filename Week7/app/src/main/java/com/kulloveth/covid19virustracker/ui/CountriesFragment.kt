package com.kulloveth.covid19virustracker.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.kulloveth.covid19virustracker.Injection
import com.kulloveth.covid19virustracker.R
import com.kulloveth.covid19virustracker.model.CountryStatus
import com.kulloveth.covid19virustracker.ui.adapter.StatusAdapter
import com.kulloveth.covid19virustracker.ui.adapter.StatusLoadStateAdapter
import kotlinx.android.synthetic.main.fragment_countries.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 */
class CountriesFragment : Fragment(), StatusAdapter.StatusITemListener {

    private val TAG = CountriesFragment::class.java.simpleName
    private var viewModel: StatusViewModel? = null
    val adapter = StatusAdapter(this)
    private var statusRv: RecyclerView? = null
    private var progress:ProgressBar? = null

    private var statusJob: Job? = null
//    private val networkStatusChecker by lazy {
//        NetworkStatusChecker(requireActivity().getSystemService(ConnectivityManager::class.java))
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_countries, container, false)


    }

    private fun getStatus() {
        statusJob?.cancel()
        statusJob = lifecycleScope.launch {
            viewModel?.getStatus()?.collectLatest {
                Log.d(TAG, "$it")
                adapter.submitData(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        statusRv = list
        progress = progress_bar
        viewModel = ViewModelProvider(
            requireActivity(),
            Injection.provideViewModelFactory()
        ).get(StatusViewModel::class.java)


        initAdapter()
        getStatus()

    }


    private fun initAdapter() {
        statusRv?.adapter = adapter.withLoadStateHeaderAndFooter(
            header = StatusLoadStateAdapter { adapter.retry() },
            footer = StatusLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            statusRv?.isVisible = loadState.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            progress?.isVisible = loadState.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            // retry_button.isVisible = loadState.refresh is LoadState.Error

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.no_internet),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }

    override fun onStatusListener(countryStatus: CountryStatus) {
        viewModel?.setUpStatus(countryStatus)
        requireView().findNavController().navigate(R.id.action_countriesFragment_to_detailsFragment)
    }


}
