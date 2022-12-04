package com.example.e05_listview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class UserAdapter(val context: Context, val UserList: ArrayList<User>) : BaseAdapter() {
    override fun getCount(): Int {
        // 리스트뷰의 개수만큼 호출
        return UserList.size
    }

    override fun getItem(position: Int): Any {
        // UserList의 포지션에 맞는 아이템을 불러옴
        return UserList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        // view에 LayoutInflater를 이용하여 context로 부터 생성한 layout을 가져옴
        val view: View = LayoutInflater.from(context).inflate(R.layout.list_item_user, null)

        // list의 개체들 id 선언
        val profile = view.findViewById<ImageView>(R.id.iv_profile)
        val name = view.findViewById<TextView>(R.id.tv_name)
        val greet = view.findViewById<TextView>(R.id.tv_greet)
        val age = view.findViewById<TextView>(R.id.tv_age)

        //
        val user = UserList[position]

        // 선언된 id에 데이터 입력
        profile.setImageResource(user.profile)
        name.text = user.name
        greet.text = user.greet
        age.text = user.age

        // view 반환
        return view
    }

}