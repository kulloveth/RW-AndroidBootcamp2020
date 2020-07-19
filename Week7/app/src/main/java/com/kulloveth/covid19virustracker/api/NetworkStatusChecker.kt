package com.kulloveth.covid19virustracker.api

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkStatusChecker(private val connectivityManager: ConnectivityManager?) {

    inline fun performIfConnectedToInternet(action: () -> Unit,noInternet:()->Unit) {
        if (hasInternetConnection()) {
            action()
        }else{
            noInternet()
        }
    }

    fun hasInternetConnection(): Boolean {
        val network = connectivityManager?.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
    }
}