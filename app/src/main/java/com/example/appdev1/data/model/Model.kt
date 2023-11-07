package com.example.appdev1.data.model

data class Model(
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)