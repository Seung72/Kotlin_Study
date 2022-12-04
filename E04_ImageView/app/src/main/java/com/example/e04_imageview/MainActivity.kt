package com.example.e04_imageview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.e04_imageview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnToast.setOnClickListener{
            binding.ivProfile.setImageResource(R.drawable.ic_baseline_ads_click_24)

            Toast.makeText(this@MainActivity, "클릭하셨어요!", Toast.LENGTH_SHORT).show()
        }

    }
}