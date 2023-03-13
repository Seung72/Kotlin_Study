package com.cholee.e17_okhttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.cholee.e17_okhttp.databinding.ActivityMainBinding
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val url = "https://www.google.co.kr"

        getHtmlBody(url)

    }

    private fun getHtmlBody(url: String) {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Error", "onFailure: client.newCall.enqueue", )
            }

            override fun onResponse(call: Call, response: Response) {
                val thread = Thread() {
                    runOnUiThread {
                        binding.tv.text = response.body!!.string()
                    }
                }
                thread.start()

            }
        })


    }
}