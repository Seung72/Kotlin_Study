package com.cholee.e18_retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.cholee.e18_retrofit.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        val service = RetroClient.getInstance().create(Service::class.java)
        val call = service?.getPath("2")
        setContentView(view)

        call?.enqueue(object : Callback<JsonPlaceDTO> {
            override fun onResponse(call: Call<JsonPlaceDTO>, response: Response<JsonPlaceDTO>) {
                if(response.isSuccessful) {
                    response.body()?.let {
                        binding.tvId.text = "현재 아이디: " + it.id
                        binding.tvUserId.text = it.userId + "님"
                        binding.tvTitle.text = "제목: " + it.title
                        binding.tvBody.text = "본문: " + it.body
                    }
                }
            }

            override fun onFailure(call: Call<JsonPlaceDTO>, t: Throwable) {
                runOnUiThread{ binding.tvBody.text = "네트워크에 문제가 있습니다."}
                Log.e("onFailure-0", "onFailure: ${t.message}", )
            }

        })
    }
}