package ru.mvrlrd.core_impl.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import javax.inject.Inject

class InternetController @Inject constructor(
    private val context: Context
) {
    fun isInternetAvailable(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        capabilities?.let {
            with(it) {
                return hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            }
        }
        return false
    }
}