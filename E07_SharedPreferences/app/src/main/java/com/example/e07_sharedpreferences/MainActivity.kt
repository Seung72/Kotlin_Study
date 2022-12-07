package com.example.e07_sharedpreferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.e07_sharedpreferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        loadData()
    }

    private fun saveData() {
        val prefName = getSharedPreferences("pref", 0)
        val edit = prefName.edit()
        edit.putString("name", binding.etHello.text.toString())
        edit.apply()
    }
    private fun loadData() {
        val prefName = getSharedPreferences("pref", 0)
        binding.etHello.setText(prefName.getString("name","Guest"))
    }

    override fun onDestroy() {
        super.onDestroy()
        saveData()
    }
}