package com.assignment.quizappdailyrounds.domain.model

data class Question(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)
