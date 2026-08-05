package com.assignment.quizappdailyrounds.presentation.quiz

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.assignment.quizappdailyrounds.domain.repository.QuizRepository
import com.assignment.quizappdailyrounds.presentation.components.OptionCard
import com.assignment.quizappdailyrounds.presentation.components.OptionState
import com.assignment.quizappdailyrounds.presentation.components.StreakIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    viewModel: QuizViewModel, onQuizFinished: () -> Unit
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when (val uiState = state) {

        QuizUiState.Loading -> {

            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

        }

        is QuizUiState.Error -> {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(uiState.message, color = Color.White)

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            viewModel.loadQuestions()
                        }) {
                        Text("Retry")
                    }
                }
            }
        }

        is QuizUiState.Success -> {

            val question = uiState.currentQuestion


            Box(modifier = Modifier.fillMaxSize()) {
                Scaffold(
                    modifier = Modifier.statusBarsPadding(), topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text("Quiz")
                            })
                    }) { padding ->

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .padding(20.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {


                        StreakIndicator(
                            streak = uiState.currentStreak
                        )

                        Text(
                            text = "Question ${uiState.currentQuestionIndex + 1} of ${uiState.questions.size}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )

                        val progress =
                            (uiState.currentQuestionIndex + 1).toFloat() / uiState.questions.size

                        val animatedProgress by animateFloatAsState(
                            targetValue = progress, animationSpec = tween(500), label = ""
                        )

                        LinearProgressIndicator(
                            progress = { animatedProgress }, modifier = Modifier.fillMaxWidth()
                        )


                        AnimatedContent(
                            targetState = question, label = "Question Animation"
                        ) { currentQuestion ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                            ) {
                                Text(
                                    text = currentQuestion.question,
                                    modifier = Modifier.padding(20.dp),
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }


                        question.options.forEachIndexed { index, option ->
                            OptionCard(
                                text = option,
                                state = getOptionState(uiState, index),
                                enabled = !uiState.showAnswer,
                                onClick = {
                                    viewModel.selectAnswer(index)
                                })
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        OutlinedButton(
                            modifier = Modifier.fillMaxWidth(), onClick = {
                                viewModel.skipQuestion()
                            }) {
                            Text("Skip")
                        }
                    }
                }
            }

        }

        is QuizUiState.Finished -> {
            LaunchedEffect(Unit) {
                onQuizFinished()
            }
        }

    }

}


private fun getOptionState(
    state: QuizUiState.Success, optionIndex: Int
): OptionState {

    if (!state.showAnswer) {
        return OptionState.NORMAL
    }

    val correctIndex = state.currentQuestion.correctAnswerIndex

    return when {
        optionIndex == correctIndex -> OptionState.CORRECT
        optionIndex == state.selectedOptionIndex -> OptionState.WRONG
        else -> OptionState.NORMAL
    }
}

class QuizViewModelFactory(
    private val repository: QuizRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            return QuizViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}