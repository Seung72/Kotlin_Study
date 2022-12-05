package com.example.e06_navigationview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.example.e06_navigationview.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnNavi.setOnClickListener{
            binding.layoutDrawer.openDrawer(GravityCompat.START) // START: LEFT, END: RIGHT
        }

        binding.naviView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId)
        {
            R.id.notification -> Toast.makeText(applicationContext, "알림", Toast.LENGTH_SHORT).show()
            R.id.share -> Toast.makeText(applicationContext, "공유", Toast.LENGTH_SHORT).show()
            R.id.settings -> Toast.makeText(applicationContext, "설정", Toast.LENGTH_SHORT).show()
        }
//        binding.layoutDrawer.closeDrawers()
        return true
    }

    override fun onBackPressed() {
        if (binding.layoutDrawer.isDrawerOpen(GravityCompat.START)){
            binding.layoutDrawer.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }
}
