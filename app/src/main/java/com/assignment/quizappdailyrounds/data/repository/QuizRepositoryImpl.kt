package com.assignment.quizappdailyrounds.data.repository

import com.assignment.quizappdailyrounds.data.api.QuizApi
import com.assignment.quizappdailyrounds.data.mapper.toDomain
import com.assignment.quizappdailyrounds.domain.repository.QuizRepository
import com.assignment.quizappdailyrounds.domain.model.Question

class QuizRepositoryImpl(
    private val api: QuizApi
) : QuizRepository {

    override suspend fun getQuestions(): Result<List<Question>> {
        return try {
            val questions = api.getQuestions()
                .map { it.toDomain() }
            Result.success(questions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}