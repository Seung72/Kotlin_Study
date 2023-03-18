package com.cholee.e18_retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroClient {
    private var instance: Retrofit? = null
    fun getInstance(): Retrofit {
        instance?.let {
            return it
        } ?: run {
            instance = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                // 응답을 변환할 방식 (Gson으로 변경)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return instance!!
        }
    }
}