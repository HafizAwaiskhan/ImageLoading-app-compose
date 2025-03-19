package com.example.jetpackapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest

object NetworkUtil {
    private var connectivityManager: ConnectivityManager? = null
    private var networkCallback: CustomNetworkCallback? = null

    /**
     * Checks if the device has any active internet connection.
     */
    fun hasInternetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }

    /**
     * Registers a network callback to monitor connectivity changes.
     */
    fun registerNetworkCallback(context: Context, listener: ConnectivityListener) {
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkCallback = CustomNetworkCallback(listener)
        val networkRequest = NetworkRequest.Builder().build()
        connectivityManager?.registerNetworkCallback(networkRequest, networkCallback!!)
    }

    /**
     * Unregisters the network callback.
     */
    fun unregisterNetworkCallback() {
        networkCallback?.let {
            connectivityManager?.unregisterNetworkCallback(it)
            networkCallback = null
        }
    }

    interface ConnectivityListener {
        fun onNetworkAvailable() // Callback when the network becomes available
        fun onLimitedNetwork() // Callback when connected to a limited network
        fun onNetworkLost() // Callback when the network is lost
    }

    private class CustomNetworkCallback(private val listener: ConnectivityListener) : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            // Check if the current network is metered (limited)
            val isLimited = connectivityManager?.isActiveNetworkMetered ?: false
            if (isLimited) {
                listener.onLimitedNetwork()
            } else {
                listener.onNetworkAvailable()

            }
        }

        override fun onLost(network: Network) {
            listener.onNetworkLost()
        }
    }
}
