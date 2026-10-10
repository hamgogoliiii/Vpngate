package com.example.data.remote

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

interface VpnGateApi {
    @GET
    suspend fun getVpnServersCsv(@Url url: String): Response<ResponseBody>

    companion object {
        val MIRROR_URLS = listOf(
            "https://www.vpngate.net/api/iphone/",
            "http://www.vpngate.net/api/iphone/",
            "https://api.vpngate.net/api/iphone/",
            "http://219.100.37.243/api/iphone/"
        )

        fun create(): VpnGateApi {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(12, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .followRedirects(true)
                .followSslRedirects(true)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.vpngate.net/")
                .client(okHttpClient)
                .build()

            return retrofit.create(VpnGateApi::class.java)
        }
    }
}
