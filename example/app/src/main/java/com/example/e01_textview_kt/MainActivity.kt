package com.example.e01_textview_kt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.e01_textview_kt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvTitle.text = "안녕하세요!"
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}
