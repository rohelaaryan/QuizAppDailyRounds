package com.assignment.quizappdailyrounds.data.mapper

import com.assignment.quizappdailyrounds.data.dto.QuestionDto
import com.assignment.quizappdailyrounds.domain.model.Question

fun QuestionDto.toDomain(): Question {

    return Question(
        id = id,
        question = question,
        options = options,
        correctAnswerIndex = answer
    )
}