package com.assignment.quizappdailyrounds.domain.repository

import com.assignment.quizappdailyrounds.domain.model.Question

interface QuizRepository {
    suspend fun getQuestions(): Result<List<Question>>
}