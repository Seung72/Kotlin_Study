package com.example.e03_intent_kt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.e03_intent_kt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnIntent.setOnClickListener{
            val intent = Intent(this, SubActivity::class.java)
            intent.putExtra("msg", binding.tvSendMessage.text.toString())
            startActivity(intent)
            finish()
        }
    }
}