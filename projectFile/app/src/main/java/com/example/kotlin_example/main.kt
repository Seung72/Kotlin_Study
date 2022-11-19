package com.example.kotlin_example

fun main() {
    var i = 10

    when {
        i > 10 -> {
            print("i는 10과 큽니다")
        }
        i > 20 -> {
            print("i는 20과 큽니다")
        }
        else -> {
            print("i는 10보다 작습니다")
        }
    }
}