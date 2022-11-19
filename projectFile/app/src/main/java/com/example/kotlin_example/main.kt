package com.example.kotlin_example

fun main() {
    val seung = BirthDay("승철", 1999, 6, 16)
    println(seung.cake)
    seung.cakeChange()
    println(seung.cake)

}
class BirthDay(val name: String, var year: Int, var month: Int, var day: Int) {
    var cake = "생크림 케이크"
        private set
        get() = " 기본 케이크 : $field"

    init{
        println("새로운 생일이 생성되었습니다.")
    }

    fun cakeChange() {
        cake = "초코 케이크"
    }
}