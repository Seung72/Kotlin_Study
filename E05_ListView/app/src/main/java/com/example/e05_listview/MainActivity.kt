package com.example.e05_listview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.e05_listview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var UserList = arrayListOf<User>(
        User(R.drawable.ic_android_24dp, "승철", "24", "코틀린을 배우고 있습니다!"),
        User(R.drawable.ic_android_24dp, "Cholee", "24", "별명 중에 하나입니다."),
        User(R.drawable.ic_android_24dp, "Seung__72", "24", "인스타그램 아이디입니다."),
        User(R.drawable.ic_android_24dp, "이승철", "24", "본명 입니다")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        val item = arrayOf("Apple Pie", "Banana Bread", "Cupcake", "Donut")
//        binding.listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, item)

        val Adapter = UserAdapter(this, UserList)
        binding.listView.adapter = Adapter

        binding.listView.onItemClickListener = AdapterView.OnItemClickListener {parent, view, position, id ->
            val selectItem= parent.getItemAtPosition(position) as User
            Toast.makeText(this, "이름: "+ selectItem.name, Toast.LENGTH_SHORT).show()
        }
    }
}