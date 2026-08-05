package com.assignment.quizappdailyrounds.presentation.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.quizappdailyrounds.domain.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizViewModel(private val repository: QuizRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<QuizUiState>(QuizUiState.Loading)

    val uiState = _uiState.asStateFlow()

    init {
        loadQuestions()
    }

    fun loadQuestions() {
        viewModelScope.launch {
            _uiState.value = QuizUiState.Loading
            withContext(Dispatchers.IO) {
                repository.getQuestions()
            }.onSuccess { questions ->
                _uiState.value = QuizUiState.Success(
                    questions = questions
                )
            }.onFailure {

                _uiState.value = QuizUiState.Error(
                    it.message ?: "Something went wrong"
                )

            }
        }
    }

    fun selectAnswer(selectedIndex: Int) {

        val currentState = _uiState.value

        if (currentState !is QuizUiState.Success) return

        // Prevent multiple taps
        if (currentState.showAnswer) return

        val currentQuestion = currentState.currentQuestion

        val isCorrect = selectedIndex == currentQuestion.correctAnswerIndex

        val updatedStreak = if (isCorrect) currentState.currentStreak + 1
        else 0

        val longestStreak = maxOf(
            currentState.longestStreak, updatedStreak
        )

        _uiState.value = currentState.copy(
            selectedOptionIndex = selectedIndex,
            showAnswer = true,
            correctAnswers = if (isCorrect) currentState.correctAnswers + 1
            else currentState.correctAnswers,
            currentStreak = updatedStreak,
            longestStreak = longestStreak
        )

        viewModelScope.launch {
            delay(1500)
            nextQuestion()
        }
    }

    fun skipQuestion() {

        val currentState = _uiState.value

        if (currentState !is QuizUiState.Success) return

        _uiState.value = currentState.copy(
            skippedQuestions = currentState.skippedQuestions + 1, currentStreak = 0
        )

        nextQuestion()
    }

    private fun nextQuestion() {

        val currentState = _uiState.value

        if (currentState !is QuizUiState.Success) return

        // Quiz Finished
        if (currentState.currentQuestionIndex == currentState.questions.lastIndex) {
            _uiState.value = QuizUiState.Finished(
                correctAnswers = currentState.correctAnswers,
                skippedQuestions = currentState.skippedQuestions,
                longestStreak = currentState.longestStreak,
                totalQuestions = currentState.questions.size
            )
            return
        }

        _uiState.value = currentState.copy(
            currentQuestionIndex = currentState.currentQuestionIndex + 1,
            selectedOptionIndex = null,
            showAnswer = false
        )
    }

    fun restartQuiz() {
        loadQuestions()
    }
}