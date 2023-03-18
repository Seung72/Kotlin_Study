package com.cholee.e18_retrofit

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Service {
    @GET("/posts")
    fun get() : Call<JsonObject>

    @GET("/posts/{path}")
    fun getPath(@Path("path") path: String) : Call<JsonPlaceDTO>

    @POST("/posts")
    fun post() : Call<JsonPlaceDTO>
}