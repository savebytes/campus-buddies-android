package com.savebytes.campusbuddy.data.remote.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkLoggerInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val startNs = System.nanoTime()

        // Extract request details
        val requestBody = request.body
        val requestBuffer = Buffer()
        requestBody?.writeTo(requestBuffer)
        val requestBodyStr = requestBuffer.readUtf8()

        Log.d("NetworkLogger", "➡️ Request")
        Log.d("NetworkLogger", "URL: ${request.url}")
        Log.d("NetworkLogger", "Method: ${request.method}")
        if (requestBodyStr.isNotBlank()) {
            Log.d("NetworkLogger", "Request Body: $requestBodyStr")
        }

        // Proceed with the request
        val response = chain.proceed(request)
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        // Extract response body
        val responseBody = response.body
        val contentType = responseBody?.contentType()
        val responseBodyStr = responseBody?.source()?.apply { request(Long.MAX_VALUE) }?.buffer?.clone()?.readString(Charset.forName("UTF-8"))

        Log.d("NetworkLogger", "⬅️ Response (${tookMs}ms)")
        Log.d("NetworkLogger", "URL: ${response.request.url}")
        Log.d("NetworkLogger", "Status Code: ${response.code}")
        if (!responseBodyStr.isNullOrEmpty()) {
            Log.d("NetworkLogger", "Response Body: $responseBodyStr")
        }

        return response
    }
}