package com.cholee.e15_room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cholee.e15_room.databinding.ActivityAddBinding
import com.cholee.e15_room.databinding.ActivityMainBinding

class AddActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddBinding
    private var db: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        db = AppDatabase.getInstance(this)
        val view = binding.root
        setContentView(view)

        val write = Runnable {
            val newDevice = Device()
            newDevice.name = binding.etName.text.toString()
            newDevice.os = binding.etOs.text.toString()
            newDevice.version = binding.etVersion.text.toString().toInt()
            db?.deviceDao()?.insert(newDevice)
        }

        binding.btnAddList.setOnClickListener {
            val thread = Thread(write)
            thread.start()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        AppDatabase.destroyInstance()
        super.onDestroy()
    }
}