package com.example.e03_intent_kt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.e03_intent_kt.databinding.ActivityMainBinding
import com.example.e03_intent_kt.databinding.ActivitySubBinding

class SubActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (intent.hasExtra("msg")) {
            binding.tvGetMsg.text = intent.getStringExtra("msg")
        }
    }
}