package com.assignment.quizappdailyrounds.presentation.quiz

import com.assignment.quizappdailyrounds.domain.model.Question

sealed interface QuizUiState {

    data object Loading : QuizUiState

    data class Success(
        val questions: List<Question>,
        val currentQuestionIndex: Int = 0,
        val selectedOptionIndex: Int? = null,
        val showAnswer: Boolean = false,
        val correctAnswers: Int = 0,
        val skippedQuestions: Int = 0,
        val currentStreak: Int = 0,
        val longestStreak: Int = 0
    ) : QuizUiState {

        val currentQuestion: Question
            get() = questions[currentQuestionIndex]
    }

    data class Error(
        val message: String
    ) : QuizUiState

    data class Finished(
        val correctAnswers: Int,
        val skippedQuestions: Int,
        val longestStreak: Int,
        val totalQuestions: Int
    ) : QuizUiState
}
