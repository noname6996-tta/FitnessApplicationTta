package com.tta.fitnessapplication.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import java.io.IOException

class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        // Log the request method, URL, and headers
        var requestLog = """
            Request Method: ${request.method}
            URL: ${request.url}
            Headers: ${request.headers}
        """.trimIndent()

        // Log the request body if it exists
        val requestBody = request.body
        if (requestBody != null) {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            val requestBodyLog = buffer.readUtf8()
            requestLog += "\nRequest Body: $requestBodyLog"
        }

        // Log the request
        // TODO: You can use any logging library of your choice here, for this example, we'll use Timber
        Log.e("api", requestLog)
        // Proceed with the request
        val response = chain.proceed(request)

        // Log the response
        // TODO: You can log the response here if needed

        return response
    }
}
