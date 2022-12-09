package com.example.e08_webview

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.example.e08_webview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = WebViewClient()
        binding.webView.webChromeClient = WebChromeClient()
        binding.webView.loadUrl("https://www.naver.com")
    }

    override fun onBackPressed() {
        if(binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}