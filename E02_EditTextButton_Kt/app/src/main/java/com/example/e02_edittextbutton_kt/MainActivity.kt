package com.example.e02_edittextbutton_kt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.e02_edittextbutton_kt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null
    private val binding get() =  mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetText.setOnClickListener {
            binding.tvTitle.text = binding.etId.text.toString()
        }
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }

}