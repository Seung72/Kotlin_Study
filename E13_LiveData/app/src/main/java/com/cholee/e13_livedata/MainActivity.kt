package com.cholee.e13_livedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.cholee.e13_livedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var liveData: MutableLiveData<String> = MutableLiveData()
    private var cnt = 0
    private var staticStr = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        liveData.observe(this, Observer {
            binding.tvLiveText.text = it
        })

        binding.btnApply.setOnClickListener{
            val userText = binding.etLiveText.text.toString()
            if (staticStr == userText) liveData.value = userText + " 중복 횟수 ${++cnt}"
            else {
                cnt = 0
                staticStr = userText
                liveData.value = userText + " 중복 횟수 ${++cnt}"
            }
        }
    }
}