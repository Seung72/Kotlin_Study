package com.example.e09_recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e09_recyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val profileList = arrayListOf(
            Profiles(R.drawable.ic_baseline_man, "박정호", 34, "Dev Team Leader"),
            Profiles(R.drawable.ic_baseline_man, "이승철", 24, "Android Developer"),
            Profiles(R.drawable.ic_baseline_man, "심형석", 24, "Tax Accounting"),
            Profiles(R.drawable.ic_baseline_man, "김철수", 30, "Flutter Developer"),
            Profiles(R.drawable.ic_baseline_woman_24, "이영희", 25, "App QA Tester"),
            Profiles(R.drawable.ic_baseline_woman_24, "최지현", 31, "BackEnd Developer")
            )

        binding.rvProfile.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvProfile.setHasFixedSize(true)

        binding.rvProfile.adapter = ProfileAdapter(profileList)




    }



}