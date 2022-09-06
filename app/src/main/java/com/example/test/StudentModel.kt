package com.example.test

import java.util.*

data class StudentModel(
    var id: Int = getId(),
    var name: String = "",
    var email: String = ""

){
    companion object {
        fun getId(): Int = Random().nextInt(100)
    }
}


