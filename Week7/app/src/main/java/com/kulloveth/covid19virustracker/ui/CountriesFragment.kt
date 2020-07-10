package com.kulloveth.covid19virustracker.ui

import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.kulloveth.covid19virustracker.App
import com.kulloveth.covid19virustracker.R
import com.kulloveth.covid19virustracker.api.NetworkStatusChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 */
class CountriesFragment : Fragment() {

    private val TAG = CountriesFragment::class.java.simpleName
    private val networkStatusChecker by lazy {
        NetworkStatusChecker(requireActivity().getSystemService(ConnectivityManager::class.java))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_countries, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        networkStatusChecker.performIfConnectedToInternet {
            lifecycleScope.launch(Dispatchers.Main) {
                val res = App.remoteApi.getStatusByCountry()
                Log.d(TAG, "coro is ${res.data}")

            }
        }
    }

}
