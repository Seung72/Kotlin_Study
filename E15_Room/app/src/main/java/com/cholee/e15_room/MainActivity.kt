package com.cholee.e15_room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.viewpager.widget.PagerAdapter
import com.cholee.e15_room.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var db: AppDatabase? = null
    lateinit var adapter: DeviceAdapter

    private var deviceList = listOf<Device>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db = AppDatabase.getInstance(applicationContext)!!

        val r = Runnable {
            try {
                deviceList = db?.deviceDao()?.getAll()!!

                adapter = DeviceAdapter(this, deviceList)
                adapter.notifyDataSetChanged()

                binding.rvDevices.adapter = adapter
                binding.rvDevices.layoutManager = LinearLayoutManager(this)
                binding.rvDevices.setHasFixedSize(true)
            } catch (e: Exception) {
                Log.e("DB error", e.toString())
            }
        }

        val thread = Thread(r)
        thread.start()

        binding.btnMoveAddAct.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        AppDatabase.destroyInstance()
        db = null
        super.onDestroy()
    }
}