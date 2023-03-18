package com.cholee.e18_retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class JsonPlaceDTO(
    @Expose // Json으로 부터 역직렬화 됨을 명시
    @SerializedName("userId") // Json에서 해당 이름을 가진 멤버가 대입됨을 명시
    var userId: String,

    @Expose
    @SerializedName("id")
    var id: String,

    @Expose
    @SerializedName("title")
    var title: String,

    @Expose
    @SerializedName("body")
    var body: String

)
