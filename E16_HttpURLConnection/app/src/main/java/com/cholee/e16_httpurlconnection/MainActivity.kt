package com.cholee.e16_httpurlconnection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.tv)

        var responseBody: String

        val url = "https://developer.android.com/reference/java/net/HttpURLConnection"
        val requestMethod = "GET"
        val httpConnectThread = Thread() {
            responseBody = httpUrlConnection(url, requestMethod)
            runOnUiThread {
                tv.textSize = 10F
                tv.text = responseBody
            }
        }

        httpConnectThread.start()
    }

    private fun httpUrlConnection(url: String, requestMethod: String): String {
        val url = URL(url)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = requestMethod
        var connectionCode = connection.responseCode

        if(connectionCode == HttpURLConnection.HTTP_OK) {
            val inputStream = connection.inputStream
            val reader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(reader)
            val response = StringBuffer()

            var inputLine: String?
            var responseBody: String?

            while (bufferedReader.readLine().also { inputLine = it } != null) {
                response.append(inputLine)
            }

            reader.close()
            connection.disconnect()
            responseBody = response.toString()

            Log.d("Connection Success!", responseBody)
            return responseBody

        } else {
            Log.e("Connection Error..", connectionCode.toString())
            return "Connection Error"
        }

    }
}