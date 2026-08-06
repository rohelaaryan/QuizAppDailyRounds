package com.assignment.quizappdailyrounds.data.mapper

import com.assignment.quizappdailyrounds.data.dto.QuestionDto
import com.assignment.quizappdailyrounds.domain.model.Question

fun QuestionDto.toDomain(): Question {

    val correctAnswer = options[answer]

    val shuffledOptions = options.shuffled()

    return Question(
        id = id,
        question = question,
        options = shuffledOptions,
        correctAnswerIndex = shuffledOptions.indexOf(correctAnswer)
    )
}