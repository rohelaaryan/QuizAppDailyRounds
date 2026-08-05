package com.assignment.quizappdailyrounds.data.dto

import com.google.gson.annotations.SerializedName

data class QuestionDto(

    @SerializedName("id")
    val id: Int,

    @SerializedName("question")
    val question: String,

    @SerializedName("options")
    val options: List<String>,

    @SerializedName("correctOptionIndex")
    val answer: Int
)
