package com.example.appdev1.data.model

import android.provider.ContactsContract.CommonDataKinds.Website
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

class WebViewRepository {

        fun fetchData(callback: (Result<String>) -> Unit) {
            val request = Request.Builder()
                .url("https://uzako.site/G4HVkV")
                .build()
            val client = OkHttpClient().newBuilder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    callback(Result.failure(e))
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.code == 404) {
                        callback(Result.failure(IOException(response.message)))
                    } else {
                        callback(Result.success(response.message))
                    }
                }
            })
        }
}