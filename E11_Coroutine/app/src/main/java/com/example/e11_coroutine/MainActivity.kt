package com.example.e11_coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.e11_coroutine.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        coroutine()
        getHtml()
    }

    fun coroutine() {
        // 동기 실행 (Main = MainThread)
        CoroutineScope(Dispatchers.Main).launch {

            // 비동기 실행 (Default = BackgroundThread)
            val html = CoroutineScope(Dispatchers.Default).async {
                getHtmlStr()
            }.await() // val로 값을 받아오기 위해 .await() 사용

            binding.tvText.text = html
        }
    }
    fun getHtmlStr() : String {
        val client = OkHttpClient.Builder().build()
        val req = Request.Builder().url("https://www.naver.com").build()
        client.newCall(req).execute().use {
            response -> return if(response.body != null) {
                response.body!!.string()
        }
            else {
                "요청한 페이지에 문제가 있습니다."
        }
        }
    }
    // 비동기 방식으로 구현하고 CallBack 함수로 받기
    fun getHtml() {
        val client = OkHttpClient.Builder().build()
        val req = Request.Builder().url("https://www.naver.com").build()
        client.newCall(req).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
            }
            override fun onResponse(call: Call, response: Response) {
                CoroutineScope(Dispatchers.Main).launch {
                    binding.tvText.text = response.body!!.string()
                }
            }

        })

    }

}